/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface5.commands;

import bot.deadface5.BotCommand;
import bot.deadface5.MyBot;
import bot.deadface5.Replyer;
import bot.deadface5.DB;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Moderator implements BotCommand {
MyBot bot;
	public Moderator(MyBot bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("modson")&&((reply.getAccount().isModerator())||reply.getAccount().isAdmin())){
			
			//reply.reply("G'day "+reply.getAccount().getName()+", if you don't know i'm a bot use 'Help' for a list of my commands");
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

	private List getModerators() throws SQLException{
		List moderators=new Vector();
		ResultSet rs=DB.getInstance().executeQuery("select name from account where moderator=1;");
		while(rs.next()){
			moderators.add(rs.getString("name"));
		}
		
		return moderators;
	}
}
