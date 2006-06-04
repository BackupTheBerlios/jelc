package bot.deadface6;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import bot.deadface6.backends.BackendManager;
import bot.deadface6.backends.Chat;
//import bot.deadface6.backends.IRC;
import bot.deadface6.backends.Pipe;
import bot.deadface6.backends.SocketBackend;
import bot.deadface6.commands.AccountSettings;
import bot.deadface6.commands.Admin;
import bot.deadface6.commands.BackendCommands;
import bot.deadface6.commands.CacheMe;
import bot.deadface6.commands.Debug;
import bot.deadface6.commands.Fortune;
import bot.deadface6.commands.Friends;
import bot.deadface6.commands.GuildCommand;
import bot.deadface6.commands.Hi;
import bot.deadface6.commands.IG;
import bot.deadface6.commands.Info;
import bot.deadface6.commands.Inventory;
import bot.deadface6.commands.IrcRegister;
import bot.deadface6.commands.JokerList2;
import bot.deadface6.commands.Log;
import bot.deadface6.commands.Login;
import bot.deadface6.commands.Mail;
import bot.deadface6.commands.Messenger;
import bot.deadface6.commands.News2;
import bot.deadface6.commands.On;
import bot.deadface6.commands.Online;
import bot.deadface6.commands.Ping;
import bot.deadface6.commands.PkList;
import bot.deadface6.commands.Rox;
import bot.deadface6.commands.ShellExec;
import bot.deadface6.commands.Sing;
import bot.deadface6.commands.Stats;
import bot.deadface6.commands.Time;
import bot.deadface6.commands.Trinity;
import bot.deadface6.commands.Uptime;
import bot.deadface6.commands.WhoIs;
import bot.deadface6.services.ActorWatcher;
import bot.deadface6.services.MessageBroadcast;
import bot.deadface6.services.OnlineWatcher;
import bot.deadface6.services.TimeWatcher;
import bot.deadface6.backends.PipeManager;





//import jelc.playerView.*;

/**
 * @author dns
 *connection
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MyBot{
//public ClientConnection connection;
//public Chat chat;
Vector commands;
//String myName;
//public PlayersOnline online;
//public SeenList2 seen;
public Admin admin;
public GuildCommand guild;
public Login login;
Log log;
Blacklist blacklist;

//public IRC irc;
PkList pk;
//public UsernameTable users;


SocketBackend socket;



boolean channelActive=true;
//public static boolean isIknow=false;



//public AccountManager accounts;


static final int LARGESTMESAGE=150;

//public Config config;



//TimeWatcher time;


Queue processes=new LinkedList();
boolean running=true;


static MyBot bot;
	public MyBot (String[] args){
		bot=this;
		Config config=null;
		if(args.length==1){
			File f=new File(args[0]);
			if(f.isFile()&&f.canRead()){
				config=Config.getConfig(f);
			}
		}
		if(config==null){
			config=Config.getConfig();
		}
		
		
		
		
		
		TimeWatcher.getInstance().addTimeWatcher(new MessageBroadcast(),Config.getConfig().getBroadcastdelay(),true);
		
		//chat=new Chat();
		//connection.addClientListener(c);
		commands=new Vector();
		
		
		//online=new PlayersOnline();
		this.addBotListener(new News2());
		
		
		
		this.addBotListener(new Inventory());
		
		this.addBotListener(new JokerList2(this));
		this.addBotListener(new Messenger(this));
		this.addBotListener(new Info(this));
		this.addBotListener(new Sing(this));
		this.addBotListener(new Time());
		this.addBotListener(new Debug(this));//debug
		
		this.addBotListener(new Hi(this));
/*		seen=new SeenList2(new File("seen.txt"),this);
		this.addBotListener(seen);*/
		//Stats stats=new Stats(online, seen,this);
		this.addBotListener(new Stats(this));
		this.addBotListener(new CacheMe());
		
		guild=new GuildCommand(this);
		this.addBotListener(guild);
		On on=new On(this);
		this.addBotListener(on);
		this.addBotListener(new Online(this));
		
		this.addBotListener(new Fortune(this));
		this.addBotListener(new Friends(this));
		
		
		admin=new Admin(this);
		this.addBotListener(admin);

		log=new Log(this);
		
		blacklist =new Blacklist(this);
		
		
		this.addBotListener(new Uptime(this));
		
		this.addBotListener(new Rox(this));
		
		
		/*this.users=new UsernameTable(this);
		this.addBotListener(users);*/
		
		this.addBotListener(new IrcRegister());
		
		
		pk=new PkList(this);
		this.addBotListener(pk);
		
		
		login=new Login(this);
		this.addBotListener(login);
		
		
		this.addBotListener(new BackendCommands(this));
		//socket=new SocketBackend(this, socketport);
		
		//connection.start();
		
		//new Ding(connection); //uncomment this to auto congratulate people when they say 'ding'
		

		
		
		//new Console(this);//std in/out backend
		
		
		
		
		//accounts=new AccountManager();
		AccountManager.getAccountManger();
		
		
		this.addBotListener(new Mail(this));
		
		this.addBotListener(new AccountSettings(this));
		
		
		new OnlineWatcher();
		
		this.addBotListener(new WhoIs(this));
		
		new ActorWatcher();
		this.addBotListener(new Trinity());
		
		this.addBotListener(new IG(this));
		
		this.addBotListener(new Ping());
		
		
		
		
		this.addBotListener(new ShellExec(null));
		////////////////////////////////////////////////////////////////////////////////////
		
		
		
		new processThread();
		
		System.out.println("got here");
		
		
		
		BackendManager.getInstance().startAll();
		
		
		
		PipeManager.getInstance();
	}
	
	public static void main(String[] args) {
		new MyBot(args);
		
    	/*if(args.length < 2){
    		System.out.println("usage: java MessengerBot user pass [server port]");
    		System.exit(1);
    	}
    	/*if(args.length == 4){
    		new MyBot(args[0], args[1], args[2], Integer.valueOf(args[3]).intValue(),9994);
    	}
    	else if(args.length == 5){
    		new MyBot(args[0], args[1], args[2], Integer.valueOf(args[3]).intValue(),Integer.valueOf(args[4]).intValue());
    	}
    	else {
    		new MyBot(args[0], args[1],ClientConnection.DEFAULT_ADRESS,ClientConnection.DEFAULT_PORT,9994);	
    	}*/
	}


	
	public void processCommands(Replyer reply, String command ){
		processes.add(new command(reply, command));
	}
	private void doProcessCommands(Replyer reply,String command){
		
		//Account account=AccountManager.getAccountManger().getAccount(reply.getAccount().getName());
		Account account=reply.getAccount();
		if(!account.getSession().empty()){
			Session back=(Session)account.getSession().peek();
			back.processSession(command);
		}
		
		//System.out.println("Processing command from"+reply.getName()+":"+command);
		//reply.reply("GOT A COMMAND FROM YOU");
		/*if (back!=null){
			back.processSession(command);
		}*/
		/*else if(blacklist.isBlaclisted(reply.getName())){
			reply.reply("YOU ARE ON THE BLACKLIST, YOU CANNOT USE MY BOT");
		}*/
		else if(account.isBanned()){
			reply.reply("YOU ARE ON THE BLACKLIST, YOU CANNOT USE MY BOT");
		}
		else if(account.isBot()){
			//bot specific stuff here, call interbot and other bot stuff here
			
			
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
			if(tmp[0].equalsIgnoreCase("help")||tmp[0].equalsIgnoreCase("#help")||tmp[0].equalsIgnoreCase("\'help\'")){
				if((reply.getType()==Misc.IRCPM)||(reply.getType()==Misc.PM)){
					processHelp(reply);
					hasProcessed=true;
				}
				else{

					reply.reply("sorry this command is only avalable through pm's, ingame pm '/iknow help'");
					hasProcessed=true;
				}
				
			}
			else{
				Iterator itr=commands.iterator();
				while(itr.hasNext()){
					BotCommand cmd=(BotCommand)itr.next();
					if(cmd.process(reply,tmp)){
						hasProcessed=true;
						System.out.println(cmd+" was the correct command");
					}
				}
			}
			if((!hasProcessed)&&(reply.getType()!=Misc.GM)&&(!account.isBot())){
				reply.reply("error your command is not valid pm me with 'help' for commands.");
			}
		}
		account.save();
		reply.flush();
	}
	/**
	 * @param reply
	 */
	private void processHelp(Replyer reply) {
		reply.reply("My commands are:");
		Iterator itr=commands.iterator();
		while(itr.hasNext()){
			BotCommand cmd=(BotCommand)itr.next();
			cmd.sendHelp(reply);
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
	private class command {
		Replyer reply;
		String command;
		command(Replyer reply,String command){
			super();
			this.command=command;
			this.reply=reply;
		}
		public void run(){
			doProcessCommands(reply,command);
		}
	}
	
	private class processThread implements Runnable{
		Thread current;
		processThread(){
			current=new Thread(this);
			current.start();
		}
		
		public void run() {
			while(running){
				try{
					command c=(command)processes.poll();
					if(c!=null){
						c.run();
					}
					else{
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				catch(Exception e){
					log.write("-------------------------------------");
					log.write("Exception: "+e.getMessage());
					log.write("Cause: "+e.getCause());
					log.write("Sack trace:");
					StackTraceElement[] st=e.getStackTrace();
					for(int i=0;i<st.length;i++){
						log.write(st[i].getClassName()+": "+st[i].getMethodName()+"@"+st[i].getLineNumber());
					}
					log.write("-------------------------------------");
				}
			}
		}
		
	}
	

	/**
	 * call this when you want to quit, it calls the onQuit() for each command so they can save thair data
	 *
	 */
	public void quit(){
		running=false;
		Iterator itr=commands.iterator();
		while(itr.hasNext()){
			BotCommand cmd=(BotCommand)itr.next();
			cmd.onQuit();
		}
		BackendManager.getInstance().broadcast("Going Down!!!!!!!!111");
		BackendManager.getInstance().stopAll();
		System.exit(0);
	}
	public static MyBot getInstance(){
		return bot;
	}
	
}