package org.ambit.command;

import java.util.ArrayList;
import java.util.List;

import org.ambit.data.LogInfo;
import org.ambit.usb.Device;
import org.ambit.usb.UsbException;
//import org.hid4java.HidDevice;

/**
 * Read all samples
 */
public class SamplesReader {
	
	private final static int LOG_START = 0x000f4240; // 1 000 000
	
	private final static short CHUNK_SIZE = 0x0400; // not the same for all models 
	                                                // 1024
	

	/**
	 * Read all log info in device
	 * @throws Exception 
	 */
	public List<LogInfo> readLogInfos(Device ambitDevice) throws UsbException {

		List<LogInfo> logs = new ArrayList<LogInfo>();
		LogCountCommandExecutor countLogCommand = new LogCountCommandExecutor();
		short logCount = countLogCommand.executeCommand(ambitDevice);
		
		LogDescCommandExecutor logPartCommand = new LogDescCommandExecutor();
		int logInfoPart = 0;
		
		for ( int i = 0; i < logCount; i++) {
			logPartCommand.setPartNumber(logInfoPart);
			LogInfo logInfo = logPartCommand.executeCommand(ambitDevice);
			logInfo.setStartInfoPart(logInfoPart);
			int sampleCount = logInfo.getSampleCount();
			logInfoPart = logInfo.getNextInfoPart();
			System.out.println(i + " Sample count " + sampleCount + " next part " + logInfoPart);
			System.out.println( logInfo.toString());
			logs.add(logInfo);
		}
		
		if ( logCount >  1) {
			logs.get(logCount-1).setStartInfoPart(logInfoPart);
		}
			
		return logs;
		
		
		
	}
	
	public void readAllSamples(Device ambitDevice) throws UsbException {
		/*
		// Read header
		LogInfoCommandExecutor logPartCommand = new LogInfoCommandExecutor();
		LogInfo logInfo = logPartCommand.executeCommand(ambitDevice);
		int sampleCount = logInfo.getSampleCount();
		
		int samplePart = 1;
		int lastSampleId = 0;
		
		int nextLogInfoPart = getNextInfoPart(logInfo.getNextEntryAdress());
		
		logPartCommand.setPartNumber(nextLogInfoPart);
		logInfo = logPartCommand.executeCommand(ambitDevice);
		sampleCount = logInfo.getSampleCount();
		
		nextLogInfoPart = getNextInfoPart(logInfo.getNextEntryAdress());
		
		logPartCommand.setPartNumber(nextLogInfoPart);
		logInfo = logPartCommand.executeCommand(ambitDevice);
		sampleCount = logInfo.getSampleCount();
		
		int k = 1;

		*/
		
		/*LogSampleCommandExecutor logSample = new LogSampleCommandExecutor();
		logSample.setMaxSample(sampleCount);*/

		/*
		while ( lastSampleId < sampleCount ) {
		
			if (samplePart == 1 ) {
				logSample.setFirst(true);
			}
			else {
				logSample.setFirst(false);
			}
			logSample.setPartNumber(samplePart);
			LogSample sample = logSample.executeCommand(ambitDevice);
			lastSampleId = sample.getLastSampleRead();

			samplePart ++;
		}
		*/
		/*
		logPartCommand = new LogInfoCommandExecutor();
		logPartCommand.setPartNumber(54);
		logInfo = logPartCommand.executeCommand(ambitDevice);
		sampleCount = logInfo.getSampleCount();
		logSample.setMaxSample(sampleCount);
		
		
		samplePart = 1;
		lastSampleId = 0;
		
		while ( lastSampleId < sampleCount ) {
			
			if (samplePart == 1 ) {
				logSample.setFirst(true);
			}
			else {
				logSample.setFirst(false);
			}
			logSample.setPartNumber(samplePart);
			LogSample sample = logSample.executeCommand(ambitDevice);
			lastSampleId = sample.getLastSampleRead();

			samplePart ++;
		}*/
		

	
		
		
		
		// 55
		//logPartCommand = new LogPartCommandExecutor();
		//logPartCommand.setAdress(1055566);
		//logPartCommand.setPartNumber(samplePart);
		
		/*logSample.setPartNumber(55);
		logSample.setFirst(true);
		LogSample sample = logSample.executeCommand(ambitDevice);*/
		/*
		logSample.setPartNumber(54);
		logSample.getSendData();
		
		
		logPartCommand = new LogPartCommandExecutor();
		logPartCommand.setAdress(1055296);//ok
		//logPartCommand.setAdress(1055566);//ok		
		logInfo = logPartCommand.executeCommand(ambitDevice);
		sampleCount = logInfo.getSampleCount();
		*/

		//1055296
		
		/*byte[] data = logSample.getSendData();
		
		logSample.setPartNumber(samplePart);
		logSample.getSendData();*/
		
		
		/*
		1055296 0x
		0x40
		0x1a
		0x10
		0x00
		
		0x00
		0x04
		0x00
		0x00
		
		nextFreeAdress:2250199
		 504d454d 4e1b1000 => 1055566
		 */

	}
	
	private int getNextInfoPart(int nextEntryAdress) {
		return (nextEntryAdress - LOG_START) / CHUNK_SIZE;
		//return 0;
	}
}
