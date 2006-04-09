package bot.deadface5;

import java.sql.*;
import java.text.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class DB {
static DB db;
Connection con;
SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
Vector namesindb=new Vector();
	private DB() {
		//df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		/*
		try {
			Class.forName(Config.getConfig().getDBDriver());
			con=DriverManager.getConnection(Config.getConfig().getDB());
			con.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
		//connect();
		
		
		connect();
		try {
			ResultSet results=executeQuery("select name from account;");
			while(results.next()){
				namesindb.add(results.getString("name"));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public synchronized void connect(){

		try {

			if((con==null)||con.isClosed()){
				System.out.println("Loading database: "+Config.getConfig().getDB()+" with driver: "+Config.getConfig().getClassString());
				try {
					Class.forName(Config.getConfig().getClassString());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				con=DriverManager.getConnection(Config.getConfig().getDB());
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Account getAccount(String name){
		Statement s;
		Account a=new Account(name);
		try {
			connect();
			s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM account WHERE name = '"+name+"'");
			if(rs.next()){
				a.setGuild(rs.getString("guild"));
				//a.setFlags(rs.getInt("flags"));
				//System.out.println("flags:"+rs.getInt("flags"));
				a.setJokerCount(rs.getInt("joker"));
				/*a.setLastOnline(rs.getDate("lastOnline").getTime());
				a.setLastSeen(rs.getDate("lastSeen").getTime());*/
				a.setCredits(rs.getInt("credits"));
				a.setUnreadMessages(rs.getBoolean("unreadMessages"));
				a.setMailEnabled(rs.getBoolean("mailEnabled"));
				a.setBanned(rs.getBoolean("banned"));
				a.setBot(rs.getBoolean("bot"));
				a.setAdmin(rs.getBoolean("admin"));
				a.setToldNews(rs.getBoolean("newsTold"));
				a.setModerator(rs.getBoolean("moderator"));
				
				a.setInDb(true);
				loadNick(a);
				loadFriends(a);
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	
	private boolean loadFriends(Account a){
		Statement s;
		try {
			connect();
			s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM friend WHERE name = '"+a.getName()+"'");
			while(rs.next()){
				a.addFriend(rs.getString("friend"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private boolean saveFriends(Account a){
		Statement s;
			try {
				connect();
			s = con.createStatement();
			//first delete previous list (becuase 
			 s.execute("DELETE FROM friend WHERE name='"+a.getName()+"'");
			for(Iterator itr=a.getFriends().iterator();itr.hasNext();){
				String friend=itr.next().toString();
				s.execute("INSERT INTO friend VALUES ('"+a.getName()+"', '"+friend+"')");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private boolean loadNick(Account a){
		Statement s;
		try {
			connect();
			s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM irc WHERE name = '"+a.getName()+"'");
			while(rs.next()){
				a.addIrcNick(rs.getString("nick"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private boolean saveNick(Account a){
		Statement s;
			try {
				connect();
			s = con.createStatement();
			//first delete previous list (becuase 
			s.execute("DELETE FROM irc WHERE name='"+a.getName()+"'");
			for(Iterator itr=a.getIrcNick().iterator();itr.hasNext();){
				String friend=itr.next().toString();
				s.execute("INSERT INTO irc VALUES ('"+a.getName()+"', '"+friend+"')");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean saveAccount(Account a){
		boolean worked=true;
		
		try {
			connect();
			Statement s=con.createStatement();
			if(a.isInDb()){//update insted of insert
				//System.out.println("QUERY: "+"UPDATE account set guild='"+a.getGuild()+"', flags= "+a.getFlags()+", joker = "+a.getJokerCount()+", lastSeen =  '"+df.format(a.getlastDateSeen())+"', lastOnline =  '"+df.format(a.getLastDateOnline())+"', credits = "+a.getCredits()+"  WHERE name = '"+a.getName()+"'");
				String str="UPDATE account set guild='"+a.getGuild()+/*"', flags= "+a.getFlags()+*/"', joker = "+a.getJokerCount()+", credits = "+a.getCredits()+", unreadMessages="+a.hasUnreadMessages()+", mailEnabled="+a.isMailEnabled()+", banned="+a.isBanned()+", bot="+a.isBot()+", admin="+a.isAdmin()+", newsTold = "+a.hasToldNews()+", moderator = "+a.isModerator()+" WHERE name = '"+a.getName()+"'";
				//System.out.println("QUERY -"+str);
				s.execute(str);
			}
			else{
				String str="INSERT into account(name, guild, joker, credits, unreadMessages,  mailEnabled,banned, bot,admin,newsTold,moderator) values ('"+a.getName()+"', '"+a.getGuild()+"' , "+/*a.getFlags()+" , "*/+a.getJokerCount()+" ,  "+a.getCredits()+","+a.hasUnreadMessages()+","+a.isMailEnabled()+","+a.isBanned()+","+a.isBot()+","+a.isAdmin()+","+a.hasToldNews()+","+a.isModerator()+");";
				System.out.println("QUERY -"+str);
				s.execute(str);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return worked=false;
		}
		
		saveFriends(a);
		saveNick(a);
		return worked;
	}
	
	public Account getIRCNick(String nick){
		try {
			connect();
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM irc WHERE nick = '"+nick+"'");
			if(rs.next()){
				String name=rs.getString("name");
				s.close();
				return getAccount(name);
			}
			s.close();
			return null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		
	}
	
	
	public ResultSet executeQuery(String query) throws SQLException{
		connect();
		Statement s=con.createStatement();
		return s.executeQuery(query);
	}
	public boolean executeUpdate(String query) throws SQLException{
		connect();
		Statement s=con.createStatement();
		return s.execute(query);
	}
	
	public boolean quit(){
        try
        {
            DriverManager.getConnection(Config.getConfig().getDB()+";shutdown=true");
            return false;
        }
        catch (SQLException se)
        {
           return true;
        }
	}
	public boolean isInDB(String name){
		return namesindb.contains(name);
	}
	public static  DB getInstance(){
		if(db==null){
			db=new DB();
			db.connect();
		}
		return db;
	}
}
