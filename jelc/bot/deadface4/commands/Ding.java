/**
 * 
 */
package bot.deadface4.commands;

import elc.*;

/**
 * @author dns
 *
 */
public class Ding implements ClientInterface {
	ClientConnection con;
	/**
	 * 
	 */
	public Ding(ClientConnection con) {
		this.con=con;
		con.addClientListener(this);
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChat(java.lang.String)
	 */
	public void onChat(String text) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChat(java.lang.String, java.lang.String)
	 */
	public void onChat(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChannelChat(java.lang.String, java.lang.String)
	 */
	public void onChannelChat(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPm(java.lang.String, java.lang.String)
	 */
	public void onPm(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPmSent(java.lang.String, java.lang.String)
	 */
	public void onPmSent(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onGm(java.lang.String, java.lang.String)
	 */
	public void onGm(String person, String message) {
		if((message.toLowerCase()).startsWith("ding")){
			con.chatGm("Well done "+person+" !!!");
		}
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onHint(java.lang.String)
	 */
	public void onHint(String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onSystemMessage(java.lang.String)
	 */
	public void onSystemMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onMinute(int)
	 */
	public void onMinute(int time) {
		// TODO Auto-generated method stub
		
	}

	public void onIG(String message) {
		// TODO Auto-generated method stub
		
	}

}
