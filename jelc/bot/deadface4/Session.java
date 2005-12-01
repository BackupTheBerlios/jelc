package bot.deadface4;
/**
 * The purpse of this is to allow for communication between sessions, the idea is that you can register your class to recieve a callback instead of process for that account.
 * 
 * @author dns
 */
public interface Session {
	public void processSession(String arg);
}
