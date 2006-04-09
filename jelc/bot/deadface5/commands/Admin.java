 package bot.deadface5.commands;

import java.io.File;
import java.io.IOException;

import playerView.PlayerList;
import elc.ClientConnection;

import bot.deadface5.Account;
import bot.deadface5.AccountManager;
import bot.deadface5.BotCommand;
import bot.deadface5.MyBot;
import bot.deadface5.Replyer;
;
/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Admin implements BotCommand {
ClientConnection connection;
MyBot bot;
PlayerList  admin;
	public Admin(ClientConnection con,MyBot bot){
		this.connection=con;
		this.bot=bot;
		//load();
	}
	public boolean process(Replyer reply,String[] args){
		if(reply.getAccount().isAdmin()){
			if(args[0].equals("quit")||args[0].equals("exit")){
				reply.reply("i'm going down!!!");
				//bot.reply(name,"i'm going down!!!",type);
				bot.quit();
				return true;
			}
			else if(args[0].equals("#cmd")){
				if(args.length!=1){
					String command=args[1];
					for(int i=2;i<args.length;i++){
						command=command+" "+args[i];
					}
					//System.out.println("got a command from person: "+reply.getName()+" '"+command+"'");
					//reply.reply(com)
					bot.con.chat(command);
					//bot.reply(name,command,MyBot.LOCAL);
					return true;
				}
			}
			else if(args[0].equals("admin")&&(args.length==3)){
				Account a=AccountManager.getAccountManger().getAccount(args[2]);
				if(args[1].equals("add")){
					a.setAdmin(true);
					reply.reply(args[1]+" is now an admin");
				}
				if(args[1].equals("remove")){
					a.setAdmin(false);
					reply.reply(args[1]+" is no longer an admin");
				}
				a.save();
				
				
			}
			else if(args[0].equalsIgnoreCase("reload")&&(args.length==2)){
				if(AccountManager.getAccountManger().reload(args[1])){
					reply.reply("reloaded account:"+args[1]);
				}
				else{
					reply.reply("error, the account '"+args[1]+"' was not reloaded.");
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("changeAccount")&&(args.length==3)){
				if(AccountManager.getAccountManger().getAccount(args[1]).procesToken(args[2])){
					reply.reply("update worked");
				}
				else{
					reply.reply("update did not work");
				}
			}
			else if(args[0].equalsIgnoreCase("restart")){
				File f=new File("restartme");
				try {
					if(f.createNewFile()){
						reply.reply("Restarting client");
						bot.quit();
					}
					else{
						reply.reply("not restarting");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bot.quit();
			}
			/*else if(args[0].equalsIgnoreCase("update")){
    			load();
        		return true;
    		}*/
    	}
		return false;
	}

	public void sendHelp(Replyer reply){
		if(reply.getAccount().isAdmin()){
			reply.reply("Secret admin commands: ");
			reply.reply("quit/exit     - kills me");
			reply.reply("#cmd [command]- uses the bot as a proxy (speaks in local) for things");
		}
	}
	/*public void load(){
		admin=new PlayerList(new File("admin.txt"));
	}*/
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
