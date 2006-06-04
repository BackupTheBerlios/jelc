
package bot.deadface6.commands;

import bot.deadface6.BotCommand;
import bot.deadface6.Replyer;
import bot.deadface6.backends.Chat;
import elc.SystemInterface;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Time implements BotCommand, SystemInterface{
//MyBot bot;
int time=0;
	public Time(){
		//this.bot=bot;
		
		Chat.getInstance().getConnection().addSystemListener(this);
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args){
		if(args[0].equalsIgnoreCase("time")){
			reply.reply("Current el time is: "+getTimeString());
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		reply.reply("time          - show the current el time");
	}
	public void onQuit() {
		
	}
	public void onChangeMap(String map) {
		// TODO Auto-generated method stub
		
	}
	public String getTimeString(){
		int hour=time/60;
		int minute=time%60;
		if(minute<10){
			return hour+":0"+minute;
		}
		return hour+":"+minute;
	}
	public void onMinute(int time) {
		this.time=time;
	}

}
