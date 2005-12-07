package jelc.playerView;
import java.io. BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
/*
 * Created on 1/10/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author me
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Player {
	public final static int PARSEDOK=0;
	public final static int PRIVACYON=1;
	public final static int PLAYERDOESNOTEXIST=2;
	public final static int PAGENOTFOUND=3;
	public final static int IOEXCEPTION=4;
	
public String name;
//Main Attributes
public String Physique;
public String Coordination;
public String Reasoning;
public String Will;
public String Instinct;
public String Vitality;
//Cross Attributes
public String Might;
public String Matter;
public String Toughness;
public String Charm;
public String Reaction;
public String Rationality;
public String Dexterity;
public String Ethereality;
//Skills
public String Overall;
public String Attack;
public String Defense;
public String Magic;
public String Harvest;
public String Manufacture;
public String Alchemy;
public String Potion;
public String Summoning;
public String Crafting;
Date lastUpdate;
//Brom's Ints :P
public String combatLevel;
public int react;
public int tough;
public int dex;
public int matter;
public int attack;
public int defense;

private boolean privacy=false;
boolean updated=false;
	public Player(String name){
		this.name=name;
	}
	int Parse(){
		URL page;
		try {
			
			page = new URL("http://eternal-lands.solexine.fr/~radu/view_user.php?user="+name);
			URLConnection huc=page.openConnection();
			//System.out.println(huc.getHeaderFields());
			BufferedReader d = new BufferedReader(new InputStreamReader(huc.getInputStream()));

			for(int i=0;i<5;i++){
				d.readLine();
			}
			
			if(!d.readLine().equals("</style>")){

				String tmp= d.readLine();
				if(tmp.equals("<tr><td colspan=1><b>Privacy mode enabled for this user</b></td></tr>")){
					privacy=true;
					return PRIVACYON;
				}
				System.out.println(tmp);
				Physique=trim(tmp.substring(26));
				tmp= d.readLine();
				Coordination=trim(tmp.substring(30));
				tmp= d.readLine();
				Reasoning=trim(tmp.substring(27));
				tmp= d.readLine();
				Will=trim(tmp.substring(22));
				tmp= d.readLine();
				Instinct=trim(tmp.substring(26));
				tmp= d.readLine();
				Vitality=trim(tmp.substring(26));
				tmp= d.readLine();//gap
				tmp= d.readLine();
				Might=trim(tmp.substring(23));
				tmp= d.readLine();
				Matter=trim(tmp.substring(24));
				tmp= d.readLine();
				Toughness=trim(tmp.substring(27));
				tmp= d.readLine();
				Charm=trim(tmp.substring(23));
				tmp= d.readLine();
				Reaction=trim(tmp.substring(26));
				tmp= d.readLine();
				Rationality=trim(tmp.substring(29));
				tmp= d.readLine();
				Dexterity=trim(tmp.substring(27));
				tmp= d.readLine();
				Ethereality=trim(tmp.substring(29));
				tmp= d.readLine();//skip
				tmp= d.readLine();
				Overall=trim(tmp.substring(25));
				tmp= d.readLine();
				Attack=trim(tmp.substring(24));
				tmp= d.readLine();
				Defense=trim(tmp.substring(25));
				tmp= d.readLine();
				Magic=trim(tmp.substring(23));
				
				tmp= d.readLine();
				Harvest=trim(tmp.substring(25));
				tmp= d.readLine();
				Manufacture=trim(tmp.substring(29));
				tmp= d.readLine();
				Alchemy=trim(tmp.substring(25));
				tmp= d.readLine();
				Potion=trim(tmp.substring(24));
				tmp= d.readLine();
				Summoning=trim(tmp.substring(27));
				tmp= d.readLine();
				Crafting=trim(tmp.substring(26));
				
				//System.out.println("|"+tmp+"|");
				
				d.close();
				
				//System.out.println(dump());
				lastUpdate=new Date();
				updated=true;
				return PARSEDOK;
			}
			return PLAYERDOESNOTEXIST;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("A malformed URL Ecxception ocured :"+e.getMessage());
			System.err.println("The likely cause of this is that the page name has changed");
			return PAGENOTFOUND;
		}
			catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
				System.err.println("An IO exception ocured:"+e1.getMessage());
				System.err.println("This means that there was an error retrieveing the web page");	
			return IOEXCEPTION;
		}
	}
	
	/*public synchronized int update(){
		if(lastUpdate==null){
			return doUpdate();
		}

		else if(new Date().getTime()-lastUpdate.getTime()>30000){//wait 5 seconds between updates
			//System.out.println("difference: "+new Long(new Date().getTime()-lastUpdate.getTime()));
			//System.out.println("Updateing");
			return doUpdate();
		}
		else{
			//System.out.println("difference: "+new Long(new Date().getTime()-lastUpdate.getTime()));
			//System.out.println("didn't update, too little time since last time");
			return true;
		}

	}*/
		
	private int parseIt(String tmp) throws NumberFormatException{
		String str;
		if(tmp.length()==3){
			str = tmp.substring(2,3);
			return Integer.parseInt(str);
		}
		else if(tmp.length()==5)
		{
			str = tmp.substring(3,5);
			return Integer.parseInt(str);
		}
		else if(tmp.length()==7){
			str = tmp.substring(4,7);
			return Integer.parseInt(str);
		}
		if(tmp.length()==4){
			str = tmp.substring(2,4);
			return Integer.parseInt(str);
		}
		else if(tmp.length()==6){//same for 99/100
			str = tmp.substring(3,6);
			return Integer.parseInt(str);
		}
		else{
			return 0;
		}//could be an uneven number eg 9/10 (haven't used pp's)
	}
	public String dump(){
		String tmp="Name: "+name+"\n";
		tmp=tmp+"Main Attributes\n";
		tmp=tmp+"Physique: "+Physique+"\n";
		tmp=tmp+"Coordination: "+Coordination+"\n";
		tmp=tmp+"Reasoning: "+Reasoning+"\n";
		tmp=tmp+"Will: "+Will+"\n";
		tmp=tmp+"Instinct: "+Instinct+"\n";
		tmp=tmp+"Vitality: "+Vitality+"\n";
		tmp=tmp+"Cross Attributes\n";
		tmp=tmp+"Might: "+Might+"\n";
		tmp=tmp+"Matter: "+Matter+"\n";
		tmp=tmp+"Toughness: "+Toughness+"\n";
		tmp=tmp+"Charm: "+Charm+"\n";
		tmp=tmp+"Reaction: "+Reaction+"\n";
		tmp=tmp+"Rationality: "+Rationality+"\n";
		tmp=tmp+"Dexterity: "+Dexterity+"\n";
		tmp=tmp+"Ethereality: "+Ethereality+"\n";
		tmp=tmp+"Skills\n";
		tmp=tmp+"Attack: "+Attack+"\n";
		tmp=tmp+"Defense: "+Defense+"\n";
		tmp=tmp+"Magic: "+Magic+"\n";
		tmp=tmp+"Harvest: "+Harvest+"\n";
		tmp=tmp+"Manufacture: "+Manufacture+"\n";
		tmp=tmp+"Alchemy: "+Alchemy+"\n";
		tmp=tmp+"Potion: "+Potion+"\n";
		tmp=tmp+"Summoning: "+Summoning+"\n";
		tmp=tmp+"Crafting: "+Crafting+"\n";
		tmp=tmp+"Overall: "+Overall+"\n";		
		return tmp;
	}

	
	String trim(String t){
		for(int i=2;i<t.length()-1;i++){
			if(t.substring(i,i+1).equals("<")){
				return t.substring(0,i);
			}
		}
		return null;
	}
	/**
	 * converts physique to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the physique as an int
	 */
	public int  getPhysique()throws NumberFormatException{
		return parseIt(Physique);
	}
	/**
	 * converts Coordination to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Coordination as an int
	 */
	public int  getCoordination() throws NumberFormatException{
		return parseIt(Coordination);
	}
	/**
	 * converts Reasoning to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Reasoning as an int
	 */
	public int  getReasoning() throws NumberFormatException{
		return parseIt(Reasoning);
	}
	/**
	 * converts Will to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Will as an int
	 */
	public int  getWill() throws NumberFormatException{
		return parseIt(Will);
	}
	/**
	 * converts Instinct to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Instinct as an int
	 */
	public int  getInstinct() throws NumberFormatException{
		return parseIt(Instinct);
	}
	/**
	 * converts Vitality to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Vitality as an int
	 */
	public int  getVitality() throws NumberFormatException{
		return parseIt(Vitality);
	}
	
	
	/**
	 * converts Might to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Might as an int
	 */
	public int  getMight() throws NumberFormatException{
		return parseIt(Might);
	}
	/**
	 * converts Matter to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Matter as an int
	 */
	public int  getMatter() throws NumberFormatException{
		return parseIt(Matter);
	}
	/**
	 * converts Matter to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the ToughnessToughness as an int
	 */
	public int  getToughness() throws NumberFormatException{
		return parseIt(Toughness);
	}
	/**
	 * converts Charm to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Charm as an int
	 */
	public int  getCharm() throws NumberFormatException{
		return parseIt(Charm);
	}
	/**
	 * converts Reaction to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Reaction as an int
	 */
	public int  getReaction() throws NumberFormatException{
		return parseIt(Reaction);
	}
	/**
	 * converts Rationality to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Rationality as an int
	 */
	public int  getRationality() throws NumberFormatException{
		return parseIt(Rationality);
	}
	/**
	 * converts Dexterity to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Dexterity as an int
	 */
	public int  getDexterity() throws NumberFormatException{
		return parseIt(Rationality);
	}
	/**
	 * converts Ethereality to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Ethereality as an int
	 */
	public int  getEthereality() throws NumberFormatException{
		return parseIt(Ethereality);
	}

	
	/**
	 * converts Overall to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Overall as an int
	 */
	public int  getOverall() throws NumberFormatException{
		return parseIt(Overall);
	}
	/**
	 * converts Attack to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Attack as an int
	 */
	public int  getAttack() throws NumberFormatException{
		return parseIt(Attack);
	}
	/**
	 * converts Defense to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Defense as an int
	 */
	public int  getDefense() throws NumberFormatException{
		return parseIt(Defense);
	}
	/**
	 * converts Magic to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Magic as an int
	 */
	public int  getMagic() throws NumberFormatException{
		return parseIt(Magic);
	}
	/**
	 * converts Harvest to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
	 * @return  returns the Harvest as an int
	 */
	public int  getHarvest() throws NumberFormatException{
		return parseIt(Harvest);
	}
	/**
	 * converts Manufacture to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
 	 * @return  returns the Manufacture as an int
	 */
	public int  getManufacture() throws NumberFormatException{ 
		return parseIt(Manufacture);
	}	
	/**
	 * converts Alchemy to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
 	 * @return  returns the Alchemy as an int
	 */
	public int  getAlchemy() throws NumberFormatException{ 
		return parseIt(Alchemy);
	}	
	/**
	 * converts Potion to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
 	 * @return  returns the Potion as an int
	 */
	public int  getPotion() throws NumberFormatException{ 
		return parseIt(Potion);
	}		
	/**
	 * converts Summoning to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
 	 * @return  returns the Summoning as an int
	 */
	public int  getSummoning() throws NumberFormatException{ 
		return parseIt(Summoning);
	}
	/**
	 * converts Crafting to an integer and returns
	 * 
	 * @throws NumberFormatException  if for some reson the string isn't in the right format it may fail and throw an exception (shouldn't happen unless they change the format)
 	 * @return  returns the Crafting as an int
	 */
	public int  getCrafting() throws NumberFormatException{ 
		return parseIt(Crafting);
	}
	public int getPickPoints() throws NumberFormatException{
		if(Overall.length()==3){
			//System.out.println(Overall.substring(0,1)+"|"+Overall.substring(2,2));
			return (Integer.parseInt(Overall.substring(2,3)))-(Integer.parseInt(Overall.substring(0,1)));		
		}
		else if(Overall.length()==5){
			//System.out.println(Overall.substring(0,2)+"|"+Overall.substring(2,2));
			return (Integer.parseInt(Overall.substring(3,5)))-(Integer.parseInt(Overall.substring(0,2)));
		}
		else if(Overall.length()==7){
			return (Integer.parseInt(Overall.substring(4,7))) -(Integer.parseInt(Overall.substring(0,3)));
		}
		
		
		return 0;
	}
	/**
	 * calculates a combat level
	 * 
	 * @throws NumberFormatException  if the depending get's fail then this will
	 * 	  @return  the combat level as a string
	 */
	public double getCombatLevel() throws NumberFormatException{
		return 	(getReaction()+getToughness()+getDexterity()+(getAttack()*.75)+(getDefense()*.75)+(getMatter()*.50));
	}
	
	public boolean updated(){
		return updated;
	}
	/**
	 * this calculates the carry capacity and returns the result
	 * 
	 * @return  carry the carry capacity 
	 */
	public int getCarry(){
		return getMight()*20;
	}
	/**
	 * this calculates the maximum helth for the player and returns the result
	 * 
	 * @return  helth the maximum helth
	 */
	public int getHelth(){
		return getMatter()*5;
	}
	/**
	 * this calculates the maximum mana for the player and returns the result
	 *  
	 * @return  mana the calculated maximum mana
	 */
	public int getMana(){
		return getEthereality()*8;
	}
	/**
	 * @return Returns the privacy.
	 */
	public boolean hasPrivacy() {
		return privacy;
	}
}
