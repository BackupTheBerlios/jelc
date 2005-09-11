package jelc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.LinkedList;

import java.util.*;
import jelc.event.*;


public class Connection {
	public final static String DEFAULT_ADRESS= "eternal-lands.solexine.fr";
	public final static int DEFAULT_PORT= 2000;
    private Socket socket;

    private DataInputStream in;

    private DataOutputStream out;

    private long last_heartbeat;

    private String username = "";

    private String password = "";
    //private String server = "localhost";
    private String server = "eternal-lands.solexine.fr";

    private int port = 2000;

    private Hashtable actors;
    private String map;
    
    public int time;
    
    LinkedList chatQueue=new LinkedList();
    LinkedList pmQueue=new LinkedList();
    
	//Timer timer
	    
	boolean running=true;
	int runlevel;
	Thread currentThread;
	
	
	
	int messageType;
	int messageSize;
	
	
	List loginManagers;
	List actorInterfaces;
	List chatInterfaces;
	List channelInterfaces;
	List timeListeners;
	List mapListeners;
	List BuddyListeners;
	
	public Connection() {
		loginManagers=new LinkedList();
		actorInterfaces=new LinkedList();
		channelInterfaces=new LinkedList();
		chatInterfaces=new LinkedList();
		timeListeners=new LinkedList();;
		mapListeners=new LinkedList();
		BuddyListeners=new LinkedList();
	}
	
	public void login(String username, String password) throws IOException{
		out.writeByte(Protocol.LOG_IN);
		String str=username+" "+password+'\0';
		out.write(str.length()+3);
		out.writeChars(str);
		
	}
	
	public void moveTo(short x, short y) throws IOException{
		out.writeByte(Protocol.MOVE_TO);
		out.writeShort(5);
		out.writeShort(x);
		out.writeShort(y);
	}
	public void getPing() throws IOException{
		out.writeByte(Protocol.PING);
		out.writeShort(5);
		out.writeLong(System.currentTimeMillis());
		
	}
	
	

	public void addLoginListener(LoginListener login){
		loginManagers.add(login);
	}
	public boolean removeLoginListener(LoginListener login){
		return loginManagers.remove(login);
	}
	public LoginListener[] getLoginListeners(){
		return (LoginListener[])loginManagers.toArray();
	}
	
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
	
	public void run() {
		Actor actor;
		EnhancedActor enhancedActor;
		this.last_heartbeat = System.currentTimeMillis();
		running=true;
		try{
			while (running) {
				if (this.socket.isConnected()) {
					messageType=in.readByte();
					messageSize=in.readShort();
					/*switch (runlevel) {
						case 8:
							runlevel++;
							break;
						default:
							if (runlevel < 1000)
								runlevel++;
						break;
					}*/
					switch (messageType) {
						case Protocol.RAW_TEXT:
							if(messageSize > 4){
								byte channel=in.readByte();
								String message=new String();
								for(int i=4;i<messageSize;i++){
									message=message+in.readChar();
								}
								for(Iterator itr=loginManagers.iterator();itr.hasNext();){
									((ChannelListener)itr.next()).chat(channel,message);
								}
							}
							break;
						case Protocol.LOG_IN_OK:
							for(Iterator itr=loginManagers.iterator();itr.hasNext();){
								((LoginListener)itr.next()).onLoginOk();
							}
							break;
						case Protocol.LOG_IN_NOT_OK:
							for(Iterator itr=loginManagers.iterator();itr.hasNext();){
								((LoginListener)itr.next()).onLoginFail();
							}
							break;
						case Protocol.YOU_DONT_EXIST:
							for(Iterator itr=loginManagers.iterator();itr.hasNext();){
								((LoginListener)itr.next()).onLoginFail();
							}                    	
							break;
						case Protocol.ADD_NEW_ACTOR:
							actor = new Actor(messageSize,in);
							//actors.put(new Integer(actor.actor_id),actor);
							for(Iterator itr=loginManagers.iterator();itr.hasNext();){
								((ActorListener)itr.next()).onAddNewActor(actor);
							}
							break;
						case Protocol.ADD_NEW_ENHANCED_ACTOR:
							enhancedActor=new EnhancedActor(messageSize,in);
							for(Iterator itr=loginManagers.iterator();itr.hasNext();){
								((ActorListener)itr.next()).onAddNewActor(enhancedActor);
							}
							break;
						case Protocol.REMOVE_ACTOR:	                        
							int id=in.readShort();
							for(Iterator itr=loginManagers.iterator();itr.hasNext();){
								((ActorListener)itr.next()).onRemoveActor(id);
							}
							break;
						case Protocol.CHANGE_MAP:
							String map=new String();
							for(int i=0;i<messageSize;i++){
								map=map+in.readChar();
							}
							for(Iterator itr=mapListeners.iterator();itr.hasNext();){
								((MapListener)itr.next()).onChangeMap(map);
							}
	                    	//onChangeMap(map);
	                    	//System.out.println(map);
	                        break;
						case Protocol.NEW_MINUTE:
							time=in.readShort();
							for(Iterator itr=timeListeners.iterator();itr.hasNext();){
								((TimeListener)itr.next()).onMinute(time);
							}
							break;
						case Protocol.BUDDY_EVENT:
							int type=in.readByte();
	        				if(type==1){
	        					int color=in.readShort();
	        					String s=new String();
								for(int i=0;i<messageSize;i++){
									s=s+in.readChar();
								}
								for(Iterator itr=timeListeners.iterator();itr.hasNext();){
									((BuddyListener)itr.next()).addBuddy(s,color);
								}
	        				}
	        				else if(type==0){
	        					String s=new String();
								for(int i=0;i<messageSize;i++){
									s=s+in.readChar();
								}
								for(Iterator itr=timeListeners.iterator();itr.hasNext();){
									((BuddyListener)itr.next()).removeBuddy(s);
								}
	        				}
	        				break;
	                    	
						case Protocol.PONG:
							
							break;
						case Protocol.PING_REQUEST:
							out.writeByte(Protocol.PING_REQUEST);
							out.writeShort(messageSize);
							byte[] tmp=new byte[messageSize];
							in.read(tmp);
							out.write(tmp);
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
							//onUnknowPacket(msg);
							break;
					}
	            }
	            try {
	                Thread.sleep(100);
	            } catch (InterruptedException e1) {
	                e1.printStackTrace();
	            }
	        }
        }
        catch(Exception e){
        	System.err.println("Exception: "+e.getLocalizedMessage());
        	e.printStackTrace();
        }
    }
    
    public void connect() throws UnknownHostException, IOException{
    	socket=new Socket(server, port);
    	in=new DataInputStream(socket.getInputStream());
    	out=new DataOutputStream(socket.getOutputStream());
		out.writeByte(Protocol.SEND_OPENING_SCREEN);
		out.writeInt(1);
    	
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
}
