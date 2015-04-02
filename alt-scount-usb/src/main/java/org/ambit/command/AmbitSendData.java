package org.ambit.command;

import java.nio.ByteOrder;
import java.util.Arrays;

import org.ambit.util.CRCUtil;
import org.ambit.util.DataUtils;


/**
 * Construct message to send 
 */
public class AmbitSendData {
	
	private static final int MAX_PACKET_SIZE = 64;
	private static byte REPORT_ID 			= 0x3F;
	private static short seqNb = 0;
	
	private byte[] data;
	
	/**
	 * Command + extra data to send (can be null) 
	 * FIXME : at the moment manage only one packet
	 */
	public AmbitSendData(AmbitCommand command, byte[] msgData) {
		data = new byte[MAX_PACKET_SIZE];
		
		int dataLen = 0;
		if ( msgData != null ) {
			dataLen = msgData.length;
		}
		
		int packetCount = 1;
		
		// Calculate number of packets
	    if (dataLen > 42) {
	    	packetCount = 2 + (dataLen - 42)/54;
	    }
	    
	    int packetPayloadLen =  Math.min(42, dataLen);
	    
	    
		data[0] = REPORT_ID;   
		data[1] = (byte) (packetPayloadLen + 12 + 8);  // UL
		data[2] = 0x5D;        
		data[3] = (byte) (packetPayloadLen + 12);  // ML
		
		byte[] partSeq = DataUtils.shortToBytes((short)packetCount, ByteOrder.LITTLE_ENDIAN);
		data[4] = partSeq[0]; 
		data[5] = partSeq[1];
		
		// Crc 
		short crc = (short) CRCUtil.crc_16_ccitt(Arrays.copyOfRange(data, 2, 6), 4);
		byte[] crcByte = DataUtils.shortToBytes(crc, ByteOrder.LITTLE_ENDIAN);
		data[6] = crcByte[0];        // CRC
		data[7] = crcByte[1];  		 // CRC
		
		byte[] cmd = DataUtils.shortToBytes(command.getCommandId(), ByteOrder.BIG_ENDIAN);
		data[8] = cmd[0];
		data[9] = cmd[1];
		data[10] = 0x05;       // send rec 
		data[11] = 0x00;       // send rec  
		data[12] = 0x09;       // format      
		data[13] = 0x00;       // format 
		
		byte[] seq = DataUtils.shortToBytes(seqNb ++, ByteOrder.BIG_ENDIAN);
		data[14] = seq[0];       // seq   --> diff numero de la sequence
		data[15] = seq[1];       // seq
		
		byte[] payloadlenBytes = DataUtils.intToBytes(dataLen, ByteOrder.LITTLE_ENDIAN);
		data[16] = payloadlenBytes[0];         // payload len 
		data[17] = payloadlenBytes[1];         // payload len
		data[18] = payloadlenBytes[2];         // payload len  
		data[19] = payloadlenBytes[3];         // payload len 
		
		// add message data if any
		if ( msgData != null ) {
			for ( int i = 0; i < dataLen; i++ ) {
				data[20 + i] = msgData[i];
			}
		}
		int idxCrx = 20 + dataLen;
		int crcMsg = CRCUtil.crc_16_ccitt_init(Arrays.copyOfRange(data, 8, idxCrx), (idxCrx-8), crc);
		byte[] crcMsgBytes = DataUtils.shortToBytes((short)crcMsg, ByteOrder.LITTLE_ENDIAN);
		
		// Add crc to end
		data[idxCrx] = crcMsgBytes[0];  
		data[idxCrx+1] = crcMsgBytes[1];  
	}
	
	
	public byte[] getData() {
		return data;
	}


}
