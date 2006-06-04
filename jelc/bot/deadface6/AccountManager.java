package bot.deadface6;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * 
 * 
 * //todo load and save account settings 
 */
public class AccountManager {
	Vector<Account> accounts;
	long cleanTime;

	static AccountManager manager;
	AccountManager(){
		accounts=new Vector();
		cleanTime=System.currentTimeMillis();
		//loadFiles();
	}
	/*
	 * need to do file loading and saveing of account data
	 *  
	 */
	public Account getAccount(String name){
		Account a=loadAccount(name);
		if(a==null){
			a=new Account(name);
			a.save();
			accounts.add(a);
			
		}
		return a;
	}
	public Account loadAccount(String name){
		Enumeration e=accounts.elements();
		Account a;
		while(e.hasMoreElements()){
			a=(Account)e.nextElement();
			if (name.equalsIgnoreCase(a.getName())){
				a.setLastUsed(System.currentTimeMillis());
				return a;
			}
		}
		//System.out.println("Not in Cache:");
		a=DB.getInstance().getAccount(name);
		if(a!=null){
			accounts.add(a);
			return a;
		}
		return null;
	}
	public Account createAccount(String name){
		Account a=new Account(name);
		accounts.add(a);
		return a;
	}
	
	public void  loadFiles(){
		accounts=new Vector();
		File f=new File("./prefs/");
		System.out.println(f);
		File[] files=f.listFiles();
		System.out.println("----"+files.length);
		for(int i=0;i<files.length;i++){
			//System.out.println("*"+f.canRead()+f.exists());
			if (files[i].isFile()&&files[i].canRead()){
				try {
					accounts.add(new Account(files[i]));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/*public void save(){
		for(Iterator itr=accounts.iterator();itr.hasNext();){
			//((Account)itr.next()).save();
			DB.getInstance().saveAccount((Account)itr.next());
		}
	}*/
	/*public List<Account> getAccounts(){
		return accounts;
	}*/
	public List<Account> getAdmins(){
		List<Account> admins=new Vector();
		for(Iterator itr=accounts.iterator();itr.hasNext();){
			Account account=(Account)itr.next();
			if(account.isAdmin()){
				admins.add(account);
			}
		}
		return admins;
	}
	public List<Account> getGuild(String tag){
		List<Account> guild=new Vector();
		for(Iterator itr=accounts.iterator();itr.hasNext();){
			Account account=(Account)itr.next();
			if((account.getGuild()!=null)&&account.getGuild().equalsIgnoreCase(tag)){
				guild.add(account);
			}
		}
		return guild;
	}
	public static AccountManager getAccountManger(){
		if(manager==null){
			manager=new AccountManager();
		}
		return manager;
	}
	public List<Account> getJoker(){
		List joker=new Vector();
		return joker;
	}
	
	public Account getIRCAccount(String nick){
		
		//check cache		
		for(Iterator itr=accounts.iterator();itr.hasNext();){
			Account acc=(Account)itr.next();
			for(Iterator itr2=acc.getIrcNick().iterator();itr2.hasNext();){
				if(itr2.next().toString().equalsIgnoreCase(nick)){
					return acc;
				}
			}
		}
		Account tmp=DB.getInstance().getIRCNick(nick);
		if(tmp!=null){		
			accounts.add(tmp);
			return tmp;
		}
		return null;
	}
	public void save(){
		for(Iterator itr=accounts.iterator();itr.hasNext();){
			Account a=(Account)itr.next();
			a.save();
			//DB.getInstance().saveAccount(a);
		}
	}
	public boolean reload(String name){
		Account a=DB.getInstance().getAccount(name);
		if(a!=null){
			accounts.add(a);
			return true;
		}
		return false;
	}
	
	/**
	 * call this every once in a while to unload some unused accounts for people that have not been on for a bit
	 * it saves and unloads unused accounts.
	 *
	 */
	public void cleanup(){
		System.out.println("Cleaning up accounts current cache is "+accounts.size());
		for(Iterator itr=accounts.iterator();itr.hasNext();){
			Account a=(Account)itr.next();
			if((a.getLastOnline()+cleanTime)>0){
				accounts.remove(a);
			}
		}
		System.out.println("Finished cleaning up accounts, current cache is "+accounts.size());
		
		cleanTime=System.currentTimeMillis();
	}
}