package client.gui.searchbook;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import client.core.AUser;

import common.data.CBook;

/**
 * CSearchResultPanel defines the book search result panel.
 */
public class CSearchResultPanel extends JPanel implements ActionListener {

	/**
	 * Defines the available operations that can cause removing this panel
	 */
	public enum SRPDecision
	{
		BACK,DETAILS
	}
	
	private static final long serialVersionUID = 1L;
	private JButton m_jButton_back_SRP = null;
	private JList m_jList_Results_SRP = null;
	private JScrollPane m_JScrollPane_Results_SRP = null;
	
	/**
	 * List of books to represent in the search
	 * result list.
	 */
	private LinkedList<CBook> m_books = null;  			  //  @jve:decl-index=0:
	
	/**
	 * Store the chosen book by the user for
	 * further book details.
	 */
	private static CBook m_chosenBook = null;

	private JLabel m_jLabel_SRP_title = null;
	private JButton m_jButton_ShowDetails_SRP = null;
	
	/**
	 * Saves the last choice of the user
	 */
	private SRPDecision m_lastChoice = SRPDecision.BACK;  //  @jve:decl-index=0:
	
	
	/**
	 * This is the default constructor 
	 */
	public CSearchResultPanel() throws Exception {
		super();
		initialize();
	}

	/**
	 * initialize initializes this class 
     * @throws Exception fail to get parameters
	 */
	private void initialize() throws Exception {
		m_jLabel_SRP_title = new JLabel();
		m_jLabel_SRP_title.setText("Search Results");
		m_jLabel_SRP_title.setLocation(new Point(0, 0));
		m_jLabel_SRP_title.setHorizontalTextPosition(SwingConstants.LEADING);
		m_jLabel_SRP_title.setHorizontalAlignment(SwingConstants.CENTER);
		m_jLabel_SRP_title.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		m_jLabel_SRP_title.setSize(new Dimension(700, 35));
		this.setSize(700, 550);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(700, 550));
		this.add(getM_jButton_back_SRP(), null);
		this.add(getM_JScrollPane_Results_SRP(), null);
		this.add(m_jLabel_SRP_title, null);
		this.add(getM_jButton_ShowDetails_SRP(), null);
	}

	/**
	 * This method initializes m_jButton_back_SRP	
	 * Pressing the button changes the panel and resume to the 
	 * previous panel. 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_back_SRP() {
		if (m_jButton_back_SRP == null) {
			m_jButton_back_SRP = new JButton();
			m_jButton_back_SRP.setPreferredSize(new Dimension(208, 34));
			m_jButton_back_SRP.setSize(new Dimension(208, 34));
			m_jButton_back_SRP.setLocation(new Point(94, 480));
			m_jButton_back_SRP.setText("Back");
			m_jButton_back_SRP.addActionListener(this);
		}
		return m_jButton_back_SRP;
	}

	/**
	 * This method initializes m_jList_Results_SRP	
	 * 	
	 * @return javax.swing.JList	
	 * @throws Exception 
	 */
	private JList getM_jList_Results_SRP() throws Exception {
		if (m_jList_Results_SRP == null) {
			setBooks(AUser.getInstance().searchBook(CSearchBookPanel.getSearchDetails()));
			String []result = new String[m_books.size()];
			int i = 0;
			for( CBook b : m_books )
			{
				result[i] = b.getM_ISBN() + " - " + b.getM_title() + " - " + b.getM_author();
				i++;
			}
			m_jList_Results_SRP = new JList(result);
			m_jList_Results_SRP.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			m_jList_Results_SRP.setSize(new Dimension(600, 400));
			m_jList_Results_SRP.setLocation(new Point(50, 50));
			m_jList_Results_SRP.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			m_jList_Results_SRP.setFont(new Font("Eras Light ITC", Font.BOLD, 24));
		}
		return m_jList_Results_SRP;
	}

	/**
	 * @return the m_JScrollPane_Results_SRP
	 * @throws Exception 
	 */
	public JScrollPane getM_JScrollPane_Results_SRP() throws Exception {
		if(m_JScrollPane_Results_SRP == null)
		{
			m_JScrollPane_Results_SRP = new JScrollPane(getM_jList_Results_SRP());
			m_JScrollPane_Results_SRP.setSize(new Dimension(600, 400));
			m_JScrollPane_Results_SRP.setLocation(new Point(50, 50));
			
		}
		return m_JScrollPane_Results_SRP;
	}

	/**
	 * actionPerformed handle responsible for action performed.
     * @param ae ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jButton_back_SRP)
		{
			this.m_lastChoice = SRPDecision.BACK;
			this.setVisible(false);
		}
		if(source == m_jButton_ShowDetails_SRP)
		{
			try{
				String res = (String)m_jList_Results_SRP.getSelectedValue();
				String[] splitedRes = res.split(" - ");
				Iterator<CBook> it = m_books.iterator();
				while(it.hasNext())
				{
					CBook temp = it.next();
					if(temp.getM_ISBN().compareTo(splitedRes[0]) == 0)
					{
						m_chosenBook = temp;
						break;
					}
				}
				this.m_lastChoice = SRPDecision.DETAILS;
				this.setVisible(false);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Please choose a book" ,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * @param m_lastChoice the m_lastChoice to set
	 */
	public void setLastChoice(SRPDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	/**
	 * @return the m_chosenBook
	 */
	public static CBook getChosenBook() {
		return m_chosenBook;
	}

	/**
	 * @return the m_lastChoice
	 */
	public SRPDecision getLastChoice() {
		return m_lastChoice;
	}

	/**
	 * @param m_books the m_books to set
	 */
	public void setBooks(LinkedList<CBook> m_books) {
		this.m_books = m_books;
	}

	/**
	 * @return the m_books
	 */
	public LinkedList<CBook> getBooks() {
		return m_books;
	}

	/**
	 * This method initializes m_jButton_ShowDetails_SRP	
	 * Pressing this button cause to show extra book details of the
	 * selected book.	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_ShowDetails_SRP() {
		if (m_jButton_ShowDetails_SRP == null) {
			m_jButton_ShowDetails_SRP = new JButton();
			m_jButton_ShowDetails_SRP.setBounds(new Rectangle(396, 481, 208, 34));
			m_jButton_ShowDetails_SRP.setText("Show Details");
			m_jButton_ShowDetails_SRP.addActionListener(this);
		}
		return m_jButton_ShowDetails_SRP;
	}

}
