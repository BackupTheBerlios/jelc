package bot.deadface6.services;
import bot.deadface6.Config;
import bot.deadface6.backends.*;
import elc.SystemInterface;
import elc.ClientConnection;

public class MessageBroadcast implements TimeEvent {
	public void onChangeMap(String map) {
		// TODO Auto-generated method stub

	}

	public void timeReached() {
		Channel c=BackendManager.getInstance().getChannel(Chat.LOCALCHANNEL);
		if (c!=null){
			String message=Config.getConfig().getBroadcastmessage();
			if(!message.equals("")){
				c.send(message);
			}
		}
	}
}
