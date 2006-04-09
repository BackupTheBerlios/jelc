/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface5.commands;

import bot.deadface5.BotCommand;
import bot.deadface5.Misc;
import bot.deadface5.MyBot;
import bot.deadface5.Replyer;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Rox implements BotCommand {
MyBot bot;
	public Rox(MyBot bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args){
		if(args[0].equalsIgnoreCase("rox")&&reply.getAccount().isAdmin()){
			bot.con.chat("#gm i "+Misc.DARK_ORANGE+"R"+Misc.GREEN+"0"+Misc.GREY+"x"+Misc.YELLOW+ " 2"+Misc.PURPLE+"!"+Misc.BLUE+"!"+Misc.LIGHT_WHITE+"!");
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply) {
		if(reply.getAccount().isAdmin()){
			reply.reply("rox           - show how much i rock!!!");
		}
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
