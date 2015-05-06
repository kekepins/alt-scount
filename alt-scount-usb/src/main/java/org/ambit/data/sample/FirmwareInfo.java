package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class FirmwareInfo extends Activity {
	
	private short fw1;
	private short fw2;
	private short fw3;
	private short year;
	private short month;
	private short day;
	private short hour;
	private short minute;
	private short sec;
	

	public FirmwareInfo(int size, int sampleNumber) {
		super(size, sampleNumber);
	}
	
	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		//System.out.println("logType:" + logType);
		
		name = "firmware info";
		//System.out.println("Firmware info");
		int time2 = DataUtils.readInt(data, pos);
		//System.out.println("time2:" + time2);
		pos += 4;

		pos++;
		fw1 = DataUtils.readByte(data, pos++);
		fw2 = DataUtils.readByte(data, pos++);
		fw3 = DataUtils.readShort(data, pos);
		pos += 2;
		//System.out.println("Version: " + fw1 + ":" + fw2 + ":" + fw3);
		year = DataUtils.readShort(data, pos); // 2015 => 7df => pos
														// 67
		pos += 2;
		month = DataUtils.readByte(data, pos++);
		day = DataUtils.readByte(data, pos++);
		hour = DataUtils.readByte(data, pos++);
		minute = DataUtils.readByte(data, pos++);
		sec = DataUtils.readShort(data, pos);
		//System.out.println(String.format("Date %d/%d/%d %d:%d:%d", year,
		//		month, day, hour, minute, sec));
		pos += 2;
		
		return posIn + size - 1;

	}

	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}

}
