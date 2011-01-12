package client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.core.AUser;
import client.core.CReader;

/**
 *	CArrangePayPanel defines the panel of the arrange payment screen
 */
public class CArrangePayPanel extends JPanel implements ActionListener {

	/**
	 * Defines the operation the will cause changing of the panel
	 */
	public enum EAPDecision
	{
		BACK,PURCHASE
	}
	
	private static final long serialVersionUID = 1L;
	private JLabel m_jLabel_title = null;
	private JButton m_jButton_Back = null;
	private JButton m_jButton_Purchase = null;
	
	/**
	 * Saves the last choice of the user
	 */
	private EAPDecision m_lastChoice = null; 
	
	private JComboBox m_jComboBox_PaymentType = null;
	private JLabel m_jLabel_ChoosePayment = null;
	/**
	 * Contains the Available option for arranging the payment.
	 */
	final private String[] types = {"Prepaid - Yearly","Prepaid - Monthly","Per Purchase"};
	private JTextField m_jTextField_CCNUM = null;
	private JTextField m_jTextField_CCEXP = null;
	private JTextField m_jTextField_SSN = null;
	private JLabel m_jLabel_CCNUM = null;
	private JLabel m_jLabel_CCEXP = null;
	private JLabel m_jLabel_SSN = null; 
	
	/**
	 * This is the default constructor
	 */
	public CArrangePayPanel() {
		super();
		initialize();
	}

	/**
	 * initialize() initializes this class
	 * 
	 * @return void
	 */
	private void initialize() {
		m_jLabel_SSN = new JLabel();
		m_jLabel_SSN.setText("SSN:");
		m_jLabel_SSN.setLocation(new Point(40, 285));
		m_jLabel_SSN.setSize(new Dimension(141, 30));
		m_jLabel_SSN.setVisible(false);
		m_jLabel_CCEXP = new JLabel();
		m_jLabel_CCEXP.setText("Expiration Date:");
		m_jLabel_CCEXP.setLocation(new Point(40, 245));
		m_jLabel_CCEXP.setSize(new Dimension(141, 30));
		m_jLabel_CCEXP.setVisible(false);
		m_jLabel_CCNUM = new JLabel();
		m_jLabel_CCNUM.setText("Credit Card Number:");
		m_jLabel_CCNUM.setSize(new Dimension(141, 30));
		m_jLabel_CCNUM.setLocation(new Point(40, 205));
		m_jLabel_CCNUM.setVisible(false);
		m_jLabel_ChoosePayment = new JLabel();
		m_jLabel_ChoosePayment.setBounds(new Rectangle(15, 91, 370, 38));
		m_jLabel_ChoosePayment.setFont(new Font("Dialog", Font.BOLD, 18));
		m_jLabel_ChoosePayment.setText("Please choose type of payment:");
		m_jLabel_title = new JLabel();
		m_jLabel_title.setText("Arrange Payment");
		m_jLabel_title.setSize(new Dimension(370, 70));
		m_jLabel_title.setFont(new Font("Freestyle Script", Font.BOLD, 48));
		m_jLabel_title.setHorizontalAlignment(SwingConstants.CENTER);
		m_jLabel_title.setLocation(new Point(15, 15));
		this.setSize(400, 400);
		this.setLayout(null);
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.add(m_jLabel_title, null);
		this.add(getM_jButton_Back(), null);
		this.add(getM_jButton_Purchase(), null);
		this.add(getM_jComboBox_PaymentType(), null);
		this.add(m_jLabel_ChoosePayment, null);
		this.add(getM_jTextField_CCNUM(), null);
		this.add(getM_jTextField_CCEXP(), null);
		this.add(getM_jTextField_SSN(), null);
		this.add(m_jLabel_CCNUM, null);
		this.add(m_jLabel_CCEXP, null);
		this.add(m_jLabel_SSN, null);
	}

	/**
	 * getM_jButton_Back() initializes m_jButton_Back	
	 * On pressing on m_jButton_Back the current screen disappears. 
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Back() {
		if (m_jButton_Back == null) {
			m_jButton_Back = new JButton();
			m_jButton_Back.setBounds(new Rectangle(24, 334, 163, 47));
			m_jButton_Back.setText("Back");
			m_jButton_Back.addActionListener(this);
		}
		return m_jButton_Back;
	}

	/**
	 * getM_jButton_Purchase() initializes m_jButton_Purchase	
	 * On pressing on getM_jButton_Purchase the purchase information 
	 * pass to the server and database and the screen disappears.	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Purchase() {
		if (m_jButton_Purchase == null) {
			m_jButton_Purchase = new JButton();
			m_jButton_Purchase.setBounds(new Rectangle(211, 334, 163, 47));
			m_jButton_Purchase.setText("Purchase");
			m_jButton_Purchase.addActionListener(this);
		}
		return m_jButton_Purchase;
	}

	/**
	 * actionPerformed handle responsible for action performed.  
	 * @param ae ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jButton_Back)
		{
			setLastChoice(EAPDecision.BACK);
			this.setVisible(false);
		}
		else if(source == m_jComboBox_PaymentType)
		{
			if(((JComboBox)source).getSelectedIndex() == 2)
			{
				setVisible();
			}
			else
			{
				setInVisible();
			}
		}
		else if(source == m_jButton_Purchase)
		{
			String result;
			try
			{
				if(m_jComboBox_PaymentType.getSelectedIndex() == 2)
				{
					result = ((CReader)AUser.getInstance()).ArrangePayment("CreditCard",m_jTextField_CCNUM.getText(),m_jTextField_CCEXP.getText(),m_jTextField_SSN.getText());
				}
				else
				{
					if(m_jComboBox_PaymentType.getSelectedIndex() == 0)
					{
						result = ((CReader)AUser.getInstance()).ArrangePayment("Yearly");
					}
					else
					{
						result = ((CReader)AUser.getInstance()).ArrangePayment("Monthly");
					}
				}
				JOptionPane.showMessageDialog(null, result, "Success",JOptionPane.INFORMATION_MESSAGE);
				setLastChoice(EAPDecision.PURCHASE);
				this.setVisible(false);
			}
			catch (ClassCastException ce)
			{
				JOptionPane.showMessageDialog(null, "You don't have the correct privilege!" , "Error",JOptionPane.ERROR_MESSAGE);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null,e.getClass().toString() + " - " + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * @param m_lastChoice the m_lastChoice to set
	 */
	public void setLastChoice(EAPDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	/**
	 * @return the m_lastChoice
	 */
	public EAPDecision getLastChoice() {
		return m_lastChoice;
	}

	/**
	 * This method initializes m_jComboBox_PaymentType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getM_jComboBox_PaymentType() {
		if (m_jComboBox_PaymentType == null) {
			m_jComboBox_PaymentType = new JComboBox(types);
			m_jComboBox_PaymentType.setSelectedIndex(0);
			m_jComboBox_PaymentType.setLocation(new Point(75, 150));
			m_jComboBox_PaymentType.setSize(new Dimension(250, 30));
			m_jComboBox_PaymentType.addActionListener(this);
		}
		return m_jComboBox_PaymentType;
	}

	/**
	 * This method initializes m_jTextField_CCNUM	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_CCNUM() {
		if (m_jTextField_CCNUM == null) {
			m_jTextField_CCNUM = new JTextField();
			m_jTextField_CCNUM.setBounds(new Rectangle(195, 205, 150, 30));
			m_jTextField_CCNUM.setVisible(false);
		}
		return m_jTextField_CCNUM;
	}

	/**
	 * This method initializes m_jTextField_CCEXP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_CCEXP() {
		if (m_jTextField_CCEXP == null) {
			m_jTextField_CCEXP = new JTextField();
			m_jTextField_CCEXP.setSize(new Dimension(150, 30));
			m_jTextField_CCEXP.setLocation(new Point(195, 245));
			m_jTextField_CCEXP.setVisible(false);
		}
		return m_jTextField_CCEXP;
	}

	/**
	 * This method initializes m_jTextField_SSN	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_SSN() {
		if (m_jTextField_SSN == null) {
			m_jTextField_SSN = new JTextField();
			m_jTextField_SSN.setSize(new Dimension(150, 30));
			m_jTextField_SSN.setLocation(new Point(195, 285));
			m_jTextField_SSN.setVisible(false);
		}
		return m_jTextField_SSN;
	}

	/**
	 * This method set the relevant features of
	 * the Credit Card payment to be Visible
	 */
	void setVisible()
	{
		m_jTextField_CCNUM.setVisible(true);
		m_jTextField_CCNUM.setText("");
		m_jTextField_CCEXP.setVisible(true);
		m_jTextField_CCEXP.setText("");
		m_jTextField_SSN.setVisible(true);
		m_jTextField_SSN.setText("");
		m_jLabel_CCNUM.setVisible(true);
		m_jLabel_CCEXP.setVisible(true);
		m_jLabel_SSN.setVisible(true); 
	}

	/**
	 * This method set the relevant features of
	 * the Credit Card payment to be Invisible
	 */
	void setInVisible()
	{
		m_jTextField_CCNUM.setVisible(false);
		m_jTextField_CCEXP.setVisible(false);
		m_jTextField_SSN.setVisible(false);
		m_jLabel_CCNUM.setVisible(false);
		m_jLabel_CCEXP.setVisible(false);
		m_jLabel_SSN.setVisible(false); 
	}
	
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
