/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface5.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

import bot.deadface5.BotCommand;
import bot.deadface5.MyBot;
import bot.deadface5.Replyer;
/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PkList implements BotCommand {
MyBot bot;
Vector list;
File filename;
	public PkList(MyBot bot){
		this.bot=bot;
		filename=new File("pkList.txt");
		load();
		
		System.out.println("PK LIST STARTED!!!!");
	}
	public boolean load(){
		return load(filename);
	}
	public boolean load(File f){

		if (f.exists()&&f.isFile()){
			list=new Vector();
			try {
				FileInputStream fis=new FileInputStream(f);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				System.out.println("loading file:"+f);
				String tmp=br.readLine();
				while(tmp!=null){
			     	list.add(new Entry(tmp));
			     	tmp=br.readLine();
			     }
			     return true;
			     
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		System.err.println("bad file: "+f.getName());
		return false;
	}

	public boolean save(){
		return save(filename);
	}
	public boolean save(File f){
		try {
			PrintWriter out=new PrintWriter(f); 
			Enumeration e=list.elements();
			while(e.hasMoreElements()){
				out.println(((Entry)e.nextElement()).dump());
			}
			out.close();
			return true;
		     
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args){
		if((args[0].equalsIgnoreCase("#add_PK")||args[0].equalsIgnoreCase("add_pk"))&&(args.length>3)){
			String str=args[2];
			for(int i=3;i<args.length;i++){
				str=str+" "+args[i];
			}
			list.add(new Entry(args[1],reply.getAccount().getName(),str));
			reply.reply("added: "+args[1] +" to the pk list");
			save();
		}
		else if(args[0].equalsIgnoreCase("list_pk")||args[0].equalsIgnoreCase("#list_pk")){
			//bot.outputList(name,type,"Your PK LIST: ",list.iterator());
			reply.reply("The PK LISt: ");
			for(int i=0;i<list.size();i++){
				reply.reply((i+1)+": "+list.get(i).toString());
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		if(reply.getAccount().isSameGuild()){
			reply.reply("add_PK        - add");
			reply.reply("list_PK       - lists members on your pk list");
		}
	}
	public void onQuit() {
		
	}
	private class Entry{
		String name;
		String reporter;
		String reson;
		Entry(String name, String reporter,String reson){
			this.name=name;
			this.reporter=reporter;
			this.reson=reson;
		}
		Entry(String tmp){
			int index1=tmp.indexOf(" ");
			int index2=tmp.indexOf(" ",index1+1);
			name=tmp.substring(0,index1);
			reporter=tmp.substring(index1+1,index2);
			reson=tmp.substring(index2+1);
		}
		String dump(){
			return name+" "+reporter+" "+reson;
		}
		public String toString(){
			return name+": "+reson;
		}
	}
	public boolean isPK(String name){
		Iterator itr=list.iterator();
		while(itr.hasNext()){
			if(((Entry)itr.next()).name.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
}
