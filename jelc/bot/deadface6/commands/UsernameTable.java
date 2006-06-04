/**
 * 
 */
package bot.deadface6.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import bot.deadface6.BotCommand;
import bot.deadface6.MyBot;
import bot.deadface6.Replyer;


/**
 * @author dns
 *
 */
public class UsernameTable implements BotCommand{
Hashtable table;
File filename;
MyBot bot;
	/**
	 * 
	 */
	public UsernameTable(MyBot bot) {
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
	public boolean process(Replyer reply, String[] args){
		if(reply.getAccount().isSameGuild()){
			if(args[0].equalsIgnoreCase("ircRegister")&&(args.length==2)){
				table.put(args[1],reply.getAccount().getName());
				reply.reply("you are now registered with the irc bot, you can now use my bot through irc");
				save();
				return true;
			}
		}
		else if(reply.getAccount().isAdmin()&&args[0].equalsIgnoreCase("update")){
			load();
			return true;
		}
		return false;
	}
	public void sendHelp(Replyer reply) {
		/*if(bot.admin.isAdmin(name)){
		}*/
	}
	public String getGuildName(String name){
		return (String)table.get(name);
	}
	public void onQuit() {
		save();
	}
}
