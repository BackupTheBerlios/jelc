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
    int protocol;

    ByteBuffer data;

    int length;

    Packet(int p, byte[] b, int l) {
        protocol = p;
        if(l > 1)
            data = ByteBuffer.wrap(b);
        else
        	data = ByteBuffer.allocate(0);
        this.data.order(ByteOrder.LITTLE_ENDIAN);
        length = l;
    }
}