/**
 * 
 */
package bots.deadface2;

import elc.ClientInterface;
import f00f.net.irc.martyr.IRCConnection;
import f00f.net.irc.martyr.replies.NamesEndReply;
import f00f.net.irc.martyr.services.AutoJoin;
import f00f.net.irc.martyr.services.AutoReconnect;
import f00f.net.irc.martyr.services.AutoRegister;
import f00f.net.irc.martyr.services.AutoResponder;
import f00f.net.irc.martyr.GenericAutoService;
import f00f.net.irc.martyr.InCommand;
import f00f.net.irc.martyr.State;
import f00f.net.irc.martyr.clientstate.*;
import f00f.net.irc.martyr.commands.MessageCommand;
import f00f.net.irc.martyr.commands.QuitCommand;


/**
 * @author dns
 *
 */
public class IRC implements BotCommand{
MyBot2 bot;
	private IRCConnection connection;
	private AutoReconnect autoReconnect;
	private Channel mainChannel;
	private ClientState clientState;

	boolean gmRedirect=false;
	/**
	 * 
	 */
	public IRC(MyBot2 bot) {
		this.bot=bot;
		clientState = new myClientState();
		connection = new IRCConnection( clientState );
		new AutoResponder( connection );
		new AutoRegister( connection, "deadface", "deadface__", "lnxbot" );
		autoReconnect = new AutoReconnect( connection );
		new AutoJoin( connection, "#lnx", null );
		new MessageMonitor(connection);
		autoReconnect.go( "irc.freenode.net", 6667 );
		f00f.net.irc.martyr.Debug.setDebugLevel( f00f.net.irc.martyr.Debug.FAULT );
		
		bot.con.addClientListener(new Chat());
		bot.addBotListener(this);
		
	}
	private class MessageMonitor extends GenericAutoService
	{

		public MessageMonitor( IRCConnection con )
		{
			super( con);

			enable();
		}

		public void updateCommand( InCommand command )
		{
			if( command instanceof MessageCommand )
			{
				MessageCommand msg = (MessageCommand)command;
				//System.out.println("dest: "+msg.getDest()+" message: "+msg.getMessage()+" source String: "+msg.getSourceString()+" source: "+msg.getSource());
				String message=msg.getMessage();
				String nick=msg.getSource().getNick();
				String person=bot.users.getGuildName(nick);
	    		if(message.startsWith("d, ")){
	    			preProcess(person,message.substring(3));
	    		}
	    		else if(message.startsWith("d ")){
	    			preProcess(person,message.substring(2));
	    		}
	    		else if(message.startsWith("dead, ")){
	    			preProcess(person,message.substring(6));
	    		}
	    		else if(message.startsWith("dead ")){
	    			preProcess(person,message.substring(5)); 	
	    		}
	    		else if(message.startsWith("deadface, ")){
	    			preProcess(person,message.substring(10));    			
	    		}
	    		else if(message.startsWith("deadface ")){
	    			preProcess(person,message.substring(9));    			
	    		}
	    		else if(message.startsWith("'")){
	    			preProcess(person,message.substring(1));    			
	    		}
	    		else if(message.startsWith("#gm ")){
	    			preProcess(person,message);    			
	    		}
				//processCommands(msg.getSource().getUser(),msg.getMessage(),IRC);
				//justin.incomingMessage( msg );
			}
			else if( command instanceof NamesEndReply )
			{
				//justin.printMembers();
			}
		}
		

		protected void updateState( State state )
		{
			/*if( state == State.UNCONNECTED && justin.shouldQuit() )
				System.exit(0);*/
		}
	}
	private void preProcess(String nick, String message){
//		if(!(nick.equalsIgnoreCase("deadface")||nick.equalsIgnoreCase("deadface_")||nick.equalsIgnoreCase("knoppy")||nick.equalsIgnoreCase("knoppy_")||nick.equalsIgnoreCase("iknow")||nick.equalsIgnoreCase("iknow_"))){
		if(nick==null){
			send("it appears you are not registered with the bot, you need to be a guild member and pm me ingame with 'ircRegister ircUsername' to register so i know");
		}
		else{
			bot.processCommands(nick,message,MyBot2.IRC);
		}
	}
	private class myClientState extends ClientState
	{
		/**
		 * We want to use our own channel object, so we create it here.
		 */
		public void addChannel( String channel ){
			super.addChannel(channel);
			mainChannel=getChannel( channel );
			connection.sendCommand( new MessageCommand( mainChannel.getName(), "I'm UP" ) );
			/*gmRedirect=true;
			bot.reply("deadface","IRC/#GM system is now: ON",MyBot2.GM);
			bot.reply("deadface","comm system is now: ON",MyBot2.IRC);*/
		}

	}

	public void send(String message) {
		connection.sendCommand( new MessageCommand( mainChannel.getName(), message ) );
	}
	public boolean process(String name, String[] args, int type) {
		if(bot.admin.isAdmin(name)){
			if(args[0].equalsIgnoreCase("com")){
				if(gmRedirect){
					gmRedirect=false;
					bot.reply("deadface","comm system is now: OFF",MyBot2.IRC);
					bot.reply("deadface","IRC/#GM system is now: OFF",MyBot2.GM);
				}
				else{
					gmRedirect=true;
					bot.reply("deadface","IRC/#GM system is now: ON",MyBot2.GM);
					bot.reply("deadface","comm system is now: ON",MyBot2.IRC);
				}
			}
		}
		// TODO Auto-generated method stub
		return false;
	}
	public void sendHelp(String name, int type) {
	}
	public void onQuit() {
		if(gmRedirect){
			gmRedirect=false;
			bot.reply("deadface","comm system is now: OFF",MyBot2.IRC);
			bot.reply("deadface","IRC/#GM system is now: OFF",MyBot2.GM);
		}
		//bot.reply("deadface",MyBot2.RED+"goodbye all, "+MyBot2.DARK_GREY+"i'm dieing",MyBot2.GM);
		autoReconnect.disable();
		connection.sendCommand( new QuitCommand( "goind down" ) );
	}
	private class Chat implements ClientInterface{

		public void onChat(String text) {
			// TODO Auto-generated method stub
			
		}

		public void onChat(String person, String message) {
			// TODO Auto-generated method stub
			
		}

		public void onChannelChat(String person, String message) {
			// TODO Auto-generated method stub
			
		}

		public void onPm(String person, String message) {
			// TODO Auto-generated method stub
			
		}

		public void onPmSent(String person, String message) {
			// TODO Auto-generated method stub
			
		}

		public void onGm(String person, String message) {
			// TODO Auto-generated method stub
			if(gmRedirect){
				//if(person.equalsIgnoreCase("tuxedo"))||person.equalsIgnoreCase("tuxedo")+){
				String tmp=new String ();
				for(int i=0;i<message.length();i++){
					char c=message.charAt(i);
					if((int)c<127){
						tmp=tmp+c;
					}
				}
				
				
				send((char)3+"12"+"#GM "+person+": "+tmp+(char)3);
				//}
			}
			
		}

		public void onHint(String message) {
			// TODO Auto-generated method stub
			
		}

		public void onSystemMessage(String message) {
			// TODO Auto-generated method stub
			
		}

		public void onMinute(int time) {
			// TODO Auto-generated method stub
			
		}
	}
}
