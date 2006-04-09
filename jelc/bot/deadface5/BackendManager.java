package bot.deadface5;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class BackendManager {
static BackendManager backend;
List backends;
	BackendManager(){
		backends=new Vector();
	}
	
	public boolean register(Backend b){
		return backends.add(b);
	}
	Replyer getReplyer(String s){
		for(Iterator itr=backends.iterator();itr.hasNext();){
			Backend tmp=(Backend)itr.next();
			if(s.startsWith(tmp.getBackendName())){
				return tmp.createReplyer(s.substring(tmp.getBackendName().length()));
			}
		}
		return null;
	}
	
	static BackendManager getInstance(){
		if(backend==null){
			backend=new BackendManager();
		}
		return backend;
	}
}
