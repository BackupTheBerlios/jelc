package bot.deadface6.backends;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import bot.deadface6.Account;
import bot.deadface6.AccountManager;
import bot.deadface6.Config;
import bot.deadface6.Misc;
import bot.deadface6.MyBot;
import bot.deadface6.Replyer;

import elc.ClientInterface;
import elc.ClientConnection;

public class Chat2 implements ClientInterface,Backend{
ClientConnection connection;
boolean channelActive=false;

List channels=new Vector();

GMReplyer gm;
LocalChannel local;
ChannelReplyer channel;
BroadcastChannel broadcast;

static Chat2 chat;
public final static String GMCHANNEL="el:gm";
public final static String LOCALCHANNEL="el:local";
public final static String BROADCAST="el:broadcast";

Vector pmChannels=new Vector();

int status=Backend.STOPED;
	public Chat2(){
		chat=this;
		Config config=Config.getConfig();
		System.out.println("Connectiong to server: "+Config.getConfig().getHostname()+":"+Config.getConfig().getPort());
		this.connection=new ClientConnection(config.getUsername(), config.getPassword(), config.getHostname(), config.getPort());
		connection.addClientListener(this);
		
		BackendManager.getInstance().addBackend(this);
		gm =new GMReplyer();
		local=new LocalChannel();
		channel=new ChannelReplyer();
		broadcast=new BroadcastChannel();
	}
    public void onChat(String text) {
    	//System.out.println(Misc.StripColours(text));
    	status=Backend.READY;
    	
    	
    	if(text.startsWith("#Message from")){
    		gm.recivededMessage((char)3+"3"+Misc.StripColours(text)+(char)3);
    	}
    	
    	
    	/*int index=text.indexOf("@ 1234]:");
    	if(index!=-1){
    		String name=text.substring(2,index-1);
    		String message=text.substring(index);
    		System.out.println("NAME '"+name+"' message '"+message+"'");
    	}*/
    }
	public void onChat(String person, String message){
    	local.recivededMessage(person+"| "+message);
	}
	public void onChannelChat(String person, String message){
		channel.recivededMessage("["+person+"] "+message);
		
		
		//System.out.println("MESSAGE FROM CHANNEL'"+person+"'"+message);
		
		/*if((!person.equals("Tuxedo"))&&(!person.equals("deadface"))&&(!person.equals("iknow"))){
			for(Iterator itr=Config.getConfig().getPrefix().iterator();itr.hasNext();){
				String prefix=itr.next().toString();
				if(message.startsWith(prefix)){
					MyBot.getInstance().processCommands(new ChannelReplyer(person),message.substring(prefix.length()));
					return;
				}
			}
			if(channelActive()){
				MyBot.getInstance().processCommands(new ChannelReplyer(person),message);
			}
		}*/
	}
	public void onPm(String name, String message){
		if(!message.equals("error your command is not valid pm me with help for commands.")){
	    	MyBot.getInstance().processCommands(getPmChannel(name),message);
		}
    }
	public void onGm(String name, String message){
		
		gm.recivededMessage((char)3+"12"+"#GM "+name+": "+Misc.StripColours(message)+(char)3);
		//gm.recivededMessage(name+": "+message);
		
		message=Misc.StripColours(message);
		for(Iterator itr=Config.getConfig().getPrefix().iterator();itr.hasNext();){
			String prefix=itr.next().toString();
			if(message.startsWith(prefix)){
				MyBot.getInstance().processCommands(gm,message.substring(prefix.length()));
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
	public void onIG(String message) {
		gm.recivededMessage((char)3+"11"+Misc.StripColours(message)+(char)3);
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
	

	public Account getAccount(String name) {
		return AccountManager.getAccountManger().getAccount(name);
	}
	public String getName() {
		return "el";
	}
	public void start() {
		status=Backend.STARTING;
		connection.connect();
		System.out.println("starting backend 'el'");
	}
	public void stop() {
		status=Backend.STOPED;
		connection.quit();
		
	}
	public List<Channel> getChannels() {
		return channels;
	}
	public Channel getDefaultChannel() {
		return gm;
	}
	public Channel getChannel(String name) {
		return new PMReplyer(name);
	}
	public static Chat2 getInstance(){
		if(chat==null){
			new Chat2();
		}
		return chat;
	}
	public ClientConnection getConnection(){
		return connection;
	}
	public void broadcast(String message) {
		// TODO Auto-generated method stub
		
	}
	private PMReplyer getPmChannel(String name){
		PMReplyer pm;
		for(Iterator itr=pmChannels.iterator();itr.hasNext();){
			pm=(PMReplyer)itr.next();
			if(pm.getName().equalsIgnoreCase(name)){
				return pm;
			}
		}
		pm=new PMReplyer(name);
		pmChannels.add(pm);
		return pm;
	}
	public void configParameter(String line) {
		
	}
	public int getStatus() {
		return status;
	}
	public boolean isEnabled() {
		return false;
	}
	private class LocalChannel extends Channel implements Replyer{
		LocalChannel(){
			channels.add(this);
			this.addMessageListener(new DebugMessageListener());
		}
	
		public String getName() {
			return "local";
		}
	
		
		public Replyer getReplyer() {
			return this;
		}
	
		@Override
		public String getChannelString() {
			return Chat2.LOCALCHANNEL;
		}
	
		@Override
		public void send(String message) {
			connection.chat(message);
		}
	
		@Override
		public void stop() {
			// TODO Auto-generated method stub
			
		}
	
		public void reply(String str) {
			connection.chat(str);
			
		}
	
		public int getType() {
			return -1;
		}
	
		public int getMaxMessageSize() {
			return 150;
		}
	
		public void flush() {
			// TODO Auto-generated method stub
			
		}
	
		public Account getAccount() {
			// TODO Auto-generated method stub
			return null;
		}
	
		public Channel getChannel() {
			return this;
		}
	
	
		@Override
		public Backend getBackend() {
			return chat;
		}
		
	}
	private class ChannelReplyer extends Channel implements Replyer{
		//int number;
		ChannelReplyer(){
			//this.number=number;
			channels.add(this);
		}
	
		public void reply(String str) {
			connection.chat("@"+" "+str);
		}
	
		public int getType() {
			return Misc.CHANNEL;
		}
	
		public String getName() {
			return "@";
		}
	
		public int getMaxMessageSize() {
			return 150;
		}
	
		public void flush() {
			// TODO Auto-generated method stub
			
		}
	
		public Account getAccount() {
			return null;
		}
	
		public Channel getChannel() {
			return this;
		}
	
		public Replyer getReplyer() {
			return this;
		}
	
		public String getChannelString() {
			return "el:@";
		}
	
		public void send(String message) {
			this.reply(message);
		}
	
		public void stop() {
			
		}
	
		@Override
		public Backend getBackend() {
			return chat;
		}
	}
	private class GMReplyer extends Channel implements Replyer{
		GMReplyer(){
			channels.add(this);
			//System.out.println("+++++++++++++++++++++++++++++++++");
		}
	
		public void reply(String str) {
			System.out.println("SOMETHING "+str);
			if(str.length()>getMaxMessageSize()){
				int index=0;
				
				String tmp=new String();
				while ((str.length()-index)>getMaxMessageSize()){
					tmp=str.substring(index,index+getMaxMessageSize()-5);
					connection.chat("#gm "+tmp+" ..");
					index=index+getMaxMessageSize()-5;
				}
				connection.chat("#gm "+str.substring(index));
	
			}
			else{
				connection.chat("#gm "+str);
			}
			
			
			//System.out.println("sent a #Gm: "+str);
		}
	
		public int getType() {
			return Misc.GM;
		}
	
		public String getName() {
			return "gm";
		}
	
		public int getMaxMessageSize() {
			return 150;
		}
	
		public void flush() {
			//noting required
		}
	
		public Account getAccount() {
			return null;
		}
	
		public Channel getChannel() {
			return this;
		}
	
		public 
		Replyer getReplyer() {
			return this;
		}
	
		public String getChannelString() {
			return "el:gm";
		}
	
		public @Override
		void send(String message) {
			reply(message);
		}
	
		public @Override
		void stop() {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public Backend getBackend() {
			return chat;
		}
	}
	private class PMReplyer extends Channel implements Replyer{
		String name;
		PMReplyer(String name){
			this.name=name;
		}
	
		public void reply(String str) {
			connection.chatPm(name,str);
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
	
		public Channel getChannel() {
			return this;
		}
	
		public Replyer getReplyer() {
			return this;
		}
	
		public @Override
		String getChannelString() {
			return "el:"+name;
		}
	
		public void send(String message) {
			this.reply(message.trim());
		}
	
		public @Override
		void stop() {
			
		}
	
		@Override
		public Backend getBackend() {
			return chat;
		}
	}
	private class BroadcastChannel extends Channel{
		BroadcastChannel(){
			channels.add(this);
		}
		

		@Override
		public String getName() {
			return "bc";
		}

		@Override
		public Replyer getReplyer() {
			return null;
		}

		@Override
		public String getChannelString() {
			return Chat2.BROADCAST;
		}

		@Override
		public void send(String message) {
			//cannot send
		}

		@Override
		public void stop() {
			//does nothing
		}

		@Override
		public Backend getBackend() {
			return chat;
		}
		
	}
}
