package bot.deadface6.services;


import bot.deadface6.*;


public class CleanupWatcher implements TimeEvent{

	public CleanupWatcher(TimeWatcher watcher) {
		watcher.addTimeWatcher(this,15,true);
	}

	public void timeReached() {
		AccountManager.getAccountManger().cleanup();
	}

}

