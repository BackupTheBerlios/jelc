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
    private Socket my_socket;

    private DataInputStream in;

    private DataOutputStream out;

    private long last_heartbeat;

    private String username = "";

    private String password = "";

    //private String server = "localhost";
    private String server = "eternal-lands.solexine.fr";

    private int port = 2000;

    private Vector actors;

    public Client(String user, String pass) {
        this.username = user;
        this.password = pass;
        this.actors = new Vector(1000);
    }
    
    public Client(String user, String pass, String serv, int p) {
        this.username = user;
        this.password = pass;
        this.server = serv;
        this.port = p;
        this.actors = new Vector(1000);
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
    public void onPM(String person, String message){
    }
    /**
     * this message is called when the message is sent to echo that the message has been recieved, if it isn't recieved correctly, a system message will be sent saying it failed
     * 
     * @param message the message sent
     */
    public void onPmSent(String message){
    	
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
        send(Protocol.RAW_TEXT, text.getBytes(), text.length() + 1);
    }
    
    /**
     * sends a private message, it must be in the format
     * '/name message
     *   
     * @param text the text string to send
     */
    public  void chatPm(String text) {
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
     * to be overwritten by subclasses
     * 
     * @param p
     */
    public void onAddNewActor(Packet p) {
    }

    /**
     * to be overwritten by subclasses
     */
    public void onRemoveActor() {
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
        msg = this.username + " " + this.password + "\0";
        send(new Packet(Protocol.LOG_IN, msg.getBytes(), msg.length() + 1));
    }

    public Vector getActors() {
        return this.actors;
    }

    private void check_heartbeat() {
        if (System.currentTimeMillis() - this.last_heartbeat  > 5000) {
            this.last_heartbeat = System.currentTimeMillis();
            send(new Packet(14, null, 1));
        }
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
                        //actors.add(a.actor_id, a);
                        onAddNewActor(msg);
                        break;
                    case Protocol.REMOVE_ACTOR:
                        //actors.remove(msg.data.getShort());
                        onRemoveActor();
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
    private void processChat(String text){
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
    		onPM(from, message);
    	}
    	else if(text.startsWith("[PM to")){// you sent a pm message
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
    	onChat(text);
    }
}