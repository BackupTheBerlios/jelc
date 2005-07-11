/**
 * 
 */
package bots.deadface2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import bots.deadface2.JokerList.Pair;
import java.util.*;
import java.io.*;
/**
 * @author dns
 *
 */
public class UsernameTable implements BotCommand{
Hashtable table;
File filename;
MyBot2 bot;
	/**
	 * 
	 */
	public UsernameTable(MyBot2 bot) {
		this.bot=bot;
		filename=new File("UserTable.txt");
		load();
	}
	public boolean load(){
		return load(filename);
	}
	public boolean load(File f){
		table=new Hashtable();
		if (f.exists()&&f.isFile()){
			try {
				FileInputStream fis=new FileInputStream(f);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				System.out.println("loading file:"+f);
				String tmp=br.readLine();
				while(tmp!=null){
			     	//System.out.println("line" +tmp);
			     	StringTokenizer st=new StringTokenizer(tmp);
			     	table.put(st.nextToken(),st.nextToken());
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
	public boolean save(){
		return save(filename);
	}
	public boolean save(File f){
		try {
			PrintWriter out=new PrintWriter(f); 
			Enumeration e=table.keys();
			while(e.hasMoreElements()){
				Object o=e.nextElement();
				out.println(o.toString()+" "+ table.get(o).toString());
			}
			out.close();
			return true;
		     
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean process(String name, String[] args, int type) {
		if(bot.guild.isGuild(name)){
			if(args[0].equalsIgnoreCase("ircRegister")&&(args.length==2)){
				table.put(args[1],name);
				bot.reply(name,"you are now registered with the irc bot, you can now use my bot through irc",type);
				save();
				return true;
			}
		}
		return false;
	}
	public void sendHelp(String name, int type) {
		if(bot.admin.isAdmin(name)){
		}
	}
	String getGuildName(String name){
		return (String)table.get(name);
	}
	public void onQuit() {
		save();
	}
}
