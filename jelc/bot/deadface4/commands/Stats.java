package bot.deadface4.commands;

//import bots.deadface.SeenList;
import bot.deadface4.BotCommand;
import bot.deadface4.MyBot4;
import bot.deadface4.Replyer;
import playerView.Player;
import playerView.PlayersOnline;

/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Stats implements BotCommand {
PlayersOnline online;
SeenList2 seen;
MyBot4 bot;
	public Stats(PlayersOnline on, SeenList2 seen2,MyBot4 bot){
		this.online=on;
		this.seen=seen2;
		this.bot=bot;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#process(java.lang.String, java.lang.String[], int)
	 */
	public boolean process(Replyer reply, String[] args) {
    	if(args[0].equalsIgnoreCase("stats")||args[0].equalsIgnoreCase("stat")){
    		if(args.length==2){
	    		String playerName=args[1];
	    		//System.out.println("|"+playerName+"|");
	    		Player player=new Player(playerName,online);
	    		
	    		if(player.update()){
	        		//System.out.println("stas for: "+player.name);
	    			Person p=seen.find(playerName);
	    			if(p!=null){
	    				if(p.getGuild().equals("")){
	    					reply.reply(playerName+"(N/A) Phys: "+player.getPhysique()+", Coord: "+player.getCoordination()+", Attack: "+player.getAttack()+", Defence: "+player.getDefense()+", Combat Level: "+player.getCombatLevel());
	    				}
	    				else{
	    					reply.reply(playerName+"("+p.getGuild()+") Phys: "+player.getPhysique()+", Coord: "+player.getCoordination()+", Attack: "+player.getAttack()+", Defence: "+player.getDefense()+", Combat Level: "+player.getCombatLevel());
	    				}
	    			}
	    			else{
	    				reply.reply(playerName+"(?) Phys: "+player.getPhysique()+", Coord: "+player.getCoordination()+", Attack: "+player.getAttack()+", Defence: "+player.getDefense());
	    			}
	    			reply.reply("Health: "+player.getHelth()+", Mana: "+player.getMana()+", Magic: "+player.getMagic()+", Summoning: "+player.getSummoning()+", Combat Level: "+player.getCombatLevel());
	        		
	    		}
	    		else{
	        		//System.out.println("stas wheren't retrieved for for: "+player.dump());
	        		reply.reply("error, stats wheren't retrieved, perhaps you put a wrong name in");
	    		}
    		}
    		else{
    			reply.reply("error, you need to specify a player name eg: stats dns");
    		}
    		return true;
    	}
    	else if(args[0].equalsIgnoreCase("skills")||args[0].equalsIgnoreCase("skill")){
    		if(args.length==2){
	    		String playerName=args[1];
	    		Player player=new Player(playerName,online);
	    		Person p=seen.find(playerName);
	    		if(player.update()){
	        		//System.out.println("skills for: "+player.name);
	    			if(p!=null){
	    				if(p.getGuild().equals("")){
	    					reply.reply(playerName+"(N/A) Pickpoints:" +player.getPickPoints()+" , OA "+player.getOverall()+", Carry capacity: "+player.getCarry()+" EMU, Mana: "+player.getMana());
	    				}
	    				else{
	    					reply.reply(playerName+"("+p.getGuild()+") Pickpoints:" +player.getPickPoints()+" , OA "+player.getOverall()+", Carry capacity: "+player.getCarry()+" EMU, Mana: "+player.getMana());
	    				}
	        		}
	    			else{
	    				reply.reply(playerName+"(?) Pickpoints:" +player.getPickPoints()+" , OA "+player.getOverall()+", Carry capacity: "+player.getCarry()+" EMU, Mana: "+player.getMana());
	    			}
	    			reply.reply("ph: "+player.getPhysique()+" co:"+player.getCoordination()+" re:"+player.getReasoning()+" wi:"+player.getWill()+" in:"+player.getInstinct()+" vi:"+player.getVitality());
	    			reply.reply("Mag:"+player.getMagic()+" Har:"+player.getHarvest()+" Man:"+player.getManufacture()+" Alc:"+player.getAlchemy()+" Pot:"+player.getPotion()+" Sum:"+player.getSummoning()+" Cra:"+player.getCrafting());
	    		}
	    		else{
	        		//System.out.println("stas wheren't retrieved for for: "+player.dump());
	        		reply.reply("error, stats wheren't retrieved, perhaps you put a wrong name in");
	    		}
    		}
    		else{
    			reply.reply("error, you need to specify a player name eg: skills dns");
    		}
    		return true;
    	}
    	return false;
	}

	/* (non-Javadoc)
	 * @see bots.deadface2.BotCommand#sendHelp(java.lang.String, int)
	 */
	public void sendHelp(Replyer reply) {
		reply.reply("stats NAME    - shows combat statistics for the player");
		reply.reply("skills NAME   - shows all skills for thaty player");
	}

	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
