package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class GpsTiny extends Activity {
	
	private byte diffLat;

	private byte diffLong;
	private int diffTime;

	public GpsTiny(int size, int sampleNumber) {
		super(size, sampleNumber);
	}
	
	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		
		name = "Gps Tiny";
		int time9 = DataUtils.readInt(data, pos);
		pos += 4;
		
		pos++;
		
		diffLat = (byte) DataUtils.readByte(data, pos++);
		diffLong = (byte) DataUtils.readByte(data, pos++);
		diffTime = ((byte) DataUtils.readByte(data, pos++)) & 0x3f;
		
		return posIn + size - 1;

	}


	public byte getDiffLat() {
		return diffLat;
	}

	public byte getDiffLong() {
		return diffLong;
	}

	public int getDiffTime() {
		return diffTime;
	}

	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}

}
