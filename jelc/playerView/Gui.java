/*
 * Created on 1/10/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package playerView;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
 * @author me
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Gui extends JFrame implements Runnable{
	PlayersOnline players;
	JSplitPane jsp;
	JComboBox type; 
	Vector views;
	JList playerList;
	Thread updateThread;
	Player player;
	PlayerList currentList;
	Gui(){
		super("Dns's and Brom's Player List");
		views=new Vector();
		
		
		buildList();
		
		
		type=new JComboBox(views);
		type.addActionListener(new typeAction());
		//playerList=new JList((Vector)views.get(0));
		playerList=new JList();
		playerList.addListSelectionListener(new listListener());
		
	
		JScrollPane scroll =new JScrollPane(playerList);
		scroll.setPreferredSize(new Dimension(70,50));
		scroll.setMaximumSize(new Dimension(170,50));
		
		JPanel side=new JPanel(new BorderLayout());
		side.add(type, BorderLayout.NORTH);
		side.add(scroll,BorderLayout.CENTER);
		JButton update=new JButton("update");
		update.addActionListener(new typeAction());
		side.add(update, BorderLayout.SOUTH);
		jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,side,new JLabel("Select a player from the scroll bar"));
		this.setSize(450,410);
		this.getContentPane().add(jsp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		updateThread=new Thread(this);
		updateThread.start();
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new Gui();
	}
	
	
	private class listListener implements ListSelectionListener, ActionListener, Runnable{
		//static boolean needsUpdate;

		JPanel pan;
		public void valueChanged(ListSelectionEvent lse) {
			player=(Player)playerList.getSelectedValue();
			doUpdate();
		}
		public void actionPerformed(ActionEvent ae){
			doUpdate();
		}
		public synchronized  void updatePanel(){
			if(player!=null){
				if(player.updated()){
					pan=new JPanel(new GridLayout(25,1));
					JLabel tmp=new JLabel("Player:");
					Font font1=tmp.getFont().deriveFont(Font.BOLD,tmp.getFont().getSize2D()+1);
					tmp.setFont(font1);
					pan.add(tmp);
					tmp=new JLabel(player.name);
					Font font2=tmp.getFont();
					tmp.setFont(font2.deriveFont(Font.BOLD,(float)font2.getSize2D()+6));
					pan.add(tmp);
					
				   //pan.add(new JLabel());//Space
					
					tmp=new JLabel("Physique:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getPhysique()+""));
		
					tmp=new JLabel("Coordination:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getCoordination()+""));
		
					tmp=new JLabel("Reasoning:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getReasoning()+""));
		
					tmp=new JLabel("Will:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getWill()+""));
		
					tmp=new JLabel("Instinct:");
					tmp.setFont(font1);
					pan.add(tmp);	
					pan.add(new JLabel(player.getInstinct()+""));
		
					tmp=new JLabel("Vitality:");
					tmp.setFont(font1);
					pan.add(tmp);	
					pan.add(new JLabel(player.getVitality()+""));
		
					pan.add(new JLabel());//Space
					pan.add(new JLabel());//Space
		
					tmp=new JLabel("Attack:");
					tmp.setFont(font1);
					pan.add(tmp);	
					pan.add(new JLabel(player.getAttack()+""));
		
					tmp=new JLabel("Defense:");
					tmp.setFont(font1);
					pan.add(tmp);	
					pan.add(new JLabel(player.getDefense()+""));
					
					tmp=new JLabel("Harvesting:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getHarvest()+""));
					
					tmp=new JLabel("Alchemy:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getAlchemy()+""));

					tmp=new JLabel("Magic:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getMagic()+""));
					
					tmp=new JLabel("Potion:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getPotion()+""));
						
					tmp=new JLabel("Summoning:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getSummoning()+""));
					
					tmp=new JLabel("Manufacture:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getManufacture()+""));
					
					tmp=new JLabel("Crafting:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getCrafting()+""));
		
					pan.add(new JLabel());//Space
					pan.add(new JLabel());//Space
					tmp=new JLabel("Overall:");
					tmp.setFont(font1);
					pan.add(tmp);	
					pan.add(new JLabel(player.getOverall()+""));

					tmp=new JLabel("Pick Points:");
					tmp.setFont(font1);
					pan.add(tmp);	
					pan.add(new JLabel(player.getPickPoints()+""));
					
					tmp=new JLabel("Carry:");
					tmp.setFont(font1);
					pan.add(tmp);	
					pan.add(new JLabel(player.getCarry()+""));
					

					tmp=new JLabel("Helth:");
					tmp.setFont(font1);
					pan.add(tmp);	
					pan.add(new JLabel(player.getHelth()+""));
					
					tmp=new JLabel("Mana:");
					tmp.setFont(font1);
					pan.add(tmp);	
					pan.add(new JLabel(player.getMana()+""));
					
					tmp=new JLabel("Combat Level:");
					tmp.setFont(font1);
					pan.add(tmp);
					pan.add(new JLabel(player.getCombatLevel()+""));//format this a little better later
		
					
					JButton button=new JButton("Update");
					button.addActionListener(this);
					
					JPanel base=new JPanel(new BorderLayout());
					base.add(pan,BorderLayout.CENTER);
					base.add(button,BorderLayout.SOUTH);
					jsp.setRightComponent(base);
				}else{
					jsp.setRightComponent(new JLabel("Error: it seems that the player doesn't exist"));
				}
			}
			else{
				jsp.setRightComponent(new JLabel("Select a player from the scroll bar"));
			}
		}
		public void doUpdate(){
			if(player!=null){
				jsp.setRightComponent(new JLabel("Retrieveing: "+player));
				System.out.println("retrieveing player: "+player);
				new Thread(this).start();
			}
		}
		public void run(){
			//System.out.println("status"+ player.update());
			player.update();
			updatePanel();
		}
	}
	
	private class typeAction implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			if(views.get(type.getSelectedIndex())!=null){
				currentList=(PlayerList)views.get(type.getSelectedIndex());
			}
			updateList();
		}
	}
	public void  buildList(){
		players= new PlayersOnline();
		currentList=new PlayerList("Online players",null);
		//views.add((Object)mask.getInstance());
		views.add(currentList);
		File f=new File("./lists/");
		System.out.println(f);
		File[] files=f.listFiles();
		for(int i=0;i<files.length;i++){
			if (files[i].isFile()&&files[i].canRead()){
				views.add(new PlayerList(files[i]));
			}
		}
	}
	public void run() {
		while(true){
			//System.out.println("doing a scheduled update");
			if(players.needsUpdate()){
				updateList();		
			}
			else if(players.load()){
				updateList();			
			}
			else{
//				System .out.println("not updateing");
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
		//playerList.setListData(((PlayerList)(views.get(type.getSelectedIndex()))).processList());
		playerList.setListData(players.checkOnline(currentList.getList()));
	}
}
