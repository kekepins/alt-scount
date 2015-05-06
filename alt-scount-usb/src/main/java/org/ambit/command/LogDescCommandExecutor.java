package org.ambit.command;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.ambit.data.AmbitModel;
import org.ambit.data.LogInfo;
import org.ambit.usb.Device;
import org.ambit.usb.UsbException;
import org.ambit.util.DataUtils;


/**
 * Read log infos
 */
public class LogDescCommandExecutor {
	private static final int PACKET_LENGTH = 64;

	private final static int LOG_START = 0x000f4240; // 1 000 000
	
	//private final static short CHUNK_SIZE = 0x0400; // not the same for all models 
	                                                // 1024

	private int partNumber;
	private int adress;

	
	public void setAdress(int adress) {
		this.adress = adress;
	}
	
	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}


	
	protected AmbitCommand getCommand() {
		return AmbitCommand.LOG_READ;
	}
	

	protected byte[] getSendData(AmbitModel ambitModel) {
		
		if ( this.adress == 0 ) {
			int ad = LOG_START + (partNumber * ambitModel.getChunkSize());
			byte[] data2 = DataUtils.intToBytes(1024, ByteOrder.LITTLE_ENDIAN);
			byte[] data1 = DataUtils.intToBytes(ad, ByteOrder.LITTLE_ENDIAN);
	
			// start + chunksize ambit2
			byte[] sendData = new byte[8];
			sendData[0] = data1[0];
			sendData[1] = data1[1];
			sendData[2] = data1[2];
			sendData[3] = data1[3];
			sendData[4] = data2[0];
			sendData[5] = data2[1];
			sendData[6] = data2[2];
			sendData[7] = data2[3];
			
			return sendData;
		}
		else {
			byte[] data2 = DataUtils.intToBytes(1024, ByteOrder.LITTLE_ENDIAN);
			byte[] data1 = DataUtils.intToBytes(adress, ByteOrder.LITTLE_ENDIAN);
	
			// start + chunksize ambit2
			byte[] sendData = new byte[8];
			sendData[0] = data1[0];
			sendData[1] = data1[1];
			sendData[2] = data1[2];
			sendData[3] = data1[3];
			sendData[4] = data2[0];
			sendData[5] = data2[1];
			sendData[6] = data2[2];
			sendData[7] = data2[3];
			
			return sendData;			
		}
	}
	
	private List<Byte> executeCmdPart(Device ambit, int idxFirst) throws UsbException {
		AmbitSendData header = new AmbitSendData(getCommand(), getSendData(ambit.getAmbitModel()));
		byte[] dataToSend = header.getData();	
		int val = ambit.write(dataToSend, PACKET_LENGTH, (byte) 0);
		if (val == -1) {
			throw new UsbException("Command execution error"  + ambit.getLastErrorMessage());
		}
		
		List<Byte> resultData = new ArrayList<Byte>();
		boolean moreData = true;
		boolean isFirst = true;
		while (moreData) {
			byte readData[] = new byte[PACKET_LENGTH];
			// This method will now block for 500ms or until data is read
			val = ambit.read(readData, 500);
			
			switch (val) {
			case -1:
				throw new UsbException("Error during read " + ambit.getLastErrorMessage());
				
			case 0:
				moreData = false;
				break;
			default:
				int startIdx = 8;
				if ( isFirst) {
					startIdx = idxFirst;
				}
				for ( int i = startIdx; i < (readData.length - 2); i++) {
					resultData.add(readData[i]);
				}
				break;
			}
			
			isFirst = false;

		}
		
		return resultData;
	}
	
	public LogInfo executeCommand(Device ambit) throws UsbException {
		List<Byte> resultCommand = executeCmdPart( ambit, 20);
		
		// check if a second call is needed
		int pemPosition = getPMEMPosition(resultCommand);
		if ( pemPosition == -1 ) {
			throw new UsbException("No PMEM found");
		}
		
		
		partNumber ++;
		resultCommand = resultCommand.subList(0, 1032);
		// log info is in 2 parts ask for more data
		List<Byte> resultCommand2 = executeCmdPart( ambit, 28).subList(0, 1024);
		
		//System.out.println("Size : " + resultCommand2.size());
		resultCommand.addAll(resultCommand2);
	
		return extractResult(resultCommand, ambit.getAmbitModel().getChunkSize());
	}
	

	
	protected LogInfo extractResult(List<Byte> byteBuffer, int chunkSize) {
		
			
		return new LogInfo(byteBuffer, partNumber + 1, chunkSize);
	}
	
	private int getPMEMPosition(List<Byte> data) {
		
        int pos = 0;
        while ((data.get(pos) != 0x50) || (data.get(pos+1) != 0x4d) ) {
        	pos++;
        	
        	if ((pos+3) >=  data.size() ) {
        		return -1;
        	}
        			
        }
        
        return pos;
		
	}

}
