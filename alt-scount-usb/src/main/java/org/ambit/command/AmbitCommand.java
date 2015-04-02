package org.ambit.command;

public enum AmbitCommand {
	
	DEVICE_INFO((short) 0x0000),
	PERSONNAL_SETTINGS((short) 0x0b00),
	LOG_COUNT((short) 0x0b06),
	LOG_HEAD_FIRST((short) 0x0b07),
	LOG_HEAD_PEEK((short) 0x0b08),
	LOG_HEAD_STEP((short) 0x0b0a),
	LOG_HEAD((short) 0x0b0b),
	LOG_READ((short) 0x0b17),
	//TEST_CMD((short) 0xa00e),
	//TEST_CMD((short) 0x0b05), // MArche A comprendre
	TEST_CMD((short) 0x0b03), // A comprendre
	//TEST_CMD((short) 0x0b02),
	//TEST_CMD((short) 0x0b04),
	//TEST_CMD((short) 0x0b06),
	
	STATUS((short) 0x0306);
		
	private AmbitCommand(short commandId) {
		this.commandId = commandId;
	}

	private short commandId;
	
	public short getCommandId() {
		return commandId;
	}

}