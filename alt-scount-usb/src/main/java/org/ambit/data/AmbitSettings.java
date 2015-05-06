package org.ambit.data;

import java.util.List;

import org.ambit.util.DataUtils;


public class AmbitSettings {
	
	private short sportModeButtonLock;
	private short timemodeButtonLock;
	private short compassDeclination;
	private short unitsMode;
	private short unitsPressure;
	private short unitsAltitude;
	private short unitsDistance;
	private short unitsHeight;
	private short unitsTemp;
	private short unitsVerticalSpeed;
	private short unitsWeight;
	private short unitsCompass;
	private short unitsHeartRate;
	private short unitsSpeed;
	private short gpsPositionFormat;
	private short language;
	private short navigationStyle;
	private short syncTimeWgps;
	private short timeFormat;
	private short alarmHour;
	private short alarmMinute;
	private short alarmEnable;
	private short dualTime;
	private short dateFormat;
	private short tonesMode;
	private short backLightMode;
	private short backLightBrightness;
	private short displayBrightness;
	private short displayIsNegative;
	private short weight;
	private short birthYear;
	private short maxHr;
	private short restHr;
	private short fitnessLevel;
	private short isMale;
	private short length;
	

	
	public AmbitSettings(List<Byte> data) {
		populateFromData(data);
	}
	
	private void populateFromData(List<Byte> data) {
		int pos = 1;
	    // Check that data is long enough
	    if (data.size() < 132) {
	        return;
	    }
	    
	    //pos+=12;
	    
	    sportModeButtonLock = DataUtils.readByte(data, pos++);  //ok 1 : Actions only  0 : All Buttons
	    //System.out.println("sportModeButtonLock " + sportModeButtonLock);
	    timemodeButtonLock = DataUtils.readByte(data, pos++);  //ok 1 : Actions only  0 : All Buttons
	    //System.out.println("timemodeButtonLock " + timemodeButtonLock);
	    pos++;
	    compassDeclination = DataUtils.readShort(data, pos);
	    //System.out.println("compassDeclination " + compassDeclination);
	    pos += 4;
	    unitsMode = DataUtils.readByte(data, pos++);
	    //System.out.println("unitsMode " + unitsMode);
	    unitsPressure = DataUtils.readByte(data, pos++);
	    //System.out.println("unitsPressure " + unitsPressure);
	    unitsAltitude = DataUtils.readByte(data, pos++);
	    //System.out.println("unitsAltitude " + unitsAltitude);
	    unitsDistance = DataUtils.readByte(data, pos++);
	    //System.out.println("unitsDistance " + unitsDistance);
	    unitsHeight = DataUtils.readByte(data, pos++);
	    //System.out.println("unitsHeight " + unitsHeight);
	    unitsTemp = DataUtils.readByte(data, pos++);
	    //System.out.println("unitsTemp " + unitsTemp);
	    unitsVerticalSpeed = DataUtils.readByte(data, pos++);
	    //System.out.println("unitsVerticalSpeed " + unitsVerticalSpeed);
	    unitsWeight = DataUtils.readByte(data, pos++);
	    //System.out.println("unitsWeight " + unitsWeight);
	    unitsCompass = DataUtils.readByte(data, pos++);  // 1 Miles: 0 degree : OK
	    //System.out.println("** unitsCompass **" + unitsCompass);
	    unitsHeartRate = DataUtils.readByte(data, pos++);
	    //System.out.println("unitsHeartRate " + unitsHeartRate);
	    unitsSpeed = DataUtils.readByte(data, pos++);
	    //System.out.println("unitsSpeed " + unitsSpeed);
	    gpsPositionFormat = DataUtils.readByte(data, pos++);
	    //System.out.println("gpsPositionFormat " + gpsPositionFormat);
	    language = DataUtils.readByte(data, pos++);  // OK 02 anglais : 04 francais : OK
	    //System.out.println("language " + language);
	    navigationStyle = DataUtils.readByte(data, pos++);
	   // System.out.println("navigationStyle " + navigationStyle);
	    pos+=2;
	    syncTimeWgps = DataUtils.readByte(data, pos++);
	    //System.out.println("syncTimeWgps " + syncTimeWgps);
	    timeFormat = DataUtils.readByte(data, pos++);
	    //System.out.println("timeFormat " + timeFormat);
	    alarmHour = DataUtils.readByte(data, pos++);
	   // System.out.println("alarmHour " + alarmHour);
	    alarmMinute = DataUtils.readByte(data, pos++);
	    //System.out.println("alarmMinute " + alarmMinute);
	    alarmEnable = DataUtils.readByte(data, pos++);
	   // System.out.println("alarmEnable " + alarmEnable);
	    //--
	    pos+=2; // unknow
 
	    //---
		dualTime = DataUtils.readShort(data, pos);
		pos+=2;

		pos+=3; // unknow
		
		dateFormat = DataUtils.readByte(data, pos++);
		//System.out.println("dateFormat " + dateFormat);
		pos+=3;
		tonesMode =  DataUtils.readByte(data, pos++); // [All off] 2 [All on]1 [Buttons off] 0  ok
		//System.out.println("tonesMode " + tonesMode);
		pos+=3;  // unknow
		backLightMode =  DataUtils.readByte(data, pos++); // 0 = normal ?
		//System.out.println("backLightMode " + backLightMode);
		backLightBrightness = DataUtils.readByte(data, pos++); // 50 ok
		//System.out.println("backLightBrightness " + backLightBrightness);
		displayBrightness = DataUtils.readByte(data, pos++);  // 50
		//System.out.println("displayBrightness " + displayBrightness);
		displayIsNegative = DataUtils.readByte(data, pos++);  // 0 = light ?
		//System.out.println("displayIsNegative " + displayIsNegative);
		
		weight = DataUtils.readShort(data, pos);  // 7350 ok
		pos += 2;
	    //System.out.println("weight " + weight);

	    
	    birthYear = DataUtils.readShort(data, pos); // 1971 ok
	    //System.out.println("birthYear " + birthYear);
	    pos += 2;
	    maxHr = DataUtils.readByte(data, pos++);
	    //System.out.println("maxHr " + maxHr);
	    restHr = DataUtils.readByte(data, pos++);  // freq cardiac au repos 60 ok
	    //System.out.println("restHr " + restHr);
	    fitnessLevel = DataUtils.readByte(data, pos++);
	    //System.out.println("fitnessLevel " + fitnessLevel);
	    isMale = DataUtils.readByte(data, pos++);  //1 OK
	    //System.out.println("isMale " + isMale);
	    length = DataUtils.readByte(data, pos++);  // taille ok en cm
	    //System.out.println("lenght " + length);
	}
	
	public String getSexStr() {
		if (isMale == 1) {
			return "Male";
		}
		else {
			return "Female";
		}
	}
	
	public short getBirthYear() {
		return birthYear;
	}
	
	public short getWeight() {
		return weight;
	}
	
	public short getHeigth() {
		return length;
	}
	
	

	public void dump() {
		/*System.out.println("Taille: " + length + " cm" );
		System.out.println("Homme: " + isMale  );
		System.out.println("birthYear: " + birthYear  );
		System.out.println("weight: " + weight/ 100  );
		System.out.println("maxHr: " + maxHr  );
		System.out.println("language: " + language + " (2 en, 4 fr)" );
		System.out.println("displayBrightness: " + displayBrightness + "%" );
		System.out.println("...");*/
		
	}
	

}
