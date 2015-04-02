package org.ambit.data;

/**
 * Ambit known models 
 */
public enum AmbitModel {
	Finch(0x001c, "Suunto Ambit3 Sport", 0x0400),
	Emu(0x001b, "Suunto Ambit3 Peak", 0x0400),
	Greentit(0x001d, "Suunto Ambit2 R", 0x0400), 
	Colibri(0x001a, "Suunto Ambit2 S", 0x0400),
	Duck(0x0019, "Suunto Ambit2", 0x0400),
	Bluebird(0x0010, "Suunto Ambit", 0x0200);
	
	private AmbitModel(int id, String description, int chunkSize) {
		this.id = id;
		this.description = description;
		this.chunkSize = chunkSize;
	}
	
	private final int id;
	private final String description;
	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public int getChunkSize() {
		return chunkSize;
	}

	private final int chunkSize;
	
	public static AmbitModel fromId(int ambitId ) {
		for (AmbitModel model : values() ) {
			if ( model.id == ambitId) {
				return model;
			}
		}
		return null;
	}

}
