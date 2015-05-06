package org.ambit.data;

import java.util.List;

import org.ambit.util.DataUtils;

public class LogData {
	/*private int lastEntry;
	private int firstEntry;
	private int entryCount;
	private int nextFreeAdress;
	private int current;
	private int next;
	private int start;*/
	//private 
	
	public LogData(List<Byte> data) {
		fromData(data);
	}
	
	private void fromData(List<Byte> data) {
		//System.out.println("data");
		int pos = 0;
		short sampleLen =  DataUtils.readShort(data, pos);
		//System.out.println("sampleLen:" + sampleLen);
		sampleLen+=2;
		
		short type =  DataUtils.readByte(data, pos);
		//System.out.println("type:" + type);
		pos++;

	  
	}

}
