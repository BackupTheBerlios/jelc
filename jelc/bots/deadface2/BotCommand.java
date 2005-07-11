/*
 * Created On 4/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bots.deadface2;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface BotCommand {
	/**
	 * the main 
	 * @param name  the name of the person useing the bot
	 * @param args  the string given as arguments to make it easyer to process
	 * @param type  the message type, these are constants defined in mybot2.java
	 * @return  should return true if the command is valid if this is not done an error message will be sent
	 */
	public boolean process(String name,String[] args ,int type);
	/**
	 * you should overwrite this command and send Help messages
	 *
	 */
	public void sendHelp(String name, int type);
	public void onQuit();
}
