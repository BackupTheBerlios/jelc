package bots.deadface2;

import java.util.*;
import java.io.*;

import elc.Actor;
import elc.ActorInterface;
import elc.EnhancedActor;

/**
 * 
 * 
 * @author dns
 */
public class SeenList2 implements Runnable,BotCommand,ActorInterface{
Vector list;
File file;
MyBot2 bot;
long lastUpdate=System.currentTimeMillis();

	/**
	 * 
	 */
	public SeenList2(File f, MyBot2 bot) {
		this.bot=bot;
		this.file=f;
		this.load(f);
		bot.con.addActorInterface(this);
	}
	public boolean load(File f){
		int notSeenCount=0;
		if (f.exists()&&f.isFile()){
			System.out.println("loading seen list");
			list=new Vector();
			try {
				FileInputStream fis=new FileInputStream(f);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String tmp=br.readLine();
				int index;
				int indexTime;
				String name;
				String guild;
				long time;
				while(tmp!=null){
					index=tmp.indexOf(".");			     	
					indexTime=tmp.indexOf(" ");
					if(indexTime!=-1){
						time=Long.parseLong(tmp.substring(indexTime+1));
					}
					else{
						time=System.currentTimeMillis();
					}
					Person p;
					if(index==-1){
						p=new Person(tmp.substring(0,indexTime),"",time);
					}
					else{
						name=tmp.substring(0,index);
						guild=tmp.substring(index+1,indexTime);
						p=new Person(name,guild,time);
					}
//					remove duplicates if they exist
					/*Person q=find(p);
					if(q==null){
						list.add(p);
					}*/
					if(p.getLastSeen()==0){
						notSeenCount++;
					}
					
					list.add(p);
					
					tmp=br.readLine();
				}
				System.err.println("see list loaded file: "+f+" "+list.size()+" "+notSeenCount);
				//save(f);
				return true;
			     
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		System.err.println("bad file: "+f.getName());
		return false;
	}
	public synchronized boolean save(File f){
		try {
			PrintWriter out=new PrintWriter(f); 
			Enumeration e=list.elements();
			while(e.hasMoreElements()){
				Person p=(Person)e.nextElement();
				out.println(p.toString()+" "+p.getLastSeen());
			}
			out.close();
			
		     
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean addPlayer(String name,String guild){
		if(name.charAt(0)==(char)65419){//if is a bot
			name=name.substring(1);
		//	System.out.println("trimmed bot:"+name);
		}
		
		Person p=new Person(name,guild);
		Person looking=find(p);
		//System.out.println(":"+p+"|"+looking+" "+c+" "+(int)c);
		
		if(looking==null){
			p.setLastSeen(System.currentTimeMillis());
			boolean res=list.add(p);
			//System.out.println("added: " +p);
			if(res){
				save();
			}
			return res;
		}
		looking.setLastSeen(System.currentTimeMillis());
		looking.guild=guild;
		//System.out.println("updated: "+looking);
		save();
		return true;
	}
	public List getSeen(){
		return list;
	}
	public Person find(Person person){
		Iterator i=list.iterator();
		Person p;
		while(i.hasNext()){
			p=(Person)i.next();
			if(p.equals(person)){
				return p;
			}
		}
		return null;	
	}
	public Person find(String name){
		Iterator i=list.iterator();
		Person p;
		while(i.hasNext()){
			p=(Person)i.next();
			if(p.name.equalsIgnoreCase(name)){
				if(!p.name.equals(name)){//name changed
					p.name=name;
				}
				return p;
			}
		}
		return null;	
	}
	
	public List getGuild(String guild){
		Vector tmp=new Vector();
		Iterator i=list.iterator();
		Person p;
		while(i.hasNext()){
			p=(Person)i.next();
			if(p.getGuild().equalsIgnoreCase(guild)){
				tmp.add(p.getName());
			}
		}
		return tmp;
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		//System.out.println("last seen:"+lastUpdate+" "+lastUpdate+5000);
		
		if(lastUpdate+5000<System.currentTimeMillis()){
			//System.out.println("updateing: "+lastUpdate+" "+lastUpdate+5000);
			lastUpdate=System.currentTimeMillis();	
			save(file);
			lastUpdate=System.currentTimeMillis();			
		}
	}
	/*
	public static void main(String[] args) {
		//new SeenList2(new File("seen.txt")).save(new File("seen.txt"));
	}*/
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[])
	 */
	public boolean process(String name, String[] args,int type) {
		if(args[0].equals("guild")){
			if(args.length==2){
				String guild=args[1];
				System.out.println("++"+guild);
				List tmp=getGuild(guild);
				//bot.replyMessage(name,"i've seen "+tmp.size()+" people.",type);
				Iterator i=tmp.iterator();
				if(i.hasNext()){
					bot.outputList(name,type,"i've seen "+tmp.size()+" people in the guild "+guild+": ",i);
				}
				else{
					bot.reply(name,"error, the guild tag was not found. usage: seen guild",type);
				}
			}
			else{
				Person me=find(name);
				if((me==null)||me.guild.equals("")){
					bot.reply(name,"sorry, it seems you don't have a guild, try useing: guild [tag]",type);
				}
				else{
					List l=this.getGuild(me.guild);
					Iterator i=l.iterator();
					if(i.hasNext()){
						bot.outputList(name,type,"i've seen "+l.size()+" people in the guild "+me.guild+": ",i);
					}
					else{
						bot.reply(name,"error, the guild tag was not found. usage: seen guild",type);
					}
				}
			}
			return true;
		}
		else if (args[0].equals("seen")){
			if(args.length==2){
				Person p=find(args[1]);
				if(p!=null){
					if(p.getLastSeen()!=0){
						/*Calendar c=Calendar.getInstance();
						c.setTime(new Date(p.getLastSeen()));*/
						
						if(!p.guild.equals("")){
							bot.reply(name,"i last saw:"+p.getName()+"("+p.guild+")"+new Date(p.getLastSeen()),type);
						}
						else{
							bot.reply(name,"i last saw:"+p.getName()+"(n/a)"+new Date(p.getLastSeen()),type);
						}
					}
					else{
						bot.reply(name,"error, i have not seen this person so i don't have a time",type);
					}
				}
				else{
					bot.reply(name,"error, i have not seen this person.",type);
					bot.reply(name,"*note this command has changed 'guild' to hold guild lists",type);
				}
			}
			else{
				bot.reply(name,"i've seen "+list.size()+" people.",type);
			}
			return true;
		}
		else if(args[0].equals("see")){
			Enumeration i=bot.con.getActors().elements();
			if(i.hasMoreElements()){
				
				bot.outputList(name,type,"i can see "+bot.con.getActors().size()+" people:",i);
			}
			else{
				bot.reply(name,"i can not see anyone",type);
			}
			return true;
		}
		return false;
	}
	public void save(){
		new Thread(this).start();
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {
		bot.reply(name,"seen [name]   - shows seen stats, or when i last saw that person",type);
		bot.reply(name,"guild [tag]   - shows who i have seen from your or the specified guild",type);
	}
	public List myGuild(String name){
		Person me=find(name);
		return getGuild(me.getGuild());
	}
	/* (non-Javadoc)
	 * @see elc.ActorInterface#onAddNewActor(elc.Actor)
	 */
	public void onAddNewActor(Actor a) {
		
	}
	/* (non-Javadoc)
	 * @see elc.ActorInterface#onRemoveActor(elc.Actor)
	 */
	public void onRemoveActor(Actor a) {
		
	}
	/* (non-Javadoc)
	 * @see elc.ActorInterface#onAddNewEnhancedActor(elc.EnhancedActor)
	 */
	public void onAddNewEnhancedActor(EnhancedActor a) {
		//System.out.println("player"+a.getActor_name());
		addPlayer(a.getActorStraightName(),a.getActorGuild());
	}
	public void onQuit() {
		save();
	}
}
