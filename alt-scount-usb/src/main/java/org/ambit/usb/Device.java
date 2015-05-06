package org.ambit.usb;

import org.ambit.data.AmbitModel;

/*
 * Interaction with a device abstraction
 * Real implementation with hid4java and android usb layer
 */
public interface Device {
	
	public int write(byte[] message, int packetLength, byte reportId) throws UsbException;
	
	public int read(byte[] bytes, int timeoutMillis) throws UsbException;
	
	public String getLastErrorMessage();
	
	public void close();

	public void open(int vendorId, int productId) throws UsbException;
	
	public boolean isOpen();
	
	public AmbitModel getAmbitModel();
	
	public void log(String log);
}
