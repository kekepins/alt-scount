package org.ambit.movescount.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class POI {
	
	@JsonProperty("Altitude")
	private String altitude;
	
	@JsonProperty("CreationLocalTime")
	private String creationLocalTime;

	@JsonProperty("Latitude")
	private String latitude;

	@JsonProperty("Longitude")
	private String longitude;
	
	@JsonProperty("Name")
	private String name;

	@JsonProperty("POIID")
	private String poiid;
	
	@JsonProperty("Type")
	private String type;


	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getCreationLocalTime() {
		return creationLocalTime;
	}

	public void setCreationLocalTime(String creationLocalTime) {
		this.creationLocalTime = creationLocalTime;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPoiid() {
		return poiid;
	}

	public void setPoiid(String poiid) {
		this.poiid = poiid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
