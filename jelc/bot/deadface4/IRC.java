/**
 * 
 */
package bot.deadface4;

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
import f00f.net.irc.martyr.commands.WelcomeCommand;



/**
 * @author dns
 *
 */
public class IRC implements BotCommand{
MyBot4 bot;
	private IRCConnection connection;
	private AutoReconnect autoReconnect;
	//private Channel mainChannel;
	private ClientState clientState;

	boolean gmRedirect=false;
	boolean ircCommand=true;
	
	boolean connected=false;
	Replyer defautChannel=new ircReplyer("","#el-lnx");
	/**
	 * 
	 */
	public IRC(MyBot4 bot) {
		this.bot=bot;
		clientState = new myClientState();
		connection = new IRCConnection( clientState );
		new AutoResponder( connection );
		if (Config.getConfig().isIknow()){
			new AutoRegister( connection, "i-know", "iknow__", "i-know" );
		}
		else{
			new AutoRegister( connection, "deadface", "deadface__", "lnxbot" );
		}
		autoReconnect = new AutoReconnect( connection );
		//new AutoJoin( connection, "#lnx");
		new AutoJoin( connection, "#el-lnx","eternallinux" );
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
				//System.out.println("dest: "+msg.getDest()+" message: "+msg.getMessage()+" source String: "+msg.getSourceString()+" source: "+msg.getSource()+"|"+clientState.getName()+","+clientState.getUser()+","+clientState.getNick());
				String message=msg.getMessage();
				String nick=msg.getSource().getNick();
				Replyer reply;
				//System.out.println("|"+msg.getDest()+"="+clientState.getNick()+"|"+msg.getDest().equals(clientState.getNick().toString())+"|"+msg.getSource()+"|"+msg.getSource().getNick());
				String person=bot.users.getGuildName(nick);
				if(msg.getDest().equals(clientState.getNick().toString())){
					reply=new ircPmReplyer(person,nick,msg.getSource().getNick());
					//System.out.println("got pm message:"+nick+": "+message);
				}
				else{
					reply=new ircReplyer(person,msg.getDest());
					//System.out.println("got channel message("+msg.getDest()+": "+message);
				}
				if(nick==null){
					reply.reply("it appears you are not registered with the bot, you need to be a guild member and pm me ingame with 'ircRegister ircUsername' to register so i know");
				}
				else{
					if(Config.getConfig().isIknow()){
			    		if(message.startsWith("i, ")){
			    			bot.processCommands(reply,message.substring(3));
			    		}
			    		else if(message.startsWith("know, ")){
			    			bot.processCommands(reply,message.substring(6));
			    		}
			    		else if(message.startsWith("iknow ")){
			    			bot.processCommands(reply,message.substring(7)); 	
			    		}
			    		else if(message.startsWith("#gm ")){
			    			bot.processCommands(reply,message);    			
			    		}
			    		else if(message.startsWith("#Gm ")){
			    			bot.processCommands(reply,message);    			
			    		}
			    		else if(message.startsWith("#gM ")){
			    			bot.processCommands(reply,message);    			
			    		}
			    		else if(message.startsWith("#GM ")){
			    			bot.processCommands(reply,message);    			
			    		}
	
					}
					else{
						if(message.startsWith("d, ")){
							bot.processCommands(reply,message.substring(3));
			    		}
			    		else if(message.startsWith("d ")){
			    			bot.processCommands(reply,message.substring(2));
			    		}
			    		else if(message.startsWith("dead, ")){
			    			bot.processCommands(reply,message.substring(6));
			    		}
			    		else if(message.startsWith("dead ")){
			    			bot.processCommands(reply,message.substring(5)); 	
			    		}
			    		else if(message.startsWith("deadface, ")){
			    			bot.processCommands(reply,message.substring(10));    			
			    		}
			    		else if(message.startsWith("deadface ")){
			    			bot.processCommands(reply,message.substring(9));    			
			    		}
					}
		    		if(message.startsWith("'")){
		    			if(ircCommand){
		    				bot.processCommands(reply,message.substring(1));
		    			}
		    		}
				}
			//processCommands(msg.getSource().getUser(),msg.getMessage(),IRC);
				//justin.incomingMessage( msg );
			}
			else if( command instanceof NamesEndReply )
			{
				//justin.printMembers();
			}
			else if (command instanceof WelcomeCommand){
				connection.sendCommand( new MessageCommand("NickServ", "IDENTIFY dns123dns" ) );
			}
		}
		

		protected void updateState( State state )
		{
			/*if( state == State.UNCONNECTED && justin.shouldQuit() )
				System.exit(0);*/
		}
	}
	private class myClientState extends ClientState
	{
		/**
		 * We want to use our own channel object, so we create it here.
		 */
		public void addChannel( String channel ){
			super.addChannel(channel);//			connection.sendCommand(new MessageCommand("NickServ","identify dns123dns"));
			//mainChannel=getChannel( channel );
			connected=true;
			Replyer reply=new ircReplyer("",channel);
			reply.reply("I'm UP");
			/*gmRedirect=true;
			bot.reply("deadface","IRC/#GM system is now: ON",MyBot4.GM);
			bot.reply("deadface","comm system is now: ON",MyBot4.IRC);*/
		}

	}

	/*public void send(String message) {
		if(connected){
			connection.sendCommand( new MessageCommand( mainChannel.getName(), message ) );
		}
		
	}*/
	public boolean process(Replyer reply, String[] args) {
		if(bot.guild.isGuild(reply.getName())){
			if(args[0].equalsIgnoreCase("com")){
				if(gmRedirect){
					this.gmRedirectDeactivate();
				}
				else{
					this.gmRedirectActivate();
				}
				return true;
			}
		}
		// TODO Auto-generated method stub
		return false;
	}
	public void sendHelp(Replyer reply){
		if(bot.guild.isGuild(reply.getName())){
			reply.reply("com           - turns IRC/EL bridge on or off");
			
		}
	}
	public void onQuit() {
		if(gmRedirect){
			this.gmRedirectDeactivate();
		}
		//bot.reply("deadface",MyBot4.RED+"goodbye all, "+MyBot4.DARK_GREY+"i'm dieing",MyBot4.GM);
		autoReconnect.disable();
		connection.sendCommand( new QuitCommand( "goind down" ) );
	}
	public boolean isGmRedirect(){
		return this.gmRedirect;
	}
	public boolean isIrcCommand(){
		return ircCommand;
	}
	public void gmRedirectActivate(){
		gmRedirect=true;
		Replyer reply=bot.chat.getGMReplyer();
		reply.reply("IRC/#GM system is now: ON");
		reply.reply("comm system is now: ON");
	}
	public void gmRedirectDeactivate(){
		gmRedirect=false;
		Replyer reply=bot.chat.getGMReplyer();
		reply.reply("comm system is now: OFF");
		reply.reply("IRC/#GM system is now: OFF");
	}
	public void ircCommandActivate(){
		ircCommand=true;
	}
	public void ircCommandDeactivate(){
		ircCommand=false;
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
				/*//if(person.equalsIgnoreCase("tuxedo"))||person.equalsIgnoreCase("tuxedo")+){
				String tmp=new String ();
				for(int i=0;i<message.length();i++){
					char c=message.charAt(i);
					if((int)c<127){
						tmp=tmp+c;
					}
				}
				
				
				send((char)3+"12"+"#GM "+person+": "+tmp+(char)3);
				//}
				
				
				*/
				defautChannel.reply((char)3+"12"+"#GM "+person+": "+Misc.StripColours(message)+(char)3);
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

		public void onIG(String message) {
			// TODO Auto-generated method stub
			
		}
	}

	private class ircReplyer implements Replyer{
		String name;
		String channel;
		String message=(char)3+"3";
		ircReplyer(String name,String channel){
			this.name=name;
			this.channel=channel;
		}
		public void reply(String str) {
	
			//connection.sendCommand( new MessageCommand( mainChannel.getName(), message ) );
			
			
			
			connection.sendCommand( new MessageCommand(channel,str ) );
			
			//System.out.println("replying|"+channel+"|"+str);
			//connection.sendCommand( new MessageCommand( channel, str ) );
			/*System.out.println("++++"+message+"|"+str);
			if((str.length()+message.length())>getMaxMessageSize()){
				connection.sendCommand( new MessageCommand( channel, message ) );
				message=(char)3+"3"+str;
			}
			else{
				message=message+str+"\r\n";
			}*/
			
		}
		public int getType() {
			return Misc.IRC;
		}
		public String getName() {
			return name;
		}
		public int getMaxMessageSize() {
			return 510;
		}
		public void flush() {
			/*if(!message.equals("")){
				connection.sendCommand( new MessageCommand( channel, message +(char)3) );
			}*/
			
		}
		public Account getAccount() {
			return AccountManager.getAccountManger().getAccount(name);
		}
	}
	private class ircPmReplyer implements Replyer{
		String name;
		String channel;
		String nick;
		ircPmReplyer(String name,String nick,String channel){
			this.name=name;
			this.channel=channel;
			this.nick=nick;
		}
		public void reply(String str) {
	
			//connection.sendCommand( new MessageCommand( mainChannel.getName(), message ) );
			
			//System.out.println("replying|"+channel+"|"+str);
			connection.sendCommand( new MessageCommand(channel,str ) );
			
		}
		public int getType() {
			// TODO Auto-generated method stub
			return Misc.IRCPM;
		}
		public String getName() {
			return name;
		}
		public int getMaxMessageSize() {
			return 510;
		}
		public void flush() {
			// TODO Auto-generated method stub
			
		}
		public Account getAccount() {
			return AccountManager.getAccountManger().getAccount(name);
		}
	}
}
