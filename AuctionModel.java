import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;

public class AuctionModel
{
	public boolean isHighest, isCHighest, bidValid, isOutBid, isTied, isCUser;
	AuctionModelItems amit = new AuctionModelItems();
	ArrayList<AuctionModelItems> itemList = new ArrayList<>();
	private double  serUp,monUp, birUp;
	public AuctionModel()
	{
    	itemList = readFile();
	}

	public void setUpdCB(String name, double up)
	{
    	if(name.equals("Serval"))
        	serUp = up;
    	else if (name.equals("Squirel Monkey"))
        	monUp = up;
    	else if (name.equals("Hyacinth Macaw"))
        	birUp = up;
	}
    
	public double getUpdCB(String name)
	{
    	if(name.equals("Serval"))
        	return serUp;
    	else if (name.equals("Squirel Monkey"))
        	return monUp;
    	else if (name.equals("Hyacinth Macaw"))
        	return birUp;
    	else
        	return 0;
	}
    
	public ArrayList<AuctionModelItems> readFile()
	{
    	try
    	{
        	Scanner file = new Scanner(new File("items.txt"));

        	while(file.hasNext())
        	{
            	String s = file.nextLine();
            	Scanner parse = new Scanner(s);
            	parse.useDelimiter(",");
            	String iN = parse.next();
            	String iD = parse.next();
            	String cB = parse.next();
            	double crB= parse.nextDouble();
            	double mB = parse.nextDouble();
           	 
            	AuctionModelItems amiT = new AuctionModelItems(iN,iD,cB,crB,mB);
            	itemList.add(amiT);
        	}
        	file.close();
    	}
    	catch (FileNotFoundException fnfe)
    	{
        	System.out.println("no file");
    	}
    	return itemList;
	}
    
	private void set()
	{
    	bidValid = true;
    	isCHighest = false;
    	isHighest = false;
    	isOutBid = false;
    	isTied = false;
	}
    
	public double minInc(String name)
	{
    	if(name.equals("Serval"))
        	return 100;
    	else if (name.equals("Squirel Monkey"))
        	return 500;
    	else if (name.equals("Hyacinth Macaw"))
        	return 1000;
    	else
        	return 0;
	}
    
	public void maxBid(String itemName, String bidder, double amount)
	{
    	set();
    	try
    	{
    	File originalFile = new File("items.txt");
    	BufferedReader br = new BufferedReader(new FileReader(originalFile));
   	 
    	File tempFile = new File("tempfile.txt");
    	PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
   	 
    	String line = "";
   	 
    	while((line=br.readLine())!= null)
    	{
        	if (line.contains(itemName) && line.contains(bidder))
            	isCHighest = true;
        	else if (line.contains(itemName) && !line.contains(bidder))
        	{
            	Scanner parse = new Scanner (line);
            	parse.useDelimiter(",");
            	String iName = parse.next();
            	String iDesc = parse.next();
            	String cBidder = parse.next();
            	double cBid = parse.nextDouble();
            	double mBid = parse.nextDouble();
           	 
            	if(mBid > cBid && mBid == amount)
            	{
                	cBid = mBid;
                	isTied = true;
            	}
            	else if (mBid >= cBid && mBid + minInc(iName) <= amount)
            	{
                	cBid = mBid + minInc(iName);
                	mBid = amount;
                	cBidder = bidder;
                	isHighest = true;
            	}
            	else if(mBid > amount && amount >= cBid + minInc(iName))
            	{
                	cBid = amount + minInc(iName);
                	isOutBid = true;
            	}
            	else if(mBid < cBid && amount >= cBid + minInc(iName))
            	{
                	cBid += minInc(iName);
                	mBid = amount;
                	cBidder = bidder;
                	isHighest = true;
            	}
            	else
                	bidValid = false;
            	line = iName + "," + iDesc + "," + cBidder + "," + cBid + "," + mBid;
            	setUpdCB(iName,cBid);
        	}
       	 
        	pw.println(line);
        	pw.flush();
    	}
    	pw.close();
    	br.close();
   	 
    	if(!originalFile.delete())
        	System.out.println("Could not delete file");
    	if(!tempFile.renameTo(originalFile))
        	System.out.println("Could not rename file");

    	}
    	catch(FileNotFoundException fnfe)
    	{
        	System.out.println("Unable to find files");
    	}
    	catch(IOException io)
    	{
        	System.out.println("Unable to find files");
    	}
	}
    
	public void placeBid(String itemName, String bidder, double amount)
	{
    	set();
    	try
    	{
    	File originalFile = new File("items.txt");
    	BufferedReader br = new BufferedReader(new FileReader(originalFile));
   	 
    	File tempFile = new File("tempfile.txt");
    	PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
   	 
    	String line = "";
   	 
    	while((line=br.readLine())!= null)
    	{
        	if (line.contains(itemName) && line.contains(bidder))
            	isCHighest = true;
        	else if (line.contains(itemName) && !line.contains(bidder))
        	{
            	Scanner parse = new Scanner (line);
            	parse.useDelimiter(",");
            	String iName = parse.next();
            	String iDesc = parse.next();
            	String cBidder = parse.next();
            	double cBid = parse.nextDouble();
            	double mBid = parse.nextDouble();
            	if(mBid > cBid && mBid == amount)
            	{
                	cBid = mBid;
                	isTied = true;
            	}
            	else if ((mBid >= cBid && mBid +minInc(iName) <= amount) || (mBid < cBid && amount >= cBid + minInc(iName)) )
            	{
                	cBid = amount;
                	mBid = amount;
                	cBidder = bidder;
                	isHighest = true;
            	}
            	else if(mBid > amount && amount >= cBid + minInc(iName))
            	{
                	cBid = amount + minInc(iName);
                	isOutBid = true;
            	}
            	else
                	bidValid = false;
            	line = iName + "," + iDesc + "," + cBidder + "," + cBid + "," + mBid;
            	setUpdCB(iName,cBid);
        	}
       	 
        	pw.println(line);
        	pw.flush();
    	}
    	pw.close();
    	br.close();
   	 
    	if(!originalFile.delete())
        	System.out.println("Could not delete file");
    	if(!tempFile.renameTo(originalFile))
        	System.out.println("Could not rename file");

    	}
    	catch(FileNotFoundException fnfe)
    	{
        	System.out.println("Unable to find files");
    	}
    	catch(IOException io)
    	{
        	System.out.println("Unable to find files");
    	}
	}
    
	public void minBid(String itemName, String bidder)
	{
    	set();
    	try
    	{
    	File originalFile = new File("items.txt");
    	BufferedReader br = new BufferedReader(new FileReader(originalFile));
   	 
    	File tempFile = new File("tempfile.txt");
    	PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
   	 
    	String line = "";
   	 
    	while((line=br.readLine())!= null)
    	{
        	if (line.contains(itemName) && line.contains(bidder))
            	isCHighest = true;
        	else if (line.contains(itemName) && !line.contains(bidder))
        	{
            	Scanner parse = new Scanner (line);
            	parse.useDelimiter(",");
            	String iName = parse.next();
            	String iDesc = parse.next();
            	String cBidder = parse.next();
            	double cBid = parse.nextDouble();
            	double mBid = parse.nextDouble();
            	cBid += minInc(iName);
            	if (cBid > mBid)
            	{
                	mBid = cBid;
                	cBidder = bidder;
                	isHighest = true;
            	}
            	else if (cBid == mBid)
            	{
                	isTied = true;
            	}
            	else
            	{
                	isOutBid = true;
            	}
            	line = iName + "," + iDesc + "," + cBidder + "," + cBid +"," + mBid;
            	setUpdCB(iName,cBid);
        	}
       	 
        	pw.println(line);
        	pw.flush();
    	}
    	pw.close();
    	br.close();
   	 
    	if(!originalFile.delete())
        	System.out.println("Could not delete file");
    	if(!tempFile.renameTo(originalFile))
        	System.out.println("Could not rename file");

    	}
    	catch(FileNotFoundException fnfe)
    	{
        	System.out.println("Unable to find files");
    	}
    	catch(IOException io)
    	{
        	System.out.println("Unable to find files");
    	}
	}
    
	public void login(String name)
	{
    	try
    	{
        	System.out.println("inside");
        	Scanner file = new Scanner(new File("users.txt"));

        	while(file.hasNext())
        	{
            	String s = file.nextLine();
            	 
            	if (s.equals(name))
                	isCUser = true;
        	}
        	if (!isCUser)
        	{
            	FileOutputStream fos = new FileOutputStream("users.txt",true);
            	PrintWriter pw = new PrintWriter(fos);
            	pw.println(name);
            	pw.close();
        	}
        	file.close();
    	}
    	catch (FileNotFoundException fnfe)
    	{
        	System.out.println("no file");
    	}
	}
    
	public void logout()
	{
    	isCUser = false;
	}
}

