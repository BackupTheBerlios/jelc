/*
 * Created on Jan 11, 2005
 */
package elc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * @author frak
 *  
 */
public class Packet {
    public int protocol;

    public ByteBuffer data;

    public int length;
    public byte[] bytes;

    Packet(int p, byte[] b, int l) {
        protocol = p;
        bytes=b;
        if(l > 1)
            data = ByteBuffer.wrap(b);
        else
        	data = ByteBuffer.allocate(0);
        this.data.order(ByteOrder.LITTLE_ENDIAN);
        length = l;
    }
    public byte[] getBytes(){
    	byte[] b=data.array();
    	byte[] c=new byte[length];
    	for(int i=0;i<length;i++){
    		c[i]=b[i];
    	}
    	return c;
    }
    public String dump(){
    	String tmp=protocol+"|";
    	if(protocol!=29){
	    	if(length!=0){
		    	byte[] b=data.array();
		    	byte[] c=new byte[length];
		    	for(int i=0;i<length;i++){
		    		c[i]=b[i];
		    		tmp=tmp+b[i]+" ";
		    	}
		    	return  tmp+"|"+new String(c)+"|"+length;
	    	}
	    }
    	return tmp;
    }
}