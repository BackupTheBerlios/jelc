
package bots.deadface2;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Uptime implements BotCommand {
MyBot2 bot;
long start;
	Uptime(MyBot2 bot){
		this.bot=bot;
		start=System.currentTimeMillis();
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(String name, String[] args, int type) {
		if(args[0].equalsIgnoreCase("up")||args[0].equalsIgnoreCase("uptime")){
			//bot.reply(name,MyBot2.timeSince("Current Uptime: ",start),type);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {
		// TODO Auto-generated method stub

	}
	
	public static void main (String[] args){
		long time=System.currentTimeMillis();
		try{
			Thread.sleep(1200);
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}
		//System.out.println(MyBot2.timeSince("hdr",time));
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
