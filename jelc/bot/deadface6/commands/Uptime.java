
package bot.deadface6.commands;

import bot.deadface6.BotCommand;
import bot.deadface6.MyBot;
import bot.deadface6.Replyer;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Uptime implements BotCommand {
MyBot bot;
long start;
	public Uptime(MyBot bot){
		this.bot=bot;
		start=System.currentTimeMillis();
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args){
		if(args[0].equalsIgnoreCase("up")||args[0].equalsIgnoreCase("uptime")){
			//bot.reply(name,MyBot.timeSince("Current Uptime: ",start),type);
			reply.reply("uptime: "+start);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		// TODO Auto-generated method stub

	}
	
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
