/*
 * Created On 5/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface4.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import bot.deadface4.MyBot4;
import elc.ClientInterface;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Log implements ClientInterface{
MyBot4 bot;
BufferedWriter out;
File log;
	public Log(MyBot4 bot){
		this.bot=bot;
		bot.con.addClientListener(this);
		try{
			Calendar c = new GregorianCalendar();
			log=new File("./log/"+c.get(Calendar.DATE)+"-"+c.get(Calendar.MONTH)+" at "+c.get(Calendar.HOUR_OF_DAY)+"-"+c.get(Calendar.MINUTE)+".txt");
			System.out.println("-Useing log file: "+log);
			out = new BufferedWriter(new FileWriter(log),1000000);
		}
		catch(Exception e){e.printStackTrace();}
	}
	public void onChat(String text) {
		write(text);
		System.out.println(text);
	}
    public void write(String text) {
    	try {
			out.write(System.currentTimeMillis()+": "+text+"\n");
			out.flush();
		} catch (IOException e1) {
			System.out.println("error while trying to write to log file");
		}
    }
	public void onChat(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	public void onChannelChat(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	public void onPm(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	public void onPmSent(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	public void onGm(String person, String message) {
		// TODO Auto-generated method stub
		
	}
	public void onHint(String message) {
		// TODO Auto-generated method stub
		
	}
	public void onSystemMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	public void onMinute(int time) {
		// TODO Auto-generated method stub
		
	}
	public void onQuit() {
		System.out.println("flushing log file: "+log);
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
