package org.ambit.usb;

public class DeviceFactory {
	
	public static Device openDevice(int vendorId, int productId) 
		throws UsbException {
		HidUsbDevice device = new HidUsbDevice();
		device.open(vendorId, productId);
		
		return device;
	}
}
