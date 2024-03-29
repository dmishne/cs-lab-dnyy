package client.gui.searchreview;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Point;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import common.data.CBookReview;
import java.awt.ComponentOrientation;

/**
 *This panel designed to present the list of founded reviews, 
 *and to give the ability to choose one from the list
 */

public class CReviewsListPanel extends JPanel implements ActionListener{

	/**
	 * Defines the available operations that can cause removing this panel
	 */
	public enum RLPDecision
	{
		BACK,SHOWREVIEW
	}
	
	private static final long serialVersionUID = 1L;
	private JLabel jLabel_mainLabel_RL = null;
	private JScrollPane jScrollPane_resultList_RL = null;
	private JList jList_resultList_RL = null;
	private JButton jButton_Back_RL = null;
	private JButton jButton_showReview_RL = null;
	/** Saves the chosen review */
	private static CBookReview m_chosenReview = null;
	private LinkedList<CBookReview> m_reviewsList = null;  //  @jve:decl-index=0:
	/** Saves the last choice of the user */
	private RLPDecision m_lastChoice = null;
	
	/**
	 * This is the default constructor
	 */
	public CReviewsListPanel() {
		super();
		m_reviewsList = CSearchReviewPanel.getReviewsList();
		initialize();
	}

	/**
	 * This method initializes this class
	 */
	private void initialize() {
		jLabel_mainLabel_RL = new JLabel();
		jLabel_mainLabel_RL.setText("Search Review Result");
		jLabel_mainLabel_RL.setSize(new Dimension(700, 35));
		jLabel_mainLabel_RL.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_mainLabel_RL.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		jLabel_mainLabel_RL.setLocation(new Point(0, 0));
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(jLabel_mainLabel_RL, null);
		this.add(getJScrollPane_resultList_RL(), null);
		this.add(getJButton_Back_RL(), null);
		this.add(getJButton_showReview_RL(), null);
	}

	/**
	 * Get the review that was chosen from the list
	 * @return the m_chosenReview
	 */
	public static CBookReview getChosenReview() {
		return m_chosenReview;
	}

	/**
	 * Set the review that was chosen from the list
	 * @param m_chosenReview the m_chosenReview to set
	 */
	public static void setChosenReview(CBookReview m_chosenReview) {
		CReviewsListPanel.m_chosenReview = m_chosenReview;
	}

	/**
	 * Get the last button that pressed on this panel.
	 * @return the m_lastChoice
	 */
	public RLPDecision getLastChoice() {
		return m_lastChoice;
	}

	/**
	 * Set the last button that pressed on this panel.
	 * @param m_lastChoice the m_lastChoice to set
	 */
	public void setLastChoice(RLPDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	/**
	 * This method initializes jScrollPane_resultList_RL.
	 * Contains jList_resultList_RL.	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_resultList_RL() {
		if (jScrollPane_resultList_RL == null) {
			jScrollPane_resultList_RL = new JScrollPane();
			jScrollPane_resultList_RL.setLocation(new Point(50, 50));
			jScrollPane_resultList_RL.setViewportView(getJList_resultList_RL());
			jScrollPane_resultList_RL.setSize(new Dimension(600, 400));
			jScrollPane_resultList_RL.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		return jScrollPane_resultList_RL;
	}

	/**
	 * This method initializes jList_resultList_RL	with founded reviews list	
	 * 
	 * @return javax.swing.JList	
	 */
	private JList getJList_resultList_RL() {
		if (jList_resultList_RL == null) {
			String []result = new String[m_reviewsList.size()];
			int i = 0;
			for(CBookReview cb : m_reviewsList)
			{
				String date = cb.getNormalWriteDate();
				date = date.replace("00:00:00.0", "");
				date = date.replace("-", ".");
				result[i] = cb.gettitle() + " - " + cb.getauthor() + " - " + cb.getisbn() + " - " + date;
			    i++;
			}
			jList_resultList_RL = new JList(result);
			jList_resultList_RL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jList_resultList_RL.setLocation(new Point(50, 50));
			jList_resultList_RL.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jList_resultList_RL.setSize(new Dimension(600, 400));
			jList_resultList_RL.setFont(new Font("Eras Light ITC", Font.BOLD, 24));
		}
		return jList_resultList_RL;
	}

	/**
	 * This method initializes jButton_Back_RL.
	 * Return to previous panel.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Back_RL() {
		if (jButton_Back_RL == null) {
			jButton_Back_RL = new JButton();
			jButton_Back_RL.setText("Back");
			jButton_Back_RL.setSize(new Dimension(208, 34));
			jButton_Back_RL.setLocation(new Point(94, 480));
			jButton_Back_RL.addActionListener(this);
		}
		return jButton_Back_RL;
	}

	/**
	 * This method initializes jButton_showReview_RL.
	 * Press this button to open the panel that shows the chosen review. 	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_showReview_RL() {
		if (jButton_showReview_RL == null) {
			jButton_showReview_RL = new JButton();
			jButton_showReview_RL.setText("Show Review");
			jButton_showReview_RL.setSize(new Dimension(208, 34));
			jButton_showReview_RL.setLocation(new Point(396, 480));
			jButton_showReview_RL.addActionListener(this);
		}
		return jButton_showReview_RL;
	}

	/**
	 *  Handle the catched action on the panel. 
	 *  
	 *  @param ae ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == jButton_Back_RL)
		{
			this.m_lastChoice = RLPDecision.BACK;
			this.setVisible(false);
		}
		if(source == jButton_showReview_RL)
		{
			try
			{
				String res = (String)jList_resultList_RL.getSelectedValue();
				String[] splitedRes = res.split(" - ");
				splitedRes[3] = splitedRes[3].replace(".", "-");
				Iterator<CBookReview> it = m_reviewsList.iterator();
				while(it.hasNext())
				{
					CBookReview temp = it.next();
					if(temp.gettitle().compareTo(splitedRes[0]) == 0 && temp.getauthor().compareTo(splitedRes[1]) == 0 && temp.getisbn().compareTo(splitedRes[2]) == 0 && temp.getNormalWriteDate().compareTo(splitedRes[3]) == 0)
					{
						m_chosenReview = temp;
						break;
					}
				}
				this.m_lastChoice = RLPDecision.SHOWREVIEW;
				this.setVisible(false);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Please choose a review" ,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

}
