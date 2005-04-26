package bots.deadface;

import java.util.*;
import java.io.*;

/**
 * 
 * 
 * @author dns
 */
public class SeenList implements Runnable{
Vector list;
File file;
	/**
	 * 
	 */
	public SeenList(File f) {
		this.file=f;
		load(f);
	}
	public boolean load(File f){
		if (f.exists()&&f.isFile()){
			list=new Vector();
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
					
					if(index==-1){
						list.add(new Person(tmp.substring(0,indexTime),"",time));
					}
					else{
						name=tmp.substring(0,index);
						guild=tmp.substring(index+1,indexTime);
						list.add(new Person(name,guild,time));
					}
					
					//list.add(tmp);
					tmp=br.readLine();
				}
				System.err.println("seen list, loaded file: "+f);
				return true;
			     
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		else{
			System.err.println("bad file: "+f.getName());
			return false;
		}
	}
	public synchronized boolean save(File f){
		try {
			PrintWriter out=new PrintWriter(f); 
			Enumeration e=list.elements();
			while(e.hasMoreElements()){
				Person p=(Person)e.nextElement();
				out.println(p.toString()+" "+p.getLastSeen());
			}
			out.close();
			
		     
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean addPlayer(String name,String guild){
		Person p=new Person(name,guild);
		Person looking=find(p);
		if(looking==null){
			boolean res=list.add(new Person(name,guild.toUpperCase(),System.currentTimeMillis()));
			System.out.println("added: " +p);
			if(res){
				new Thread(this).start();
			}
			return res;
		}
		else{
			looking.setLastSeen(System.currentTimeMillis());
			System.out.println("updated: "+looking);
			new Thread(this).start();
		}
		return true;
	}
	public List getSeen(){
		return list;
	}
	public Person find(Person person){
		Iterator i=list.iterator();
		Person p;
		while(i.hasNext()){
			p=(Person)i.next();
			if(p.equals(person)){
				return p;
			}
		}
		return null;	
	}
	public Person find(String name){
		Iterator i=list.iterator();
		Person p;
		while(i.hasNext()){
			p=(Person)i.next();
			if(p.name.equals(name)){
				return p;
			}
		}
		return null;	
	}
	
	public List getGuild(String guild){
		Vector tmp=new Vector();
		Iterator i=list.iterator();
		Person p;
		while(i.hasNext()){
			p=(Person)i.next();
			if(p.getGuild().equalsIgnoreCase(guild)){
				tmp.add(p.getName());
			}
		}
		return tmp;
	}
	public class Person{
		String name;
		String guild;
		private long lastSeen;
		Person(String name, String guild){
			this.name=name;
			this.guild=guild;
		}
		Person(String name, String guild, long lastSeen){
			this.name=name;
			this.guild=guild;
			this.setLastSeen(lastSeen);
		}
		public boolean equals(Person p){
			return (p.name).equals(name);
		}
		public String toString(){
			if(!guild.equals("")){
				return name+"."+guild;
			}
			return name;
		}
		public String getName(){
			return name;
		}
		public String getGuild(){
			return guild;
		}
		/**
		 * @param lastSeen The lastSeen to set.
		 */
		void setLastSeen(long lastSeen) {
			this.lastSeen = lastSeen;
		}
		/**
		 * @return Returns the lastSeen.
		 */
		long getLastSeen() {
			return lastSeen;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		save(file);
	}
	
	public static void main(String[] args) {
		new SeenList(new File("seen.txt")).save(new File("seen.txt"));
	}
}
