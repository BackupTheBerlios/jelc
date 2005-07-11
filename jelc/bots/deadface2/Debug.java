package bots.deadface2;

import elc.ClientInterface;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Debug implements BotCommand,ClientInterface {
boolean on;
boolean on2;
MyBot2 bot;
	Debug(MyBot2 bot){
		this.bot=bot;
		on=false;
		on2=true;
		bot.con.addClientListener(this);
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[])
	 */
	public boolean process(String name, String[] args,int type) {
		if(on){
			System.out.println("name: "+name+" args: "+args.length);
			for(int i=0;i<args.length;i++){
				System.out.println(i+": "+args[i]);
			}
		}
		/*if(on2){
			for(int i=0;i<args.length;i++){
				for(int j=0;j<args[i].length();j++){
					System.out.println(args[i].charAt(j)+" "+(int)args[i].charAt(j));
				}
			}
			System.out.flush();
		}*/
		if(args[0].equalsIgnoreCase("debug")&&bot.admin.isAdmin(name)){
			if(on){
				on=false;
				bot.reply(name,"debug off",type);
			}
			else{
				on=true;
				bot.reply(name,"debug on",type);
			}
			return true;
		}
		else if(args[0].equalsIgnoreCase("debug2")&&bot.admin.isAdmin(name)){
			if(on){
				on2=false;
				bot.reply(name,"debug2 off",type);
			}
			else{
				on2=true;
				bot.reply(name,"debug2 on",type);
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {
		//bot.replyMessage(name,"debug       - turn On/off debug",type);
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}
	public void onChat(String text) {
		// TODO Auto-generated method stub
		
	}
	public void onChat(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	public void onChannelChat(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	public void onPm(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	public void onPmSent(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	public void onGm(String person, String message) {
		if(on2){
			for(int i=0;i<message.length();i++){
				System.out.println(message.charAt(i)+" "+(int)message.charAt(i));
			}
			System.out.flush();
		}
	}
	public void onHint(String message) {
		// TODO Auto-generated method stub
		
	}
	public void onSystemMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	public void onMinute(int time) {
		// TODO Auto-generated method stub
		
	}

}
