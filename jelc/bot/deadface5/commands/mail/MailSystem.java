package bot.deadface5.commands.mail;

import java.util.Iterator;
import java.util.List;

import bot.deadface5.*;

public class MailSystem implements Session {
Replyer reply;
Message last;
//MailSender sender;
	public MailSystem(Replyer reply) {
		this.reply=reply;
		reply.reply("Welcome To my Email System, use 'help' for avalable commands, 'quit' to exit the system");
	}

	public void processSession(String arg) {
		/*if(sender!=null){
			sender.processSession(arg);
			if(arg.equals(".")){
				sender=null;
			}
		}
		else{*/
		if(arg.startsWith("quit")||arg.startsWith("logout")){
			reply.getAccount().getSession().pop();
		}
		else if(arg.startsWith("help")){
			reply.reply("This system takes controll of your session so normal commands don't work but this allows for a more interactive bot.");
			reply.reply("While logged into the email system a seperate set of commands can be used for example you can just type 'read message_number' to read that message");
			reply.reply("The commands are 'read number' will open and read the specified message, 'get' lists the messages, 'reply' start a new message to the last message");
		}
		else if(arg.startsWith("get")){
			List<Message> messages=reply.getAccount().getMessages();
			if(messages.size()!=0){
				int i=0;
				for(Iterator itr=messages.iterator() ;itr.hasNext() ;i++){
					reply.reply(i+": "+itr.next().toString());
				}
			}
			else{
				reply.reply("Sorry you don't have any messages");
			}
		}
		//}
	}

}
