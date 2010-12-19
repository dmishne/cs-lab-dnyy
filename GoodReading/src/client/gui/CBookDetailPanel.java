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

public class CBookDetailPanel extends JPanel implements MouseListener,ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton m_jButton_back_BDP = null;
	private CBook m_book;
	private JLabel m_jLabel_Title = null;
	private JLabel m_jLabel_Author = null;
	private JTextArea m_jTextArea_Summary = null;
	private CFiveStarPanel m_cFiveStarPanel = null;

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
			m_jButton_back_BDP.setSize(new Dimension(208, 34));
			m_jButton_back_BDP.setLocation(new Point(94, 480));
			m_jButton_back_BDP.setPreferredSize(new Dimension(208, 34));
			m_jButton_back_BDP.addActionListener(this);
		}
		return m_jButton_back_BDP;
	}

	/**
	 * @return the m_book
	 */
	public CBook getBook() {
		return m_book;
	}

	public void actionPerformed(ActionEvent arg0) {
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
			m_cFiveStarPanel = new CFiveStarPanel(3);
			m_cFiveStarPanel.setPreferredSize(new Dimension(250, 60));
			m_cFiveStarPanel.setSize(new Dimension(200, 30));
			m_cFiveStarPanel.setLocation(new Point(40, 55));
			m_cFiveStarPanel.addMouseListener((MouseListener) this);
		}
		return m_cFiveStarPanel;
	}

	public void mouseClicked(MouseEvent me) {
		Point clicked = me.getPoint();
		if(clicked.x >= 0 && clicked.x <= 30)
		{
		//   ((CReader)AUser.getInstance()).submitScore(1, m_book.getM_ISBN());
		}
		else if(clicked.x >= 40 && clicked.x <= 70)
		{
//			((CReader)AUser.getInstance()).submitScore(2, m_book.getM_ISBN());
		}
		else if(clicked.x >= 80 && clicked.x <= 30)
		{
	//		((CReader)AUser.getInstance()).submitScore(3, m_book.getM_ISBN());
		}
		else if(clicked.x >= 120 && clicked.x <= 150)
		{
		//	((CReader)AUser.getInstance()).submitScore(4, m_book.getM_ISBN());
		}
		else if(clicked.x >= 160 && clicked.x <= 190)
		{
//			((CReader)AUser.getInstance()).submitScore(5, m_book.getM_ISBN());
		}
	}


	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}  //  @jve:decl-index=0:visual-constraint="20,-195"
