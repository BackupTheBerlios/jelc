package jelc;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.LinkedList;

import java.util.*;
import jelc.event.*;
import jelc.io.EndianDataOutputStream;
import jelc.io.EndianInputStream;

import jelc.packet.*;


public class Connection  {
	public final static String DEFAULT_ADRESS= "eternal-lands.solexine.fr";
	public final static int DEFAULT_PORT= 2000;
    private Socket socket;



   

    private long last_heartbeat;

    private String username = "";

    private String password = "";
    //private String server = "localhost";
    private String server = "eternal-lands.solexine.fr";

    private int port = 2000;

    private Hashtable actors;
    private String map;
    
    public int time;
    
    /*LinkedList chatQueue=new LinkedList();
    LinkedList pmQueue=new LinkedList();*/
    
	//Timer timer
	    
	boolean running=true;
	private int runlevel=0;
	//Thread currentThread;
	
	
	
	
	//List loginManagers;
	List actorInterfaces;
	List chatInterfaces;
	List channelInterfaces;
	List timeListeners;
	List mapListeners;
	List BuddyListeners;
	
	
	List outPackets;
	
	boolean connected;
	
	private LoginManager manager;
	
	
	
	public Connection(String  server, int port) {
		this.server=server;
		this.port=port;
		//loginManagers=new LinkedList();
		actorInterfaces=new LinkedList();
		channelInterfaces=new LinkedList();
		chatInterfaces=new LinkedList();
		timeListeners=new LinkedList();;
		mapListeners=new LinkedList();
		BuddyListeners=new LinkedList();
		outPackets=new LinkedList();
	}
	
	/**
	 * Connects to the server
	 *
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect() throws UnknownHostException, IOException{
		socket=new Socket(server, port);
		
		outputThread out=new outputThread();
		this.putPacket(new SendOpeningScreen());
		new inputThread();	
		new heartBeatThread();
	}

	/**
	 * @param manager The manager to set.
	 */
	public void setLoginManager(LoginManager manager) {
		this.manager = manager;
	}

	/**
	 * @return Returns the manager.
	 */
	public LoginManager getLoginManager() {
		return manager;
	}

	public void login(String username, String password) throws IOException{
		putPacket(new Login(username, password));	
	}
	

	
	

	/*public void addLoginListener(LoginManager login){
		loginManagers.add(login);
	}
	public boolean removeLoginListener(LoginManager login){
		return loginManagers.remove(login);
	}
	public LoginManager[] getLoginListeners(){
		return (LoginManager[])loginManagers.toArray();
	}*/
	
	public void addActorListener(ActorListener ae){
		actorInterfaces.add(ae);
	}
	public boolean removeActorListener(ActorListener ae){
		return actorInterfaces.remove(ae);
	}
	public ActorListener[] getActorListeners(){
		return (ActorListener[])actorInterfaces.toArray();
	}
	
	public void addTimeListener(TimeListener tl){
		timeListeners.add(tl);
	}
	public boolean removeTimeListener(TimeListener tl){
		return timeListeners.remove(tl);
	}
	public TimeListener[] getTimeListeners(){
		return (TimeListener[])timeListeners.toArray();
	}
	
	public void addMapListener(TimeListener tl){
		mapListeners.add(tl);
	}
	public boolean removeMapListener(TimeListener tl){
		return mapListeners.remove(tl);
	}
	public MapListener[] getMapListeners(){
		return (MapListener[])mapListeners.toArray();
	}
	public void addChannelListener(ChannelListener cl){
		channelInterfaces.add(cl);
	}
	public boolean removeChannelListener(ChannelListener cl){
		return channelInterfaces.remove(cl);
	}
	public ChannelListener[] getChannelListeners(){
		return (ChannelListener[])channelInterfaces.toArray();
	}
	
	/**
	     * this method takes text input, and triggers the apropriate 
	     * @param text
	     */
	   /* private void processChat(String text){
	    	for(Iterator itr=chatInterfaces.iterator();itr.hasNext();){
	    		((ChatListener)itr.next()).onChat(text);
	    	}
	    	
	    	
	    	if (text.startsWith("[PM from ")){// for a pm message,
	    		int length=8;
	    		String from;
	    		String message;
	    		for(;length<text.length();length++){
	    			if(text.charAt(length)==':'){
	    				break;
	    			}
	    		}
	    		from=text.substring(9,length);
	    		message=text.substring(length+2,text.length()-1);
	        	for(Iterator itr=chatInterfaces.iterator();itr.hasNext();){
	        		((ChatListener)itr.next()).onPm(from,message);
	        	}
	    	}
	    	else if(text.startsWith("[PM to ")){// you sent a pm message
	
	    		int length=text.indexOf(": ");
	    		
	    		String to=text.substring(7,length); //we DO need to know who it is going to
	    		
	    		String message=text.substring(length+2,text.length()-1);
	    		
	           	for(Iterator itr=chatInterfaces.iterator();itr.hasNext();){
	        		((ChatListener)itr.next()).onPmSent(to,message);
	        	}
	    		
	    	}
	    	else if(text.startsWith("[")){//must be a local chat
	    		int length=0;
	    		String from;
	    		String message;
	    		for(;length<text.length();length++){
	    			if(text.charAt(length)==']'){
	    				break;
	    			}
	    		}
	    		from=text.substring(1,length);
	    		message=text.substring(length+4,text.length());
	           	for(Iterator itr=chatInterfaces.iterator();itr.hasNext();){
	        		((ChatListener)itr.next()).onChannelChat(from,message);
	        	}
	    	}
	    	else if(text.startsWith("#GM from ")){//must be a #Gm from the guild
	    		int length=9;
	    		String from;
	    		String message;
	    		for(;length<text.length();length++){
	    			if(text.charAt(length)==':'){
	    				break;
	    			}
	    		}
	    		from=text.substring(9,length);
	    		message=text.substring(length+2,text.length());
	           	for(Iterator itr=chatInterfaces.iterator();itr.hasNext();){
	        		((ChatListener)itr.next()).onGm(from,message);
	        	}
	    	}
	    	else if(text.startsWith("#Message from ")){//is a admin mesage
	    		//
	    	}
	    	else if(text.startsWith("#Hint: ")){//hint message
	    		//hint message
	    	}
	    	else{
	    		int length=0;
	    		String from;
	    		String message;
	    		for(;length<text.length();length++){//looks for name: message
	    			if(text.charAt(length)==':'){
	    				break;
	    			}
	    		}
	    		if(length!=text.length()){//found a :, it is a local chat
	    			from=text.substring(0,length);
	    			if(length+3<text.length()){
	    				//System.out.println("||"+text+"|"+from+"|"+(length+3)+" "+text.length());
	    				
	    				message=text.substring(length+3,text.length());
	    			}
	    			else{
	    				message=new String();
	    			}
	               	for(Iterator itr=chatInterfaces.iterator();itr.hasNext();){
	            		((ChatListener)itr.next()).onChat(from,message);
	            	}
	    			
	    		}
	    		else{//hasnt identified the type at all, so it is something else
	               	for(Iterator itr=chatInterfaces.iterator();itr.hasNext();){
	            		((ChatListener)itr.next()).onSystemMessage(text);
	            	}
	    		}
	    	}
	    }*/
	    public boolean putPacket(OutputPacket packet){
	    		return outPackets.add(packet);	
	    }

	/**
	 * @param runlevel The runlevel to set.
	 */
	public void setRunlevel(int runlevel) {
		this.runlevel = runlevel;
	}

	/**
	 * @return Returns the runlevel.
	 */
	public int getRunlevel() {
		return runlevel;
	}

	public class inputThread implements Runnable{
		private EndianInputStream in;
		//private EndianDataOutputStream out;
		public inputThread() throws IOException{ 
			in=new EndianInputStream(socket.getInputStream());
			//out=new EndianDataOutputStream(socket.getOutputStream());
			new Thread(this).start();
		}
		
		public void run() {
			Actor actor;
			EnhancedActor enhancedActor;
			String welcomeMessage=null;
			//this.last_heartbeat = System.currentTimeMillis();
			
			System.out.println("input ready");
			running=true;
			int type;
			int size;
			
			try{
//				int packet=0;
				while (running) {
					if (socket.isConnected()) {
						type=in.read();
						size=in.readSwapedShort();
						System.out.println("PACKET: "+type+" "+(size-2)/*+" "+packet*/);
					
						/*switch (runlevel) {
							case 8:
								runlevel++;
								break;
							default:
								if (runlevel < 1000)
									runlevel++;
							break;
						}*/
						switch (type) {
							case Protocol.RAW_TEXT:
								byte channel=in.readByte();
								String message=new String();
								for(int i=0;i<size-2;i++){
									message=message+(char)in.read();
								}

								for(Iterator itr=channelInterfaces.iterator();itr.hasNext();){
									((ChannelListener)itr.next()).chat(channel,message);
								}
								System.out.println("MESSAGE: "+ channel+" "+message);
								if(welcomeMessage==null){
									
									welcomeMessage=message;
									
									Login  tmp=manager.showWelcome(message);
									if(tmp!=null){
										putPacket(tmp);
									}
									/*for(Iterator itr=loginManagers.iterator();itr.hasNext();){
										((LoginManager)itr.next()).showWelcome(,message);
									}*/
									
									
								}
								break;
							case Protocol.LOG_IN_OK:
								/*for(Iterator itr=loginManagers.iterator();itr.hasNext();){
									((LoginManager)itr.next()).onLoginOk();
								}*/
								break;
							case Protocol.LOG_IN_NOT_OK:
								/*for(Iterator itr=loginManagers.iterator();itr.hasNext();){
									((LoginManager)itr.next()).onLoginFail();
								}*/
								break;
							case Protocol.YOU_DONT_EXIST:
								/*for(Iterator itr=loginManagers.iterator();itr.hasNext();){
									((LoginManager)itr.next()).onLoginFail();
								}*/                 	
								break;
							/*case Protocol.ADD_NEW_ACTOR:
								actor = new Actor(size,in);
								//actors.put(new Integer(actor.actor_id),actor);
								for(Iterator itr=actorInterfaces.iterator();itr.hasNext();){
									((ActorListener)itr.next()).onAddNewActor(actor);
								}
								break;
							case Protocol.ADD_NEW_ENHANCED_ACTOR:
								enhancedActor=new EnhancedActor(size,in);
								for(Iterator itr=actorInterfaces.iterator();itr.hasNext();){
									((ActorListener)itr.next()).onAddNewActor(enhancedActor);
								}
								break;
							case Protocol.REMOVE_ACTOR:	                        
								int id=in.readShort();
								for(Iterator itr=actorInterfaces.iterator();itr.hasNext();){
									((ActorListener)itr.next()).onRemoveActor(id);
								}
								break;*/
							case Protocol.CHANGE_MAP:
								String map=new String();
								for(int i=0;i<size;i++){
									map=map+in.readChar();
								}
								for(Iterator itr=mapListeners.iterator();itr.hasNext();){
									((MapListener)itr.next()).onChangeMap(map);
								}
		                    	//onChangeMap(map);
		                    	//System.out.println(map);
		                        break;
							case Protocol.NEW_MINUTE:
								time=in.readInt();
								for(Iterator itr=timeListeners.iterator();itr.hasNext();){
									((TimeListener)itr.next()).onMinute(time);
								}
								break;
							/*case Protocol.BUDDY_EVENT:
								int type=in.readByte();
		        				if(type==1){
		        					int color=in.readShort();
		        					String s=new String();
									for(int i=0;i<size;i++){
										s=s+in.readChar();
									}
									for(Iterator itr=timeListeners.iterator();itr.hasNext();){
										((BuddyListener)itr.next()).addBuddy(s,color);
									}
		        				}
		        				else if(type==0){
		        					String s=new String();
									for(int i=0;i<size;i++){
										s=s+in.readChar();
									}
									for(Iterator itr=timeListeners.iterator();itr.hasNext();){
										((BuddyListener)itr.next()).removeBuddy(s);
									}
		        				}
		        				break;
		                    	
							case Protocol.PONG:

								break;*/
							case Protocol.PING_REQUEST:
								byte[] data=new byte[size-1];
								in.read(data);
								putPacket(new PingResponse(data));
								break;
								
								
							case Protocol.GET_ACTIVE_SPELL_LIST:
								 data=new byte[size-1];
								in.read(data);
								break;
		                    /*case Protocol.LOG_IN_OK:
		                    	onLogin();
		                    	break;
		                    case Protocol.LOG_IN_NOT_OK:
		                    	onNoLogin();
		                    	break;
		                    case Protocol.YOU_DONT_EXIST:
		                    	onLoginNotExist();
		                    	break;
		                    case Protocol.HERE_YOUR_INVENTORY:
		                    	onHereYourInventory(msg);
		                    	break;
		                    case Protocol.GET_TRADE_ACCEPT:
		                    	onTradeAccept(msg);
		                    	break;
		                    case Protocol.REMOVE_TRADE_OBJECT:
		                    	onRemoveTradeObject(msg);
		                    	break;
		                    case Protocol.GET_TRADE_OBJECT:
		                    	onGetTradeObject(msg);
		                    	break;
		                    case Protocol.GET_YOUR_TRADEOBJECTS:
		                    	onGetYourTradeObjects(msg);
		                    	break;
		                    case Protocol.GET_TRADE_REJECT:
		                    	onGetTradeReject(msg);
		                    	break;
		                    case Protocol.GET_TRADE_EXIT:
		                    	onGetTradeExit();
		                    	break;
		                    case Protocol.SEND_PARTIAL_STAT:
		                    	onGetPartialStat(msg);
		                    	break;
		                    case Protocol.ADD_ACTOR_COMMAND:
		                    	onAddActorCommand(msg);
		                    	break;
		                    case Protocol.BUDDY_EVENT:
		                    	int type=msg.data.getInt();
		        				if(type==1){
		        					int types=msg.data.getInt();
		        					String s=new String();
		        					char c=msg.data.getChar();
		        					while(c!=0){
		        						s=s+c;
		        						c=msg.data.getChar();
		        					}
		        					//msg.data.asCharBuffer()
		        					this.addBuddy(s,types);
		        				}
		        				else if(type==0){
		        					String s=new String();
		        					char c=msg.data.getChar();
		        					while(c!=0){
		        						s=s+c;
		        						c=msg.data.getChar();
		        					}
		        					this.removeBuddy(s);
		        				}
		                    	break;*/
							default:
								System.out.println("SKIPPED PACKET ___________________________________________");
								/*for (int i=0;i<size-1;i++){
									System.out.println(i+" "+in.read());
								}*/
								
								//onUnknowPacket(msg);
								in.skipBytes(size-1);
							System.out.println("skipped data");
								break;
						}
						
						//packet++;
		            }
		        }
	        }
	        catch(Exception e){
	        	System.err.println("Exception: "+e.getLocalizedMessage());
	        	e.printStackTrace();
	        }
	    }
	}



	private class outputThread implements Runnable{
		private EndianDataOutputStream out;
		Thread current;
		Heartbeat heart=new Heartbeat();
		outputThread() throws IOException{
			out=new EndianDataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			current=new Thread(this);
			current.start();
		}
		public void run() {
			while(socket.isConnected()){
				OutputPacket packet;
				if(outPackets.size()!=0){
					packet=(OutputPacket)outPackets.get(0);
					outPackets.remove(0);
					try {
						packet.writePacket(out);
						System.out.println("sent "+packet);
						out.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
		/*public void writePacket(OutputPacket p) {
			try {
				p.writePacket(out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		/*public void start(){
			current=new Thread(this);
			current.start();
		}*/
	}
	private class heartBeatThread implements Runnable{
		Thread current;
		heartBeatThread(){
			current=new Thread(this);
			current.start();
		}
		
		public void run() {
			while(socket.isConnected()){
				if(System.currentTimeMillis() - last_heartbeat  > 5000){
					putPacket(new Heartbeat());
					last_heartbeat=System.currentTimeMillis();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
