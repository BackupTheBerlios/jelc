

package elc.gui;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import elc.*;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommunicationsTab extends JPanel  implements ClientInterface{
JTabbedPane pane;
Vector messages;
Tab local;
Tab channel;
Tab system;
Tab guild;
ClientConnection con;
/**
	 * @param con
	 */
	public CommunicationsTab(ClientConnection con) {
		super(new BorderLayout());
		this.con=con;
		
		pane =new JTabbedPane();
		
		
		local=new Tab(0);
		pane.add("Local",local);
		
		system=new Tab(3);
		pane.add("System",system);
		
		channel=new Tab(1);
		pane.add("channel",channel);
		
		guild=new Tab(4);
		pane.add("Guild",guild);
		
		this.add(pane);
		messages=new Vector();
		
		con.addClientListener(this);
	}

	private class Tab  extends JPanel implements ActionListener{
		JTextField to;
		//JTextArea from;
		JList from;
		ClientConnection client;
		int type;
		Vector listData;
		String name;
		JButton send;
		JButton close;
		public Tab(int type){
			super(new BorderLayout());
			this.type=type;
			
			from=new JList();
			listData=new Vector();
			
			if(type==3){
				this.add(new JScrollPane(from), BorderLayout.CENTER);
			}
			else{
				
				to=new JTextField();
				to.addActionListener(this);
				
				JPanel buttons=new JPanel(new GridLayout(2,1));
				
				send=new JButton("send");
				buttons.add(send);
				send.addActionListener(this);
				if(type!=0){
				close=new JButton("close");
				close.addActionListener(this);
					buttons.add(close);
				}
						
				this.add(new JScrollPane(from), BorderLayout.CENTER);
				this.add(to, BorderLayout.NORTH);
				this.add(buttons,BorderLayout.EAST);
			}
		}
		public Tab(String name){
			super(new BorderLayout());
			this.name=name;
			type=2;
			
			to=new JTextField();
			to.addActionListener(this);
			/*
			from=new JTextArea(4,80);
			from.setEditable(false);*/
			
			from=new JList();
			listData=new Vector();
			
			JPanel buttons=new JPanel(new GridLayout(2,1));
			
			send=new JButton("send");
			buttons.add(send);
			send.addActionListener(this);
			close=new JButton("close");
			close.addActionListener(this);
			buttons.add(close);
					
			this.add(new JScrollPane(from), BorderLayout.CENTER);
			this.add(to, BorderLayout.NORTH);
			this.add(buttons,BorderLayout.EAST);
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(to)||e.getSource().equals(this.send)){
				String line=to.getText();
				if (!line.matches("")){
					if(line.startsWith("/")){
						con.chatPm(line);
					}
					else if(line.startsWith("@")||line.startsWith("#gm")){
						con.chat(line);
					}
					else{
						if(type==0){
								con.chat(line);
						}
						else if(type==1){
							con.chatChannel(line);
						}
						else if(type==2){
							con.chatPm(name,line);
						}
						else if(type==4){
							con.chatGm(line);
						}
					}
					
					to.setText("");
				}
			}
			else if(e.getSource().equals(close)){
				pane.remove(this);
			}
		}
		public void processMessage(String message){
			listData.add(message);
			from.setListData(listData);
		}
		public String getName(){
			return name;
		}
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChat(java.lateng.String)
	 */
	public void onChat(String text) {
		//System.out.println("chat: "+text);
		local.processMessage(text);
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChat(java.lang.String, java.lang.String)
	 */
	public void onChat(String person, String message) {
		
		//channel.processMessage(person+": "+message);
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChannelChat(java.lang.String, java.lang.String)
	 */
	public void onChannelChat(String person, String message) {
		channel.processMessage("["+person+"]: "+message);
		if(pane.indexOfComponent(channel)==-1){
			pane.add("channel",channel);
		}
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPm(java.lang.String, java.lang.String)
	 */
	public void onPm(String person, String message) {
		Tab tmp;
		Iterator itr=messages.iterator();
		boolean found=false;
		while(itr.hasNext()){
			tmp=(Tab)itr.next();
			if(tmp.name.equals(person)){
				tmp.processMessage(person+": "+message);
				found=true;
			}
		}
		if(!found){
			Tab newTab=new Tab(person);
			newTab.processMessage(person+": "+message);
			pane.add("PM: "+person,newTab);
			messages.add(newTab);
		}
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPmSent(java.lang.String)
	 */
	public void onPmSent(String person, String message) {
		Tab tmp;
		Iterator itr=messages.iterator();
		boolean found=false;
		while(itr.hasNext()){
			tmp=(Tab)itr.next();
			if(tmp.name.equals(person)){
				tmp.processMessage("YOU : "+message);
				found=true;
				if(pane.indexOfComponent(tmp)==-1){
					pane.add("PM: "+person,tmp);
				}
			}
			
		}
		if(!found){
			Tab newTab=new Tab(person);
			newTab.processMessage("YOU: "+message);
			pane.add("PM: "+person,newTab);
			messages.add(newTab);
		}

		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onGm(java.lang.String, java.lang.String)
	 */
	public void onGm(String person, String message) {
		guild.processMessage(person+": "+message);
		if(pane.indexOfComponent(guild)==-1){
			pane.add("Guild",guild);
		}
		
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onHint(java.lang.String)
	 */
	public void onHint(String message) {
		// TODO Auto-generated method stub
		
	}
	
	public void PMConversation(String person){
		Tab tmp;
		Iterator itr=messages.iterator();
		boolean found=false;
		while(itr.hasNext()){//looks if we already have one
			tmp=(Tab)itr.next();
			if(tmp.name.equals(person)){
				found=true;
				if(pane.indexOfComponent(tmp)==-1){
					pane.add("PM: "+person,tmp);
				}
			}
		}
		if(!found){
			Tab newTab=new Tab(person);
			pane.add("PM: "+person,newTab);
			messages.add(newTab);
		}
	}
	
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onSystemMessage(java.lang.String)
	 */
	public void onSystemMessage(String message) {
		system.processMessage(message);
		if (message.startsWith("")){
			
		}
	}

	public void onMinute(int time) {
		
	}
}
