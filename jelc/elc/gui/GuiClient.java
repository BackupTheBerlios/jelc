package elc.gui;
import javax.swing.*;
import java.awt.*;

import elc.*;
import playerView.*;

/*
 * Created on 7/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GuiClient extends JFrame{
	CommunicationsTab communications;
	SidePanel side;
	ClientConnection con;
	PlayersOnline online;
	
	GuiClient(String name, String password,String hostname,int port){
		super("Java EL Client");
		
		online=new PlayersOnline();
		
		
		this.con=new ClientConnection(name,password,hostname,port);
		JPanel main=new JPanel( new BorderLayout());
		communications=new CommunicationsTab(con);
		
		//main.add(new JLabel("graphics go here"),BorderLayout.CENTER);
		main.add(new MapView(this),BorderLayout.CENTER);
		main.add(communications,BorderLayout.SOUTH);
		
		side=new SidePanel(this); 
		JPanel root=new JPanel(new BorderLayout());
		root.add(main,BorderLayout.CENTER);
		root.add(side, BorderLayout.EAST);
		
		this.getContentPane().add(root);
		this.setSize(600,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		con.start();
		this.setVisible(true);
	}
	public ClientConnection getClientConnection(){
		return con;
	}
	public PlayersOnline getPlayersOnline(){
		return online;
	}
	
	
	public static void main(String[] args) {
		if(args.length==2){
			new GuiClient(args[0],args[1],Client.DEFAULT_ADRESS,2000);
		}
		else if(args.length==4){
			int tmp=Integer.parseInt(args[3]);
			new GuiClient(args[0],args[1],args[2],tmp);
		}
		else {
			System.err.println("Usage: java -jar gui.jar username password [server port]");
		}
	
	}


}
