package org.ambit.data.sample;

import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class Periodic extends Sample{
	
	private int altitude;
	private int distance;
	private int time_s;
	private int hr;
	private int speed;
	
	public int getAltitude() {
		return altitude;
	}

	public int getDistance() {
		return distance;
	}

	public int getTime_s() {
		return time_s;
	}

	public int getHr() {
		return hr;
	}

	public int getSpeed() {
		return speed;
	}

	public PeriodicDefinition getDefinition() {
		return definition;
	}

	private PeriodicDefinition definition;

	public Periodic(int size, int sampleNumber, PeriodicDefinition definition) {
		super(size, sampleNumber);
		this.definition = definition;
	}

	@Override
	public int decode(List<Byte> data, int pos) {
		int posIn = pos;
		
		for (ValueDefinition def : definition.getDefinitions()) {
			int val = DataUtils.readVal(data, pos + def.getOffset(), def.getLength());
			
			if( def.getId() == PeriodicDefinition.ALTITUDE ) {
				this.altitude = val;
			}
			else if (  def.getId() == PeriodicDefinition.DISTANCE ) {
				this.distance = val;
			}
			else if (  def.getId() == PeriodicDefinition.TIME ) {
				time_s = val;
			}
			else if (  def.getId() == PeriodicDefinition.HR ) {
				hr = val;
			}
			else if (  def.getId() == PeriodicDefinition.SPEED ) {
				speed = val;
			}

			//System.out.println("Val" + val);
		}
		
		return posIn + size - 1;
	}

	@Override
	public String getName() {
		return "Periodic";
	}

	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}

}
