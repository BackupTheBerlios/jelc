/*
 * Created on Jan 20, 2005
 */
package elc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

/**
 * @author frak A simple Client as example and test.
 */
public class SimpleClient extends Client {
    BufferedReader input;

    SimpleClient(String user, String pass) {
        super(user, pass);

        this.input = new BufferedReader(new InputStreamReader(System.in));
    }

    public void onChat(String text) {
        System.out.println(text);
    }
    
    public void onAddNewActor(Packet p){
        Actor i;
        Enumeration actors = this.getActors().elements();
        while(actors.hasMoreElements()){
            i = (Actor)actors.nextElement();
            System.out.print(new String(i.actor_name) + ", ");
        }
        System.out.println();
    }

    public void onLoop() {
        String line = "";
        try {
            if (input.ready())
                line = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!line.matches("")) {
            if(line.charAt(0) == '/')
                this.chatPm(line);
            else
                this.chat(line);
        }

    }


}