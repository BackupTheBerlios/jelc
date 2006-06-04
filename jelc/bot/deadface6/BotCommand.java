/*
 * Created On 4/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface6;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface BotCommand {
	/**
	 * the main 
	 * @param args  the string given as arguments to make it easyer to process
	 * @param reply  the message type, these are constants defined in mybot2.java
	 * @return  should return true if the command is valid if this is not done an error message will be sent
	 */
	public boolean process(Replyer reply,String[] args);
	/**
	 * you should overwrite this command and send Help messages
	 *
	 */
	public void sendHelp(Replyer reply);
	public void onQuit();
}
