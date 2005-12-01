package bot.deadface4;

public interface Replyer {
	public void reply(String str);
	public int getType();
	public String getName();
	public int getMaxMessageSize();
	public void flush();
	public Account getAccount();
}
