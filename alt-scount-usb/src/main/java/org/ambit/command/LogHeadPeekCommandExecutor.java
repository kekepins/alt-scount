package org.ambit.command;

import java.util.List;

import org.ambit.util.DataUtils;


/**
 * Getting settings 
 */
public class LogHeadPeekCommandExecutor extends AmbitCommandExecutor<Boolean> {

	@Override
	protected AmbitCommand getCommand() {
		return AmbitCommand.LOG_HEAD_PEEK;
	}

	@Override
	protected Boolean extractResult(List<Byte> byteBuffer) {
		
		int valMore = DataUtils.readInt(byteBuffer, 0);
		boolean isMore = false;
		if ( 0x00000400 == valMore ) {
			isMore = true;
		}
		
		return isMore;
	}

}
