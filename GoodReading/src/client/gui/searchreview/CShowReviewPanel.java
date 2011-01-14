package client.gui.searchreview;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Point;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import common.data.CBookReview;
import client.core.*;
import client.gui.newmessages.CNewReviewsPanel;


public class CShowReviewPanel extends JPanel implements ActionListener{

	/**
	 * Defines the available operations that can cause removing this panel
	 */
	public enum SRPDecision
	{
		BACK,DELETEREVIEW,SAVE
	}
	
	public enum SRPFrom
	{
		RLP,NRP
	}
	
	private static final long serialVersionUID = 1L;
	private JLabel jLabel_mainLabel_SR = null;
	private JLabel jLabel_title_SR = null;
	private JLabel jLabel_reviewAuthor_SR = null;
	private JTextField jTextField_title_SR = null;
	private JTextField jTextField_reviewAuthor_SR = null;
	private JLabel jLabel_bookName_SR = null;
	private JTextField jTextField_bookName_SR = null;
	private JScrollPane jScrollPane_revie_SR = null;
	private JButton jButton_Back_SR = null;
	private JButton jButton_Save_SR = null;
	private JLabel jLabel_confirm_SR = null;
	private JCheckBox jCheckBox_confirm_SR = null;
	private JTextArea jTextArea_review_SR = null;
	private JButton jButton_editReview_SR = null;
	private JLabel jLabel = null;
	private AUser user = null;
	private boolean E_flag = false;
	/**
	 * Saves the last choice of the user
	 */
	private SRPDecision m_lastChoice = null;
	private SRPFrom m_fromWhere = null;
	private CBookReview review = null;
	private JButton jButton_DeleteReview_SR = null;  
	private String m_from;

	
	/**
	 * @return the m_fromWhere
	 */
	public SRPFrom getFromWhere() {
		return m_fromWhere;
	}

	/**
	 * This is the default constructor
	 */
	public CShowReviewPanel(String from) {
		super();
		m_from = from;
		try {
			user = AUser.getInstance();
			if(from.compareTo("RLP") == 0)
			{
				this.setReview(CReviewsListPanel.getChosenReview());
			}
			else if(from.compareTo("NRP") == 0)
			{
				this.setReview(CNewReviewsPanel.getChosen_message());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel = new JLabel();
		jLabel.setText("of Review");
		jLabel.setLocation(new Point(20, 107));
		jLabel.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel.setSize(new Dimension(70, 13));	
		jLabel_confirm_SR = new JLabel();
		jLabel_confirm_SR.setBounds(new Rectangle(24, 433, 106, 26));
		jLabel_confirm_SR.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_confirm_SR.setText("Confirm");
		jLabel_confirm_SR.setVisible(false);
		if(user.getPrivilege() == EActor.Librarian || user.getPrivilege() == EActor.LibraryManager)
		{
			jLabel_confirm_SR.setVisible(true);
		}
		jLabel_bookName_SR = new JLabel();
		jLabel_bookName_SR.setText("Book ");
		jLabel_bookName_SR.setSize(new Dimension(70, 26));
		jLabel_bookName_SR.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_bookName_SR.setLocation(new Point(20, 123));
		jLabel_reviewAuthor_SR = new JLabel();
		jLabel_reviewAuthor_SR.setText("Author");
		jLabel_reviewAuthor_SR.setLocation(new Point(20, 94));
		jLabel_reviewAuthor_SR.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_reviewAuthor_SR.setPreferredSize(new Dimension(42, 13));
		jLabel_reviewAuthor_SR.setSize(new Dimension(70, 13));
		jLabel_title_SR = new JLabel();
		jLabel_title_SR.setText("Title");
		jLabel_title_SR.setLocation(new Point(20, 65));
		jLabel_title_SR.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_title_SR.setSize(new Dimension(70, 26));
		jLabel_mainLabel_SR = new JLabel();
		jLabel_mainLabel_SR.setText("Review");
		jLabel_mainLabel_SR.setSize(new Dimension(700, 35));
		jLabel_mainLabel_SR.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_mainLabel_SR.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		jLabel_mainLabel_SR.setLocation(new Point(0, 0));
		this.setSize(700, 550);
		this.setLayout(null);
		this.setEnabled(false);
		this.add(jLabel_mainLabel_SR, null);
		this.add(jLabel_title_SR, null);
		this.add(jLabel_reviewAuthor_SR, null);
		this.add(getJTextField_title_SR(), null);
		this.add(getJTextField_reviewAuthor_SR(), null);
		this.add(jLabel_bookName_SR, null);
		this.add(getjTextField_bookName_SR(), null);
		this.add(getJScrollPane_revie_SR(), null);
		this.add(getJButton_Back_SR(), null);
		this.add(getJButton_Save_SR(), null);
		this.add(jLabel_confirm_SR, null);
		this.add(getJCheckBox_confirm_SR(), null);
		this.add(getJButton_editReview_SR(), null);
		this.add(jLabel, null);
		this.add(getJButton_DeleteReview_SR(), null);
	}
	
	
	/**
	 * @param m_lastChoice the m_lastChoice to set
	 */
	public void setLastChoice(SRPDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	/**
	 * @return the m_lastChoice
	 */
	public SRPDecision getLastChoice() {
		return m_lastChoice;
	}


	/**
	 * @return the review
	 */
	public CBookReview getReview() {
		return review;
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(CBookReview review) {
		this.review = review;
	}

	/**
	 * This method initializes jTextField_title_SR	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_title_SR() {
		if (jTextField_title_SR == null) {
			jTextField_title_SR = new JTextField(this.getReview().gettitle());
			jTextField_title_SR.setSize(new Dimension(580, 27));
			jTextField_title_SR.setLocation(new Point(100, 65));
			jTextField_title_SR.setEditable(false);
			jTextField_title_SR.setVisible(true);
		}
		return jTextField_title_SR;
	}

	/**
	 * This method initializes jTextField_reviewAuthor_SR	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_reviewAuthor_SR() {
		if (jTextField_reviewAuthor_SR == null) {
			jTextField_reviewAuthor_SR = new JTextField(this.getReview().getauthor());
			jTextField_reviewAuthor_SR.setSize(new Dimension(580, 27));
			jTextField_reviewAuthor_SR.setEditable(false);
			jTextField_reviewAuthor_SR.setLocation(new Point(100, 95));
		}
		return jTextField_reviewAuthor_SR;
	}

	/**
	 * This method initializes jTextField_bookName_SR	
	 * 	
	 * @return javax.swing.JTextField	
	 */

	private JTextField getjTextField_bookName_SR() {
		if (jTextField_bookName_SR == null) {
			jTextField_bookName_SR = new JTextField(this.getReview().getBookName());
			jTextField_bookName_SR.setSize(new Dimension(580, 27));
			jTextField_bookName_SR.setEditable(false);
			jTextField_bookName_SR.setLocation(new Point(100, 124));
		}
		return jTextField_bookName_SR;
	}

	/**
	 * This method initializes jScrollPane_revie_SR	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_revie_SR() {
		if (jScrollPane_revie_SR == null) {
			jScrollPane_revie_SR = new JScrollPane();
			jScrollPane_revie_SR.setLocation(new Point(20, 160));
			jScrollPane_revie_SR.setViewportView(getJTextArea_review_SR());
			jScrollPane_revie_SR.setSize(new Dimension(660, 253));
			jScrollPane_revie_SR.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		return jScrollPane_revie_SR;
	}

	/**
	 * This method initializes jButton_Back_SR	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Back_SR() {
		if (jButton_Back_SR == null) {
			jButton_Back_SR = new JButton();
			jButton_Back_SR.setText("Back");
			jButton_Back_SR.setSize(new Dimension(150, 34));
			jButton_Back_SR.setLocation(new Point(62, 480));
			jButton_Back_SR.addActionListener(this);
		}
		return jButton_Back_SR;
	}

	/**
	 * This method initializes jButton_Save_SR	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Save_SR() {
		if (jButton_Save_SR == null) {
			jButton_Save_SR = new JButton();
			jButton_Save_SR.setText("Save");
			jButton_Save_SR.setSize(new Dimension(150, 34));
			jButton_Save_SR.setLocation(new Point(486, 480));
			jButton_Save_SR.setVisible(false);
			jButton_Save_SR.addActionListener(this);
			if(user.getPrivilege() == EActor.Librarian || user.getPrivilege() == EActor.LibraryManager)
			{
				jButton_Save_SR.setVisible(true);
			}
		}
		return jButton_Save_SR;
	}

	/**
	 * This method initializes jCheckBox_confirm_SR	
	 * 	
	 * @return javax.swing.JCheckBox	
	 * @throws Exception 
	 */
	private JCheckBox getJCheckBox_confirm_SR() {
		if (jCheckBox_confirm_SR == null) {
			jCheckBox_confirm_SR = new JCheckBox();
			jCheckBox_confirm_SR.setBounds(new Rectangle(98, 438, 18, 14));
			jCheckBox_confirm_SR.setEnabled(false);	
			if(review.getaccepted() == 1)
				jCheckBox_confirm_SR.setSelected(true);
			else
				jCheckBox_confirm_SR.setSelected(false);
			jCheckBox_confirm_SR.setVisible(false);
			if(user.getPrivilege() == EActor.Librarian || user.getPrivilege() == EActor.LibraryManager)
			{
				jCheckBox_confirm_SR.setVisible(true);
			}
		}
		return jCheckBox_confirm_SR;
	}

	/**
	 * This method initializes jTextArea_review_SR	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea_review_SR() {
		if (jTextArea_review_SR == null) {
			jTextArea_review_SR = new JTextArea(this.getReview().getreview());
			jTextArea_review_SR.setEditable(false);
			jTextArea_review_SR.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			jTextArea_review_SR.setWrapStyleWord(true);
		}
		return jTextArea_review_SR;
	}
	
	
	/**
	 * This method initializes jButton_DeleteReview_SR	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_DeleteReview_SR() {
		if (jButton_DeleteReview_SR == null) {
			jButton_DeleteReview_SR = new JButton();
			jButton_DeleteReview_SR.setText("Delete Review");
			jButton_DeleteReview_SR.setSize(new Dimension(150, 34));
			jButton_DeleteReview_SR.setLocation(new Point(485, 430));
			jButton_DeleteReview_SR.setVisible(false);
			if(user.getPrivilege() == EActor.Librarian || user.getPrivilege() == EActor.LibraryManager)
			{
				jButton_DeleteReview_SR.setVisible(true);
			}
			jButton_DeleteReview_SR.addActionListener(this);
		}
		return jButton_DeleteReview_SR;
	}

	/**
	 * This method initializes jButton_editReview_SR	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_editReview_SR() {
		if (jButton_editReview_SR == null) {
			jButton_editReview_SR = new JButton();
			jButton_editReview_SR.setText("Edit");
			jButton_editReview_SR.setSize(new Dimension(150, 34));
			jButton_editReview_SR.setLocation(new Point(274, 479));
			jButton_editReview_SR.setVisible(false);
			jButton_editReview_SR.addActionListener(this);
			if(user.getPrivilege() == EActor.Librarian || user.getPrivilege() == EActor.LibraryManager)
			{
				jButton_editReview_SR.setVisible(true);
			}
		}
		return jButton_editReview_SR;
	}

	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(m_from.compareTo("NRP") == 0)
		{
			m_fromWhere = SRPFrom.NRP;
		}
		else
		{
			m_fromWhere = SRPFrom.RLP;
		}
		if(source == jButton_Back_SR)
		{	
			setLastChoice(SRPDecision.BACK);
			this.setVisible(false);
		}
		if(source == jButton_editReview_SR)
		{
			if(!E_flag)
			{
			   jTextArea_review_SR.setEditable(true);
			   jTextField_title_SR.setEditable(true);
			   jCheckBox_confirm_SR.setEnabled(true);
			   E_flag = true;
			   
			}
			else if (E_flag)
			{
			   jTextArea_review_SR.setEditable(false);
			   jTextField_title_SR.setEditable(false);
			   jCheckBox_confirm_SR.setEnabled(false);
			   E_flag = false;
			}
		}
		if(source == jButton_DeleteReview_SR)
		{
			try {
				setLastChoice(SRPDecision.DELETEREVIEW);
				((CLibrarian)AUser.getInstance()).deleteReview(this.getReview().gettitle(),this.getReview().getauthor() , this.getReview().getisbn());
				JOptionPane.showMessageDialog(null, "Review Deleted!" ,"Ok",JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
			} catch (Exception e) {
				System.out.println("client.core.CLibrarian.deleteReview Exception");
			}
		}
		if(source == jButton_Save_SR)
		{
			try {
				((CLibrarian)AUser.getInstance()).updateReview(review.getisbn(), jTextField_reviewAuthor_SR.getText(), jTextField_title_SR.getText(), this.getReview().gettitle(), jTextArea_review_SR.getText(),jCheckBox_confirm_SR.isSelected());
				JOptionPane.showMessageDialog(null, "Review updated!" ,"Ok",JOptionPane.INFORMATION_MESSAGE);
			    this.setLastChoice(SRPDecision.SAVE);
			    this.setVisible(false);
			} catch (Exception e) {
				System.out.println("client.core.CLibrarian.updateReview Exception");
				
			}
		}
	}

	

	
}
