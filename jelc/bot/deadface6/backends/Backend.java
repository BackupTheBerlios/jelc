package bot.deadface6.backends;
import java.util.List;

import bot.deadface6.Account;

public interface Backend {
	static final int READY=0;
	static final int STOPED=1;
	static final int STARTING=2;//started but not connected
	
	static final String ENABLED="ENABLED:";
	
	
	static final String TRUE="TRUE";
	static final String FALSE="FALSE";
	
	
	public Account getAccount(String name);
	
	//getSession(String backend);
	public String getName();
	public void start();
	public void stop();
	public List<Channel> getChannels();
	public Channel getDefaultChannel();
	public Channel getChannel(String name);
	public void broadcast(String message);
	public int getStatus();
	public void configParameter(String line);
	public boolean isEnabled();
}
