package bot.deadface5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import bot.deadface5.commands.Person;

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
		
		//seen list
		
		
		f=new File("seen.txt");
		if (f.exists()&&f.isFile()){
			System.out.println("loading seen list");
//			Vector seen=new Vector();
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
						//p=new Person(tmp.substring(0,indexTime),"",time);
						Account account=manager.getAccount(tmp.substring(0,indexTime));
						account.setLastSeen(time);
						account.setGuild("");
						
						
					}
					else{
						name=tmp.substring(0,index);
						guild=tmp.substring(index+1,indexTime);
						Account account=manager.getAccount(name);
						account.setLastSeen(time);
						account.setGuild(guild);
						//p=new Person(name,guild,time);
					}
//					remove duplicates if they exist
					/*Person q=find(p);
					if(q==null){
						list.add(p);
					}*/
					/*if(p.getLastSeen()==0){
						notSeenCount++;
					}*/
					
					//list.add(p);
					
					tmp=br.readLine();
				}
			//	System.err.println("see list loaded file: "+f+" "+list.size()+" "+notSeenCount);
				//save(f);
//				return true;
			     
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("processed file: "+f.getName());
		
		
		
		
		
		//manager.save();
		/*for(Iterator itr=manager.getAccounts().iterator();itr.hasNext();){
			((Account)itr.next()).save();
		}*/
		
		System.out.println("Converted everything (hopefully)");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Converter();
	}

}
