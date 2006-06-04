package bot.deadface6.commands;

//import bots.deadface.SeenList;
import bot.deadface6.Account;
import bot.deadface6.AccountManager;
import bot.deadface6.BotCommand;
import bot.deadface6.MyBot;
import bot.deadface6.Replyer;
import bot.deadface6.StatsCache;
import jelc.playerView.Player;
import jelc.playerView.PlayersOnline;


/**
 * @author dns
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Stats implements BotCommand {
PlayersOnline online;
//SeenList2 seen;
MyBot bot;
	public Stats( MyBot bot){
		//this.seen=seen2;
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
				//Player player=new Player(playerName);
				Player  player=StatsCache.getInstance().getPlayer(playerName);
				int status=player.getStatus();
				if(status==Player.PARSEDOK){
					//System.out.println("stas for: "+player.name);
					Account account=AccountManager.getAccountManger().getAccount(playerName);
					if(account.getGuild()!=null){
						if(account.getGuild().equals("")){
							reply.reply(playerName+"(N/A) Phys: "+player.getPhysique()+", Coord: "+player.getCoordination()+", Attack: "+player.getAttack()+", Defence: "+player.getDefense()+", Combat Level: "+player.getCombatLevel());
						}
						else{
							reply.reply(playerName+"("+account.getGuild()+") Phys: "+player.getPhysique()+", Coord: "+player.getCoordination()+", Attack: "+player.getAttack()+", Defence: "+player.getDefense()+", Combat Level: "+player.getCombatLevel());
						}
					}
					else{
						reply.reply(playerName+"(?) Phys: "+player.getPhysique()+", Coord: "+player.getCoordination()+", Attack: "+player.getAttack()+", Defence: "+player.getDefense());
					}
					reply.reply("Health: "+player.getHelth()+", Mana: "+player.getMana()+", Magic: "+player.getMagic()+", Summoning: "+player.getSummoning()+", Combat Level: "+player.getCombatLevel());	        		
				}
				else if(status==Player.INCACHE){
					reply.reply("That player has privacy on but I have a (possibly stale) cache, their combat level is "+player.getCombatLevel()+".");
				}
				else if(status==Player.PLAYERDOESNOTEXIST){
					reply.reply("error, stats wheren't retrieved, perhaps you put a wrong name in");
				}
				else if((status==Player.PRIVACYON)||(status==Player.FILENOTFOUND)){
					reply.reply("error, that player has privacy on so i cannot see the stats");
				}
				else if(status==Player.PAGENOTFOUND){
					reply.reply("error, page not fond. perhaps they moved the online pages?");
				}
				else if(status==Player.LOCKED){
					reply.reply("error, This player has done somthing bad and has been locked");
				}
				else{
					reply.reply("error, an unknown error ocured and i could not fix it");
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
				//Player player=new Player(playerName);
//				Person p=seen.find(playerName);
				//int status=player.Parse();
				
				Player  player=StatsCache.getInstance().getPlayer(playerName);
				int status=player.getStatus();
				if(status==Player.PARSEDOK){
	        		//System.out.println("skills for: "+player.name);
					Account account=AccountManager.getAccountManger().getAccount(playerName);
					if(account.getGuild()!=null){
	    				if(account.getGuild().equals("")){
	    					reply.reply(playerName+"(N/A) Pickpoints:" +player.getPickPoints()+" , OA "+player.getOverall()+", Carry capacity: "+player.getCarry()+" EMU, Mana: "+player.getMana());
	    				}
	    				else{
	    					reply.reply(playerName+"("+account.getGuild()+") Pickpoints:" +player.getPickPoints()+" , OA "+player.getOverall()+", Carry capacity: "+player.getCarry()+" EMU, Mana: "+player.getMana());
	    				}
	        		}
	    			else{
	    				reply.reply(playerName+"(?) Pickpoints:" +player.getPickPoints()+" , OA "+player.getOverall()+", Carry capacity: "+player.getCarry()+" EMU, Mana: "+player.getMana());
	    			}
	    			reply.reply("ph: "+player.getPhysique()+" co:"+player.getCoordination()+" re:"+player.getReasoning()+" wi:"+player.getWill()+" in:"+player.getInstinct()+" vi:"+player.getVitality());
	    			reply.reply("Mag:"+player.getMagic()+" Har:"+player.getHarvest()+" Man:"+player.getManufacture()+" Alc:"+player.getAlchemy()+" Pot:"+player.getPotion()+" Sum:"+player.getSummoning()+" Cra:"+player.getCrafting());
				}
				else if(status==Player.PLAYERDOESNOTEXIST){
					reply.reply("error, stats wheren't retrieved, perhaps you put a wrong name in");
				}
				else if((status==Player.PRIVACYON)||(status==Player.FILENOTFOUND)||(status==Player.INCACHE)){
					reply.reply("error, that player has privacy on so i cannot see the stats");
				}
				else if(status==Player.PAGENOTFOUND){
					reply.reply("error, page not fond. perhaps they moved the online pages?");
				}
				else if(status==Player.LOCKED){
					reply.reply("error, This player has done somthing bad and has been locked");
				}
				else{
					reply.reply("error, an unknown error ocured and i could not fix it");
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
