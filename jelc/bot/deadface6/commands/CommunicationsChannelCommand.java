package bot.deadface6.commands;

import bot.deadface6.BotCommand;
import bot.deadface6.MyBot;
import bot.deadface6.Replyer;
import bot.deadface6.backends.BackendManager;
import bot.deadface6.backends.Channel;
import bot.deadface6.backends.Chat;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommunicationsChannelCommand implements BotCommand{
MyBot bot;
	public CommunicationsChannelCommand(MyBot bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(reply.getAccount().isSameGuild()&&args[0].equalsIgnoreCase("#ig")&&(args.length>2)){
			String tmp="#ig "+args[1]+" ("+reply.getAccount().getName()+")";
			for(int i=2;i<args.length;i++){
				tmp=tmp+" "+args[i];
			}
			Channel local =BackendManager.getInstance().getChannel(Chat.LOCALCHANNEL);
			local.send(tmp);
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
