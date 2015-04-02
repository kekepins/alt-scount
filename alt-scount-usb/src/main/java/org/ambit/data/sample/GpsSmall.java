package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class GpsSmall extends Activity {
	

	private short latitudeOffsetFromGpsBase;
	private short longitudeOffsetFromGpsBase;
	private int diffTime;

	public GpsSmall(int size, int sampleNumber) {
		super(size, sampleNumber);
	}
	
	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		//System.out.println("logType:" + logType);
		
		name = "Gps Small";
		int time10 = DataUtils.readInt(data, pos);
		//System.out.println("time6:" + time10);
		pos += 4;
		
		pos++;
		
		latitudeOffsetFromGpsBase = DataUtils.readShort(data, pos);
		//System.out.println("latitudeOffsetFromGpsBase:" + latitudeOffsetFromGpsBase);
		pos += 2;

		longitudeOffsetFromGpsBase = DataUtils.readShort(data, pos);
		//System.out.println("longitudeOffsetFromGpsBase:" + longitudeOffsetFromGpsBase);
		pos += 2;

		diffTime = DataUtils.readShort(data, pos);
		if ( diffTime < 0 ) {
			diffTime += 65536;
		}
		diffTime = diffTime/1000;
		//System.out.println("timeOnlySinMs:" + timeOnlySinMs);
		pos += 2;
		
		byte ehpe2 = (byte) DataUtils.readByte(data, pos++);
		//System.out.println("ehpe2:" + ehpe2);

		byte nbSat = (byte) DataUtils.readByte(data, pos++);
		//System.out.println("nbSat:" + nbSat);
		
		return posIn + size - 1;
	}

	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}
	
	public short getLatitudeOffsetFromGpsBase() {
		return latitudeOffsetFromGpsBase;
	}

	public short getLongitudeOffsetFromGpsBase() {
		return longitudeOffsetFromGpsBase;
	}

	public int getDiffTime() {
		return diffTime;
	}


}
