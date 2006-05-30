/*
 * Created on 17/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.event;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ActorListener {
	public abstract void onAddNewActor(Actor a);
	public abstract void onRemoveActor(int id);
	public abstract void onAddNewEnhancedActor(EnhancedActor a);
}
