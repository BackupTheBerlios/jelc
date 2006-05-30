package bot.deadface4.commands;


//import java.io.File;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

import bot.deadface4.BotCommand;
import bot.deadface4.Misc;
import bot.deadface4.MyBot4;
import bot.deadface4.Replyer;

import playerView.PlayerList;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class News implements BotCommand{
//Vector gtpm;
MyBot4 bot;
Vector list;
//Vector told;
PlayerList toldList;
	public News(MyBot4 bot){
		this.bot=bot;
		list=new Vector();
		toldList=new PlayerList(new File("told.txt"));
		//told=toldList.getList();
		load();
	}
	public void save(){
		save(new File("news.txt"));
	}
	public void load(){
		load(new File("news.txt"));
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args){
		if((args[0].equalsIgnoreCase("putnews")&&reply.getAccount().isAdmin())){
			if(args.length!=1){
				String command=args[1];
				for(int i=2;i<args.length;i++){
					command=command+" "+args[i];
				}
				//System.out.println("got a command from person: "+reply.getName()+" '"+command+"'");
				list.add(new Message(reply.getName(),command));
				save();
			}
			return true;
		}
		else if((args[0].equalsIgnoreCase("delnews")&&reply.getAccount().isAdmin())){
			if(args.length==2){
				int story;
				try{
					story=Integer.parseInt(args[1]);
					if(story<list.size()){
						list.remove(story);
					}
					else{
						reply.reply("bad story");
					}
				}
				catch(NumberFormatException nfe){
					reply.reply("error parseing:"+nfe.getLocalizedMessage());
				}
			}
			return true;
		}
		//System.out.println("size: "+toldList.getList().size());
		if(!toldList.contains(reply.getName())){
			if(reply.getType()!=Misc.GM){
				for(Iterator itr=list.iterator();itr.hasNext();){
					/*String message=itr.next().toString();
					System.out.println("message: "+message);*/
					reply.reply(itr.next().toString());
					
				}
				toldList.getList().add(reply.getName());
				toldList.save(new File("told.txt"));
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		reply.reply("msg message   = sends a message to dns, if you want me to add a feature do this.");
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
				//System.out.println("loading file:"+f);
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