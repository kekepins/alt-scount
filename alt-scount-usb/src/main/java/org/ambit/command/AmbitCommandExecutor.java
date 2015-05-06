package org.ambit.command;

import java.util.ArrayList;
import java.util.List;

import org.ambit.data.AmbitModel;
import org.ambit.usb.Device;
import org.ambit.usb.UsbException;
import org.ambit.util.DisplayUtils;


public abstract class AmbitCommandExecutor <T>{
	
	private static final int PACKET_LENGTH 				= 64;
	private static final int FIRST_PACKET_DATA_START 	= 20;
	private static final int OTHER_PACKET_DATA_START 	= 8;
	private static final int READ_TIMEOUT 				= 500; // ms
	
	protected abstract AmbitCommand getCommand();
	protected abstract T extractResult(List<Byte> byteBuffer);
	
	/**
	 * To override if there is data to send to the ambit with the command  
	 * @param ambitModel 
	 */
	protected byte[] getSendData(AmbitModel ambitModel) {
		return null;
	}
	
	/**
	 * Execute a command on the ambit
	 * 
	 * 1. Send command data
	 * 2. Read response
	 * 3. Extract result for   
	 */
	public T executeCommand(Device ambitDevice) throws UsbException {
		AmbitSendData sendData = new AmbitSendData(getCommand(), getSendData(ambitDevice.getAmbitModel()));
		byte[] data = sendData.getData();		
		int val = ambitDevice.write(data, PACKET_LENGTH, (byte) 0);
		ambitDevice.log("Message write cmd " + getCommand());
		
		List<Byte> byteBuffer = new ArrayList<Byte>();
		
		// Prepare to read a single data packet
		boolean moreData = true;
		boolean isFirst = true;
		while (moreData) {
			byte readData[] = new byte[PACKET_LENGTH];
			val = ambitDevice.read(readData, READ_TIMEOUT);
			ambitDevice.log("Message read cmd " + getCommand());
			
			
			switch (val) {
				case 0:
					moreData = false;
					break;
				default:
					DisplayUtils.displayBytes(readData);
					// Where is use full data is starting ? 
					int startIdx = OTHER_PACKET_DATA_START;
					if ( isFirst) {
						startIdx = FIRST_PACKET_DATA_START;
					}
					for ( int i = startIdx; i < (readData.length - 2); i++) {
						byteBuffer.add(readData[i]);
					}
					break;
				}
			
			isFirst = false;
		}
		
		return extractResult(byteBuffer);

	}
}
