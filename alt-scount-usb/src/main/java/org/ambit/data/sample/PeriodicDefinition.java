package org.ambit.data.sample;

import java.util.ArrayList;
import java.util.List;

import org.ambit.export.ExportVisitor;
import org.ambit.util.DataUtils;

public class PeriodicDefinition extends Sample{
	
	public static final byte DISTANCE    	= 0x03;
	public static final byte SPEED 		 	= 0x04;
	public static final byte HR 		 	= 0x05;
	public static final byte TIME 	 	 	= 0x06;
	public static final byte ALTITUDE	 	= 0x0c;
	public static final byte ENERGY_CONS 	= 0x0e;
	public static final byte TEMPERATURE 	= 0x0f;
	public static final byte PRESSURE	 	= 0x18;
	public static final byte VERTICAL_SPEED	= 0x19;
	
	private List<ValueDefinition> definitions = new ArrayList<ValueDefinition>();
	
	public PeriodicDefinition(int size) {
		super(size, -1);
	}

	@Override
	public int decode(List<Byte> data, int pos) {
		int posIn = pos;
		//System.out.println("Period sample specifier");
		short count = DataUtils.readShort(data, pos);
		//System.out.println("count:" + count);
		pos+=2;

		for (int i=0; i < count; i++) {

			short id = DataUtils.readByte(data, pos);
			pos+=2;
			short offset = DataUtils.readByte(data, pos);
			pos+=2;
			short length = DataUtils.readByte(data, pos);
			pos+=2;
			//System.out.println("id" + id + "of" + offset + "length"+ length);
			definitions.add(new ValueDefinition(id, offset, length));
		}
		
		return posIn + size - 1;
	}


	public List<ValueDefinition> getDefinitions() {
		return definitions;
	}

	@Override
	public String getName() {
		return "Periodic Definition";
	}
	
	@Override
	public void accept(ExportVisitor visitor) {
		visitor.visit(this);
	}


}

