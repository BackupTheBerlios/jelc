package bot.deadface6.backends;

import java.util.List;
import java.util.Vector;
import java.util.Iterator;

import bot.deadface6.Account;
import bot.deadface6.Config;

public class PipeManager {
	static PipeManager manager;
	private List<Pipe> pipes;
	
	public PipeManager() {
		setPipes(new Vector());
		for(Iterator itr=Config.getConfig().getPipes().iterator();itr.hasNext();){
			String tmp=itr.next().toString();
			if(tmp!=null){
				Pipe p=getPipe(tmp);
				if(p!=null){
					p.open();
					getPipes().add(p);
				}
			}
		}
	}
	
	public boolean setupPipe(Channel in, Channel out){
		Pipe p=new Pipe(in, out);
		p.open();
		getPipes().add(p);
		return true;
	}
	public boolean setupLurkPipe(Channel in, Channel out){
		Pipe p=new LurkPipe(in, out);
		p.open();
		getPipes().add(p);
		return true;
	}
	
	public boolean unsetupPipe(Channel in, Channel out){
		for(Iterator itr=getPipes().iterator();itr.hasNext();){
			Pipe tmp=(Pipe)itr.next();
			if(tmp.equals(in,out)){
				tmp.close();
				getPipes().remove(tmp);
				return true;
			}
		}
		return false;
	}
	public boolean addPipe(Pipe p){
		p.open();
		return getPipes().add(p);
	}
	
	private Pipe getPipe(String str){
		int index=str.indexOf(" ");
		Channel in=BackendManager.getInstance().getChannel(str.substring(0,index));
		if(in==null){
			return null; 
		}
		
		int index2=str.indexOf(" ",index+1);
		System.out.println("'"+str+"'"+index+" "+index2);
		Channel out=BackendManager.getInstance().getChannel(str.substring(index+1,str.length()));
		if(out==null){
			return null;
		}
		
		return new Pipe(in,out);
	}
	
	
	public class Pipe implements MessageListener{
		Channel out;
		Channel in;
		String prefix;
		
		public Pipe(Channel in,Channel out) {
			this.out=out;
			this.in=in;
			System.out.println("PIPE SET UP BETWEEN "+in+" and "+out);
		} 
		public void open(){
			in.addMessageListener(this);
			System.out.println("Set up a pipe from="+in.getChannelString()+" to="+out.getChannelString());
			out.send("Set up a pipe from="+in.getChannelString()+" to="+out.getChannelString());
			in.send("Set up a pipe from this channel to="+out.getChannelString());
		}

		public void revieved(Channel c, String message) {
			//out.send("<"+c+"> "+message);
			out.send(message);
		}
		public void close(){
			in.removeMessageListener(this);
		}
		public boolean equals(Channel in, Channel out){
			return this.in.equals(in)&&(this.out.equals(out));
		}
		public String toString(){
			return "Connection from "+in.getChannelString()+" to "+out.getChannelString();
		}
	}
	public  class Conversation extends Pipe{
		public Conversation(Channel in, Channel out) {
			super(in, out);
		}
		public void open(){
			in.addMessageListener(this);
			out.addMessageListener(this);
		}
		public void close(){
			in.removeMessageListener(this);
			out.removeMessageListener(this);
		}
		public void revieved(Channel c, String message) {
			if(c.equals(in)){
				//out.send("<"+c+"> "+message);
				out.send(message);
			}
			else if(c.equals(out)){
				in.send(message);
				//in.send("<"+c+"> "+message);
			}
			else{
				System.err.println("Recieved a messag from "+c+" that i did not request");
			}
		}		
	}
	public  class LurkPipe extends Pipe{


		public LurkPipe(Channel in, Channel out) {
			super(in, out);
		}
		
		public void open(){
			in.addMessageListener(this);
			System.out.println("Set up a pipe from="+in.getChannelString()+" to="+out.getChannelString());
			out.send("Set up a pipe from="+in.getChannelString()+" to="+out.getChannelString());
			//in.send("Set up a pipe from this channel to="+out.getChannelString());*/
		}
		
	}
	
	public static PipeManager getInstance(){
		if(manager==null){
			manager=new PipeManager();
		}
		return manager;
	}
	void setPipes(List<Pipe> pipes) {
		this.pipes = pipes;
	}
	public List<Pipe> getPipes() {
		return pipes;
	}

}
