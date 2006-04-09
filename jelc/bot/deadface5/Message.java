package bot.deadface5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Message{
	private String to;
	private String from;
	private String subject;
	long time;
	private List message;
	private boolean read=false;
	
	static final String FROM="FROM:";
	static final String TO="TO:";
	static final String TIME="TIME:";
	static final String READ="READ:";
	static final String SUBJECT="SUBJECT:";
	
	public Message(String to,String from,List message){
		this.setTo(to);
		this.setFrom(from);
		this.setMessage(message);
		time=System.currentTimeMillis();
	}
	public Message(File f) throws FileNotFoundException,IOException{
		setMessage(new Vector());
		FileInputStream fis=new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		System.out.println("loading file:"+f);
		String line=br.readLine();
		while(line!=null&&(!line.equals(""))){
			if(line.startsWith(FROM)){
				setFrom(line.substring(FROM.length()));
			}
			else if(line.startsWith(TO)){
				setTo(line.substring(TO.length()));
			}
			else if(line.startsWith(SUBJECT)){
				setSubject(line.substring(SUBJECT.length()));
			}
			else if(line.startsWith(TIME)){
				time=Long.parseLong(line.substring(TIME.length()));
			}
			else if(line.startsWith(READ)){
				String tmp=line.substring(READ.length());
				if(tmp.equalsIgnoreCase("true")){
					setRead(true);
				}
				else{
					setRead(false);
				}
				
			}
			line=br.readLine();
		}
		line=br.readLine();
		while(line!=null){
			getMessage().add(line);
			line=br.readLine();
		}
	}
	public boolean save(){
		
		//check if folder exists
		//File dir=new File("./mail/"+to+"/");
		File dir=new File("./mail/"+getTo().toLowerCase()+"/");
		if(!dir.exists()){
			dir.mkdir();//if it doesn't creat the folder
			System.out.println("created mail directory : "+dir);
		}
		
		
		
		//File f=new File("./mail/"+to+"/"+System.currentTimeMillis()+".txt");
		File f=new File("./mail/"+getTo().toLowerCase()+"/"+time+".txt");
		try {
			PrintWriter out=new PrintWriter(f); 
			out.println(FROM+ getFrom());
			out.println(TO+getTo());
			out.println(SUBJECT+getSubject());
			out.println(TIME+time);
			if(isRead()){
				out.println(READ+isRead());
				
			}
			System.out.println("----saveing READ:"+isRead());
			out.println();
			//System.out.println(":"+message);
			Iterator i=getMessage().iterator();
			while(i.hasNext()){
				out.println(i.next());
			}
			out.close();
			
			//clean up account data by loading the new message
			Account account=AccountManager.getAccountManger().getAccount(getTo());
			account.setMessages(Message.getMessages(getTo()));
			account.setUnreadMessages(true);
			
			return true;
		     
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	static public List<Message> getMessages(String name){
		List<Message> messages=new LinkedList<Message>();
		File f=new File("./mail/"+name.toLowerCase()+"/");
		File[] files=f.listFiles();
		System.out.println(":"+files.length);
		if(f.exists()&&f.isDirectory()){
			for(int i=0;i<files.length;i++){
				if (files[i].isFile()&&files[i].canRead()){
					try {
						messages.add(new Message(files[i]));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		/*else{
			System.err.println("no lists loaded. put some txt files in ./lists/");
		}*/
		
		return messages;
	}
	/**
	 * @param read The read to set.
	 */
	public void setRead(boolean read) {
		this.read = read;
	}
	/**
	 * @return Returns the read.
	 */
	public boolean isRead() {
		return read;
	}
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	public String toString(){
		if(read){
			return subject;
		}
		return "*"+subject;
	}
	/**
	 * @param message The message to set.
	 */
	public void setMessage(List message) {
		this.message = message;
	}
	/**
	 * @return Returns the message.
	 */
	public List getMessage() {
		return message;
	}
	/**
	 * @param to The to to set.
	 */
	public void setTo(String to) {
		this.to = to;
	}
	/**
	 * @return Returns the to.
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param from The from to set.
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return Returns the from.
	 */
	public String getFrom() {
		return from;
	}
}