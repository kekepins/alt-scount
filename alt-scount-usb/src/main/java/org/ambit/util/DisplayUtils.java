package org.ambit.util;

import java.util.List;

public class DisplayUtils {
	public static String getDurationStr(int milliseconds) {
		int hr = milliseconds / 3600000;
		int rem = milliseconds % 3600000;
		int mn = rem / 60000;
		int sec = rem % 60000;
		
		return String.format("%d:%d:%d", hr, mn, sec/1000);

	}
	
	public static void displayBytes(byte[] data) {
		int ligne = 0;
		int i = 0;
		
		for( int idx = 1; idx < data.length; idx++) {
			
			if ( i % 16 == 0 ) {
				System.out.print("  ");
				for ( int j = 0; j < 16; j++) {
					//System.out.print(" " + (i + j) );
				}
				System.out.println("");
			}
			
			if ( idx % 16 == 1 ) {
				System.out.print(ligne + ": " );
				ligne++;
			}
			System.out.printf(" %02x", data[idx-1]);	
			i++;
			
			/*if (idx % 16 == 0  ) {
				System.out.println("");
			}*/
		}
		System.out.println("");
	}
	
	public static void displayBytes(List<Byte> data) {
		int ligne = 0;
		int i = 0;
		
		for( int idx = 1; idx < data.size(); idx++) {
			
			if ( i % 16 == 0 ) {
				//System.out.print("  ");
				for ( int j = 0; j < 16; j++) {
					//System.out.print(" " + (i + j) );
				}
				System.out.println("");
			}
			
			if ( idx % 16 == 1 ) {
				//System.out.print(ligne + ": " );
				ligne++;
			}
			//System.out.printf(" %02x", data.get(idx-1));	
			i++;
			
			if (idx % 16 == 0  ) {
				//System.out.println("");
			}
		}
	}
}
