package bot.deadface5;
import java.io.*;
import java.net.*;
import elc.*;


public class SocketBackend implements Runnable{
MyBot bot;
int port;
	public SocketBackend(MyBot bot, int port) {
		this.bot=bot;
		this.port=port;
		new Thread(this).start();
	}

	public void run() {
		try {
			ServerSocket ss=new ServerSocket(port);
			System.out.println("Socket Server is up on port " +port+ ":");
			while(true){
				Socket s=ss.accept();
				new Connection(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private class Connection implements Runnable, Replyer,ClientInterface{
		Socket sock;
		BufferedReader in ;
		DataOutputStream out;
		boolean connected=true;
		String name;
		
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
							
							bot.con.addClientListener(this);
							
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
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//connected=false;
				//e.printStackTrace();
				quit();
			}
		}
		public void reply(String str) {
			try{
				out.writeChars(str+"\r\n");
			} catch (IOException e) {
				//e.printStackTrace();
				//connected=false;
				quit();
			}
			
		}
		public int getType() {
			// TODO Auto-generated method stub
			return 0;
		}
		public String getName() {
			return name;
		}
		public int getMaxMessageSize() {
			// TODO Auto-generated method stub
			return 0;
		}
		public void flush() {
			try{
				out.writeChar('\n');
				out.flush();
			} catch (IOException e) {
				connected=false;
				quit();
			}
			
		}
		public Account getAccount() {
			return AccountManager.getAccountManger().getAccount(name);
		}
		public void quit(){
			connected=false;
			bot.con.removeClientListener(this);
		}
		public void onChat(String arg0) {
			// TODO Auto-generated method stub
			
		}
		public void onChat(String arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}
		public void onChannelChat(String arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}
		public void onPm(String arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}
		public void onPmSent(String arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}
		public void onGm(String arg0, String arg1) {
			try {
				out.writeChars("#gm: "+arg0+" "+arg1);
				out.flush();
				//System.out.println("got gm: "+arg0+" "+arg1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				quit();
			}
			
		}
		public void onHint(String arg0) {
			// TODO Auto-generated method stub
			
		}
		public void onSystemMessage(String arg0) {
			// TODO Auto-generated method stub
			
		}
		public void onMinute(int arg0) {
			// TODO Auto-generated method stub
			
		}
		public void onIG(String message) {
			// TODO Auto-generated method stub
			
		}
	}
}
