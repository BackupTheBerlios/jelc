/*
 * Created on 16/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package elc.gui;

import java.awt.*;

import javax.swing.*;

import playerView.*;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StatsDialog extends JDialog {
Player player;
JPanel pan;
	/**
	 * @param owner
	 * @throws java.awt.HeadlessException
	 */
	public StatsDialog(Frame f,Player p){
		super(f,"stats for: "+p.name);
		this.player=p;
		JPanel pan=new JPanel();
		pan.add(new JLabel("retrieveing"));
		this.getContentPane().add(pan);
		player.update();
		buildGui();
		this.setSize(new Dimension(300,400));
		this.setVisible(true);
	}
	public void buildGui(){
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
				//button.addActionListener(this);
				
				JPanel base=new JPanel(new BorderLayout());
				base.add(pan,BorderLayout.CENTER);
				base.add(button,BorderLayout.SOUTH);
				this.getContentPane().removeAll();
				this.getContentPane().add(base);
				
			}
		}
	}
}
