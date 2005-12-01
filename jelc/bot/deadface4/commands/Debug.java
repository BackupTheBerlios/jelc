package bot.deadface4.commands;

import bot.deadface4.BotCommand;
import bot.deadface4.MyBot4;
import bot.deadface4.Replyer;
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
MyBot4 bot;
	public Debug(MyBot4 bot){
		this.bot=bot;
		on=false;
		on2=false;
		bot.con.addClientListener(this);
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[])
	 */
	public boolean process(Replyer reply, String[] args) {
		if(on){
			System.out.println("name: "+reply.getName()+" args: "+args.length);
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
		if(args[0].equalsIgnoreCase("debug")&&reply.getAccount().isAdmin()){
			if(on){
				on=false;
				reply.reply("debug off");
			}
			else{
				on=true;
				reply.reply("debug on");
			}
			return true;
		}
		else if(args[0].equalsIgnoreCase("debug2")&&reply.getAccount().isAdmin()){
			if(on){
				on2=false;
				reply.reply("debug2 off");
			}
			else{
				on2=true;
				reply.reply("debug2 on");
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply) {
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
