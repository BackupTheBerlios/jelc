/*
 * Created on Jan 20, 2005
 */
package elc;

/**
 * @author frak
 * A simple Client as example and test.
 */
public class SimpleClient extends Client {

	/**
	 * @param user
	 * @param pass
	 */
	SimpleClient(String user, String pass) {
		super(user, pass);
	}

	/* (non-Javadoc)
	 * @see elc.Client#onChat(java.lang.String)
	 */
	public void onChat(String text) {
		System.out.println(text);
	}
}
