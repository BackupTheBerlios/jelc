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

public class Ping implements OutputPacket {

	public Ping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void writePacket(EndianDataOutputStream out) throws IOException {
		out.writeByte(Protocol.PING);
		out.writeShort(5);
		out.writeLong(System.currentTimeMillis());
	}

}
