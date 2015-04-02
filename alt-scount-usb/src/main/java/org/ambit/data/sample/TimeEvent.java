package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class TimeEvent extends Activity {
	
	private int time;
	private short year; 
	private short month;
	private short day;
	private short hour;
	private short minute;
	private short sec;
	private int duration;
	private int distance;


	public TimeEvent(int size, int sampleNumber) {
		super(size, sampleNumber);
	}
	
	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		//System.out.println("logType:" + logType);
		
		name = "time event";
		//System.out.println("Time Event");
		time = DataUtils.readInt(data, pos);
		pos += 4;
		pos++;
		pos++;
		
		year = DataUtils.readShort(data, pos); 
		// 67
		pos += 2;
		month = DataUtils.readByte(data, pos++);
		day = DataUtils.readByte(data, pos++);
		hour = DataUtils.readByte(data, pos++);
		minute = DataUtils.readByte(data, pos++);
		sec = DataUtils.readShort(data, pos);
		/*System.out.println(String.format("Date %d/%d/%d %d:%d:%d", year2,
				month2, day2, hour2, minute2, sec2));*/
		pos += 2;

		duration = DataUtils.readInt(data, pos);
		//System.out.println("duration:" + duration);
		pos += 4;
		
		distance = DataUtils.readInt(data, pos);
		//System.out.println("distance:" + distance);
		pos += 4;
		
		return posIn + size - 1;
	}


	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}

}
