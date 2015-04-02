package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class CadenceSrc extends Activity {
	
	private short type;

	public CadenceSrc(int size, int sampleNumber) {
		super(size, sampleNumber);
	}
	
	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		//System.out.println("logType:" + logType);
		
		name = "Cadence Src";
		//System.out.println("Lat long");
		int time7 = DataUtils.readInt(data, pos);
		//System.out.println("time6:" + time7);
		pos += 4;
		
		pos++;
		
		type = DataUtils.readByte(data, pos++);
		//System.out.println("type:" + type);  // 0x40 wrist
		
		return posIn + size - 1;

	}

	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}

}
