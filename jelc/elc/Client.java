/*
 * Created on Jan 11, 2005
 */
package elc;

import java.net.*;
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

    private String server = "eternal-lands.solexine.fr";

    private int port = 2000;

    Client(String user, String pass) {
        this.username = user;
        this.password = pass;
    }

    /**
     * gets calld once in every main loop iteration;
     */
    public abstract void onLoop();

    public abstract void onChat(String text);

    public abstract void onUnknowPacket(Packet p);

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
        try {
            this.out.write(p.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.last_heartbeat = System.currentTimeMillis();
    }

    public void chat(String text) {
        send(Protocol.RAW_TEXT, text.getBytes(), text.length() + 1);
    }

    public void login() {
        String msg;
        msg = this.username + " " + this.password + "\0";
        send(new Packet(Protocol.LOG_IN, msg.getBytes(), msg.length() + 1));
    }

    private void check_heartbeat() {
        if (this.last_heartbeat - System.currentTimeMillis() < 2500) {
            this.last_heartbeat = System.currentTimeMillis();
            send(new Packet(14, null, 1));
        }
    }

    private Packet poll() {
        Packet msg;
        int p;
        int l;
        byte[] d = new byte[1024];

        try {
            if (this.in.available() > 3) {
                p = this.in.readUnsignedByte();
                l = this.in.readByte();
                //l += this.in.readUnsignedByte() << 8;
                this.in.read(d, 0, l);
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
            this.my_socket = new Socket(this.server, this.port);
            this.in = new DataInputStream(this.my_socket.getInputStream());
            this.out = new DataOutputStream(this.my_socket.getOutputStream());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.last_heartbeat = System.currentTimeMillis();

        while (true) {
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
                    onChat(new String(msg.data));
                    break;
                default:
                    onUnknowPacket(msg);
                    break;
                }

            }

            try {
                sleep(100);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            check_heartbeat();

            onLoop();
        }
    }
}