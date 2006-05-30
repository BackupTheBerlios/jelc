/*
 * Created on 11/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EndianDataOutputStream  extends DataOutputStream{
OutputStream out;
	public EndianDataOutputStream(OutputStream out) {
		super(out);
		this.out=out;
		// TODO Auto-generated constructor stub
	}
	public  void writeEndianInt(int data) throws IOException{
		EndianUtils.writeSwappedInteger(out,data);
	}
	public void writeEndianShort(short data) throws IOException{
		EndianUtils.writeSwappedShort(out,data);
	}
	
	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

}
