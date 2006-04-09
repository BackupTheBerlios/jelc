package bot.deadface5;

import java.util.Iterator;

import elc.ClientInterface;
import elc.ClientConnection;

public class Chat implements ClientInterface{
ClientConnection con;
MyBot bot;
boolean channelActive=false;
	Chat(MyBot bot){
		this.bot=bot;
		this.con=bot.con;
		con.addClientListener(this);
	}
    public void onChat(String text) {
    	//System.out.println(Misc.StripColours(text));
    }
	public void onChat(String person, String message){
		/*
		if(isAdmin(person)){
			processCommands(person,message,0);
		}
    	else if((!person.equals(myName))&&(!person.equals("Tuxedo"))){
    		//processCommands(person,message,MyBot.); //ignoreing local
    		if(message.startsWith("hi")||message.startsWith("hello")||message.startsWith("gday")){
    		//	chat("hi "+person+", i'm a bot, pm me with 'Help' for instructions");
    		}
    	}*/
	}
	public void onChannelChat(String person, String message){
		if((!person.equals("Tuxedo"))&&(!person.equals("deadface"))&&(!person.equals("iknow"))){
			/*if (Config.getConfig().isIknow()){
				if(message.startsWith("i,")){
					bot.processCommands(new ChannelReplyer(person),message.substring(3));
				}
				else if(message.startsWith("i.")){
					bot.processCommands(new ChannelReplyer(person),message.substring(3));
				}
				else if(message.startsWith("know,")){
					bot.processCommands(new ChannelReplyer(person),message.substring(6));
				}
				else if(message.startsWith("know")){
					bot.processCommands(new ChannelReplyer(person),message.substring(5)); 	
				}
				else if(message.startsWith("iknow")){
					bot.processCommands(new ChannelReplyer(person),message.substring(6));    			
				}
				else if(message.startsWith("iknow,")){
					bot.processCommands(new ChannelReplyer(person),message.substring(7));    			
				}
				else{
					if(channelActive()){
						bot.processCommands(new ChannelReplyer(person),message);
					}
				}
			}
			else{
				if(message.startsWith("d, ")){
					bot.processCommands(new ChannelReplyer(person),message.substring(3));
				}
				else if(message.startsWith("d ")){
					bot.processCommands(new ChannelReplyer(person),message.substring(2));
				}
				else if(message.startsWith("dead, ")){
					bot.processCommands(new ChannelReplyer(person),message.substring(6));
				}
				else if(message.startsWith("dead ")){
					bot.processCommands(new ChannelReplyer(person),message.substring(5)); 	
				}
				else if(message.startsWith("deadface, ")){
					bot.processCommands(new ChannelReplyer(person),message.substring(10));    			
				}
				else if(message.startsWith("deadface ")){
					bot.processCommands(new ChannelReplyer(person),message.substring(9));    			
				}
				else{
					if(channelActive()){
						bot.processCommands(new ChannelReplyer(person),message);
					}
				}
			}*/
			
			for(Iterator itr=Config.getConfig().getPrefix().iterator();itr.hasNext();){
				String prefix=itr.next().toString();
				if(message.startsWith(prefix)){
					bot.processCommands(new ChannelReplyer(person),message.substring(prefix.length()));
					return;
				}
			}
			if(channelActive()){
				bot.processCommands(new ChannelReplyer(person),message);
			}
    		}
	}
	public void onPm(String person, String message){
	//	System.out.println("|"+person+"|"+message+"|");
		if(!message.equals("error your command is not valid pm me with help for commands.")){
	    	bot.processCommands(new PMReplyer(person),message);
		}
    }
	public void onGm(String person, String message){
		//System.out.println("sdfsdfsddfsddf");
		//System.out.println("|"+message.charAt(1)+" "+(int)message.charAt(1)+" "+DARK_BLUE);
		
		
		/*if((message.charAt(0)=)){
			message=message.substring(1);
			System.out.println(message);
		}*/
    	//System.out.println("person: |"+person+"|");
		/*if((!person.equals(myName))&&(!person.equals("iknow"))&&(!person.equals("Tuxedo"))&&(!person.equals("knoppy"))){
    		if(message.startsWith("k, ")){
    			processCommands(person,message.substring(3),MyBot.GM);
    		}//put more here if you want
		}*/
		
		message=Misc.StripColours(message);
		/*if (Config.getConfig().isIknow()){
			if((!person.equals("deadface"))&&(!person.equals("iknow"))&&(!person.equals("Tuxedo"))){
				if(message.startsWith("i,")){
					bot.processCommands(new GMReplyer(person),message.substring(3));
				}
				else if(message.startsWith("i.")){
					bot.processCommands(new GMReplyer(person),message.substring(3));
				}
				else if(message.startsWith("know,")){
					bot.processCommands(new GMReplyer(person),message.substring(6));
				}
				else if(message.startsWith("know")){
					bot.processCommands(new GMReplyer(person),message.substring(5)); 	
				}
				else if(message.startsWith("iknow")){
					bot.processCommands(new GMReplyer(person),message.substring(6));    			
				}
				else if(message.startsWith("iknow,")){
					bot.processCommands(new GMReplyer(person),message.substring(7));    			
				}
			}
		}
		else{
			if((!person.equals("deadface"))&&(!person.equals("iknow"))&&(!person.equals("Tuxedo"))){
				if(message.startsWith("d, ")){
					bot.processCommands(new GMReplyer(person),message.substring(3));
				}
				else if(message.startsWith("d ")){
					bot.processCommands(new GMReplyer(person),message.substring(2));
				}
				else if(message.startsWith("dead, ")){
					bot.processCommands(new GMReplyer(person),message.substring(6));
				}
				else if(message.startsWith("dead ")){
					bot.processCommands(new GMReplyer(person),message.substring(5)); 	
				}
				else if(message.startsWith("deadface, ")){
					bot.processCommands(new GMReplyer(person),message.substring(10));    			
				}
				else if(message.startsWith("deadface ")){
					bot.processCommands(new GMReplyer(person),message.substring(9));    			
				}
			}
		}*/
		for(Iterator itr=Config.getConfig().getPrefix().iterator();itr.hasNext();){
			String prefix=itr.next().toString();
			if(message.startsWith(prefix)){
				bot.processCommands(new GMReplyer(person),message.substring(prefix.length()));
				return;
			}
		}
		
		
	}
	

	/* (non-Javadoc)
	 * @see elc.ClientInterface#onPmSent(java.lang.String, java.lang.String)
	 */
	public void onPmSent(String person, String message) {
	}


	/* (non-Javadoc)
	 * @see elc.ClientInterface#onHint(java.lang.String)
	 */
	public void onHint(String message) {
		
	}


	/* (non-Javadoc)
	 * @see elc.ClientInterface#onSystemMessage(java.lang.String)
	 */
	public void onSystemMessage(String message) {
		
	}


	/* (non-Javadoc)
	 * @see elc.ClientInterface#onMinute(int)
	 */
	public void onMinute(int time) {
		
	}

	public boolean channelActive(){
		return channelActive;
	}
	public void activateChannel(){
		channelActive=true;
	}
	public void deactivateChannel(){
		channelActive=false;
	}	
	
	public Replyer getGMReplyer(){
		return new GMReplyer("iknow");
	}
	private class GMReplyer implements Replyer{
		String name;
		GMReplyer(String name){
			this.name=name;
		}

		public void reply(String str) {
			if(str.length()>getMaxMessageSize()){
				int index=0;
				
				String tmp=new String();
				while ((str.length()-index)>getMaxMessageSize()){
					tmp=str.substring(index,index+getMaxMessageSize()-5);
					con.chat("#gm "+tmp+" ..");
					index=index+getMaxMessageSize()-5;
				}
				con.chat("#gm "+str.substring(index));

			}
			else{
				con.chat("#gm "+str);
			}
			
			
			//System.out.println("sent a #Gm: "+str);
		}

		public int getType() {
			return Misc.GM;
		}

		public String getName() {
			return name;
		}

		public int getMaxMessageSize() {
			return 150;
		}

		public void flush() {
			// TODO Auto-generated method stub
			
		}

		public Account getAccount() {
			return AccountManager.getAccountManger().getAccount(name);
		}
	}
	private class ChannelReplyer implements Replyer{
		String name;
		ChannelReplyer(String name){
			this.name=name;
		}

		public void reply(String str) {
			con.chat("@ "+str);
		}

		public int getType() {
			return Misc.CHANNEL;
		}

		public String getName() {
			return name;
		}

		public int getMaxMessageSize() {
			return 150;
		}

		public void flush() {
			// TODO Auto-generated method stub
			
		}

		public Account getAccount() {
			return AccountManager.getAccountManger().getAccount(name);
		}
	}
	private class PMReplyer implements Replyer{
		String name;
		PMReplyer(String name){
			this.name=name;
		}

		public void reply(String str) {
			con.chatPm(name,str);
			//System.out.println("sent a pm: /"+name+" "+str);
		}

		public int getType() {
			return Misc.PM;
		}

		public String getName() {
			return name;
		}

		public int getMaxMessageSize() {
			return 150;
		}

		public void flush() {
			// TODO Auto-generated method stub
			
		}
		public Account getAccount() {
			return AccountManager.getAccountManger().getAccount(name);
		}
	}
	public void onIG(String message) {
		// TODO Auto-generated method stub
		
	}
}
