package org.ambit.data.sample;

public class ValueDefinition {
	
	private short id;
	private short offset;
	private short length;
	
	public ValueDefinition(short id, short offset, short length) {
		this.id = id;
		this.offset = offset;
		this.length = length;
	}

	public short getId() {
		return id;
	}

	public short getOffset() {
		return offset;
	}

	public short getLength() {
		return length;
	}
}
