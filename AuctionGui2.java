

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.*;

public class AuctionGui2 extends JFrame{
    
	private Container contents;
	private JLabel curent_bid, picker, image, empty1, empty2, cbid, status;
	private JTextField mybid, description;
	private JTextArea desc;
	private JButton place_your_bid, max, min, logout;
	private ImageIcon icon;
	private JComboBox items;
	private JPanel bidPanel1, bidPanel2, pickerPanel, logoutPanel1, logoutPanel2, 	buttonPanel,
    	bidPanel3, bidPanel4;
    
	AuctionGui f1;
	String bid;
	private  static AuctionModel am = new AuctionModel();
    
	private String bd = "0.00";
	ArrayList<AuctionModelItems> itemArray = am.readFile();
	String[] options = { itemArray.get(0).getItemName(), itemArray.get(1).getItemName(), itemArray.get(2).getItemName()}; //Combo box items;
    
	public AuctionGui2(){
   	 
    	contents = getContentPane( );
   	 
    	//set border layout for the entire frame
    	contents.setLayout( new BorderLayout() );
   	 
    	//instantiate all components
    	place_your_bid = new JButton("Place Your Bid");
    	ButtonHandler bh = new ButtonHandler();
    	place_your_bid.addActionListener(bh);
   	 
    	min = new JButton("Min Bid");
    	min.addActionListener(bh);
   	 
    	max = new JButton("Max Bid");
    	max.addActionListener(bh);
   	 
    	logout = new JButton("Logout");
    	logout.addActionListener(bh);
   	 
    	empty1 = new JLabel("");
    	empty2 = new JLabel("");
    	curent_bid = new JLabel("Current Bid:");
    	cbid = new JLabel(""+itemArray.get(0).getCBid());
    	status = new JLabel("");
    	picker = new JLabel("Pick:");
    	icon = new ImageIcon("Serval.png");
    	image = new JLabel(icon);
    	image.setPreferredSize(new Dimension(177, 122+10));
   	 
    	mybid = new JTextField("", 7 );
   	 
    	description = new JTextField("", 4);
    	desc = new JTextArea(itemArray.get(0).getDescription());
    	desc.setEditable(false);
   	 
    	//create a panel with variables for bidding
    	bidPanel1 = new JPanel();
    	bidPanel1.setLayout( new GridLayout(2, 2, 10, 0) );
    	bidPanel1.add( curent_bid );
    	bidPanel1.add( cbid );
    	bidPanel1.add( mybid );
    	bidPanel1.add( status );

    	//create a panel with all bidding buttons
    	buttonPanel = new JPanel( );
    	buttonPanel.setLayout( new GridLayout(1, 3) );
    	buttonPanel.add(place_your_bid);
    	buttonPanel.add(min);
    	buttonPanel.add(max);
   	 
    	//create a panel to combine the bidding panel and the button bidding panel
    	bidPanel2 = new JPanel( );
    	bidPanel2.setLayout( new BorderLayout() );
    	bidPanel2.add( BorderLayout.NORTH, bidPanel1);
    	bidPanel2.add( BorderLayout.SOUTH, buttonPanel );
   	 
    	//create a panel to contain all bidding components and an image
    	bidPanel3 = new JPanel( );
    	bidPanel3.setLayout( new BorderLayout());
    	bidPanel3.add( BorderLayout.WEST, image );
    	bidPanel3.add( BorderLayout.EAST, bidPanel2 );
   	 
    	//create a panel for the logout button
    	logoutPanel1 = new JPanel( );
    	GridLayout gl3 = new GridLayout(1, 3);
    	logoutPanel1.setLayout( gl3 );
    	logoutPanel1.add(empty1); //empty string JLabels used for spacing purposes
    	logoutPanel1.add(empty2);
    	logoutPanel1.add(logout);
   	 
    	//create a panel for the logout button and description
    	logoutPanel2 = new JPanel( );
    	logoutPanel2.setLayout( new BorderLayout() );
    	logoutPanel2.add( BorderLayout.NORTH, desc );
    	logoutPanel2.add( BorderLayout.SOUTH, logoutPanel1 );
   	 
    	//create a panel to combine all the bidding components with the logout button and decription
    	bidPanel4 = new JPanel( );
    	bidPanel4.setLayout( new BorderLayout() );
    	bidPanel4.add( BorderLayout.NORTH, bidPanel3 );
    	bidPanel4.add( BorderLayout.SOUTH, logoutPanel2 );

    	items = new JComboBox(options);
    	ComboBox cb = new ComboBox();
   	items.addItemListener(cb);
    	items.setSelectedIndex(0);
   	 
    	//create a panel for the combo box with a JLabel
    	pickerPanel = new JPanel( );
    	pickerPanel.add(picker);
    	pickerPanel.add(items);
   	 
    	//add combo box and panel and the combination of the bidding and description panel to the contentPane
    	contents.add( BorderLayout.WEST, pickerPanel );
    	contents.add( BorderLayout.CENTER, bidPanel4 );
   	 
    	setSize( 800, 220 );
    	setVisible( true );
	}
    
	public void updateUser(){
	}
    
	public void setF1( AuctionGui newF1 )
 	{
   	f1 = newF1;
 	}
    
	public void updateView(){
    	bid = mybid.getText();
	}
    
 	public void showMessage(String itemName)
	{
    	if(am.isCHighest)
    	JOptionPane.showMessageDialog(null, "Your are the current high bidder for this item, you may not outbid yourself ","Error", JOptionPane.ERROR_MESSAGE);
    	else if(am.isHighest)
    	JOptionPane.showMessageDialog(null, "Your are now the current high bidder for this item ");
    	else if(am.isOutBid)
    	JOptionPane.showMessageDialog(null, "You bid of " + bid +" was outbid ");
    	else if(am.isTied)
    	JOptionPane.showMessageDialog(null, "You tied the max bid, you are not the high bidder ");
    	else if(!am.bidValid)
    	JOptionPane.showMessageDialog(null, "Your bid must be at least " + am.minInc(itemName)+"higher than the current bid ");
	}
    
	private class ButtonHandler implements ActionListener
 	{
   	public void actionPerformed( ActionEvent ae )
    	{
      	JButton buttonClicked = ( JButton ) ae.getSource( );
      	if (buttonClicked == place_your_bid)
      	{
      	String item = "" + itemArray.get(items.getSelectedIndex()).getItemName();
      	String userName =f1.userN;
      	double bid = Double.valueOf(mybid.getText());
      	am.placeBid(item, userName, bid);
      	showMessage(item);
     	 
      	cbid.setText(am.getUpdCB(item) + "");
      	}
      	else if(buttonClicked == min)
      	{
      	String item = "" + itemArray.get(items.getSelectedIndex()).getItemName();
      	String userName =f1.userN;
          	am.minBid(item, userName);
          	showMessage(item);
         	 
        	cbid.setText(am.getUpdCB(item) + "");      	}
      	else if(buttonClicked == max)
      	{
      	String item = "" + itemArray.get(items.getSelectedIndex()).getItemName();
      	String userName =f1.userN;
      	double bid = Double.parseDouble(mybid.getText());
          	am.maxBid(item, userName, bid);
          	showMessage(item);
          	cbid.setText(am.getUpdCB(item) + "");
      	}
      	else if(buttonClicked == logout)
      	{
      	f1.setFrame2Created( false );
      	am.logout();
     	f1.updateView();
      	dispose( );
      	}
    	}
 	}
    
  	private class ComboBox implements ItemListener{
    	public void itemStateChanged( ItemEvent ie){
     	 
        	int cmbType = items.getSelectedIndex();
        	//String s = cmbType;
        	//updateLabel(options);
       	 
        	if(cmbType == 0){
            	mybid.setText("");
            	desc.setText(itemArray.get(0).getDescription());
            	icon = new ImageIcon(itemArray.get(0).getItemName() + ".png");
            	image.setIcon(icon);
            	image.setToolTipText(itemArray.get(0).getItemName());
            	if(am.getUpdCB(itemArray.get(0).getItemName())== 0)
            	cbid.setText(itemArray.get(0).getCBid() + "");  
            	else
            	cbid.setText(am.getUpdCB(itemArray.get(0).getItemName()) + "");
        	}
        	else if(cmbType == 1){
            	mybid.setText("");
            	desc.setText(itemArray.get(1).getDescription());
            	icon = new ImageIcon(itemArray.get(1).getItemName() + ".png");
            	image.setIcon(icon);
            	image.setToolTipText(itemArray.get(1).getItemName());
            	if(am.getUpdCB(itemArray.get(1).getItemName())== 0)
            	cbid.setText(itemArray.get(1).getCBid() + "");  
            	else
            	cbid.setText(am.getUpdCB(itemArray.get(1).getItemName()) + "");
        	}
       	 
        	else if(cmbType == 2){
            	mybid.setText("");
            	desc.setText(itemArray.get(2).getDescription());
            	icon = new ImageIcon(itemArray.get(2).getItemName() + ".png");
            	image.setIcon(icon);
            	image.setToolTipText(itemArray.get(2).getItemName());
            	if(am.getUpdCB(itemArray.get(2).getItemName())== 0)
            	cbid.setText(itemArray.get(2).getCBid() + "");  
            	else
            	cbid.setText(am.getUpdCB(itemArray.get(2).getItemName()) + "");   
        	}
       	 
    	}
	}

  	}
