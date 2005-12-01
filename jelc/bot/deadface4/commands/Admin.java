 package bot.deadface4.commands;

import playerView.PlayerList;
import elc.ClientConnection;

import bot.deadface4.Account;
import bot.deadface4.AccountManager;
import bot.deadface4.BotCommand;
import bot.deadface4.MyBot4;
import bot.deadface4.Replyer;
;
/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Admin implements BotCommand {
ClientConnection connection;
MyBot4 bot;
PlayerList  admin;
	public Admin(ClientConnection con,MyBot4 bot){
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
					//bot.reply(name,command,MyBot4.LOCAL);
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
