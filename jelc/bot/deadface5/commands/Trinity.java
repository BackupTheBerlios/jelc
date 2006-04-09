/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface5.commands;

import java.io.File;
import java.util.*;

import bot.deadface5.BotCommand;
import bot.deadface5.Guild;
import bot.deadface5.Replyer;



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
		return true;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){

	}
	public void onQuit() {
	}

}
