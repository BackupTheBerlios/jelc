/*
 * Created on 14/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface6.commands;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Person {
String name;
public String guild;
private long lastSeen;
	Person(String name, String guild){
		this.name=name;
		this.guild=guild;
	}
	Person(String name, String guild, long lastSeen){
		this.name=name;
		this.guild=guild;
		this.setLastSeen(lastSeen);
	}
	public boolean equals(Person p){
		return (p.name).equalsIgnoreCase(name);
	}
	public String toString(){
		if(!guild.equals("")){
			return name+"."+guild;
		}
		return name;
	}
	public String getName(){
		return name;
	}
	public String getGuild(){
		return guild;
	}
	/**
	 * @param lastSeen The lastSeen to set.
	 */
	void setLastSeen(long lastSeen) {
		this.lastSeen = lastSeen;
	}
	/**
	 * @return Returns the lastSeen.
	 */
	long getLastSeen() {
		return lastSeen;
	}
}
