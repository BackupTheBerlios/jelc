package bot.deadface4;

import java.io.*;
import java.util.*;




/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Account {
	final static String NAME="NAME:";
	final static String GUILD="GUILD:";
	final static String SAMEGUILD="SAMEGUILD:";
	final static String BOT="BOT:";
	final static String ADMIN="ADMIN:";
	final static String UNREADMESSAGES="UNREADMESSAGES:";
	final static String MAILENABLED="MAILENABLED:";
	final static String FRIEND="FRIEND:";
	final static String IRC="IRCNICK:";
	final static String BANNED="BANNED:";
	final static String JOKER="JOKER:";

	
	String name;
	boolean bot=false;
	boolean admin=false;
	boolean mailEnabled=true;
	private boolean sameGuild=false;
	private boolean banned=false;
	String guild;
	int jokerCount=0;
	
	//int unreadMessages=0;
	private boolean unreadMessages=false;
	List<String> friends;
	List<String> irc;
	List<String> emailBlacklist;
	
	private Session session;

	private List<Message> messages;
	
	
	public Account(String name){
		this.name=name;
		friends=new Vector();
		irc=new Vector();
		emailBlacklist=new Vector();
		this.save();
	}
	public Account(File f) throws FileNotFoundException,IOException{
		friends=new Vector();
		irc=new Vector();
		emailBlacklist=new Vector();
		
		
		FileInputStream fis=new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		System.out.println("loading file:"+f);
		String line=br.readLine();
		while(line!=null&&(!line.equals(""))){
			if(line.startsWith(NAME)){
				name=line.substring(NAME.length());
			}
			else if(line.startsWith(GUILD)){
				guild=line.substring(GUILD.length());
			}
			else if(line.startsWith(BOT)){
				String tmp=line.substring(BOT.length());
				if(tmp.equalsIgnoreCase("true")){
					bot=true;
				}
				else{
					bot=false;
				}
			}
			else if(line.startsWith(ADMIN)){
				String tmp=line.substring(ADMIN.length());
				if(tmp.equalsIgnoreCase("true")){
					admin=true;
				}
				else{
					admin=false;
				}
			}
			else if(line.startsWith(UNREADMESSAGES)){
				String tmp=line.substring(UNREADMESSAGES.length());
				if(tmp.equalsIgnoreCase("true")){
					setUnreadMessages(true);
				}
				else{
					setUnreadMessages(false);
				}
				/*try{
					unreadMessages=Integer.parseInt(tmp);
				}
				catch(NumberFormatException nfe){
					
				}*/
			}
			else if(line.startsWith(MAILENABLED)){
				String tmp=line.substring(MAILENABLED.length());
				if(tmp.equalsIgnoreCase("true")){
					mailEnabled=true;
				}
				else{
					mailEnabled=false;
				}
			}
			else if(line.startsWith(FRIEND)){
				friends.add(line.substring(FRIEND.length()));
			}
			else if(line.startsWith(IRC)){
				irc.add(line.substring(IRC.length()));
			}
			else if(line.startsWith(SAMEGUILD)){
				String tmp=line.substring(SAMEGUILD.length());
				if(tmp.equalsIgnoreCase("true")){
					setSameGuild(true);
				}
				else{
					setSameGuild(false);
				}
			}
			else if(line.startsWith(BANNED)){
				String tmp=line.substring(BANNED.length());
				if(tmp.equalsIgnoreCase("true")){
					this.banned=true;
				}
				else{
					this.banned=false;
				}
			}
			else if(line.startsWith(JOKER)){
				try{
					jokerCount=Integer.parseInt(line.substring(JOKER.length()));
					}
					catch (NumberFormatException nfe){
						System.err.println("Error parseing Joker string: "+nfe.getLocalizedMessage());
					}
			}
			line=br.readLine();
		}
	}
	public boolean save(){
		File f=new File("./prefs/"+name+".txt");
		try {
			PrintWriter out=new PrintWriter(f);
			
			out.println(NAME+ name);
			if(bot){
				out.println(BOT+bot);
			}
			if(admin){
				out.println(ADMIN+admin);
			}
			if(sameGuild){
				out.println(SAMEGUILD+sameGuild);
			}
			if(!mailEnabled){
				out.println(MAILENABLED+mailEnabled);
			}
			if(banned){
				out.println(BANNED+banned);
			}
			if(jokerCount!=0){
				out.println(JOKER+jokerCount);
			}
			
			out.println(UNREADMESSAGES+hasUnreadMessages());
			for(Iterator itr=friends.iterator();itr.hasNext();){
				out.println(FRIEND+itr.next());
			}
			
			for(Iterator itr=irc.iterator();itr.hasNext();){
				out.println(IRC+itr.next());
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
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	public boolean isBot(){
		return bot;
	}
	public boolean isSameGuild(){
		return sameGuild;
	}
	public boolean isAdmin(){
		return admin;
	}
	public void setAdmin(boolean admin){
		this.admin=admin;
	}
	

	
	
	
	
	public boolean equals(Object o){
		if (o instanceof Account ){
			return ((Account)o).name.equalsIgnoreCase(name);
		}
		return false;
	}
	/**
	 * @param messages The messages to set.
	 */
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	/**
	 * @return Returns the messages.
	 */
	public List<Message> getMessages() {
		if(messages==null){
			messages=Message.getMessages(name);
		}
		return messages;
	}
	public List<Message> getUnreadMessages() {
		List<Message> read=getMessages();
		List<Message> res=new Vector<Message>();
		
		System.out.println("reply "+read.size());
		for(Iterator itr=read.iterator();itr.hasNext();){
			Message tmp=(Message)itr.next();
			if(!tmp.isRead()){
				res.add(tmp);
			}
		}
		return res;
	}
	
	public boolean addFriend(String name){
		if(!friends.contains(name)){
			return friends.add(name);
		}
		return true;
	}
	public boolean removeFriend(String name){
		return friends.remove(name);
	}
	public List getFriends(){
		return friends;
	}
	public boolean addIrcNick(String name){
		if(!irc.contains(name)){
			return irc.add(name);
		}
		return true;
	}
	public boolean removeIrcNick(String name){
		return irc.remove(name);
	}
	public List<String> getIrcNick(){
		return irc;
	}
	/**
	 * @param sameGuild The sameGuild to set.
	 */
	public void setSameGuild(boolean sameGuild) {
		this.sameGuild = sameGuild;
	}
	/**
	 * @param session The session to set.
	 */
	public void setSession(Session session) {
		this.session = session;
	}
	/**
	 * @return Returns the session.
	 */
	public Session getSession() {
		return session;
	}
	/**
	 * @param unreadMessages The unreadMessages to set.
	 */
	public void setUnreadMessages(boolean unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
	/**
	 * @return Returns the unreadMessages.
	 */
	public boolean hasUnreadMessages() {
		return unreadMessages;
	}
	/**
	 * @param banned The banned to set.
	 */
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	/**
	 * @return Returns the banned.
	 */
	public boolean isBanned() {
		return banned;
	}
	
	public boolean canSendTO(String from){
		if(emailBlacklist.contains(from)){
			return false;
		}
		else if(!mailEnabled){
			return false;
		}
		else if(banned){
			return false;
		}
		return true;
	}
	public int getUnreadCount(){
		return getUnreadMessages().size();
	}
	
}
