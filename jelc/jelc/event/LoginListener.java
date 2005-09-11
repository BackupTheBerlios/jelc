package jelc.event;

public interface LoginListener {
	/**
	 * This method is called when the client connects to the server (and sends the welcome message)
	 * put your login code in here, and call jelc.Connection.login(username,password)
	 * 
	 * @param c  The client connection that recieved the message
	 */
	public void showWelcome(jelc.Connection c);
	public void onLoginOk();
	public void onLoginFail();
	public void onLoginNotExist();
}
