/*
 * Created on Jan 11, 2005
 */
package elc;

import java.net.*;
import java.io.*;

/**
 * @author hoffmamn
 * 
 * TODO alot
 */
public abstract class Client extends Thread {
	private Socket my_socket;
	private InputStream in;
	private OutputStream out;
	private long last_heartbeat;
	private String username = "";
	private String password = "";
	private String server = "eternal-lands.solexine.fr";
	private int port = 2000;

	Client(String user, String pass) {
		username = user;
		password = pass;
	}

	public abstract void onChat(String text);

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
	}

	private void send(Packet p) {
		try {
			this.out.write(p.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void chat(String text) {
		send((byte) 0, text.getBytes(), text.length() + 1);
	}

	public void login() {
		String msg;
		msg = this.username + " " + this.password + "\0";
		send(new Packet(140, msg.getBytes(), msg.length() + 1));
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

				p = this.in.read();
				l = this.in.read();
				this.in.skip(1);
				//System.out.println("recv p" + p + " l" + l);
				this.in.read(d, 0, l - 1);
				msg = new Packet(p, d, l);
				return msg;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void run() {

		String line = "";
		Packet msg;
		int runlevel = 0;
		int second = 0;

		try {
			this.my_socket = new Socket(this.server, this.port);
			this.in = this.my_socket.getInputStream();
			this.out = this.my_socket.getOutputStream();
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
				send(new Packet(9, null, 1));
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
				//System.out.println("got msg");
				switch (msg.protocol) {
				case 0:
					onChat(new String(msg.data));
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
		}
	}
}