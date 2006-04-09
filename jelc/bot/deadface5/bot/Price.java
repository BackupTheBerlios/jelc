/*
 * Created on 30-Jan-06
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bot.deadface5.bot;

import java.util.Comparator;

public class Price {
	private int  bot;
	private int productID;
	private int price;
	
	public Price(int bot, int productID, int price){
		this.setBot(bot);
		this.setProductID(productID);
		this.setPrice(price);
	}
	public boolean equals(Object o){
		if(o instanceof Price){
			Price tmp=(Price)o;
			return ((bot==tmp.getPrice())&&(productID==tmp.getProductID()));
		}
		return false;
	}
	/**
	 * @param bot The bot to set.
	 */
	public void setBot(int bot) {
		this.bot = bot;
	}
	/**
	 * @return Returns the bot.
	 */
	public int getBot() {
		return bot;
	}
	/**
	 * @param productID The productID to set.
	 */
	public void setProductID(int productID) {
		this.productID = productID;
	}
	/**
	 * @return Returns the productID.
	 */
	public int getProductID() {
		return productID;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	/**
	 * @return Returns the price.
	 */
	public int getPrice() {
		return price;
	}
	 
}
