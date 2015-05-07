package org.ambit.altscount.altscountandroid.org.ambit.usb.android;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import org.ambit.command.AmbitCommand;
import org.ambit.command.AmbitSendData;
import org.ambit.command.DeviceInfoCommandExecutor;
import org.ambit.command.SamplesReader;
import org.ambit.command.SettingsCommandExecutor;
import org.ambit.command.StatusCommandExecutor;
import org.ambit.data.AmbitInfo;
import org.ambit.data.AmbitSettings;
import org.ambit.data.LogInfo;
import org.ambit.usb.Device;
import org.ambit.usb.UsbException;

import java.util.List;

/**
 * Created by philippe on 16/04/2015.
 */
public class AmbitManager {
    private final static int SUUNTO_VENDOR  = 0x1493;
    private final static int AMBIT_2R   	= 0x1d;   // 29

    private Device ambitDevice;
    private UsbManager manager;
    private Context context;

    public AmbitManager(UsbManager manager, Context context) {
        this.manager = manager;
        this.context = context;
    }

    public boolean isConnected() {
        return (ambitDevice != null && ambitDevice.isOpen());
    }

    public void connectDevice() throws UsbException {
        if ( ambitDevice == null || (ambitDevice != null && !ambitDevice.isOpen()) ) {
            ambitDevice = DeviceFactory.openDevice(manager, context, SUUNTO_VENDOR, AMBIT_2R);
        }
    }

    /**
     * Get all Log info stored in ambit
     *
     * @return
     * @throws UsbException
     */
    public List<LogInfo> readMoveDescriptions() throws UsbException {
        SamplesReader samplesReader = new SamplesReader();
        return samplesReader.readLogInfos(ambitDevice);
    }

    /**
     * Get device charge
     *
     * @throws UsbException
     */
    public short getDeviceCharge() throws UsbException {
        StatusCommandExecutor statusCommandExecutor = new StatusCommandExecutor();
        return statusCommandExecutor.executeCommand(ambitDevice);

    }

    public AmbitInfo getDeviceInfo() throws UsbException {
        DeviceInfoCommandExecutor deviceInfoCommandExecutor = new DeviceInfoCommandExecutor();
        return deviceInfoCommandExecutor.executeCommand(ambitDevice);
    }

    /**
     * Get settings
     */
    public AmbitSettings getSettings() throws UsbException {
        SettingsCommandExecutor settingCommand = new SettingsCommandExecutor();
        return settingCommand.executeCommand(ambitDevice);
    }


    /**
     * Get settings
     */
    public AmbitSettings getSettings2() throws UsbException {
        /*SettingsCommandExecutor settingCommand = new SettingsCommandExecutor();
        return settingCommand.executeCommand(ambitDevice);*/

        //AmbitSendData sendData = new AmbitSendData(AmbitCommand.PERSONNAL_SETTINGS, null);
        AmbitSendData sendData = new AmbitSendData(AmbitCommand.STATUS, null);

        byte[] data = sendData.getData();
        int val = ambitDevice.write(data, 64, (byte) 0);
        ambitDevice.log("Message write cmd " + AmbitCommand.STATUS);

        /*
        // Prepare to read a single data packet
        boolean moreData = true;
        boolean isFirst = true;
        while (moreData) {
            byte readData[] = new byte[64];
            val = ambitDevice.read(readData, 500);
            ambitDevice.log("Message read cmd " + AmbitCommand.PERSONNAL_SETTINGS + " ret " + val);

            switch (val) {
                case 0:
                    moreData = false;
                    break;
                default:

                    break;
            }

            isFirst = false;
        }
        */

        return null;

    }

    //public
}
