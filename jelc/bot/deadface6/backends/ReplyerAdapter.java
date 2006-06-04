package bot.deadface6.backends;

import bot.deadface6.Account;
import bot.deadface6.Replyer;
import bot.deadface6.backends.Channel;

public class ReplyerAdapter implements Replyer {
	Channel c;
	Account a;
	ReplyerAdapter(Channel c,Account a){
		this.c=c;
		this.a=a;
	}

	public void reply(String str) {
		System.out.println("Sending message back: "+str+"="+c);
		c.send(str);
	}

	public int getType() {
		return 0;
	}

	public int getMaxMessageSize() {
		return 140;
	}

	public void flush() {

	}

	public Account getAccount() {
		return a;
	}

	public Channel getChannel() {
		return c;
	}

}
