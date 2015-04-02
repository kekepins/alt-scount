package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class DistanceSrc extends Activity {
	
	private short source;

	public DistanceSrc(int size, int sampleNumber) {
		super(size, sampleNumber);
	}
	
	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		//System.out.println("logType:" + logType);
		
		name = "Distance Src";
		//System.out.println("Lat long");
		int time8  = DataUtils.readInt(data, pos);
		//System.out.println("time6:" + time8);
		pos += 4;
		
		pos++;
		
		source = DataUtils.readByte(data, pos++);
		//System.out.println("source:" + source);  //0x2 gps
		
		return posIn + size - 1;

	}

	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}

}
