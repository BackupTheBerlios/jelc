package bot.deadface6;

import bot.deadface6.backends.Channel;

public interface Replyer {
	public void reply(String str);
	public int getType();
	//public String getName();
	public int getMaxMessageSize();
	public void flush();
	public Account getAccount();
	public Channel getChannel();
}
