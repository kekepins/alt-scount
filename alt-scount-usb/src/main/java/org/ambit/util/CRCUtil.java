package org.ambit.util;

public class CRCUtil {
	
	public static int crc_16_ccitt(byte[] msg, int len) {
		
		int crc = 0xFFFF; // initial value
		int polynomial = 0x1021; 

		boolean bit, c15;
		for (int b = 0; b < len; b++) {
			for (int i = 0; i < 8; i++) {
				bit = ((msg[b] >> (7 - i) & 1) == 1);
				c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xFFFF;

		return crc;
	}
	
	public static int crc_16_ccitt_init(byte[] msg, int len, int init) {
		int crc = init; // initial value
		int polynomial = 0x1021; // 0001 0000 0010 0001 (0, 5, 12)

		boolean bit, c15;
		for (int b = 0; b < len; b++) {
			for (int i = 0; i < 8; i++) {
				bit = ((msg[b] >> (7 - i) & 1) == 1);
				c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;

		return crc;
	}

}
