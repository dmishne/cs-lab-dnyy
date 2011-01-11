package client.gui.searchbook;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import client.core.AUser;
import client.core.CReader;
import client.core.EActor;
import client.gui.CustomLabel;

import common.data.CBook;
import javax.swing.JScrollPane;

public class CBookDetailPanel extends JPanel implements MouseListener,ActionListener{

	public enum EBDDecision
	{
		BACK,REVIEW,ORDER,EDITBOOK,REPORT
	}
	
	private static final long serialVersionUID = 1L;
	private JButton m_jButton_back_BDP = null;
	private static CBook m_book;
	private CustomLabel m_jLabel_Title = null;
	private CustomLabel m_jLabel_Author = null;
	private JTextArea m_jTextArea_Summary = null;
	private CFiveStarPanel m_cFiveStarPanel = null;
	private JLabel m_jLabel_Price = null;
	private JButton m_jButton_Purchase_BDP = null;
	private JButton m_jButton_publishReview = null;
	private EBDDecision m_lastChoice = EBDDecision.BACK;  //  @jve:decl-index=0:
	private JButton m_jButton_EditBook = null;
	private JButton m_jButton_Report = null;
	private JLabel jLabel_scoreRes = null;
	private CustomLabel jLabel_publisher = null;
	private CustomLabel jLabel_language = null;
	private CustomLabel jLabel_isbn = null;
	private CustomLabel jLabel_releaseDate = null;
	private CustomLabel jLabel_publish = null;
	private CustomLabel jLabel_lang = null;
	private CustomLabel jLabel_isb = null;
	private CustomLabel jLabel_re_date = null;
	private JScrollPane jScrollPane_toc = null;
	private JTextArea jTextArea_toc = null;
	private CustomLabel jLabel_toc = null;

	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public CBookDetailPanel(CBook book) throws Exception {
		super();
		m_book = CSearchResultPanel.getChosenBook();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		//
		jLabel_toc = new CustomLabel();
		jLabel_toc.setText("TOC :");
		jLabel_toc.setSize(new Dimension(40, 27));
		jLabel_toc.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel_toc.setLocation(new Point(45, 300));
		jLabel_re_date = new CustomLabel();
		jLabel_re_date.setText("Release Date  :");
		jLabel_re_date.setLocation(new Point(45, 260));
		jLabel_re_date.setSize(new Dimension(90, 27));
		jLabel_isb = new CustomLabel();
		jLabel_isb.setText("ISBN  :");
		jLabel_isb.setLocation(new Point(45, 230));
		jLabel_isb.setSize(new Dimension(90, 27));
		jLabel_lang = new CustomLabel();
		jLabel_lang.setText("Language  :");
		jLabel_lang.setLocation(new Point(45, 200));
		jLabel_lang.setSize(new Dimension(90, 27));
		jLabel_publish = new CustomLabel();
		jLabel_publish.setText("Publisher  :");
		jLabel_publish.setSize(new Dimension(90, 27));
		jLabel_publish.setLocation(new Point(45, 170));
		String rDate = m_book.getM_release_date();
		jLabel_releaseDate = new CustomLabel();
		jLabel_releaseDate.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		jLabel_releaseDate.setLocation(new Point(140, 260));
		jLabel_releaseDate.setSize(new Dimension(190, 27));
		jLabel_releaseDate.setText(rDate);
		String isbn = m_book.getM_ISBN();
		jLabel_isbn = new CustomLabel();
		jLabel_isbn.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		jLabel_isbn.setLocation(new Point(140, 230));
		jLabel_isbn.setSize(new Dimension(190, 27));
		jLabel_isbn.setText(isbn);
		String lang = m_book.getM_language();
		jLabel_language = new CustomLabel();
		jLabel_language.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		jLabel_language.setLocation(new Point(140, 200));
		jLabel_language.setSize(new Dimension(190, 27));
		jLabel_language.setText(lang);
		String publisher = m_book.getM_publisher();
		jLabel_publisher = new CustomLabel();
		jLabel_publisher.setLocation(new Point(140, 170));
		jLabel_publisher.setSize(new Dimension(190, 27));
		int size = sized(publisher);	
		jLabel_publisher.setFont(new Font("Eras Light ITC", Font.BOLD, size));
		jLabel_publisher.setText(publisher);
		//
		jLabel_scoreRes = new JLabel();
		jLabel_scoreRes.setText("    1                                                  5");
		jLabel_scoreRes.setLocation(new Point(40, 85));
		jLabel_scoreRes.setSize(new Dimension(200, 10));
		m_jLabel_Price = new JLabel();
		m_jLabel_Price.setBounds(new Rectangle(226, 420, 257, 45));
		m_jLabel_Price.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		m_jLabel_Price.setHorizontalAlignment(SwingConstants.CENTER);
		m_jLabel_Price.setText("Price: " + ((Double)m_book.getM_price()).intValue() +"." + (int)((m_book.getM_price()-((Double)m_book.getM_price()).intValue())*100) + " $USD");
		String title = m_book.getM_title();
		m_jLabel_Title = new CustomLabel();
		m_jLabel_Title.setSize(new Dimension(700, 60));
		m_jLabel_Title.setLocation(new Point(0, 0));
		int sizet = sizedM(title);
		m_jLabel_Title.setFont(new Font("Freestyle Script", Font.PLAIN, sizet));
		m_jLabel_Title.setText("");
		m_jLabel_Title.setHorizontalAlignment(SwingConstants.CENTER);
		m_jLabel_Title.setForeground(Color.blue);		
		m_jLabel_Author = new CustomLabel();
		m_jLabel_Author.setFont(new Font("Freestyle Script", Font.BOLD, (int)(sizet*36/60)));
		m_jLabel_Author.setSize(new Dimension(350, 50));
		m_jLabel_Author.setLocation(new Point(350, 45));
		m_jLabel_Author.setText("");
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
		this.add(jLabel_scoreRes, null);
		this.add(jLabel_publisher, null);
		this.add(jLabel_language, null);
		this.add(jLabel_isbn, null);
		this.add(jLabel_releaseDate, null);
		this.add(jLabel_publish, null);
		this.add(jLabel_lang, null);
		this.add(jLabel_isb, null);
		this.add(jLabel_re_date, null);
		this.add(getJScrollPane_toc(), null);
		this.add(jLabel_toc, null);
		if(AUser.getInstance().getPrivilege() == EActor.User ||
		   AUser.getInstance().getPrivilege() == EActor.Reader )
		{
			this.add(getM_jButton_publishReview(), null);
			this.add(getM_jButton_Purchase_BDP(), null);
			
			
		}
		else if(AUser.getInstance().getPrivilege() == EActor.Librarian)
		{
			this.add(getM_jButton_EditBook(), null);
		}
		else if(AUser.getInstance().getPrivilege() == EActor.LibraryManager)
		{
			this.add(getM_jButton_EditBook(), null);
			this.add(getM_jButton_Report(), null);
		}
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
			this.setLastChoice(EBDDecision.BACK);
			this.setVisible(false);
		}
		else if(source == m_jButton_publishReview)
		{
			this.setLastChoice(EBDDecision.REVIEW);
			this.setVisible(false);
		}
		else if(source == m_jButton_Purchase_BDP)
		{
			this.setLastChoice(EBDDecision.ORDER);
			this.setVisible(false);
		}
		else if(source == m_jButton_EditBook)
		{
			this.setLastChoice(EBDDecision.EDITBOOK);
			this.setVisible(false);
		}
		else if(source == m_jButton_Report)
		{
			this.setLastChoice(EBDDecision.REPORT);
			this.setVisible(false);
		}
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
			m_jTextArea_Summary.setWrapStyleWord(true);
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
			if(AUser.getInstance().getPrivilege() != EActor.Librarian   &&  AUser.getInstance().getPrivilege() != EActor.LibraryManager)
			{
				String ans = ((CReader)AUser.getInstance()).addScore(m_book.getM_ISBN(),score);
				JOptionPane.showMessageDialog(null, ans, "Server Answer :",JOptionPane.INFORMATION_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null, "You have no authorization for this action !", "Message :",JOptionPane.ERROR_MESSAGE);
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
			m_jButton_Purchase_BDP.setText("Purchase");
			m_jButton_Purchase_BDP.setPreferredSize(new Dimension(150, 34));
			m_jButton_Purchase_BDP.addActionListener(this);
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

	/**
	 * This method initializes m_jButton_EditBook	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_EditBook() {
		if (m_jButton_EditBook == null) {
			m_jButton_EditBook = new JButton();
			m_jButton_EditBook.setBounds(new Rectangle(486, 480, 150, 34));
			m_jButton_EditBook.setText("Edit Book");
			m_jButton_EditBook.addActionListener(this);
		}
		return m_jButton_EditBook;
	}

	/**
	 * This method initializes m_jButton_Report	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Report() {
		if (m_jButton_Report == null) {
			m_jButton_Report = new JButton();
			m_jButton_Report.setText("Report");
			m_jButton_Report.setLocation(new Point(62, 420));
			m_jButton_Report.setSize(new Dimension(150, 34));
			m_jButton_Report.addActionListener(this);
		}
		return m_jButton_Report;
	}
	
	
	private int sized(String labelText) {
		Font LFont = jLabel_publisher.getFont();
		double widthRatio = (double)jLabel_publisher.getWidth() / (double)jLabel_publisher.getFontMetrics(LFont).stringWidth(labelText);;
		int newFontSize = (int)(LFont.getSize() * widthRatio);
		int componentHeight = jLabel_publisher.getHeight();		
		int fontSizeToUse = Math.min(newFontSize, componentHeight);
		int newSize = Math.min(fontSizeToUse, 14);
		return newSize;
	}
	
	
	private int sizedM(String labelText) {
		Font LFont = m_jLabel_Title.getFont();
		double widthRatio = (double)m_jLabel_Title.getWidth() / (double)m_jLabel_Title.getFontMetrics(LFont).stringWidth(labelText);;
		int newFontSize = (int)(LFont.getSize() * widthRatio*1.25);
		int componentHeight = m_jLabel_Title.getHeight();		
		int fontSizeToUse = Math.min(newFontSize, componentHeight);
		int newSize = Math.min(fontSizeToUse, 60);
		return newSize;
	}

	/**
	 * This method initializes jScrollPane_toc	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_toc() {
		if (jScrollPane_toc == null) {
			jScrollPane_toc = new JScrollPane();
			jScrollPane_toc.setLocation(new Point(90, 305));
			jScrollPane_toc.setViewportView(getJTextArea_toc());
			jScrollPane_toc.setSize(new Dimension(240, 100));
		}
		return jScrollPane_toc;
	}

	/**
	 * This method initializes jTextArea_toc	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea_toc() {
		if (jTextArea_toc == null) {
			jTextArea_toc = new JTextArea(m_book.getM_TOC());
			jTextArea_toc.setEditable(false);
			jTextArea_toc.setWrapStyleWord(true);
			jTextArea_toc.setLineWrap(true);
			jTextArea_toc.setFont(new Font("Dialog", Font.PLAIN, 12));
			jTextArea_toc.setSize(new Dimension(238, 99));
			jTextArea_toc.setBackground(new Color(238, 238, 238));
		}
		return jTextArea_toc;
	}
	
}  //  @jve:decl-index=0:visual-constraint="20,-195"

