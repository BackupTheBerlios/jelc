/*
 * Created on 8/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import jelc.playerView.Player;

public class StatsCache {
private static StatsCache cache;
static long cachetime=1800000;
//List players;
	private StatsCache(){
		//players=new Vector();
	}
	public Player getPlayer(String name){
		/*for(Iterator itr=players.iterator();itr.hasNext();){
			Player tmp=(Player)itr.next();
			if(tmp.name.equalsIgnoreCase(name)){
				return tmp;
			}
		}*/
		name=name.toLowerCase();
		Player tmp=new Player(name);
		File f=new File("./cache/"+name+".php");
		if(f.isFile()&&f.canRead()){
			if((f.lastModified()+cachetime)>System.currentTimeMillis()){
				System.out.println("useing local stats file: "+f);
				tmp.Parse(f);
				tmp.setStatus(Player.PARSEDOK);
				return tmp;
			}
			f.delete();
		}
		
		
		//last resort just parse the page
		int code=tmp.Parse();
		if(code==Player.PRIVACYON){
			//System.out.println("privacy");
			tmp.Parse(new File("./stats/"+name+".php"));
			//System.out.println("privacy on, loaded from cache"+status);
		}
		tmp.save(f);
		//players.add(tmp);
		return tmp;
	}
	
	public int cache(String name){
		URL url;
		try {
			
			File f=new File("./stats/"+name+".php");
			//get the page
			url = new URL("http://eternal-lands.solexine.fr/~radu/view_user.php?user="+name);
			URLConnection con=url.openConnection();
			
			InputStream in=con.getInputStream();
			FileOutputStream out=new FileOutputStream(f);
			int data=in.read();
			while(data!=-1){
				out.write(data);
				data=in.read();
			}
			out.close();
			in.close();
			
			
			//test it
			Player player=new Player(name);
			int status=player.Parse(f);
			if(status!=Player.INCACHE){//tried to parse but it failed (could have privacy on)
				f.delete();
				return status;
			}
			//players.add(player);
			return status;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return Player.PAGENOTFOUND;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Player.IOEXCEPTION;
		}
	}
	
	

	
	public static StatsCache getInstance(){
		if(cache==null){
			cache=new StatsCache();
		}
		return cache;
	}
}
