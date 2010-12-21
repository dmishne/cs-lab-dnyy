package client.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;

import client.core.*;
import client.gui.CSubmitReviewPanel.ESRDecision;

import common.data.CBook;

public class COrderBookPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel jLabel_screenTitle = null;
	private JButton jButton_Back = null;
	private JButton jButton_Purchase = null;
	private JComboBox jComboBox_PayType = null;
	private JLabel jLabel_BookTitle = null;
	private JLabel jLabel_BookAuthor = null;
	private JLabel jLabel_DateRelease = null;
	private JLabel jLabel_BookPrice = null;
	private JLabel jLabel_BookLanguage = null;
	private JButton jButton_ShowReceipt = null;
	
	
	private JLabel jLabel_release = null;
	private JLabel jLabel_lang = null;
	private JLabel jLabel_labelPrice = null;
	private JLabel jLabel_choosePayment = null;
	private CBook m_book;
	private EOBDecision m_lastChoice = null;  //  @jve:decl-index=0:
	

	
	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public COrderBookPanel() throws Exception {
		super();
		m_book = CSearchResultPanel.getChosenBook();
		initialize();
	}
	
	
	public enum EOBDecision
	{
		BACK,PURCHASE,SHOWREVIEW
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		jLabel_choosePayment = new JLabel();
		jLabel_choosePayment.setText("Select your payment type");
		jLabel_choosePayment.setFont(new Font("Elephant", Font.BOLD, 12));
		jLabel_choosePayment.setLocation(new Point(205, 310));
		jLabel_choosePayment.setSize(new Dimension(290, 27));
		jLabel_choosePayment.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_labelPrice = new JLabel();
		jLabel_labelPrice.setText("Price               :");
		jLabel_labelPrice.setFont(new Font("Elephant", Font.BOLD, 12));
		jLabel_labelPrice.setLocation(new Point(20, 260));
		jLabel_labelPrice.setSize(new Dimension(110, 30));
		jLabel_labelPrice.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_lang = new JLabel();
		jLabel_lang.setText("Language       :");
		jLabel_lang.setFont(new Font("Elephant", Font.BOLD, 12));
		jLabel_lang.setLocation(new Point(20, 230));
		jLabel_lang.setSize(new Dimension(110, 30));
		jLabel_lang.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_release = new JLabel();
		jLabel_release.setText("Date Release :");
		jLabel_release.setFont(new Font("Elephant", Font.BOLD, 12));
		jLabel_release.setLocation(new Point(20, 200));
		jLabel_release.setSize(new Dimension(110, 30));
		jLabel_release.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_BookLanguage = new JLabel(); 
		jLabel_BookLanguage.setText("English");
		jLabel_BookLanguage.setSize(new Dimension(150, 30));
		jLabel_BookLanguage.setLocation(new Point(130, 230));
		jLabel_BookLanguage.setFont(new Font("Elephant", Font.BOLD, 12));
		//String price = Double.toString(m_book.getM_price());	
		jLabel_BookPrice = new JLabel();
		jLabel_BookPrice.setText("233.50$");
		jLabel_BookPrice.setSize(new Dimension(150, 30));
		jLabel_BookPrice.setLocation(new Point(130, 260));
		jLabel_BookPrice.setFont(new Font("Elephant", Font.BOLD, 12));
		jLabel_DateRelease = new JLabel(); 
		jLabel_DateRelease.setText("12.10.1987");
		jLabel_DateRelease.setSize(new Dimension(150, 30));
		jLabel_DateRelease.setLocation(new Point(130, 200));
		jLabel_DateRelease.setFont(new Font("Elephant", Font.BOLD, 12));
		jLabel_BookAuthor = new JLabel(); 
		jLabel_BookAuthor.setBounds(new Rectangle(230, 161, 435, 40));
		//jLabel_BookAuthor.setText(m_book.getM_author());
		jLabel_BookAuthor.setText("by 2PAC");
		jLabel_BookAuthor.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		jLabel_BookAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_BookTitle = new JLabel(); 
		jLabel_BookTitle.setBounds(new Rectangle(0, 110, 540, 45));
		//jLabel_BookTitle.setText(m_book.getM_title());
		jLabel_BookTitle.setText("Tag Life");
		jLabel_BookTitle.setFont(new Font("Freestyle Script", Font.BOLD, 45));
		jLabel_BookTitle.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_screenTitle = new JLabel();
		jLabel_screenTitle.setText("Order Book Menu");
		jLabel_screenTitle.setSize(new Dimension(700, 75));
		jLabel_screenTitle.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_screenTitle.setFont(new Font("Freestyle Script", Font.BOLD, 48));
		jLabel_screenTitle.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		jLabel_screenTitle.setLocation(new Point(0, 0));
		this.setLayout(null);
		this.setBounds(new Rectangle(0, 0, 700, 600));
		this.add(jLabel_screenTitle, null);
		this.add(getJButton_Back(), null);
		this.add(getJButton_Purchase(), null);
		this.add(getJComboBox_PayType(), null);
		this.add(jLabel_BookTitle, null);
		this.add(jLabel_BookAuthor, null);
		this.add(jLabel_DateRelease, null);
		this.add(jLabel_BookPrice, null);
		this.add(jLabel_BookLanguage, null);
		this.add(getJButton_ShowReceipt(), null);
		this.add(jLabel_release, null);
		this.add(jLabel_lang, null);
		this.add(jLabel_labelPrice, null);
		this.add(jLabel_choosePayment, null);
	}
	
	
	public void setLastChoice(EOBDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}
	
	public EOBDecision getLastChoice() {
		return m_lastChoice;
	}

	/**
	 * This method initializes jButton_Back	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Back() {
		if (jButton_Back == null) {
			jButton_Back = new JButton();
			jButton_Back.setText("Back");
			jButton_Back.setLocation(new Point(94, 480));
			jButton_Back.setPreferredSize(new Dimension(73, 27));
			jButton_Back.setSize(new Dimension(208, 34));
			jButton_Back.addActionListener(this);
		}
		return jButton_Back;
	}

	/**
	 * This method initializes jButton_Purchase	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Purchase() {
		if (jButton_Purchase == null) {
			jButton_Purchase = new JButton();
			jButton_Purchase.setText("Purchase");
			jButton_Purchase.setLocation(new Point(396, 480));
			jButton_Purchase.setSize(new Dimension(208, 34));
			jButton_Purchase.addActionListener(this);
		}
		return jButton_Purchase;
	}

	/**
	 * This method initializes jComboBox_PayType	
	 * 	
	 * @return javax.swing.JComboBox	
	 * @throws Exception 
	 */
	private JComboBox getJComboBox_PayType() throws Exception {
		if (jComboBox_PayType == null) {
			//jComboBox_PayType = new JComboBox(((CReader)AUser.getInstance()).getPaymentType());
			jComboBox_PayType = new JComboBox();
			jComboBox_PayType.setLocation(new Point(205, 338));
			jComboBox_PayType.setSize(new Dimension(290, 27));
		}
		return jComboBox_PayType;
	}

	/**
	 * This method initializes jButton_ShowReceipt	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_ShowReceipt() {
		if (jButton_ShowReceipt == null) {
			jButton_ShowReceipt = new JButton();
			jButton_ShowReceipt.setText("Show Receipt");
			jButton_ShowReceipt.setLocation(new Point(396, 480));
			jButton_ShowReceipt.setSize(new Dimension(208, 34));
			jButton_ShowReceipt.setPreferredSize(new Dimension(73, 27));
			jButton_ShowReceipt.setVisible(false);
			jButton_ShowReceipt.addActionListener(this);
		}
		return jButton_ShowReceipt;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == jButton_Back)
		{
			setLastChoice(EOBDecision.BACK);
			this.setVisible(false);
		}
		
	}

}
