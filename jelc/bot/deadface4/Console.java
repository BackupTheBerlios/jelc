/**
 * 
 */
package bot.deadface4;



import java.io.*;


/**
 * @author dns
 *
 */
public class Console implements Replyer, Runnable{
MyBot4 bot;

	/**
	 * 
	 */
	public Console(MyBot4 bot) {
		this.bot=bot;
		new Thread(this).start();
	}



	public void run() {
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		try{
			System.out.println("Welcome to the console backend");
			String line = in.readLine();
			while(line!=null){
				System.out.print(":");
				System.out.flush();
				bot.processCommands(this,line);
				line=in.readLine();
			}
		}
		catch(IOException io){
			System.err.println("error reading std in");
		}
	}
	
	public void reply(String str) {
		System.out.print(str);
		System.out.print('\n');
	}


	public int getType() {
		return Misc.CONSOLE;
	}


	public String getName() {
		return "dns";
	}


	public int getMaxMessageSize() {
		return 1024;//could be max size of int but that should be enough
	}


	public void flush() {
		System.out.println();
		//System.out.flush();
	}
	public Account getAccount() {
		return AccountManager.getAccountManger().getAccount("dns");
	}
}
