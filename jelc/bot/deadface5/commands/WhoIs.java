/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface5.commands;

import bot.deadface5.Account;
import bot.deadface5.AccountManager;
import bot.deadface5.BotCommand;
import bot.deadface5.MyBot;
import bot.deadface5.Replyer;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WhoIs implements BotCommand {
MyBot bot;
	public WhoIs(MyBot bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args){
		if(args[0].equalsIgnoreCase("whois")&&(args.length==2)){
			Account a=AccountManager.getAccountManger().getAccount(args[1]);
			reply.reply(a.getName()+"("+a.getGuild()+")"+a.isAdmin()+"-"+a.getLastOnline()+"-"+a.getLastSeen());
			/*Person p=bot.seen.find(reply.getName());
			if(p!=null){
				if(p.guild!=null){
					Person you=bot.seen.find(reply.getName());
					
				}
			}
			else{
				reply.reply("sorry i know nothing about that personq, are you shure they exist?");
			}*/
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply) {

	}
	public void onQuit() {
		
	}

}
