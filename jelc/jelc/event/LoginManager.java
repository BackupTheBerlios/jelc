package jelc.event;

import jelc.packet.Login;

public interface LoginManager {
	/**
	 * This method is called when the client connects to the server (and sends the welcome message)
	 * You should send back a valid login packet or you may return null but you will have to send a valid login packet later
	 * 
	 * @param message  The welcome message
	 * @return Login  A login packet to be sent back
	 */
	public Login showWelcome(String message);
	public void onLoginOk();
	public void onLoginFail();
	public void onLoginNotExist();
}
