package org.ambit.data;

import java.util.List;

import org.ambit.data.sample.Sample;
import org.ambit.data.sample.SampleFactory;
import org.ambit.util.DataUtils;


public class LogSample {
	
	private static final int MAX_OFFSET = 1032;
	
	private SampleReadInfo nextSampleReadInfo;

	private int endPos;
	private int maxSample;
	
	
	public LogSample(List<Byte> data, int offset, int maxSample, SampleReadInfo currentSampleReadInfo) {
		this.maxSample = maxSample;
		fromData(data, offset, currentSampleReadInfo);
	}
	
	private void fromData(List<Byte> data, int offset, SampleReadInfo currentSampleReadInfo) {
		
		// remove unnecessary start
		for ( int unnec = 0; unnec < offset; unnec++) {
			data.remove(0);
		}
		
		if (currentSampleReadInfo.remainingData.size() > 0) {
			data.addAll(0, currentSampleReadInfo.remainingData);
		}
			
		endPos = (MAX_OFFSET - offset)  + currentSampleReadInfo.remainingData.size();
		
		// Init next sample info with current
		nextSampleReadInfo = currentSampleReadInfo;
		nextSampleReadInfo.partNumber++;
	
		//int pos = offset;
		int pos = 0;
		
		while (pos != -1) {
	       pos = readSample(pos, data);
		}
	}
	
	private int readSample(int pos, List<Byte> data) {
		
		if ( pos >= (endPos - 1) ) {
			//remainingBytes.clear();
			nextSampleReadInfo.remainingData.clear();
			
	       	for ( int j = pos; j < endPos; j++) {
        		//remainingBytes.add(data.get(j));
	       		nextSampleReadInfo.remainingData.add(data.get(j));
        	}

			return -1;
		}
		
	    short sampleLength = DataUtils.readShort(data, pos);
        //System.out.println("sampleLength:" + sampleLength);
        pos+=2;
        
        if ( pos + sampleLength > endPos ) {
        	nextSampleReadInfo.remainingData.clear();
        	
        	for ( int j = pos-2; j < endPos; j++) {
        		nextSampleReadInfo.remainingData.add(data.get(j));
        	}
        	// Construct remaining
        	return -1;
        }
 
        short sampleType = DataUtils.readByte(data, pos++);
        //System.out.println("sampleType:" + sampleType);
        
        
        try {
        	Sample sample = SampleFactory.fromTypeNew((byte) sampleType, data, pos, sampleLength);
        	nextSampleReadInfo.samples.addSample(sample);
        	
        	int posIn = pos - 3;
			pos = sample.decode(data, pos);
			
			if (sample.getSampleNumber() != -1 ) {
				nextSampleReadInfo.setLastSampleRead(sample.getSampleNumber());
			}
			/*
	      	System.out.println("*************************************************************");
        	System.out.println("***** pos " + posIn + "*****");
        	System.out.println("********** Sample #" + sample.getSampleNumber()+ " " + sample.getName());
        	System.out.println("*************************************************************");*/
        	
 	       if ( sample.getSampleNumber() == maxSample) {
	    	   // stop 
	    	   return -1;
	       }
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return pos;
	}

	public SampleReadInfo getNextSampleReadInfo() {
		return nextSampleReadInfo;
	}

	public void setNextSampleReadInfo(SampleReadInfo nextSampleReadInfo) {
		this.nextSampleReadInfo = nextSampleReadInfo;
	}

}
