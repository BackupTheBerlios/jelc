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
	/**
	 * @param user
	 * @param pass
	 */
	public ClientConnection(String user, String pass) {
		super(user, pass);
		clients=new Vector();
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
	public void onPmSent(String message) {
		Enumeration e=clients.elements();
		while(e.hasMoreElements()){
			((ClientInterface)e.nextElement()).onPmSent(message);
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

	public boolean addClientInterface(ClientInterface ci){
		return clients.add(ci);
	}
	public boolean removeClientInterface(ClientInterface ci){
		return clients.remove(ci); 
	}
}
