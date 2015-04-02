package org.ambit.data.sample;

import java.util.List;

import org.ambit.util.DataUtils;


public class SampleFactory {
	private final static byte SAMPLE_ACTIVITY = 0x03;
	private final static byte PERIODIC_DESCRIPTION = 0x00;
	private final static byte PERIODIC_SAMPLE = 0x02;
	
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
	private static final short PAUSE			= 0x04;

	
	private static PeriodicDefinition lastDefinition;
	
	private static int next_stample = 1;
	
	// FIXME beurk
	public static void initSampleNumber() {
		next_stample = 1;
	}
	
	/*public static Sample fromType(byte sampleType, int size) throws Exception {
		switch (sampleType ) {
			case SAMPLE_ACTIVITY :
				return new Activity(size, next_stample++);
			case PERIODIC_DESCRIPTION :
				lastDefinition = new  PeriodicDefinition(size);
				return lastDefinition;
			case PERIODIC_SAMPLE :
				return new Periodic(size, next_stample++, lastDefinition);
			default:
				throw new Exception("Type not supported " + sampleType);

		}
	}*/
	
	public static Sample fromTypeNew(byte sampleType, List<Byte> data, int pos, int size)  throws Exception {
		switch (sampleType ) {
			case SAMPLE_ACTIVITY : {
				short logType = DataUtils.readByte(data, pos + 4);
				//System.out.println("logType:" + logType);

				switch (logType) {
					case ACTIVITY:
						return new FinalActivity(size, next_stample++);
					case FIRMWARE_INFO:
						return new FirmwareInfo(size, next_stample++);
					case TIME_EVENT:
						return new TimeEvent(size, next_stample++);
					case TIME :
						return new Time(size, next_stample++);
					case GPS_BASE :
						return new GpsBase(size, next_stample++);
					case LAT_LON :
						return new LatLong(size, next_stample++);
					case CADENCE_SRC :
						return new CadenceSrc(size, next_stample++);
					case DISTANCE_SRC :
						return new DistanceSrc(size, next_stample++);
					case GPS_TINY :
						return new GpsTiny(size, next_stample++);
					case GPS_SMALL :
						return new GpsSmall(size, next_stample++);
					case PAUSE :
						return new Pause(size, next_stample++);
					default :
						return new NotSupported(size, next_stample++);
				}
			}
				//return new Activity(size, next_stample++);
			case PERIODIC_DESCRIPTION :
				lastDefinition = new  PeriodicDefinition(size);
				return lastDefinition;
			case PERIODIC_SAMPLE :
				return new Periodic(size, next_stample++, lastDefinition);
			default:
				throw new Exception("Type not supported " + sampleType);

		}
	}


}
