package org.ambit.data.sample;

import java.util.ArrayList;
import java.util.List;

import org.ambit.export.ExportVisitor;

public class LogSamples {
	private List<Sample> samples = new ArrayList<Sample>();
	
	public void addSample(Sample sample) {
		samples.add(sample);
	}
	
	public void visit(ExportVisitor visitor) {
		for ( Sample sample : samples ) {
			sample.accept(visitor);
		}
	}
}
