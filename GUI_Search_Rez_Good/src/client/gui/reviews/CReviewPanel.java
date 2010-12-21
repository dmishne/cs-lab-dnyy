package client.gui.reviews;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import common.data.CBookReview;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JSlider;

public class CReviewPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private CBookReview m_review;
	private JTextArea jTextAreaTitle = null;
	private JLabel jLabelTitle = null;
	private JLabel jLabelBy = null;
	private JTextArea jTextAreaAuthor = null;
	private JLabel jLabelCont = null;
	private JTextArea jTextAreaCont = null;
	private JLabel jLabelDate = null;
	private JTextArea jTextAreadate = null;
	private JLabel jLabel = null;
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(12, 8, 43, 23));
		jLabelDate = new JLabel();
		jLabelDate.setBounds(new Rectangle(512, 9, 30, 21));
		jLabelDate.setText("Date:");
		jLabelCont = new JLabel();
		jLabelCont.setBounds(new Rectangle(11, 35, 50, 33));
		jLabelCont.setText("Context:");
		jLabelBy = new JLabel();
		jLabelBy.setBounds(new Rectangle(368, 10, 32, 21));
		jLabelBy.setText("By");
		jLabelTitle = new JLabel();
		jLabelTitle.setBounds(new Rectangle(58, 12, 40, 20));
		jLabelTitle.setText("Title:");
		this.setSize(669, 116);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(600, 100));
		this.add(getJTextAreaTitle(), null);
		this.add(jLabelTitle, null);
		this.add(jLabelBy, null);
		this.add(getJTextAreaAuthor(), null);
		this.add(jLabelCont, null);
		this.add(getJTextAreaCont(), null);
		this.add(jLabelDate, null);
		this.add(getJTextAreadate(), null);
		this.add(jLabel, null);
					
	}
	public CReviewPanel(CBookReview rev,int id)
	{
		super();	
		m_review=rev;
		initialize();
		id++;
		jLabel.setText("#"+id);
	}
	

	public void setReview(CBookReview m_review) {
		this.m_review = m_review;
	}
	public CBookReview getReview() {
		return m_review;
	}

	/**
	 * This method initializes jTextAreaTitle	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaTitle() {
		if (jTextAreaTitle == null) {
			jTextAreaTitle = new JTextArea();
			jTextAreaTitle.setBounds(new Rectangle(93, 10, 268, 22));
			jTextAreaTitle.setText(m_review.gettitle());
			jTextAreaTitle.setBackground(this.getBackground());

		}
		return jTextAreaTitle;
	}

	/**
	 * This method initializes jTextAreaAuthor	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaAuthor() {
		if (jTextAreaAuthor == null) {
			jTextAreaAuthor = new JTextArea();
			jTextAreaAuthor.setBounds(new Rectangle(406, 9, 102, 24));
			jTextAreaAuthor.setText(m_review.getauthor());
			jTextAreaAuthor.setBackground(this.getBackground());
		}
		return jTextAreaAuthor;
	}

	/**
	 * This method initializes jTextAreaCont	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaCont() {
		if (jTextAreaCont == null) {
			jTextAreaCont = new JTextArea();
			jTextAreaCont.setBounds(new Rectangle(63, 34, 587, 65));
			jTextAreaCont.setText(m_review.getreview());
			jTextAreaCont.setBackground(this.getBackground());
		}
		return jTextAreaCont;
	}


	/**
	 * This method initializes jTextAreadate	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreadate() {
		if (jTextAreadate == null) {
			jTextAreadate = new JTextArea();
			jTextAreadate.setBounds(new Rectangle(550, 9, 107, 22));
			jTextAreadate.setText(m_review.getwrite_date());
			jTextAreadate.setBackground(this.getBackground());
		}
		return jTextAreadate;
	}

}  //  @jve:decl-index=0:visual-constraint="11,11"
