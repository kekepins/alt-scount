package org.ambit.command;

import java.util.List;

import org.ambit.data.LogHeader;


/**
 * Getting Log Head (should be call twice) 
 */
public class LogHeadCommandExecutor extends AmbitCommandExecutor<LogHeader> {

	private boolean withData;
	
	public void setWithData(boolean withData) {
		this.withData = withData;
	}

	public LogHeadCommandExecutor(boolean withData) {
		this.withData = withData;
	}

	@Override
	protected AmbitCommand getCommand() {
		return AmbitCommand.LOG_HEAD;
	}

	@Override
	protected LogHeader extractResult(List<Byte> byteBuffer) {
		
		if ( withData ) {
			return new LogHeader(byteBuffer);
		}
		else {
			return null;
		}
	}
	

}
