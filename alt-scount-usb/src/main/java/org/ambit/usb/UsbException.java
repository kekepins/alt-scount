package org.ambit.usb;


@SuppressWarnings("serial")
public class UsbException extends Exception{

	public UsbException(String msg, Exception e) {
		super(msg, e);

	}

	public UsbException(String msg) {
		super(msg);
	}

}
