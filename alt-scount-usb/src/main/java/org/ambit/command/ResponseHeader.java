package org.ambit.command;


import org.ambit.util.DataUtils;


public class ResponseHeader {
	
	private final static byte FIRST_PACKET = 93;
	private final static byte OTHER_PACKET = 94;
	
	private final static int POS_SIZE = 16;  
	private final static int POS_TYPE = 2;
	private int size;
	private boolean isFirst;
	
	public ResponseHeader(byte[] data) {
		fromData(data);
	}
	
	private void fromData(byte[] data) {
		short type = DataUtils.readByte(data, POS_TYPE);
		if ( type == FIRST_PACKET ) {
			isFirst = true;
		}
		else {
			isFirst = false;
		}
		
		size = DataUtils.readInt(data, POS_SIZE);
		//System.out.println("Size:" + size);
		//System.out.println("isFirst:" + isFirst);
	}
	
	public int getSize() {
		return size;
	}
}
