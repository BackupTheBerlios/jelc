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

    Client(String user, String pass) {
        this.username = user;
        this.password = pass;
        this.actors = new Vector(1000);
    }

    /**
     * to be overwritten by subclasses gets calld once in every main loop
     * iteration;
     */
    public void onLoop() {
    }

    /**
     * to be overwritten by subclasses
     * 
     * @param text
     */
    public void onChat(String text) {
    }

    /**
     * to be overwritten by subclasses
     * 
     * @param p
     */
    public void onUnknowPacket(Packet p) {
    }

    /**
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

    private void send(byte protocol, byte[] data, int len) {
        try {
            this.out.write(protocol);
            this.out.write(len);
            this.out.write(0);
            this.out.write(data);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.last_heartbeat = System.currentTimeMillis();
    }

    private void send(byte[] b) {
        try {
            this.out.write(b[0]);
            this.out.write(b.length);
            this.out.write(0);
            for (int i = 1; i < b.length; i++) {
                this.out.write(b[i]);
            }
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

    public void chat(String text) {
        send(Protocol.RAW_TEXT, text.getBytes(), text.length() + 1);
    }

    public void chatPm(String text) {
        send(Protocol.SEND_PM, text.substring(1).getBytes(), text.length());
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
        if (System.currentTimeMillis() - this.last_heartbeat  > 2500) {
        	System.out.println(System.currentTimeMillis() - this.last_heartbeat);
            this.last_heartbeat = System.currentTimeMillis();
            send(new Packet(14, null, 1));
        }
    }

    private Packet poll() {
        Packet msg;
        int p = 0;
        int l = 0;
        byte[] d = new byte[1024];
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
                        onChat(new String(msg.data.array(), 1, msg.length - 1));
                        break;
                    case Protocol.ADD_NEW_ACTOR:
                        Actor a = new Actor(msg);
                        actors.add(a.actor_id, a);
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
}