package org.ambit.altscount.altscountandroid.org.ambit.usb.android;

import android.content.Context;
import android.hardware.usb.UsbManager;

import org.ambit.usb.Device;
import org.ambit.usb.UsbException;

/**
 * Created by philippe on 16/04/2015.
 */
public class DeviceFactory {

    public static Device openDevice(UsbManager manager, Context context, int vendorId, int productId)
            throws UsbException {
        AmbitAndroidDevice device = new AmbitAndroidDevice();
        device.setManager(manager);
        device.setContext(context);
        device.open(vendorId, productId);

        return device;
    }
}
