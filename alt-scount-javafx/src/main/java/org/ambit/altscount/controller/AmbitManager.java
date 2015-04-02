package org.ambit.altscount.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.beans.property.ReadOnlyLongWrapper;

import org.ambit.command.DeviceInfoCommandExecutor;
import org.ambit.command.LogDescCommandExecutor;
import org.ambit.command.LogSampleCommandExecutor;
import org.ambit.command.SamplesReader;
import org.ambit.command.SettingsCommandExecutor;
import org.ambit.command.StatusCommandExecutor;
import org.ambit.data.AmbitInfo;
import org.ambit.data.AmbitModel;
import org.ambit.data.AmbitSettings;
import org.ambit.data.LogInfo;
import org.ambit.data.LogSample;
import org.ambit.data.SampleReadInfo;
import org.ambit.export.GpxExportVisitor;
import org.ambit.usb.Device;
import org.ambit.usb.DeviceFactory;
import org.ambit.usb.UsbException;

public class AmbitManager {
	
	private final static int SUUNTO_VENDOR = 0x1493;
	
	private Device ambitDevice;
	
	private final ReadOnlyLongWrapper progress = new ReadOnlyLongWrapper(this, "progress");
	
    public long getProgress() {
        return progress.get();
    }
    
    public void setProgress(long newVal) {
        progress.set(newVal);
    }

    
    public ReadOnlyLongWrapper progressProperty() {
    	return progress;
    }
	
	
	public void connectDevice() throws UsbException {
		if ( ambitDevice == null || (ambitDevice != null && !ambitDevice.isOpen()) ) {
			ambitDevice = DeviceFactory.openDevice(SUUNTO_VENDOR,0);
		}
	}
	
	public boolean isConnected() {
		return (ambitDevice != null && ambitDevice.isOpen());
	}
	
	/**
	 * Get device Info
	 * @return
	 * @throws UsbException
	 */
	public AmbitInfo getDeviceInfo() throws UsbException {
		DeviceInfoCommandExecutor deviceInfoCommandExecutor = new DeviceInfoCommandExecutor();
		return deviceInfoCommandExecutor.executeCommand(ambitDevice);
	}
	
	/**
	 * Get device charge
	 * 
	 * @throws UsbException 
	 */
	public short getDeviceCharge() throws UsbException {
		StatusCommandExecutor statusCommandExecutor = new StatusCommandExecutor();
		return statusCommandExecutor.executeCommand(ambitDevice);
	}
	
	/**
	 * Get connected ambit model
	 */
	public AmbitModel getAmbitModel() {
		if ( isConnected()) {
			return ambitDevice.getAmbitModel();
		}
		return null;
	}
	
	/**
	 * Get all Log info stored in ambit
	 * 
	 * @return
	 * @throws UsbException
	 */
	public List<LogInfo> readMoveDescriptions() throws UsbException {
		SamplesReader samplesReader = new SamplesReader();
		return samplesReader.readLogInfos(ambitDevice);
	}
	
	/**
	 * Export a log to Gpx 
	 * TODO add more file type
	 */
	public void exportLog( LogInfo logInfo, File exportDir, long currentProgress , long maxProgress ) throws UsbException, IOException {
		
		LogSampleCommandExecutor logSampleCommand = new LogSampleCommandExecutor();
		
		SampleReadInfo sampleReadInfo = logInfo.getFirstSampleReadInfo();
		logSampleCommand.setMaxSample(logInfo.getSampleCount());
		
		while ( sampleReadInfo.getLastSampleRead() < logInfo.getSampleCount() ) {
			logSampleCommand.setSampleReadInfo(logInfo.getFirstSampleReadInfo());
			LogSample logSample = logSampleCommand.executeCommand(ambitDevice);
			sampleReadInfo = logSample.getNextSampleReadInfo();
			setProgress(currentProgress + sampleReadInfo.getLastSampleRead()); 
		}
		
		// Now generate gpx, FIXME more filetype
		GpxExportVisitor gpxVisitor = new GpxExportVisitor(exportDir, logInfo.getCompleteName() + ".gpx");
		gpxVisitor.init();
		sampleReadInfo.samples.visit(gpxVisitor);
		gpxVisitor.finish();
	}
	
	/**
	 * Get settings
	 */
	public AmbitSettings getSettings() throws UsbException { 
		SettingsCommandExecutor settingCommand = new SettingsCommandExecutor();
		return settingCommand.executeCommand(ambitDevice);
	}

	/*
	 * Test command => to remove
	 */
	public void debugParts() throws UsbException {
		
		LogDescCommandExecutor logInfoCommand = new LogDescCommandExecutor();
		logInfoCommand.setPartNumber(2);
		LogInfo logInfo = logInfoCommand.executeCommand(ambitDevice);
		LogSampleCommandExecutor logSampleCommand = new LogSampleCommandExecutor();
		
		SampleReadInfo sampleReadInfo = logInfo.getFirstSampleReadInfo();
		logSampleCommand.setMaxSample(logInfo.getSampleCount());
		
		while ( sampleReadInfo.getLastSampleRead() < logInfo.getSampleCount() ) {
			logSampleCommand.setSampleReadInfo(logInfo.getFirstSampleReadInfo());
			LogSample logSample = logSampleCommand.executeCommand(ambitDevice);
			sampleReadInfo = logSample.getNextSampleReadInfo();
		}


	}
	

}
