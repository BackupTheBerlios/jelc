/*
 * Created on 11/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.packet;

import java.io.IOException;

import jelc.Connection;
import jelc.Protocol;
import jelc.io.EndianDataOutputStream;

public class SendOpeningScreen implements OutputPacket {
	public SendOpeningScreen() {
		super();
	}

	public void writePacket(EndianDataOutputStream out) throws IOException {
		/*out.writeByte(Protocol.SEND_OPENING_SCREEN);
		out.writeEndianShort((short) 1);
		out.flush();*/
		byte[] data=new byte[3];
		data[0]=9;
		data[1]=1;
		out.write(data);
		System.out.println("requests opening screen");
	}

}
