/*
 * Created On 5/05/2005
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
public class Hi implements BotCommand {
MyBot2 bot;
	Hi(MyBot2 bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(String name, String[] args, int type) {
		if(args[0].equalsIgnoreCase("hi")||args[0].equalsIgnoreCase("hello")||args[0].equalsIgnoreCase("yo")||args[0].equalsIgnoreCase("hey")){
			bot.reply(name,"G'day "+name+", if you don't know i'm a bot use 'Help' for a list of my commands",type);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {

	}
	public void onQuit() {
		
	}

}
