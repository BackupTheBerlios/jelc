/**
 * 
 */
package bot.deadface5;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import elc.ClientInterface;
import f00f.net.irc.martyr.IRCConnection;
import f00f.net.irc.martyr.replies.NamesEndReply;
import f00f.net.irc.martyr.services.AutoJoin;
import f00f.net.irc.martyr.services.AutoReconnect;
import f00f.net.irc.martyr.services.AutoRegister;
import f00f.net.irc.martyr.services.AutoResponder;
import f00f.net.irc.martyr.util.FullNick;
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
public class IRC implements BotCommand, Backend{
MyBot bot;
	private IRCConnection connection;
	private AutoReconnect autoReconnect;
	//private Channel mainChannel;
	private ClientState clientState;

	boolean gmRedirect=false;
	boolean ircCommand=true;
	
	boolean connected=false;
	Replyer defaultChannel;
	List channels=new Vector();
	/**
	 * 
	 */
	public IRC(MyBot bot) {
		this.bot=bot;
		clientState = new myClientState();
		connection = new IRCConnection( clientState );
		new AutoResponder( connection );
		new AutoRegister(connection,Config.getConfig().getIrcNick(),Config.getConfig().getIrcNick()+"_",Config.getConfig().getIrcName());
		autoReconnect = new AutoReconnect( connection );
		for(Iterator itr= Config.getConfig().getIrcChannels().iterator();itr.hasNext();){
			Config.Channel chan=(Config.Channel)itr.next();
			if(chan.getPassword()!=null){
				new AutoJoin( connection, chan.getChannel(),chan.getPassword());
			}
			else{
				new AutoJoin(connection,chan.getChannel());
			}
			if(defaultChannel==null){
				defaultChannel=new ircReplyer(null,chan.getChannel());
			}
		}
		
		
		new MessageMonitor(connection);
		autoReconnect.go(Config.getConfig().getIrcHostname(), Config.getConfig().getIrcPort() );
		f00f.net.irc.martyr.Debug.setDebugLevel( f00f.net.irc.martyr.Debug.FAULT );
		
		bot.con.addClientListener(new Chat());
		bot.addBotListener(this);
	}
	
	private void preProcess(Replyer reply, String message){
		if(reply.getAccount()==null){
			reply.reply("it appears you are not registered with the bot, you need to be a guild member and pm me ingame with 'ircRegister ircUsername' ");
		}
		else{
			bot.processCommands(reply,message);
		}
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
				String message=msg.getMessage();
				String nick=msg.getSource().getNick();
				Replyer reply;
				Account person=AccountManager.getAccountManger().getIRCAccount(nick);
				
				
				if(msg.getDest().equals(clientState.getNick().toString())){
					reply=new ircPmReplyer(person,msg.getSource());
				}
				else{
					reply=new ircReplyer(person,msg.getDest());
				}
				for(Iterator itr=Config.getConfig().getPrefix().iterator();itr.hasNext();){
					String prefix=itr.next().toString();
					if(message.startsWith(prefix)){
						preProcess(reply,message.substring(prefix.length()));
						break;
					}
					
				}
				if(message.startsWith("#gm ")){
					preProcess(reply,message);    			
				}
				else if(message.startsWith("#Gm ")){
					preProcess(reply,message);    			
				}
				else if(message.startsWith("#gM ")){
					preProcess(reply,message);    			
				}
				else if(message.startsWith("#GM ")){
					preProcess(reply,message);    			
				}
				else if(message.startsWith("#ig ")){
					preProcess(reply,message);    			
				}
				else if(message.startsWith("#Ig ")){
					preProcess(reply,message);    			
				}
				else if(message.startsWith("#iG ")){
					preProcess(reply,message);    			
				}
				else if(message.startsWith("#IG ")){
					preProcess(reply,message);    			
				}
	    			if(message.startsWith("'")){
	    				if(ircCommand){
	    					preProcess(reply,message.substring(1));
	    				}
	    			}
			}
			else if (command instanceof WelcomeCommand){
				String pass=Config.getConfig().getIrcPass();
				if(pass!=null){
					connection.sendCommand( new MessageCommand("NickServ", "IDENTIFY "+ pass) );
				}
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
			connection.sendCommand( new MessageCommand(channel,"I'm UP!" ) );
			/*Replyer reply=new ircReplyer(AccountManager.getAccountManger().getAccount(Config.),channel);
			reply.reply("I'm UP");
			/*gmRedirect=true;
			bot.reply("deadface","IRCNICK/#GM system is now: ON",MyBot.GM);
			bot.reply("deadface","comm system is now: ON",MyBot.IRC);*/
		}

	}

	/*public void send(String message) {
		if(connected){
			connection.sendCommand( new MessageCommand( mainChannel.getName(), message ) );
		}
		
	}*/
	public boolean process(Replyer reply, String[] args) {
		if(reply.getAccount().isSameGuild()){
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
		if(reply.getAccount().isSameGuild()){
			reply.reply("com           - turns IRCNICK/EL bridge on or off");
			
		}
	}
	public void onQuit() {
		if(gmRedirect){
			this.gmRedirectDeactivate();
		}
		//bot.reply("deadface",MyBot.RED+"goodbye all, "+MyBot.DARK_GREY+"i'm dieing",MyBot.GM);
		autoReconnect.disable();
		connection.sendCommand( new QuitCommand( "going down" ) );
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
		reply.reply("IRCNICK/#GM system is now: ON");
		//reply.reply("comm system is now: ON");
	}
	public void gmRedirectDeactivate(){
		gmRedirect=false;
		Replyer reply=bot.chat.getGMReplyer();
		//reply.reply("comm system is now: OFF");
		reply.reply("IRCNICK/#GM system is now: OFF");
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
				defaultChannel.reply((char)3+"12"+"#GM "+person+": "+Misc.StripColours(message)+(char)3);
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
			defaultChannel.reply((char)3+"11"+Misc.StripColours(message)+(char)3);
		}
	}

	private class ircReplyer implements Replyer{
		Account account;
		String channel;
		String message=(char)3+"3";
		ircReplyer(Account account,String channel){
			this.account=account;
			this.channel=channel;
		}
		public void reply(String str) {
			connection.sendCommand( new MessageCommand(channel,str ) );			
		}
		public int getType() {
			return Misc.IRC;
		}

		public int getMaxMessageSize() {
			return 510;
		}
		public void flush() {
			//not required, it sends per message
		}
		public Account getAccount() {
			//return AccountManager.getAccountManger().getAccount(name);
			return account;
		}
	}
	private class ircPmReplyer implements Replyer{
		/*String name;
		String channel;
		//String nick;
		 */
		Account account;
		FullNick source;
		/*ircPmReplyer(String name,String nick,String channel){
			this.name=name;
			this.channel=channel;
		//	this.nick=nick;
		}*/
		public ircPmReplyer(Account person2, FullNick source) {
			this.source=source;
			this.account=person2;
		}
		public void reply(String str) {
			connection.sendCommand( new MessageCommand(source,str ) );
			
		}
		public int getType() {
			// TODO Auto-generated method stub
			return Misc.IRCPM;
		}

		public int getMaxMessageSize() {
			return 510;
		}
		public void flush() {
			// TODO Auto-generated method stub
			
		}
		public Account getAccount() {
			//return AccountManager.getAccountManger().getAccount(name);
			return account;
		}
	}

	public String getBackendName() {
		return "IRC:";
	}

	public Replyer createReplyer(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

	public Replyer createReplyer(String s) {
		// TODO Auto-generated method stub
		return null;
	}
}
