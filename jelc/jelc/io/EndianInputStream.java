/*
 * Created on 11/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EndianInputStream extends DataInputStream {
InputStream in;
	public EndianInputStream(InputStream in) {
		super(in);
		this.in=in;
	}
	public int readSwapedShort() throws IOException{
		
        int value=this.readShort();
		
        
		return  ( ( ( value >> 8 ) & 0xff ) << 0 )+ ( ( ( value >> 0 ) & 0xff ) << 8 ) ;
		
		
		//return EndianUtils.readSwappedShort(in);
		
		
	}


}
