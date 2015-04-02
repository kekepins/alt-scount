package org.ambit.data.sample;


public abstract class Activity extends Sample {

	/*
	private static final short ACTIVITY 		= 0x18;
	private static final short FIRMWARE_INFO 	= 0x1c;
	private static final short TIME_EVENT 		= 0x09;
	private static final short TIME 			= 0x12;
	private static final short GPS_BASE			= 0x0f;
	private static final short LAT_LON			= 0x1b;
	private static final short CADENCE_SRC		= 0x1a;
	private static final short DISTANCE_SRC		= 0x08;
	private static final short GPS_TINY			= 0x11;
	private static final short GPS_SMALL		= 0x10;
	private static final short PAUSE			= 0x04;*/

	
	protected String name;
	
	public Activity(int size, int sampleNumber) {
		super(size, sampleNumber);
	}

	/*
	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		//System.out.println("logType:" + logType);

		switch (logType) {

		// Activity
		case ACTIVITY:
			name = "activity";
			//System.out.println("Sample (Activity)");
			int time = DataUtils.readInt(data, pos);
			//System.out.println("time:" + time);
			pos += 4;

			pos++;

			short activityType = DataUtils.readShort(data, pos);
			//System.out.println("activityType:" + activityType);
			pos += 2;

			int logActivityCustomModeId = DataUtils.readInt(data, pos);
			//System.out.println("logActivityCustomModeId:"
			//		+ logActivityCustomModeId);
			
			pos += 4;
			
			break;

		case FIRMWARE_INFO:
			name = "firmware info";
			System.out.println("Firmware info");
			int time2 = DataUtils.readInt(data, pos);
			System.out.println("time2:" + time2);
			pos += 4;

			pos++;
			short fw1 = DataUtils.readByte(data, pos++);
			short fw2 = DataUtils.readByte(data, pos++);
			short fw3 = DataUtils.readShort(data, pos);
			pos += 2;
			System.out.println("Version: " + fw1 + ":" + fw2 + ":" + fw3);
			short year = DataUtils.readShort(data, pos); // 2015 => 7df => pos
															// 67
			pos += 2;
			short month = DataUtils.readByte(data, pos++);
			short day = DataUtils.readByte(data, pos++);
			short hour = DataUtils.readByte(data, pos++);
			short minute = DataUtils.readByte(data, pos++);
			short sec = DataUtils.readShort(data, pos);
			System.out.println(String.format("Date %d/%d/%d %d:%d:%d", year,
					month, day, hour, minute, sec));
			pos += 2;
			break;
			
		case TIME_EVENT:
			name = "time event";
			System.out.println("Time Event");
			int time3 = DataUtils.readInt(data, pos);
			System.out.println("time3:" + time3);
			pos += 4;

			pos++;
			
			pos++;
			
			short year2 = DataUtils.readShort(data, pos); // 2015 => 7df => pos
			// 67
			pos += 2;
			short month2 = DataUtils.readByte(data, pos++);
			short day2 = DataUtils.readByte(data, pos++);
			short hour2 = DataUtils.readByte(data, pos++);
			short minute2 = DataUtils.readByte(data, pos++);
			short sec2 = DataUtils.readShort(data, pos);
			System.out.println(String.format("Date %d/%d/%d %d:%d:%d", year2,
					month2, day2, hour2, minute2, sec2));
			pos += 2;

			int duration = DataUtils.readInt(data, pos);
			System.out.println("duration:" + duration);
			pos += 4;
			
			int distance = DataUtils.readInt(data, pos);
			System.out.println("distance:" + distance);
			pos += 4;

			break;
			
		case TIME :
			name = "time";
			System.out.println("Time");
			int time4 = DataUtils.readInt(data, pos);
			System.out.println("time3:" + time4);
			pos += 4;
			
			pos++;
			
			short hour3 = DataUtils.readByte(data, pos++);
			short minute3 = DataUtils.readByte(data, pos++);
			short sec3 = DataUtils.readByte(data, pos++);
			System.out.println(String.format("Date %d:%d:%d",  hour3, minute3, sec3));

			break;
			
		case GPS_BASE:
			name = "gps base";
			System.out.println("Gps base");
			int time5 = DataUtils.readInt(data, pos);
			System.out.println("time5:" + time5);
			pos += 4;
			
			pos++;
			
			pos += 2;
			
			short navType = DataUtils.readShort(data, pos);
			System.out.println("navType:" + navType);
			pos += 2;
			
			short year4 = DataUtils.readShort(data, pos); // 2015 => 7df => pos
			// 67
			pos += 2;
			short month4 = DataUtils.readByte(data, pos++);
			short day4 = DataUtils.readByte(data, pos++);
			short hour4 = DataUtils.readByte(data, pos++);
			short minute4 = DataUtils.readByte(data, pos++);
			short sec4 = DataUtils.readShort(data, pos);
			System.out.println(String.format("Date %d/%d/%d %d:%d:%d", year4,
					month4, day4, hour4, minute4, sec4));
			pos += 2;
			
			int latitude = DataUtils.readInt(data, pos);
			System.out.println("latitude:" + latitude);
			pos += 4;
	
			int longitude = DataUtils.readInt(data, pos);
			System.out.println("longitude:" + longitude);
			pos += 4;
			
			int altitude = DataUtils.readInt(data, pos);
			System.out.println("altitude:" + altitude);
			pos += 4;
			
			short speed = DataUtils.readShort(data, pos);
			System.out.println("speed:" + speed);
			pos += 2;
			
			short direction = DataUtils.readShort(data, pos);
			System.out.println("direction:" + direction);
			pos += 2;
			
			int ehpe = DataUtils.readInt(data, pos);
			System.out.println("ehpe:" + ehpe);
			pos += 4;
			
			short satCount = DataUtils.readByte(data, pos++);
			System.out.println("satCount:" + satCount);

			short hdop = DataUtils.readByte(data, pos++);
			System.out.println("hdop:" + hdop);
			
			for(int i=0; i < size-40; i+=6) {
				System.out.println("Sat");
				short sv = DataUtils.readByte(data, pos++);
				System.out.println("sv:" + sv);
				
				short state = DataUtils.readByte(data, pos++);
				System.out.println("state" + state);
				
				pos++;
				
				short SNR = DataUtils.readByte(data, pos++);
				System.out.println("SNR" + SNR);

			}

			
			break;
			
		case LAT_LON:
			name = "Lat Lon";
			System.out.println("Lat long");
			int time6 = DataUtils.readInt(data, pos);
			System.out.println("time6:" + time6);
			pos += 4;
			
			pos++;
			
			int latitude2 = DataUtils.readInt(data, pos);
			System.out.println("latitude2:" + latitude2);
			pos += 4;
	
			int longitude2 = DataUtils.readInt(data, pos);
			System.out.println("longitude2:" + longitude2);
			pos += 4;


			break;
			
		case CADENCE_SRC:
			name = "Cadence Src";
			System.out.println("Lat long");
			int time7 = DataUtils.readInt(data, pos);
			System.out.println("time6:" + time7);
			pos += 4;
			
			pos++;
			
			short type = DataUtils.readByte(data, pos++);
			System.out.println("type:" + type);  // 0x40 wrist


			break;
		case DISTANCE_SRC :
			name = "Distance Src";
			System.out.println("Lat long");
			int time8  = DataUtils.readInt(data, pos);
			System.out.println("time6:" + time8);
			pos += 4;
			
			pos++;
			
			short source = DataUtils.readByte(data, pos++);
			System.out.println("source:" + source);  //0x2 gps
			
			break;
		case GPS_TINY :
			name = "Gps Tiny";
			System.out.println("Lat long");
			int time9 = DataUtils.readInt(data, pos);
			System.out.println("time6:" + time9);
			pos += 4;
			
			pos++;
			
			byte difflat = (byte) DataUtils.readByte(data, pos++);
			System.out.println("difflat:" + difflat);  //0x2 gps
			byte difflong = (byte) DataUtils.readByte(data, pos++);
			System.out.println("difflong:" + difflong);  //0x2 gps
			short difftime_s = DataUtils.readByte(data, pos++);
			System.out.println("difftime_s:" + difftime_s);  //0x2 gps


			break;		
		case GPS_SMALL:
			name = "Gps Small";
			System.out.println("Lat long");
			int time10 = DataUtils.readInt(data, pos);
			System.out.println("time6:" + time10);
			pos += 4;
			
			pos++;
			
			short latitudeOffsetFromGpsBase = DataUtils.readShort(data, pos);
			System.out.println("latitudeOffsetFromGpsBase:" + latitudeOffsetFromGpsBase);
			pos += 2;

			short longitudeOffsetFromGpsBase = DataUtils.readShort(data, pos);
			System.out.println("longitudeOffsetFromGpsBase:" + longitudeOffsetFromGpsBase);
			pos += 2;

			short timeOnlySinMs = DataUtils.readShort(data, pos);
			System.out.println("timeOnlySinMs:" + timeOnlySinMs);
			pos += 2;
			
			byte ehpe2 = (byte) DataUtils.readByte(data, pos++);
			System.out.println("ehpe2:" + ehpe2);

			byte nbSat = (byte) DataUtils.readByte(data, pos++);
			System.out.println("nbSat:" + nbSat);

			break;
		case PAUSE:
			name = "Gps Small";
			System.out.println("Lat long");
			int time11 = DataUtils.readInt(data, pos);
			System.out.println("time11:" + time11);
			pos += 4;
			
			pos++;
				
			break;
		default:
			throw new Exception("Activity type not supported " + logType);

		}

		return posIn + size - 1;
		//return pos;

	}*/

	@Override
	public String getName() {
		return name;
	}

}
