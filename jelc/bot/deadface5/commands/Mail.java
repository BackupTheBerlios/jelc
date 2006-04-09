/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface5.commands;




import java.util.Iterator;

import bot.deadface5.Account;
import bot.deadface5.AccountManager;
import bot.deadface5.BotCommand;
import bot.deadface5.Message;
import bot.deadface5.MyBot;
import bot.deadface5.Replyer;
import bot.deadface5.commands.mail.*;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Mail implements BotCommand {
MyBot bot;
	public Mail(MyBot bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("mail")&&(args.length>1)){
			if(args[1].equalsIgnoreCase("send")&&(args.length>2)){				MailSender mail=new MailSender(reply);
				boolean ok=true;
				for(int i=2;i<args.length&&ok;i++){
					Account account=AccountManager.getAccountManger().getAccount(args[i]);
					if(account.canSendTO(reply.getAccount().getName())){
						mail.addName(args[i]);
					}
					else{
						ok=false;
					}
				}
				if(ok){
					reply.getAccount().getSession().push(mail);
				}
				else{
					reply.reply("Error, could not send the message atleast one person specified");
					reply.reply("this could be that they are ether BANNED, has mail dissabled or you are on thair blacklist");
				}
				return true;
				
			}
			else if(args[1].equalsIgnoreCase("login")){
				reply.getAccount().getSession().push(new MailSystem(reply));
				return true;
			}
			else if(args[1].equalsIgnoreCase("get")){
				if((args.length==3)&&args[2].equalsIgnoreCase("unread")){
					int i=0;
					for(Iterator itr=reply.getAccount().getUnreadMessages().iterator();itr.hasNext();i++){
						Message message=(Message)itr.next();
						if(message.isRead()){
							reply.reply(i+": ("+message.getFrom()+") "+message.getSubject());
						}
						else{
							reply.reply(i+": ("+message.getFrom()+") *"+message.getSubject());
						}
					}
				}
				else{
					int i=0;
					for(Iterator itr=reply.getAccount().getMessages().iterator();itr.hasNext();i++){
						Message message=(Message)itr.next();
						if(message.isRead()){
							reply.reply(i+": "+message.getSubject());
						}
						else{
							reply.reply(i+": *"+message.getSubject());
						}
					}
				}
				return true;
			}
			else if(args[1].equalsIgnoreCase("read")&&(args.length==3)){
				//System.out.println("ID: "+args[2]);
				try{
					int index=Integer.parseInt(args[2]);
					
					Message message=reply.getAccount().getMessages().get(index);
					if(message!=null){
						reply.reply("From:"+message.getFrom());
						reply.reply("Subject: "+message.getSubject());
						//List messageText=message.getMessage();
						for(Iterator itr=message.getMessage().iterator();itr.hasNext();){
							reply.reply(itr.next().toString());
						}
						message.setRead(true);
						//System.out.println("READ: "+message.isRead());
						message.save();
					}
					else{
						reply.reply("error message doesn't exist");
					}
				}
				catch(NumberFormatException nfe){
					reply.reply("incorrect message: "+nfe.getLocalizedMessage());
				}
				return true;
			}
			//System.out.println("|||||||||||||||||||||here");
		}
		else{
			if(reply.getAccount().hasUnreadMessages()){
				int unread=reply.getAccount().getUnreadCount();
				if(unread!=0){
					reply.reply("You have "+unread+" unread message(s)");
				}
				else{
					reply.getAccount().setUnreadMessages(false);
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply)  {
		reply.reply("mail name [s] - sends a message to the people, you can send to multiple by adding names space seperated");
	}
	public void onQuit() {
		
	}
	
	
	
	
	

}
