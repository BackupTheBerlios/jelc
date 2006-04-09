package bot.deadface5.commands;
import java.util.*;

import bot.deadface5.BotCommand;
import bot.deadface5.Misc;
import bot.deadface5.MyBot;
import bot.deadface5.Replyer;


import jelc.playerView.*;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Friends implements BotCommand {
//Vector list;
MyBot bot;
	public Friends(MyBot bot){
		//list=new Vector();
		//buildList();
		this.bot=bot;
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args){
		//System.out.println("sddfsfdsdffsdfsdfss");
    	if(args[0].equals("friends")|args[0].equals("friend")){
    		//PlayerList pl=getList(reply.getName());
    		if((args.length==3)){
        		if(args[1].equalsIgnoreCase("add")){
        			if(reply.getAccount().addFriend(args[2])){
        				reply.reply("added: "+args[2]+" to your friends list");
        			}
        			else{
        				reply.reply("error player was not added :"+args[2]);
        			}
        		}
        		else if((args[1].equalsIgnoreCase("remove"))||(args[1].equalsIgnoreCase("del"))||(args[1].equalsIgnoreCase("rem"))){
        			if(reply.getAccount().removeFriend(args[2])){
        				reply.reply("removed: "+args[2]+" from your friends list");
        			}
        			else{
        				reply.reply("error player was not removed :"+args[2]);
        			}
        		}
        		reply.getAccount().save();
    		}
    		else{
    			
    			if(reply.getAccount().getFriends().size()!=0){
    				Vector on=new Vector();
    				Vector off=new Vector();
    				PlayersOnline online=PlayersOnline.getInstance();
    				Iterator itr=reply.getAccount().getFriends().iterator();
    				while(itr.hasNext()){
    					String friend=(String) itr.next();
    					if(online.parseOnline().contains(friend)){//not found
    						off.add(friend+"*");
    					}
    					else{
    						on.add(friend);
    					}
    				}
    				on.addAll(off);
    				Misc.ouputList(reply,"Friends: ",on.iterator());
    				reply.reply("* denotes they are not online");
    				
    				/*
        			List l=bot.online.checkOnline(pl.getList());
    				bot.outputList(name,type,"Friends:",l.iterator());*/
    			}
    			else{
    				reply.reply("sorry you don't have any friends in my list, use: friends add playername");
    			}
    		}
    		/*
    		try {
				save(pl);
			} catch (IOException e) {
				System.err.println("error saveing friends: "+e.getLocalizedMessage());
			}*/
    		return true;
    	}
    	return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply) {
		reply.reply("friends       - your friends online");
		reply.reply("friends add   - add a friend to your list");
		reply.reply("friends del   - remove a friend from your list");
	}

	/*public void  buildList(){
		
		File f=new File("./Friends/");
		System.out.println("loading lists from: "+f);
		File[] files=f.listFiles();
		for(int i=0;i<files.length;i++){
			if (files[i].isFile()&&files[i].canRead()){
				System.out.println("- processing file: "+files[i]);
				list.add(new PlayerList(files[i]));
			}
		}
	}*/
	/*
	 * return Returns the list.
	 */
	/*public Vector getGuildList() {
		return reply.getAccount().getFriends();
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
	}*/
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}
}
