package bot.deadface6.backends;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import bot.deadface6.Account;
import bot.deadface6.MyBot;
import bot.deadface6.Replyer;
import bot.deadface6.AccountManager;


public class SocketBackend implements Runnable,Backend{
MyBot bot;
int port;
static SocketBackend socket;
int status=Backend.STOPED;
List connections=new Vector();
	public SocketBackend(MyBot bot, int port) {
		this.bot=bot;
		this.port=port;
		socket=this;
	}

	public void run() {
		try {
			ServerSocket ss=new ServerSocket(port);
			System.out.println("Socket Server is up on port " +port+ ":");
			status=Backend.READY;
			while(true){
				Socket s=ss.accept();
				new Connection(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private class Connection extends Channel implements Runnable, Replyer{
		Socket sock;
		BufferedReader in ;
		DataOutputStream out;
		boolean connected=true;
		String name=null;
		Connection(Socket sock){
			this.sock=sock;
			new Thread(this).start();
		}
		public void run() {
			try {
				
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out=new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
				out.writeChars("Welcome to my bot\r\n");
				out.writeChars("username: ");
				out.flush();
				String username;
				String line=in.readLine();
				while(connected&&line!=null){
					username=line;
					if((username!=null)||(username.equals(""))){
						out.writeChars("password: ");
						out.flush();
						String password=in.readLine();
						name=bot.login.login(username,password);
						if(name!=null){
							//main loop
							out.writeChars("You are now loogged in, \r\n");
							out.writeChars(">");
							out.flush();
							
							
							line=in.readLine();
							while(connected&&line!=null){
								if(line.equalsIgnoreCase("logout")){
									connected=false;
								}
								else if(line.equalsIgnoreCase("")){
									out.writeChars(">");
									out.flush();
									line=in.readLine();
									
								}
								else{
									bot.processCommands(this,line);
									out.writeChars(">");
									out.flush();
									line=in.readLine();
								}
								
							}							
						}
					}
					out.writeChars("username: ");
					out.flush();
					line=in.readLine();
				}
				this.stop();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//connected=false;
				//e.printStackTrace();
				
			}
		}
		public void reply(String str) {
			try{
				out.writeChars(str+"\r\n");
			} catch (IOException e) {
				//e.printStackTrace();
				//connected=false;
				this.stop();
			}
			
		}
		public Replyer getReplyer() {
			return this;
		}

		public Channel getChannel() {
			return this;
		}
		public int getType() {
			return 0;
		}
		public int getMaxMessageSize() {
			return 512;
		}
		public void flush() {
			// TODO Auto-generated method stub
			
		}
		public Account getAccount() {
			return AccountManager.getAccountManger().getAccount(name);
		}
		
		public String getName() {
			return name;
		}
		
		@Override
		public String getChannelString() {
			return "socket:"+name;
		}
		@Override
		public void send(String message) {
			reply(message);
		}
		@Override
		public void stop() {
			connections.remove(this);
		}
		@Override
		public Backend getBackend() {
			return socket;
		}

	}
	public Account getAccount(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void start() {
		status=Backend.STARTING;
		new Thread(this).start();
		
	}

	public void stop() {
		status=Backend.STOPED;
	}

	public List<Channel> getChannels() {
		// TODO Auto-generated method stub
		return null;
	}

	public Channel getDefaultChannel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Channel getChannel(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void broadcast(String message) {
		// TODO Auto-generated method stub
		
	}

	public int getStatus() {
		return status;
	}

	public void configParameter(String line) {
		// TODO Auto-generated method stub
		
	}

	public boolean isEnabled() {
		return false;
	}
}
