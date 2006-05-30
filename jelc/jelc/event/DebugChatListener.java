/*
 * Created on 12/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.event;

public class DebugChatListener implements ChannelListener{

	public DebugChatListener() {
		super();
	}

	public void chat(byte channel, String str) {
		System.out.println("CHAT: "+channel +" "+str);
	}


}
