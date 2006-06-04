
package bot.deadface6.commands;

import java.util.Iterator;
import java.util.List;

import bot.deadface6.BotCommand;
import bot.deadface6.MyBot;
import bot.deadface6.Replyer;
import bot.deadface6.Misc;
import bot.deadface6.backends.*;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BackendCommands implements BotCommand {
MyBot bot;
	public BackendCommands(MyBot bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		if(reply.getAccount().isAdmin()&&(args[0].equalsIgnoreCase("backend")||args[0].equalsIgnoreCase("backends")||args[0].equalsIgnoreCase("back"))){
			if((args.length==2)&&args[1].equalsIgnoreCase("get")){
				Misc.ouputList(reply,"backends: ",BackendManager.getInstance().getBackends().iterator());
				return true;
			}
			//System.out.println("|||||||||||||||||||||"+args.length+" "+args[]);
			if((args.length>3)&&(args[1].equalsIgnoreCase("msg"))){
				System.out.println("------------------------------'"+args[2]+"'");
				Channel c=BackendManager.getInstance().getChannel(args[2]);
				if(c!=null){
					System.out.println(c.getChannelString()+" "+c.getName());
					String str=args[3];
					for(int i=4;i<args.length;i++){
						str=str+" "+args[i];
					}
					System.out.println("sending '"+str+"'");
					
					c.send(reply.getAccount().getName()+": "+str);
					return true;
				}
				else{
					reply.reply("sorry the channel '"+args[2]+"' was not found");
				}
				return true;
				
			}
			if((args.length==3)&&args[1].equalsIgnoreCase("info")){
				Backend b=BackendManager.getInstance().getBackend(args[2]);
				if(b!=null){
					reply.reply("Backend: "+b.getName()+ " default channel="+b.getDefaultChannel());
					reply.reply("Channels: "+b.getChannels().size());
					for(int i=0; i<2;i++){
						System.out.println("|"+b.getChannels().get(i));
						//System.out.println("|"+itr.next().toString());
						
					}
					Misc.ouputList(reply,"channels: ",b.getChannels().iterator());
				}
				else{
					reply.reply("backend '"+args[2]+"' was not found");
					
				}
				return true;
			}
			if((args.length==3)&&(args[1].equalsIgnoreCase("lurk"))){
				Channel c=BackendManager.getInstance().getChannel(args[2]);
				if(c!=null){
					PipeManager.getInstance().setupLurkPipe(c,reply.getChannel());
					return true;
				}
				else{
					reply.reply("sorry the channel '"+args[2]+"' was not found");
					return false;
				}
			}
			if((args.length==3)&&(args[1].equalsIgnoreCase("listen"))){
				Channel c=BackendManager.getInstance().getChannel(args[2]);
				if(c!=null){
					PipeManager.getInstance().setupPipe(c,reply.getChannel());
					return true;
				}
				else{
					reply.reply("sorry the channel '"+args[2]+"' was not found");
					return false;
				}
			}
			if((args.length==3)&&(args[1].equalsIgnoreCase("unlisten"))){
				Channel c=BackendManager.getInstance().getChannel(args[2]);
				if(c!=null){
					PipeManager.getInstance().unsetupPipe(c,reply.getChannel());
					reply.reply("no longer listening to "+args[2]);
					return true;
				}
				else{
					reply.reply("sorry the channel '"+args[2]+"' was not found");
					return false;
				}
			}
			if((args.length==2)&&(args[1].equalsIgnoreCase("pipes"))){
				reply.reply("Active pipes:");
				for(Iterator itr=PipeManager.getInstance().getPipes().iterator();itr.hasNext();){
					reply.reply(itr.next().toString());
				}
				return true;
			}
		}
		if(args[0].startsWith("@")){
			String str=args[0].substring(1);
			for(int i=1;i<args.length;i++){
				str=str+args[i];
			}
			Channel c=BackendManager.getInstance().getChannel("el:@");
			if(c!=null){
				c.send("("+reply.getAccount().getName()+") "+str);
			}
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

}
