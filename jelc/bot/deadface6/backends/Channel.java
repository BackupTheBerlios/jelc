package bot.deadface6.backends;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import bot.deadface6.Account;
import bot.deadface6.Replyer;

public abstract class Channel {
	List <MessageListener> listeners;
	protected Channel(){
		listeners=new Vector();
	}
	public abstract String getName();
	public abstract Replyer getReplyer();
	public abstract String getChannelString();
	public abstract void send(String message);
	public boolean addMessageListener(MessageListener listener){
		return listeners.add(listener);
	}
	public boolean removeMessageListener(MessageListener listener){
		return listeners.remove(listener);
	}
	public  List<MessageListener> getMessageListeners(){
		return listeners;
	}
	public void recivededMessage(String s){
		List templist=new Vector(listeners);
		for(Iterator itr=templist.iterator();itr.hasNext();){
			MessageListener tmp=(MessageListener)itr.next();
			System.out.println(this);
			System.out.println(s);
			tmp.revieved(this, s);
		}
	}
	public abstract void stop();
	public abstract Backend getBackend();
	public String toString(){
		return getChannelString();
	}
	public boolean equals(Object o){
		if (o instanceof Channel){
			Channel tmp=(Channel)o;
			if(tmp.getChannelString().equalsIgnoreCase(this.getChannelString())){
				return true;
			}
		}
		return false;
	}
	public boolean canRemove(){
		if(listeners.size()!=0){
			return true;
		}
		return false;
	}
}
