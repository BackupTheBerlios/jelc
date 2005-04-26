package elc;

import java.util.*;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ClientConnection extends Client implements ClientInterface {
Vector clients;
Vector systemInterfaces;
Vector actorInterfaces;
	/**
	 * @param user
	 * @param pass
	 */
	public ClientConnection(String user, String pass) {
		super(user, pass);
		clients=new Vector();
		systemInterfaces=new Vector();
		actorInterfaces=new Vector();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param user
	 * @param pass
	 * @param serv
	 * @param p
	 */
	public ClientConnection(String user, String pass, String serv, int p) {
		super(user, pass, serv, p);
		// TODO Auto-generated constructor stub
		clients=new Vector();
		systemInterfaces=new Vector();
		actorInterfaces=new Vector();
	}

	/**
	 * @param serv
	 * @param p
	 */
	public ClientConnection(String serv, int p) {
		super(serv, p);
		// TODO Auto-generated constructor stub
		clients=new Vector();
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChat(java.lang.String)
	 */
	public void onChat(String text) { 
		Enumeration e=clients.elements();
		while(e.hasMoreElements()){
			((ClientInterface)e.nextElement()).onChat(text);
		}
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChat(java.lang.String, java.lang.String)
	 */
	public void onChat(String person, String message) {
		Enumeration e=clients.elements();
		while(e.hasMoreElements()){
			((ClientInterface)e.nextElement()).onChat(person,message);
		}
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChannelChat(java.lang.String, java.lang.String)
	 */
	public void onChannelChat(String person, String message) {
		Enumeration e=clients.elements();
		while(e.hasMoreElements()){
			((ClientInterface)e.nextElement()).onChannelChat(person,message);
		}
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPm(java.lang.String, java.lang.String)
	 */
	public void onPm(String person, String message) {
		Enumeration e=clients.elements();
		while(e.hasMoreElements()){
			((ClientInterface)e.nextElement()).onPm(person,message);
		}
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPmSent(java.lang.String)
	 */
	public void onPmSent(String person, String message) {
		Enumeration e=clients.elements();
		while(e.hasMoreElements()){
			((ClientInterface)e.nextElement()).onPmSent(person, message);
		}
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onGm(java.lang.String, java.lang.String)
	 */
	public void onGm(String person, String message) {
		Enumeration e=clients.elements();
		while(e.hasMoreElements()){
			((ClientInterface)e.nextElement()).onGm(person,message);
		}
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onHint(java.lang.String)
	 */
	public void onHint(String message) {
		Enumeration e=clients.elements();
		while(e.hasMoreElements()){
			((ClientInterface)e.nextElement()).onHint(message);
		}
	}
	public void onMinute(int time) {
		Enumeration e=clients.elements();
		while(e.hasMoreElements()){
			((ClientInterface)e.nextElement()).onMinute(time);
		}
		e=systemInterfaces.elements();
		while(e.hasMoreElements()){
			((SystemInterface)e.nextElement()).onMinute(time);
		}
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onSystemMessage(java.lang.String)
	 */
	public void onSystemMessage(String message) {
		Enumeration e=clients.elements();
		while(e.hasMoreElements()){
			((ClientInterface)e.nextElement()).onSystemMessage(message);
		}
	}
	
	public void onChangeMap(String map){
		Enumeration e=systemInterfaces.elements();
		while(e.hasMoreElements()){
			((SystemInterface)e.nextElement()).onChangeMap(map);
		}
	}
	public void onAddNewActor(Actor a){
		Enumeration e=actorInterfaces.elements();
		while(e.hasMoreElements()){
			((ActorInterface)e.nextElement()).onAddNewActor(a);
		}	
	}
	public void onAddNewEnhancedActor(EnhancedActor a){
		Enumeration e=actorInterfaces.elements();
		while(e.hasMoreElements()){
			((ActorInterface)e.nextElement()).onAddNewEnhancedActor(a);
		}	
	}
	public void onRemoveActor(Actor a){
		Enumeration e=actorInterfaces.elements();
		while(e.hasMoreElements()){
			((ActorInterface)e.nextElement()).onRemoveActor(a);
		}	
	}
	
	
	public boolean addClientListener(ClientInterface ci){
		return clients.add(ci);
	}
	public boolean removeClientListener(ClientInterface ci){
		return clients.remove(ci); 
	}
	public ClientInterface[] getClientListeners(){
		return (ClientInterface[])clients.toArray();
	}
	
	public boolean addSystemListener(SystemInterface si){
		return systemInterfaces.add(si);
	}
	public boolean removeSystemListener(SystemInterface si){
		return systemInterfaces.remove(si);
	}
	public SystemInterface[] getSystemInterfaces(){
		return (SystemInterface[])systemInterfaces.toArray();
	}
	
	public boolean addActorInterface(ActorInterface si){
		return actorInterfaces.add(si);
	}
	public boolean removeActorInterface(ActorInterface si){
		return actorInterfaces.remove(si);
	}
	public ActorInterface[] getActorInterfaces(){
		return (ActorInterface[])actorInterfaces.toArray();
	}
}
