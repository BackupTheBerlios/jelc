
package bot.deadface4.commands;

import bot.deadface4.BotCommand;
import bot.deadface4.MyBot4;
import bot.deadface4.Replyer;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Uptime implements BotCommand {
MyBot4 bot;
long start;
	public Uptime(MyBot4 bot){
		this.bot=bot;
		start=System.currentTimeMillis();
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args){
		if(args[0].equalsIgnoreCase("up")||args[0].equalsIgnoreCase("uptime")){
			//bot.reply(name,MyBot4.timeSince("Current Uptime: ",start),type);
			reply.reply("uptime: "+start);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		// TODO Auto-generated method stub

	}
	
	public static void main (String[] args){
		long time=System.currentTimeMillis();
		try{
			Thread.sleep(1200);
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}
		//System.out.println(MyBot4.timeSince("hdr",time));
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
