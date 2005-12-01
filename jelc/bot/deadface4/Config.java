package bot.deadface4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Config {
	final static String USERNAME="USERNAME:";
	final static String PASSWORD="PASSWORD:";
	final static String PORT="PORT:";
	final static String HOSTNAME="HOSTNAME:";
	final static String ISIKNOW="ISIKNOW:";
	
	
	static Config config;
	
	private String username;
	private String password;
	private int port=2000;
	private String hostname="eternal-lands.network-studio.com";
	
	private boolean iknow=false;

	private Config(File f) throws FileNotFoundException,IOException{

		
		FileInputStream fis=new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		System.out.println("loading file:"+f);
		String line=br.readLine();
		while(line!=null&&(!line.equals(""))){
			
			if(line.startsWith(USERNAME)){
				setUsername(line.substring(USERNAME.length()));
			}
			else if(line.startsWith(PASSWORD)){
				setPassword(line.substring(PASSWORD.length()));
			}
			else if(line.startsWith(HOSTNAME)){
				this.setHostname(line.substring(HOSTNAME.length()));
			}
			else if(line.startsWith(PORT)){
				try{
				this.setPort(Integer.parseInt(line.substring(PORT.length())));
				}
				catch (NumberFormatException nfe){
					System.err.println("Error parseing port string: "+nfe.getLocalizedMessage());
				}
			}
			else if(line.startsWith(ISIKNOW)){
				String tmp=line.substring(ISIKNOW.length());
				if(tmp.equalsIgnoreCase("true")){
					iknow=true;
				}
				else{
					iknow=false;
				}
			}
			line=br.readLine();
		}
	}
	Config(){
		
	}
	
	public boolean save(){
		File f=new File("config.txt");
		try {
			PrintWriter out=new PrintWriter(f);
			
			out.println(USERNAME+username);
			out.println(PASSWORD+password);
			out.println(PORT+getPort());
			out.println(HOSTNAME+getHostname());
			if(isIknow()){
				out.println(ISIKNOW+isIknow());
			}
			
			out.println();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param hostname The hostname to set.
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	/**
	 * @return Returns the hostname.
	 */
	public String getHostname() {
		return hostname;
	}
	/**
	 * @param iknow The iknow to set.
	 */
	public void setIknow(boolean iknow) {
		this.iknow = iknow;
	}
	/**
	 * @return Returns the iknow.
	 */
	public boolean isIknow() {
		return iknow;
	}
	public static Config getConfig(){
		if(config==null){
			try {
				config=new Config(new File("config.txt"));
				return new Config(new File(""));
			} catch (FileNotFoundException e) {
				System.err.println("error loading file: "+e.getLocalizedMessage());
			} catch (IOException e) {
				System.err.println("error loading config: "+e.getLocalizedMessage());
			}
			
			
		}
		return config;
	}

}
