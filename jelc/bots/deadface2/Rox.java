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
public class Rox implements BotCommand {
MyBot2 bot;
	Rox(MyBot2 bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(String name, String[] args, int type) {
		if(args[0].equalsIgnoreCase("rox")&&bot.admin.isAdmin(name)){
			bot.reply(name,"i "+MyBot2.DARK_ORANGE+"R"+MyBot2.GREEN+"0"+MyBot2.GREY+"x"+MyBot2.YELLOW+ " 2"+MyBot2.PURPLE+"!"+MyBot2.BLUE+"!"+MyBot2.LIGHT_WHITE+"!",MyBot2.GM);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {
		if(bot.admin.isAdmin(name)){
			bot.reply(name,"rox           - show how much i rock!!!",type);
		}
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
