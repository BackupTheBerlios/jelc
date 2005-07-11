package bots.deadface2;


//import java.io.File;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

import bots.deadface2.JokerList.Pair;

import elc.ClientInterface;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Messenger implements BotCommand{
//Vector gtpm;
MyBot2 bot;
Vector list;
	Messenger(MyBot2 bot){
		this.bot=bot;
		list=new Vector();
		load();
	}
	public void save(){
		save(new File("messages.txt"));
	}
	public void load(){
		load(new File("messages.txt"));
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(String name, String[] args, int type) {
		if((args[0].equalsIgnoreCase("msg"))||(args[0].equalsIgnoreCase("#msg"))){
			if(args.length!=1){
				String command=args[1];
				for(int i=2;i<args.length;i++){
					command=command+" "+args[i];
				}
				System.out.println("got a command from person: "+name+" '"+command+"'");
				list.add(new Message(name,command));
				save();
			}
			return true;
		}
		else if(args[0].equalsIgnoreCase("getMsg")&&bot.admin.isAdmin(name)){
			if(list.size()==0){
				bot.reply(name,"you have no messages",type);
			}
			else{
				for(int  i=0;i<list.size();i++){
					bot.reply(name,(i+1)+": "+(Message)list.get(i),type);
				}
			}
			return true;
		}
		else if((args[0].equalsIgnoreCase("messenger")||args[0].equalsIgnoreCase("#messenger"))&&bot.admin.isAdmin(name)){
			if((args.length==2)&&args[1].equalsIgnoreCase("get")){
				if(list.size()==0){
					bot.reply(name,"you have no messages",type);
				}
				else{
					for(int  i=0;i<list.size();i++){
						bot.reply(name,(i+1)+": "+(Message)list.get(i),type);
					}
				}
				return true;
			}
			else if((args.length==3)&&args[1].equalsIgnoreCase("del")){
				try{
					int index=Integer.parseInt(args[2]);
					if(index+1<list.size()){
						if(list.remove(index+1)!=null){
							bot.reply(name,"removed message "+index+1,type);
						}
					}
					else{
						bot.reply(name,"bad index"+index,type);
					}
				}
				catch(NumberFormatException nfe){
					bot.reply(name,"error, parseing number: "+nfe.getCause(),type);
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {
		bot.reply(name,"msg message   = sends a message to dns, if you want me to add a feature do this.",type);
	}
	private class Message{
		String name;
		String message;
		Message(String name, String message){
			this.name=name;
			this.message=message;
		}
		public String toString(){
			return name+" "+message;
		}
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
					int index=tmp.indexOf(" ");		     	
			     	list.add(new Message(tmp.substring(0,index),tmp.substring(index+1,tmp.length())));
			     	tmp=br.readLine();
			     }
			     return true;
			     
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		System.err.println("bad file: "+f.getName());
		return false;
	}
	public boolean save(File f){
		try {
			PrintWriter out=new PrintWriter(f); 
			Enumeration e=list.elements();
			while(e.hasMoreElements()){
				out.println(e.nextElement());
			}
			out.close();
			return true;
		     
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}
}
