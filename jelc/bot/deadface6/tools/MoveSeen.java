package bot.deadface6.tools;

import java.sql.*;

import bot.deadface6.*;
public class MoveSeen {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ResultSet rs=DB.getInstance().executeQuery("select * from account;");
			while(rs.next()){
				String name=rs.getString("name");
				long lastOnline=rs.getDate("lastOnline").getTime();
				long lastSeen=rs.getDate("lastSeen").getTime();
				String query="REPLACE into stats(name, online,seen)values('"+name+"','"+lastOnline+"','"+lastSeen+"');";
				System.out.println("executeing query: "+query);
				DB.getInstance().executeUpdate(query);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
