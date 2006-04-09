/**
 * 
 */
package bot.deadface5.commands;


import bot.deadface5.BotCommand;
import bot.deadface5.Replyer;


/**
 * @author dns
 *
 */
public class IrcRegister implements BotCommand{

	/**
	 * 
	 */
	public IrcRegister() {
	}
	public boolean process(Replyer reply, String[] args){
		if(reply.getAccount().isSameGuild()){
			if(args[0].equalsIgnoreCase("ircRegister")&&(args.length==2)){
				reply.getAccount().addIrcNick(args[1]);
				reply.reply("the nickname '"+args[1]+"' is now registered to you");
				reply.getAccount().save();
				return true;
			}
		}
		
		return false;
	}
	public void sendHelp(Replyer reply) {
		if(reply.getAccount().isSameGuild()){
			reply.reply("ircregister   - register your nickname so that you can use the bot with irc");
		}
		/*if(bot.admin.isAdmin(name)){
		}*/
	}
	public void onQuit() {
	}
}
