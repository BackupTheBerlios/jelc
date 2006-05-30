/*
 * Created On 4/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface4.commands;

//import java.io.File;
import java.io.File;

import bot.deadface4.BotCommand;
import bot.deadface4.Misc;
import bot.deadface4.MyBot4;
import bot.deadface4.Replyer;
import bot.deadface4.AccountManager;


import playerView.PlayerList;

import elc.ClientInterface;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Guild implements BotCommand,ClientInterface{
//Vector gtpm;
MyBot4 bot;
String gmTo;
PlayerList list;
File file =new File("guild.txt");

	public Guild(MyBot4 bot){
		this.bot=bot;
		list=new PlayerList(file);
		bot.con.addClientListener(this);
	}
	public void save(){
		list.save(file);
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(isGuild(reply.getName())){
			if(args[0].equalsIgnoreCase("#gm")){
				if(((reply.getType()==Misc.IRC)&&bot.irc.isGmRedirect())||(reply.getType()==Misc.PM)){
					String tmp=new String();
					if(args[1].startsWith("!")){
						tmp=args[1]+" "+reply.getName()+": ";
						for(int i=2;i<args.length;i++){
							tmp=tmp+" "+args[i];
						}
					}
					else if((args[1].equalsIgnoreCase("t,")||(args[1].startsWith("tux")))){
						tmp=args[1];
						for(int i=2;i<args.length;i++){
							tmp=tmp+" "+args[i];
						}					
					}
					else{
						for(int i=1;i<args.length;i++){
							tmp=tmp+" "+args[i];
						}
						//bot.reply(name,"("+name+")"+tmp,MyBot4.GM);
						tmp="("+reply.getName()+")"+tmp;
					}
					bot.chat.getGMReplyer().reply(tmp);
				}
			}
			else if(args[0].equals("#gtpm")){
				if(isGuild(reply.getName())){
					if(gmTo!=null){
						gmTo=null;
						reply.reply("#gtpm no longer active");
					}
					else{
						gmTo=reply.getName();
						reply.reply("#gtpm now active");
					}
					//System.out.println("gto: "+gmTo);
				}
			}
			else if(args[0].equals("#accept")){
				if(isGuild(reply.getName())){
					bot.con.chat("#accept ");
					/*if(isAdmin(name)){
						chat("#change_rank "+person+" 18");
					}
					else{*/
					bot.con.chat("#change_rank "+reply.getName()+" 5");					
					//}
					bot.con.chat("#gm welcome "+reply.getName()+" back into the guild");
				}
				/*else if(isPending(person)){
					chat("#accept "+person+" ");
					chat("#change_rank "+person+" 5");
					chatGm("welcome "+person+" as our new guild member");
				}*/
				else{
					reply.reply("error, you are not a pending player or you don't have enough votes");
				}
			}
			else if(args[0].equals("#remove")){
				if(isGuild(reply.getName())){
					System.out.println("removeing person: "+reply.getName());
					bot.con.chat("#remove "+reply.getName());
					bot.con.chat("#gm has been removed from the guild");
				}
			}
			else if(reply.getAccount().isAdmin()&&(args.length>1)&&(args[0].equalsIgnoreCase("guildlist"))){
				if(args[1].equalsIgnoreCase("add")&&(args.length==3)){
					AccountManager.getAccountManger().getAccount(args[2]).setSameGuild(false);
					reply.reply("add: "+args[2]);
					/*if(!bot.guild.list.containsName(args[2])){
						if(bot.guild.add(args[2])){
							reply.reply("add: "+args[2]);
						}
					}
					else{
						reply.reply("did not add: "+args[2]+", already in the list.");
					}*/
				}
				else if(args[1].equalsIgnoreCase("del")&&(args.length==3)){
					AccountManager.getAccountManger().getAccount(args[2]).setSameGuild(false);
					reply.reply("removed: "+args[2]);
					/*if(bot.guild.del(args[2])){
						//System.out.println("del: "+args[2]);
						reply.reply("removed: "+args[2]);
					}
					else{
						reply.reply("did not remove: "+args[2]+"  perhaps it was not in the list?");
					}*/
				}
				else if(args[1].equalsIgnoreCase("list")){
					Misc.ouputList(reply,"the Guild: ",list.getList().iterator());
				}
			}
			else{
				return false;
			}
			return true;
		}
		return false;
	}
    public boolean isGuild(String name){
    	return bot.seen.getGuild("lnx").contains(name)||list.contains(name);
    }
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp()
	 */
	public void sendHelp(String name) {
		if(isGuild(name)){
		}	
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply) {
		if(isGuild(reply.getName())){
			reply.reply("Messenger Commands: (oh so secret)");
			reply.reply("#gtpm         = redirects #gm messages to pm messages (for when you are training)");
			reply.reply("#gm           = sends a #gm (for when you are training)");
			reply.reply("#accept       = accepts you (back)into the guild");
			reply.reply("#remove       = removes you from the guild");
		}
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChat(java.lang.String)
	 */
	public void onChat(String text) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChat(java.lang.String, java.lang.String)
	 */
	public void onChat(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChannelChat(java.lang.String, java.lang.String)
	 */
	public void onChannelChat(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPm(java.lang.String, java.lang.String)
	 */
	public void onPm(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPmSent(java.lang.String, java.lang.String)
	 */
	public void onPmSent(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onGm(java.lang.String, java.lang.String)
	 */
	public void onGm(String person, String message) {
		if(gmTo!=null){
			message=Misc.StripColours(message);
			bot.con.chatPm(gmTo, "#GM from "+person+": "+message);
			//System.out.println("sent a message");
		}
		if(!list.contains(person)){
			list.getList().add(person);
			save();
		}
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onHint(java.lang.String)
	 */
	public void onHint(String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onSystemMessage(java.lang.String)
	 */
	public void onSystemMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onMinute(int)
	 */
	public void onMinute(int time) {
		// TODO Auto-generated method stub
		
	}
	public void onQuit() {
		// TODO Auto-generated method stub	
	}
	/*public boolean add(String name){
		boolean ok=list.getList().add(name);
		save();
		return ok;
	}
	public boolean del(String name){
		boolean ok=list.getList().remove(name);
		save();
		return ok;
	}*/
	public void onIG(String message) {
		// TODO Auto-generated method stub
		
	}

}
