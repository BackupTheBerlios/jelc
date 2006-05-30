/*
 * Created On 4/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bots.deadface2;

//import java.io.File;
import java.io.File;
import java.util.*;

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
MyBot2 bot;
String gmTo;
PlayerList list;
File file =new File("guild.txt");

	Guild(MyBot2 bot){
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
	public boolean process(String name, String[] args, int type) {
		if(isGuild(name)){
			if(args[0].equals("#gm")){
				String tmp=new String();
				if(args[1].startsWith("!")){
					tmp=args[1]+" "+name+": ";
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
					//bot.reply(name,"("+name+")"+tmp,MyBot2.GM);
					tmp="("+name+")"+tmp;
				}
				bot.reply(name,tmp,MyBot2.GM);
			}
			else if(args[0].equals("#gtpm")){
				if(isGuild(name)){
					if(gmTo!=null){
						gmTo=null;
						bot.reply(name,"#gtpm no longer active",type);
					}
					else{
						gmTo=name;
						bot.reply(name,"#gtpm now active",type);
					}
					System.out.println("gto: "+gmTo);
				}
			}
			else if(args[0].equals("#accept")){
				if(isGuild(name)){
					bot.reply(name,"#accept "+name+" ",MyBot2.LOCAL);
					/*if(isAdmin(name)){
						chat("#change_rank "+person+" 18");
					}
					else{*/
					bot.reply(name,"#change_rank "+name+" 5",MyBot2.LOCAL);					
					//}
					bot.reply(name,"welcome "+name+" back into the guild",MyBot2.GM);
				}
				/*else if(isPending(person)){
					chat("#accept "+person+" ");
					chat("#change_rank "+person+" 5");
					chatGm("welcome "+person+" as our new guild member");
				}*/
				else{
					bot.reply(name,"error, you are not a pending player or you don't have enough votes",type);
				}
			}
			else if(args[0].equals("#remove")){
				if(isGuild(name)){
					System.out.println("removeing person: "+name);
					bot.reply(name,"#remove "+name+" ",MyBot2.LOCAL);
					bot.reply(name," has been removed from the guild",MyBot2.GM);
				}
			}
			else if(bot.admin.isAdmin(name)&&(args.length>1)&&(args[0].equalsIgnoreCase("guildlist"))){
				if(args[1].equalsIgnoreCase("add")&&(args.length==3)){
					if(!bot.guild.list.containsName(args[2])){
						if(bot.guild.add(args[2])){
							bot.reply(name,"add: "+args[2],type);
						}
					}
					else{
						bot.reply(name,"did not add: "+args[2]+", already in the list.",type);
					}
				}
				else if(args[1].equalsIgnoreCase("del")&&(args.length==3)){
					if(bot.guild.del(args[2])){
						//System.out.println("del: "+args[2]);
						bot.reply(name,"removed: "+args[2],type);
					}
					else{
						bot.reply(name,"did not remove: "+args[2]+"  perhaps it was not in the list?",type);
					}
				}
				else if(args[1].equalsIgnoreCase("list")){
					bot.outputList(name,type,"the Guild: ",list.getList().iterator());
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
    	return bot.seen.getGuild("lnx").contains(name)||list.containsName(name);
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
	public void sendHelp(String name, int type) {
		if(isGuild(name)){
			bot.reply(name,"Messenger Commands: (oh so secret)",type);
			bot.reply(name,"#gtpm         = redirects #gm messages to pm messages (for when you are training)",type);
			bot.reply(name,"#gm           = sends a #gm (for when you are training)",type);
			bot.reply(name,"#accept       = accepts you (back)into the guild",type);
			bot.reply(name,"#remove       = removes you from the guild",type);
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
			bot.reply(gmTo, "#GM from "+person+": "+message,MyBot2.PM);
			System.out.println("sent a message");
		}
		if((message.toLowerCase()).startsWith("ding")){
			bot.reply(person, "Well done "+person+" !!!",MyBot2.GM);
		}
		if(!list.containsName(person)){
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
	public boolean add(String name){
		boolean ok=list.getList().add(name);
		save();
		return ok;
	}
	public boolean del(String name){
		boolean ok=list.getList().remove(name);
		save();
		return ok;
	}
	public void onIG(String message) {
		// TODO Auto-generated method stub
		
	}

}
