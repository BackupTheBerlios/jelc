package bot.deadface6.commands;

import java.util.Iterator;

import bot.deadface6.BotCommand;
import bot.deadface6.Guild;
import bot.deadface6.Replyer;



/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Trinity implements BotCommand {
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("getguild")){
			for(Iterator itr=Guild.getInstance().getMembers().iterator();itr.hasNext();){
				reply.reply("GUILD: "+itr.next());
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){

	}
	public void onQuit() {
	}

}
