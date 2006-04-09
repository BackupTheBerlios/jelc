package bot.deadface5.commands;
import java.util.*;
import java.io.*;

import bot.deadface5.BotCommand;
import bot.deadface5.Misc;
import bot.deadface5.MyBot;
import bot.deadface5.Replyer;

import elc.ClientInterface;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JokerList implements BotCommand,ClientInterface {
File filename;
Vector  list;
MyBot bot;
	public JokerList(MyBot bot,File f){
		this.filename=f;
		this.bot=bot;
		bot.con.addClientListener(this);
		load();
	}
	public boolean load(){
		return load(filename);
	}
	public boolean load(File f){

		if (f.exists()&&f.isFile()){
			list=new Vector();
			try {
				FileInputStream fis=new FileInputStream(f);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				System.out.println("loading file:"+f);
				String tmp=br.readLine();
				while(tmp!=null){
			     	//System.out.println("line" +tmp);
			     	StringTokenizer st=new StringTokenizer(tmp);
			     	list.add(new Pair(st.nextToken(),Integer.parseInt(st.nextToken())));
			     	tmp=br.readLine();
			     }
			     return true;
			     
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		System.err.println("bad file: "+f.getName());
		return false;
	}
	public boolean found(String name){
		//sort();
		Enumeration e=list.elements();
		while(e.hasMoreElements()){
			Pair p=(Pair)e.nextElement();
			//System.out.println("+"+p);
			if(p.getName().equals(name)){
				p.incrementCount();
				return true;
			}
		}
		return list.add(new Pair(name,1));
		
	}
	public boolean save(){
		return save(filename);
	}
	public boolean save(File f){
		try {
			PrintWriter out=new PrintWriter(f); 
			Enumeration e=list.elements();
			while(e.hasMoreElements()){
				out.println(e.nextElement());
			}
			out.close();
			return true;
		     
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void dump(){
		Enumeration e=list.elements();
		System.out.println("---");
		while(e.hasMoreElements()){
			System.out.println(e.nextElement());
		}
	}
	public void sort(){
		if(list.size()!=0){
			Collections.sort(list,(Pair)list.get(0));
		}
	}
	class Pair implements Comparator{
		String name;
		int count;
		Pair(String name, int count){
			this.name=name;
			this.count=count;
			sort();
		}
		/**
		 * @return Returns the count.
		 */
		public int getCount() {
			return count;
		}
		/**
		 * @param count The count to set.
		 */
		public void setCount(int count) {
			this.count = count;
		}
		public void incrementCount(){
			count=count+1;
		}
		/**
		 * @return Returns the name.
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name The name to set.
		 */
		public void setName(String name) {
			this.name = name;
		}
		public String toString(){
			return name+" "+count;
		}
		public int compare(Object a, Object b){
			int aval=((Pair)a).count;
			int bval=((Pair)b).count;
			if(aval>bval){
				return -1;
			}
			else if(aval<bval){
				return 1;
			}
			else{
				return 0;
			}
			 
		}
		public boolean equals(Pair o){
			return name.equals(o.name);
		}
	}
	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("joker")){
			sort();
			Iterator i=list.subList(0,10).iterator();
			if(i.hasNext()){
				Misc.ouputList(reply,list.size()+" people have found the joker (when i've been online) the top 10 are: ",i);
			}
			else{
				reply.reply("no one has found him yet");
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		reply.reply("joker         - displays top 10 people to find the joker(that i've seen)");
	}
    public void onSystemMessage(String message){
    	StringTokenizer st=new StringTokenizer(message," ");
    	String person=st.nextToken();
    	if(st.nextToken().equals("found")&&st.nextToken().equals("Joker")){
    		//System.out.println("---"+person+" found the joker, good for him");
    		found(person);
    		save();
    	}
    }
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
		
	}
	public void onHint(String message) {
		// TODO Auto-generated method stub
		
	}
	public void onMinute(int time) {
		// TODO Auto-generated method stub
		
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}
	public void onIG(String message) {
		// TODO Auto-generated method stub
		
	}
}

