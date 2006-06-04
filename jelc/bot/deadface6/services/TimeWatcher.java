package bot.deadface6.services;

import java.util.List;

import elc.SystemInterface;
import java.util.*;

import bot.deadface6.Config;
import bot.deadface6.MyBot;
import bot.deadface6.backends.BackendManager;
import bot.deadface6.backends.Channel;
import bot.deadface6.backends.Chat;

public class TimeWatcher implements SystemInterface {
	List<Data> events;
	static TimeWatcher watcher;
	private TimeWatcher() {
		Chat.getInstance().getConnection().addSystemListener(this);
		events=new Vector<Data>();
	}

	public void onChangeMap(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onMinute(int time) {
		
		if(((time%60)==0)||(time==360)){//new hour mesage
			String message=Config.getConfig().getBroadcastmessage();
			if(!message.equals("")){
				Channel c=BackendManager.getInstance().getChannel(Chat.GMCHANNEL);
				if(c!=null){
					c.send(Config.getConfig().getHourmessage());
				}
			}
		}
		
		
		for(Iterator itr=events.iterator();itr.hasNext();){
			Data tmp=(Data)itr.next();
			int to=tmp.to;
			System.out.println("time: delay: "+tmp.delay+" to: "+tmp.to);
			
			if(to<=0){
				if(tmp.repeat){
					tmp.to=tmp.delay;
					System.out.println("re setting time: delay: "+tmp.delay+" to: "+tmp.to);
				}
				else{
					events.remove(tmp);
				}
				Thread t=new Thread(tmp);
				t.setPriority(Thread.MIN_PRIORITY);
				t.start();
			
			}
			else{
				tmp.to=to-1;
			}
		}
	}
	
	public boolean addTimeWatcher(TimeEvent event, int delay, boolean repeat){
		return events.add(new Data(event,delay,repeat));
	}
	public boolean removeTimeWatcher(TimeEvent event){
		return events.remove(event);
	}
	
	public static TimeWatcher getInstance(){
		if(watcher==null){
			watcher=new TimeWatcher();
		}
		return watcher;
	}
	
	private class Data implements Runnable{
		public Data(TimeEvent event, int delay, boolean repeat) {
			this.event=event;
			this.delay=delay;
			to=delay;
			this.repeat=repeat;
		}
		TimeEvent event;
		int delay;
		int to;
		boolean repeat;
		public void run() {
			event.timeReached();
		}
	}
}
