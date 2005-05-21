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
public abstract class Client implements Runnable{
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
    
	Item[] inventory;
	//Timer timer
	    
	boolean running=true;
	int runlevel;
	Thread currentThread;
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
     * 
     * @param a
     */
    public void onAddNewEnhancedActor(EnhancedActor  a) {
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
    
    private void onHereYourInventory(Packet p){
    	int totalNumberOfItems = p.data.get();
    	//System.out.println("total items: "+totalNumberOfItems);
    	inventory = new Item[totalNumberOfItems];
    	//System.out.println("here your inventory:");
    	for(int i=0; i<totalNumberOfItems; i++)
    	{
    		int image = p.data.getShort(i*8+1);
    		int quantity = p.data.getInt(i*8+3);
    		int pos = p.data.get(i*8+7);
    		int flags = p.data.get(i*8+8);
    		Item it = new Item(image,quantity,pos,flags);
    		//System.out.println(it);
    		inventory[i]=it;
    	}
    	onHereYourInventory(inventory);
    }
    public void onHereYourInventory(Item[] items){
    	
    }
    
    public void onGetPartialStat(Packet p)
    {
    	
    }
       
    public void onTradeAccept(Packet p){
    	
    }
    
    public void onGetYourTradeObjects(Packet p){
    	
    }
    public void onGetTradeObject(Packet p){
    	
    }
    public void onGetTradeExit(){
    	//timer.cancel();
    	
    }
    
    public void tradeWith(Actor partner){
    	/*timer = new Timer();
    	TradeTimeOut task = new TradeTimeOut(this);
    	timer.schedule(task,30000);*/
    	
    	//byte[] id = new byte[4];
    	//Integer in = new Integer(partner.actor_id);
    	//byte b = in.byteValue();
    	//id[0]=b;
    	//in.
    	ByteBuffer b = ByteBuffer.allocate(4);
    	b.order(ByteOrder.LITTLE_ENDIAN);
        b.putShort((short)partner.actor_id);
    	//System.out.println("trying to trade with id: "+new Integer(id[0]));
    	send(Protocol.TRADE_WITH, b.array() ,5);//wrong
    }
    
    public void putObjectOnTrade(int pos, int quant){
    	ByteBuffer b = ByteBuffer.allocate(3);
    	b.order(ByteOrder.LITTLE_ENDIAN);
    	b.put((byte)pos);
    	b.putShort((short)quant);
    	send(Protocol.PUT_OBJECT_ON_TRADE, b.array(),4);
    }
    
    public void removeObjectFromTrade(int pos, int quant)
    {
    	ByteBuffer b = ByteBuffer.allocate(3);
    	b.order(ByteOrder.LITTLE_ENDIAN);
    	b.put((byte)pos);
    	b.putShort((short)quant);
    	send(Protocol.REMOVE_OBJECT_FROM_TRADE, b.array(),4);
    }
    
    public void acceptTrade(){
    	send(Protocol.ACCEPT_TRADE, null,1);
    	try{Thread.sleep(500);}catch(Exception e){e.printStackTrace();}
    	send(Protocol.SEND_MY_INVENTORY,null,1);
    	//timer.cancel();
    }
    
    public void abortTrade(){
    	send(Protocol.EXIT_TRADE, null,1);
    	//timer.cancel();
    }
    
    public void askForInv()
    {
    	
    	send(Protocol.SEND_MY_INVENTORY,null,1);
    }
    
    public void moveInv(int firstpos,int secondpos)
    {
    	ByteBuffer b = ByteBuffer.allocate(2);
    	b.order(ByteOrder.LITTLE_ENDIAN);
    	b.put((byte)firstpos);
    	b.put((byte)secondpos);
    	send(Protocol.MOVE_INVENTORY_ITEM,b.array(),3);
    	askForInv();
    }
    
    public void useItem(int pos)
    {
    	ByteBuffer b = ByteBuffer.allocate(1);
    	b.order(ByteOrder.LITTLE_ENDIAN);
    	b.put((byte)pos);
    	send(Protocol.USE_INVENTORY_ITEM,b.array(),2);
    }
    
    public void attack(int target, double x, double y)
    {
    	ByteBuffer b = ByteBuffer.allocate(4);
    	b.order(ByteOrder.LITTLE_ENDIAN);
    	b.putInt(target);
    	send(Protocol.ATTACK_SOMEONE,b.array(),5);
    }
    
    public void moveTo(double x, double y)
    {
    	ByteBuffer b = ByteBuffer.allocate(4);
    	b.order(ByteOrder.LITTLE_ENDIAN);
    	b.putShort((short)x);
    	b.putShort((short)y);
    	send(Protocol.MOVE_TO,b.array(),5);
    }
    
    public void pickup(int pos, int quantity)
    {
    	ByteBuffer b = ByteBuffer.allocate(3);
    	b.order(ByteOrder.LITTLE_ENDIAN);
    	b.put((byte)pos);
    	b.putShort((short)quantity);
    	send(Protocol.PICK_UP_ITEM,b.array(),4);
    }
    
    public void drop(int pos, int quantity)
    {
    	ByteBuffer b = ByteBuffer.allocate(3);
    	b.order(ByteOrder.LITTLE_ENDIAN);
    	b.put((byte)pos);
    	b.putShort((short)quantity);
    	send(Protocol.DROP_ITEM,b.array(),4);
    }
    
    public void harvest(int id)
    {
    	ByteBuffer b = ByteBuffer.allocate(2);
    	b.order(ByteOrder.LITTLE_ENDIAN);
    	b.putShort((short)id);
    	send(Protocol.HARVEST,b.array(),3);
    }
    
    public void onRemoveTradeObject(Packet msg){
    	
    }
    
    public void onGetTradeReject(Packet msg){
    	
    }
    public void onChangeMap(String map){
    	
    }

    public void onAddActorCommand(Packet msg){
    	//System.out.println(msg.dump());
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
            System.err.println("error: "+e.getMessage());
            this.reconnect();
        }
        this.last_heartbeat = System.currentTimeMillis();
    }

    private void send(byte[] b) {
    	if (my_socket.isClosed()){
    		connect();
    	}
        try {
            this.out.write(b);
        }
        catch (IOException e) {
            System.err.println("error: "+e.getMessage());
            this.reconnect();
        }
        this.last_heartbeat = System.currentTimeMillis();
    }

	private void send(Packet p) {
    	if (my_socket.isClosed()){
    		connect();
    	}
		try {
			ByteBuffer b = ByteBuffer.allocate(p.length + 2);
			b.order(ByteOrder.LITTLE_ENDIAN);
			b.put((byte)p.protocol);
			b.putShort((short)p.length);
        	if (p.length > 1)
        		b.put(p.data);
        	this.out.write(b.array());
        } catch (IOException e) {
        	System.err.println("error: "+e.getMessage());
            this.reconnect();
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
    	if (my_socket.isClosed()){
    		connect();
    	} 
    	if (System.currentTimeMillis() - this.last_heartbeat  > 5000) {
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
    private void sendChat(){
    	if(chatQueue.size()!=0){
    		this.doChat((String)chatQueue.removeFirst());
            this.last_heartbeat = System.currentTimeMillis();
    	}
    	else if(pmQueue.size()!=0){
    		doChatPm((String)pmQueue.removeFirst());
            this.last_heartbeat = System.currentTimeMillis();
    	}
    }

    private Packet poll() {
        if(my_socket.isClosed()){
        	connect();
        }
        Packet msg;
        int p = 0;
        int l = 0;
        byte[] d = new byte[2048];        
        try {	
            if ((this.in.available() > 3)) {
                p = this.in.readUnsignedByte();
                l = this.in.readUnsignedByte();
                l += this.in.readUnsignedByte() << 8;
                this.in.read(d, 0, l - 1);
                d[l] = 0;
                msg = new Packet(p, d, l - 1);
                return msg;
            }
        }
        catch (IOException e) {
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
    
    public boolean connect(){
        runlevel = 0;
    	try {
    		if(my_socket!=null){
    			my_socket.close();
    		}
    		this.my_socket = new Socket(server, port);
    		this.in = new DataInputStream(my_socket.getInputStream());
    		this.out = new DataOutputStream(my_socket.getOutputStream());
    		currentThread=new Thread(this);
    		currentThread.start();
    		return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
        	e.printStackTrace();
        	return false;
        }
    }
    public boolean reconnect(){
    	System.out.println("Reconnecting...");
    	running=false;
    	try {
		
    		Thread.sleep(500);//delay a little to hopefully get around bad network connections
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	return connect();
    }
    
    public void run() {
        Packet msg;
        Actor actor;
        EnhancedActor enhancedActor;
        this.last_heartbeat = System.currentTimeMillis();
        running=true;
        while (running) {
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
                        actor = new Actor(msg);
                     	actors.put(new Integer(actor.actor_id),actor);
                        onAddNewActor(actor);
                        break;
                    case Protocol.ADD_NEW_ENHANCED_ACTOR:
                    	enhancedActor=new EnhancedActor(msg);
                    	actors.put(new Integer(enhancedActor.actor_id),enhancedActor);
                    	this.onAddNewEnhancedActor(enhancedActor);
                    	break;
                    case Protocol.REMOVE_ACTOR:
                        //actors.remove(msg.data.getShort());
                    	actor=(Actor)actors.remove(new Integer(msg.data.getShort()));
                        onRemoveActor(actor);
                        break;
                    case Protocol.CHANGE_MAP:
                    	map=new String(msg.getBytes());
                    	map=map.substring(0,map.length()-1);
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
                    case Protocol.HERE_YOUR_INVENTORY:
                    	onHereYourInventory(msg);
                    	break;
                    case Protocol.GET_TRADE_ACCEPT:
                    	onTradeAccept(msg);
                    case Protocol.GET_TRADE_OBJECT:
                    	onGetTradeObject(msg);
                    case Protocol.REMOVE_TRADE_OBJECT:
                    	onRemoveTradeObject(msg);
                    case Protocol.GET_YOUR_TRADEOBJECTS:
                    	onGetYourTradeObjects(msg);
                    case Protocol.GET_TRADE_REJECT:
                    	onGetTradeReject(msg);
                    case Protocol.GET_TRADE_EXIT:
                    	onGetTradeExit();
                    case Protocol.SEND_PARTIAL_STAT:
                    	onGetPartialStat(msg);
                    case Protocol.ADD_ACTOR_COMMAND:
                    	onAddActorCommand(msg);
                    default:
                        onUnknowPacket(msg);
                        break;
                    }
                }
                if(running){//incase the above crashed
                	check_heartbeat();
                	sendChat();
                	onLoop();
                }
            }
            try {
                Thread.sleep(100);
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
	
	/**
	 * call this method when you want to quit.
	 *
	 */
	public void quit(){
		this.running=false;
    	while(chatQueue.size()!=0){
    		this.doChat((String)chatQueue.removeFirst());
            this.last_heartbeat = System.currentTimeMillis();
    	}
    	while(pmQueue.size()!=0){
    		doChatPm((String)pmQueue.removeFirst());
            this.last_heartbeat = System.currentTimeMillis();
    	}
		try {
			my_socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void start(){//might as well put this as legacy as it used to expose the thread connect spawns a new thread now
		connect();
	}
}