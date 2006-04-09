package bot.deadface5;

public interface Backend {
	String getBackendName();
	Replyer createReplyer(Account account);
	Replyer createReplyer(String s);
}
