/*
 * Created on 2/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package elc;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ClientInterface {
    /**
     * is caled when any message is recieved by the client
     * to be overwritten by subclasses
     * 
     * @param text  the contents of the message
     */
    public abstract void onChat(String text);
    
    /**
     * this method is called when a message is recieved in the local area
     * 
     * @param person  the person that sent the message
     * @param message  the message that was recieved
     */
    public abstract void onChat(String person, String message);
    
    /**
     * this method is called when a message is recieved on the current channel
     *
     * @param person  the person that sent the message
     * @param message  the message that was recieved
     */
    public abstract void onChannelChat(String person, String message);
    
    /**
     * this method is called when a message is recieved on the current channel
     *
     * @param person  the person that sent the message
     * @param message  the message that was recieved
     */
    public abstract void onPm(String person, String message);
    /**
     * this message is called when the message is sent to echo that the message has been recieved, if it isn't recieved correctly, a system message will be sent saying it failed
     * 
     * @param message the message sent
     */
    public abstract void onPmSent(String person, String message);
    
    /**
     * this method is called when a #gm (guild message) message is recieved
     *
     * @param person  the person that sent the message
     * @param message  the message that was recieved
     */
    public abstract void onGm(String person, String message);
    /**
     * this is recieved when a 'hint' message is recieved, for when the player is new to the game
     * 
     * @param message the message recieved
     */
    public abstract void onHint(String message);
    
    /**
     * this is called when a message as been recieved and does not fit any of the above patterns 
     * 
     * @param message
     */
    public abstract void onSystemMessage(String message);
    
    public abstract void onMinute(int time);
    public abstract void onIG(String message);
}
