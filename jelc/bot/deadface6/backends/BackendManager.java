package bot.deadface6.backends;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import bot.deadface6.Config;



public class BackendManager {
Vector<Backend> backends=new Vector();
static BackendManager backendManager;
	private BackendManager() {
		/*addBackend(new TelnetBackend(9999));
		addBackend(new ConsoleBackend());*/
		
		//addBackend(new Console());
		/*if(Config.getConfig().isIrcEnabled()){
			addBackend(IRC2.getInstance());
		}*/
		
	}
	public boolean addBackend(Backend b){
		return backends.add(b);
	}
	public boolean removeBackend(Backend b){
		return backends.remove(b);
	}
	
	public List getBackends(){
		List<String> res=new Vector();
		for(Iterator itr=backends.iterator();itr.hasNext();){
			Backend b=(Backend)itr.next();
			res.add(b.getName());
		}
		return res;
	}
	public Backend getBackend(String name){
		for(Iterator itr=backends.iterator();itr.hasNext();){
			Backend b=(Backend)itr.next();
			if(b.getName().equalsIgnoreCase(name)){
				return b;
			}
		}
		return null;
	}
	
	public bot.deadface6.backends.Channel getChannel(String name){
		int index=name.indexOf(":");
		if(index==-1){//no :found, so justget the default channel from that backend
			for(Iterator itr=backends.iterator();itr.hasNext();){
				Backend b=(Backend)itr.next();
				if(name.equalsIgnoreCase(b.getName())){
					return b.getDefaultChannel();
				}
			}
		}
		else{
			String backend=name.substring(0,index);
			String channel=name.substring(index+1);
			//System.out.println("|"+backend+"|"+channel+"|");
			for(Iterator itr=backends.iterator();itr.hasNext();){
				Backend b=(Backend)itr.next();
				//System.out.println("checking backeind:"+b.getName());
				if(b.getName().equalsIgnoreCase(backend)){
					//System.out.println("got prefix ok"+b.getChannels().size());
					for(Iterator itr2=b.getChannels().iterator();itr2.hasNext();){
						Channel c=(Channel)itr2.next();
						//System.out.println("checking channel: "+c.getChannelString());
						if(c.getName().equals(channel)){
							return c;
						}
					}
					//System.out.println("nope not in list, try another way");
					return b.getChannel(channel);
				}
			}
		}
		//
		
		
		return null;
	}
	
	public void startAll(){
		for(Iterator itr=backends.iterator();itr.hasNext();){
			Backend b=(Backend)itr.next();
			b.start();
		}
	}
	public void stopAll(){
		for(Iterator itr=backends.iterator();itr.hasNext();){
			Backend b=(Backend)itr.next();
			b.stop();
		}
	}

	public static BackendManager getInstance(){
		if(backendManager==null){
			backendManager=new BackendManager();
		}
		return backendManager;
	}
	
	public void broadcast(String message){
		for(Iterator itr=backends.iterator();itr.hasNext();){
			Backend b=(Backend)itr.next();
			Channel c=b.getDefaultChannel();
			if(c!=null){
				c.send(message);
			}
		}
	}
	public static void main(String[] args){
		BackendManager  backend=BackendManager.getInstance();
		
		backend.startAll();
		
		
		for(Iterator itr=backend.getBackends().iterator();itr.hasNext();){
			System.out.println("Backend '"+itr.next()+"'");
		}
		
		Backend b=backend.getBackend("console");
		if(b!=null){
			System.out.println("Backend = "+b.getName());
			
			for(Iterator itr=b.getChannels().iterator();itr.hasNext();){
				Channel c=(Channel)itr.next();
				System.out.println("Channel: "+c.getName());
				
			}
			
			Channel c=b.getDefaultChannel();
			if(c!=null){
				c.addMessageListener(new DebugMessageListener());
			}
			else{
				System.out.println("No Default Channel");
			}
			
		}
		else{
			System.out.println("Error, backend is not found");
		}
		
		
		
		Channel console=backend.getChannel("console");
		System.out.println(" "+console);
		
		
		
	}

}
