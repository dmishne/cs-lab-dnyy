package client.gui.searchreview;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
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

import common.data.CBookReview;

public class CSearchReviewPanel extends JPanel implements ActionListener{


	public enum SRPDecision{
		BACK, SEARCH
	}
	
	private static final long serialVersionUID = 1L;
	private JButton m_jButton_back_SRP = null;
	private JLabel m_jLabel_title_SBP = null;
	private JLabel m_jLabel_Author_SBP = null;
	private JLabel m_jLabel_Title_SBP = null;
	private JLabel m_jLabel_keyphrase_SBP = null;
	private JTextField m_jTextField_title_SBP = null;
	private JTextField m_jTextField_Author_SBP = null;
	private JTextField m_jTextField_keyphrase_SBP = null;
	private JButton m_jButton_Search_SRP = null;
	private SRPDecision m_lastChoice = SRPDecision.BACK;  //  @jve:decl-index=0:
	
	static private LinkedList<CBookReview> m_reviewsList = null;
	static private HashMap<String,String> m_searchDetails = null;
	
	/**
	 * @return the m_lastChoice
	 */
	public SRPDecision getLastChoice() {
		return m_lastChoice;
	}

	/**
	 * @param mLastChoice the m_lastChoice to set
	 */
	public void setLastChoice(SRPDecision mLastChoice) {
		m_lastChoice = mLastChoice;
	}

	/**
	 * This is the default constructor
	 */
	public CSearchReviewPanel(){
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		m_jLabel_keyphrase_SBP = new JLabel();
		m_jLabel_keyphrase_SBP.setText("Key phrase");
		m_jLabel_keyphrase_SBP.setSize(new Dimension(160, 40));
		m_jLabel_keyphrase_SBP.setLocation(new Point(30, 218));
		m_jLabel_keyphrase_SBP.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		m_jLabel_Title_SBP = new JLabel();
		m_jLabel_Title_SBP.setText("Title:");
		m_jLabel_Title_SBP.setSize(new Dimension(160, 40));
		m_jLabel_Title_SBP.setLocation(new Point(30, 105));
		m_jLabel_Title_SBP.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		m_jLabel_Author_SBP = new JLabel();
		m_jLabel_Author_SBP.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		m_jLabel_Author_SBP.setSize(new Dimension(160, 40));
		m_jLabel_Author_SBP.setLocation(new Point(29, 161));
		m_jLabel_Author_SBP.setText("Author:");
		m_jLabel_title_SBP = new JLabel();
		m_jLabel_title_SBP.setHorizontalAlignment(SwingConstants.CENTER);
		m_jLabel_title_SBP.setText("Search Review");
		m_jLabel_title_SBP.setLocation(new Point(0, 15));
		m_jLabel_title_SBP.setSize(new Dimension(700, 60));
		m_jLabel_title_SBP.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		m_jLabel_title_SBP.setFont(new Font("Freestyle Script", Font.BOLD, 48));
		this.setSize(700, 550);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(700, 550));
		this.add(getM_jButton_back_SRP(), null);
		this.add(m_jLabel_title_SBP, null);
		this.add(m_jLabel_Author_SBP, null);
		this.add(m_jLabel_Title_SBP, null);
		this.add(m_jLabel_keyphrase_SBP, null);
		this.add(getM_jTextField_title_SBP(), null);
		this.add(getM_jTextField_Author_SBP(), null);
		this.add(getM_jTextField_keyphrase_SBP(), null);
		this.add(getM_jButton_Search_SRP(), null);
	}
	
	
	
	/**
	 * This method initializes m_jButton_back_SRP	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_back_SRP() {
		if (m_jButton_back_SRP == null) {
			m_jButton_back_SRP = new JButton();
			m_jButton_back_SRP.setPreferredSize(new Dimension(73, 27));
			m_jButton_back_SRP.setLocation(new Point(94, 480));
			m_jButton_back_SRP.setText("Back");
			m_jButton_back_SRP.setSize(new Dimension(208, 34));
			m_jButton_back_SRP.addActionListener(this);
		}
		return m_jButton_back_SRP;
	}

	/**
	 * This method initializes m_jTextField_title_SBP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_title_SBP() {
		if (m_jTextField_title_SBP == null) {
			m_jTextField_title_SBP = new JTextField();
			m_jTextField_title_SBP.setLocation(new Point(200, 110));
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
			m_jTextField_Author_SBP.setLocation(new Point(200, 167));
		}
		return m_jTextField_Author_SBP;
	}

	/**
	 * This method initializes m_jTextField_keyphrase_SBP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_keyphrase_SBP() {
		if (m_jTextField_keyphrase_SBP == null) {
			m_jTextField_keyphrase_SBP = new JTextField();
			m_jTextField_keyphrase_SBP.setSize(new Dimension(400, 30));
			m_jTextField_keyphrase_SBP.setLocation(new Point(200, 223));
		}
		return m_jTextField_keyphrase_SBP;
	}

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jButton_back_SRP)
		{
			this.setLastChoice(SRPDecision.BACK);
			this.setVisible(false);
		}
		else if(source == m_jButton_Search_SRP)
		{
			m_searchDetails = new HashMap<String,String>();
			if(m_jTextField_title_SBP.getText().length() >0 )
				m_searchDetails.put("title",m_jTextField_title_SBP.getText());
			if(m_jTextField_Author_SBP.getText().length() >0 )
				m_searchDetails.put("author",m_jTextField_Author_SBP.getText());
			if(m_jTextField_keyphrase_SBP.getText().length() >0 )
				m_searchDetails.put("review",m_jTextField_keyphrase_SBP.getText());
			try {
				m_reviewsList = AUser.getInstance().searchBookReview(m_searchDetails);
			} catch (Exception e) {
				System.out.println("Fail: Can't reach - AUser.searchBookReview()");
			}
			this.setLastChoice(SRPDecision.SEARCH);
			this.setVisible(false);
		}
	}

	/**
	 * @return the m_searchDetails
	 */
	static public HashMap<String, String> getSearchDetails() {
		return m_searchDetails;
	}

	/**
	 * @return the m_reviewsList
	 */
	public static LinkedList<CBookReview> getReviewsList() {
		return m_reviewsList;
	}

	/**
	 * This method initializes m_jButton_Search_SRP	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Search_SRP() {
		if (m_jButton_Search_SRP == null) {
			m_jButton_Search_SRP = new JButton();
			m_jButton_Search_SRP.setBounds(new Rectangle(396, 480, 208, 34));
			m_jButton_Search_SRP.setText("Search Review");
			m_jButton_Search_SRP.addActionListener(this);
		}
		return m_jButton_Search_SRP;
	}
	
}
