/*
 * Created on Jan 20, 2005
 */
package elc;

/**
 * @author frak
 */
public class RunSimpleClient {
    public static void main(String[] args) {
        SimpleClient c = new SimpleClient(args[0], args[1]);
        c.start();
    }
}