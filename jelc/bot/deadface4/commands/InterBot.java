/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface4.commands;

import java.io.File;
import java.util.*;

import bot.deadface4.BotCommand;
import bot.deadface4.Misc;
import bot.deadface4.MyBot4;
import bot.deadface4.Replyer;

import elc.SystemInterface;
import playerView.PlayerList;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InterBot implements BotCommand,SystemInterface {
MyBot4 bot;
PlayerList  botList;

Vector active;
Vector pending;
int nextPing=-1;
boolean sentPing=false;
	public InterBot(MyBot4 bot){
		this.bot=bot;
		botList=new PlayerList(new File("bot.txt"));
		bot.con.addSystemListener(this);
		active=new Vector();
		pending=new Vector();
		System.out.println("starting the inter bot module");
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("bot")&&(reply.getAccount().isBot()||reply.getAccount().isAdmin())){
			//bot.reply(name,"G'day "+name+", if you don't know i'm a bot use 'Help' for a list of my commands",type);
			if(args.length==2){
				if(args[1].equals("active")){
					reply.reply("bot irc "+bot.irc.isIrcCommand());
					reply.reply("bot gmRedirect "+bot.irc.isGmRedirect());
					reply.reply("bot channel "+bot.chat.channelActive());
					//bot.reply(name,"bot gtpm "+bot.guild.,MyBot4.PM);
					return true;
				}
				else if(args[1].equals("getactive")){
					//System.out.println("getactive");
					Misc.ouputList(reply,"Active Guild Bots: ",active.listIterator());
					return true;
				}
				else if(args[1].equals("sendping")){
					for(Iterator itr=botList.getList().iterator();itr.hasNext();){
						bot.con.chatPm(itr.next().toString(),"ping");
					}
					return true;
				}
				
			}
			else if(args.length==3){
				if(args[1].equals("irc")){
					boolean was=bot.irc.isIrcCommand();
					if(args[2].equals("true")){
						if(was){
							//System.out.println(reply.getName()+" has control of irc so i will Dissable it");
							bot.irc.ircCommandDeactivate();
						}
						
					}
					else{
						if(!was){
							//System.out.println(reply.getName()+" does not have control of irc so i will Activate it");
							bot.irc.ircCommandActivate();
						}
					}
					return true;
				}
				else if(args[1].equals("gmRedirect")){
					boolean was=bot.irc.isGmRedirect();
					if(args[2].equals("true")){
						if(was){
							//System.out.println(reply.getName()+" has control of irc so i will Dissable it");
							bot.irc.gmRedirectDeactivate();
						}
					}
					else{
						if(!was){
							//System.out.println(reply.getName()+" does not have control of irc so i will Activate it");
							bot.irc.gmRedirectActivate();
						}
					}
					return true;
				}
				else if(args[1].equals("channel")){
					boolean was=bot.chat.channelActive();
					if(args[2].equals("true")){
						if(was){
							//System.out.println(reply.getName()+" has control of the channel so i will Dissable it");
							bot.chat.deactivateChannel();
						}
					}
					else{
						if(!was){
							//System.out.println(reply.getName()+" does not have control of the channel so i will Activate it");
							bot.chat.activateChannel();
						}
					}
					return true;
				}
			}
			return true;
		}		
		else if((args.length==1)&&args[0].equalsIgnoreCase("ping")){
			reply.reply("pong");
			return true;
		}
		else if((args.length==1)&&args[0].equalsIgnoreCase("pong")){
			//the bot is now active
			//insert useful code here
			if(!active.contains(reply.getName())){
				active.add(reply.getName());
				reply.reply("bot active");
			}
			pending.remove(reply.getName());
			//System.out.println("there are "+active.size()+"active bots");
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){

	}
	public void onQuit() {
		for(Iterator itr=botList.getList().iterator();itr.hasNext();){
			String name=itr.next().toString();
			bot.con.chatPm(name,"bot irc "+bot.irc.isIrcCommand());
			bot.con.chatPm(name,"bot gmRedirect "+bot.irc.isGmRedirect());
		}
	}
	/*public boolean isBot(String name){
		return botList.getList().contains(name);
	}*/
	public void onChangeMap(String arg0) {
		// TODO Auto-generated method stub
		
	}
	public void onMinute(int minute) {
		if(sentPing){
			for(Iterator itr=pending.iterator();itr.hasNext();){//did not recieve a 'pong' so they are not active
				active.remove(itr.next());
			}
			pending.clear();

			if(active.size()==0){//no other guild bots are active so i am takeing control
				System.out.println("takeing control of irc and #gm/irc bridge");
				if(!bot.irc.isGmRedirect()){
					bot.irc.gmRedirectActivate();
				}
				if(!bot.irc.isIrcCommand()){
					bot.irc.ircCommandActivate();
				}
				if(!bot.chat.channelActive()){
					bot.chat.activateChannel();
				}
			}
			sentPing=false;
		}
		
		
		if((nextPing==-1)||(nextPing==minute)){//start state or next 
			for(Iterator itr=botList.getList().iterator();itr.hasNext();){
				bot.con.chatPm(itr.next().toString(),"ping");
			}
			sentPing=true;
			nextPing=nextPing+5;//do another check in 5 minutes;
			if(nextPing>360){//greater than number of minutes in a game (ie is for next day)
				nextPing=nextPing-360;
			}
		}
		/*Random r=new Random();
		
		if((r.nextInt()%20)==0){
			for(Iterator itr=botList.getList().iterator();itr.hasNext();){
				bot.reply(itr.next().toString(),"bot active",MyBot4.PM);
			}
		}*/
	}
	

}
