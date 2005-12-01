/*
 * Created On 3/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface4;
import java.util.*;

import playerView.PlayersOnline;
import elc.Client;
/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NotifyList {
Vector list;
PlayersOnline online;
Client client;
	/**
	 * 
	 */
	public NotifyList(PlayersOnline online,Client client) {
		list=new Vector();
		this.online=online;
		this.client=client;
	}
	public void check(){
		Iterator i=list.iterator();
		online.load();
		while(i.hasNext()){

			notifyer tmp=(notifyer)i.next();
			//System.out.println(tmp.toString()+"|"+online.findIn(tmp.person));
			if(online.findIn(tmp.person)!=null){
				client.chatPm(tmp.notifyingPerson,tmp.person+", is now online.");
				list.remove(tmp);
			}
		}
	}
	public boolean addNotifyer(String personWaiting, String PersonBeingNotifyed){
		return list.add(new notifyer(personWaiting,PersonBeingNotifyed));
	}
	private class notifyer{
		String person;//person to find
		String notifyingPerson;//person
		notifyer(String person,String notifyingPerson){
			this.person=person;
			this.notifyingPerson=notifyingPerson;
		}
		public String toString(){
			return person+" "+notifyingPerson;
		}
	}
}
