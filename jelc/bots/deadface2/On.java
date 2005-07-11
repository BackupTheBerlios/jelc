/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bots.deadface2;

import java.util.Iterator;
import java.util.List;
import playerView.PlayersOnline;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class On implements BotCommand {
PlayersOnline online;
SeenList2 seen;
MyBot2 bot;
	On(PlayersOnline on, SeenList2 seen2,MyBot2 bot){
		this.online=on;
		this.seen=seen2;
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(String name, String[] args, int type) {
    	if(args[0].equalsIgnoreCase("on")||args[0].equalsIgnoreCase("#on")){
    		if(args.length==2){
    			String guild=args[1];
    			List tmp=seen.getGuild(guild);
    			System.out.println("guild:"+guild);
    			if(tmp.size()!=0){
    				List on=online.getOnline(tmp);
    				if(on.size()!=0){
    					Iterator i=on.iterator();
    					bot.outputList(name,type,"",i);
    				}
    				else{
    					bot.reply(name,"it looks like there is no one from "+guild+" online",type);
    				}
    			}
    			else{
    				bot.reply(name,"i have not seen the guild: "+guild,type);
    			}
    		}
    		else{
    			Person p=seen.find(name);
    			if((p!=null)||(p.guild).equals("")){
    				bot.reply(name,"sorry, you don't have a guild, or i don't know it, try; on guildtag",type);
    			}
    			else{
    	   			List tmp=seen.getGuild(p.guild);
        			if(tmp.size()!=0){
        				Iterator i=online.getOnline(tmp).iterator();
        				bot.outputList(name,type,"",i);
        			}
        			else{
        				bot.reply(name,"i have not seen the guild: "+p.getGuild(),type);
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
	public void sendHelp(String name, int type) {
		bot.reply(name,"on [guild]    - shows who i have seen from that guild or your own guild",type);
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
