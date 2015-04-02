package org.ambit.util;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;


public class DataUtils {
	public static short readByte(List<Byte> data, int pos) {
		short val = data.get(pos);
		
		if ( val < 0 ) {
			val += 256;
		}
		//System.out.printf(" %02x:%d\r\n", data.get(pos), val);
		return val;
	}
	
	public static short readByte(byte[] data, int pos) {
		short val = data[pos];
		
		if ( val < 0 ) {
			val += 256;
		}
		//System.out.printf(" %02x:%d\r\n",  data[pos], val);
		return val;
	}

	
	public static short readShort(List<Byte> data, int pos) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(data.get(pos));
		bb.put(data.get(pos+1));
		
		short val =  bb.getShort(0);
		
		//System.out.printf(" %02x%02x => %d\r\n", data.get(pos), data.get(pos+1), val);
		
		return val;
	}
	
	public static short readShort(byte[] data, int pos) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(data[pos]);
		bb.put(data[pos+1]);
		
		short val =  bb.getShort(0);
		
		//System.out.printf(" %02x%02x => %d\r\n", data[pos], data[pos+1], val);
		
		return val;
	}

	public static int readInt(List<Byte> data, int pos) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(data.get(pos));
		bb.put(data.get(pos+1));
		bb.put(data.get(pos+2));
		bb.put(data.get(pos+3));

		int val =  bb.getInt(0);
		
		/*System.out.printf(" %02x%02x%02x%02x => %d\r\n", 
				data.get(pos), data.get(pos+1), 
				data.get(pos+2), data.get(pos+3), val);*/
		
		return val;
	}
	
	public static int readVal(List<Byte> data, int pos, short size ) {
		if ( size == 1 ) {
			return readByte(data, pos);
		}
		else if ( size == 2 ) {
			return readShort(data, pos);
		}
		else if ( size == 4 ) {
			return readInt(data, pos);
		}
		return 0;
		
	}
	
	public static int readInt(byte[] data, int pos) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(data[pos]);
		bb.put(data[pos+1]);
		bb.put(data[pos+2]);
		bb.put(data[pos+3]);

		int val =  bb.getInt(0);
		
		/*System.out.printf(" %02x%02x%02x%02x => %d\r\n", 
				data[pos], data[pos+1], 
				data[pos+2], data[pos+3], val);*/
		
		return val;
	}
	
	public static String readString(List<Byte> data, int pos, int size, String encoding) 
		throws UnsupportedEncodingException {
		//String str = new String();
		
		byte[] strBytes = new byte[size];
		for ( int idx = 0; idx < size; idx++) {
			strBytes[idx] = data.get(pos + idx);
		}
		
		return new String(strBytes, encoding);
	}

	public static byte[] shortToBytes(short shortValue, ByteOrder order) {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.order(order);
		buffer.putShort(shortValue);
		return buffer.array();
	}
	
	public static byte[] intToBytes(int intValue, ByteOrder order) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.order(order);
		buffer.putInt(intValue);
		return buffer.array();
	}

	
}
