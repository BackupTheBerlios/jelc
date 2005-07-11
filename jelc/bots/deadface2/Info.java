package bots.deadface2;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Info implements BotCommand {
boolean on;
MyBot2 bot;
	Info(MyBot2 bot){
		this.bot=bot;
		on=false;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[])
	 */
	public boolean process(String name, String[] args,int type) {
		if(args[0].equalsIgnoreCase("info")){
			bot.reply(name,"Welcome to my bot, iknow. it is a bot created by dns written in java",type);
			bot.reply(name,"This bot is generously hosted by chance, and is a duplicate of deadface",type);
			bot.reply(name,"The source code is in cvs http://developer.berlios.de/projects/jelc/",type);
			bot.reply(name,"if you have any problems/suggestions use 'msg' to send me a message or pm dns through the el forums",type);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {
		bot.reply(name,"info          - info about my bot",type);
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
