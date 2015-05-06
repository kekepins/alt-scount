package org.ambit.command;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.ambit.data.AmbitModel;
import org.ambit.data.LogSample;
import org.ambit.util.DataUtils;
import org.ambit.util.DisplayUtils;

public class DebugSamplesCommand extends AmbitCommandExecutor<Integer> {
	private final static int LOG_START = 0x000f4240;
	private final static int LOG_SIZE =  0x0029f630; /* 2 750 000 */
	
	private final static int OFFSET_FIRST  = 44;
	
	private int partNumber;
	
	private boolean isFirst;
	
	public void setMaxSample(int maxSample) {
		this.maxSample = maxSample;
	}



	//private int sampleRead;
	private int maxSample;
	
	
	
	public int getMaxSample() {
		return maxSample;
	}



	private List<Byte> remainingBytes = new ArrayList<Byte>();
	
	//private final static short CHUNK_SIZE = 0x0400; // not the same for all models // 1024
	// adress = start + (idx * CHUNK_SIZE)
	
	@Override
	protected AmbitCommand getCommand() {
		return AmbitCommand.LOG_READ;
	}
	
	
	
	protected byte[] getSendData(AmbitModel ambitModel) {
		int adress = LOG_START + (partNumber * ambitModel.getChunkSize());
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
	protected Integer extractResult(List<Byte> byteBuffer) {
		//System.out.println("-----------------------------");
		//System.out.println("PART " + partNumber);
		DisplayUtils.displayBytes(byteBuffer);
		//System.out.println("-----------------------------");
		return -1;
	}



	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}

}
