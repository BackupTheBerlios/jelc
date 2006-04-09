package bot.deadface5.services;

import java.util.List;
import java.util.Iterator;

import bot.deadface5.*;
import jelc.playerView.*;


public class CleanupWatcher implements TimeEvent{

	public CleanupWatcher(TimeWatcher watcher) {
		watcher.addTimeWatcher(this,15,true);
	}

	public void timeReached() {
		AccountManager.getAccountManger().cleanup();
	}

}

