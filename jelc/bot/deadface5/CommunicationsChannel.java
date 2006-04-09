/*
 * Created on 16-Feb-06
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface5;

public class CommunicationsChannel {
Replyer a;
Replyer b;
	public CommunicationsChannel(Replyer a, Replyer b) {
		this.a=a;
		a.getAccount().getSession().push(new CommunicationsSession(b));
		b.reply("session now active, to end send a single '.' to end");
		this.b=b;
		b.getAccount().getSession().push(new CommunicationsSession(a));
		a.reply("session now active, to end send a single '.' to end");
	}
	private class CommunicationsSession implements Session{
		Replyer r;
		CommunicationsSession(Replyer r){
			this.r=r;
		}
		public void processSession(String arg) {
			if(arg.equals(".")){
				a.reply("Session closed");
				b.reply("session closed");
				//remove session
			}
			else{
				r.reply(arg);
			}
		}
		
	}

}
