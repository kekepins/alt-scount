package org.ambit.usb;

import org.ambit.data.AmbitModel;
import org.hid4java.HidDevice;
import org.hid4java.HidException;
import org.hid4java.HidManager;
import org.hid4java.HidServices;

public class HidUsbDevice implements Device {
	
	private HidDevice hidDDevice;
	private AmbitModel ambitModel;
	
	HidUsbDevice() {
	}

	@Override
	public int write(byte[] message, int packetLength, byte reportId) 
		throws UsbException{
		int ret =  hidDDevice.write(message, packetLength, reportId);
		if ( ret == -1) {
			// Error
			throw new UsbException("Error during write [" + getLastErrorMessage() +"]");
		}
		
		return ret;
	}

	@Override
	public int read(byte[] bytes, int timeoutMillis) throws UsbException{
		
		int ret = hidDDevice.read(bytes, timeoutMillis);
		if ( ret == -1) {
			// Error
			throw new UsbException("Error during read [" + getLastErrorMessage() +"]");
		}
		
		return ret;
		
	}

	@Override
	public String getLastErrorMessage() {
		return hidDDevice.getLastErrorMessage();
	}

	@Override
	public void close() {
		if ( hidDDevice != null ) {
			hidDDevice.close();
		}
	}
	
	public boolean isOpen() {
		return (hidDDevice!= null && hidDDevice.isOpen());
	}

	/**
	 * Open a device from vendorId an productId
	 */
	@Override
	public void open(int vendorId, int productId) 
		throws UsbException {
		HidServices hidServices = null;
		try {
			hidServices = HidManager.getHidServices();
		} 
		catch (HidException e) {
			throw new UsbException("Cant get HidServices", e);
			
		}
		
		hidDDevice = hidServices.getHidDevice(vendorId, productId, null);
		
		if ( hidDDevice != null ) {
			ambitModel = AmbitModel.fromId(hidDDevice.getProductId());
		}
	}

	@Override
	public AmbitModel getAmbitModel() {
		// TODO Auto-generated method stub
		return ambitModel;
	}


}
