package bot.deadface5.commands;

import bot.deadface5.BotCommand;
import bot.deadface5.Config;
import bot.deadface5.MyBot;
import bot.deadface5.Replyer;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Info implements BotCommand {
boolean on;
MyBot bot;
	public Info(MyBot bot){
		this.bot=bot;
		on=false;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[])
	 */
	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("info")){
			if(Config.getConfig().isIknow()){
				reply.reply("Welcome to my bot, iknow. it is a bot created by dns written in java");
				reply.reply("This bot is generously hosted by algorn, and is a duplicate of deadface");
				reply.reply("The source code is in cvs http://developer.berlios.de/projects/jelc/");
				reply.reply("if you have any problems/suggestions use 'msg' to send me a message or pm dns through the el forums");
			}
			else{
				reply.reply("Welcome to my bot, iknow. it is a bot created by dns written in java");
				reply.reply("This bot is the debugging version of iknow and might be unstable");
				reply.reply("The source code is in cvs http://developer.berlios.de/projects/jelc/");
				reply.reply("if you have any problems/suggestions use 'msg' to send me a message or pm dns through the el forums");
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		reply.reply("info          - info about my bot");
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
