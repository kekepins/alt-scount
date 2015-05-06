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
			//System.out.println(i + " Sample count " + sampleCount + " next part " + logInfoPart);
			//System.out.println( logInfo.toString());
			logs.add(0, logInfo);
		}
		
		if ( logCount >  1) {
			logs.get(logCount-1).setStartInfoPart(logInfoPart);
		}
			
		return logs;
		
	}
}
