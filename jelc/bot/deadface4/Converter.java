package bot.deadface4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import playerView.PlayerList;

public class Converter {

	public Converter() {
		super();
		AccountManager manager=new AccountManager();
		
		
		
		//process the Friends lists
		File f=new File("./Friends/");
		System.out.println("loading lists from: "+f);
		File[] files=f.listFiles();
		for(int i=0;i<files.length;i++){
			if (files[i].isFile()&&files[i].canRead()){
				System.out.println("- processing file: "+files[i]);
				PlayerList list =new PlayerList(files[i]);
				Account account=manager.getAccount(list.getName());
				for(Iterator itr=list.getList().iterator();itr.hasNext();){
					account.addFriend(itr.next().toString());
				}
				account.save();
			}
		}
		
		
		//bot.txt
		PlayerList botList=new PlayerList(new File("bot.txt"));
		for(Iterator itr=botList.getList().iterator();itr.hasNext();){
			Account account=manager.getAccount(itr.next().toString());
			account.bot=true;
		}
		//guild
		PlayerList guildList=new PlayerList(new File("guild.txt"));
		for(Iterator itr=guildList.getList().iterator();itr.hasNext();){
			Account account=manager.getAccount(itr.next().toString());
			account.setSameGuild(true);
		}
		//admin
		PlayerList adminList=new PlayerList(new File("admin.txt"));
		for(Iterator itr=adminList.getList().iterator();itr.hasNext();){
			Account account=manager.getAccount(itr.next().toString());
			account.admin=true;
		}
		
		//irc
		
		f=new File("UserTable.txt");
		if (f.exists()&&f.isFile()){
			try {
				FileInputStream fis=new FileInputStream(f);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				System.out.println("loading file:"+f);
				String tmp=br.readLine();
				while(tmp!=null){
			     	//System.out.println("line" +tmp);
			     	StringTokenizer st=new StringTokenizer(tmp);
			     	String nick=st.nextToken();
			     	String user=st.nextToken();
			     	Account account=manager.getAccount(user);
			     	account.addIrcNick(nick);
			     	account.save();
			     	
			     	//table.put(st.nextToken(),st.nextToken());
			     	tmp=br.readLine();
			     }
			     
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//manager.save();
		for(Iterator itr=manager.getAccounts().iterator();itr.hasNext();){
			((Account)itr.next()).save();
		}
		
		System.out.println("Converted everything (hopefully)");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Converter();
	}

}
