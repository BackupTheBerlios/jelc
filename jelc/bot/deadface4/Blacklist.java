/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface4;

import bot.deadface4.commands.Person;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Blacklist implements BotCommand {
MyBot4 bot;
	public Blacklist(MyBot4 bot){
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		// TODO Auto-generated method stub
	}
	public boolean isBlaclisted(String name){
		Person p=bot.seen.find(name);
		if(p!=null){
			if(p.guild.equals("lol")){
				return true;
			}	
		}
		else if(name.equalsIgnoreCase("KnIgHt_PaLaDiN")){
			return true;
		}
		else if(name.equalsIgnoreCase("Jackthelad")){
			return true;
		}
		
		return false;
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
