package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class FinalActivity extends Activity {
	
	private short activityType;
	private int logActivityCustomModeId;

	public FinalActivity(int size, int sampleNumber) {
		super(size, sampleNumber);
	}
	
	@Override
	public int decode(List<Byte> data, int position) throws Exception {
		int posIn = position;
		int pos = position;

		short logType = DataUtils.readByte(data, pos + 4);

		name = "activity";
		//System.out.println("Sample (Activity)");
		int time = DataUtils.readInt(data, pos);
		//System.out.println("time:" + time);
		pos += 4;

		pos++;

		activityType = DataUtils.readShort(data, pos);
		//System.out.println("activityType:" + activityType);
		pos += 2;

		logActivityCustomModeId = DataUtils.readInt(data, pos);
		//System.out.println("logActivityCustomModeId:"	+ logActivityCustomModeId);
		
		pos += 4;
		
		return posIn + size - 1;
	}

	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}


}
