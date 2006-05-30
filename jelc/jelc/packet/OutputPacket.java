/*
 * Created on 11/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.packet;
import jelc.io.*;
import java.io.*;

public interface OutputPacket {
	public void writePacket(EndianDataOutputStream out) throws IOException;
}
