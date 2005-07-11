/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bots.deadface2;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Sing implements BotCommand {
MyBot2 bot;
	Sing(MyBot2 bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(String name, String[] args, int type) {
		if(args[0].equalsIgnoreCase("sing")){
			/*bot.replyMessage(name,"100 buckets of bits on the bus",type);
			bot.replyMessage(name,"100 buckets of bits",type);
			bot.replyMessage(name,"Take one down, short it to ground",type);
			bot.replyMessage(name,"there would be FF buckets of bits on the bus",type);
			bot.replyMessage(name,"FF buckets of bits on the bus",type);
			//bot.replyMessage(name,"     ad infinitum...",type);
			return true;*/
			bot.reply(name,"roses are #FF0000",type);
			bot.reply(name,"violets are #0000FF",type);
			bot.reply(name,"all my base",type);
			bot.reply(name,"are belong to you",type);
			//bot.replyMessage(name,"     ad infinitum...",type);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {
		bot.reply(name,"sing          - let me sing to you",type);

	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
