/*
 * Created on 11/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.packet;

import java.io.DataOutputStream;
import java.io.IOException;

import jelc.Protocol;
import jelc.io.EndianDataOutputStream;

public class PingResponse implements OutputPacket {
byte[] data;
	public PingResponse(byte[] data) {
		this.data=data;
	}

	public void writePacket(EndianDataOutputStream out) throws IOException {		
		/*for(int i=0;i<data.length;i++){
			System.out.print(data[i]);
		}*/
		out.write(Protocol.PING_RESPONSE);
		out.writeEndianShort((short)(data.length+3));
		out.writeEndianShort((short)(data.length+1));
		out.write(data);
		
//		System.out.println("sent ping response");
	}

}
