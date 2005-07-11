package bots.deadface2;

import elc.*;
import f00f.net.irc.martyr.GenericAutoService;
import f00f.net.irc.martyr.IRCConnection;
import f00f.net.irc.martyr.InCommand;
import f00f.net.irc.martyr.State;
import f00f.net.irc.martyr.commands.MessageCommand;
import f00f.net.irc.martyr.replies.NamesEndReply;

import java.util.*;
import java.io.File;


import playerView.*;
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
/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MyBot2{
ClientConnection con;
Vector commands;
String myName;
PlayersOnline online;
SeenList2 seen;
Admin admin;
Guild guild;
Log log;
Blacklist blacklist;

IRC irc;
UsernameTable users;
static final int LOCAL=0;
static final int PM=1;
static final int GM=2;
static final int CHANNEL=3;
static final int IRC=4;

static final int LARGESTMESAGE=150;


static final char LIGHT_PINK=(char)127;
static final char LIGHT_ORANGE=(char)8364;
static final char LIGHT_YELLOW=(char)65533;
static final char LIGHT_GREEN=(char)8218;
static final char LIGHT_BLUE=(char)402;
static final char LIGHT_PURPLE=(char)8222;
static final char LIGHT_WHITE=(char)8230;
static final char RED=(char)8224;
static final char ORANGE=(char)8225;
static final char YELLOW=(char)710;
static final char GREEN=(char)8240;
static final char BLUE=(char)352;
static final char PURPLE=(char)8249;
static final char GREY=(char)338;
static final char DARK_RED=(char)65533;
static final char DARK_ORANGE=(char)381;
static final char DARK_YELLOW=(char)65533;
static final char DARK_GREEN=(char)65533;
static final char DARK_BLUE=(char)8216;
static final char DARK_PURPLE=(char)8217;
static final char DARK_GREY=(char)8220;
static final char DARKER_RED=(char)8221;
static final char DARKER_ORANGE=(char)8226;
static final char DARKER_YELLOW=(char)8211;
static final char DARKER_GREEN=(char)121;
static final char DARKER_BLUE=(char)732;



	MyBot2 (String name, String password, String adress, int port){
		con=new ClientConnection(name, password, adress, port);		
		Chat c=new Chat();
		con.addClientListener(c);
		commands=new Vector();
		
		
		online=new PlayersOnline();
		this.addBotListener(new News(this));
		
		
		
		
		this.addBotListener(new Help(this));
		this.addBotListener(new JokerList(this,new File("jokerList.txt")));
		this.addBotListener(new Messenger(this));
		this.addBotListener(new Info(this));
		this.addBotListener(new Sing(this));
		this.addBotListener(new Time(this));
		//this.addBotListener(new Debug(this));//debug
		
		this.addBotListener(new Hi(this));
		seen=new bots.deadface2.SeenList2(new File("seen.txt"),this);
		this.addBotListener(seen);
		//Stats stats=new Stats(online, seen,this);
		this.addBotListener(new Stats(online, seen,this));
		guild=new Guild(this);
		this.addBotListener(guild);
		On on=new On(online,seen,this);
		this.addBotListener(on);
		this.addBotListener(new Online(online,seen,this));
		
		this.addBotListener(new Fortune(this));
		this.addBotListener(new Friends(this));
		
		
		admin=new Admin(con,this);
		this.addBotListener(admin);
		log=new Log(this);
		this.addBotListener(log);
		
		blacklist =new Blacklist(this);
		
		
		this.addBotListener(new Uptime(this));
		
		this.addBotListener(new Rox(this));
		
		
		this.users=new UsernameTable(this);
		this.addBotListener(users);
		
		
		irc=new IRC(this);
		//con.start();
		
		con.connect();
	}
	
	public static void main(String[] args) {
    	if(args.length < 2){
    		System.out.println("usage: java MessengerBot user pass [server port]");
    		System.exit(1);
    	}
    	if(args.length == 4){
    		new MyBot2(args[0], args[1], args[2], Integer.valueOf(args[3]).intValue());
    	} else {
    		new MyBot2(args[0], args[1],ClientConnection.DEFAULT_ADRESS,ClientConnection.DEFAULT_PORT);	
    	}
	}

    public void reply(String name,String  message,int type){
    	//System.out.println(message.length()+message+message.length());
    	String tmp=new String();
    	if(message.length()>LARGESTMESAGE+name.length()){
    		tmp=message.substring(LARGESTMESAGE,message.length());
    		message=message.substring(0,LARGESTMESAGE)+"..";
    	}
    	if(type==MyBot2.LOCAL){
    		con.chat(message);
    	}
    	else if (type==MyBot2.PM){
    		con.chatPm("/"+name+" "+message);	
    	}
    	else if (type==MyBot2.GM){
    		con.chatGm(message);
    	}
    	else if(type==MyBot2.CHANNEL){
    		con.chatChannel(message);
    	}
    	else if(type==MyBot2.IRC){
    		irc.send((char)3+"3"+message+(char)3);
    	}
    	if(!tmp.equals("")){
    		reply(name, ".."+tmp,type);
    	}
    	
    }
	void processCommands(String name,String command, int type){
		new processThread(name, command, type);
	}
	private void doProcessCommands(String name,String command, int type){
		if(blacklist.isBlaclisted(name)){
			this.reply(name,"YOU ARE ON THE BLACKLIST, YOU CANNOT USE MY BOT",type);
		}
		else{
			
			
			Vector vec=new Vector(4);
			command=command.trim();
			int index=command.indexOf(" ");
			int startIndex=0;
			if(index==-1){
				index=0;
			}
			else{
				while(index!=-1){
					vec.add(command.substring(startIndex,index));
					startIndex=index+1;
					index=command.indexOf(" ",startIndex);
				}
			}
			vec.add(command.substring(startIndex,command.length()));
			String[] tmp=new String[vec.size()];
			for(int i=0;i<tmp.length;i++){
				tmp[i]=(String)vec.get(i);
			}
			/*BotCommand[] cmd=getBotListeners();
			for(int i=0;i<cmd.length;i++){
				cmd[i].process(name,tmp,type);
			}*/
			boolean hasProcessed=false;
			if(tmp[0].equalsIgnoreCase("help")){
				processHelp(name, type);
				hasProcessed=true;
			}
			else{
				Iterator itr=commands.iterator();
				while(itr.hasNext()){
					BotCommand cmd=(BotCommand)itr.next();
					if(cmd.process(name,tmp,type)){
						hasProcessed=true;
					}
				}
			}
			if((!hasProcessed)&&(type!=MyBot2.GM)){
				this.reply(name,"error your command is not valid pm me with help for commands.",type);
			}
		}
	}
	/**
	 * @param name
	 * @param type
	 */
	private void processHelp(String name, int type) {
		this.reply(name,"My commands are:",type);
		Iterator itr=commands.iterator();
		while(itr.hasNext()){
			BotCommand cmd=(BotCommand)itr.next();
			cmd.sendHelp(name, type);
		}
	}

	public void outputList(String person, int type, String header, Iterator i ){
		String res=header;
		String tmp;
		if(i.hasNext()){
			tmp=i.next().toString();
			if (res.length()+tmp.length()<150){
				res=res+tmp;
			}
			else{
				reply(person,res+".",type);
			}
			while(i.hasNext()){
				tmp=i.next().toString();
				if (res.length()+tmp.length()+person.length()<LARGESTMESAGE){
					res=res+", "+tmp;
				}
				else{
					reply(person,res+".",type);
					res=tmp;
				}
			}
			reply(person,res,type);	
		}
	}
	public void outputList(String person, int type, String header, Enumeration e ){
		String res=header;
		String tmp;
		if(e.hasMoreElements()){
			tmp=e.nextElement().toString();
			if (res.length()+tmp.length()<150){
				res=res+tmp;
			}
			else{
				reply(person,res+".",type);
			}
			while(e.hasMoreElements()){
				tmp=e.nextElement().toString();
				if (res.length()+tmp.length()+person.length()<LARGESTMESAGE){
					res=res+", "+tmp;
				}
				else{
					reply(person,res+".",type);
					res=tmp;
				}
			}
			reply(person,res,type);	
		}
	}
	public boolean addBotListener(BotCommand bot){
		return commands.add(bot);
	}
	public boolean removeBotListener(BotCommand bot){
		return commands.remove(bot);
	}
	public BotCommand[] getBotListeners(){
		return (BotCommand[])commands.toArray();
	}
	private class processThread extends Thread{
		String name;
		String command;
		int type;
		processThread(String name,String command, int type){
			super();
			this.name=name;
			this.command=command;
			this.type=type;
			this.start();
		}
		public void run(){
			doProcessCommands(name,command,type);
		}
	}
	private class Chat implements ClientInterface{
	    public void onChat(String text) {
	    	System.out.println(text);
	    }
		public void onChat(String person, String message){
			/*
			if(isAdmin(person)){
				processCommands(person,message,0);
			}
	    	else if((!person.equals(myName))&&(!person.equals("Tuxedo"))){
	    		//processCommands(person,message,MyBot2.); //ignoreing local
	    		if(message.startsWith("hi")||message.startsWith("hello")||message.startsWith("gday")){
	    		//	chat("hi "+person+", i'm a bot, pm me with 'Help' for instructions");
	    		}
	    	}*/
		}
		public void onChannelChat(String person, String message){
	    	if((!person.equals(myName))&&(!person.equals("Tuxedo"))&&(!person.equals("deadface"))&&(!person.equals("iknow"))){
	    		processCommands(person,message,MyBot2.CHANNEL);
	    	}
		}
		public void onPm(String person, String message){
		//	System.out.println("|"+person+"|"+message+"|");
	    	processCommands(person,message,MyBot2.PM);
	    }
		public void onGm(String person, String message){
			//System.out.println("sdfsdfsddfsddf");
			//System.out.println("|"+message.charAt(1)+" "+(int)message.charAt(1)+" "+DARK_BLUE);
			if((message.charAt(0)==DARK_BLUE)){
				message=message.substring(1);
				System.out.println(message);
			}
	    	//System.out.println("person: |"+person+"|");
			/*if((!person.equals(myName))&&(!person.equals("iknow"))&&(!person.equals("Tuxedo"))&&(!person.equals("knoppy"))){
	    		if(message.startsWith("k, ")){
	    			processCommands(person,message.substring(3),MyBot2.GM);
	    		}//put more here if you want
			}*/
	    	if((!person.equals(myName))&&(!person.equals("iknow"))&&(!person.equals("Tuxedo"))){
	    		if(message.startsWith("d, ")){
	    			processCommands(person,message.substring(3),MyBot2.GM);
	    		}
	    		else if(message.startsWith("d ")){
	    			processCommands(person,message.substring(2),MyBot2.GM);
	    		}
	    		else if(message.startsWith("dead, ")){
	        		processCommands(person,message.substring(6),MyBot2.GM);
	    		}
	    		else if(message.startsWith("dead ")){
	        		processCommands(person,message.substring(5),MyBot2.GM); 	
	    		}
	    		else if(message.startsWith("deadface, ")){
	        		processCommands(person,message.substring(10),MyBot2.GM);    			
	    		}
	    		else if(message.startsWith("deadface ")){
	        		processCommands(person,message.substring(9),MyBot2.GM);    			
	    		}
	       	}
			/*if((!person.equals(myName))&&(!person.equals("deadface"))&&(!person.equals("Tuxedo"))){
	    		if(message.startsWith("i,")){
	    			processCommands(person,message.substring(3),MyBot2.GM);
	    		}
	    		else if(message.startsWith("i.")){
	    			processCommands(person,message.substring(3),MyBot2.GM);
	    		}
	    		else if(message.startsWith("know,")){
	        		processCommands(person,message.substring(6),MyBot2.GM);
	    		}
	    		else if(message.startsWith("know")){
	        		processCommands(person,message.substring(5),MyBot2.GM); 	
	    		}
	    		else if(message.startsWith("iknow")){
	        		processCommands(person,message.substring(6),MyBot2.GM);    			
	    		}
	    		else if(message.startsWith("iknow,")){
	        		processCommands(person,message.substring(7),MyBot2.GM);    			
	    		}
	       	}*/
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
	}
	static String timeSince(String header,long time){
		long difference=System.currentTimeMillis()-time;
		Calendar c=Calendar.getInstance();
		//System.out.println(difference+" "+new Date(difference));
		c.setTimeInMillis(difference);
		int day=c.get(Calendar.DAY_OF_YEAR);
		int hour=c.get(Calendar.HOUR_OF_DAY);
		int minute=c.get(Calendar.MINUTE);
		int second=c.get(Calendar.SECOND);		
		if(day!=0){
			return header+" "+day+" Days, "+hour+" Hours, "+minute+" minutes and "+second+" seconds.";
		}
		else if(hour!=0){
			return header+" "+hour+" Hours, "+minute+" minutes and "+second+" seconds.";
		}
		else if(minute!=0){
			return header+" "+minute+" minutes and "+second+" seconds.";
		}
		else{
			return header+" "+second+" seconds.";
		}
	}//118800000
	/**
	 * call this when you want to quit, it calls the onQuit() for each command so they can save thair data
	 *
	 */
	public void quit(){
		Iterator itr=commands.iterator();
		while(itr.hasNext()){
			BotCommand cmd=(BotCommand)itr.next();
			cmd.onQuit();
		}
		con.quit();
	}
}
