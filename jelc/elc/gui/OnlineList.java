package elc.gui;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import playerView.Player;
import playerView.PlayerList;
import playerView.PlayersOnline;

/*
 * Created on 15/04/2005
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
public class OnlineList extends JPanel implements Runnable, ActionListener{

	GuiClient gui;
	PlayersOnline players;
	JComboBox type; 
	Vector views;
	JList playerList;
	Thread updateThread;
	Player player;
	PlayerList currentList;
	
	JButton chatWith;
	JButton stats;
	/**
	 * @param isDoubleBuffered
	 */
	public OnlineList(GuiClient g) {
		super(new BorderLayout());
		
		this.gui=g;
		this.players=g.getPlayersOnline();
		
		
		views=new Vector();
		type=new JComboBox(views);
		type.addActionListener(this);
		this.add(type,BorderLayout.NORTH);

		
		playerList=new JList();
		JScrollPane scroll =new JScrollPane(playerList);
		scroll.setPreferredSize(new Dimension(70,50));
		scroll.setMinimumSize(new Dimension(50,50));
		scroll.setMaximumSize(new Dimension(170,50));
		this.add(scroll,BorderLayout.CENTER);
		
		JPanel buttons=new JPanel(new GridLayout(2,1));
		
		chatWith=new JButton("chat");
		chatWith.addActionListener(this);
		buttons.add(chatWith);
		
		stats=new JButton("stats");
		stats.addActionListener(this);
		buttons.add(stats);

		this.add(buttons,BorderLayout.SOUTH);
		
		/*JButton update=new JButton("refresh");
		this.add(update,BorderLayout.SOUTH);*/

		buildList();
		
		this.setPreferredSize(new Dimension (100,50));
		
		updateThread=new Thread(this);
		updateThread.start();

	}
	public void  buildList(){
		players= new PlayersOnline();
		currentList=new PlayerList("Online players",null);
		//views.add((Object)mask.getInstance());
		views.add(currentList);
		File f=new File("./lists/");
		System.out.println(f);
		File[] files=f.listFiles();
		if(f.exists()&&f.isDirectory())
			for(int i=0;i<files.length;i++){
				if (files[i].isFile()&&files[i].canRead()){
					views.add(new PlayerList(files[i]));
				}
			}
		else{
			System.err.println("no lists loaded. put some txt files in ./lists/");
		}
	}
	public void run() {
		while(true){
			if(players.needsUpdate()){
				updateList();		
			}
			else if(players.load()){
				updateList();			
			}
			
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void updateList(){
		/*System.out.println(currentList.getList());
		System.out.println(players.checkOnline(currentList.getList()));
		System.out.println(playerList);*/
		playerList.setListData(players.checkOnline(currentList.getList()));
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource().equals(type)){
			if(views.get(type.getSelectedIndex())!=null){
				currentList=(PlayerList)views.get(type.getSelectedIndex());
			}
			updateList();
		}
		else if(ae.getSource().equals(chatWith)){
			gui.communications.PMConversation(((Player)playerList.getSelectedValue()).name);
		}
		else if(ae.getSource().equals(stats)){
			if(playerList.getSelectedValue()!=null){
				new StatsDialog(gui,(Player)playerList.getSelectedValue());
			}
		}
	}
}
