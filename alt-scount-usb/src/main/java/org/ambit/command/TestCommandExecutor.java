package org.ambit.command;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.ambit.util.DataUtils;


/**
 * Getting settings 
 */
public class TestCommandExecutor extends AmbitCommandExecutor<Short> {

	@Override
	protected AmbitCommand getCommand() {
		return AmbitCommand.TEST_CMD;
	}

	@Override
	protected Short extractResult(List<Byte> byteBuffer) {
		/*short chargePct = DataUtils.readByte(byteBuffer, 1);
		return chargePct;
		*/
		
		for (Byte b: byteBuffer) {
			System.out.print(Byte.toString(b)+ " ");
		}
		
		for (Byte b: byteBuffer) {
			System.out.print(Byte.toString(b)+ " ");
		}
		
		try {
			//String str = DataUtils.readString(byteBuffer, 0, byteBuffer.size(), "UTF-8");
			String str = DataUtils.readString(byteBuffer, 0, byteBuffer.size(), "ISO-8859-15");
			
			//System.out.println("srt-->" + str);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return 1;
	}

}
