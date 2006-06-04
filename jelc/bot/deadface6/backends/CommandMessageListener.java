package bot.deadface6.backends;
import bot.deadface6.*;

public class CommandMessageListener implements MessageListener {
Channel c;
	public CommandMessageListener(Channel c) {
		this.c=c;
		c.addMessageListener(this);
	}

	public void revieved(String name, String message) {
		System.out.println("Processing: "+message+" "+name);
		
		//MyBot.getInstance().processCommands(c.getBackend().getAccount(),message);
	}

	public void revieved(Account from, String message) {
		
		MyBot.getInstance().processCommands(new ReplyerAdapter(c,from),message);
	}

	public void revieved(Channel c, String message) {
		MyBot.getInstance().processCommands(c.getReplyer(),message);
	}

}
