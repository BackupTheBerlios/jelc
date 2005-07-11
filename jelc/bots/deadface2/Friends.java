package bots.deadface2;
import java.io.File;
import java.io.IOException;
import java.util.*;

import playerView.Player;
import playerView.PlayerList;
import playerView.PlayersOnline;
/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Friends implements BotCommand {
Vector list;
MyBot2 bot;
	public Friends(MyBot2 bot){
		list=new Vector();
		buildList();
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(String name, String[] args, int type) {
		//System.out.println("sddfsfdsdffsdfsdfss");
    	if(args[0].equals("friends")|args[0].equals("friend")){
    		PlayerList pl=getList(name);
    		if((args.length==3)){
        		if(args[1].equalsIgnoreCase("add")){
        			if(pl.getList().add(args[2])){
        				bot.reply(name,"added: "+args[2]+" to your friends list",type);
        			}
        			else{
        				bot.reply(name,"error player was not added :"+args[2],type);
        			}
        		}
        		else if((args[1].equalsIgnoreCase("remove"))||(args[1].equalsIgnoreCase("del"))||(args[1].equalsIgnoreCase("rem"))){
        			if(pl.getList().remove(args[2])){
        				bot.reply(name,"removed: "+args[2]+" from your friends list",type);
        			}
        			else{
        				bot.reply(name,"error player was not removed :"+args[2],type);
        			}
        		}
    		}
    		else{
    			
    			if(list.size()!=0){
    				Vector on=new Vector();
    				Vector off=new Vector();
    				bot.online.load();
    				Iterator itr=pl.getList().iterator();
    				while(itr.hasNext()){
    					String s=(String) itr.next();
    					Player p=bot.online.findIn(s);
    					//System.out.println(" +"+s+" "+p);
    					if(p==null){//not found
    						off.add(s+"*");
    					}
    					else{
    						on.add(s);
    					}
    				}
    				on.addAll(off);
    				bot.outputList(name,type,"Friends:",on.iterator());
    				bot.reply(name,"* denotes they are not online",type);
    				
    				/*
        			List l=bot.online.checkOnline(pl.getList());
    				bot.outputList(name,type,"Friends:",l.iterator());*/
    			}
    			else{
    				bot.reply(name,"sorry you don't have any friends in my list, use: friends add playername",type);
    			}
    		}
    		try {
				save(pl);
			} catch (IOException e) {
				System.err.println("error saveing friends: "+e.getLocalizedMessage());
			}
    		return true;
    	}
    	return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {
		bot.reply(name,"friends       - your friends online",type);
		bot.reply(name,"friends add   - add a friend to your list",type);
		bot.reply(name,"friends del   - remove a friend from your list",type);
	}

	public void  buildList(){
		
		File f=new File("./Friends/");
		System.out.println("loading lists from: "+f);
		File[] files=f.listFiles();
		for(int i=0;i<files.length;i++){
			if (files[i].isFile()&&files[i].canRead()){
				System.out.println("- processing file: "+files[i]);
				list.add(new PlayerList(files[i]));
			}
		}
	}
	/**
	 * @return Returns the list.
	 */
	public Vector getGuildList() {
		return list;
	}
	public PlayerList retrieve(String name){
		Enumeration e=list.elements();
		PlayerList tmp;
		while(e.hasMoreElements()){
			tmp=(PlayerList)e.nextElement();
			if(tmp.getName().equals(name)){
				return tmp; 
			}
		}
		return null;
	}
	public PlayerList getList(String name){
		for(Iterator itr=list.iterator();itr.hasNext();){
			PlayerList tmp=(PlayerList)itr.next();
			if(tmp.getName().equals(name)){
				return tmp;
			}
		}
		PlayerList tmp=new PlayerList(name,new Vector());
		list.add(tmp);
		return tmp;
	}
	public void save(){
		for(Iterator itr=list.iterator();itr.hasNext();){
			try {
				save((PlayerList)itr.next());
			} catch (IOException e) {
				System.err.println("Error: "+e.getLocalizedMessage());
			}
		}
	}
	private boolean save(PlayerList pl) throws IOException{
		File f=new File("./Friends/"+pl.getName());
		if(!f.exists()){
			f.createNewFile();
		}
		return pl.save(f);
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}
}
