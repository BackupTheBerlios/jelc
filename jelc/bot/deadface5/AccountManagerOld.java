package bot.deadface5;
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
public class AccountManagerOld {
	Vector<Account> accounts;

	static AccountManagerOld manager;
	AccountManagerOld(){
		accounts=new Vector();
		//loadFiles();
	}
	/*
	 * need to do file loading and saveing of account data
	 *  
	 */
	public Account getAccount(String name){
		Enumeration e=accounts.elements();
		Account a;
		while(e.hasMoreElements()){
			a=(Account)e.nextElement();
			if (name.equalsIgnoreCase(a.getName())){
				return a;
			}
		}
		a=new Account(name);
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
	public void save(){
		for(Iterator itr=accounts.iterator();itr.hasNext();){
			((Account)itr.next()).save();
		}
	}
	public List<Account> getAccounts(){
		return accounts;
	}
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
	public static AccountManagerOld getAccountManger(){
		if(manager==null){
			manager=new AccountManagerOld();
		}
		return manager;
	}
	public List<Account> getJoker(){
		List joker=new Vector();
		return joker;
	}
}
