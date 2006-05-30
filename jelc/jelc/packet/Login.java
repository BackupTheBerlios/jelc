/*
 * Created on 11/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jelc.packet;

import java.io.IOException;

import jelc.Protocol;
import jelc.io.EndianDataOutputStream;

public class Login implements OutputPacket {
	private String username;
	private String password;
	Login(){
		
	}
	public Login(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public void writePacket(EndianDataOutputStream out) throws IOException {
		out.writeByte(Protocol.LOG_IN);
		String str=getUsername()+" "+getPassword()+'\0';
		out.writeEndianShort((short)(str.length()+1));
		out.write(str.getBytes());
		//System.out.println("sent login");
	}
	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

}
