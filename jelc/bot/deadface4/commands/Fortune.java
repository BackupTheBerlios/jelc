/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface4.commands;

import java.util.*;
import java.io.*;

import bot.deadface4.BotCommand;
import bot.deadface4.Misc;
import bot.deadface4.MyBot4;
import bot.deadface4.Replyer;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Fortune implements BotCommand {
MyBot4 bot;
Random ran;
Vector categorys;
	public Fortune(MyBot4 bot){
		this.bot=bot;
		ran=new Random();
		categorys=new Vector();
		load();
	}
	Fortune(){
		ran=new Random();
		categorys=new Vector();
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args){
		if(args[0].equalsIgnoreCase("fortune")&&(args.length==1)){
			List tmp=getRandomFortune();
			//System.out.println("-----"+tmp.size());
			for(Iterator itr=tmp.iterator();itr.hasNext();){
				reply.reply(itr.next().toString().trim());
			}
			return true;
		}
		else if(args[0].equalsIgnoreCase("fortune")&&(args.length==2)){
			if(args[1].equalsIgnoreCase("list")||args[1].equalsIgnoreCase("lists")){
				Misc.ouputList(reply,"lists: ",categorys.iterator());
			}
			else{
				Iterator itr=categorys.iterator();
				boolean found=false;
				while(itr.hasNext()&&(!found)){
					fortuneList fl=(fortuneList)itr.next();
					if(fl.getName().equalsIgnoreCase(args[1])){
						List l=fl.getFortune();
						for(int i=0;i<l.size();i++){
							//System.out.println("|"+l.get(i).toString().trim());
							if(reply.getType()==Misc.PM){
								reply.reply(l.get(i).toString().trim());
							}
							else if((reply.getType()==Misc.IRC)||(reply.getType()==Misc.IRCPM)){
								reply.reply((char)3+"6"+l.get(i).toString()+(char)3);
							}
						}
//						bot.outputList(name,type,"",l.iterator());
						found=true;
					}
				}
				if(!found){
					reply.reply("error the category does not exist, to find lists: fortune list");
				}
			}
			return true;
		}
    	else if(reply.getAccount().isAdmin()){
    		if(args[0].equalsIgnoreCase("update")){
    			load();
        		return true;
    		}
    	}
		return false;
	}
	public void load(){
		File f=new File("./fortunes/");
		System.out.println(f);
		File[] files=f.listFiles();
		if(f.exists()&&f.isDirectory()){
			categorys.clear();
			for(int i=0;i<files.length;i++){
				if (files[i].isFile()&&files[i].canRead()){
					fortuneList tmp=new fortuneList(files[i]);
					tmp.load();
					categorys.add(tmp);
				}
			}
		}
		else{
			System.err.println("no lists loaded. put some txt files in ./lists/");
		}
	}
	public List getRandomFortune(){
		fortuneList fl=(fortuneList)categorys.get(ran.nextInt(categorys.size()));
		return fl.getFortune();
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply) {
		reply.reply("fortune [list]- displays a random fortune from a random or the specified category");
		reply.reply("fortune list  - show a list of possible categories");
	}
	public class fortuneList{
		Vector fortune;
		File file;
		fortuneList(File f){
			file=f;
		}
		public void load(){
			//System.out.println("loading: "+file+","+getName());
			//load(file);
		}
		public boolean load(File f){
			if (f.exists()&&f.isFile()){
				try {
					FileInputStream fis=new FileInputStream(f);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					String tmp=br.readLine();
					while(tmp!=null){
						Vector list=new Vector();
						while((tmp!=null)&&(!tmp.equals("%"))){
							list.add(tmp);
							tmp=br.readLine();
						}
						fortune.add(list);
						tmp=br.readLine();
					}
					//System.out.println("-loaded fortune file: "+f+" "+fortune.size());
					//save(f);
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
		public List getFortune(){
			//System.out.println("loading file"+file+fortune);
			if(fortune==null){
				fortune=new Vector();
				load(file);
			}
			List tmp=(List)fortune.get(ran.nextInt(fortune.size()));
			//System.out.println(tmp.size());
			return tmp;
		}
		String getName(){
			return file.getName();
		}
		public String toString(){
			return getName();
		}
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}	
}
