/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface4.commands;

import bot.deadface4.BotCommand;
import bot.deadface4.MyBot4;
import bot.deadface4.Replyer;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Hi implements BotCommand {
MyBot4 bot;
	public Hi(MyBot4 bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("hi")||args[0].equalsIgnoreCase("hello")||args[0].equalsIgnoreCase("yo")||args[0].equalsIgnoreCase("hey")){
			reply.reply("G'day "+reply.getName()+", if you don't know i'm a bot use 'Help' for a list of my commands");
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply)  {

	}
	public void onQuit() {
		
	}

}
