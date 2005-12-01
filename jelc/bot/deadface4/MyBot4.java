package bot.deadface4;

import elc.*;


import java.util.*;
import java.io.File;


import bot.deadface4.commands.*;





import playerView.*;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MyBot4{
public ClientConnection con;
public Chat chat;
Vector commands;
//String myName;
public PlayersOnline online;
public SeenList2 seen;
public Admin admin;
public Guild guild;
public Login login;
Log log;
Blacklist blacklist;

public IRC irc;
PkList pk;
public UsernameTable users;


SocketBackend socket;



boolean channelActive=true;
//public static boolean isIknow=false;



//public AccountManager accounts;


static final int LARGESTMESAGE=150;

public Config config;


	public MyBot4 (){
		/*try {
			config=new Config(new File("config.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		Config config=Config.getConfig();
		con=new ClientConnection(config.getUsername(), config.getPassword(), config.getHostname(), config.getPort());		
		chat=new Chat(this);
		//con.addClientListener(c);
		commands=new Vector();
		
		
		online=new PlayersOnline();
		this.addBotListener(new News(this));
		
		
		
		
		this.addBotListener(new JokerList(this,new File("jokerList.txt")));
		this.addBotListener(new Messenger(this));
		this.addBotListener(new Info(this));
		this.addBotListener(new Sing(this));
		this.addBotListener(new Time(this));
		this.addBotListener(new Debug(this));//debug
		
		this.addBotListener(new Hi(this));
		seen=new SeenList2(new File("seen.txt"),this);
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
		
		blacklist =new Blacklist(this);
		
		
		this.addBotListener(new Uptime(this));
		
		this.addBotListener(new Rox(this));
		
		
		this.users=new UsernameTable(this);
		this.addBotListener(users);
		
		
		pk=new PkList(this);
		this.addBotListener(pk);
		
		irc=new IRC(this);
		
		login=new Login(this);
		this.addBotListener(login);
		//socket=new SocketBackend(this, socketport);
		
		//con.start();
		
		//new Ding(con); //uncomment this to auto congratulate people when they say 'ding'
		

		this.addBotListener(new InterBot(this));
		
		
		//new Console(this);//std in/out backend
		
		
		
		
		//accounts=new AccountManager();
		AccountManager.getAccountManger();
		
		
		this.addBotListener(new Mail(this));
		
		
		con.connect();
		System.out.println("got here");
		
		
		
		
		/*try { //blocking call, it waits for the current thread to end before it can end (which it will not to because it is waiting)
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public static void main(String[] args) {
		new MyBot4();
		
    	/*if(args.length < 2){
    		System.out.println("usage: java MessengerBot user pass [server port]");
    		System.exit(1);
    	}
    	/*if(args.length == 4){
    		new MyBot4(args[0], args[1], args[2], Integer.valueOf(args[3]).intValue(),9994);
    	}
    	else if(args.length == 5){
    		new MyBot4(args[0], args[1], args[2], Integer.valueOf(args[3]).intValue(),Integer.valueOf(args[4]).intValue());
    	}
    	else {
    		new MyBot4(args[0], args[1],ClientConnection.DEFAULT_ADRESS,ClientConnection.DEFAULT_PORT,9994);	
    	}*/
	}


	
	public void processCommands(Replyer reply, String command ){
		new processThread(reply, command);
	}
	private void doProcessCommands(Replyer reply,String command){
		Account account=AccountManager.getAccountManger().getAccount(reply.getName());
		Session back=account.getSession();
		
		//System.out.println("Processing command from"+reply.getName()+":"+command);
		//reply.reply("GOT A COMMAND FROM YOU");
		if (back!=null){
			back.processSession(command);
		}
		else if(blacklist.isBlaclisted(reply.getName())){
			reply.reply("YOU ARE ON THE BLACKLIST, YOU CANNOT USE MY BOT");
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
				processHelp(reply);
				hasProcessed=true;
			}
			else{
				Iterator itr=commands.iterator();
				while(itr.hasNext()){
					BotCommand cmd=(BotCommand)itr.next();
					if(cmd.process(reply,tmp)){
						hasProcessed=true;
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
	private class processThread extends Thread{
		Replyer reply;
		String command;
		processThread(Replyer reply,String command){
			super();
			this.command=command;
			this.reply=reply;
			this.start();
		}
		public void run(){
			doProcessCommands(reply,command);
		}
	}


	
	
	

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