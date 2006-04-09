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
public class AccountSettings implements BotCommand {
MyBot bot;
	public AccountSettings(MyBot bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("Account")){
			//reply.reply("G'day "+reply.getName()+", if you don't know i'm a bot use 'Help' for a list of my commands");
			if((args.length==3)&&(args[1].equalsIgnoreCase("blacklist"))&&(args[2].equalsIgnoreCase("get"))){
				Misc.ouputList(reply,"Your email blacklist: ",reply.getAccount().getEmailBlacklist().iterator());
			}
			if(args.length>2){
				if(args[1].equalsIgnoreCase("blacklist")){
					if(args[2].equalsIgnoreCase("add")){
						Misc.ouputList(reply,"Your email blacklist: ",reply.getAccount().getEmailBlacklist().iterator());
					}
					
				}
				if(args[1].equalsIgnoreCase("mail")){
					if(args[2].equalsIgnoreCase("enable")){
						reply.getAccount().setMailEnabled(true);
					}
					else if(args[2].equalsIgnoreCase("dissable")){
						reply.getAccount().setMailEnabled(false);
					}
					else if(args[2].equalsIgnoreCase("status")){
						reply.reply("is Mail enabled? "+reply.getAccount().isMailEnabled());
					}
				}
			}
			if(args.length==2)
			
			
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

}
