package org.ambit.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.ambit.data.sample.CadenceSrc;
import org.ambit.data.sample.DistanceSrc;
import org.ambit.data.sample.FinalActivity;
import org.ambit.data.sample.FirmwareInfo;
import org.ambit.data.sample.GpsBase;
import org.ambit.data.sample.GpsSmall;
import org.ambit.data.sample.GpsTiny;
import org.ambit.data.sample.LatLong;
import org.ambit.data.sample.NotSupported;
import org.ambit.data.sample.Pause;
import org.ambit.data.sample.Periodic;
import org.ambit.data.sample.PeriodicDefinition;
import org.ambit.data.sample.Time;
import org.ambit.data.sample.TimeEvent;


public class GpxExportVisitor implements ExportVisitor {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.000Z'");
	
	private int lastGpsBaseLatitude;
	private int lastGpsBaseLongitude;
	
	private int lastGpsLatitude;
	private int lastGpsLongitude;

	
	private int altitude;
	private short year;
	private short month;
	private short day;
	private short hour;
	private short minute;
	private int sec;
	
	private File exportFile;
	
	private BufferedWriter writerGpx;
	
	public GpxExportVisitor(File exportDir, String fileName ) {
		this.exportFile = new File(exportDir, fileName);
	}
	
	public void init() throws IOException {
		
		Path gpxFile = Paths.get( exportFile.getAbsolutePath()  );
		
	
		writerGpx = Files.newBufferedWriter(gpxFile,  StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW );
		
		//StringBuffer start = new StringBuffer();
		writerGpx.write("<gpx xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " );
		writerGpx.newLine();
		writerGpx.write("  xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 " );
		writerGpx.newLine();
		writerGpx.write("  http://www.topografix.com/GPX/1/1/gpx.xsd ");
		writerGpx.newLine();
		writerGpx.write("  http://www.cluetrust.com/XML/GPXDATA/1/0 ");
		writerGpx.newLine();
		writerGpx.write("  http://www.cluetrust.com/Schemas/gpxdata10.xsd ");
		writerGpx.newLine();
		writerGpx.write("  http://www.garmin.com/xmlschemas/TrackPointExtension/v1 ");
		writerGpx.newLine();
		writerGpx.write("  http://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd\" ");
		writerGpx.newLine();
		writerGpx.write("  xmlns:gpxdata=\"http://www.topografix.com/GPX/1/0\" ");
		writerGpx.newLine();
		writerGpx.write("  xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\" ");
		writerGpx.newLine();
		writerGpx.write("  version=\"1.1\" ");
		writerGpx.newLine();
		writerGpx.write("  creator=\"altscount the keke production\" ");
		writerGpx.newLine();
		writerGpx.write("  xmlns=\"http://www.topografix.com/GPX/1/1\">");
		writerGpx.newLine();
		
		
		writerGpx.write("  <trk>");
		writerGpx.newLine();
		writerGpx.write("    <name>Move</name>");
		writerGpx.newLine();
		writerGpx.write("    <trkseg>");
		writerGpx.newLine();


		writerGpx.flush();
		//System.out.println(start);
	}
	
	public void finish() {
		try {
			writerGpx.write("    </trkseg>");
			writerGpx.newLine();
			writerGpx.write("  </trk>");
			writerGpx.newLine();
			writerGpx.write("</gpx>");
			writerGpx.newLine();
			
			writerGpx.flush();
			writerGpx.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void visit(CadenceSrc cadenceSrc) {
	}

	@Override
	public void visit(DistanceSrc distanceSrc) {
	}

	@Override
	public void visit(FinalActivity finalActivity) {
	}

	@Override
	public void visit(FirmwareInfo firmwareInfo) {
	}

	@Override
	public void visit(GpsBase gpsBase) {
		//System.out.println("GpsBase " + latitudeRef + "-->" );
		this.lastGpsBaseLatitude = gpsBase.getLatitude();
		//System.out.println( latitudeRef );
		this.lastGpsBaseLongitude = gpsBase.getLongitude();
		this.lastGpsLatitude = gpsBase.getLatitude();
		//System.out.println( latitudeRef );
		this.lastGpsLongitude = gpsBase.getLongitude();
	
		
		this.year = gpsBase.getYear();
		this.day = gpsBase.getDay();
		this.month = gpsBase.getMonth();
		this.hour = gpsBase.getHour();
		this.minute = gpsBase.getMinute();
		this.sec = gpsBase.getSec();
		
		//System.out.println("gpsbase");
		formatTrkpt(
				gpsBase.getLatitude(), 
				gpsBase.getLongitude(), 
				gpsBase.getAltitude(), 
				gpsBase.getYear(), 
				gpsBase.getMonth(), 
				gpsBase.getDay(), 
				gpsBase.getHour(), 
				gpsBase.getMinute(), 
				gpsBase.getSec());
	}

	@Override
	public void visit(GpsTiny gpsTiny) {
		//System.out.println("gpstiny " + gpsTiny.getDiffLat() + " " + gpsTiny.getDiffLong() + " " + gpsTiny.getDiffTime());
		lastGpsLatitude = lastGpsLatitude + (gpsTiny.getDiffLat() * 10);
		lastGpsLongitude = lastGpsLongitude +  (gpsTiny.getDiffLong() * 10);

		formatTrkpt(
				lastGpsLatitude , 
				lastGpsLongitude, 
				altitude, 
				year, 
				month, 
				day,
				hour,
				minute,
				gpsTiny.getDiffTime());
	}

	@Override
	public void visit(LatLong latLong) {
	}

	@Override
	public void visit(NotSupported notSupported) {
	}

	@Override
	public void visit(Pause pause) {
	}

	@Override
	public void visit(Time time) {
	}

	@Override
	public void visit(TimeEvent timeEvent) {
	}

	@Override
	public void visit(Periodic periodic) {
		if ( periodic.getAltitude() != 0 ) {
			altitude = periodic.getAltitude();
		}
	}

	@Override
	public void visit(PeriodicDefinition periodicDefinition) {
	}
	
	@Override
	public void visit(GpsSmall gpsSmall) {
		
		lastGpsLatitude = lastGpsBaseLatitude + (gpsSmall.getLatitudeOffsetFromGpsBase() * 10);
		lastGpsLongitude = lastGpsBaseLongitude +  (gpsSmall.getLongitudeOffsetFromGpsBase() * 10);

		formatTrkpt(
				lastGpsLatitude,
				lastGpsLongitude,
				altitude, 
				year, 
				month, 
				day,
				hour,
				minute,
				gpsSmall.getDiffTime());
	}
	
	private void formatTrkpt(
			int latitude, int longitude, int altitude, 
			int year, int month, int day, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		try {
			writerGpx.write("      <trkpt lat=\"" + ((double)latitude / 10000000) + "\" lon=\"" + ((double)longitude  / 10000000) + "\">");
			writerGpx.newLine();
			writerGpx.write("        <ele>" + altitude + "</ele>");
			writerGpx.newLine();
			writerGpx.write("       <time>" + dateFormat.format(c.getTime()) + "</time>");
			writerGpx.newLine();
			writerGpx.write("      </trkpt>");
			writerGpx.newLine();
			writerGpx.flush();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	

}
