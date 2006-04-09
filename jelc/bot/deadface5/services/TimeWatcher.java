package bot.deadface5.services;

import java.util.List;

import elc.SystemInterface;
import java.util.*;

import bot.deadface5.MyBot;

public class TimeWatcher implements SystemInterface {
	List<Data> events;

	public TimeWatcher(MyBot bot) {
		bot.con.addSystemListener(this);
		events=new Vector<Data>();
	}

	public void onChangeMap(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onMinute(int arg0) {
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
