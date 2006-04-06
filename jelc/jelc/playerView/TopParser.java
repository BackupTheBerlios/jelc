/*
 * Created on 5-Feb-06
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.playerView;

import java.util.*;
import java.net.*;
import java.io.*;

public class TopParser {
	
	public final static int PARSEDOK=0;
	public final static int PRIVACYON=1;
	public final static int PLAYERDOESNOTEXIST=2;
	public final static int PAGENOTFOUND=3;
	public final static int IOEXCEPTION=4;
	public final static int LOCKED=5;
	public final static int EXCEPTION=6;//unknown exception (probly null pointer)
	public final static int FILENTOFOUND=7;
	
//	 TODO Auto-generated catch block
	List attack;
	List defence;
	List magic;
	List potion;
	List manufacture;
	List crafting;
	List summoning;
	List alchemy;
	List harvest;
	List overall;
	
	
	TopParser(){
		//this(new URL("http://www.other-life.com/el/top/top200.php"));
	}
	public int parse(){
		try{
			URL page = new URL("http://www.other-life.com/el/top/top20.php");
			//page=new URL("file:///home/dns/view_user.php.html");
			URLConnection con = page.openConnection();
			//System.out.println(huc.getHeaderFields());
			//BufferedReader d = new BufferedReader(new InputStreamReader(huc.getInputStream()));
			return parse(con.getInputStream());
	
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("A malformed URL Ecxception ocured :"+e.getMessage());
			System.err.println("The likely cause of this is that the page name has changed");
			return PAGENOTFOUND;
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			System.err.println("An IO exception ocured:"+e1.getMessage());
			System.err.println("This means that there was an error retrieveing the web page");	
			return IOEXCEPTION;
		}
	}
	public int parse(File f){
		try {
			return parse(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return FILENTOFOUND;
		}
	}
	
	public int parse(URL url){
		try{
			//URL page = new URL("http://eternal-lands.solexine.fr/~radu/view_user.php?user="+name);
			//page=new URL("file:///home/dns/view_user.php.html");
			URLConnection con=url.openConnection();
			//System.out.println(huc.getHeaderFields());
			//BufferedReader d = new BufferedReader(new InputStreamReader(huc.getInputStream()));
			return parse(con.getInputStream());
	
		}
		catch (MalformedURLException e) {
			System.err.println("A malformed URL Ecxception ocured :"+e.getMessage());
			System.err.println("The likely cause of this is that the page name has changed");
			return PAGENOTFOUND;
		}
		catch (IOException e1) {
			System.err.println("An IO exception ocured:"+e1.getMessage());
			System.err.println("This means that there was an error retrieveing the web page");	
			return IOEXCEPTION;
		}
		
	}
	public int parse(InputStream in){
//		 TODO Auto-generated catch block
		attack=new Vector();
		defence=new Vector();
		magic=new Vector();
		potion=new Vector();
		manufacture=new Vector();
		crafting=new Vector();
		summoning=new Vector();
		alchemy=new Vector();
		harvest=new Vector();
		overall=new Vector();
		
		BufferedReader d = new BufferedReader(new InputStreamReader(in));
		try {

			//for(int i=0;i<120;i++){
			for(int i=0;i<140;i++){
				d.readLine();
			}
			
			////attack
			String tmp=d.readLine();
			int index=tmp.indexOf("view_user.php?user=");
			System.out.println("Index"+index);
			while(index!=-1){
				index=index+15;
				int index2=tmp.indexOf('"',index);
				String name=tmp.substring(index+4,index2);
				attack.add(name);
				//System.out.println("|"+name);
				
				index=tmp.indexOf("view_user.php?user=",index2);
			}
			
			for(int i=0;i<5;i++){
				d.readLine();
			}
			
			///defence
			tmp=d.readLine();
			index=tmp.indexOf("view_user.php?user=");
			while(index!=-1){
				index=index+15;
				int index2=tmp.indexOf('"',index);
				String name=tmp.substring(index+4,index2);
				defence.add(name);
				index=tmp.indexOf("view_user.php?user=",index2);
			}
			
			
			for(int i=0;i<8;i++){
				d.readLine();
			}
			
			////magic
			tmp=d.readLine();
			index=tmp.indexOf("view_user.php?user=");
			while(index!=-1){
				index=index+15;
				int index2=tmp.indexOf('"',index);
				String name=tmp.substring(index+4,index2);
				magic.add(name);
				index=tmp.indexOf("view_user.php?user=",index2);
			}			

			for(int i=0;i<5;i++){
				d.readLine();
			}
			
			////potion
			tmp=d.readLine();
			index=tmp.indexOf("view_user.php?user=");
			while(index!=-1){
				index=index+15;
				int index2=tmp.indexOf('"',index);
				String name=tmp.substring(index+4,index2);
				potion.add(name);
				index=tmp.indexOf("view_user.php?user=",index2);
			}
			
			
			for(int i=0;i<8;i++){
				d.readLine();
			}
			
			////manu
			tmp=d.readLine();
			index=tmp.indexOf("view_user.php?user=");
			while(index!=-1){
				index=index+15;
				int index2=tmp.indexOf('"',index);
				String name=tmp.substring(index+4,index2);
				manufacture.add(name);
				index=tmp.indexOf("view_user.php?user=",index2);
			}			

			for(int i=0;i<5;i++){
				d.readLine();
			}
			
			////crafting
			tmp=d.readLine();
			index=tmp.indexOf("view_user.php?user=");
			while(index!=-1){
				index=index+15;
				int index2=tmp.indexOf('"',index);
				String name=tmp.substring(index+4,index2);
				crafting.add(name);
				index=tmp.indexOf("view_user.php?user=",index2);
			}
			
			
			for(int i=0;i<8;i++){
				d.readLine();
			}
			
			////Summoning
			tmp=d.readLine();
			index=tmp.indexOf("view_user.php?user=");
			while(index!=-1){
				index=index+15;
				int index2=tmp.indexOf('"',index);
				String name=tmp.substring(index+4,index2);
				summoning.add(name);
				index=tmp.indexOf("view_user.php?user=",index2);
			}			

			for(int i=0;i<5;i++){
				d.readLine();
			}
			
			////alchemy
			tmp=d.readLine();
			index=tmp.indexOf("view_user.php?user=");
			while(index!=-1){
				index=index+15;
				int index2=tmp.indexOf('"',index);
				String name=tmp.substring(index+4,index2);
				alchemy.add(name);
				index=tmp.indexOf("view_user.php?user=",index2);
			}
			
			
			for(int i=0;i<8;i++){
				d.readLine();
			}
			
			////harvest
			tmp=d.readLine();
			index=tmp.indexOf("view_user.php?user=");
			while(index!=-1){
				index=index+15;
				int index2=tmp.indexOf('"',index);
				String name=tmp.substring(index+4,index2);
				harvest.add(name);
				index=tmp.indexOf("view_user.php?user=",index2);
			}			

			for(int i=0;i<5;i++){
				d.readLine();
			}
			
			////overall
			tmp=d.readLine();
			index=tmp.indexOf("view_user.php?user=");
			while(index!=-1){
				index=index+15;
				int index2=tmp.indexOf('"',index);
				String name=tmp.substring(index+4,index2);
				overall.add(name);
				index=tmp.indexOf("view_user.php?user=",index2);
			}
			
			
			
			
			return PARSEDOK;
		}
		catch (IOException e1) {
			System.err.println("An IO exception ocured:"+e1.getMessage());
			System.err.println("This means that there was an error retrieveing the web page");	
			return IOEXCEPTION;
		}
		catch(Exception e){
			System.err.println("An UNKNOWN Exception ocured:"+e.getMessage());
			return EXCEPTION;
		}
	}
	
	
	public static void main(String[] args){
		TopParser tmp=new TopParser();
		int status=tmp.parse(new File("top20.php"));
		System.out.println("parsed with the status: "+status);
		int i=0;
		for(Iterator itr=tmp.overall.iterator();itr.hasNext();){
			System.out.println((i++)+":"+itr.next());
		}
	}
}
