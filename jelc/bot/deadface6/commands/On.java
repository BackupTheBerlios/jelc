/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface6.commands;

import java.util.Iterator;
import java.util.List;

import bot.deadface6.AccountManager;
import bot.deadface6.BotCommand;
import bot.deadface6.Misc;
import bot.deadface6.MyBot;
import bot.deadface6.Replyer;
import jelc.playerView.PlayersOnline;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class On implements BotCommand {
//PlayersOnline online;
//SeenList2 seen;
MyBot bot;
	public On(MyBot bot){
		/*this.online=on;
		this.seen=seen2;*/
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
    	if(args[0].equalsIgnoreCase("on")||args[0].equalsIgnoreCase("#on")){
    		if(args.length==2){
    			String guild=args[1];
    			List tmp=AccountManager.getAccountManger().getGuild(guild);
    			//System.out.println("guild:"+guild);
    			if(tmp.size()!=0){
    				List on=PlayersOnline.getInstance().getOnline(tmp);
    				if(on.size()!=0){
    					Iterator i=on.iterator();
    					Misc.ouputList(reply,guild+":- ",i);
    				}
    				else{
    					reply.reply("it looks like there is no one from "+guild+" online");
    				}
    			}
    			else{
    				reply.reply("i have not seen the guild: "+guild);
    			}
    		}
    		else{
    			//Person p=seen.find(reply.getName());
    			String guild=reply.getAccount().getGuild();
    			if((guild==null)||guild.equals("")){
    				reply.reply("sorry, you don't have a guild, or i don't know it, try; on guildtag");
    			}
    			else{
    				//List tmp=getGuild(p.guild);
    				List on=PlayersOnline.getInstance().getOnline(AccountManager.getAccountManger().getGuild(guild));
        			if(on.size()!=0){
        				//Iterator i=on.iterator();
        				Misc.ouputList(reply,guild+":- ",on.iterator());
        			}
        			else{
        				reply.reply("acording to my bot there is no one from you guild online");
        			}
    			}
    		}
    		return true;
    	}
    	return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		reply.reply("on [guild]    - shows who i have seen from that guild or your own guild");
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
