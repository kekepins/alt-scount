package org.ambit.command;

import java.util.List;

import org.ambit.data.AmbitSettings;


/**
 * Getting settings 
 */
public class SettingsCommandExecutor extends AmbitCommandExecutor<AmbitSettings> {

	@Override
	protected AmbitCommand getCommand() {
		return AmbitCommand.PERSONNAL_SETTINGS;
	}

	@Override
	protected AmbitSettings extractResult(List<Byte> byteBuffer) {
		return new AmbitSettings(byteBuffer);
	}

}
