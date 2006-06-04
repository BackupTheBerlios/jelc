package bot.deadface6.backends;

import bot.deadface6.Account;

public interface MessageListener {
	public void revieved(Channel c,String message);
}
