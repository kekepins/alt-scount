package org.ambit.data;

import java.util.List;

import org.ambit.data.sample.LogSamples;

public class SampleReadInfo {
	public int partNumber;
	public List<Byte> remainingData;
	private int lastSampleRead;
	public LogSamples samples;
	
	public int getLastSampleRead() {
		return lastSampleRead;
	}
	public void setLastSampleRead(int lastSampleRead) {
		this.lastSampleRead = lastSampleRead;
	}
}
