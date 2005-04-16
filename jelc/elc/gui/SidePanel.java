package elc.gui;
import javax.swing.*;

import playerView.Player;
import playerView.PlayerList;
import playerView.PlayersOnline;

import java.awt.*;
import java.io.File;
import java.util.Vector;

import elc.*;
import playerView.*;
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
public class SidePanel extends JPanel implements SystemInterface {
GuiClient gui;
JLabel mapName;
JLabel time;

OnlineList on;


/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public SidePanel(GuiClient gui) {
		super(new BorderLayout());
		this.gui=gui;
		
		JPanel top=new JPanel(new GridLayout(2,1));
		mapName=new JLabel("Map: ");
		top.add(mapName);
		
		time=new JLabel("0:00");
		time.setFont(time.getFont().deriveFont(14));
		top.add(time);
		this.add(top,BorderLayout.NORTH);
		
		on=new OnlineList(gui);
		this.add(on,BorderLayout.CENTER);
		
		gui.getClientConnection().addSystemListener(this);
		
	}
	public void onMinute(int t) {
		if((t%60)<10){
			time.setText((t/60)+":0"+(t%60));
		}
		else{
			time.setText((t/60)+":"+(t%60));
		}
	}
	/* (non-Javadoc)
	 * @see elc.SystemInterface#onChangeMap(java.lang.String)
	 */
	public void onChangeMap(String map) {
		mapName.setText("Map: "+map);
	}

}
