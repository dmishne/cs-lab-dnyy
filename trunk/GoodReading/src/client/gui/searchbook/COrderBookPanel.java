package client.gui.searchbook;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;
import client.core.*;
import client.gui.CustomLabel;

public class COrderBookPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel jLabel_screenTitle = null;
	private JButton jButton_Back = null;
	private JButton jButton_Purchase = null;
	private JComboBox jComboBox_PayType = null;
	private CustomLabel jLabel_BookTitle = null;
	private CustomLabel jLabel_BookAuthor = null;
	private JLabel jLabel_DateRelease = null;
	private JLabel jLabel_BookPrice = null;
	private JLabel jLabel_BookLanguage = null;
	private JButton jButton_ShowReceipt = null;
	private JComboBox jComboBox_fileType = null;
	private JLabel jLabel_release = null;
	private JLabel jLabel_lang = null;
	private JLabel jLabel_labelPrice = null;
	private JLabel jLabel_choosePayment = null;
	private JLabel jLabel_chooseFile = null;
	private EOBDecision m_lastChoice = null;  
	private static String chosenFileType = null;  //  @jve:decl-index=0:
	

	
	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public COrderBookPanel() throws Exception {
		super();
		initialize();
	}
	
	
	public enum EOBDecision
	{
		BACK,PURCHASE,SHOWRECEIPT
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
		jLabel_choosePayment.setLocation(new Point(387, 349));
		jLabel_choosePayment.setSize(new Dimension(230, 27));
		jLabel_choosePayment.setHorizontalAlignment(SwingConstants.CENTER);
	    jLabel_chooseFile = new JLabel();
		jLabel_chooseFile.setText("Select preferred file type");
		jLabel_chooseFile.setFont(new Font("Elephant", Font.BOLD, 12));
		jLabel_chooseFile.setSize(new Dimension(230, 27));
		jLabel_chooseFile.setLocation(new Point(80, 349));
		jLabel_chooseFile.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_labelPrice = new JLabel();
		jLabel_labelPrice.setText("Price              : ");
		jLabel_labelPrice.setFont(new Font("Eras Bold ITC", Font.BOLD, 12));
		jLabel_labelPrice.setLocation(new Point(60, 275));
		jLabel_labelPrice.setSize(new Dimension(110, 30));
		jLabel_labelPrice.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_lang = new JLabel();
		jLabel_lang.setText("Language       : ");
		jLabel_lang.setFont(new Font("Eras Bold ITC", Font.BOLD, 12));
		jLabel_lang.setLocation(new Point(60, 245));
		jLabel_lang.setSize(new Dimension(110, 30));
		jLabel_lang.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_release = new JLabel();
		jLabel_release.setText("Date Release : ");
		jLabel_release.setFont(new Font("Eras Bold ITC", Font.BOLD, 12));
		jLabel_release.setLocation(new Point(60, 215));
		jLabel_release.setSize(new Dimension(110, 30));
		jLabel_release.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_BookLanguage = new JLabel(); 
		jLabel_BookLanguage.setText(CBookDetailPanel.getBook().getM_language());
		jLabel_BookLanguage.setSize(new Dimension(150, 30));
		jLabel_BookLanguage.setLocation(new Point(170, 245));
		jLabel_BookLanguage.setFont(new Font("Eras Bold ITC", Font.BOLD, 12));
		Double pricetemp = (CBookDetailPanel.getBook().getM_price());
		String price = pricetemp.intValue() + "." + (int)((pricetemp-(pricetemp).intValue())*100) + "$";
		jLabel_BookPrice = new JLabel();
		jLabel_BookPrice.setText(price);
		jLabel_BookPrice.setSize(new Dimension(150, 30));
		jLabel_BookPrice.setLocation(new Point(170, 275));
		jLabel_BookPrice.setFont(new Font("Eras Bold ITC", Font.BOLD, 12));
		jLabel_BookPrice.setForeground(Color.red);
		jLabel_DateRelease = new JLabel(); 
		jLabel_DateRelease.setText(CBookDetailPanel.getBook().getM_release_date());
		jLabel_DateRelease.setSize(new Dimension(150, 30));
		jLabel_DateRelease.setLocation(new Point(170, 215));
		jLabel_DateRelease.setFont(new Font("Eras Bold ITC", Font.BOLD, 12));
		/*
		 *   DO NOT CHANGE ORDER OF :
		 */
		jLabel_BookTitle = new CustomLabel();
		jLabel_BookTitle.setPreferredSize(new Dimension(640, 45));
		jLabel_BookTitle.setLocation(new Point(30, 114));
		jLabel_BookTitle.setSize(new Dimension(640, 45));
		String booktitle = CBookDetailPanel.getBook().getM_title();
		jLabel_BookTitle.setText(booktitle);
		int title_size = sized(booktitle);
		jLabel_BookTitle.setFont(new Font("Freestyle Script", Font.BOLD, title_size));
		jLabel_BookTitle.setForeground(Color.blue);		
		jLabel_BookTitle.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_BookAuthor = new CustomLabel();
		jLabel_BookAuthor.setText("by " + CBookDetailPanel.getBook().getM_author());
		jLabel_BookAuthor.setFont(new Font("Freestyle Script", Font.BOLD, (int)(title_size*36/48)));
		jLabel_BookAuthor.setSize(new Dimension(435, 35));
		jLabel_BookAuthor.setLocation(new Point(264, 160));
		jLabel_BookAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		/*
		 *   END OF "DO NOT CHANGE ORDER OF"
		 */
		jLabel_screenTitle = new JLabel();
		jLabel_screenTitle.setText("Order Book Menu");
		jLabel_screenTitle.setSize(new Dimension(700, 75));
		jLabel_screenTitle.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_screenTitle.setFont(new Font("Freestyle Script", Font.BOLD, 48));
		jLabel_screenTitle.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		jLabel_screenTitle.setLocation(new Point(0, 15));
		this.setLayout(null);
		this.setBounds(new Rectangle(0, 0, 700, 600));
		this.add(jLabel_screenTitle, null);
		this.add(getJButton_Back(), null);
		this.add(getJButton_Purchase(), null);
		this.add(getJComboBox_PayType(), null);
		this.add(getJComboBox_fileType(),null);
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
		this.add(jLabel_chooseFile, null);
	}
	
	
	private int sized(String labelText) {
		Font LFont = jLabel_BookTitle.getFont();
		double widthRatio = (double)jLabel_BookTitle.getWidth() / (double)jLabel_BookTitle.getFontMetrics(LFont).stringWidth(labelText);;
		int newFontSize = (int)(LFont.getSize() * widthRatio);
		int componentHeight = jLabel_BookTitle.getHeight();		
		int fontSizeToUse = Math.min(newFontSize, componentHeight);
		return fontSizeToUse;
	}


	public void setLastChoice(EOBDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}
	
	public EOBDecision getLastChoice() {
		return m_lastChoice;
	}

	


	
	


	/**
	 * @return the chosenFileType
	 */
	public static String getChosenFileType() {
		return chosenFileType;
	}


	/**
	 * @param chosenFileType the chosenFileType to set
	 */
	public static void setChosenFileType(String chosenFileType) {
		COrderBookPanel.chosenFileType = chosenFileType;
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
			jComboBox_PayType = new JComboBox(((CReader)AUser.getInstance()).getPaymentType());
			jComboBox_PayType.setLocation(new Point(387, 377));
			jComboBox_PayType.setSize(new Dimension(230, 27));
		}
		return jComboBox_PayType;
	}
	
	
	/**
	 * This method initializes jComboBox_PayType	
	 * 	
	 * @return javax.swing.JComboBox	
	 * @throws Exception 
	 */
	private JComboBox getJComboBox_fileType() throws Exception {
		if (jComboBox_fileType == null) {
			jComboBox_fileType = new JComboBox(AUser.getInstance().getFileType(CBookDetailPanel.getBook().getM_ISBN()));
			jComboBox_fileType.setLocation(new Point(80, 377));
			jComboBox_fileType.setSize(new Dimension(230, 27));
		}
		return jComboBox_fileType;
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
		String receipt;
		if(source == jButton_Back)
		{
			this.setLastChoice(EOBDecision.BACK);
			this.setVisible(false);
		}
		if(source == jButton_Purchase)
		{
			try {
				chosenFileType = jComboBox_fileType.getSelectedItem().toString();
				receipt =((CReader)AUser.getInstance()).orderBook(CBookDetailPanel.getBook().getM_ISBN(),(String)jComboBox_PayType.getSelectedItem() );	       
				if(receipt.isEmpty() == false)
	            {
	            	jButton_Purchase.setVisible(false);
	            	jButton_ShowReceipt.setVisible(true);	
	            }
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		if(source == jButton_ShowReceipt)
		{
			this.setLastChoice(EOBDecision.SHOWRECEIPT); 
			this.setVisible(false);
		}
		
	}
	

}
