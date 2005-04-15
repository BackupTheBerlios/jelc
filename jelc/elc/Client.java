/*
 * Created on Jan 11, 2005
 */
package elc;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.io.*;

/**
 * @author frak
 *  
 */
public abstract class Client extends Thread {
	public final static String DEFAULT_ADRESS= "eternal-lands.solexine.fr";
	public final static int DEFAULT_PORT= 2000;
    private Socket my_socket;

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
    public Client(String user, String pass) {
        this.setUsername(user);
        this.setPassword(pass);
        this.actors = new Hashtable(20);
    }
    
    public Client(String user, String pass, String serv, int p) {
        this.setUsername(user);
        this.setPassword(pass);
        this.server = serv;
        this.port = p;
        this.actors = new Hashtable(20);
    }
    public Client(String serv, int p){
        this.server = serv;
        this.port = p;
    }

    /**
     * to be overwritten by subclasses gets calld once in every main loop
     * iteration;
     */
    public void onLoop() {
    }

    /**
     * is caled when any message is recieved by the client
     * to be overwritten by subclasses
     * 
     * @param text  the contents of the message
     */
    public void onChat(String text) {
    }
    
    /**
     * this method is called when a message is recieved in the local area
     * 
     * @param person  the person that sent the message
     * @param message  the message that was recieved
     */
    public void onChat(String person, String message){
	}
    
    /**
     * this method is called when a message is recieved on the current channel
     *
     * @param person  the person that sent the message
     * @param message  the message that was recieved
     */
    public void onChannelChat(String person, String message){
	}
    
    /**
     * this method is called when a message is recieved on the current channel
     *
     * @param person  the person that sent the message
     * @param message  the message that was recieved
     */
    public void onPm(String person, String message){
    }
    
    /**
     * this message is called when the message is sent to echo that the message has been recieved, if it isn't recieved correctly, a system message will be sent saying it failed
     * 
     * @param message the message sent
     */
    public void onPmSent(String person, String message){
    }
    
    /**
     * this method is called when a #gm (guild message) message is recieved
     *
     * @param person  the person that sent the message
     * @param message  the message that was recieved
     */
    public void onGm(String person, String message){
    }
    
    /**
     * this is recieved when a 'hint' message is recieved, for when the player is new to the game
     * 
     * @param message the message recieved
     */
    public void onHint(String message){
    }
    
    /**
     * this is called when a message as been recieved and does not fit any of the above patterns 
     * 
     * @param message
     */
    public void onSystemMessage(String message){
    }
    
    /**
     * sends a raw text message to the local area
     * 
     * @param text
     */
    public void chat(String text) {
        //send(Protocol.RAW_TEXT, text.getBytes(), text.length() + 1);
    	chatQueue.addLast(text);//for java 1.4 compatablility
    	//chatQueue.offer(text);
    }
    /**
     * chat(string text) now puts items into a queue,
     * this is called later in check_heartbeat() to send items in the queue
     
     * * @param text
     */
    private void doChat(String text){
    	send(Protocol.RAW_TEXT, text.getBytes(), text.length() + 1);
    }
    
    /**
     * sends a private message, it must be in the format
     * '/name message
     *   
     * @param text the text string to send
     */
    public  void chatPm(String text) {
        //send(Protocol.SEND_PM, text.substring(1).getBytes(), text.length());
    	pmQueue.addLast(text);//for java 1.4
    	//pmQueue.offer(text);
    }
    /**
     * chatPm(string text) now puts items into a queue,
     * this is called later in check_heartbeat() to send items in the queue 
     * 
     * @param text
     */
    private void doChatPm(String text){
    	send(Protocol.SEND_PM, text.substring(1).getBytes(), text.length());
    }
    
    /**
     * sends a private message to the indicated person
     * 
     * @param name  the name of the person to send the message
     * @param message  the message to send
     */
    public void chatPm(String name, String message){
    	chatPm("/"+name+" "+message);
    }

    /**
     * sends a #gm (guild message) to members of the guild
     * 
     * @param message  the message to send
     */
    public void chatGm(String message){
    	chat("#gm "+message);
    }
    
    /**
     * sends a message to the local channel
     * to be overwritten by subclasses
     * 
     * @param message
     */
    public void chatChannel(String message){
    	 chat("@"+message);
    }
    
    /**
     * to be overwritten by subclasses
     * 
     * @param p
     */
    public void onUnknowPacket(Packet p) {
    }
    
    /**
     * 
     * @param a
     */
    public void onAddNewActor(Actor  a) {
    }
    
    /**
     * to be overwritten by subclasses
     */
    public void onRemoveActor(Actor a) {
    }
    
    /**
     * this method is called when a new minute occurs
     * to be overwritten in sub classes
     * 
     * @param time  the ammount of minutes game time
     */
    public void onMinute(int time){
    	
    }
    public void onChangeMap(String map){
    	
    }

    private void send(int protocol, byte[] data, int len) { 
    	ByteBuffer b = ByteBuffer.allocate(len+2);
    	b.order(ByteOrder.LITTLE_ENDIAN);
    	b.put((byte)protocol);
    	b.putShort((short)len);
    	if(len>1)
    		b.put(data);
        try {
            this.out.write(b.array());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.last_heartbeat = System.currentTimeMillis();
    }

    private void send(byte[] b) {
        try {
            this.out.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.last_heartbeat = System.currentTimeMillis();
    }

    private void send(Packet p) {
    	ByteBuffer b = ByteBuffer.allocate(p.length + 2);
    	b.order(ByteOrder.LITTLE_ENDIAN);
        try {
        	b.put((byte)p.protocol);
        	b.putShort((short)p.length);
        	if (p.length > 1)
        		b.put(p.data);
        	this.out.write(b.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.last_heartbeat = System.currentTimeMillis();
    }
    public void login() {
        String msg;
        msg = this.getUsername() + " " + this.getPassword() + "\0";
        send(new Packet(Protocol.LOG_IN, msg.getBytes(), msg.length() + 1));
    }
	public void login(String username, String password){
        String msg;
        msg = this.getUsername() + " " + this.getPassword() + "\0";
        send(new Packet(Protocol.LOG_IN, msg.getBytes(), msg.length() + 1));
    }

    public Hashtable getActors() {
        return this.actors;
    }

    private void check_heartbeat() {
    	if(chatQueue.size()!=0){
    		this.doChat((String)chatQueue.removeFirst());
            this.last_heartbeat = System.currentTimeMillis();
    	}
    	else if(pmQueue.size()!=0){
    		doChatPm((String)pmQueue.removeFirst());
            this.last_heartbeat = System.currentTimeMillis();
    	}
        else if (System.currentTimeMillis() - this.last_heartbeat  > 5000) {
            this.last_heartbeat = System.currentTimeMillis();
            send(new Packet(Protocol.HEART_BEAT, null, 1));
        }
    	
    	/*if(chatQueue.peek()!=null){
    		this.doChat((String)chatQueue.remove());
            this.last_heartbeat = System.currentTimeMillis();
    	}
    	else if(pmQueue.peek()!=null){
    		doChatPm((String)pmQueue.remove());
            this.last_heartbeat = System.currentTimeMillis();
    	}
        else if (System.currentTimeMillis() - this.last_heartbeat  > 5000) {
            this.last_heartbeat = System.currentTimeMillis();
            send(new Packet(Protocol.HEART_BEAT, null, 1));
        }*/
    }

    private Packet poll() {
        Packet msg;
        int p = 0;
        int l = 0;
        byte[] d = new byte[2048];
        try {
            if (this.in.available() > 3) {
                p = this.in.readUnsignedByte();
                l = this.in.readUnsignedByte();
                l += this.in.readUnsignedByte() << 8;
                this.in.read(d, 0, l - 1);
                d[l] = 0;
                msg = new Packet(p, d, l - 1);
                return msg;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
   public  void updateActors(){
    	send(new Packet(Protocol.SEND_ME_MY_ACTORS, null, 1));
    }
    public  void locateME(){
    	send(new Packet(Protocol.SEND_ME_MY_ACTORS, null, 1));
    }
    public void run() {
        Packet msg;
        int runlevel = 0;

        try {
            this.my_socket = new Socket(server, port);
            this.in = new DataInputStream(my_socket.getInputStream());
            this.out = new DataOutputStream(my_socket.getOutputStream());

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.last_heartbeat = System.currentTimeMillis();

        while (true) {
            if (this.my_socket.isConnected()) {
                msg = poll();
                switch (runlevel) {
                case 2:
                    send(new Packet(Protocol.SEND_OPENING_SCREEN, null, 1));
                    runlevel++;
                    break;
                case 8:
                    login();
                    runlevel++;
                    break;
                default:
                    if (runlevel < 1000)
                        runlevel++;
                    break;
                }
                if (msg != null) {
                    switch (msg.protocol) {
                    case Protocol.RAW_TEXT:
                    	processChat(new String(msg.data.array(), 1, msg.length - 1));
                        break;
                    case Protocol.ADD_NEW_ACTOR:
                        Actor a = new Actor(msg);
                     	actors.put(new Integer(a.actor_id),a);
                        onAddNewActor(a);
                        break;
                    case Protocol.REMOVE_ACTOR:
                        //actors.remove(msg.data.getShort());
                    	Actor b=(Actor)actors.remove(new Integer(msg.data.getShort()));
                        onRemoveActor(b);
                        break;
                    case Protocol.CHANGE_MAP:
                    	map=new String(msg.getBytes());
                    	onChangeMap(map);
                    	//System.out.println(map);
                        break;
                    case Protocol.NEW_MINUTE:
                    	time=msg.data.getShort();
                        onMinute(time);
                    	break;
                    case Protocol.LOG_IN_OK:
                    	onLogin();
                    	break;
                    case Protocol.LOG_IN_NOT_OK:
                    	onNoLogin();
                    	break;
                    case Protocol.YOU_DONT_EXIST:
                    	onLoginNotExist();
                    	break;
                    default:
                        onUnknowPacket(msg);
                        break;
                    }
                }
                check_heartbeat();
                onLoop();
            }
            try {
                sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
    /**
     * this method takes text input, and triggers the apropriate 
     * @param text
     */
    private void processChat(String text){
    	onChat(text);
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
    		onPm(from, message);
    	}
    	else if(text.startsWith("[PM to ")){// you sent a pm message
    		
    		/*int length=6;
    		String to;
    		String message;
    		for(;length<text.length();length++){
    			if(text.charAt(length)==':'){
    				break;
    			}
    		}*/
    		int length=text.indexOf(": ");
    		
    		String to=text.substring(7,length); //we DO need to know who it is going to
    		
    		String message=text.substring(length+2,text.length()-1);
    		onPmSent(to, message);
    		
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
    		onChannelChat(from, message);
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
    		onGm(from, message);
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
    			message=text.substring(length+3,text.length());
    			onChat(from, message);
    		}
    		else{//hasnt identified the type at all, so it is something else
    			onSystemMessage(text);
    		}
    	}
    }
    /**
     * this method is called when the user sucsessfully logs in to the game
     *
     */
    public void onLogin(){
    	
    }
    /**
     * this method is called when a login fails, usually because of a bad password.
     * this prints an error message, and exits the program.
     * This is designed to be overwridden in sub classes if required
     */
    public void onNoLogin(){
    	System.err.println("Error: login failure.");
    	System.err.println("this is most likely caused by an incorrect password");
    	System.exit(1);
    }
    /**
     * this method is called when a login fails, usually because of a bad password.
     * this prints an error message, and exits the program
     * This is designed to be overwridden in sub classes if required
     */    
    public void onLoginNotExist(){
    	System.err.println("Error: You Don't exist");
    	System.err.println("check to see if your login ");
    	System.exit(1);    	
    }

	/**
	 * @param username The username to set.
	 */
	private void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return Returns the username.
	 */
	private String getUsername() {
		return username;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}