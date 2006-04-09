package bot.deadface5.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Iterator;

import bot.deadface5.*;
import bot.deadface5.commands.Log;
import jelc.playerView.*;


public class OnlineWatcher implements TimeEvent{

	public OnlineWatcher(TimeWatcher watcher) {
		//System.out.println("initial load of online people");
		//timeReached();
		//System.out.println("load complete");
		
		//watcher.addTimeWatcher(this,0,false);//schedule an initial load soon
		watcher.addTimeWatcher(this,10,true);//each 10 minutes check the online pages
		//timeReached();//might as well load and update the people first
	}

	public void timeReached() {
		System.out.println("ONLINE UPDATEING, Saveing accounts");
		//PlayersOnline.getInstance().parse();
		//List tmp=PlayersOnline.getInstance().getPlayers();
		
		
		long online;
		long seen;
		
		List tmp=PlayersOnline.getInstance().parseOnline();
		try {
			for(Iterator itr=tmp.iterator();itr.hasNext();){
				String name=itr.next().toString();
				ResultSet rs;
					rs = DB.getInstance().executeQuery("select * from stats where name='"+name+"' ");
					if(rs.next()){
					online=rs.getLong("online");
					seen=rs.getLong("seen");
				}
				else{
					online=System.currentTimeMillis();
					seen=online;
				}
					DB.getInstance().executeUpdate("REPLACE into stats (name,online,seen) values('"+name+"',"+online+","+seen+");");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*
	private class Stat{
		String name;
		long online;
		long seen;
		public Stat(String name) throws SQLException{
			this.name=name;
			ResultSet rs=DB.getInstance().executeQuery("select * from stats where name='"+name+"' ");
			if(rs.next()){
				online=rs.getLong("online");
				seen=rs.getLong("seen");
			}
			else{
				online=System.currentTimeMillis();
				seen=online;
			}
			DB.getInstance().executeUpdate("REPLACE into stats (name,online,seen) values('"+name+"',"+credits+","+seen+");");
		}
	}
	
		/*for(Iterator itr=tmp.iterator();itr.hasNext();){
			String name=itr.next().toString();
			Account account=AccountManager.getAccountManger().loadAccount(name);
			if (account!=null){//if cache
				account.setLastOnline(PlayersOnline.getInstance().getLastUpdated());
				account.save();
			}
			else{
				if(!DB.getInstance().isInDB(name)){//if not in the db
					account=new Account(name);
					DB.getInstance().saveAccount(account);
				}
			}
			updateTime(name);
		}
		System.out.println("Finished Saveing Files");
	}
	private void updateTime(String name){
		String str="UPDATE account set lastOnline= '"+(PlayersOnline.getInstance().getLastUpdated())+"' WHERE name = '"+name+"';";
		try {
			DB.getInstance().executeUpdate("REPLACE stats set online= '"+(PlayersOnline.getInstance().getLastUpdated())+"' WHERE name = '"+name+"';");
		} catch (SQLException e) {
			Log.getInstance().write("ERROR executeing query: "+str+" "+e.getLocalizedMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}

