package bot.deadface5.tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

import bot.deadface5.*;


public class ImportJoker {

	public ImportJoker() {
		super();
		AccountManager manager=AccountManager.getAccountManger();
		File f=new File("jokerList.txt");
			if (f.exists()&&f.isFile()){
				try {
					FileInputStream fis=new FileInputStream(f);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					System.out.println("loading file:"+f);
					String tmp=br.readLine();
					while(tmp!=null){
				     	//System.out.println("line" +tmp);
				     	StringTokenizer st=new StringTokenizer(tmp);
				     	
				     	Account a=manager.getAccount(st.nextToken());
				     	a.setJokerCount(a.getJokerCount()+Integer.parseInt(st.nextToken()));
				     	a.save();
				     	tmp=br.readLine();
				     }
				     System.out.println("Conversion complete");
				     
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else{
				System.err.println("bad file: "+f.getName());
			}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ImportJoker();
	}

}
