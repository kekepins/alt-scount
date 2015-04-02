package org.ambit.command;

import java.util.List;

import org.ambit.util.DataUtils;


/**
 * Getting settings 
 */
public class StatusCommandExecutor extends AmbitCommandExecutor<Short> {

	@Override
	protected AmbitCommand getCommand() {
		return AmbitCommand.STATUS;
	}

	@Override
	protected Short extractResult(List<Byte> byteBuffer) {
		short chargePct = DataUtils.readByte(byteBuffer, 1);
		return chargePct;
	}

}
