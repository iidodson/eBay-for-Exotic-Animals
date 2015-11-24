public class AuctionModelItems
{
	private String userName, itemName, description;
	private double cBid, minBid,maxBid;
    
	public AuctionModelItems()
	{
   	 
	}
   	 
	public AuctionModelItems(String startItemName, String startDescription, String startUserName, double startCBid, double startMaxBid)
	{
    	setItemName(startItemName);
    	setDescription(startDescription);
    	setUserName(startUserName);
    	setCBid(startCBid);
    	setMaxBid(startMaxBid);
	}
    
	public String toString()
	{
    	return getItemName() + "," + getDescription() + "," + getUserName() + "," + getCBid() + "," + getMaxBid();
	}
    
	public String getUserName()
	{
    	return userName;
	}
    
	public String getDescription()
	{
    	return description;
	}
    
	public String getItemName()
	{
    	return itemName;
	}
    
	public double getCBid()
	{
    	return cBid;
	}
    
	public double getMinBid()
	{
    	return minBid;
	}
    
	public double getMaxBid()
	{
    	return maxBid;
	}
    
	public void setUserName(String newUserName)
	{
    	userName = newUserName;
	}
    
	public void setItemName(String newItemName)
	{
    	itemName = newItemName;
	}
    
	public void setDescription(String newDescription)
	{
    	description = newDescription;
	}
    
	public void setCBid(double newCBid)
	{
    	cBid = newCBid;
	}
    
	public void setMinBid(double newMinBid)
	{
    	minBid = newMinBid;
	}
    
	public void setMaxBid(double newMaxBid)
	{
    	maxBid = newMaxBid;
	}

}
