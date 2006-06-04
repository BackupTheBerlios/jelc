package bot.deadface6.backends;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

import f00f.net.irc.martyr.commands.MessageCommand;
import f00f.net.irc.martyr.commands.WelcomeCommand;

import bot.deadface6.Account;
import bot.deadface6.AccountManager;
import bot.deadface6.Config;
import bot.deadface6.Replyer;
import bot.deadface6.MyBot;
import bot.deadface6.Config.Channels;

public class IRC2 implements Backend {
int status=Backend.STOPED;
IRCImlementation ircImpl;
//static IRC2 irc2;
static int numberOfConnections=0;

List<Channel> channels =new Vector();

Channel defaultchan;
boolean enabled;

String nick="deadface";
String password;
String hostname="irc.freenode.net";
int port=6667;

final static String NICK="NICK:";
final static String PASSWORD="PASSWORD:";
final static String HOSTNAME="HOSTNAME";
final static String PORT="PORT:";
final static String CHANNEL="CHANNEL:";
 
String id;
	public IRC2(String id){
		this.id=id;
		//irc2=this;
		
		//ircImpl=new IRCImlementation(Config.getConfig().getIrcNick());
		/*for(Iterator itr= Config.getConfig().getIrcChannels().iterator();itr.hasNext();){
			Config.Channels chan=(Config.Channels)itr.next();
			
			Channel c=new IRCChannel(chan.getChannel(),chan.getPassword());
			if(defaultchan==null){
				defaultchan=c;
			}
			
			channels.add(c);
		}*/
	}

	public Account getAccount(String name) {
		return AccountManager.getAccountManger().getIRCAccount(name);
	}

	public void start() {
		System.out.println("STARTING IRC BACKEND");
		System.out.println("Server:'"+hostname+":"+port+"' "+nick);
		
		try {
			status=Backend.STARTING;
			ircImpl=new IRCImlementation(nick);
			
			//ircImpl.setVerbose(true);
			ircImpl.connect(hostname,port);

			/////
			for (Iterator itr=channels.iterator();itr.hasNext();){
				IRCChannel c=(IRCChannel)itr.next();
				if(c.getPassword()!=null){
					ircImpl.joinChannel(c.getName(),c.getPassword());
				}
				else{
					ircImpl.joinChannel(c.getName());
				}
				System.out.println("Joined CHANNEL: "+c);
			}
			
		} catch (NickAlreadyInUseException e) {
			status=Backend.STOPED;
		} catch (IOException e) {
			status=Backend.STOPED;
		} catch (IrcException e) {
			status=Backend.STOPED;
		}
	}

	public void stop() {
		// TODO Auto-generated method stub
		status=Backend.STOPED;
		ircImpl.disconnect();
	}

	public Channel getDefairc2ultChannel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Channel getChannel(String name) {
		for (Iterator itr=channels.iterator();itr.hasNext();){
			Channel c=(Channel)itr.next();
			System.out.println("checking channel '"+c+"' compareing it to'"+name+"'");
			if(c.getChannelString().equalsIgnoreCase(name)){
				return c;
			}
		}
		
		return new IRCChannel(name,null);
	}

	public void broadcast(String message) {
		for (Iterator itr=channels.iterator();itr.hasNext();){
			Channel c=(Channel)itr.next();
			c.send(message);
		}
	}

	public int getStatus() {
		return status;
	}

	public List<Channel> getChannels() {
		return channels;
	}
	
	public String getName() {
		return "irc2-"+id;
	}
	
	/*public static IRC2 getInstance(){
		if(irc2==null){
			irc2=new IRC2();
		}
		return irc2;
	}*/
	
	public Channel getDefaultChannel() {
		return defaultchan;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private class IRCImlementation extends PircBot {
		public IRCImlementation(String nick) {
			this.setName(nick);
			this.changeNick(nick);
			if(password!=null){
				this.sendMessage("nickserv","identify "+password);
			}
		}
		public  void onDisconnect(){
			if(status!=Backend.STOPED){//reconnect if disconnected not if we did the dissconnection
			    try {
			        reconnect();
			    }
			    catch (Exception e) {
			        // Couldn't reconnect!
			        // Pause for a short while...?
			    }
			}
		}
		public  void onMessage(String channel, String sender, String login, String hostname,String message){
			System.out.println("MESSAGE: "+channel+" "+sender+" "+login+" "+hostname+" "+message);
			Account a=getAccount(sender);
				
			Channel c=getChannel(IRC2.this.getName()+":"+channel);
			c.recivededMessage("<"+sender+"> "+message);
			
			String error="it appears you are not registered with the bot, pm me ingame with 'ircRegister ircUsername' ";
			
			if(message.startsWith("@")){
				if(a!=null){
					//c.recivededMessage("("+a.getName()+") "+message.substring(1));
					Channel c2=BackendManager.getInstance().getChannel("el:@");
					if(c2!=null){
						c2.send("("+a.getName()+") "+message.substring(1,message.length()));
					}
				}
				else{
					this.sendMessage(channel,error);
				}
			}
			if(/*channel.equalsIgnoreCase("#el-lnx")*/ true){
				Replyer r=new ReplyerAdapter(c,a);
				System.out.println("REPLYER"+r+" "+c+" "+a+" "+a.isSameGuild());
				
				if(message.startsWith("#gm ")){
					if(a!=null){
						MyBot.getInstance().processCommands(r,message);
					}
					else{
						this.sendMessage(channel,error);
					}
				}
				else if(message.startsWith("#Gm ")){
					if(a!=null){
						MyBot.getInstance().processCommands(r,message);
					}
					else{
						this.sendMessage(channel,error);
					}
				}
				else if(message.startsWith("#gM ")){
					
					if(a!=null){
						MyBot.getInstance().processCommands(r,message);
					}
					else{
						this.sendMessage(channel,error);
					}

					MyBot.getInstance().processCommands(r,message);
				}
				else if(message.startsWith("#GM ")){
					if(a!=null){
						MyBot.getInstance().processCommands(r,message);
					}
					else{
						this.sendMessage(channel,error);
					}
				}
				else if(message.startsWith("#ig ")){
					if(a!=null){
						MyBot.getInstance().processCommands(r,message);
					}
					else{
						this.sendMessage(channel,error);
					}
				}
				else if(message.startsWith("#Ig ")){
					if(a!=null){
						MyBot.getInstance().processCommands(r,message);
					}
					else{
						this.sendMessage(channel,error);
					}
				}
				else if(message.startsWith("#iG ")){
					if(a!=null){
						MyBot.getInstance().processCommands(r,message);
					}
					else{
						this.sendMessage(channel,error);
					}
				}
				else if(message.startsWith("#IG ")){
					if(a!=null){
						MyBot.getInstance().processCommands(r,message);
					}
					else{
						this.sendMessage(channel,error);
					}
				}
				
			}
			if(message.startsWith("'")){
				if(a!=null){
					Replyer r=new ReplyerAdapter(c,a);
					MyBot.getInstance().processCommands(new ReplyerAdapter(c,a),message.substring(1));
					
					//System.out.println("PROCESS: "+r+" "+ message.substring(1));
				
				}
				else{
					this.sendMessage(channel,error);
				}
			}
		}
		protected void onPrivateMessage(String sender, String login, String hostname, String message){
			
			Channel c=getChannel(sender);
			if(message.startsWith("@")){
				c.recivededMessage("<"+sender+"> "+message);
			}
			if(message.startsWith("'")){
				Replyer r=new ReplyerAdapter(c,getAccount(sender));
				MyBot.getInstance().processCommands(r,message.substring(1));
				
				System.out.println("PROCESS: "+r+" "+ message.substring(1));
			}
			
		}
		
		
		
		protected void onJoin(String channel, String sender, String login, String hostname){
			status=Backend.READY;
		}
	}
	
	private class IRCChannel extends Channel{
		String channelName;
		String password;

		IRCChannel(String name,String password){
			this.channelName=name;
			this.password=password;
			System.out.println("Joined Channel: "+name);
		}
		
		@Override
		public String getName() {
			return channelName;
		}

		@Override
		public Replyer getReplyer() {
			return null;
		}

		@Override
		public String getChannelString() {
			
			return getBackend().getName()+":"+channelName;
		}

		@Override
		public void send(String message) {
			System.out.println("Sending message '"+message+"' to channel "+getChannelString()+"'"+channelName+"'");
			ircImpl.sendMessage(channelName,message);
		}

		@Override
		public void stop() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Backend getBackend() {
			return IRC2.this;
		}
		
		public String getPassword(){
			return password;
		}
	}
	private class IRCPMChannel extends Channel{
		String channelName;
		String password;

		IRCPMChannel(String name){
			this.channelName=name;
			this.password=password;
			System.out.println("Joined Channel: "+name);
		}
		
		@Override
		public String getName() {
			return channelName;
		}

		@Override
		public Replyer getReplyer() {
			return null;
		}

		@Override
		public String getChannelString() {
			return getBackend().getName()+":"+channelName;
		}

		@Override
		public void send(String message) {
			System.out.println("Sending message '"+message+"' to channel "+getChannelString()+" '"+channelName+"'");
			ircImpl.sendMessage(channelName,message);
		}

		@Override
		public void stop() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Backend getBackend() {
			return IRC2.this;
		}
		
	}
	public void configParameter(String line) {
		System.out.println("CONFIG PARAM:'"+line+"'");
		if(line.startsWith(NICK)){
			nick=line.substring(NICK.length()).trim();
		}
		else if(line.startsWith(PASSWORD)){
			password=line.substring(PASSWORD.length()).trim();
		}
		else if(line.startsWith(HOSTNAME)){
			hostname=line.substring(HOSTNAME.length()+1).trim();
		}
		else if(line.startsWith(PORT)){
			String tmp=line.substring(PORT.length()).trim();
			try{
				port=Integer.parseInt(tmp);
			}
			catch (NumberFormatException nfe){
				System.err.println("Error parseing port string: "+nfe.getLocalizedMessage());
			}
		}
		else if (line.startsWith(CHANNEL)){
			String tmp=line.substring(CHANNEL.length()).trim();
			int index=tmp.indexOf(' ');
			Channel c;
			if(index!=-1){//format is CHANNEL:channel password
				c=new IRCChannel(tmp.substring(0,index),tmp.substring(index+1));
			}//format is CHANNEL:channel
			else{
				c=new IRCChannel(tmp,null);
			}
			System.out.println("|||||"+tmp+"|");
			if(defaultchan==null){
				defaultchan=c;
			}
			
			channels.add(c);
		}
	}

	public boolean isEnabled() {
		return enabled;
	}


}
