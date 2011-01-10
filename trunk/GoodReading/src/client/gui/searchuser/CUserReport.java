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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JList;

import client.core.AUser;
import client.core.CLibraryManager;
import javax.swing.JButton;
import javax.swing.JComboBox;

import common.data.CPurchaseStats;

public class CUserReport extends JPanel implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel_main_label = null;
	private JList m_jList = null;
	private JScrollPane m_listScroll = null;
	private JButton m_jButton_Back = null;
	private JComboBox m_jComboBox_years = null;
	private Vector<String> m_years = null;  //  @jve:decl-index=0:
	private String m_lastYear;

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
		m_years = ((CLibraryManager)AUser.getInstance()).getYears();
		setMaxYear();
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
		this.add(getM_jComboBox_Years(), null);
	}

	/**
	 * This method initializes m_jList	
	 * 	
	 * @return javax.swing.JList	
	 * @throws Exception 
	 */
	private JList getM_jList() throws Exception {
		if (m_jList == null) {
			Vector<CPurchaseStats> temp = ((CLibraryManager)AUser.getInstance()).getUserPurchases(CShowUserListPanel.getChosenUser().getM_userName(),(String)getM_jComboBox_Years().getSelectedItem());
			Collections.sort(temp);	
			m_jList = new JList(temp);
			m_jList.setLocation(new Point(50, 50));
			m_jList.setSize(new Dimension(600, 380));
		}
		return m_jList;
	}
	
	private JScrollPane getM_jListScrollPane() throws Exception {
		if (m_listScroll == null) {
			m_listScroll = new JScrollPane(getM_jList());
			m_listScroll.setLocation(new Point(50, 50));
			m_listScroll.setSize(new Dimension(600, 380));
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

	/**
	 * This method initializes m_jComboBox_years	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getM_jComboBox_Years() throws Exception {
		if (m_jComboBox_years == null) {
			m_jComboBox_years = new JComboBox(m_years);
			m_jComboBox_years.setSize(new Dimension(150, 25));
			m_jComboBox_years.setPreferredSize(new Dimension(150, 25));
			m_jComboBox_years.setLocation(new Point(258, 445));
			m_jComboBox_years.setSelectedItem(m_lastYear);
			m_jComboBox_years.addItemListener(this);
		}
		return m_jComboBox_years;
	}

	private void setMaxYear()
	{
		String max = m_years.get(0);
		for(String temp: m_years)
		{
			if(temp.compareTo(max)>0)
			{
				max = temp;
			}
		}
		m_lastYear = max;
	}
	
	public void itemStateChanged(ItemEvent ie) {
		if(ie.getSource() == m_jComboBox_years)
		{
			this.remove(m_listScroll);
			m_jList = null;
			m_listScroll = null;
			try {
				this.add(getM_jListScrollPane());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	

}
