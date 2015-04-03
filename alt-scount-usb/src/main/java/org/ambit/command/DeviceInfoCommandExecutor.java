package org.ambit.command;

import java.util.List;

import org.ambit.data.AmbitInfo;
import org.ambit.data.AmbitModel;

public class DeviceInfoCommandExecutor extends AmbitCommandExecutor <AmbitInfo>{

	@Override
	protected AmbitInfo extractResult(List<Byte> byteBuffer) {
		return new AmbitInfo(byteBuffer);
	}

	@Override
	protected AmbitCommand getCommand() {
		return AmbitCommand.DEVICE_INFO;
	}
	
	// Send recent moveslink version as data, if not ambit biiiiiiip
	protected byte[] getSendData(AmbitModel ambitModel) {
		return new byte[] {0x02, 0x00, 0x3A, 0x00};
	}
	

}
