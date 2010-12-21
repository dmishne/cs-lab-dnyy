package client.gui;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.core.AUser;
import client.core.CReader;
import common.data.CBook;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class CBookDetailPanel extends JPanel implements MouseListener,ActionListener{

	public enum EBDDecision
	{
		BACK,REVIEW,ORDER
	}
	
	private static final long serialVersionUID = 1L;
	private JButton m_jButton_back_BDP = null;
	private static CBook m_book;
	private JLabel m_jLabel_Title = null;
	private JLabel m_jLabel_Author = null;
	private JTextArea m_jTextArea_Summary = null;
	private CFiveStarPanel m_cFiveStarPanel = null;
	private JLabel m_jLabel_Price = null;
	private JButton m_jButton_Purchase_BDP = null;
	private JButton m_jButton_publishReview = null;
	private EBDDecision m_lastChoice = EBDDecision.BACK;  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public CBookDetailPanel(CBook book) {
		super();
		m_book = CSearchResultPanel.getChosenBook();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		m_jLabel_Price = new JLabel();
		m_jLabel_Price.setBounds(new Rectangle(226, 420, 257, 45));
		m_jLabel_Price.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		m_jLabel_Price.setHorizontalAlignment(SwingConstants.CENTER);
		m_jLabel_Price.setText("Price: " + m_book.getM_price() + " $USD");
		m_jLabel_Author = new JLabel();
		m_jLabel_Author.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		m_jLabel_Author.setSize(new Dimension(350, 50));
		m_jLabel_Author.setLocation(new Point(350, 45));
		m_jLabel_Author.setText("");
		m_jLabel_Title = new JLabel();
		m_jLabel_Title.setText("");
		m_jLabel_Title.setSize(new Dimension(700, 60));
		m_jLabel_Title.setFont(new Font("Freestyle Script", Font.PLAIN, 60));
		m_jLabel_Title.setHorizontalAlignment(SwingConstants.CENTER);
		m_jLabel_Title.setLocation(new Point(0, 0));
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(getM_jButton_back_BDP(), null);
		m_jLabel_Title.setText(m_book.getM_title());
		m_jLabel_Author.setText("By " + m_book.getM_author());
		this.add(m_jLabel_Title, null);
		this.add(m_jLabel_Author, null);
		this.add(getM_jTextArea_Summary(), null);
		this.add(getM_cFiveStarPanel(), null);
		this.add(m_jLabel_Price, null);
		this.add(getM_jButton_Purchase_BDP(), null);
		this.add(getM_jButton_publishReview(), null);
	}

	/**
	 * This method initializes m_jButton_back_BDP	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_back_BDP() {
		if (m_jButton_back_BDP == null) {
			m_jButton_back_BDP = new JButton();
			m_jButton_back_BDP.setText("Back");
			m_jButton_back_BDP.setSize(new Dimension(150, 34));
			m_jButton_back_BDP.setLocation(new Point(62, 480));
			m_jButton_back_BDP.setPreferredSize(new Dimension(150, 34));
			m_jButton_back_BDP.addActionListener(this);
		}
		return m_jButton_back_BDP;
	}

	/**
	 * @return the m_book
	 */
	public static CBook getBook() {
		return m_book;
	}

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source == m_jButton_back_BDP)
		{
			this.setVisible(false);
			this.setLastChoice(EBDDecision.BACK);
		}
		else if(source == m_jButton_publishReview)
		{
			this.setVisible(false);
			this.setLastChoice(EBDDecision.REVIEW);
		}
		this.setVisible(false);
	}

	/**
	 * This method initializes m_jTextArea_Summary	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getM_jTextArea_Summary() {
		if (m_jTextArea_Summary == null) {
			m_jTextArea_Summary = new JTextArea();
			m_jTextArea_Summary.setEditable(false);
			m_jTextArea_Summary.setSize(new Dimension(325, 300));
			m_jTextArea_Summary.setLocation(new Point(350, 105));
			m_jTextArea_Summary.setPreferredSize(new Dimension(325, 300));
			m_jTextArea_Summary.setLineWrap(true);
			m_jTextArea_Summary.setBackground(new Color(238, 238, 238));
			//m_jTextArea_Summary.setBorder(BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "Summary", TitledBorder.LEFT, TitledBorder.TOP,  new Font("Dialog", Font.PLAIN, 12),Color.BLACK));
			m_jTextArea_Summary.setText(m_book.getM_summary());
		}
		return m_jTextArea_Summary;
	}

	/**
	 * This method initializes m_cFiveStarPanel	
	 * 	
	 * @return client.gui.CFiveStarPanel	
	 */
	private CFiveStarPanel getM_cFiveStarPanel() {
		if (m_cFiveStarPanel == null) {
			m_cFiveStarPanel = new CFiveStarPanel(m_book.getScore());
			m_cFiveStarPanel.setPreferredSize(new Dimension(250, 60));
			m_cFiveStarPanel.setSize(new Dimension(200, 30));
			m_cFiveStarPanel.setLocation(new Point(40, 55));
			m_cFiveStarPanel.addMouseListener((MouseListener) this);
		}
		return m_cFiveStarPanel;
	}

	public void mouseClicked(MouseEvent me){
		Point clicked = me.getPoint();
		int score = 0;
		if(clicked.x >= 0 && clicked.x <= 30)
		{
			score = 1;
		}
		else if(clicked.x >= 40 && clicked.x <= 70)
		{
			score = 2;
		}
		else if(clicked.x >= 80 && clicked.x <= 30)
		{
			score = 3;
		}
		else if(clicked.x >= 120 && clicked.x <= 150)
		{
			score = 4;
		}
		else if(clicked.x >= 160 && clicked.x <= 190)
		{
			score = 5;
		}
		try {
			((CReader)AUser.getInstance()).addScore(m_book.getM_ISBN(),score);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
	}


	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	/**
	 * This method initializes m_jButton_Purchase_BDP	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Purchase_BDP() {
		if (m_jButton_Purchase_BDP == null) {
			m_jButton_Purchase_BDP = new JButton();
			m_jButton_Purchase_BDP.setBounds(new Rectangle(486, 480, 150, 34));
			m_jButton_Purchase_BDP.setText("Order");
			m_jButton_Purchase_BDP.setPreferredSize(new Dimension(150, 34));
		}
		return m_jButton_Purchase_BDP;
	}

	/**
	 * This method initializes m_jButton_publishReview	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_publishReview() {
		if (m_jButton_publishReview == null) {
			m_jButton_publishReview = new JButton();
			m_jButton_publishReview.setBounds(new Rectangle(274, 480, 150, 34));
			m_jButton_publishReview.setText("Publish Review");
			m_jButton_publishReview.addActionListener(this);
		}
		return m_jButton_publishReview;
	}

	/**
	 * @param m_lastChoice the m_lastChoice to set
	 */
	public void setLastChoice(EBDDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	/**
	 * @return the m_lastChoice
	 */
	public EBDDecision getLastChoice() {
		return m_lastChoice;
	}
	
}  //  @jve:decl-index=0:visual-constraint="20,-195"
