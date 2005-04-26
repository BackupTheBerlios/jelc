package elc.gui;
import javax.swing.*;

import java.awt.*;

import elc.*;

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
ActorPanel actor;

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
		
		actor=new ActorPanel(gui);
		this.add(actor,BorderLayout.SOUTH);
		
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
