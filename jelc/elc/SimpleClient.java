/*
 * Created on Jan 20, 2005
 */
package elc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author frak A simple Client as example and test.
 */
public class SimpleClient extends Client {
    BufferedReader input;

    /**
     * @param user
     * @param pass
     */
    SimpleClient(String user, String pass) {
        super(user, pass);

        this.input = new BufferedReader(new InputStreamReader(System.in));
    }

    /*
     * (non-Javadoc)
     * 
     * @see elc.Client#onChat(java.lang.String)
     */
    public void onChat(String text) {
        System.out.println("text: " + text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see elc.Client#onLoop()
     */
    public void onLoop() {
        String line = "";
        try {
            if (input.ready())
                line = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!line.matches("")) {
            this.chat(line);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see elc.Client#onUnknowPacket(elc.Packet)
     */
    public void onUnknowPacket(Packet p) {
        // TODO Auto-generated method stub
        //System.out.println("Protocol: " + p.protocol);
    }
}