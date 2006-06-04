/**
 * 
 */
package bot.deadface6.backends;





import java.io.*;
import java.util.List;
import java.util.Vector;


import bot.deadface6.Account;
import bot.deadface6.AccountManager;
import bot.deadface6.Misc;
import bot.deadface6.MyBot;
import bot.deadface6.Replyer;


/**
 * @author dns
 *
 */
public class Console implements Backend{
MyBot bot;
ConsoleThread c;
List channels=new Vector();
static Console console;
int status=Backend.STOPED;
boolean enabled=false;
	/**
	 * 
	 */
	public Console() {
		console=this;
	}





	public void start() {
		status=Backend.STARTING;
		channels.clear();
		if(c!=null){
			c.stop();
		}
		c=new ConsoleThread();
		channels.add(c);	
	}



	public void stop() {
		c.stop();
		c=null;
		status=Backend.STOPED;
	}



	public List<Channel> getChannels() {
		return channels;
	}



	public Channel getDefaultChannel() {
		return c;
	}



	public Channel getChannel(String name) {
		return null;
	}
	public Account getAccount(String name) {
		// TODO Auto-generated method stub
		return null;
	}





	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	private class ConsoleThread extends Channel implements Runnable, Replyer{
		DataInputStream in;
		boolean running;
		Thread t;
		ConsoleThread(){
			super();
			
	
			in=new DataInputStream(System.in);
			running=true;
			
			//this.addMessageListener(new CommandMessageListener(this));
			new CommandMessageListener(this);
			t=new Thread (this);
			t.start();
		}
		public String getName() {
			return "stdin/out";
		}
	
		public Replyer getReplyer() {
			return this;
		}
	
		public void send(String message) {
			System.out.println("ConsoleBackend: "+message);
		}
		public void stop(){
			
		}
		public void run() {
			status=Backend.READY;
			System.out.println("Started console backend");
			BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
			try{
				
				
				System.out.println("Welcome to the console backend");
				String line = in.readLine();
				while(line!=null){
					this.recivededMessage( line);
					System.out.print(":");
					System.out.flush();

					
					line=in.readLine();
				}
			}
			catch(IOException io){
				System.err.println("error reading std in");
			}
		}
		public String getChannelString() {
			return "console";
		}
		public void reply(String message) {
			// TODO Auto-generated method stub
			
		}
		public String getChannelString(String channel) {
			return "console";
		}
		public Channel getChannel() {
			return this;
		}
		public int getType() {
			// TODO Auto-generated method stub
			return 0;
		}
		public int getMaxMessageSize() {
			// TODO Auto-generated method stub
			return 0;
		}
		public void flush() {
			// TODO Auto-generated method stub
			
		}
		public Account getAccount() {
			return new Account("dns");
		}
		@Override
		public Backend getBackend() {
			return console;
		}
		
	}
	public void broadcast(String message) {
		// TODO Auto-generated method stub
		
	}





	public int getStatus() {
		return status;
	}





	public void configParameter(String line) {
		if(line.startsWith(Backend.ENABLED)){
			String tmp=line.substring(Backend.ENABLED.length());
			if(tmp.equalsIgnoreCase(Backend.TRUE)){
				enabled=true;
			}
			else if(tmp.equalsIgnoreCase(Backend.FALSE)){
				enabled=false;
			}
			else{
				enabled=false;
			}
		}
	}





	public boolean isEnabled() {
		return enabled;
	}
}
