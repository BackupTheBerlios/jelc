package bot.deadface6.bot;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import bot.deadface6.Replyer;
import bot.deadface6.Config;
import bot.deadface6.services.TimeEvent;
import bot.deadface6.services.TimeWatcher;

public class BotCommands implements TimeEvent{
	List activeBots;
	List pendingPing;
	List dead;
	public BotCommands() {
		super();
		activeBots=new Vector();
		pendingPing=new Vector();
		TimeWatcher.getInstance().addTimeWatcher(this,10,true);
	}
	public void process(Replyer reply, String command){
		if(command.startsWith("ping")){
			activeBots.add(reply.getAccount().getName());
		}
	}
	public void timeReached() {
		for (Iterator itr=pendingPing.iterator();itr.hasNext();){
			String tmp=itr.next().toString();
			dead.add(tmp);
			activeBots.remove(tmp);
		}
		//List tmp=activeBots();
	}
	
	public void something(){
		for(Iterator itr=Config.getConfig().getRelaybots().iterator();itr.hasNext();){
			String tmp=itr.next().toString();
			
		}
	}
}
