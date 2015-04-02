package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;

public abstract class Sample {

	
	//Sample real size =  size + 2
	protected int size;

	protected int sampleNumber;
	
	public int getSize() {
		return size;
	}
	
	public Sample(int size, int sampleNumber) {
		this.size = size;
		this.sampleNumber = sampleNumber;
	}
	
	public abstract int decode(List<Byte> data, int pos) throws Exception;
	
	public abstract String getName();
	
	public int getSampleNumber() {
		return sampleNumber;
	}
	
    public abstract void accept(ExportVisitor visitor);

}
