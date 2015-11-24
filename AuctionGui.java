import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AuctionGui extends JFrame{
  	private Container contents;
  	private JLabel user, empty1, empty2;
  	private JTextField username;
  	private JButton login;
  	private JPanel textPanel, loginPanel;
  	private static String title = "Exotic Animal Auction";
  	private boolean frame2Created;
  	public String userN;
 	 
  	private AuctionModel am = new AuctionModel();
  	private AuctionModelItems ami = new AuctionModelItems();
  	String bd;
 	 
  	public AuctionGui(){
     	 
      	super(title);
      	contents = getContentPane( );
     	 
      	//set border layout for the entire frame
      	BorderLayout bl = new BorderLayout(); //non-anonymous object
      	contents.setLayout( bl );
     	 
      	//instantiate all components
      	user = new JLabel("Username:");
      	username = new JTextField("", 12 );
      	login = new JButton("Login");
      	empty1 = new JLabel("");
      	empty2 = new JLabel("");
     	 
      	//create a panel with new layout for user and username  
      	textPanel = new JPanel( );
      	textPanel.add( user );
      	textPanel.add( username );
     	 
      	//create a panel for the login button
      	loginPanel = new JPanel( );
      	loginPanel.setLayout( new GridLayout(1, 3) ); //anonymous object
      	loginPanel.add( empty1 );
      	loginPanel.add( login );
      	loginPanel.add( empty2 );
     	 
      	ButtonHandler bh = new ButtonHandler();
      	login.addActionListener(bh);
     	 
      	//add both panels to the entire frame layout
      	contents.add( BorderLayout.NORTH, textPanel );
      	contents.add( BorderLayout.SOUTH, loginPanel );
     	 
      	setVisible( true );

  	}
 	 
  	public void setFrame2Created( boolean b )
  	{
    	frame2Created = b;
  	}
 	 
  	public void updateView(){
      	ami.setUserName(username.getText());
  	}
 	 
  	public void updateUser(){
        	userN = username.getText();
    	}
 	 
  	public String getUserN()
    	{
        	return userN;
    	}
 	 
  	public void setUserN(String newUserN)
    	{
        	userN = newUserN;
    	}
 	 
  	private class ButtonHandler implements ActionListener //Login
  	{
    	public void actionPerformed( ActionEvent ae )
     	{
       	if( frame2Created == false && !(username.getText().trim().equals("")))
       	{
        	 
         	am.login(username.getText());
         	setUserN(username.getText());
        	 
         	AuctionGui2 f2 = new AuctionGui2( );
         	f2.setF1( AuctionGui.this );
         	f2.setTitle("Welcome " + getUserN() + "!" );
         	f2.updateView();
         	frame2Created = true;
       	}
       	else{
           	System.err.println("Null");
       	}
     	}
  	}
	}







