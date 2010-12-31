package client.gui.searchuser;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import client.core.AUser;
import client.core.CLibraryManager;
import client.gui.CustomLabel;

import common.data.CUser;

public class CSearchUserPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel jLabel_SUMainLabel = null;
	private JLabel jLabel_username = null;
	private JLabel jLabel_firstname = null;
	private JLabel jLabel_lastname = null;
	private JLabel jLabel_userID = null;
	private JTextField jTextField_UserName = null;
	private JTextField jTextField_UserID = null;
	private JTextField jTextField_FirstName = null;
	private JTextField jTextField_LastName = null;
	private JButton jButton_back = null;
	private JButton jButton_SearchUser = null;
	private SUDecision m_lastChoice = null;  //  @jve:decl-index=0:
	private static LinkedList<CUser> result = null;
	private JLabel jLabel_Prescription = null;
	
	
	public enum SUDecision{
		BACK, SEARCHUSER
	}

	public CSearchUserPanel() {
		super();
		initialize();
	}

	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel_Prescription = new CustomLabel();
		jLabel_Prescription.setBounds(new Rectangle(30, 140, 265, 32));
		jLabel_Prescription.setText("<html><u>Please Insert One Or More Fields :</u></html>");
		jLabel_Prescription.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		jLabel_userID = new JLabel();
		jLabel_userID.setText("ID (user)");
		jLabel_userID.setSize(new Dimension(160, 40));
		jLabel_userID.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		jLabel_userID.setLocation(new Point(30, 230));
		jLabel_lastname = new JLabel();
		jLabel_lastname.setText("Last Name");
		jLabel_lastname.setSize(new Dimension(160, 40));
		jLabel_lastname.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		jLabel_lastname.setLocation(new Point(30, 330));
		jLabel_firstname = new JLabel();
		jLabel_firstname.setText("First Name");
		jLabel_firstname.setSize(new Dimension(160, 40));
		jLabel_firstname.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		jLabel_firstname.setLocation(new Point(30, 280));
		jLabel_username = new JLabel();
		jLabel_username.setText("User Name");
		jLabel_username.setSize(new Dimension(160, 40));
		jLabel_username.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		jLabel_username.setLocation(new Point(30, 180));
		jLabel_SUMainLabel = new JLabel();
		jLabel_SUMainLabel.setBounds(new Rectangle(1, 30, 698, 74));
		jLabel_SUMainLabel.setText("Search User");
		jLabel_SUMainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_SUMainLabel.setFont(new Font("Freestyle Script", Font.BOLD, 48));
		jLabel_SUMainLabel.setLocation(new Point(0, 15));
		jLabel_SUMainLabel.setSize(new Dimension(700, 75));
		jLabel_SUMainLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(jLabel_SUMainLabel, null);
		this.add(jLabel_username, null);
		this.add(jLabel_firstname, null);
		this.add(jLabel_lastname, null);
		this.add(jLabel_userID, null);
		this.add(getJTextField_UserName(), null);
		this.add(getJTextField_UserID(), null);
		this.add(getJTextField_FirstName(), null);
		this.add(getJTextField_LastName(), null);
		this.add(getJButton_back(), null);
		this.add(getJButton_SearchUser(), null);
		this.add(jLabel_Prescription, null);
	}


	/**
	 * @return the m_lastChoice
	 */
	public SUDecision getlastChoice() {
		return m_lastChoice;
	}


	/**
	 * @param m_lastChoice the m_lastChoice to set
	 */
	public void setlastChoice(SUDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}


	/**
	 * @return the result
	 */
	static public LinkedList<CUser> getResult() {
		return result;
	}


	/**
	 * This method initializes jTextField_UserName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_UserName() {
		if (jTextField_UserName == null) {
			jTextField_UserName = new JTextField();
			jTextField_UserName.setSize(new Dimension(400, 30));
			jTextField_UserName.setLocation(new Point(200, 185));
		}
		return jTextField_UserName;
	}


	/**
	 * This method initializes jTextField_UserID	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_UserID() {
		if (jTextField_UserID == null) {
			jTextField_UserID = new JTextField();
			jTextField_UserID.setLocation(new Point(200, 235));
			jTextField_UserID.setSize(new Dimension(400, 30));
		}
		return jTextField_UserID;
	}


	/**
	 * This method initializes jTextField_FirstName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_FirstName() {
		if (jTextField_FirstName == null) {
			jTextField_FirstName = new JTextField();
			jTextField_FirstName.setLocation(new Point(200, 285));
			jTextField_FirstName.setSize(new Dimension(400, 30));
		}
		return jTextField_FirstName;
	}


	/**
	 * This method initializes jTextField_LastName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_LastName() {
		if (jTextField_LastName == null) {
			jTextField_LastName = new JTextField();
			jTextField_LastName.setLocation(new Point(200, 335));
			jTextField_LastName.setSize(new Dimension(400, 30));
		}
		return jTextField_LastName;
	}


	/**
	 * This method initializes jButton_back	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_back() {
		if (jButton_back == null) {
			jButton_back = new JButton();
			jButton_back.setLocation(new Point(94, 480));
			jButton_back.setText("Back");
			jButton_back.setSize(new Dimension(208, 34));
			jButton_back.addActionListener(this);
		}
		return jButton_back;
	}


	/**
	 * This method initializes jButton_SearchUser	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_SearchUser() {
		if (jButton_SearchUser == null) {
			jButton_SearchUser = new JButton();
			jButton_SearchUser.setLocation(new Point(396, 480));
			jButton_SearchUser.setText("Search User");
			jButton_SearchUser.setSize(new Dimension(208, 34));
			jButton_SearchUser.addActionListener(this);
		}
		return jButton_SearchUser;
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == jButton_back)
		{
			this.setlastChoice(SUDecision.BACK);
			this.setVisible(false);
		}
		if(source == jButton_SearchUser)
		{
			try {
				result = ((CLibraryManager)AUser.getInstance()).searchUser(jTextField_UserName.getText(),jTextField_UserID.getText(),jTextField_FirstName.getText(),jTextField_LastName.getText());
				this.setlastChoice(SUDecision.SEARCHUSER);
				this.setVisible(false);
			    } catch (Exception e) {
				     JOptionPane.showMessageDialog(null, e.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
			    }
		}
		
	}
}
