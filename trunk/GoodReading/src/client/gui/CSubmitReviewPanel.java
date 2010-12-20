package client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Rectangle;

public class CSubmitReviewPanel extends JPanel implements ActionListener, MouseListener  {

	private static final long serialVersionUID = 1L;
	private ESRDecision m_lastChoice = null;
	private JTextField title = null;
	private JTextArea review = null;
	private JLabel jtitle1 = null;
	private JButton JButton_back = null;
	private JButton JButton_reset = null;
	private JButton JButton_submit = null;
	private JLabel Jlabel_label = null;
	private JScrollPane spfText = null;
	
	static private HashMap<String,String> m_newReview = null;
	
	public enum ESRDecision
	{
		BACK,RESET,SUBMIT
	}
	

	/**
	 * This is the default constructor
	 */
	public CSubmitReviewPanel() {
		super();
		initialize();
	}


	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		Jlabel_label = new JLabel();
		Jlabel_label.setFont(new Font("Freestyle Script", Font.BOLD, 48));
		Jlabel_label.setLocation(new Point(236, 14));
		Jlabel_label.setSize(new Dimension(264, 44));
		Jlabel_label.setPreferredSize(new Dimension(263, 57));
		Jlabel_label.setHorizontalAlignment(SwingConstants.CENTER);
		Jlabel_label.setText("review submition");
		jtitle1 = new JLabel();
		jtitle1.setText("Title :");
		jtitle1.setSize(new Dimension(59, 40));
		jtitle1.setLocation(new Point(38, 73));
		jtitle1.setPreferredSize(new Dimension(70, 21));
		jtitle1.setFont(new Font("Eras Light ITC", Font.BOLD, 18));
		this.setSize(700, 600);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(700, 600));
		this.add(getTitle(), null);
		this.add(jtitle1, null);		
		this.add(getBack(), null);
		this.add(getReset(), null);
		this.add(getSubmit(), null);
		this.add(Jlabel_label, null);
		this.add(getReview(), null);
		spfText = new JScrollPane(review);
		spfText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spfText.setBounds(new Rectangle(42, 135, 620, 343));
		this.add(spfText, null);
	}
	
	public void setLastChoice(ESRDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}
	
	public ESRDecision getLastChoice() {
		return m_lastChoice;
	}

	/**
	 * This method initializes title	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTitle() {
		if (title == null) {
			title = new JTextField();
			title.setBorder(BorderFactory.createLineBorder(Color.black));
			title.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			title.setBounds(new Rectangle(100, 81, 560, 25));
			title.setForeground(Color.gray);
			title.setText(" Add Name to your review");
			title.addMouseListener(this);
		}
		return title;
	}

	/**
	 * This method initializes review	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getReview() {
		if (review == null) {
			review = new JTextArea();
			review.setForeground(Color.gray);
			review.setText(" Add your review here");
			review.setLineWrap(true);
			review.setBorder(BorderFactory.createLineBorder(Color.black));
			review.setWrapStyleWord(true);
			review.setSize(new Dimension(606, 19));
			review.setLocation(new Point(0, 0));
			review.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			review.addMouseListener(this);
		}
		return review;
	}

	/**
	 * This method initializes back	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBack() {
		if (JButton_back == null) {
			JButton_back = new JButton();
			JButton_back.setSize(new Dimension(150, 34));
			JButton_back.setPreferredSize(new Dimension(73, 27));
			JButton_back.setText("Back");
			JButton_back.setLocation(new Point(60, 510));
			JButton_back.addActionListener(this);
		}
		return JButton_back;
	}

	/**
	 * This method initializes reset	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getReset() {
		if (JButton_reset == null) {
			JButton_reset = new JButton();
			JButton_reset.setLocation(new Point(275, 510));
			JButton_reset.setText("Reset");
			JButton_reset.setSize(new Dimension(150, 34));
			JButton_reset.addActionListener(this);
		}
		return JButton_reset;
	}

	/**
	 * This method initializes submit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSubmit() {
		if (JButton_submit == null) {
			JButton_submit = new JButton();
			JButton_submit.setLocation(new Point(485, 510));
			JButton_submit.setText("Submit");
			JButton_submit.setSize(new Dimension(150, 34));
			JButton_submit.addActionListener(this);
		}
		return JButton_submit;
	}

	
	/**
	 * @return the m_newReview
	 */
	public static HashMap<String, String> getM_newReview() {
		return m_newReview;
	}

	public void actionPerformed(ActionEvent ae)  {
		Object source = ae.getSource();
		if(source == JButton_back)
		{
			setLastChoice(ESRDecision.BACK);
			this.setVisible(false);
		}
		else if(source == JButton_reset)
		{
			review.setForeground(Color.gray);
			review.setText(" Add your review here");
			title.setForeground(Color.gray);
			title.setText(" Add Name to your review");
		}
		else if(source == JButton_submit)
		{
			String store_review;
			String store_title;
			m_newReview = new HashMap<String,String>();
			store_review = review.getText();
			store_title = title.getText();
			if(store_title.isEmpty() || store_title.compareTo(" Add Name to your review") == 0)
			{
				   m_newReview.put("title", " ");
		           this.setLastChoice(ESRDecision.SUBMIT);
			}
			else
			       m_newReview.put("title", title.getText());
			if(store_review.isEmpty() || store_review.compareTo("Add your review here") == 0)
			{
				   m_newReview.put("review", " ");
		           this.setLastChoice(ESRDecision.SUBMIT);
			}
			else
				m_newReview.put("review", review.getText());
	            this.setLastChoice(ESRDecision.SUBMIT);
				
			
			this.setVisible(false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getSource() == review)
		{
			if(review.getText().compareTo(" Add your review here") == 0)
			{
		                 review.setText("");
		                 review.setForeground(Color.black);
			}
		}
		if(me.getSource() == title)
		{
			if(title.getText().compareTo(" Add Name to your review") == 0)
			{
				      title.setText("");
				      title.setForeground(Color.black);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}  //  @jve:decl-index=0:visual-constraint="-22,-36"
