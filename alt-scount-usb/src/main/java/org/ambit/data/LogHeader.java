package org.ambit.data;


import java.io.UnsupportedEncodingException;
import java.util.List;

import org.ambit.util.DataUtils;
import org.ambit.util.DisplayUtils;


public class LogHeader {
	
	private short year; 
	private short month;
	private short day;
	private short hour;
	private short minute;
	private short ms;
	private int duration;
	private short ascent;
	private short descent;
	private int ascentTime;
	private int descentTime;
	private short recoveryTime;
	private int speedAvg;
	private int speedMax;
	private short altitudeMax;
	private short altitudeMin;
	private short heartRateAvg;
	private short heartRateMax;
	private short peakTrainingEffect;
	private short activityType;
	private String title;
	private int distance;

	
	public LogHeader( List<Byte> data ) {
		fromData(data);
	}
	
	private void fromData(List<Byte> data) {
		int pos = 9;
		
		year = DataUtils.readShort(data, pos);
		pos+=2;
		month = DataUtils.readByte(data, pos++);
		day = DataUtils.readByte(data, pos++);
		hour = DataUtils.readByte(data, pos++);
		minute = DataUtils.readByte(data, pos++);
		ms = DataUtils.readByte(data, pos++);
		
		//System.out.printf("y %d m %d d %d\r\n", year, month, day);
		//System.out.printf("h %d m %d ms %d\r\n", hour, minute, ms);
		
		pos += 5;
		
		duration = DataUtils.readInt(data, pos) * 100 ; // ms
		//System.out.println("Duration: " + duration);
		//System.out.println("Duration" + DisplayUtils.getDurationStr(duration));
		
		pos+=4;
		ascent = DataUtils.readShort(data, pos); // OK
		//System.out.println("Ascent: " + ascent);
		pos+=2;
		descent = DataUtils.readShort(data, pos);  //OK
		//System.out.println("descent: " + descent);
		pos+=2;
		ascentTime = DataUtils.readInt(data, pos);
		//System.out.println("ascentTime: " + ascentTime); // OK
		//System.out.println("ascentTime" + DisplayUtils.getDurationStr(ascentTime*1000));
		pos+=4;
		descentTime = DataUtils.readInt(data, pos);
		//System.out.println("descentTime: " + descentTime); //OK
		//System.out.println("descentTime" + DisplayUtils.getDurationStr(descentTime*1000));
		pos+=4;
		recoveryTime = DataUtils.readShort(data, pos);
		//System.out.println("recoveryTime: " + recoveryTime);
		pos+=2;
		speedAvg = DataUtils.readShort(data, pos) * 10;  // m/s
		//System.out.println("speedAvg: " + speedAvg);
		pos+=4;
		speedMax = DataUtils.readShort(data, pos) * 10 ; // m/s  => bof
		//System.out.println("speedMax: " + speedMax);
		pos+=2;
		altitudeMax = DataUtils.readShort(data, pos);  // ok
		//System.out.println("altitudeMax: " + altitudeMax);
		pos+=2;
		altitudeMin = DataUtils.readShort(data, pos);  // ok
		//System.out.println("altitudeMin: " + altitudeMin);
		pos+=2;
		heartRateAvg = DataUtils.readByte(data, pos++);
		//System.out.println("heartRateAvg: " + heartRateAvg);
		heartRateMax = DataUtils.readByte(data, pos++);
		//System.out.println("heartRateMax: " + heartRateMax);
		peakTrainingEffect = DataUtils.readByte(data, pos++);
		//System.out.println("peakTrainingEffect: " + peakTrainingEffect);
		activityType = DataUtils.readByte(data, pos++);
		//System.out.println("activityType: " + activityType); // 82 trail ok 22 ski de fond
		//System.out.println("----");
		//for ( int i = 0; i< 16; i++) {
		//	System.out.printf(" %02x", data.get(pos + i));
		//}
		//System.out.println("\r\n----");
		try {
			title = DataUtils.readString(data, pos, 16, "ISO-8859-15");
			//System.out.println("Title: " + title);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pos+=16;
		
		pos+=6;
		distance = DataUtils.readInt(data, pos);
		//System.out.println("distance: " + distance + "m");
		
	}
	
	public void dump() {
		/*System.out.println("Activity: " + title);
		System.out.println("Sport: " + activityType  +" (82 trail, 22 ski de fond)");
		System.out.println("Duration: " + DisplayUtils.getDurationStr(duration));
		System.out.println("Distance: " +  distance + " m");
		System.out.println("D+: " +  ascent + " m");*/
	}
}
