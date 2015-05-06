package org.ambit.altscount;

import org.ambit.altscount.controller.AmbitManager;
import org.ambit.usb.UsbException;

public class TestUsb {
	public static void main(String[] args) {
		AmbitManager ambitManager = new AmbitManager();
		try {
			ambitManager.connectDevice();
			short charge = ambitManager.getDeviceCharge();
			System.out.println("Charge " + charge);
			//ambitManager.getSettings();
			//ambitManager.getDeviceInfo();
		} catch (UsbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
