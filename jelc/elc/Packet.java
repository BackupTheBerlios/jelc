/*
 * Created on Jan 11, 2005
 */
package elc;

/**
 * @author frak
 * 
 */
public class Packet {
	int protocol;
	byte[] data;
	int length;
	
	Packet(int p, byte[] b, int l){
		protocol = p;
		data = b;
		length = l;
	}
	
	byte[] getBytes() {
		int i = 0;
		byte[] result = new byte[length + 2];
		result[0] = (byte)protocol;
		result[2] = 0;
		result[1] = (byte)length;
		for(i=0; i < length - 1; i++){
			result[i+3] = data[i]; 
		}
		return result;
	}
}
