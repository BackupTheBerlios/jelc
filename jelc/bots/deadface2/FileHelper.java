package bots.deadface2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.util.Enumeration;
//import java.util.StringTokenizer;
import java.util.Vector;
import java.util.*;


//import bots.deadface2.JokerList.Pair;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileHelper {

	public static List load(File f){

			List list=new Vector();
			try {
				FileInputStream fis=new FileInputStream(f);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				System.out.println("loading file:"+f);
				String tmp=br.readLine();
				while(tmp!=null){
			     	list.add(tmp);
			     	tmp=br.readLine();
			     }
			     
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return null;
			}
			return list;
	}
	public static boolean save(File f, List list){
		try {
			PrintWriter out=new PrintWriter(f); 
			Iterator i=list.iterator();
			while(i.hasNext()){
				out.println(i.next());
			}
			out.close();
			return true;
		     
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


}
