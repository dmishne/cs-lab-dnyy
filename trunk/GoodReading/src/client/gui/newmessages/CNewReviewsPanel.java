package client.gui.newmessages;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import client.core.*;
import common.data.*;

/**
 * CNewReviewsPanel defines the New Reviews Panel.
 */
public class CNewReviewsPanel extends JPanel implements ActionListener{

	/**
	 * Defines the available operations that can cause removing this panel
	 */
	public enum NRPDecision
	{
		BACK,SHOWMESSAGE
	}

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane_NewReviewsScrolPane = null;
	private JLabel jLabel_menuLabel_NR = null;
	private JButton jButton_back_NR = null;
	private JButton jButton_ShowReview_NR = null;
	private JList jList_NewReviewsList = null;
	/**
	 * Saves the last choice of the user
	 */
	private NRPDecision m_lastChoice = null;
	/**
	 * Save list of new BookReviews
	 */
	private LinkedList<CBookReview> m_messages = null;  //  @jve:decl-index=0:
	/**
	 * Chosen book review to represent.
	 */
	private static CBookReview m_chosen_message = null;
	
	
	/**
	 * CNewReviewsPanel is the default constructor
	 */
	public CNewReviewsPanel() {
		super();
		initialize();
	}

	/**
	 * initialize initializes this class
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel_menuLabel_NR = new JLabel();
		jLabel_menuLabel_NR.setText("New Reviews Result");
		jLabel_menuLabel_NR.setSize(new Dimension(700, 35));
		jLabel_menuLabel_NR.setLocation(new Point(0, 0));
		jLabel_menuLabel_NR.setHorizontalTextPosition(SwingConstants.LEADING);
		jLabel_menuLabel_NR.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_menuLabel_NR.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		this.setSize(700, 550);
		this.setLayout(null);
		try {
			this.add(getJScrollPane_NewReviewsScrolPane(), null);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
		}
		this.add(jLabel_menuLabel_NR, null);
		this.add(getJButton_back_NR(), null);
		this.add(getJButton_ShowReview_NR(), null);
	}

	
	
	/**
	 * @param m_lastChoice the m_lastChoice to set
	 */
	public void setLastChoice(NRPDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	/**
	 * @return the m_lastChoice
	 */
	public NRPDecision getLastChoice() {
		return m_lastChoice;
	}

	
	/**
	 * @return the m_chosen_message
	 */
	public static CBookReview getChosen_message() {
		return m_chosen_message;
	}

	/**
	 * @param m_chosen_message the m_chosen_message to set
	 */
	public static void setChosen_message(CBookReview m_chosen_message) {
		CNewReviewsPanel.m_chosen_message = m_chosen_message;
	}

	/**
	 * This method initializes jScrollPane_NewReviewsScrolPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 * @throws Exception 
	 */
	private JScrollPane getJScrollPane_NewReviewsScrolPane() throws Exception {
		if (jScrollPane_NewReviewsScrolPane == null) {
			jScrollPane_NewReviewsScrolPane = new JScrollPane();
			jScrollPane_NewReviewsScrolPane.setSize(new Dimension(600, 400));
			jScrollPane_NewReviewsScrolPane.setViewportView(getJList_NewReviewsList());
			jScrollPane_NewReviewsScrolPane.setLocation(new Point(50, 50));
			jScrollPane_NewReviewsScrolPane.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		return jScrollPane_NewReviewsScrolPane;
	}

	/**
	 * This method initializes jButton_back_NR	
	 * Pressing on this button will return to the Main Menu.	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_back_NR() {
		if (jButton_back_NR == null) {
			jButton_back_NR = new JButton();
			jButton_back_NR.setText("Back");
			jButton_back_NR.setSize(new Dimension(208, 34));
			jButton_back_NR.setLocation(new Point(94, 480));
			jButton_back_NR.addActionListener(this);
		}
		return jButton_back_NR;
	}

	/**
	 * This method initializes jButton_ShowReview_NR	
	 * Pressing on this button will change panels and show the chosen review,
	 * unless no review has been chosen.
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_ShowReview_NR() {
		if (jButton_ShowReview_NR == null) {
			jButton_ShowReview_NR = new JButton();
			jButton_ShowReview_NR.setText("Show Review");
			jButton_ShowReview_NR.setSize(new Dimension(208, 34));
			jButton_ShowReview_NR.setLocation(new Point(396, 480));
			jButton_ShowReview_NR.addActionListener(this);
		}
		return jButton_ShowReview_NR;
	}

	/**
	 * This method initializes jList_NewReviewsList	
	 * @return javax.swing.JList	
	 * @throws Exception 
	 */
	private JList getJList_NewReviewsList() throws Exception {
		if (jList_NewReviewsList == null) {
				m_messages = ((CLibrarian)AUser.getInstance()).searchNewReviews();
				if(m_messages.isEmpty())
				{
				    JOptionPane.showMessageDialog(null, "No new reviews!" ,"Message :",JOptionPane.INFORMATION_MESSAGE);
				    jList_NewReviewsList = new JList();
				}
				else
				{
					String[] show = new String[m_messages.size()];
					int i = 0;
					for( CBookReview b : m_messages )
					{
						String date = b.getNormalWriteDate();
						date = date.replace("00:00:00.0", "");
						date = date.replace("-", ".");
						show[i] = b.getisbn() + " - " + b.gettitle() + " - " + b.getauthor() + " - " + date;
						i++;
					} 
				    jList_NewReviewsList = new JList(show);
				    jList_NewReviewsList.setFont(new Font("Eras Light ITC", Font.BOLD, 24));
			   }
		}
		return jList_NewReviewsList;
	}

	/**
	 * actionPerformed handle responsible for action performed.
     * @param ae ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == jButton_back_NR)
		{
			this.m_lastChoice = NRPDecision.BACK;
			this.setVisible(false);
		}
		if(source == jButton_ShowReview_NR)
		{
			try{
				String res = (String)jList_NewReviewsList.getSelectedValue();
				String[] splitedRes = res.split(" - ");
				Iterator<CBookReview> it = m_messages.iterator();
				while(it.hasNext())
				{
					CBookReview temp = it.next();
					if(temp.getisbn().compareTo(splitedRes[0]) == 0)
					{
						m_chosen_message = temp;
						break;
					}
				}
				this.m_lastChoice = NRPDecision.SHOWMESSAGE;
				this.setVisible(false);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Please choose review" ,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	/**
	 * @return the m_messages
	 */
	public LinkedList<CBookReview> getm_Messages() {
		return m_messages;
	}
	
}
