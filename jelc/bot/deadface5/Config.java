package bot.deadface5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;


public class Config {
	final static String USERNAME="USERNAME:";
	final static String PASSWORD="PASSWORD:";
	final static String PORT="PORT:";
	final static String HOSTNAME="HOSTNAME:";
	final static String ISIKNOW="ISIKNOW:";
	final static String DB="DB:";
	final static String DBCLASSNAME="DBCLASSNAME:";
	final static String MYGUILD="MYGUILD:";
	final static String PREFIX="PREFIX:";
	final static String PASSTHROUGH="PASSTHROUGH:";
	final static String NEWS="NEWS:";
	
	final static String IRCENABLED="IRCENABLED:";
	final static String IRCNICK="IRCNICK:";
	final static String IRCNAME="IRCNAME:";
	final static String IRCPASS="IRCPASS:";
	final static String IRCHOST="IRCHOST:";
	final static String IRCPORT="IRCPORT:";
	final static String IRCCHANNEL="IRCCHANNEL:";
	
	
	static Config config;
	private String username;
	private String password;
	private int port=2000;
	static File DEFAULTFILE = new File("config.txt");
	private String hostname="eternal-lands.network-studio.com";
	private String db="jdbc:derby:DB";
	private String dbClass="org.apache.derby.jdbc.EmbeddedDriver";
	private String myguild="LNX";
	
	private boolean iknow=false;

	private List prefix=new Vector();
	private List passthrough=new Vector();
	
	private String news;
	
	
	private boolean ircEnabled=true;
	private String ircNick;
	private String ircPass;	
	private String ircName;
	private String ircHostname="irc.freenode.net";
	private int ircPort=6667;
	
	private List ircChannels=new Vector();
	private Config(File f) throws FileNotFoundException,IOException{
		
		
		FileInputStream fis=new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		System.out.println("loading file:"+f);
		String line=br.readLine();
		while(line!=null){
			if((line.equals(""))|line.startsWith("#")){
				//standard unix commet, ie starts with a #
				//blank lines are also ignored
			}
			else if(line.startsWith(USERNAME)){
				setUsername(line.substring(USERNAME.length()));
			}
			else if(line.startsWith(PASSWORD)){
				setPassword(line.substring(PASSWORD.length()));
			}
			else if(line.startsWith(HOSTNAME)){
				this.setHostname(line.substring(HOSTNAME.length()));
			}
			else if(line.startsWith(PORT)){
				try{
				this.setPort(Integer.parseInt(line.substring(PORT.length())));
				}
				catch (NumberFormatException nfe){
					System.err.println("Error parseing port string: "+nfe.getLocalizedMessage());
				}
			}
			else if(line.startsWith(ISIKNOW)){
				String tmp=line.substring(ISIKNOW.length());
				if(tmp.equalsIgnoreCase("true")){
					iknow=true;
				}
				else{
					iknow=false;
				}
			}
			else if(line.startsWith(MYGUILD)){
				setMyGuild(line.substring(MYGUILD.length()));
			}
			else if(line.startsWith(DB)){
				this.setDB(line.substring(DB.length()));
			}
			else if(line.startsWith(DBCLASSNAME)){
				this.setClassString(line.substring(DBCLASSNAME.length()));
			}
			else if(line.startsWith(PREFIX)){
				getPrefix().add(line.substring(PREFIX.length()));
			}
			else if(line.startsWith(PASSTHROUGH)){
				getPassthrough().add(line.substring(PASSTHROUGH.length()));
			}
			else if(line.startsWith(NEWS)){
				setNews(line.substring(NEWS.length()));
			}
			
			else if(line.startsWith(IRCENABLED)){
				String tmp=line.substring(IRCENABLED.length());
				if(tmp.equalsIgnoreCase("true")){
					setIrcEnabled(true);
				}
				else{
					setIrcEnabled(false);
				}
			}
			else if(line.startsWith(IRCNICK)){
				this.ircNick=line.substring(IRCNICK.length());
			}
			else if(line.startsWith(IRCPASS)){
				this.ircPass=line.substring(IRCPASS.length());
			}
			else if(line.startsWith(IRCNAME)){
				this.setIrcName(line.substring(IRCNAME.length()));
			}
			else if(line.startsWith(IRCHOST)){
				this.ircHostname=line.substring(IRCHOST.length());
			}
			else if(line.startsWith(IRCPORT)){
				try{
					setIrcPort(Integer.parseInt(line.substring(IRCPORT.length())));
					}
					catch (NumberFormatException nfe){
						System.err.println("Error parseing port string: "+nfe.getLocalizedMessage());
					}
			}
			else if(line.startsWith(IRCCHANNEL)){
				String tmp=line.substring(IRCCHANNEL.length());
				int index=tmp.indexOf(' ');
				if(index!=-1){//format is IRCCHANNEL:channel password
					this.getIrcChannels().add(new Channel(tmp.substring(0,index),tmp.substring(index+1)));
				}//format is IRCCHANNEL:channel
				else{
					this.getIrcChannels().add(new Channel(tmp,null));
				}
			}
			
			
			
			else{
				System.err.println("Error parseing config line: "+line);
			}
			
			line=br.readLine();
		}
	}
	Config(){
		
	}
	
	public boolean save(){
		File f=new File("config.txt");
		try {
			PrintWriter out=new PrintWriter(f);
			
			out.println(USERNAME+username);
			out.println(PASSWORD+password);
			out.println(PORT+getPort());
			out.println(HOSTNAME+getHostname());
			out.println(MYGUILD+getMyGuild());
			if(isIknow()){
				out.println(ISIKNOW+isIknow());
			}
			
			out.println();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param hostname The hostname to set.
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	/**
	 * @return Returns the hostname.
	 */
	public String getHostname() {
		return hostname;
	}
	/**
	 * @param iknow The iknow to set.
	 */
	public void setIknow(boolean iknow) {
		this.iknow = iknow;
	}
	/**
	 * @return Returns the iknow.
	 */
	public boolean isIknow() {
		return iknow;
	}
	public static Config getConfig(){
		if(config==null){
			try {
				config=new Config(DEFAULTFILE);
				return config;
			} catch (FileNotFoundException e) {
				System.err.println("error loading config file '"+DEFAULTFILE+"': "+e.getLocalizedMessage());
			} catch (IOException e) {
				System.err.println("error loading config: "+e.getLocalizedMessage());
			}
			
			
		}
		return config;
	}
	public static Config getConfig(File f){
		if(config==null){
			try {
				config=new Config(f);
				return config;
			} catch (FileNotFoundException e) {
				System.err.println("error loading config file '"+DEFAULTFILE+"': "+e.getLocalizedMessage());
			} catch (IOException e) {
				System.err.println("error loading config: "+e.getLocalizedMessage());
			}
			
			
		}
		return config;
	}
	/**
	 * @param db The db to set.
	 */
	public void setDB(String db) {
		this.db = db;
	}
	/**
	 * @return Returns the db.
	 */
	public String getDB() {
		return db;
	}
	/**
	 * @param dbClass The dbClass to set.
	 */
	public void setClassString(String dbDriver) {
		this.dbClass = dbDriver;
	}
	/**
	 * @return Returns the dbClass.
	 */
	public String getClassString() {
		return dbClass;
	}
	/**
	 * @param myguild The myguild to set.
	 */
	public void setMyGuild(String myguild) {
		this.myguild = myguild;
	}
	/**
	 * @return Returns the myguild.
	 */
	public String getMyGuild() {
		return myguild;
	}
	/**
	 * @param prefix The prefix to set.
	 */
	public void setPrefix(List prefix) {
		this.prefix = prefix;
	}
	/**
	 * @return Returns the prefix.
	 */
	public List getPrefix() {
		return prefix;
	}
	/**
	 * @param passthrough The passthrough to set.
	 */
	public void setPassthrough(List passthrough) {
		this.passthrough = passthrough;
	}
	/**
	 * @return Returns the passthrough.
	 */
	public List getPassthrough() {
		return passthrough;
	}
	/**
	 * @param news The news to set.
	 */
	public void setNews(String news) {
		this.news = news;
	}
	/**
	 * @return Returns the news.
	 */
	public String getNews() {
		return news;
	}
	/**
	 * @param ircNick The ircNick to set.
	 */
	public void setIrcNick(String irc) {
		this.ircNick = irc;
	}
	/**
	 * @return Returns the ircNick.
	 */
	public String getIrcNick() {
		return ircNick;
	}
	/**
	 * @param ircPass The ircPass to set.
	 */
	public void setIrcPass(String ircPass) {
		this.ircPass = ircPass;
	}
	/**
	 * @return Returns the ircPass.
	 */
	public String getIrcPass() {
		return ircPass;
	}
	/**
	 * @param ircName The ircName to set.
	 */
	public void setIrcName(String ircName) {
		this.ircName = ircName;
	}
	/**
	 * @return Returns the ircName.
	 */
	public String getIrcName() {
		return ircName;
	}
	/**
	 * @param ircHostname The ircHostname to set.
	 */
	public void setIrcHostname(String ircHostname) {
		this.ircHostname = ircHostname;
	}
	/**
	 * @return Returns the ircHostname.
	 */
	public String getIrcHostname() {
		return ircHostname;
	}
	/**
	 * @param ircPort The ircPort to set.
	 */
	public void setIrcPort(int ircPort) {
		this.ircPort = ircPort;
	}
	/**
	 * @return Returns the ircPort.
	 */
	public int getIrcPort() {
		return ircPort;
	}
	/**
	 * @param ircChannels The ircChannels to set.
	 */
	public void setIrcChannels(List ircChannels) {
		this.ircChannels = ircChannels;
	}
	/**
	 * @return Returns the ircChannels.
	 */
	public List getIrcChannels() {
		return ircChannels;
	}
	/**
	 * @param ircEnabled The ircEnabled to set.
	 */
	public void setIrcEnabled(boolean ircEnabled) {
		this.ircEnabled = ircEnabled;
	}
	/**
	 * @return Returns the ircEnabled.
	 */
	public boolean isIrcEnabled() {
		return ircEnabled;
	}
	public class Channel{
		private String channel;
		private String password;
		Channel(String channel,String password ){
			this.setChannel(channel);
			this.setPassword(password);
			
			System.out.println("|"+channel+"|"+password+"|");
			
		}
		/**
		 * @param password The password to set.
		 */
		public void setPassword(String password) {
			this.password = password;
		}
		/**
		 * @return Returns the password.
		 */
		public String getPassword() {
			return password;
		}
		/**
		 * @param channel The channel to set.
		 */
		public void setChannel(String channel) {
			this.channel = channel;
		}
		/**
		 * @return Returns the channel.
		 */
		public String getChannel() {
			return channel;
		}
	}
}
