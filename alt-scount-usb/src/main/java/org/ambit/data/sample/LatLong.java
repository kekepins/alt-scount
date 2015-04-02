package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class LatLong extends Activity {
	
	private int latitude;
	private int longitude;

	public LatLong(int size, int sampleNumber) {
		super(size, sampleNumber);
	}
	
	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		//System.out.println("logType:" + logType);

		name = "Lat Lon";
		//System.out.println("Lat long");
		int time6 = DataUtils.readInt(data, pos);
		//System.out.println("time6:" + time6);
		pos += 4;
		
		pos++;
		
		latitude = DataUtils.readInt(data, pos);
		//System.out.println("latitude2:" + latitude2);
		pos += 4;

		longitude = DataUtils.readInt(data, pos);
		//System.out.println("longitude2:" + longitude2);
		pos += 4;
		
		return posIn + size - 1;
	}


	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}

}
