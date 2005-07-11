/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bots.deadface2;

import java.io.File;
import java.util.Iterator;
//import java.util.List;
import java.util.Vector;

import playerView.PlayerList;
import playerView.PlayersOnline;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Online implements BotCommand {
PlayersOnline online;
SeenList2 seen;
MyBot2 bot;
Vector views;
PlayerList mod;
	Online(PlayersOnline on, SeenList2 seen2,MyBot2 bot){
		this.online=on;
		this.seen=seen2;
		this.bot=bot;
		buildList();
	}
	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(String name, String[] args, int type) {
    	if(args[0].equals("online")){
    		System.out.println("mod"+args.length);
    		if(args.length==1){
    			System.out.println("getting mods");
				bot.outputList(name,type, mod.getName()+": ",online.getOnline(mod.getList()).listIterator());
    		}
    		else if(args.length==2){
    			if((args[1].equals("list"))||(args[1].equals("lists"))){
    				bot.outputList(name,type,"Lists: ",views.listIterator());
    			}
    			else{
    				boolean found=false;
    				for(Iterator itr=views.iterator();itr.hasNext();){
    					PlayerList tmp=(PlayerList)itr.next();
    					if(tmp.getName().equals(args[1])){
    						bot.outputList(name,type, tmp.getName()+": ",online.getOnline(tmp.getList()).listIterator());
    						found=true;
    					}
    				}
    				if(!found){
    					bot.reply(name,"Error, the list does not exist",type);
    				}
    	    	}
	    		/*if(args.length==3){
	    			String guild=args[1];
	    			List tmp=seen.getGuild(guild);
	    			System.out.println("guild:"+guild);
	    			if(tmp.size()!=0){
	    				Iterator i=online.getOnline(tmp).iterator();
	        			bot.outputList(name,type,"",i);
	    			}
	    			else{
	    				bot.replyMessage(name,"i have not seen the guild: "+guild,type);
	    			}
	    		}*/
    		}
    		else if(args.length==3){
    			if(args[1].equalsIgnoreCase("showlist")){
    				for(Iterator itr=views.iterator();itr.hasNext();){
    					PlayerList tmp=(PlayerList)itr.next();
    					if(tmp.getName().equalsIgnoreCase(args[2])){
    						bot.outputList(name,type, tmp.getName()+": ",tmp.getList().iterator());
    					}
    				}
    			}
    		}
    		return true;
    	}
    	else if(bot.admin.isAdmin(name)){
    		if(args[0].equalsIgnoreCase("update")){
    			buildList();
    		}
    		return true;
    	}
    	return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(String name, int type) {
		bot.reply(name,"online [list] - shows who is online in that list (default is moderators)",type);
		bot.reply(name,"online list   - display possible lists for the above command",type);
	}
	public void  buildList(){
		views=new Vector();
		File f=new File("./lists/");
		System.out.println(f);
		File[] files=f.listFiles();
		if(f.exists()&&f.isDirectory())
			for(int i=0;i<files.length;i++){
				if (files[i].isFile()&&files[i].canRead()){
					PlayerList tmp=new PlayerList(files[i]);
					System.out.println("adding list: "+files[i].getName());
					if(files[i].getName().equals("mods.txt")){
						System.out.println("mods list found");
						mod=tmp;
					}
					views.add(tmp);
				}
			}
		else{
			System.err.println("no lists loaded. put some txt files in ./lists/");
		}
		if(mod==null){
			mod=new PlayerList("ERROR, NO Lists",new Vector());
		}
	}
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
