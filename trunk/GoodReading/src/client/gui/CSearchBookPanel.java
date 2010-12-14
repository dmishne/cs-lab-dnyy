package client.gui;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.Point;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import client.common.CClientConnector;

public class CSearchBookPanel extends JPanel implements ActionListener{


	public enum SBPDecision{
		BACK, SEARCH
	}
	
	private static final long serialVersionUID = 1L;
	private JButton m_jButton_back_SBP = null;
	private JLabel m_jLabel_title_SBP = null;
	private JLabel m_jLabel_Author_SBP = null;
	private JLabel m_jLabel_Title_SBP = null;
	private JLabel m_jLabel_TOC_SBP = null;
	private JLabel m_jLabel_Topic_SBP = null;
	private JLabel m_jLabel_Languege_SBP = null;
	private JLabel m_jLabel_Summery_SBP = null;
	private JLabel m_jLabel_Labels_SBP = null;
	private JTextField m_jTextField_title_SBP = null;
	private JTextField m_jTextField_Author_SBP = null;
	private JTextField m_jTextField_Summery_SBP = null;
	private JTextField m_jTextField_TOC_SBP = null;
	private JTextField m_jTextField_Labels_SBP = null;
	private JComboBox m_jComboBox_Languege_SBP = null;
	private JComboBox m_jComboBox_Topics_SBP = null;
	private JButton m_jButton_Search_SBP = null;
	private SBPDecision m_lastChoice = SBPDecision.BACK;  //  @jve:decl-index=0:
	
	/**
	 * @return the m_lastChoice
	 */
	public SBPDecision getLastChoice() {
		return m_lastChoice;
	}

	/**
	 * @param mLastChoice the m_lastChoice to set
	 */
	public void setLastChoice(SBPDecision mLastChoice) {
		m_lastChoice = mLastChoice;
	}

	/**
	 * This is the default constructor
	 */
	public CSearchBookPanel() throws Exception{
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() throws Exception {
		m_jLabel_Labels_SBP = new JLabel();
		m_jLabel_Labels_SBP.setText("Labels:");
		m_jLabel_Labels_SBP.setSize(new Dimension(160, 40));
		m_jLabel_Labels_SBP.setLocation(new Point(30, 390));
		m_jLabel_Labels_SBP.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		m_jLabel_Summery_SBP = new JLabel();
		m_jLabel_Summery_SBP.setText("Summery:");
		m_jLabel_Summery_SBP.setSize(new Dimension(160, 40));
		m_jLabel_Summery_SBP.setLocation(new Point(30, 270));
		m_jLabel_Summery_SBP.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		m_jLabel_Languege_SBP = new JLabel();
		m_jLabel_Languege_SBP.setText("Languege:");
		m_jLabel_Languege_SBP.setSize(new Dimension(160, 40));
		m_jLabel_Languege_SBP.setLocation(new Point(30, 230));
		m_jLabel_Languege_SBP.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		m_jLabel_Topic_SBP = new JLabel();
		m_jLabel_Topic_SBP.setText("Topic:");
		m_jLabel_Topic_SBP.setSize(new Dimension(160, 40));
		m_jLabel_Topic_SBP.setLocation(new Point(30, 350));
		m_jLabel_Topic_SBP.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		m_jLabel_TOC_SBP = new JLabel();
		m_jLabel_TOC_SBP.setText("TOC:");
		m_jLabel_TOC_SBP.setSize(new Dimension(160, 40));
		m_jLabel_TOC_SBP.setLocation(new Point(30, 310));
		m_jLabel_TOC_SBP.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		m_jLabel_Title_SBP = new JLabel();
		m_jLabel_Title_SBP.setText("Title:");
		m_jLabel_Title_SBP.setSize(new Dimension(160, 40));
		m_jLabel_Title_SBP.setLocation(new Point(30, 150));
		m_jLabel_Title_SBP.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		m_jLabel_Author_SBP = new JLabel();
		m_jLabel_Author_SBP.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		m_jLabel_Author_SBP.setSize(new Dimension(160, 40));
		m_jLabel_Author_SBP.setLocation(new Point(30, 190));
		m_jLabel_Author_SBP.setText("Author:");
		m_jLabel_title_SBP = new JLabel();
		m_jLabel_title_SBP.setHorizontalAlignment(SwingConstants.CENTER);
		m_jLabel_title_SBP.setText("Search Book");
		m_jLabel_title_SBP.setLocation(new Point(0, 60));
		m_jLabel_title_SBP.setSize(new Dimension(700, 60));
		m_jLabel_title_SBP.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		m_jLabel_title_SBP.setFont(new Font("Freestyle Script", Font.BOLD, 48));
		this.setSize(700, 600);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(700, 600));
		this.add(getM_jButton_back_SBP(), null);
		this.add(m_jLabel_title_SBP, null);
		this.add(m_jLabel_Author_SBP, null);
		this.add(m_jLabel_Title_SBP, null);
		this.add(m_jLabel_TOC_SBP, null);
		this.add(m_jLabel_Topic_SBP, null);
		this.add(m_jLabel_Languege_SBP, null);
		this.add(m_jLabel_Summery_SBP, null);
		this.add(m_jLabel_Labels_SBP, null);
		this.add(getM_jTextField_title_SBP(), null);
		this.add(getM_jTextField_Author_SBP(), null);
		this.add(getM_jTextField_Summery_SBP(), null);
		this.add(getM_jTextField_TOC_SBP(), null);
		this.add(getM_jTextField_Labels_SBP(), null);
		this.add(getM_jComboBox_Languege_SBP(), null);
		this.add(getM_jComboBox_Topics_SBP(), null);
		this.add(getM_jButton_Search_SBP(), null);
	}

	/**
	 * This method initializes m_jButton_back_SBP	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_back_SBP() {
		if (m_jButton_back_SBP == null) {
			m_jButton_back_SBP = new JButton();
			m_jButton_back_SBP.setPreferredSize(new Dimension(73, 27));
			m_jButton_back_SBP.setLocation(new Point(94, 480));
			m_jButton_back_SBP.setText("Back");
			m_jButton_back_SBP.setSize(new Dimension(208, 34));
			m_jButton_back_SBP.addActionListener(this);
		}
		return m_jButton_back_SBP;
	}

	/**
	 * This method initializes m_jTextField_title_SBP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_title_SBP() {
		if (m_jTextField_title_SBP == null) {
			m_jTextField_title_SBP = new JTextField();
			m_jTextField_title_SBP.setLocation(new Point(200, 155));
			m_jTextField_title_SBP.setSize(new Dimension(400, 30));
		}
		return m_jTextField_title_SBP;
	}

	/**
	 * This method initializes m_jTextField_Author_SBP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_Author_SBP() {
		if (m_jTextField_Author_SBP == null) {
			m_jTextField_Author_SBP = new JTextField();
			m_jTextField_Author_SBP.setSize(new Dimension(400, 30));
			m_jTextField_Author_SBP.setLocation(new Point(200, 195));
		}
		return m_jTextField_Author_SBP;
	}

	/**
	 * This method initializes m_jTextField_Summery_SBP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_Summery_SBP() {
		if (m_jTextField_Summery_SBP == null) {
			m_jTextField_Summery_SBP = new JTextField();
			m_jTextField_Summery_SBP.setSize(new Dimension(400, 30));
			m_jTextField_Summery_SBP.setLocation(new Point(200, 275));
		}
		return m_jTextField_Summery_SBP;
	}

	/**
	 * This method initializes m_jTextField_TOC_SBP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_TOC_SBP() {
		if (m_jTextField_TOC_SBP == null) {
			m_jTextField_TOC_SBP = new JTextField();
			m_jTextField_TOC_SBP.setSize(new Dimension(400, 30));
			m_jTextField_TOC_SBP.setLocation(new Point(200, 315));
		}
		return m_jTextField_TOC_SBP;
	}

	/**
	 * This method initializes m_jTextField_Labels_SBP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_Labels_SBP() {
		if (m_jTextField_Labels_SBP == null) {
			m_jTextField_Labels_SBP = new JTextField();
			m_jTextField_Labels_SBP.setSize(new Dimension(400, 30));
			m_jTextField_Labels_SBP.setLocation(new Point(200, 395));
		}
		return m_jTextField_Labels_SBP;
	}

	/**
	 * This method initializes m_jComboBox_Languege_SBP	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getM_jComboBox_Languege_SBP() throws Exception {
		if (m_jComboBox_Languege_SBP == null) {
			m_jComboBox_Languege_SBP = new JComboBox(CClientConnector.getInstance().getLangages());
			m_jComboBox_Languege_SBP.setLocation(new Point(200, 235));
			m_jComboBox_Languege_SBP.setSize(new Dimension(400, 30));
		}
		return m_jComboBox_Languege_SBP;
	}

	/**
	 * This method initializes m_jComboBox_Topics_SBP	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getM_jComboBox_Topics_SBP() throws Exception {
		if (m_jComboBox_Topics_SBP == null) {
			m_jComboBox_Topics_SBP = new JComboBox(CClientConnector.getInstance().getTopics());
			m_jComboBox_Topics_SBP.setSize(new Dimension(400, 30));
			m_jComboBox_Topics_SBP.setLocation(new Point(200, 355));
		}
		return m_jComboBox_Topics_SBP;
	}

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jButton_back_SBP)
		{
			this.setLastChoice(SBPDecision.BACK);
			this.setVisible(false);
		}
		else if(source == m_jButton_Search_SBP)
		{
			this.setLastChoice(SBPDecision.SEARCH);
			this.setVisible(false);
		}
	}

	/**
	 * This method initializes m_jButton_Search_SBP	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Search_SBP() {
		if (m_jButton_Search_SBP == null) {
			m_jButton_Search_SBP = new JButton();
			m_jButton_Search_SBP.setBounds(new Rectangle(396, 480, 208, 34));
			m_jButton_Search_SBP.setText("Search Book");
			m_jButton_Search_SBP.addActionListener(this);
		}
		return m_jButton_Search_SBP;
	}

}
