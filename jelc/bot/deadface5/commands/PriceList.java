package bot.deadface5.commands;
import java.util.*;
import java.io.*;

import bot.deadface5.BotCommand;
import bot.deadface5.MyBot;
import bot.deadface5.Replyer;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PriceList implements BotCommand {
File filename;
Vector  list;
MyBot bot;
	PriceList(MyBot bot){
		this.filename=new File("priceList.txt");
		this.bot=bot;
		load();
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
			     	//System.out.println("line" +tmp);
			     	list.add(new Data(tmp));
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
			Enumeration e=list.elements();
			while(e.hasMoreElements()){
				out.println(((Data) e.nextElement()).dump());
			}
			out.close();
			return true;
		     
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void dump(){
		Enumeration e=list.elements();
		System.out.println("---");
		while(e.hasMoreElements()){
			System.out.println(e.nextElement());
		}
	}

	class Data{
		private int price;
		private String tag;
		private String category;
		private String name;
		public Data(String data)throws NumberFormatException{
			StringTokenizer st=new StringTokenizer(data);
			this.price=Integer.parseInt(st.nextToken());
			this.tag=st.nextToken();
			this.category=st.nextToken();
			this.name=st.nextToken();
			while (st.hasMoreTokens()){
				this.name=this.name+st.nextToken();
			}
			
		}
		public Data(int price, String tag, String category, String name){
			this.price=price;
			this.tag=tag;
			this.category=category;
			this.name=name;
		}
		/**
		 * @param price The price to set.
		 */
		void setPrice(int price) {
			this.price = price;
		}
		/**
		 * @param tag The tag to set.
		 */
		void setTag(String tag) {
			this.tag = tag;
		}
		/**
		 * @param name The name to set.
		 */
		void setName(String name) {
			this.name = name;
		}
		/**
		 * @return Returns the name.
		 */
		String getName() {
			return name;
		}
		/**
		 * @return Returns the tag.
		 */
		String getTag() {
			return tag;
		}
		/**
		 * @param category The category to set.
		 */
		void setCategory(String category) {
			this.category = category;
		}
		/**
		 * @return Returns the category.
		 */
		String getCategory() {
			return category;
		}
		/**
		 * @return Returns the price.
		 */
		int getPrice() {
			return price;
		}
		public String dump(){
			return getPrice()+" "+getTag()+" "+getCategory()+" "+getName();
		}
	}
	public boolean process(Replyer reply, String[] args){
		if(args[0].equalsIgnoreCase("price")){
			if(args.length>2){
				if(reply.getAccount().isAdmin()){
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply){
		reply.reply("price         - displays top 10 people to find the joker(that i've seen)");
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}
}

