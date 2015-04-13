package org.ambit.movescount.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AmbitDeviceInfo {
	@JsonProperty("DeviceName")
	private String deviceName;
	
	@JsonProperty("DeviceURI")
	private String deviceURI;

	@JsonProperty("FirmwareURI")
	private String firmwareURI;

	@JsonProperty("FirmwareVersion")
	private String firmwareVersion;

	@JsonProperty("HardwareURI")
	private String hardwareURI;

	@JsonProperty("HardwareVersion")
	private String hardwareVersion;
	
	@JsonProperty("LastSynchedMoveStartTime")
	private String lastSynchedMoveStartTime;

	@JsonProperty("POIs")
	private List<POI> pois;

/*
		"POIs":[{"Altitude":null,"CreationLocalTime":"2015-04-05T22:27:16.0","Latitude":45.22715,"Longitude":5.785257,"Name":"ZPROUT","POIID":879313,"Type":26},
		{"Altitude":null,"CreationLocalTime":"2015-04-04T22:34:29.0","Latitude":45.232262,"Longitude":5.812006,"Name":"MAIWON","POIID":877822,"Type":26}],
		"RouteURIs":null,
		"SelfURI":"userdevices\/23B2095113001300",
		"SerialNumber":"23B2095113001300",*/


	

	public String getDeviceURI() {
		return deviceURI;
	}

	public void setDeviceURI(String deviceURI) {
		this.deviceURI = deviceURI;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getFirmwareURI() {
		return firmwareURI;
	}

	public void setFirmwareURI(String firmwareURI) {
		this.firmwareURI = firmwareURI;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getHardwareURI() {
		return hardwareURI;
	}

	public void setHardwareURI(String hardwareURI) {
		this.hardwareURI = hardwareURI;
	}

	public String getHardwareVersion() {
		return hardwareVersion;
	}

	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	public String getLastSynchedMoveStartTime() {
		return lastSynchedMoveStartTime;
	}

	public void setLastSynchedMoveStartTime(String lastSynchedMoveStartTime) {
		this.lastSynchedMoveStartTime = lastSynchedMoveStartTime;
	}

	public List<POI> getPois() {
		return pois;
	}

	public void setPois(List<POI> pois) {
		this.pois = pois;
	}


}
