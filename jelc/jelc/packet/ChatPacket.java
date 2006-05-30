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

public class ChatPacket implements OutputPacket {
String message;
	public ChatPacket(String message) {
		this.message=message;
	}

	public void writePacket(EndianDataOutputStream out) throws IOException {
		out.write(Protocol.RAW_TEXT);
		out.writeEndianShort((short)(message.length()+3));
		out.write(message.getBytes());
	}

}
