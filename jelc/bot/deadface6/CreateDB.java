package bot.deadface6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
public class CreateDB {

	public CreateDB() {
		/*
		System.out.println("createing database with driver: "+Config.getConfig().getDBDriver()+" DB String:" +Config.getConfig().getDB());
		Class.forName(Config.getConfig().getDBDriver());
		Connection conn = DriverManager.getConnection(Config.getConfig().getDB()+";create=true");
		conn.setAutoCommit(false);
		Statement statement=conn.createStatement();
		statement.execute("CREATE TABLE ACCOUNT(name varchar(30),guild varchar(4) , flags int, joker int, lastSeen DATE, lastOnline DATE, credits int)");
		statement.execute("CREATE TABLE irc(name varchar(30),nick varchar(40))");
		statement.execute("CREATE TABLE friend(name varchar(30),friend varchar(30))");
		System.out.println("should have created the database (hopefully)");
		conn.commit();
		conn.close();
		
		System.gc();
		//AccountManager manager=AccountManager.getAccountManger();
		*/
		DB db=DB.getInstance();
		
		File f=new File("./prefs/");
		System.out.println(f);
		File[] files=f.listFiles();
		System.out.println("----"+files.length);
		for(int i=0;i<files.length;i++){
			//System.out.println("*"+f.canRead()+f.exists());
			if (files[i].isFile()&&files[i].canRead()){
				try {
					//accounts.add(new Account(files[i]));
					Account a=new Account(files[i]);
					System.out.println(a.dump());
					db.saveAccount(a);
					System.out.println(i+":SAVED:"+a);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			

		db.quit();
	}
	public static void main(String[] args) {
		new CreateDB();
	}
}
