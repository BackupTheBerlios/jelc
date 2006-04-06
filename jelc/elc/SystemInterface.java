/*
 * Created on 15/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package elc;

/**
 * Overwrite this interface then register it with clientConnection to recieve the actions
 * 
 *  @author dns
 */
public interface SystemInterface {
	/**
	 * this event is triggered when you change maps
	 * 
	 * @param map
	 */
	public abstract void onChangeMap(String map);
	
	/**
	 * this event is triggered when a new minute occurs.
	 * 
	 * @param time  the time of the 
	 */
	public abstract void onMinute(int time);
}
