package org.ambit.altscount.altscountandroid.org.ambit.usb.android;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;

import org.ambit.data.AmbitModel;
import org.ambit.usb.Device;
import org.ambit.usb.UsbException;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


// https://github.com/devunwired/accessory-samples/blob/65ea8de034d36ce29616a2320a6bf5bff4473ae5/ScaleMonitor/src/com/examples/usb/scalemonitor/ScaleActivity.java

/**
 * Created by philippe on 14/04/2015.
 */
public class AmbitAndroidDevice implements Device, Runnable {

    private static final String TAG = "USB";

    private static final String ACTION_USB_PERMISSION = "org.ambit.altscount.altscountandroid.USB_PERMISSION";


    private UsbManager manager;

    private Context context;

    private UsbDevice ambitDevice;
    private UsbInterface ambitInterface;

    private UsbEndpoint endPointAmbitToAndroid;
    private UsbEndpoint endPointAndroidToAmbit;

    private UsbDeviceConnection deviceConnection;

    private UsbRequest mUsbRequest;

    private int readCount = 0;
    private long lastRead = 0;

    // Control variable for Monitoring thread
    private boolean stopReadingThread = false;

    private AmbitModel ambitModel;

    // read datas
    private BlockingQueue<byte[]> readDatas = new LinkedBlockingQueue<byte[]>();
    private Thread readingThread;



    @Override
    public int write(byte[] message, int packetLength, byte reportId) throws UsbException {

        if ( !isOpen()) {
            throw new UsbException("Device is not Open");
        }

        // Write the data as a bulk transfer with defined data length.
        int result = deviceConnection.bulkTransfer(endPointAndroidToAmbit, message, packetLength, 500);
        if ( result == -1 ) {
            throw new UsbException("write return -1");
        }

        Log.i("AUB", "bulk send " + result);
        return result;
    }


    @Override
    public String getLastErrorMessage() {
        return "no info";
    }

    @Override
    public void close() {
        deviceConnection.releaseInterface(ambitInterface);
        deviceConnection.close();

    }

    @Override
    public void open(int vendorId, int productId) throws UsbException {
        HashMap<String, UsbDevice> devices = manager.getDeviceList();

        if ( devices != null ) {
            for ( UsbDevice usbDevice : devices.values()) {
                if ( (usbDevice.getVendorId()==  vendorId) && (usbDevice.getProductId() == productId) ) {
                    ambitDevice = usbDevice;

                    Log.i("AUB", "Ambit device found " + ambitDevice);
                }
            }
        }

        // No device found at the moment
        if( ambitDevice == null) {
            return;
        }


        ambitModel = AmbitModel.fromId(productId);


        // request permission
        requestPermission(ambitDevice);

        ambitInterface = ambitDevice.getInterface(0);
        Log.i("AUB", "interface " + ambitInterface);
        endPointAmbitToAndroid = ambitInterface.getEndpoint(0);
        Log.i("AUB", "endPointAmbitToAndroid " + endPointAmbitToAndroid);
        endPointAndroidToAmbit = ambitInterface.getEndpoint(1);
        Log.i("AUB", "endPointAndroidToAmbit " + endPointAndroidToAmbit);

        deviceConnection = manager.openDevice(ambitDevice);
        boolean claimed = deviceConnection.claimInterface(ambitInterface, true);

        Log.i("AUB","claimed" + claimed);

        Log.i("AUB","Open is finish");

        // Init reading thread
        stopReadingThread = false;
        readingThread = new Thread(this);
        readingThread.start();
    }

    @Override
    public boolean isOpen() {
        return ambitDevice != null;
    }

    @Override
    public AmbitModel getAmbitModel() {
        return ambitModel;
    }


    public void setManager(UsbManager manager) {
        this.manager = manager;
        //dumpInfo(manager);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void requestPermission(UsbDevice device) {
        PendingIntent permissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        context.registerReceiver(mUsbReceiver, filter);
        manager.requestPermission(device, permissionIntent);
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            //androidPermission.granted(new AndroidAmbitDevice(mUsbManager, device));
                        }
                    } else {
                        //androidPermission.denied();
                    }
                }
            }
        }
    };


    @Override
    public void log(String log) {
        Log.i("MSG", log);
    }

    @Override
    public int read(byte[] buffer, int timeoutMillis) throws UsbException {
        log("** Read IN" );

        try {
            byte[] readBytes = readDatas.poll(timeoutMillis, TimeUnit.MILLISECONDS);
            log("** POLL out" );
            if ( readBytes != null ) {
                log("** Copy result" );
                // copy
                int idx = 0;
                for ( byte readByte :readBytes ) {
                    buffer[idx] = readBytes[idx];
                    idx++;
                }
                return readBytes.length;
            }
            else {
                log("** No more result" );
                return 0;
            }

        } catch (InterruptedException e) {
            log("** Interrupt" );
            e.printStackTrace();
            return -1;
        }


        //return queueRead(buffer, 0, 64);
        //log("read send -1");
        //return -1;
    }


    @Override
    public void run() {
        ByteBuffer readBuffer = ByteBuffer.allocate(64);
        mUsbRequest = new UsbRequest();

        // Although documentation says that UsbRequest doesn't work on Endpoint 0 it actually works
        mUsbRequest.initialize(deviceConnection, endPointAmbitToAndroid);

        log( "Status line monitoring thread started");

        while (!stopReadingThread) {
            mUsbRequest.queue(readBuffer, 64);
            UsbRequest retRequest = deviceConnection.requestWait();

            // The request returns when any line status has changed
            if  ((!stopReadingThread) && (retRequest != null) && (retRequest.getEndpoint()==endPointAmbitToAndroid)) {

                log("request READ");

                byte[] buffer = new byte[64];
                readBuffer.rewind();
                //final byte[] bs = new byte[length];
                readBuffer.get(buffer, 0, 64);
                long current = System.currentTimeMillis();
                long timeBeforeLast = 0;
                if (lastRead != 0) {
                    timeBeforeLast = current - lastRead;
                }
                lastRead = current;


                log("---- data ---- " + readCount++);
                log("Elapse " + timeBeforeLast);
                for (byte b: buffer){
                    log( String.format("0x%20x", b));
                }
                log("-------- ");

                // add to result queue
                readDatas.add(buffer);
             }

        }
        log("Status line monitoring thread stopped");
    }

}
