package client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import client.core.AUser;
import client.core.EActor;
import java.awt.Point;

/**
 * CMainMenuPanel defines the panel of the Main Menu Screen.
 */
public class CMainMenuPanel extends JPanel implements ActionListener {

	/**
	 * Defines the available operations that can cause removing this panel
	 */
	public enum EMMDecision{
		LOGOUT,SEARCHBOOK,SEARCHREVIEW,ARRANGE,NEWMSGS,ADDNEWBOOK,REPORT,SEARCHUSER
	}
		
	private static final long serialVersionUID = 1L;
	private JButton m_jButton_Logout = null;
	private JButton m_jButton_SearchBook_MM = null;
	private JButton m_jButton_NewMsgs_MM = null;
	private JButton m_jButton_AddNewBook_MM = null;
	private JButton m_jButton_SearchUser_MM = null;
	

	/**
	 * Saves the last choice of the user
	 */
	private EMMDecision m_lastChoice = EMMDecision.LOGOUT;  //  @jve:decl-index=0:
	private JButton m_jButton_ArrangePayment = null;
	private JButton m_jButton_SearchReview_MM = null;
	private JLabel m_jLabel_Greeting = null;
	
	/**
	 * This is the default constructor
	 */
	public CMainMenuPanel() throws Exception {
		super();
		initialize();
	}

	/**
	 * Initialize greeting message for User.
	 * @throws Exception on User not connected
	 */
	public void initGreeting() throws Exception
	{
		m_jLabel_Greeting.setText("Hey " + AUser.getInstance().getFirstName() + " " + AUser.getInstance().getLastName() + ", Privilege: " + AUser.getInstance().getPrivilege().toString());
	}
	
	
	/**
	 * This method initializes this
	 */
	private void initialize() throws Exception{
		m_jLabel_Greeting = new JLabel();
		m_jLabel_Greeting.setBounds(new Rectangle(128, 18, 384, 35));
		m_jLabel_Greeting.setHorizontalTextPosition(SwingConstants.LEFT);
		m_jLabel_Greeting.setFont(new Font("Eras Light ITC", Font.BOLD, 16));
		initGreeting();
		this.setSize(700, 550);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(700, 550));
		this.add(getM_jButton_Logout(), null);
		this.add(getM_jButton_Search_MM(), null);
		this.add(getM_jButton_NewMsgs_MM(), null);
		this.add(getM_jButton_AddNewBook_MM(), null);
		this.add(getM_jButton_SearchUser_MM(), null);
		this.add(getM_jButton_ArrangePayment(), null);
		this.add(getM_jButton_SearchReview_MM(), null);
		this.add(m_jLabel_Greeting, null);
	}

	/**
	 * This method initializes m_jButton_Logout	
	 * Pressing on this button cause to logout an returning to login screen.	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Logout() {
		if (m_jButton_Logout == null) {
			m_jButton_Logout = new JButton();
			m_jButton_Logout.setBounds(new Rectangle(28, 18, 86, 34));
			m_jButton_Logout.setText("Logout");
			m_jButton_Logout.addActionListener(this);
		}
		return m_jButton_Logout;
	}

	/**
	 * This method initializes m_jButton_SearchBook_MM	
	 * Pressing on this button cause to switch to Search Book panel.	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Search_MM() {
		if (m_jButton_SearchBook_MM == null) {
			m_jButton_SearchBook_MM = new JButton();
			m_jButton_SearchBook_MM.setBounds(new Rectangle(117, 111, 174, 75));
			m_jButton_SearchBook_MM.setText("Search Book");
			m_jButton_SearchBook_MM.addActionListener(this);
		}
		return m_jButton_SearchBook_MM;
	}

	/**
	 * This method initializes m_jButton_NewMsgs_MM	
	 * Pressing on this button cause to switch to New Messages panel.
	 * Button visible and enable only to Librarian and LibraryManager.	
	 * @return javax.swing.JButton	
	 * @throws Exception 
	 */
	private JButton getM_jButton_NewMsgs_MM() throws Exception {
		if (m_jButton_NewMsgs_MM == null) {
			m_jButton_NewMsgs_MM = new JButton();
			m_jButton_NewMsgs_MM.setBounds(new Rectangle(408, 216, 174, 75));
			m_jButton_NewMsgs_MM.setText("New Messages");
			m_jButton_NewMsgs_MM.addActionListener(this);
		}
		if(AUser.getInstance().getPrivilege() == EActor.User ||
		   AUser.getInstance().getPrivilege() == EActor.Reader)
		{
			m_jButton_NewMsgs_MM.setVisible(false);
			m_jButton_NewMsgs_MM.setEnabled(false);
		}
		return m_jButton_NewMsgs_MM;
	}

	/**
	 * This method initializes m_jButton_AddNewBook_MM	
	 * Pressing on this button cause to switch to Add New Book panel.
	 * Button visible and enable only to Librarian and LibraryManager.
	 * @return javax.swing.JButton	
	 * @throws Exception 
	 */
	private JButton getM_jButton_AddNewBook_MM() throws Exception {
		if (m_jButton_AddNewBook_MM == null) {
			m_jButton_AddNewBook_MM = new JButton();
			m_jButton_AddNewBook_MM.setBounds(new Rectangle(117, 216, 174, 75));
			m_jButton_AddNewBook_MM.setText("Add New Book");
			m_jButton_AddNewBook_MM.addActionListener(this);
		}
		if(AUser.getInstance().getPrivilege() == EActor.User ||
		   AUser.getInstance().getPrivilege() == EActor.Reader)
		{
			m_jButton_AddNewBook_MM.setVisible(false);
			m_jButton_AddNewBook_MM.setEnabled(false);
		}
		
		return m_jButton_AddNewBook_MM;
	}

	/**
	 * This method initializes m_jButton_ArrangePayment	
	 * Pressing on this button cause to switch to Arrange Payment panel.
	 * Button visible and enable only to User and Reader.
	 * @return javax.swing.JButton	
	 * @throws Exception 
	 */
	private JButton getM_jButton_ArrangePayment() throws Exception {
		if (m_jButton_ArrangePayment == null) {
			m_jButton_ArrangePayment = new JButton();
			m_jButton_ArrangePayment.setBounds(new Rectangle(524, 18, 148, 34));
			m_jButton_ArrangePayment.setText("Arrange Payment");
			m_jButton_ArrangePayment.addActionListener(this);
		}
		if(AUser.getInstance().getPrivilege() == EActor.Librarian ||
		   AUser.getInstance().getPrivilege() == EActor.LibraryManager)	{
			m_jButton_ArrangePayment.setVisible(false);
			m_jButton_ArrangePayment.setEnabled(false);
		}
		return m_jButton_ArrangePayment;
	}

	
	/**
	 * This method initializes m_jButton_SearchUser_MM	
	 * Pressing on this button cause to switch to Search User panel.
	 * Button visible and enable only to Library Manager.
	 * @return javax.swing.JButton	
	 * @throws Exception 
	 */
	private JButton getM_jButton_SearchUser_MM() throws Exception {
		if (m_jButton_SearchUser_MM == null) {
			m_jButton_SearchUser_MM = new JButton();
			m_jButton_SearchUser_MM.setText("Search User");
			m_jButton_SearchUser_MM.addActionListener(this);
			m_jButton_SearchUser_MM.setSize(new Dimension(174, 75));
			m_jButton_SearchUser_MM.setLocation(new Point(263, 312));
		}
		if(AUser.getInstance().getPrivilege() == EActor.User ||
		   AUser.getInstance().getPrivilege() == EActor.Reader ||
		   AUser.getInstance().getPrivilege() == EActor.Librarian)
		{
			m_jButton_SearchUser_MM.setVisible(false);
			m_jButton_SearchUser_MM.setEnabled(false);
		}
		return m_jButton_SearchUser_MM;
	}

	/**
	 * actionPerformed handle responsible for action performed.
	 * @param ae ActionEvent
	 */
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if(source == m_jButton_Logout)
		{
			setLastChoice(EMMDecision.LOGOUT);
			AUser.logout();
			this.setVisible(false);
		}
		else if(source == m_jButton_ArrangePayment)
		{
			setLastChoice(EMMDecision.ARRANGE);
			this.setVisible(false);
		}
		else if(source == m_jButton_SearchBook_MM)
		{
			setLastChoice(EMMDecision.SEARCHBOOK);
			this.setVisible(false);
		}
		else if(source == m_jButton_SearchReview_MM)
		{
			setLastChoice(EMMDecision.SEARCHREVIEW);
			this.setVisible(false);
		}
		else if(source == m_jButton_AddNewBook_MM)
		{
			setLastChoice(EMMDecision.ADDNEWBOOK);
			this.setVisible(false);
		}
		else if(source == m_jButton_NewMsgs_MM)
		{
			setLastChoice(EMMDecision.NEWMSGS);
			this.setVisible(false);
		}
		else if(source == m_jButton_SearchUser_MM)
		{
			setLastChoice(EMMDecision.SEARCHUSER);
			this.setVisible(false);
		}
	}

	/**
	 * 
	 * @param m_lastChoice
	 */
	public void setLastChoice(EMMDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	/**
	 * 
	 * @return m_lastChoice
	 */
	public EMMDecision getLastChoice() {
		return m_lastChoice;
	}

	/**
	 * This method initializes m_jButton_SearchReview_MM	
	 * Pressing on this button cause to switch to Search Review panel.
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_SearchReview_MM() {
		if (m_jButton_SearchReview_MM == null) {
			m_jButton_SearchReview_MM = new JButton();
			m_jButton_SearchReview_MM.setBounds(new Rectangle(408, 111, 174, 75));
			m_jButton_SearchReview_MM.setText("Search Review");
			m_jButton_SearchReview_MM.addActionListener(this);
		}
		return m_jButton_SearchReview_MM;
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="45,24"
