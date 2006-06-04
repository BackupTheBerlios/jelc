/*
 * Created on 27-Jan-06
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface6;

import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

import playerView.PlayerList;

public class Guild {
static Guild guild;
private Vector members;
	private Guild(){
		PlayerList list =new PlayerList(new File("guild.txt"));
		members=list.getList();
	}

	public static Guild getInstance(){
		if(guild==null){
			guild=new Guild();
		}
		return guild;
	}

	/**
	 * @param members The members to set.
	 */
	public void setMembers(List members) {
		this.members = (Vector) members;
	}

	/**
	 * @return Returns the members.
	 */
	public List getMembers() {
		return members;
	}
	public boolean save(){
		PlayerList list =new PlayerList("guild",members);
		return list.save(new File("guild.txt"));
	}
	public boolean addName(String name){
		if(!members.contains(name)){
			if(members.add(name)){
				return save();
			}
			return false;
		}
		return true;
	}
	public boolean isGuild(String name){
		for(Iterator itr=members.iterator();itr.hasNext();){
			String tmp=itr.next().toString();
			if(tmp.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
}
