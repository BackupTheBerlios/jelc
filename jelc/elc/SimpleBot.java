/*
 * Created on 9/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package elc;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SimpleBot extends Client {
	SimpleBot(String user, String password){
		super(user, password);
	}
	SimpleBot(String user, String pass, String server, int port) {
        super(user, pass, server, port);
    }
	/**
	 * just log the console output like normal
	 */
	public void onChat(String message){
		System.out.println(message);
	}

	/**
	 * when a local message with the text 'hi' is recieved, reply with 'hi USER'
	 */
	public void onChat(String user, String message){
		if(message.equalsIgnoreCase("hi")){
			this.chat("hi "+user);
		}
	}


	public static void main(String[] args) {
		SimpleBot c;
    	if(args.length < 2){
    		System.out.println("usage: java elc/SimpleBot user pass [server port]");
    		System.exit(1);
    	}
    	if(args.length == 4){
    		c = new SimpleBot(args[0], args[1], args[2], Integer.valueOf(args[3]).intValue());
    	} else {
    		c = new SimpleBot(args[0], args[1]);	
    	}
        c.start();
	}
}
