
package elc;

public class Item {
	int imageID;
	int position;
	int quantity;
	int isReagent;
	int isResource;
	int useWithInventory;

	

    
    public Item(int imageID, int quantity, int pos, int flags){
    	this.imageID=imageID;
    	this.quantity=quantity;
    	this.position=pos;
    }

    
    public String toString(){
    	return "Item(imageID:"+imageID+";quantity:"+quantity+";pos:"+position;
    }
	/**
	 * @return Returns the isReagent.
	 */
	public int getIsReagent() {
		return isReagent;
	}
	/**
	 * @param isReagent The isReagent to set.
	 */
	public void setIsReagent(int isReagent) {
		this.isReagent = isReagent;
	}
	/**
	 * @return Returns the isResource.
	 */
	public int getIsResource() {
		return isResource;
	}
	/**
	 * @param isResource The isResource to set.
	 */
	public void setIsResource(int isResource) {
		this.isResource = isResource;
	}
	/**
	 * @return Returns the position.
	 */
	public int getPosition() {
		return position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	/**
	 * @return Returns the quantity.
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return Returns the useWithInventory.
	 */
	public int getUseWithInventory() {
		return useWithInventory;
	}
	/**
	 * @param useWithInventory The useWithInventory to set.
	 */
	public void setUseWithInventory(int useWithInventory) {
		this.useWithInventory = useWithInventory;
	}
	/**
	 * @return Returns the imageID.
	 */
	public int getImageID() {
		return imageID;
	}
	/**
	 * @param imageID The imageID to set.
	 */
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
}

