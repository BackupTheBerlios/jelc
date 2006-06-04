package bot.deadface6;

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
	final static String MAILENABLED="MAILENABLED:";
	final static String BANNED="BANNED:";
	final static String UNREADMESSAGES="UNREADMESSAGES:";
	final static String FRIEND="FRIEND:";
	final static String IRC="IRCNICK:";
	final static String JOKER="JOKER:";
	final static String MAILBLACKLIST="MAILBLACKLIST:";
	final static String LASTSEEN="LASTSEEN:";
	final static String LASTONLINE="LASTONLINE:";
	final static String CREDITS="CREDITS:";
	final static String TOLDNEWS="TOLDNEWS:";
	final static String MODERATOR="MODERATOR:";


	final static int GUILDFLAG=1;
	final static int BOTFLAG=2;
	final static int ADMINFLAG=4;
	final static int MAILFLAG=8;
	final static int BANNEDFLAG=16;
	final static int UNREADFLAG=32;
	
	String name;
	private String guild="";
	private boolean sameGuild=false;
	boolean bot=false;
	boolean admin=false;
	private boolean mailEnabled=true;
	private boolean banned=false;
	private boolean unreadMessages=false;
	private boolean toldNews=false;
	
	
	//int unreadMessages=0;
	List<String> friends;
	List<String> irc;
	private int jokerCount=0;
	private List<String> emailBlacklist;
	
	private Session session;

	private List<Message> messages;
	
	private long lastSeen=0;
	private long lastOnline=0;
	private int credits=initialcredits;
	
	static final int initialcredits=500;
	
	Stack sessions=sessions=new Stack();
	
	private boolean inDb=false;///to determine wether it exists in the database so it is ether an intsert or update query
	
	private long lastUsed;
	
	private boolean moderator=false;
	
	private boolean needsSave=false;
	private String password="";
	public Account(String name){
		this.name=name;
		friends=new Vector();
		irc=new Vector();
		setEmailBlacklist(new Vector());
		
		
		lastUsed=System.currentTimeMillis();
	//	sessions=new Stack();
		//this.save();
	}
	
	
	public Account(File f) throws FileNotFoundException,IOException{
		friends=new Vector();
		irc=new Vector();
		setEmailBlacklist(new Vector());
	//	sessions=new Stack();
		
		
		FileInputStream fis=new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		System.out.println("loading file:"+f);
		String line=br.readLine();
		while(line!=null&&(!line.equals(""))){
			procesToken(line);
			line=br.readLine();
		}
	}
	public boolean procesToken(String line){
		if(line.startsWith(NAME)){
			name=line.substring(NAME.length());
			return true;
		}
		else if(line.startsWith(GUILD)){
			setGuild(line.substring(GUILD.length()));
			return true;
		}
		else if(line.startsWith(BOT)){
			String tmp=line.substring(BOT.length());
			if(tmp.equalsIgnoreCase("true")){
				bot=true;
			}
			else{
				bot=false;
			}
			return true;
		}
		else if(line.startsWith(ADMIN)){
			String tmp=line.substring(ADMIN.length());
			if(tmp.equalsIgnoreCase("true")){
				admin=true;
			}
			else{
				admin=false;
			}
			return true;
		}
		else if(line.startsWith(UNREADMESSAGES)){
			String tmp=line.substring(UNREADMESSAGES.length());
			if(tmp.equalsIgnoreCase("true")){
				setUnreadMessages(true);
			}
			else{
				setUnreadMessages(false);
			}
			return true;
			/*try{
				unreadMessages=Integer.parseInt(tmp);
			}
			catch(NumberFormatException nfe){
				
			}*/
		}
		else if(line.startsWith(MAILENABLED)){
			String tmp=line.substring(MAILENABLED.length());
			if(tmp.equalsIgnoreCase("true")){
				setMailEnabled(true);
			}
			else{
				setMailEnabled(false);
			}
			return true;
		}
		else if(line.startsWith(FRIEND)){
			return friends.add(line.substring(FRIEND.length()));
			
		}
		else if(line.startsWith(IRC)){
			return irc.add(line.substring(IRC.length()));
		}
		else if(line.startsWith(SAMEGUILD)){
			String tmp=line.substring(SAMEGUILD.length());
			if(tmp.equalsIgnoreCase("true")){
				setSameGuild(true);
			}
			else{
				setSameGuild(false);
			}
			return true;
		}
		else if(line.startsWith(BANNED)){
			String tmp=line.substring(BANNED.length());
			if(tmp.equalsIgnoreCase("true")){
				this.banned=true;
			}
			else{
				this.banned=false;
			}
			return true;
		}
		else if(line.startsWith(TOLDNEWS)){
			String tmp=line.substring(TOLDNEWS.length());
			if(tmp.equalsIgnoreCase("true")){
				this.toldNews=true;
			}
			else{
				this.toldNews=false;
			}
			return true;
		}
		else if(line.startsWith(JOKER)){
			try{
				setJokerCount(Integer.parseInt(line.substring(JOKER.length())));
			}
			catch (NumberFormatException nfe){
				System.err.println("Error parseing Joker string: "+nfe.getLocalizedMessage());
			}
			return true;
		}
		else if(line.startsWith(MAILBLACKLIST)){
			return getEmailBlacklist().add(line.substring(MAILBLACKLIST.length()));
			
		}
		else if(line.startsWith(LASTSEEN)){
			try{
				lastSeen=Long.parseLong(line.substring(LASTSEEN.length()));
			}
			catch (NumberFormatException nfe){
				System.err.println("Error parseing Joker string: "+nfe.getLocalizedMessage());
			}
			return true;
		}
		else if(line.startsWith(LASTSEEN)){
			try{
				lastSeen=Long.parseLong(line.substring(LASTSEEN.length()));
			}
			catch (NumberFormatException nfe){
				System.err.println("Error parseing Joker string: "+nfe.getLocalizedMessage());
			}
			return true;
		}
		else if(line.startsWith(LASTONLINE)){
			try{
				lastOnline=Long.parseLong(line.substring(LASTONLINE.length()));
			}
			catch (NumberFormatException nfe){
				System.err.println("Error parseing Joker string: "+nfe.getLocalizedMessage());
			}
			return true;
		}
		else if (line.startsWith(CREDITS)){
			try{
				setCredits(Integer.parseInt(line.substring(CREDITS.length())));
			}
			catch (NumberFormatException nfe){
				System.err.println("Error parseing CREDITS string: "+nfe.getLocalizedMessage());
			}
			return true;
		}
		else if(line.startsWith(MODERATOR)){
			String tmp=line.substring(MODERATOR.length());
			if(tmp.equalsIgnoreCase("true")){
				setModerator(true);
			}
			else{
				setModerator(false);
			}
			return true;
		}
		return false;
	}
	
	public void save(){
		//saveFile(new File("./prefs/"+name+".txt"));
		DB.getInstance().saveAccount(this);
		this.setInDb(true);
	}
	public boolean saveFile(File f){
		//File f=new File("./prefs/"+name+".txt");
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
			if(!isMailEnabled()){
				out.println(MAILENABLED+isMailEnabled());
			}
			if(banned){
				out.println(BANNED+banned);
			}
			if(this.hasToldNews()){
				out.println(TOLDNEWS+this.hasToldNews());
			}
			if(getJokerCount()!=0){
				out.println(JOKER+getJokerCount());
			}
			
			out.println(UNREADMESSAGES+hasUnreadMessages());
			for(Iterator itr=friends.iterator();itr.hasNext();){
				out.println(FRIEND+itr.next());
			}
			
			for(Iterator itr=irc.iterator();itr.hasNext();){
				out.println(IRC+itr.next());
			}
			
			for(Iterator itr=getEmailBlacklist().iterator();itr.hasNext();){
				out.println(MAILBLACKLIST+itr.next());
			}
			
			if(guild!=null){
				out.println(GUILD+guild);
			}
			out.println(LASTSEEN+lastSeen);
			out.println(LASTONLINE+lastOnline);
			
			if(getCredits()!=initialcredits){
				out.println(CREDITS+getCredits());
			}
			if(isModerator()){
				out.println(MODERATOR+isModerator());
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
	public void setName(String name){
		this.name=name;
	}
	public boolean isBot(){
		return bot;
	}
	public void setBot(boolean bot){
		this.bot=bot;
	}
	public boolean isSameGuild(){
		//return sameGuild;
		if(Guild.getInstance().isGuild(name)){
			return true;
		}
		return guild.equalsIgnoreCase(Config.getConfig().getMyGuild());
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
		
		//System.out.println("reply "+read.size());
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
	 * @return Returns the session.
	 */
	public <Session>Stack getSession() {
		return sessions;
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
		if(getEmailBlacklist().contains(from)){
			return false;
		}
		else if(!isMailEnabled()){
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
	/**
	 * @param emailBlacklist The emailBlacklist to set.
	 */
	public void setEmailBlacklist(List<String> emailBlacklist) {
		this.emailBlacklist = emailBlacklist;
	}
	/**
	 * @return Returns the emailBlacklist.
	 */
	public List<String> getEmailBlacklist() {
		return emailBlacklist;
	}
	/**
	 * @param guild The guild to set.
	 */
	public void setGuild(String guild) {
		if(guild.equals("NULL")){
			this.guild="";
		}
		else{
			this.guild = guild;
		}
	}
	/**
	 * @return Returns the guild.
	 */
	public String getGuild() {
		return guild;
	}
	/**
	 * @param lastSeen The lastSeen to set.
	 */
	public void setLastSeen(long lastSeen) {
		this.lastSeen = lastSeen;
	}
	/**
	 * @return Returns the lastSeen.
	 */
	public long getLastSeen() {
		return lastSeen;
	}
	public Date getlastDateSeen(){
		return new Date(getLastSeen());
	}
	/**
	 * @param lastOnline The lastOnline to set.
	 */
	public void setLastOnline(long lastOnline) {
		this.lastOnline = lastOnline;
	}
	/**
	 * @return Returns the lastOnline.
	 */
	public long getLastOnline() {
		return lastOnline;
	}
	public Date getLastDateOnline(){
		return new Date(getLastOnline());
	}
	public String toString(){
		return name;
	}
	/**
	 * @param mailEnabled The mailEnabled to set.
	 */
	public void setMailEnabled(boolean mailEnabled) {
		this.mailEnabled = mailEnabled;
	}
	/**
	 * @return Returns the mailEnabled.
	 */
	public boolean isMailEnabled() {
		return mailEnabled;
	}
	public void setFlags(int flag){
		//System.out.println("|"+((flag%GUILDFLAG)==0)+"|"+((flag%BOTFLAG)==0)+"|"+((flag%ADMINFLAG)==0));
		if(UNREADFLAG<flag){
			unreadMessages=true;
			flag=flag-UNREADFLAG;
		}
		else{
			unreadMessages=false;
		}
		if(BANNEDFLAG<flag){
			banned=true;
			flag=flag-BANNEDFLAG;
		}
		else{
			banned=false;
		}
		if(MAILFLAG<flag){
			mailEnabled=true;
			flag=flag-MAILFLAG;
		}
		else{
			mailEnabled=false;
		}
		if(ADMINFLAG<flag){
			admin=true;
			flag=flag-ADMINFLAG;
		}
		else{
			admin=false;
		}
		if(BOTFLAG<flag){
			bot=true;
			flag=flag-BOTFLAG;
		}
		else{
			bot=false;
		}
		if(GUILDFLAG<flag){
			bot=true;
		}
		else{
			sameGuild=false;
		}
		
		
		//dump();
		/*
		sameGuild=((flag%GUILDFLAG)==0);
		bot=((flag%BOTFLAG)==0);
		admin=((flag%ADMINFLAG)==0);
		mailEnabled=((flag%MAILFLAG)==0);
		banned=((flag%BANNEDFLAG)==0);
		unreadMessages=((flag%UNREADFLAG)==0);*/
	}
	public int getFlags(){
		
		int res=0;
		if(sameGuild){
			res=res+GUILDFLAG;
		}
		if (bot){
			res=res+BOTFLAG;
		}
		if(admin){
			res=res+ADMINFLAG;
		}
		if(mailEnabled){
			res=res+MAILFLAG;
		}
		if(banned){
			res=res+BANNEDFLAG;
		}
		if(unreadMessages){
			res=res+UNREADFLAG;
		}
		return res;
	}


	/**
	 * @param jokerCount The jokerCount to set.
	 */
	public void setJokerCount(int jokerCount) {
		this.jokerCount = jokerCount;
	}


	/**
	 * @return Returns the jokerCount.
	 */
	public int getJokerCount() {
		return jokerCount;
	}


	/**
	 * @param credits The credits to set.
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}


	/**
	 * @return Returns the credits.
	 */
	public int getCredits() {
		return credits;
	}


	/**
	 * @param inDb The inDb to set.
	 */
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
	}


	/**
	 * @return Returns the inDb.
	 */
	public boolean isInDb() {
		return inDb;
	}
	
	
	public String dump(){
		String res=name;
		res=res+"."+guild+"\n";
		res=res+" SAME GUILD "+isSameGuild();
		res=res+" "+BOT+bot;
		res=res+" "+ADMIN+admin;
		res=res+" "+MAILENABLED+mailEnabled;
		res=res+" "+BANNED+banned;
		res=res+" "+UNREADMESSAGES+unreadMessages;
		res=res+" "+TOLDNEWS+toldNews;
		res=res+" "+LASTONLINE+this.lastOnline;
		res=res+" "+LASTSEEN+this.lastSeen;
		res=res+" Friends: ";
		for(Iterator itr=friends.iterator();itr.hasNext();){
			res=res+" "+itr.next();
		}
		res=res+" IRCNICK:";
		for(Iterator itr=irc.iterator();itr.hasNext();){
			res=res+" "+itr.next();
		}
		return res;
	}


	/**
	 * @param lastUsed The lastUsed to set.
	 */
	public void setLastUsed(long lastUsed) {
		this.lastUsed = lastUsed;
	}


	/**
	 * @return Returns the lastUsed.
	 */
	public long getLastUsed() {
		return lastUsed;
	}


	/**
	 * @param needsSave The needsSave to set.
	 */
	void setNeedsSave(boolean needsSave) {
		this.needsSave = needsSave;
	}


	/**
	 * @return Returns the needsSave.
	 */
	boolean needsSave() {
		return needsSave;
	}


	public void setToldNews(boolean newsTold) {
		this.toldNews = newsTold;
	}


	/**
	 * @return Returns the toldNews.
	 */
	public boolean hasToldNews() {
		return toldNews;
	}


	public void setModerator(boolean moderator) {
		this.moderator = moderator;
	}


	public boolean isModerator() {
		return moderator;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPassword() {
		return password;
	}
}
