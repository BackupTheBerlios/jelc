package bot.deadface6.backends;

import bot.deadface6.Account;

public class Pipe  implements MessageListener{
Channel out;
Channel in;
	public Pipe(Channel in,Channel out) {
		this.out=out;
		this.in=in;
		in.addMessageListener(this);
		System.out.println("Set up a pipe from="+in.getChannelString());
		System.out.println(" to="+out.getChannelString());
	}


	public void revieved(Account from, String message) {
		// TODO Auto-generated method stub
		out.send("Message from channel '"+in.getName()+"': "+message);
		
	}


	public void revieved(Channel c, String message) {
		out.send("<"+c+"> "+message);
	}

}
