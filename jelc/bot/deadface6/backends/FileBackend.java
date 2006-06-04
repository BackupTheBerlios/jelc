package bot.deadface6.backends;

import java.util.List;
import java.util.Vector;
import java.io.File;

import bot.deadface6.Account;
import bot.deadface6.Replyer;

public class FileBackend implements Backend {
File f;

static FileBackend backend;
int status;
List openFiles=new Vector();
File root=new File("./files/");
boolean enabled=false;
	public FileBackend() {
		backend=this;
	}

	public Account getAccount(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return "file";
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public List<Channel> getChannels() {
		// TODO Auto-generated method stub
		return null;
	}

	public Channel getDefaultChannel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Channel getChannel(String name) {
		FileImpl tmp=new FileImpl(new File(root+name));
		openFiles.add(tmp);
		return tmp;
	}

	public void broadcast(String message) {
		//don't do anything
	}

	public int getStatus() {
		return Backend.READY;
	}
	
	private class FileImpl extends Channel{
		File f;
		FileImpl(File f){
			this.f=f;
		}

		@Override
		public String getName() {
			return "file:"+f;
		}

		@Override
		public Replyer getReplyer() {
			return null;
		}

		@Override
		public String getChannelString() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void send(String message) {
			
		}

		@Override
		public void stop() {
		}

		@Override
		public Backend getBackend() {
			return backend;
		}
		
	}

	public void configParameter(String line) {
		
		if(line.startsWith(Backend.ENABLED)){
			String tmp=line.substring(Backend.ENABLED.length());
			if(tmp.equalsIgnoreCase(Backend.TRUE)){
				enabled=true;
			}
			else if(tmp.equalsIgnoreCase(Backend.FALSE)){
				enabled=false;
			}
			else{
				enabled=false;
			}
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

}
