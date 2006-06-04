package bot.deadface6.backends;

import bot.deadface6.Account;

public class DebugMessageListener implements MessageListener {


	public void revieved(Channel c, String message) {
		System.out.println("Message from "+c+" "+message);
	}

}
