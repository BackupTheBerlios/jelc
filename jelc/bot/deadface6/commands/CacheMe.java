package bot.deadface6.commands;

import bot.deadface6.BotCommand;
import bot.deadface6.Replyer;
import bot.deadface6.StatsCache;
import jelc.playerView.*;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CacheMe implements BotCommand {
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("cacheme")){
			int status=StatsCache.getInstance().cache(reply.getAccount().getName());
			if(status==Player.PRIVACYON){
				reply.reply("error, you still have privacy on, #set_privacy off, then pm me again");
			}
			if(status==Player.INCACHE){
				Player p=StatsCache.getInstance().getPlayer(reply.getAccount().getName());
				reply.reply("player sucsessfully put in cache, if someone asks you have a combat lvl of "+p.getCombatLevel()+".");
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply)  {
		reply.reply("cacheme       - Gets me to store a cached version of your stats and will only reviel a combat level when people ask for stats");
	}
	public void onQuit() {
		
	}

}
