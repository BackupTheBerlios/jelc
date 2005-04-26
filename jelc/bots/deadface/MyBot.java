package bots.deadface;
import elc.*;

import playerView.*;

import java.util.*;
import java.io.*;

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
//	PlayerList  guild=new PlayerList(new File("list.txt"));
	long lastGmHi=0;
	boolean allowNewHour=true;
	boolean debug=false;
	JokerList joker;
	SeenList seen;
	MyBot (String name, String password){
		super(name, password);
		this.myName=name;
		joker=new JokerList();
		online=new PlayersOnline();
		seen=new SeenList(new File("seen.txt"));
	}
	MyBot (String name, String password, String adress, int port){
		super(name, password, adress,port);
		this.myName=name;
		joker=new JokerList();
		online=new PlayersOnline();
		seen=new SeenList(new File("seen.txt"));
	}
	
    public void onChat(String text) {
    	System.out.println(text);
    }
	public void onChat(String person, String message){
		
		if(isAdmin(person)){
			processCommands(person,message,0);
		}
    	/*else if((!person.equals(myName))&&(!person.equals("Tuxedo"))){
    		//processCommands(person,message,1); //ignoreing local
    		if(message.startsWith("hi")||message.startsWith("hello")||message.startsWith("gday")){
    		//	chat("hi "+person+", i'm a bot, pm me with 'help' for instructions");
    		}
    	}*/
	}
	public void onChannelChat(String person, String message){
    	if(!person.equals(myName)){
    		processCommands(person,message,4);
    	}
	}
	public void onPm(String person, String message){
	//	System.out.println("|"+person+"|"+message+"|");
    	processCommands(person,message,1);
    }
	public void onGm(String person, String message){
    	//System.out.println("person: |"+person+"|");
    	if(!person.equals(myName)){//ignore yourself
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
    	
//		System.out.println("index: "+(message.indexOf(" ")));
    	int index=message.indexOf(" ");
    	String command;
    	if(index!=-1){
        	 command=(message.substring(0,index)).toLowerCase();
    	}
    	else{
    		command=(message).toLowerCase();
    	}
		//System.err.println("|"+person+"|"+command+"|"+message+"|"+type);
    	if(command.equals("hi")){
    		replyMessage(person,"G'day to you",type);
    	}
    	else if(command.equals("online")){
    		/*Iterator i=online.getOnline(guild.getList()).iterator();
    		outputList(person,type,"",i);*/
    		replyMessage(person,"command replaced with 'on' use that instead",type);
    		
    	}
    	else if(command.equals("on")){
    		if(message.length()>3){
    			String guild=message.substring(3);
    			List tmp=seen.getGuild(guild);
    			if(tmp.size()!=0){
    				Iterator i=online.getOnline(tmp).iterator();
        			outputList(person,type,"",i);
    			}
    			else{
    				replyMessage(person,"i have not seen the guild: "+guild,type);
    			}
    		}
    		else{
    			SeenList.Person p=seen.find(person);
    			
    			if((p.getGuild()).equals("")){
    				replyMessage(person,"sorry, you don't have a guild, or i don't know it, try; on guildtag"+p.getGuild(),type);
    			}
    			else{
    	   			List tmp=seen.getGuild(p.guild);
        			if(tmp.size()!=0){
        				Iterator i=online.getOnline(tmp).iterator();
            			outputList(person,type,"",i);
        			}
        			else{
        				replyMessage(person,"i have not seen the guild: "+p.getGuild(),type);
        			}
    			}
    		}
    	}
    	
    	
    	else if(command.equals("stats")){
    		String playerName=message.substring(6,message.length());
    		//System.out.println("|"+playerName+"|");
    		Player player=new Player(playerName,online);
    		
    		if(player.update()){
        		//System.out.println("stas for: "+player.name);
    			SeenList.Person p=seen.find(playerName);
    			if(p!=null){
    				if(p.getGuild().equals("")){
    					replyMessage(person,playerName+"(N/A) Phys: "+player.getPhysique()+", Coord: "+player.getCoordination()+", Attack: "+player.getAttack()+", Defence: "+player.getDefense()+", Combat Level: "+player.getCombatLevel(),type);
    				}
    				else{
    					replyMessage(person,playerName+"("+p.getGuild()+") Phys: "+player.getPhysique()+", Coord: "+player.getCoordination()+", Attack: "+player.getAttack()+", Defence: "+player.getDefense()+", Combat Level: "+player.getCombatLevel(),type);
    				}
    			}
    			else{
    				replyMessage(person,playerName+"(?) Phys: "+player.getPhysique()+", Coord: "+player.getCoordination()+", Attack: "+player.getAttack()+", Defence: "+player.getDefense()+", Combat Level: "+player.getCombatLevel(),type);
    			}
        		
    		}
    		else{
        		System.out.println("stas wheren't retrieved for for: "+player.dump());
        		replyMessage(person,"error, stats wheren't retrieved, perhaps you put a wrong name in",type);
    		}

    	}
    	else if(command.equals("skills")){
    		String playerName=message.substring(7,message.length());
    		Player player=new Player(playerName,online);
    		SeenList.Person p=seen.find(playerName);
    		if(player.update()){
        		System.out.println("skills for: "+player.name);
    			if(p!=null){
    				if(p.getGuild().equals("")){
    					replyMessage(person,playerName+"(N/A) Pickpoints:" +player.getPickPoints()+" , OA "+player.getOverall()+", Carry capacity: "+player.getCarry()+" EMU, Mana: "+player.getMana(),type);
    				}
    				else{
    					replyMessage(person,playerName+"("+p.getGuild()+") Pickpoints:" +player.getPickPoints()+" , OA "+player.getOverall()+", Carry capacity: "+player.getCarry()+" EMU, Mana: "+player.getMana(),type);
    				}
        		}
    			else{
    				replyMessage(person,playerName+"(?) Pickpoints:" +player.getPickPoints()+" , OA "+player.getOverall()+", Carry capacity: "+player.getCarry()+" EMU, Mana: "+player.getMana(),type);
    			}
        		replyMessage(person,"ph: "+player.getPhysique()+" co:"+player.getCoordination()+" re:"+player.getReasoning()+" wi:"+player.getWill()+" in:"+player.getInstinct()+" vi:"+player.getVitality(),type);
        		replyMessage(person,"Mag:"+player.getMagic()+" Har:"+player.getHarvest()+" Man:"+player.getManufacture()+" Alc:"+player.getAlchemy()+" Pot:"+player.getPotion()+" Sum:"+player.getSummoning()+" Cra:"+player.getCrafting(),type);        		
        		
    		}
    		else{
        		System.out.println("stas wheren't retrieved for for: "+player.dump());
        		replyMessage(person,"error, stats wheren't retrieved, perhaps you put a wrong name in",type);
    		}
    	}
		else if(command.equals("help")){
			replyMessage(person,"hi, i'm a bot written by dns from the lnx guild. my accepted commands are:",type);
			replyMessage(person,"on - shows online members of your guild (if i've seen you and your guild)",type);
			replyMessage(person,"on GUILD - shows online members of the specified guild(if i've seen you and your guild)",type);
			replyMessage(person,"stats NAME - shows combat statistics for the player",type);
			replyMessage(person,"skills NAME - shows all skills for thaty player",type);
			replyMessage(person,"sing - let me sing to you",type);
			replyMessage(person,"joker - displays top 10 people to find the joker",type);
			if(isGuild(person)){
				replyMessage(person,"Guild Commands: (oh so secret)",type);
				replyMessage(person,"#gtpm = redirects #gm messages to pm messages (for when you are training)",type);
				replyMessage(person,"#gm = sends a #gm (for when you are training)",type);
			}
		}
		else if(command.startsWith("#gm")){
			if(isGuild(person)){
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
		else if(command.equals("broadcast")){
			if(isAdmin(person)){
				broadcast();
			}
		}
		else if(command.startsWith("#gtpm")){
			if(isGuild(person)){
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
			else{
				chatPm(person,"eror you are not in the same guild");
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
			Iterator i=joker.list.subList(0,10).iterator();
			//List l=joker.list.subList(0,10);
			if(i.hasNext()){
				
				outputList(person,type,joker.list.size()+" people have found the joker (when i've been online) the top 10 are: ",i);
				/*
				String res=joker.list.size()+" people have found the joker (when i've been online) the top 5 are: "+((JokerList.Pair)e.nextElement()).toString();				
				for(int i=1;i<5&&e.hasMoreElements();i++){
					res=res+", "+(JokerList.Pair)e.nextElement();
				}
				replyMessage(person,res+".",type);*/
			}
			else{
				replyMessage(person,"no one has found him yet",type);
			}

		}
		else if(command.equals("debug")){
			if(debug){
				debug=false;
				replyMessage(person,"debug off",type);
			}
			else{
				debug=true;
				replyMessage(person,"debug on",type);
			}
		}
		else if(command.equals("see")){
			Enumeration i=getActors().elements();
			if(i.hasMoreElements()){
				
				outputList(person,type,"i can see "+getActors().size()+" people:",i);
			}
			else{
				replyMessage(person,"i can not see anyone",type);
			}
		}
		else if(command.equals("seen")){
			if(message.length()>4){
				String guild=message.substring(5);
				System.out.println("++"+guild);
				List tmp=seen.getGuild(guild.toUpperCase());
				Iterator i=tmp.iterator();
				if(i.hasNext()){
					outputList(person,type,"i've seen "+tmp.size()+" people in the guild "+guild+": ",i);
				}
			}
			else{
				/*Iterator i=seen.list.iterator();
				if(i.hasNext()){
					outputList(person,type,"i've seen "+seen.list.size()+" people: ",i);
				}*/
				replyMessage(person,"i've seen "+seen.list.size()+" people.",type);
			}
		}
		else if(command.equals("quit")){
			this.chatGm("going down!");
			this.chatChannel("going down!");
			quit();
		}
		/*else if(command.equals("notify")){
			String waitingFor=message.substring(7,message.length());
			notify.addNotifyer(waitingFor,person);
			replyMessage(person,"i will notify you when :"+waitingFor+"is online",type);
			notify.check();
		}*/
		
    	else{
    		
    		if(isGuild(person)){
    			
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
			//if(allowNewHour){
				chatGm("Happy New Hour");
			//}
		}
		/*else if((minute%60)==59){
			online.load();
			if(online.findIn("Tuxedo")==null){//if tux isn't online
				this.allowNewHour=true;
			}
			else{
				this.allowNewHour=false;
			}
			
		}*/
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
    	return s.equals("dns");//put names of admins here
    }
    public boolean isGuild(String s){
//    	return guild.getList().contains(s);
//    	return s.equals("person1")||s.equals("person2");
		SeenList.Person p=seen.find(this.myName);
		if(p.getGuild().equals("")){
			System.err.println("gdfggdfd");
			return false;//the bot has no guild
		}
		else{
			return seen.getGuild(p.getGuild()).contains(p.getGuild());
		}
    }
    public void onLogin(){
    	chatGm("I'm Back!!");
    	chatChannel("I'm Back");
    	//broadcast();
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
	public void outputList(String person, int type, String header, Iterator i ){
		String res=header;
		String tmp;
		if(i.hasNext()){
			tmp=i.next().toString();
			if (res.length()+tmp.length()<110){
				res=res+tmp;
			}
			else{
				replyMessage(person,res+".",type);
			}
			while(i.hasNext()){
				tmp=i.next().toString();
				if (res.length()+tmp.length()<110){
					res=res+", "+tmp;
				}
				else{
					replyMessage(person,res+".",type);
					res=tmp;
				}
			}
			replyMessage(person,res,type);	
		}
	}
	public void outputList(String person, int type, String header, Enumeration e ){
		String res=header;
		String tmp;
		if(e.hasMoreElements()){
			tmp=e.nextElement().toString();
			if (res.length()+tmp.length()<110){
				res=res+tmp;
			}
			else{
				replyMessage(person,res+".",type);
			}
			while(e.hasMoreElements()){
				tmp=e.nextElement().toString();
				if (res.length()+tmp.length()<110){
					res=res+", "+tmp;
				}
				else{
					replyMessage(person,res+".",type);
					res=tmp;
				}
			}
			replyMessage(person,res,type);	
		}
	}
	public void notifyPending(String person){
		this.chatPm(person,"hi "+person+" you have enough votes to join our guild. please type '#join_guild EL Linux Community");
		this.chatPm(person,"when you have typed in the above pm me with #accept, eg /deadface #accept");
	}
	public void onAddNewEnhancedActor(EnhancedActor e){
		seen.addPlayer(e.getActorStraightName(),e.getActorGuild());
	}
}