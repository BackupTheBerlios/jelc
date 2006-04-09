package bot.deadface5.commands;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.io.*;

import bot.deadface5.*;
import bot.deadface5.commands.JokerList.Pair;

import elc.ClientInterface;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JokerList2 implements BotCommand,ClientInterface {
	public JokerList2(MyBot bot){
		bot.con.addClientListener(this);
	}


	public boolean process(Replyer reply, String[] args) {
		if(args[0].equalsIgnoreCase("joker")){
			List list=(List) getJokers().iterator();
			if(list==null){
				if(list.iterator().hasNext()){
					Misc.ouputList(reply,list.size()+" people have found the joker (when i've been online) the top 10 are: ",list.iterator());
				}
				else{
					reply.reply("no one has found him yet");
				}
			}
			else{
				reply.reply("sorry there was an internal error");
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
	    		Account account=AccountManager.getAccountManger().getAccount(person);
	    		account.setJokerCount(account.getJokerCount()+1);
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
	
	
	public List getJokers(){
		List tmp=new Vector();
		
		try {
			ResultSet rs=DB.getInstance().executeQuery("SELECT name, joker FROM account ORDER BY joker DESC LIMIT 10;");
			while(rs.next()){
				new Pair(rs.getString("name"),rs.getInt("joker"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return tmp;
	}
	
	class Pair implements Comparator{
		String name;
		int count;
		Pair(String name, int count){
			this.name=name;
			this.count=count;
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

	public void onIG(String message) {
		// TODO Auto-generated method stub
		
	}
	
}

