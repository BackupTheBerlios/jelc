package bots.deadface;
import java.util.*;
import java.io.*;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JokerList {
File filename=new File("jokerList.txt");
Vector  list;
	JokerList(){
		load();
	}
	public boolean load(){
		return load(filename);
	}
	public boolean load(File f){

		if (f.exists()&&f.isFile()){
			list=new Vector();
			try {
				FileInputStream fis=new FileInputStream(f);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				System.out.println("loading file:"+f);
				String tmp=br.readLine();
				while(tmp!=null){
			     	//System.out.println("line" +tmp);
			     	StringTokenizer st=new StringTokenizer(tmp);
			     	list.add(new Pair(st.nextToken(),Integer.parseInt(st.nextToken())));
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
		else{
			System.err.println("bad file: "+f.getName());
			return false;
		}
	}
	public boolean found(String name){
		//sort();
		Enumeration e=list.elements();
		while(e.hasMoreElements()){
			Pair p=(Pair)e.nextElement();
			//System.out.println("+"+p);
			if(p.getName().equals(name)){
				p.incrementCount();
				return true;
			}
		}
		return list.add(new Pair(name,1));
		
	}
	public boolean save(){
		return save(filename);
	}
	public boolean save(File f){
		try {
			PrintWriter out=new PrintWriter(f); 
			Enumeration e=list.elements();
			while(e.hasMoreElements()){
				out.println(e.nextElement());
			}
			out.close();
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

	public void dump(){
		Enumeration e=list.elements();
		System.out.println("---");
		while(e.hasMoreElements()){
			System.out.println(e.nextElement());
		}
	}
	public void sort(){
		if(list.size()!=0){
			Collections.sort(list,(Pair)list.get(0));
		}
	}
	class Pair implements Comparator{
		String name;
		int count;
		Pair(String name, int count){
			this.name=name;
			this.count=count;
			sort();
		}
		/**
		 * @return Returns the count.
		 */
		public int getCount() {
			return count;
		}
		/**
		 * @param count The count to set.
		 */
		public void setCount(int count) {
			this.count = count;
		}
		public void incrementCount(){
			count=count+1;
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
		public String toString(){
			return name+" "+count;
		}
		public int compare(Object a, Object b){
			int aval=((Pair)a).count;
			int bval=((Pair)b).count;
			if(aval>bval){
				return -1;
			}
			else if(aval<bval){
				return 1;
			}
			else{
				return 0;
			}
			 
		}
		public boolean equals(Pair o){
			return name.equals(o.name);
		}
	}
}

