package org.ambit.altscount.model;

import org.ambit.data.LogInfo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class TLogInfo {
	
	private final LogInfo logInfo;
	
    private BooleanProperty selectedProperty = new SimpleBooleanProperty(false);

	
	public TLogInfo(LogInfo logInfo) {
		this.logInfo = logInfo;
	}
	

	public BooleanProperty selectedProperty() { 
		return this.selectedProperty; 
	}
	
	public boolean isSelected() {
		return this.selectedProperty.get();
	}

	public LogInfo getLogInfo() {
		return logInfo;
	}


	public long getSampleCount() {
		return logInfo.getSampleCount();
	}


	public String getCompleteName() {
		return logInfo.getCompleteName();
	}
	
	public String getDate() {
		return logInfo.getDate();
	}
	
	public double getDistance() {
		return logInfo.getDistance();
	}
	
	public short getAscent() {
		return logInfo.getAscent();
	}
	
	
	public String getDuration() {
		return logInfo.getDuration();
	}
	
	public String getActivityName() {
		return logInfo.getActivityName();
	}
	


}
