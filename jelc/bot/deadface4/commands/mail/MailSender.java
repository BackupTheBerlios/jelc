package bot.deadface4.commands.mail;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import bot.deadface4.Message;
import bot.deadface4.Replyer;
import bot.deadface4.Session;

public class MailSender implements Session{
	Replyer reply;
	List names;
	List message;
	String subject;
	public MailSender(Replyer reply){
		this.reply=reply;
		names=new LinkedList();
		message=new LinkedList();
		reply.reply("please enter the subject");
	}
	public void processSession(String arg) {
		if(arg.equals(".")){
			//send
			for(Iterator itr=names.iterator();itr.hasNext();){
				Message msg=new Message((String)itr.next(),reply.getName(),message);
				msg.setSubject(subject);
				msg.save();
				//bot.session.unregister(bot.accounts.getAccount(reply.getName()));
				if(reply.getAccount().getSession().equals(this)){
					reply.getAccount().setSession(null);//for nested call
				}
			}
			reply.reply("Message sent");
		}
		else{
			if(subject==null){
				subject=arg;
				reply.reply("names entered, please enter your message. To finish editing reply with '.'");
			}
			else{
				message.add(arg);
			}
		}
	}
	public boolean addName(String name){
		return names.add(name);
	}
}