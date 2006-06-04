/*
 * Created on 27-Jan-06
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface6.bot;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class BotPriceList {
	List prices;
	BotPriceList(){
		proces("Quartermaster","(5500 gc) BODY PIERCING CLOAK (I have 1)");
		prices=new Vector();
	}

	public void proces(String name, String message){
		if("Quartermaster".equals(name)){
			
			if(message.startsWith("(")){//should be an item in the inventory
				int costindex=message.indexOf(" gc");
				if(costindex!=-1){
					String costString=message.substring(1,costindex);
					costString=costString.trim();
					int nameIndex=message.indexOf(" (",costindex);
					if(nameIndex!=-1){
						String product=(message.substring(costindex+5,nameIndex)).toLowerCase();
						
						processItem(name,costString,product);
					}
					
					
				}
			}
		}
	}
	
	public void processItem(String bot, String cost,String name){
		System.out.println("|"+bot+"|"+cost+"|"+name+"|");
		int priceCost=Integer.parseInt(cost);
		
		Price newPrice=new Price(0,getID(name),priceCost);
		for(Iterator itr=prices.iterator();itr.hasNext();){
			Price tmp=(Price)itr.next();
			if(newPrice.equals(tmp)){
				
			}
		}
	}
	public int getID(String product){
		return 0;
	}
	public static void main(String[] args){
		new BotPriceList();
	}
}
