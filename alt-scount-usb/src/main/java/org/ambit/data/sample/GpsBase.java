package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class GpsBase extends Activity {
	
	private short navType;
	private short year;
	private short month;
	private short day;
	private short hour;
	private short minute;
	private int sec;
	private int latitude;
	private int longitude;
	private int altitude;


	public GpsBase(int size, int sampleNumber) {
		super(size, sampleNumber);
	}
	

	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		//System.out.println("logType:" + logType);
		
		name = "gps base";
		//System.out.println("Gps base");
		int time5 = DataUtils.readInt(data, pos);
		//System.out.println("time5:" + time5);
		pos += 4;
		
		pos++;
		
		pos += 2;
		
		navType = DataUtils.readShort(data, pos);
		//System.out.println("navType:" + navType);
		pos += 2;
		
		year = DataUtils.readShort(data, pos); // 2015 => 7df => pos
		// 67
		pos += 2;
		month = DataUtils.readByte(data, pos++);
		day = DataUtils.readByte(data, pos++);
		hour = DataUtils.readByte(data, pos++);
		minute = DataUtils.readByte(data, pos++);
		sec = DataUtils.readShort(data, pos);
		pos += 2;
		
		sec = sec /1000;
		
		latitude = DataUtils.readInt(data, pos);
		//System.out.println("latitude:" + latitude);
		pos += 4;

		longitude = DataUtils.readInt(data, pos);
		//System.out.println("longitude:" + longitude);
		pos += 4;
		
		altitude = DataUtils.readInt(data, pos);
		altitude = (int) ((double) altitude / 100);
		//System.out.println("altitude:" + altitude);
		pos += 4;
		
		short speed = DataUtils.readShort(data, pos);
		//System.out.println("speed:" + speed);
		pos += 2;
		
		short direction = DataUtils.readShort(data, pos);
		//System.out.println("direction:" + direction);
		pos += 2;
		
		int ehpe = DataUtils.readInt(data, pos);
		//System.out.println("ehpe:" + ehpe);
		pos += 4;
		
		short satCount = DataUtils.readByte(data, pos++);
		//System.out.println("satCount:" + satCount);

		short hdop = DataUtils.readByte(data, pos++);
		//System.out.println("hdop:" + hdop);
		
		for(int i=0; i < size-40; i+=6) {
			//System.out.println("Sat");
			short sv = DataUtils.readByte(data, pos++);
			//System.out.println("sv:" + sv);
			
			short state = DataUtils.readByte(data, pos++);
			//System.out.println("state" + state);
			
			pos++;
			
			short SNR = DataUtils.readByte(data, pos++);
			//System.out.println("SNR" + SNR);
		}
		
		return posIn + size - 1;
	}
	
	public short getYear() {
		return year;
	}


	public short getMonth() {
		return month;
	}


	public short getDay() {
		return day;
	}


	public short getHour() {
		return hour;
	}


	public short getMinute() {
		return minute;
	}


	public int getSec() {
		return sec;
	}


	public int getLatitude() {
		return latitude;
	}


	public int getLongitude() {
		return longitude;
	}


	public int getAltitude() {
		return altitude;
	}


	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}

}
