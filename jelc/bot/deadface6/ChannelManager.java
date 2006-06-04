/*
 * Created on 25-Feb-06
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface6;
import java.util.*;

public class ChannelManager {
	List channels;
	public ChannelManager() {
		super();
		channels=new Vector();
	}
	public void recievedMessage(Replyer reply,String message){
		for(Iterator itr=channels.iterator();itr.hasNext();){
			Event e =(Event)itr.next();
			if(reply.getType()==e.type){
				e.channel.recievedMessage(reply,message);
			}
		}
	}
	public boolean addChannelListener(ChannelListener channel, int type){
		return channels.add(new Event(channel,type));
	}
	
	private class Event{
		public Event(ChannelListener channel,int  type){
			this.channel=channel;
			this.type=type;
		}
		public int type;
		public ChannelListener channel;
	}
}
