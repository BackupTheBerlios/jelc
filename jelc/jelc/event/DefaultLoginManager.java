package jelc.event;

import java.io.IOException;

import jelc.Connection;
import jelc.packet.Login;

public class DefaultLoginManager implements LoginManager{
String username;
String password;

	public DefaultLoginManager(String username,String password) {
		this.username=username;
		this.password=password;
	}

	
	public Login showWelcome( String message) {
		return new Login(username, password);
	}

	public void onLoginOk() {
		// TODO Auto-generated method stub

	}

	public void onLoginFail() {
		// TODO Auto-generated method stub

	}

	public void onLoginNotExist() {
		// TODO Auto-generated method stub

	}
}
