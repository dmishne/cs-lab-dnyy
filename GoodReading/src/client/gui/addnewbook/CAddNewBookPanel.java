package client.gui.addnewbook;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Point;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import client.core.*;

public class CAddNewBookPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private ANBDecision m_lastChoice;
	private JLabel jLabel_Title = null;
	private JLabel jLabel_Auhtor = null;
	private JLabel jLabel_ISBN = null;
	private JLabel jLabel_Language = null;
	private JLabel jLabel_Topic = null;
	private JLabel jLabel_ReleaseDate = null;
	private JLabel jLabel_Publisher = null;
	private JLabel jLabel_Price = null;
	private JTextField jTextField_isbn = null;
	private JTextField jTextField_title = null;
	private JTextField jTextField_author = null;
	private JTextField jTextField_lang = null;
	private JTextField jTextField_topic = null;
	private JTextField jTextField_r_date = null;
	private JTextField jTextField_publisher = null;
	private JTextField jTextField_price = null;
	private JScrollPane jScrollPane_summary = null;
	private JTextArea jTextArea_summary = null;
	private JScrollPane jScrollPane_TOC = null;
	private JTextArea jTextArea_toc = null;
	private JLabel jLabel_visibility = null;
	private JCheckBox jCheckBox_visibilityCheck = null;
	private JButton jButton_back = null;
	private JButton jButton_AddBook = null;
	private JLabel jLabel_MainLabel = null;
	private JLabel jLabel_summary = null;
	private JLabel jLabel_toc = null;
	private JLabel jLabel_label = null;
	private JTextField jTextField_label = null;
	
	
	public enum ANBDecision
	{
		BACK,ADDBOOK
	}
	
	
	/**
	 * This is the default constructor
	 */
	public CAddNewBookPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel_label = new JLabel();
		jLabel_label.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_label.setSize(new Dimension(90, 20));
		jLabel_label.setLocation(new Point(20, 264));
		jLabel_label.setText("Labels");
		jLabel_toc = new JLabel();
		jLabel_toc.setText("Table Of Contents");
		jLabel_toc.setLocation(new Point(482, 155));
		jLabel_toc.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		jLabel_toc.setSize(new Dimension(140, 22));
		jLabel_summary = new JLabel();
		jLabel_summary.setBounds(new Rectangle(67, 330, 91, 22));
		jLabel_summary.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		jLabel_summary.setText("Summary");
		jLabel_MainLabel = new JLabel();
		jLabel_MainLabel.setText("Add New Book");
		jLabel_MainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_MainLabel.setFont(new Font("Freestyle Script", Font.BOLD, 48));
		jLabel_MainLabel.setLocation(new Point(0, 15));
		jLabel_MainLabel.setSize(new Dimension(700, 75));
		jLabel_MainLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		jLabel_visibility = new JLabel();
		jLabel_visibility.setText("    Visible");
		jLabel_visibility.setBorder(BorderFactory.createLineBorder(Color.black));
		jLabel_visibility.setLocation(new Point(477, 110));
		jLabel_visibility.setFont(new Font("Eras Light ITC", Font.BOLD, 16));
		jLabel_visibility.setSize(new Dimension(146, 35));
		jLabel_Price = new JLabel();
		jLabel_Price.setText("Price  :");
		jLabel_Price.setLocation(new Point(20, 286));
		jLabel_Price.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Price.setSize(new Dimension(90, 20));
		jLabel_Publisher = new JLabel();
		jLabel_Publisher.setText("Publisher  :");
		jLabel_Publisher.setLocation(new Point(21, 242));
		jLabel_Publisher.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Publisher.setSize(new Dimension(90, 20));
		jLabel_ReleaseDate = new JLabel();
		jLabel_ReleaseDate.setText("Release Date  :");
		jLabel_ReleaseDate.setLocation(new Point(21, 220));
		jLabel_ReleaseDate.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_ReleaseDate.setSize(new Dimension(90, 20));
		jLabel_Topic = new JLabel();
		jLabel_Topic.setText("Topic  :");
		jLabel_Topic.setLocation(new Point(21, 198));
		jLabel_Topic.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Topic.setSize(new Dimension(90, 20));
		jLabel_Language = new JLabel();
		jLabel_Language.setText("Language  :");
		jLabel_Language.setLocation(new Point(21, 176));
		jLabel_Language.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Language.setSize(new Dimension(90, 20));
		jLabel_ISBN = new JLabel();
		jLabel_ISBN.setText("Book ISBN  :");
		jLabel_ISBN.setLocation(new Point(21, 110));
		jLabel_ISBN.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_ISBN.setSize(new Dimension(90, 20));
		jLabel_Auhtor = new JLabel();
		jLabel_Auhtor.setText("Author  :");
		jLabel_Auhtor.setLocation(new Point(21, 154));
		jLabel_Auhtor.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Auhtor.setSize(new Dimension(90, 20));
		jLabel_Title = new JLabel();
		jLabel_Title.setText("Title  :");
		jLabel_Title.setLocation(new Point(20, 132));
		jLabel_Title.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Title.setSize(new Dimension(90, 20));
		this.setSize(700, 600);
		this.setLayout(null);
		this.add(jLabel_Title, null);
		this.add(jLabel_Auhtor, null);
		this.add(jLabel_ISBN, null);
		this.add(jLabel_Language, null);
		this.add(jLabel_Topic, null);
		this.add(jLabel_ReleaseDate, null);
		this.add(jLabel_Publisher, null);
		this.add(jLabel_Price, null);
		this.add(getJTextField_isbn(), null);
		this.add(getJTextField_title(), null);
		this.add(getJTextField_author(), null);
		this.add(getJTextField_lang(), null);
		this.add(getJTextField_topic(), null);
		this.add(getJTextField_r_date(), null);
		this.add(getJTextField_publisher(), null);
		this.add(getJTextField_price(), null);
		this.add(getJScrollPane_summary(), null);
		this.add(getJScrollPane_TOC(), null);
		this.add(jLabel_visibility, null);
		this.add(getJCheckBox_visibilityCheck(), null);
		this.add(getJButton_back(), null);
		this.add(getJButton_AddBook(), null);
		this.add(jLabel_MainLabel, null);
		this.add(jLabel_summary, null);
		this.add(jLabel_toc, null);
		this.add(jLabel_label, null);
		this.add(getJTextField_label(), null);
	}

	
	public void setLastChoice(ANBDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}
	
	public ANBDecision getLastChoice() {
		return m_lastChoice;
	}
	

	/**
	 * This method initializes jTextField_isbn	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_isbn() {
		if (jTextField_isbn == null) {
			jTextField_isbn = new JTextField();
			jTextField_isbn.setSize(new Dimension(280, 20));
			jTextField_isbn.setLocation(new Point(116, 110));
		}
		return jTextField_isbn;
	}

	/**
	 * This method initializes jTextField_title	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_title() {
		if (jTextField_title == null) {
			jTextField_title = new JTextField();
			jTextField_title.setSize(new Dimension(280, 20));
			jTextField_title.setLocation(new Point(116, 132));
		}
		return jTextField_title;
	}

	/**
	 * This method initializes jTextField_author	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_author() {
		if (jTextField_author == null) {
			jTextField_author = new JTextField();
			jTextField_author.setSize(new Dimension(280, 20));
			jTextField_author.setLocation(new Point(116, 154));
		}
		return jTextField_author;
	}

	/**
	 * This method initializes jTextField_lang	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_lang() {
		if (jTextField_lang == null) {
			jTextField_lang = new JTextField();
			jTextField_lang.setSize(new Dimension(280, 20));
			jTextField_lang.setLocation(new Point(116, 176));
		}
		return jTextField_lang;
	}

	/**
	 * This method initializes jTextField_topic	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_topic() {
		if (jTextField_topic == null) {
			jTextField_topic = new JTextField();
			jTextField_topic.setSize(new Dimension(280, 20));
			jTextField_topic.setLocation(new Point(116, 198));
		}
		return jTextField_topic;
	}

	/**
	 * This method initializes jTextField_r_date	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_r_date() {
		if (jTextField_r_date == null) {
			jTextField_r_date = new JTextField();
			jTextField_r_date.setSize(new Dimension(280, 20));
			jTextField_r_date.setLocation(new Point(116, 220));
		}
		return jTextField_r_date;
	}

	/**
	 * This method initializes jTextField_publisher	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_publisher() {
		if (jTextField_publisher == null) {
			jTextField_publisher = new JTextField();
			jTextField_publisher.setSize(new Dimension(280, 20));
			jTextField_publisher.setLocation(new Point(116, 242));
		}
		return jTextField_publisher;
	}

	/**
	 * This method initializes jTextField_price	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_price() {
		if (jTextField_price == null) {
			jTextField_price = new JTextField();
			jTextField_price.setSize(new Dimension(120, 20));
			jTextField_price.setLocation(new Point(115, 286));
		}
		return jTextField_price;
	}

	/**
	 * This method initializes jScrollPane_summary	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_summary() {
		if (jScrollPane_summary == null) {
			jScrollPane_summary = new JScrollPane();
			jScrollPane_summary.setBounds(new Rectangle(23, 351, 376, 130));
			jScrollPane_summary.setViewportView(getJTextArea_summary());
			jScrollPane_summary.setBorder(BorderFactory.createLineBorder(Color.black));
			
		}
		return jScrollPane_summary;
	}

	/**
	 * This method initializes jTextArea_summary	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea_summary() {
		if (jTextArea_summary == null) {
			jTextArea_summary = new JTextArea();
		}
		return jTextArea_summary;
	}

	/**
	 * This method initializes jScrollPane_TOC	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_TOC() {
		if (jScrollPane_TOC == null) {
			jScrollPane_TOC = new JScrollPane();
			jScrollPane_TOC.setSize(new Dimension(263, 305));
			jScrollPane_TOC.setLocation(new Point(415, 176));
			jScrollPane_TOC.setViewportView(getJTextArea_toc());
			jScrollPane_TOC.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		return jScrollPane_TOC;
	}

	/**
	 * This method initializes jTextArea_toc	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea_toc() {
		if (jTextArea_toc == null) {
			jTextArea_toc = new JTextArea();
		}
		return jTextArea_toc;
	}

	/**
	 * This method initializes jCheckBox_visibilityCheck	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_visibilityCheck() {
		if (jCheckBox_visibilityCheck == null) {
			jCheckBox_visibilityCheck = new JCheckBox();
			jCheckBox_visibilityCheck.setPreferredSize(new Dimension(27, 27));
			jCheckBox_visibilityCheck.setLocation(new Point(582, 114));
			jCheckBox_visibilityCheck.setSelected(true);
			jCheckBox_visibilityCheck.setSize(new Dimension(27, 27));
			try {
				if(AUser.getInstance().getPrivilege().compareTo(EActor.LibraryManager) != 0)
				{
					jCheckBox_visibilityCheck.setEnabled(false);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		return jCheckBox_visibilityCheck;
	}

	/**
	 * This method initializes jButton_back	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_back() {
		if (jButton_back == null) {
			jButton_back = new JButton();
			jButton_back.setText("Back");
			jButton_back.setSize(new Dimension(208, 34));
			jButton_back.setLocation(new Point(94, 520));
			jButton_back.addActionListener(this);
		}
		return jButton_back;
	}

	/**
	 * This method initializes jButton_AddBook	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_AddBook() {
		if (jButton_AddBook == null) {
			jButton_AddBook = new JButton();
			jButton_AddBook.setText("Add Book");
			jButton_AddBook.setSize(new Dimension(208, 34));
			jButton_AddBook.setLocation(new Point(396, 520));
			jButton_AddBook.addActionListener(this);
		}
		return jButton_AddBook;
	}
	
	

	/**
	 * This method initializes jTextField_label	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_label() {
		if (jTextField_label == null) {
			jTextField_label = new JTextField();
			jTextField_label.setSize(new Dimension(280, 20));
			jTextField_label.setLocation(new Point(115, 264));
		}
		return jTextField_label;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == jButton_back)
		{
			setLastChoice(ANBDecision.BACK);
			this.setVisible(false);
		}
		if(source == jButton_AddBook)
		{ 		
				try {
					((CLibrarian)AUser.getInstance()).addNewBook(jTextField_title.getText(), jTextField_author.getText(), jTextField_isbn.getText(), jTextField_r_date.getText(), jTextField_publisher.getText(), jTextArea_summary.getText(),jTextField_price.getText(), jTextField_topic.getText(), jTextField_label.getText(), jTextArea_toc.getText(), jCheckBox_visibilityCheck.isSelected(), jTextField_lang.getText());
					setLastChoice(ANBDecision.ADDBOOK);
					this.setVisible(false);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
					}			
		}
	}

}