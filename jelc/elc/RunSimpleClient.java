/*
 * Created on Jan 20, 2005
 */
package elc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author hoffmamn
 */
public class RunSimpleClient {

	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		SimpleClient c = new SimpleClient("", "");
		c.start();
	}
}
