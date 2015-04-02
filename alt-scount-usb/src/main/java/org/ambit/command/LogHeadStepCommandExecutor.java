package org.ambit.command;
import java.util.List;


/**
 * Getting settings 
 */
public class LogHeadStepCommandExecutor extends AmbitCommandExecutor<Integer> {

	@Override
	protected AmbitCommand getCommand() {
		return AmbitCommand.LOG_HEAD_STEP;
	}

	@Override
	protected Integer extractResult(List<Byte> byteBuffer) {
		// nothing
		return 1;
	}

}
