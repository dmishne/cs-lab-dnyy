package client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CArrangePayPanel extends JPanel implements ActionListener {

	public enum EAPDecision
	{
		BACK,PURCHASE
	}
	
	private static final long serialVersionUID = 1L;
	private JLabel m_jLabel_title = null;
	private JButton m_jButton_Back = null;
	private JButton m_jButton_Purchase = null;
	
	private EAPDecision m_lastChoice = null;
	
	/**
	 * This is the default constructor
	 */
	public CArrangePayPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
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
	}

	/**
	 * This method initializes m_jButton_Back	
	 * 	
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
	 * This method initializes m_jButton_Purchase	
	 * 	
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

	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jButton_Back)
		{
			setLastChoice(EAPDecision.BACK);
			this.setVisible(false);
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

}  //  @jve:decl-index=0:visual-constraint="10,10"
