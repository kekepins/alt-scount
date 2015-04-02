package org.ambit.command;

import java.util.List;

import org.ambit.util.DataUtils;


/**
 * Getting settings 
 */
public class LogCountCommandExecutor extends AmbitCommandExecutor<Short> {

	@Override
	protected AmbitCommand getCommand() {
		return AmbitCommand.LOG_COUNT;
	}

	@Override
	protected Short extractResult(List<Byte> byteBuffer) {
		short logCount = DataUtils.readShort(byteBuffer, 2);
		return logCount;
	}

}
