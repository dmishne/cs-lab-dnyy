package client.gui;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CMainMenuPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton m_jButton_Logout = null;
	private JButton m_jButton_SearchBook_MM = null;
	private JButton m_jButton_NewMsgs_MM = null;
	private JButton m_jButton_AddNewBook_MM = null;
	private JButton m_jButton_Report_MM = null;
	private JButton m_jButton_SearchUser_MM = null;
	
	public enum MM_Decision{
		LOGOUT,SEARCH,XXXXXXXX,NEWMSGS,ADDNEWBOOK,REPORT,SEARCHUSER
	}
	
	private MM_Decision m_lastChoice = MM_Decision.LOGOUT;
	
	
	/**
	 * This is the default constructor
	 */
	public CMainMenuPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 500);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(500, 500));
		this.add(getM_jButton_Logout(), null);
		this.add(getM_jButton_Search_MM(), null);
		this.add(getM_jButton_NewMsgs_MM(), null);
		this.add(getM_jButton_AddNewBook_MM(), null);
		this.add(getM_jButton_Report_MM(), null);
		this.add(getM_jButton_SearchUser_MM(), null);
	}

	/**
	 * This method initializes m_jButton_Logout	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Logout() {
		if (m_jButton_Logout == null) {
			m_jButton_Logout = new JButton();
			m_jButton_Logout.setBounds(new Rectangle(23, 18, 121, 34));
			m_jButton_Logout.setText("Logout");
			m_jButton_Logout.addActionListener(this);
		}
		return m_jButton_Logout;
	}

	/**
	 * This method initializes m_jButton_SearchBook_MM	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Search_MM() {
		if (m_jButton_SearchBook_MM == null) {
			m_jButton_SearchBook_MM = new JButton();
			m_jButton_SearchBook_MM.setBounds(new Rectangle(50, 111, 174, 75));
			m_jButton_SearchBook_MM.setText("Search Book");
		}
		return m_jButton_SearchBook_MM;
	}

	/**
	 * This method initializes m_jButton_NewMsgs_MM	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_NewMsgs_MM() {
		if (m_jButton_NewMsgs_MM == null) {
			m_jButton_NewMsgs_MM = new JButton();
			m_jButton_NewMsgs_MM.setBounds(new Rectangle(274, 111, 174, 75));
			m_jButton_NewMsgs_MM.setText("New Messages");
		}
		return m_jButton_NewMsgs_MM;
	}

	/**
	 * This method initializes m_jButton_AddNewBook_MM	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_AddNewBook_MM() {
		if (m_jButton_AddNewBook_MM == null) {
			m_jButton_AddNewBook_MM = new JButton();
			m_jButton_AddNewBook_MM.setBounds(new Rectangle(50, 216, 174, 75));
			m_jButton_AddNewBook_MM.setText("Add New Book");
		}
		return m_jButton_AddNewBook_MM;
	}

	/**
	 * This method initializes m_jButton_Report_MM	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Report_MM() {
		if (m_jButton_Report_MM == null) {
			m_jButton_Report_MM = new JButton();
			m_jButton_Report_MM.setBounds(new Rectangle(274, 214, 174, 75));
			m_jButton_Report_MM.setText("Generate Report");
		}
		return m_jButton_Report_MM;
	}

	/**
	 * This method initializes m_jButton_SearchUser_MM	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_SearchUser_MM() {
		if (m_jButton_SearchUser_MM == null) {
			m_jButton_SearchUser_MM = new JButton();
			m_jButton_SearchUser_MM.setBounds(new Rectangle(162, 312, 174, 75));
			m_jButton_SearchUser_MM.setText("Search User");
		}
		return m_jButton_SearchUser_MM;
	}


	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if(source == m_jButton_Logout)
		{
			setM_lastChoice(MM_Decision.LOGOUT);
			this.setVisible(false);
		}
	}

	public void setM_lastChoice(MM_Decision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	public MM_Decision getM_lastChoice() {
		return m_lastChoice;
	}

}  //  @jve:decl-index=0:visual-constraint="45,24"
