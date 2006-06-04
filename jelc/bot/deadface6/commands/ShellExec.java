/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface6.commands;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import bot.deadface6.BotCommand;
import bot.deadface6.MyBot;
import bot.deadface6.Replyer;
import bot.deadface6.Session;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShellExec implements BotCommand {
MyBot bot;
	public ShellExec(MyBot bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("getShell")&&reply.getAccount().isAdmin()){
			String str=new String();
			for(int i=1;i<args.length;i++){
				str=str+" "+args[i];
			}
			new ShellImpl(str,reply);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply)  {

	}
	public void onQuit() {
		
	}
	private class ShellImpl implements Session, Runnable{
		Process p;
		Replyer reply;
		BufferedReader in;
		DataOutputStream out;
		String command;
		boolean running=true;
		ShellImpl(String command, Replyer reply){
			this.reply=reply;
			this.command=command;
			reply.getAccount().getSession().push(this);
			new Thread(this).start();
		}
		public void run(){
			Runtime r=Runtime.getRuntime();
			try {
				p=r.exec(command);
				in=new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line=in.readLine();;
				while(running&&(line!=null)){
					reply.reply(line);
					line=in.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
				closed();
			}
			closed();
		}
		public void processSession(String arg) {
			try {
				out.writeUTF(arg);
			} catch (IOException e) {
				e.printStackTrace();
				closed();
			}
		}
		public void closed(){
			p.destroy();
			reply.reply("Process terminated");
			reply.getAccount().getSession().pop();
		}

		
	}
}
