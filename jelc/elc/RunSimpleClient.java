/*
 * Created on Jan 20, 2005
 */
package elc;

/**
 * @author frak
 */
public class RunSimpleClient {
    public static void main(String[] args) {
    	SimpleClient c;
    	if(args.length < 2){
    		System.out.println("usage: java elc/RunSimpleClient user pass [server port]");
    		System.exit(1);
    	}
    	if(args.length == 4){
    		c = new SimpleClient(args[0], args[1], args[2], Integer.valueOf(args[3]).intValue());
    	} else {
    		c = new SimpleClient(args[0], args[1]);	
    	}
        c.start();
    }
}