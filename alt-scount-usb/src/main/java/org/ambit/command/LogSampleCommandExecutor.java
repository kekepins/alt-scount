package org.ambit.command;
import java.nio.ByteOrder;
import java.util.List;

import org.ambit.data.AmbitModel;
import org.ambit.data.LogSample;
import org.ambit.data.SampleReadInfo;
import org.ambit.data.sample.SampleFactory;
import org.ambit.util.DataUtils;


/**
 * Getting a log part 
 */
public class LogSampleCommandExecutor extends AmbitCommandExecutor<LogSample> {
	private final static int LOG_START = 0x000f4240;
	
	private SampleReadInfo sampleReadInfo;
	
	public void setMaxSample(int maxSample) {
		this.maxSample = maxSample;
	}

	private int maxSample;
	
	
	public int getMaxSample() {
		return maxSample;
	}
	
	//private final static short CHUNK_SIZE = 0x0400; // not the same for all models // 1024
	
	@Override
	protected AmbitCommand getCommand() {
		return AmbitCommand.LOG_READ;
	}
	
	
	
	protected byte[] getSendData(AmbitModel ambitModel) {
		int adress = LOG_START + (sampleReadInfo.partNumber * ambitModel.getChunkSize());
		byte[] data2 = DataUtils.intToBytes(1024, ByteOrder.LITTLE_ENDIAN);
		byte[] data1 = DataUtils.intToBytes(adress, ByteOrder.LITTLE_ENDIAN);

		// start + chunksize ambit2
		byte[] sendData = new byte[8];
		sendData[0] = data1[0];
		sendData[1] = data1[1];
		sendData[2] = data1[2];
		sendData[3] = data1[3];
		sendData[4] = data2[0];
		sendData[5] = data2[1];
		sendData[6] = data2[2];
		sendData[7] = data2[3];
		
		return sendData;
	}


	@Override
	protected LogSample extractResult(List<Byte> byteBuffer) {
		
		// FIXME crad
		if ( sampleReadInfo.getLastSampleRead() == 0 ) {
			SampleFactory.initSampleNumber();
		}
		LogSample logSample = new LogSample(byteBuffer, 8, maxSample, sampleReadInfo );
		return logSample;
	}


	/*

	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}


	public List<Byte> getRemainingBytes() {
		return remainingBytes;
	}



	public void setRemainingBytes(List<Byte> remainingBytes) {
		this.remainingBytes = remainingBytes;
	}
	*/



	public void setSampleReadInfo(SampleReadInfo sampleReadInfo) {
		this.sampleReadInfo = sampleReadInfo;
	}


}
