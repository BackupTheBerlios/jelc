/*
 * Created on 1/10/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.playerView;
import java.util.*;
import java.io.*;
import java.net.*;

/**
 * @author me
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PlayersOnline{
	public final static int OK=0; 
	public final static int PAGENOTFOUND=1;
	public final static int IOEXCEPTION=2;
private Vector<String> players;
private long lastUpdated;
private long cacheTime=50000;

static PlayersOnline online;

	private PlayersOnline(){
		setPlayers(new Vector());
		//parse();
	}
	/**
	 * Parse the online page ignoreing if it has it in cache
	 * @return  status code
	 */
	public  int  parse(){
		URL page;
		try {
			//System.out.println("- retreving who's online");
			page = new URL("http://eternal-lands.solexine.fr/~radu/online_players.htm");
			URLConnection huc=page.openConnection();
			
			
			//parseing bit
			setPlayers(new Vector<String>());
			BufferedReader dr = new BufferedReader(new InputStreamReader(huc.getInputStream()));
			String tmp=dr.readLine();
			for(int i=0;i<9;i++){//skip the first 9 lines
				tmp=dr.readLine();		 	
			}
			String working=tmp;
			int startIndex=working.indexOf("view_user.php?user=");;
			int endIndex=working.indexOf('"',startIndex);
			while(startIndex!=-1){
				startIndex=startIndex+19;//got to add the length of "view_user.php?user=" 
				getPlayers().add(working.substring(startIndex,endIndex));
				startIndex=working.indexOf("view_user.php?user=",endIndex);
				endIndex=working.indexOf('"',startIndex);
			}
			
			
			huc.getInputStream().close();
			//System.out.println("- completed retreving who's online");
			setLastUpdated(System.currentTimeMillis());
			return OK;
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("A malformed URL Ecxception ocured :"+e.getMessage());
			System.err.println("The likely cause of this is that the page name has changed");
			
			return PAGENOTFOUND;
		}
			catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
				System.err.println("An IO exception ocured:"+e1.getMessage());
				System.err.println("This means that there was an error retrieveing the web page");	
			return IOEXCEPTION;
		}
	}
	/*private load(InputStream in) throws IOException{
		//System.out.println("PARSEING");
		
		
		players=new Vector();
	     BufferedReader dr
         = new BufferedReader(new InputStreamReader(in));
		 String tmp=dr.readLine();
		 for(int i=0;i<9;i++){
			  tmp=dr.readLine();		 	
		 }
		 
		 
		 String working=tmp;
		 //System.out.println("WORKING:"+working);
		 int startIndex=working.indexOf("view_user.php?user=");;
		 int endIndex=working.indexOf('"',startIndex);
		 while(startIndex!=-1){
			 startIndex=startIndex+19;//got to add the length of "view_user.php?user=" 
			 players.add(working.substring(startIndex,endIndex));
			 //System.out.println("player: "+working.substring(startIndex,endIndex));
			 startIndex=working.indexOf("view_user.php?user=",endIndex);
			 endIndex=working.indexOf('"',startIndex);
		 }
		 
		 
		 /*StringTokenizer st=new StringTokenizer(tmp,"");
		 for(int i=0;i<8;i++){
		 	st.nextToken();
		 }
		 
		 working.
		 String working=st.nextToken();
		 //System.out.println(working);
		 int online=Integer.parseInt(working.substring(9,13).trim());//how many player online string
		 for(int i=0;i<2;i++){
		 	st.nextToken();
		 }
		 for(int i=0;i<online;i++){
			 working=st.nextToken();
			 //String playerName=st.nextToken().substring(working.length()-2);
			 String playerName=working.substring(29,working.length()-1).trim();
			 players.add(new Player(playerName, this));
		 	st.nextToken();
		 }
	}
	/*public Player findIn(String tmp){
		for(int i=0;i<players.size();i++){
			if(((Player)players.get(i)).name.equalsIgnoreCase(tmp)){
				return (Player)players.get(i);
			}
		}
		return null;
	}*/
	
	/*public Vector checkOnline(List mask){
		load();
		if(mask!=null){
			Vector tmp=new Vector();
			Vector offline=new Vector();
			for(int i=0;i<mask.size();i++){
				Player player=findIn((String)mask.get(i));
				if(player!=null){
					tmp.add(player);
				}
				else{
					offline.add(new Player((String)mask.get(i), this));
				}
			}
			tmp.addAll(offline);
			return tmp;
		}
		return players;
	}*/

	public Vector<String> getOnline(List mask){
		if(needsUpdate()){
			parse();
		}
		Vector<String> res=new Vector();
		for(Iterator itr=mask.iterator();itr.hasNext();){
			String name=itr.next().toString();
			if(players.contains(name)){
				res.add(name);
			}
		}
		
		
		return res;
	}
	/*public Vector getOffline(List mask){
		load();
		if(mask!=null){
			Vector tmp=new Vector();
			for(int i=0;i<mask.size();i++){
				Player player=findIn((String)mask.get(i));
				if(player==null){
					tmp.add(new Player((String)mask.get(i), this));
				}
			}
			return tmp;
		}
		return players;
	}
	public String toString(){
		return ("All players Online");
	}*/
	
	

	/**
	 * @param lastUpdated The lastUpdate to set.
	 */
	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * @return Returns the lastUpdate.
	 */
	public long getLastUpdated() {
		return lastUpdated;
	}
	
	public static PlayersOnline getInstance(){
		if(online==null){
			online=new PlayersOnline();
		}
		return online;
	}

	/**
	 * @param cacheTime The cacheTime to set.
	 */
	public void setCacheTime(long cacheTime) {
		this.cacheTime = cacheTime;
	}

	/**
	 * @return Returns the cacheTime.
	 */
	public long getCacheTime() {
		return cacheTime;
	}

	/**
	 * @param players The players to set.
	 */
	public void setPlayers(Vector<String> players) {
		this.players = players;
	}

	/**
	 * @return Returns the players.
	 */
	public Vector<String> getPlayers() {
		return players;
	}
	/**
	 * First looks to see if there is a recent version in cache or parses the new page
	 * @return a list of players currently online 
	 */
	public Vector<String> parseOnline(){
		if(needsUpdate()){
			parse();
		}
		return players;
	}
	public boolean needsUpdate(){
		//System.out.println("LAST: "+getLastUpdated()+" CACHE TIME: "+getCacheTime()+" CURRENTTIME: "+System.currentTimeMillis()+": "+(System.currentTimeMillis()-getLastUpdated()));
		return(System.currentTimeMillis()-getLastUpdated())>getCacheTime();
	}
}
 