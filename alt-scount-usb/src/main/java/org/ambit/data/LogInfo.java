package org.ambit.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import org.ambit.data.sample.LogSamples;
import org.ambit.util.DataUtils;

/**
 * All info describing a log  
 *
 */
public class LogInfo {
	
	private final static int LOG_START = 0x000f4240; // 1 000 000
	
	//private final static short CHUNK_SIZE = 0x0400; // not the same for all models 
	
	private int sampleCount;

	private int nextEntryAdress;
	
	private int startInfoPart;

	private int nextInfoPart;
	
	private int startSamplePart;
	
	private SampleReadInfo firstSampleReadInfo;
	
	// sport info
	private  short year;
    private  short month; 
    private short day; 
    private short hour; 
    private short minute; 
    private short sec; 
    private int duration; 
    private short ascent;
    private short descent; 
    private short avgSpeed;
    private short altiMax;
    private short altiMin; 
    private short activityType;
    private String activityName;
    private int distance;
    
    private BooleanProperty selectedProperty = new SimpleBooleanProperty(false);
    
    public double getDistance() {
    	return distance / 1000.;
    }
    
    public String getDate() {
    	return String.format("%d/%d/%d %d:%d:%d", day, month, year, hour, minute, sec);
    }
	
	public int getNextEntryAdress() {
		return nextEntryAdress;
	}
	
	public short getAscent() {
		return ascent;
	}
	
	public int getNextInfoPart() {
		return nextInfoPart;
	}
	
	public String getActivityName() {
		return activityName;
	}


	//Move_2015_03_03_12_09_53_Trail+running
	public String getCompleteName() {
		String activ = activityName.trim();
		activ = activ.replace(' ', '+');
		
		return String.format("Move_%d_%02d_%02d_%02d_%02d_%02d_%s",year, month, day, hour, minute, sec, activ);
		//return "Move_" + year + "_" + month + "_" + day + "_" + hour + "_" + minute + "_" + sec + "_" + activ;
	}
	
	
	public int getSampleCount() {
		return sampleCount;
	}

	public LogInfo(List<Byte> data, int startSamplePart, int chunkSize) {
		this.startSamplePart = startSamplePart;
		fromData(data, chunkSize);
	}
	
	private void fromData(List<Byte> data, int chunkSize) {
		
		//DisplayUtils.displayBytes(data);
		//if ( data != null ) return;
		
		int pos = 0;
		int adress = DataUtils.readInt(data, pos); // 40420f00 ?
        //System.out.println("adress:" + adress);
        pos += 4 ;
        
        int length = DataUtils.readInt(data, pos);
        //System.out.println("length:" + length);
        pos += 4 ;
        
        int lastAdress = DataUtils.readInt(data, pos);
        //System.out.println("lastAdress:" + lastAdress);
        pos += 4 ;

        int linkAdress = DataUtils.readInt(data, pos);
        //System.out.println("linkAdress:" + linkAdress);
        pos += 4 ;

        int entryCount = DataUtils.readInt(data, pos);
        //System.out.println("entryCount:" + entryCount);
        pos += 4 ;
        
        int nextFreeAdress = DataUtils.readInt(data, pos);
        //System.out.println("nextFreeAdress:" + nextFreeAdress);
        pos += 4 ;
        
        while ((data.get(pos) != 0x50) || (data.get(pos+1) != 0x4d) ) {
        	pos++;
        }

        //PMEM
        /*System.out.printf(" %02x%02x%02x%02x", 
				data.get(pos), data.get(pos+1), 
				data.get(pos+2), data.get(pos+3));*/
        
        pos += 4; 
        
        nextEntryAdress = DataUtils.readInt(data, pos);
        //System.out.println("nextAdress:" + nextEntryAdress);
        pos += 4 ;
        
        nextInfoPart = (nextEntryAdress - LOG_START) / chunkSize;
        
        int previousEntryAdress = DataUtils.readInt(data, pos);
        //System.out.println("previousEntryAdress:" + previousEntryAdress);
        pos += 4 ;
        
        short headerLength = DataUtils.readShort(data, pos);
        //System.out.println("headerLength:" + headerLength);
        pos += 2 ;
        
        pos += headerLength;
        //pos+=2;
        
        short headerLength2 = DataUtils.readShort(data, pos);
        //System.out.println("headerLength2:" + headerLength2);
        pos += 2 ;
        
        pos+=1; // unknow
        
        // date
        year = DataUtils.readShort(data, pos); // 2015 => 7df => pos 67
        pos+=2;
        month = DataUtils.readByte(data, pos++);
        day = DataUtils.readByte(data, pos++);
        hour = DataUtils.readByte(data, pos++);
        minute = DataUtils.readByte(data, pos++);
        sec = DataUtils.readByte(data, pos++);
        
        //System.out.println(String.format("Date %d/%d/%d %d:%d:%d", year, month, day, hour, minute, sec));
        
        pos+=5; //unknow
        duration =  DataUtils.readInt(data, pos);
        //System.out.println("duration:" + duration);
        pos+=4;
        ascent = DataUtils.readShort(data, pos);
        pos+=2;
        descent = DataUtils.readShort(data, pos);
        pos+=2;
        //System.out.println(String.format("%d+ %d-:", ascent, descent));
        
        int ascentTime =  DataUtils.readInt(data, pos); //OK
        //System.out.println("ascentTime:" + ascentTime);
        pos+=4;
        
        int descentTime =  DataUtils.readInt(data, pos);
        //System.out.println("descentTime:" + descentTime);
        pos+=4;
        
        short logRecovery = DataUtils.readShort(data, pos);
        //System.out.println("logRecovery:" + logRecovery);
        pos+=2;
        
        avgSpeed = DataUtils.readShort(data, pos);  // m/h
        //System.out.println("avgSpeed:" + avgSpeed);
        pos+=2;

        short maxSpeed = DataUtils.readShort(data, pos);  // m/h
        //System.out.println("maxSpeed:" + maxSpeed);
        pos+=2;
        
        //pos+=2;
        
        altiMax = DataUtils.readShort(data, pos);
        //System.out.println("altiMax:" + altiMax);
        pos+=2;
        
        altiMin = DataUtils.readShort(data, pos);
        //System.out.println("altiMin:" + altiMin);
        pos+=2;
        
        short hrAvg = DataUtils.readByte(data, pos);
        //System.out.println("hrAvg:" + hrAvg);
        pos++;
        
        short hrMax = DataUtils.readByte(data, pos);
        //System.out.println("hrMax:" + hrMax);
        pos++;
        
        short peekEffect = DataUtils.readByte(data, pos);
        //System.out.println("peekEffect:" + peekEffect);
        pos++;

        activityType = DataUtils.readByte(data, pos);
        //System.out.println("activityType:" + activityType);
        pos++;
        
        activityName = "";
		try {
			activityName = DataUtils.readString(data, pos, 16, "ISO-8859-15");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println("activityName:" + activityName);
        pos += 16;
        
        short hrMin = DataUtils.readByte(data, pos);
        //System.out.println("hrMin:" + hrMin);
        pos++;
        
        pos++;
        
        short tempMax = DataUtils.readShort(data, pos);
        //System.out.println("tempMax:" + tempMax);
        pos+=2;
        
        short tempMin = DataUtils.readShort(data, pos);
        //System.out.println("tempMin:" + tempMin);
        pos+=2;
     
        distance =  DataUtils.readInt(data, pos);
        //System.out.println("distance:" + distance);
        pos+=4;
 
        sampleCount =  DataUtils.readInt(data, pos);
        //System.out.println("sampleCount:" + sampleCount);
        pos+=4;
        
        short energy = DataUtils.readShort(data, pos);
        //System.out.println("energy:" + energy);
        pos+=2;
    
        short cadenceMax = DataUtils.readByte(data, pos);
        //System.out.println("cadenceMax:" + cadenceMax);
        pos++;
        
        short cadenceAvg = DataUtils.readByte(data, pos);
        //System.out.println("cadenceAvg:" + cadenceAvg);
        pos++;

        pos += 2;
        
        short swimLength = DataUtils.readShort(data, pos);
        //System.out.println("swimLength:" + swimLength);
        pos+=2;
  
        int speedMaxTime =  DataUtils.readInt(data, pos);
        //System.out.println("speedMaxTime:" + speedMaxTime);
        pos+=4;
         
        int altMaxTime =  DataUtils.readInt(data, pos);
        //System.out.println("altMaxTime:" + altMaxTime);
        pos+=4;

        int altMinTime =  DataUtils.readInt(data, pos);
        //System.out.println("altMinTime:" + altMinTime);
        pos+=4;

        int hrMaxTime =  DataUtils.readInt(data, pos);
        //System.out.println("hrMaxTime:" + hrMaxTime);
        pos+=4;

        int hrMinTime =  DataUtils.readInt(data, pos);
        //System.out.println("hrMinTime:" + hrMinTime);
        pos+=4;
        
        int tempMaxTime =  DataUtils.readInt(data, pos);
        //System.out.println("tempMaxTime:" + tempMaxTime);
        pos+=4;

        int tempMinTime =  DataUtils.readInt(data, pos);
        //System.out.println("tempMinTime:" + tempMinTime);
        pos+=4;
        
        int cadenceMaxTime =  DataUtils.readInt(data, pos);
        //System.out.println("cadenceMaxTime:" + cadenceMaxTime);
        pos+=4;
        
        int swimmingPoolLength =  DataUtils.readInt(data, pos);
        //System.out.println("swimmingPoolLength:" + swimmingPoolLength);
        pos+=4;
        
        short logHeaderTime = DataUtils.readShort(data, pos);
        //System.out.println("logHeaderTime:" + logHeaderTime);
        pos+=2;
   
        short batteryStart = DataUtils.readByte(data, pos);
        //System.out.println("batteryStart:" + batteryStart);
        pos++;
        
        short batteryStop = DataUtils.readByte(data, pos);
        //System.out.println("batteryStop:" + batteryStop);
        pos++;
        
        pos+=4;  // unknown
        
        int distanceBeforeCalib =  DataUtils.readInt(data, pos);  // OK
        //System.out.println("distanceBeforeCalib:" + distanceBeforeCalib);
        pos+=4;
        
        System.out.println("pos start " + pos);
        if ( headerLength2 >= 913 ) {
        	pos += 24; // unknow
        	
            String activityName2 = "";
    		try {
    			activityName2 = DataUtils.readString(data, pos, 16, "ISO-8859-15");
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		pos += 16;
    		
    		pos += 4; // unknow
    		// date
            short year2 = DataUtils.readShort(data, pos); 
            pos+=2;
            short month2 = DataUtils.readByte(data, pos++);
            short day2 = DataUtils.readByte(data, pos++);
            short hour2 = DataUtils.readByte(data, pos++);
            short minute2 = DataUtils.readByte(data, pos++);
            short sec2 = DataUtils.readByte(data, pos++);
            
            //
            pos++;
            
            int distance2 =  DataUtils.readInt(data, pos);
             pos+=4;
            
            int duration2 =  DataUtils.readInt(data, pos);
            pos+=4;
            
            short maxSpeed2 = DataUtils.readShort(data, pos);
            pos+=2;
            
            pos+=2; //unknow
            
            short ascent2 = DataUtils.readShort(data, pos); 
            pos+=2;

            short descent2 = DataUtils.readShort(data, pos); 
            pos+=2;
            
            pos+=2; // unknow
            
            short heartRateAvg2 = DataUtils.readByte(data, pos++);
            short heartRateMax2 = DataUtils.readByte(data, pos++);
 
        	pos +=4;
        	
            short recov2 = DataUtils.readShort(data, pos); 
            pos+=2;
            short energyc2 = DataUtils.readShort(data, pos); 
            pos+=2;
            
            short peak2 = DataUtils.readByte(data, pos++);
            short activityType2 = DataUtils.readByte(data, pos++);
            
            //System.out.println("pos bef " + pos);
            
            //System.out.println("Add " + (headerLength2 -211));
            
            pos += headerLength2 -211; 
            
            //System.out.println("pos end " + pos);
            
            List<Byte> sampleStartData = new ArrayList<Byte>();
            for ( int i = pos; i < data.size(); i++) {
            	//System.out.println(String.format("pos %d : %02x", i, data.get(i)));
            	sampleStartData.add(data.get(i));
            }
            
            firstSampleReadInfo = new SampleReadInfo();
            firstSampleReadInfo.samples = new LogSamples();
            firstSampleReadInfo.partNumber = startSamplePart;
            firstSampleReadInfo.remainingData = sampleStartData;
              
            
        }
        

 
	}
	
	public String toString() {
		return "" + day  + "/" + month + "/" + year + " " + hour + ":" + minute + " " + distance;
		 
	}


	public BooleanProperty selectedProperty() { 
		return this.selectedProperty; 
	}
	
	public boolean isSelected() {
		return this.selectedProperty.get();
	}
	
	public int getStartInfoPart() {
		return startInfoPart;
	}
	
	public void setStartInfoPart(int startPart) {
		startInfoPart = startPart;
	}


	public SampleReadInfo getFirstSampleReadInfo() {
		return firstSampleReadInfo;
	}
	
	public String getDuration() {
		int dur = duration/10;
		return String.format("%d:%02d:%02d", dur/3600, (dur%3600)/60, (dur%60));
	}


}
