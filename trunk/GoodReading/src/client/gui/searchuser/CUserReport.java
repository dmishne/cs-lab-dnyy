package client.gui.searchuser;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import client.core.AUser;
import client.core.CLibraryManager;
import javax.swing.JButton;

public class CUserReport extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel_main_label = null;
	private JList m_jList = null;
	private JScrollPane m_listScroll = null;
	private JButton m_jButton_Back = null;

	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public CUserReport() throws Exception {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		jLabel_main_label = new JLabel();
		jLabel_main_label.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		jLabel_main_label.setText("Users Report");
		jLabel_main_label.setSize(new Dimension(700, 35));
		jLabel_main_label.setLocation(new Point(0, 0));
		jLabel_main_label.setPreferredSize(new Dimension(700, 35));
		jLabel_main_label.setHorizontalAlignment(SwingConstants.CENTER);
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(jLabel_main_label, null);
		this.add(getM_jListScrollPane(), null);
		this.add(getM_jButton_Back(), null);
	}

	/**
	 * This method initializes m_jList	
	 * 	
	 * @return javax.swing.JList	
	 * @throws Exception 
	 */
	private JList getM_jList() throws Exception {
		if (m_jList == null) {
			m_jList = new JList(((CLibraryManager)AUser.getInstance()).getUserPurchases(CShowUserListPanel.getChosenUser().getM_userName(),"2011"));
			m_jList.setLocation(new Point(50, 50));
			m_jList.setSize(new Dimension(600, 300));
		}
		return m_jList;
	}
	
	private JScrollPane getM_jListScrollPane() throws Exception {
		if (m_listScroll == null) {
			m_listScroll = new JScrollPane(getM_jList());
			m_listScroll.setLocation(new Point(50, 50));
			m_listScroll.setSize(new Dimension(600, 300));
		}
		return m_listScroll;
	}

	/**
	 * This method initializes m_jButton_Back	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Back() {
		if (m_jButton_Back == null) {
			m_jButton_Back = new JButton();
			m_jButton_Back.setText("Back");
			m_jButton_Back.setSize(new Dimension(150, 34));
			m_jButton_Back.setLocation(new Point(258, 480));
			m_jButton_Back.addActionListener(this);
		}
		return m_jButton_Back;
	}

	public void actionPerformed(ActionEvent ae) {
		this.setVisible(false);
	}
	

}
