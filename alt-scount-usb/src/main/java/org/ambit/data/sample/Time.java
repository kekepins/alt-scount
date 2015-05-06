package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class Time extends Activity{
	
	private int time;
	private short hour;
	private short minute;
	private short sec;

	public Time(int size, int sampleNumber) {
		super(size, sampleNumber);
	}
	
	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		//System.out.println("logType:" + logType);
		
		name = "time";
		//System.out.println("Time");
		time = DataUtils.readInt(data, pos);
		//System.out.println("time3:" + time4);
		pos += 4;
		
		pos++;
		
		hour = DataUtils.readByte(data, pos++);
		minute = DataUtils.readByte(data, pos++);
		sec = DataUtils.readByte(data, pos++);
		//System.out.println(String.format("Date %d:%d:%d",  hour3, minute3, sec3));
		
		return posIn + size - 1;
	}


	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}

}
