package org.ambit.data;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.ambit.util.DataUtils;
import org.ambit.util.DisplayUtils;

public class AmbitInfo {
	
	private String model;
	private String serial;
	private String version;
	
	public String getModel() {
		return model;
	}

	public String getSerial() {
		return serial;
	}

	public AmbitInfo(List<Byte> data) {
		populateFromData(data);
	}
	
	private void populateFromData(List<Byte> data) {
		int pos = 0;
		//DisplayUtils.displayBytes(data);
		try {
			model = DataUtils.readString(data, pos, 16, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pos += 16;
		try {
			serial = DataUtils.readString(data, pos, 16, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pos += 16;
		
		short versionHigh = DataUtils.readByte(data, pos++ );
		short versionMid = DataUtils.readByte(data, pos++ );
		short versionSmall = DataUtils.readShort(data, pos );
		pos+=2;
		
		version = String.format("%d.%d.%d", versionHigh, versionMid, versionSmall);
	}

	public String getVersion() {
		return version;
	}

}
