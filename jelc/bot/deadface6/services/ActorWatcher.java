package bot.deadface6.services;

import bot.deadface6.Account;
import bot.deadface6.AccountManager;
import bot.deadface6.Misc;
import bot.deadface6.backends.Chat;
import elc.Actor;
import elc.ActorInterface;
import elc.EnhancedActor;

public class ActorWatcher implements ActorInterface {

	public ActorWatcher() {
		System.out.println("makeing him see");
		Chat.getInstance().getConnection().addActorInterface(this);
	}

	public void onAddNewActor(Actor a) {
		// TODO Auto-generated method stub
		
	}

	public void onRemoveActor(Actor a) {
		// TODO Auto-generated method stub
		
	}

	public void onAddNewEnhancedActor(EnhancedActor a) {
//		System.out.println("i see: "+a.getActor_name()+", "+a.getActorStraightName()+", "+a.getActorGuild());
		String name=Misc.StripColours(a.getActorStraightName());
		Account account=AccountManager.getAccountManger().getAccount(name);
		account.setName(name);
		account.setGuild(a.getActorGuild());
		account.setLastSeen(System.currentTimeMillis());
		account.save();
		//System.out.println("saveing account"+account+"  "+account.getLastSeen());
	}

}
