/*
 * Created on 10/10/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package playerView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Iterator;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class  PlayerList {
	String name;
	Vector list;
	public PlayerList(String name,Vector list){
		this.name=name;
		this.list=list;
	}
	
	public PlayerList(File f){
		
		loadFile(f);
	}
	
	public boolean loadFile(File f){
		if (f.exists()&&f.isFile()){
			list=new Vector();
			try {
				FileInputStream fis=new FileInputStream(f);
			     BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			     name=br.readLine();
			     br.readLine();
			     String tmp=br.readLine();
			     while(tmp!=null){
				     tmp=tmp.toLowerCase();
			    	 if(!list.contains(tmp)){
			    		 list.add(tmp);
			    	 }
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
	public boolean save(File f){
		try {
			/*FileOutputStream fos=new FileOutputStream(f);
			DataOutputStream out = new DataOutputStream(fos);*/
			PrintWriter out=new PrintWriter(f); 
			out.println(name);
			out.println("");
			out.flush();
			Enumeration e=list.elements();
			while(e.hasMoreElements()){
				out.println(e.nextElement());
			}
			out.close();
		     
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	/**
	 * @return Returns the list.
	 */
	public Vector getList() {
		return list;
	}
	/**
	 * @param list The list to set.
	 */
	public void setList(Vector list) {
		this.list = list;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean contains(String str){
		for(Iterator itr=list.iterator();itr.hasNext();){
			String next=itr.next().toString();
			if(next.equalsIgnoreCase(next)){
				return true;
			}
		}
		return false;
	}
	public String toString(){
		return name;
	}
}
