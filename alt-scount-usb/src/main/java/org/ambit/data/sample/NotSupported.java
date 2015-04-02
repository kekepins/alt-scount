package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class NotSupported extends Activity {

	public NotSupported(int size, int sampleNumber) {
		super(size, sampleNumber);
	}

	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);
		//System.out.println("logType:" + logType);
		
		name = "Not Supported " + logType;
		
		return posIn + size - 1;
	}

	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}


}
