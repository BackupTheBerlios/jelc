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
import bot.deadface4.Config;;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Sing implements BotCommand {
MyBot4 bot;
	public Sing(MyBot4 bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args){
		if(args[0].equalsIgnoreCase("sing")){
			if(Config.getConfig().isIknow()){
				reply.reply("roses are #FF0000");
				reply.reply("violets are #0000FF");
				reply.reply("all my base");
				reply.reply("are belong to you");
			}
			else{
				reply.reply("100 buckets of bits on the bus");
				reply.reply("100 buckets of bits");
				reply.reply("Take one down, short it to ground");
				reply.reply("there would be FF buckets of bits on the bus");
				reply.reply("FF buckets of bits on the bus");
				reply.reply("     ad infinitum...");
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		reply.reply("sing          - let me sing to you");

	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
