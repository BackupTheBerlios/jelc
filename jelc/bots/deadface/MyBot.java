package bots.deadface;
import elc.Actor;
import elc.Client;
import elc.Packet;
import elc.Protocol;

import playerView.*;

import java.util.*;
import java.io.*;
/*
 * Created on 7/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
class MyBot extends Client {
	String gmTo;
	String myName;
	PlayersOnline online;
	PlayerList  guild=new PlayerList(new File("lnx.txt"));
	long lastGmHi=0;
	boolean allowNewHour=true;
	boolean debug=false;
	JokerList joker;

	MyBot (String name, String password){
		super(name, password);
		this.myName=name;
		joker=new JokerList();
		online=new PlayersOnline();
	}
	MyBot (String name, String password, String adress, int port){
		super(name, password, adress,port);
		this.myName=name;
		joker=new JokerList();
		online=new PlayersOnline();
	}
	
    public void onChat(String text) {
    	System.out.println(text);
    }
	public void onChat(String person, String message){
		
		if(isAdmin(person)){
			processCommands(person,message,0);
		}
    	else if((!person.equals(myName))&&(!person.equals("Tuxedo"))){
    		//processCommands(person,message,1); //ignoreing local
    		if(message.startsWith("hi")||message.startsWith("hello")||message.startsWith("gday")){
    		//	chat("hi "+person+", i'm a bot, pm me with 'help' for instructions");
    		}
    	}
	}
	public void onChannelChat(String person, String message){
    	if((!person.equals(myName))&&(!person.equals("Tuxedo"))){
    		processCommands(person,message,4);
    	}
	}
	public void onPm(String person, String message){
	//	System.out.println("|"+person+"|"+message+"|");
    	processCommands(person,message,1);
    }
	public void onGm(String person, String message){
    	//System.out.println("person: |"+person+"|");
    	if((!person.equals(myName))&&(!person.equals("Tuxedo"))){
    		if(message.startsWith("d,")){
    			processCommands(person,message.substring(3),2);
    		}
    		else if(message.startsWith("d")){
    			processCommands(person,message.substring(2),2);
    		}
    		else if(message.startsWith("dead,")){
        		processCommands(person,message.substring(6),2);    			
    		}
    		else if(message.startsWith("dead")){
        		processCommands(person,message.substring(5),2);    			
    		}
    		else if(message.startsWith("deadface,")){
        		processCommands(person,message.substring(10),2);    			
    		}
    		else if(message.startsWith("deadface")){
        		processCommands(person,message.substring(9),2);    			
    		}
       	}
		if(gmTo!=null){
			chatPm(gmTo, "#GM from "+person+": "+message);
			System.out.println("sent a message");
		}
		if(message.startsWith("ding")){
			this.chatGm("well done "+person+"!");
		}

    }
    void processCommands(String person, String message, int type){
    	String command=message.toLowerCase();
    	if(command.startsWith("hi")){
    		replyMessage(person,"G'day to you",type);
    	}
    	else if(command.startsWith("online")){
    		Vector tmp=online.getOnline(guild.getList());
    		System.out.println(""+tmp.size());
    		String res="";
    		for(int i=0;i<tmp.size(); i++){
    			String player=((Player)tmp.get(i)).toString();
    			if (res.length()+player.length()<110){
    				if(res.length()==0){
    					res=player;
    				}
    				else{
    					res=res+", "+player;
    				}
    			}
    			else{
    				replyMessage(person,""+res,type);
    				res=player;
    			}
    		}
    		replyMessage(person,""+res,type);
    	}
    	else if(command.startsWith("stats")){
    		String playerName=message.substring(6,message.length());
    		//System.out.println("|"+playerName+"|");
    		Player player=new Player(playerName,online);
    		
    		if(player.update()){
        		System.out.println("stas for: "+player.name);
        		replyMessage(person,"Phys: "+player.getPhysique()+", Coord: "+player.getCoordination()+", Attack: "+player.getAttack()+", Defence: "+player.getDefense()+", Combat Level: "+player.getCombatLevel(),type);
        		
    		}
    		else{
        		System.out.println("stas wheren't retrieved for for: "+player.dump());
        		replyMessage(person,"error, stats wheren't retrieved, perhaps you put a wrong name in",type);
    		}

    	}
    	else if(command.startsWith("skills")){
    		String playerName=message.substring(7,message.length());
    		Player player=new Player(playerName,online);
    		
    		if(player.update()){
        		System.out.println("skills for: "+player.name);
        		replyMessage(person,"Pickpoints:" +player.getPickPoints()+" , OA "+player.getOverall()+", Carry capacity: "+player.getCarry()+" EMU, Mana: "+player.getMana(),type);
        		replyMessage(person,"ph: "+player.getPhysique()+" co:"+player.getCoordination()+" re:"+player.getReasoning()+" wi:"+player.getWill()+" in:"+player.getInstinct()+" vi:"+player.getVitality(),type);
        		replyMessage(person,"Mag:"+player.getMagic()+" Har:"+player.getHarvest()+" Man:"+player.getManufacture()+" Alc:"+player.getAlchemy()+" Pot:"+player.getPotion()+" Sum:"+player.getSummoning()+" Cra:"+player.getCrafting(),type);        		
        		
    		}
    		else{
        		System.out.println("stas wheren't retrieved for for: "+player.dump());
        		replyMessage(person,"error, stats wheren't retrieved, perhaps you put a wrong name in",type);
    		}
    	}
		else if(command.startsWith("help")){
			replyMessage(person,"hi, i'm a bot written by dns from the lnx guild. my accepted commands are:",type);
			replyMessage(person,"online - shows online members of the lnx guild",type);
			replyMessage(person,"stats NAME - shows combat statistics for the player",type);
			replyMessage(person,"skills NAME - shows all skills for thaty player",type);
			replyMessage(person,"sing - let me sing to you",type);
			replyMessage(person,"joker - displays top 5 people to find the joker",type);
		}
		else if(command.startsWith("#gm")){
			if(isAdmin(person)){
				chatGm(message.substring(3,message.length()));
			}
		}
		else if(command.startsWith("#cmd")){
			System.out.println("got a command from person|"+person+":" +message.substring(4,message.length()));
			if(isAdmin(person)){
				System.out.println("got a command:" +message.substring(4,message.length()));
				//chat(message.substring(6,message.length()));
				chatChannel(message.substring(6,message.length()));
			}
			
		}
		else if(command.startsWith("sing")){
			replyMessage(person,"100 buckets of bits on the bus",type);
			replyMessage(person,"100 buckets of bits",type);
			replyMessage(person,"Take one down, short it to ground",type);
			replyMessage(person,"there would be FF buckets of bits on the bus",type);
			replyMessage(person,"FF buckets of bits on the bus",type);
			//replyMessage(person,"     ad infinitum...",type);
		}
		else if(command.startsWith("broadcast")){
			if(isAdmin(person)){
				broadcast();
			}
		}
		else if(command.startsWith("#ff")){
			System.out.println("dooing stuff");
			Enumeration e=getActors().elements();
			while(e.hasMoreElements()){
				System.out.println((Actor)e.nextElement());	
			}
		}
		else if(command.startsWith("#gg")){
			//this.locateME();
			//sendGm((char)135+"hi");
			//chatGm("\u0080hi");			
		}
		else if(command.startsWith("#gtpm")){
			if(isAdmin(person)){
				if(gmTo!=null){
					gmTo=null;
					chatPm(person,"#gtpm no longer active");
				}
				else{
					gmTo=person;
					chatPm(person,"#gtpm now active");
				}
				System.out.println("gto: "+gmTo);
			}
		}
		else if (command.startsWith("#quit")){
			if(isAdmin(person)){
		    	chatGm("I'm going down");
		    	chatChannel("I'm going down");
				System.exit(0);
			}
		}
		else if(command.startsWith("hour")){
			if(allowNewHour){
				allowNewHour=false;
				replyMessage(person,"Hour broadcast: off",type);
				
			}
			else{
				allowNewHour=true;
				replyMessage(person,"Hour broadcast: on",type);
			}
		}
		else if(command.startsWith("joker")){
			joker.sort();
			Enumeration e=joker.list.elements();

			if(e.hasMoreElements()){
				
				String res=joker.list.size()+" people have found the joker (when i've been online) the top 5 are: "+((JokerList.Pair)e.nextElement()).toString();				
				for(int i=1;i<5&&e.hasMoreElements();i++){
					res=res+", "+(JokerList.Pair)e.nextElement();
				}
				replyMessage(person,res+".",type);
			}
			else{
				replyMessage(person,"no one has found him yet",type);
			}

		}
		else if(command.startsWith("debug")){
			if(debug){
				debug=false;
				replyMessage(person,"debug off",type);
			}
			else{
				debug=true;
				replyMessage(person,"debug on",type);
			}
		}
    	else{
    		
    		if(inGuild(person)){
    			
    		}
    		else if((type!=2)&&(type!=0)){
        		replyMessage(person,"error, your command is not valid pm me with 'help' for avalable commands",type);			
    		}	
    	}
    }
    public void replyMessage(String name,String  message,int type){
    	if(type==0){
    		chat(message);
    	}
    	else if (type==1){
    		chatPm("/"+name+" "+message);	
    	}
    	else if (type==2){
    		chatGm(message);
    	}
    	else if(type==4){
    		chatChannel(message);
    	}
    }
    
    
	public static void main(String[] args) {
		MyBot b;
    	if(args.length < 2){
    		System.out.println("usage: java MessengerBot user pass [server port]");
    		System.exit(1);
    	}
    	if(args.length == 4){
    		b= new MyBot(args[0], args[1], args[2], Integer.valueOf(args[3]).intValue());
    	} else {
    		b = new MyBot(args[0], args[1]);	
    	}
    	
    	b.start();
	}
	public void onUnknowPacket(Packet p) {
		/*if (p.getProtocol()==Protocol.LOG_IN_OK){
			//ByteBuffer tmp=p.getData();
			System.out.println(p.getData());
		}*/
		if(debug){
			if(p.protocol!=2){
				System.out.println(p.protocol+" "+p.length);
				System.out.println(p.dump());
			}
		}
    }
	public void broadcast(){
		chat("hello people i'm a bot, pm me with 'help' for instructions");
	}
	public void onMinute(int minute){
		/*System.out.println("minute: "+minute);
		System.out.println((minute/60)+":" +(minute%60));*/
		
		if((minute%60)==0){
			if(allowNewHour){
				chatGm("Happy New Hour");
			}
		}
		else if((minute%60)==59){
			online.load();
			if(online.findIn("Tuxedo")==null){//if tux isn't online
				this.allowNewHour=true;
			}
			else{
				this.allowNewHour=false;
			}
			
		}
	}
	
    public void onAddNewActor(Packet p){
    	/*this.getActors().add(new Actor(p));
        Actor i;
        Enumeration actors = this.getActors().elements();
        while(actors.hasMoreElements()){
            i = (Actor)actors.nextElement();
            System.out.print(":sdfDFS"+i.toString() + ", ");
        }
        System.out.println();*/
    	//System.out.println("new Actor");
    }
    public boolean isAdmin(String s){
    	return s.equals("dns");
    }
    public boolean inGuild(String s){
    	return guild.getList().contains(s);
    }
    public void onLogin(){
    	chatGm("I'm Back!!");
    	chatChannel("I'm Back");
    	broadcast();
    }
    public void onSystemMessage(String message){
    	StringTokenizer st=new StringTokenizer(message," ");
    	String person=st.nextToken();
    	if(st.nextToken().equals("found")&&st.nextToken().equals("Joker")){
    		//System.out.println("---"+person+" found the joker, good for him");
    		joker.found(person);
    		joker.save();
    	}
    }
    
}