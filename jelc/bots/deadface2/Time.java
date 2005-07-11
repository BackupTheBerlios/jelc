/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bots.deadface2;

import elc.SystemInterface;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Time implements BotCommand, SystemInterface{
MyBot2 bot;
int time=0;
	Time(MyBot2 bot){
		this.bot=bot;
		bot.con.addSystemListener(this);
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(String name, String[] args, int type) {
		if(args[0].equalsIgnoreCase("time")){
			bot.reply(name,"Current el time is: "+getTimeString(),type);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {
		bot.reply(name,"time          - show the current el time",type);
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
