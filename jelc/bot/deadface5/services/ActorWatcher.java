package bot.deadface5.services;

import elc.Actor;
import elc.ActorInterface;
import elc.EnhancedActor;
import bot.deadface5.*;
import elc.ClientConnection;

public class ActorWatcher implements ActorInterface {

	public ActorWatcher(ClientConnection c) {
		System.out.println("makeing him see");
		c.addActorInterface(this);
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
