package jelc.event;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import jelc.Connection;

public class LoginDialog extends JDialog implements LoginListener, ActionListener {
JButton login;
JButton newAccount;
JTextField username;
JPasswordField password;
Connection c;
	public LoginDialog(JFrame frame)  {
		super(frame, true);
		JPanel root=new JPanel(new GridLayout(3,2));
		
		root.add(new JLabel("username: "));
		username=new JTextField();
		root.add(username);
		
		root.add(new JLabel("Password: "));
		password=new JPasswordField();
		root.add(password);
		
		newAccount=new JButton("New Account");
		newAccount.setEnabled(false);
		root.add(newAccount);
		
		login=new JButton("Login");
		login.addActionListener(this);
		root.add(login);
		
		this.setSize(new Dimension(150,120));
		this.getContentPane().add(root);
	}
	public void showWelcome(Connection c) {
		this.c=c;
		this.setVisible(true);
	}

	public void onLoginOk() {
		this.setVisible(false);
	}

	public void onLoginFail() {

	}

	public void onLoginNotExist() {

	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(login)){
			this.setTitle("logging in");
			try {
				c.login(login.getText(),new String(password.getPassword()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		LoginDialog login=new LoginDialog(new JFrame());
		System.out.println("hi");
		login.setVisible(true);
		System.out.println("hihi");
		
	}
}
