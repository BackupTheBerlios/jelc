/*
 * Created on 1/10/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package playerView;
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
Date lastUpdate;
Vector players;
long updateTime=150000;
	public boolean needsUpdate(){
		if(lastUpdate==null){
			return true;
		}
		return (new Date().getTime()-lastUpdate.getTime()>updateTime);
	}
	public PlayersOnline(){
		players=new Vector();
	}
	public  boolean load(){
		boolean sucsess =false;
		for(int i=0;!sucsess&&(i<5);i++){

			if(needsUpdate()){
				//System.out.println("Updateing");
				//lastUpdate=new Date();
				sucsess =doLoad();
				if(!sucsess&&(i<4)){
	//				System.err.println("--will attempt to try again");
				}
			}
			else{
//				System.out.println("Too little time since last update, useing cached version");
				return true;
			}
		}
		
		return sucsess;
	}
	public boolean doLoad(){
		
		URL page;
		try {
			//System.out.println("- retreving who's online");
			page = new URL("http://eternal-lands.solexine.fr/~radu/online_players.htm");
			URLConnection huc=page.openConnection();
			InputStream  is=huc.getInputStream();
			load(is);
			is.close();
			System.out.println("- completed retreving who's online");
			lastUpdate=new Date();
			return true;
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("A malformed URL Ecxception ocured :"+e.getMessage());
			System.err.println("The likely cause of this is that the page name has changed");
			
			return false;
		}
			catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
				System.err.println("An IO exception ocured:"+e1.getMessage());
				System.err.println("This means that there was an error retrieveing the web page");	
			return false;
		}
	}
	public boolean load(InputStream in) throws IOException{
	     BufferedReader dr
         = new BufferedReader(new InputStreamReader(in));
		 String tmp=dr.readLine();
		 for(int i=0;i<9;i++){
			  tmp=dr.readLine();		 	
		 }
		 StringTokenizer st=new StringTokenizer(tmp,">");
		 for(int i=0;i<8;i++){
		 	st.nextToken();
		 }
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
		 return true;

	}
	public Player findIn(String tmp){
		for(int i=0;i<players.size();i++){
			if(((Player)players.get(i)).name.equals(tmp)){
				return (Player)players.get(i);
			}
		}
		return null;
	}
	
	public Vector checkOnline(List mask){
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
	}

	public Vector getOnline(List mask){
		load();
		if(mask!=null){
			Vector tmp=new Vector();
			Vector offline=new Vector();
			for(int i=0;i<mask.size();i++){
				Player player=findIn((String)mask.get(i));
				if(player!=null){
					tmp.add(player);
				}
	
			}	
			return tmp;
		}
		else{
			return players;
		}
	}
	public Vector getOffline(List mask){
		load();
		if(mask!=null){
			Vector tmp=new Vector();
			Vector offline=new Vector();
			for(int i=0;i<mask.size();i++){
				Player player=findIn((String)mask.get(i));
				if(player==null){
					tmp.add(new Player((String)mask.get(i), this));
				}
			}
			return tmp;
		}
		else{
			return players;
		}
	}
	public String toString(){
		return ("All players Online");
	}
	public Vector processList(){
		load();
		return players;
	}

}
 