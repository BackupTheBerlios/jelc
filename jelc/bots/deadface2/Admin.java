 package bots.deadface2;

import playerView.PlayerList;
import elc.ClientConnection;
import java.io.*;
/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Admin implements BotCommand {
ClientConnection connection;
MyBot2 bot;
PlayerList  admin;
	Admin(ClientConnection con,MyBot2 bot){
		this.connection=con;
		this.bot=bot;
		load();
	}
	public boolean process(String name, String[] args, int type) {
		if(isAdmin(name)){
			if(args[0].equals("quit")||args[0].equals("exit")){
				bot.reply(name,"i'm going down!!!",type);
				bot.quit();
				return true;
			}
			else if(args[0].equals("#cmd")){
				if(args.length!=1){
					String command=args[1];
					for(int i=2;i<args.length;i++){
						command=command+" "+args[i];
					}
					System.out.println("got a command from person: "+name+" '"+command+"'");
					bot.reply(name,command,MyBot2.LOCAL);
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("update")){
    			load();
        		return true;
    		}
    	}
		return false;
	}
	public boolean isAdmin(String name){
		return admin.getList().contains(name);
	}
	/** (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String)
	 */
	public void sendHelp(String name, int type) {
		if(isAdmin(name)){
			bot.reply(name,"Secret admin commands: ",type);
			bot.reply(name,"quit/exit     - kills me",type);
		}
	}
	public void load(){
		admin=new PlayerList(new File("admin.txt"));
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
