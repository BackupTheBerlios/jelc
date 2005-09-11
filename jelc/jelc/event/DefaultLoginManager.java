package jelc.event;

import java.io.IOException;

import jelc.Connection;

public class DefaultLoginManager implements LoginListener{
String username;
String password;

	public DefaultLoginManager(String username,String password) {
		this.username=username;
		this.password=password;
	}

	
	public void showWelcome(Connection c) {
		try {
			c.login(username, password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
