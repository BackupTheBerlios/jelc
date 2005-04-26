package elc.gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import elc.Actor;
import elc.ActorInterface;
import elc.EnhancedActor;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ActorPanel extends JPanel implements ActorInterface {
GuiClient gui;
JList actorList;
JList enhancedActorList;
Vector actors;
Vector enhancedActor;
	/**
	 * 
	 */
	public ActorPanel(GuiClient gui) {
		super(new GridLayout(2,1));
		
		actors=new Vector();
		enhancedActor=new Vector();
		
		actorList =new JList();
		this.add(new JScrollPane(actorList));
		enhancedActorList=new JList();
		this.add(new JScrollPane(enhancedActorList));
		gui.getClientConnection().addActorInterface(this);
	}

	/* (non-Javadoc)
	 * @see elc.ActorInterface#onAddNewActor(elc.Actor)
	 */
	public void onAddNewActor(Actor a) {
		actors.add(a);
		actorList.setListData(actors);
	}

	/* (non-Javadoc)
	 * @see elc.ActorInterface#onRemoveActor(elc.Actor)
	 */
	public void onRemoveActor(Actor a) {
		if(a.isEnhanced()){
			enhancedActor.remove(a);
			actorList.setListData(actors);
		}
		else{
			actors.remove(a);
			actorList.setListData(actors);
		}
	}

	/* (non-Javadoc)
	 * @see elc.ActorInterface#onAddNewEnhancedActor(elc.Actor)
	 */
	public void onAddNewEnhancedActor(EnhancedActor a) {
		System.err.println("=="+a);
		enhancedActor.add(a);
		enhancedActorList.setListData(enhancedActor);
	}


}
