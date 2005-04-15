/*
 * Created on 2/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package elc;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class simpleGui extends JFrame implements ClientInterface,ActionListener {
JTextField in;
JList out;
ClientConnection cc;
Vector log;
JLabel timel;
	/**
	 * @throws java.awt.HeadlessException
	 */
	public simpleGui(String username, String password, String adress, int port) throws HeadlessException {
		super("JELC - a JAVA EL Client");
		JPanel root=new JPanel(new BorderLayout());
		in=new JTextField();
		in.addActionListener(this);
		root.add(in,BorderLayout.NORTH);
		log=new Vector();
		out=new JList(log);
		
		root.add(new JScrollPane(out), BorderLayout.CENTER);
		//`out.setEditable(false);
		//root.add(out, BorderLayout.CENTER);
		//JPanel buttons=new JPanel();
		timel=new JLabel("0:00");
		timel.setFont(timel.getFont().deriveFont(14));
		//buttons.add(timel,BorderLayout.SOUTH);
//		root.add(buttons);
		root.add(timel,BorderLayout.SOUTH);
		cc=new ClientConnection(username, password, adress, port);
		cc.addClientListener(this);
		cc.start();
		getContentPane().add(root);
		this.setSize(600,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		String text=in.getText()+"\n";
		if(text.startsWith("/")){
			cc.chatPm(text);
		}
		else{
			cc.chat(in.getText()+"\n");	
		}
		in.setText("");
	}


	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChat(java.lang.String)
	 */
	public void onChat(String text) {
		System.out.println(text);
		//out.append(text+"\n");
		log.add(text);
		out.setListData(log);
	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChat(java.lang.String, java.lang.String)
	 */
	public void onChat(String person, String message) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onChannelChat(java.lang.String, java.lang.String)
	 */
	public void onChannelChat(String person, String message) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPm(java.lang.String, java.lang.String)
	 */
	public void onPm(String person, String message) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPmSent(java.lang.String)
	 */
	public void onPmSent(String person,String message) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onGm(java.lang.String, java.lang.String)
	 */
	public void onGm(String person, String message) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onHint(java.lang.String)
	 */
	public void onHint(String message) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onSystemMessage(java.lang.String)
	 */
	public void onSystemMessage(String message) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
	  	if(args.length < 2){
    		System.out.println("usage: java elc/simpleGui user pass [server port]");
    		System.exit(1);
    	}
    	if(args.length == 4){
    		new simpleGui(args[0], args[1], args[2], Integer.valueOf(args[3]).intValue());
    	} else {
    		new SimpleBot(args[0], args[1],"eternal-lands.solexine.fr", 2001);	
    	}
	}
	/* (non-Javadoc)
	 * @see elc.ClientInterface#onMinute(int)
	 */
	public void onMinute(int time) {
		String tmp=(time/60)+":"+(time%60);
		timel.setText(tmp);
	}
}
