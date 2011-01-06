package client.gui.searchbook;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import common.data.CBook;
import client.core.AUser;
import client.core.CLibrarian;
import client.core.EActor;
import javax.swing.JComboBox;


public class CEditBookDetailsPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private EBDDecision m_lastChoice;
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
	private JButton jButton_back_EBD = null;
	private JButton jButton_Save_EBD = null;
	private JLabel jLabel_MainLabel = null;
	private JLabel jLabel_summary = null;
	private JLabel jLabel_toc = null;
	private JLabel jLabel_label = null;
	private JTextField jTextField_label = null;
	private JLabel jLabel_FileTypes = null;
	private CBook m_book = null;
	private JLabel jLabel_subtopic = null;
	private JTextField jTextField_subtopic = null;
	private JComboBox jComboBox_fileTypes = null;
	private JButton jButton_deleteBook = null;
	
	
	public enum EBDDecision
	{
		BACK,SAVE,DELETEBOOK
	}
	
	
	
	
	/**
	 * This is the default constructor
	 */
	public CEditBookDetailsPanel() {
		super();
		m_book = CBookDetailPanel.getBook();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel_subtopic = new JLabel();
		jLabel_subtopic.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_subtopic.setLocation(new Point(20, 220));
		jLabel_subtopic.setSize(new Dimension(90, 26));
		jLabel_subtopic.setText("Subtopic :");
		jLabel_FileTypes = new JLabel();
		jLabel_FileTypes.setText("File Type :");
		jLabel_FileTypes.setSize(new Dimension(60, 24));
		jLabel_FileTypes.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_FileTypes.setLocation(new Point(450, 140));
		jLabel_label = new JLabel();
		jLabel_label.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_label.setSize(new Dimension(90, 26));
		jLabel_label.setLocation(new Point(20, 304));
		jLabel_label.setText("Labels");
		jLabel_toc = new JLabel();
		jLabel_toc.setText("Table Of Contents");
		jLabel_toc.setLocation(new Point(476, 185));
		jLabel_toc.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		jLabel_toc.setSize(new Dimension(140, 22));
		jLabel_summary = new JLabel();
		jLabel_summary.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		jLabel_summary.setSize(new Dimension(91, 22));
		jLabel_summary.setLocation(new Point(63, 368));
		jLabel_summary.setText("Summary");
		jLabel_MainLabel = new JLabel();
		jLabel_MainLabel.setText("Edit Book Details");
		jLabel_MainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_MainLabel.setFont(new Font("Freestyle Script", Font.BOLD, 48));
		jLabel_MainLabel.setLocation(new Point(0, 15));
		jLabel_MainLabel.setSize(new Dimension(700, 50));
		jLabel_MainLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		jLabel_visibility = new JLabel();
		jLabel_visibility.setText("    Visible");
		jLabel_visibility.setBorder(BorderFactory.createLineBorder(Color.black));
		jLabel_visibility.setLocation(new Point(482, 80));
		jLabel_visibility.setFont(new Font("Eras Light ITC", Font.BOLD, 16));
		jLabel_visibility.setSize(new Dimension(135, 32));
		jLabel_Price = new JLabel();
		jLabel_Price.setText("Price  :");
		jLabel_Price.setLocation(new Point(20, 332));
		jLabel_Price.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Price.setSize(new Dimension(90, 26));
		jLabel_Publisher = new JLabel();
		jLabel_Publisher.setText("Publisher  :");
		jLabel_Publisher.setLocation(new Point(20, 276));
		jLabel_Publisher.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Publisher.setSize(new Dimension(90, 26));
		jLabel_ReleaseDate = new JLabel();
		jLabel_ReleaseDate.setText("Release Date  :");
		jLabel_ReleaseDate.setLocation(new Point(20, 248));
		jLabel_ReleaseDate.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_ReleaseDate.setSize(new Dimension(90, 26));
		jLabel_Topic = new JLabel();
		jLabel_Topic.setText("Topic  :");
		jLabel_Topic.setLocation(new Point(21, 192));
		jLabel_Topic.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Topic.setSize(new Dimension(90, 26));
		jLabel_Language = new JLabel();
		jLabel_Language.setText("Language  :");
		jLabel_Language.setLocation(new Point(21, 164));
		jLabel_Language.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Language.setSize(new Dimension(90, 26));
		jLabel_ISBN = new JLabel();
		jLabel_ISBN.setText("Book ISBN  :");
		jLabel_ISBN.setLocation(new Point(20, 80));
		jLabel_ISBN.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_ISBN.setSize(new Dimension(90, 26));
		jLabel_Auhtor = new JLabel();
		jLabel_Auhtor.setText("Author  :");
		jLabel_Auhtor.setLocation(new Point(20, 136));
		jLabel_Auhtor.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Auhtor.setSize(new Dimension(90, 26));
		jLabel_Title = new JLabel();
		jLabel_Title.setText("Title  :");
		jLabel_Title.setLocation(new Point(20, 108));
		jLabel_Title.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		jLabel_Title.setSize(new Dimension(90, 26));
		this.setSize(700, 550);
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
		this.add(getjButton_back_EBD(), null);
		this.add(getjButton_Save_EBD(), null);
		this.add(jLabel_MainLabel, null);
		this.add(jLabel_summary, null);
		this.add(jLabel_toc, null);
		this.add(jLabel_label, null);
		this.add(getJTextField_label(), null);
		this.add(jLabel_FileTypes, null);
		this.add(jLabel_subtopic, null);
		this.add(getJTextField_subtopic(), null);
		this.add(getJComboBox_fileTypes(), null);
		this.add(getJButton_deleteBook(), null);
	}

	
	public void setLastChoice(EBDDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}
	
	public EBDDecision getLastChoice() {
		return m_lastChoice;
	}
	

	/**
	 * This method initializes jTextField_isbn	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_isbn() {
		if (jTextField_isbn == null) {
			jTextField_isbn = new JTextField(m_book.getM_ISBN());
			jTextField_isbn.setSize(new Dimension(280, 27));
			jTextField_isbn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			jTextField_isbn.setPreferredSize(new Dimension(280, 22));
			jTextField_isbn.setLocation(new Point(115, 80));
			jTextField_isbn.setEditable(false);
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
			jTextField_title = new JTextField(m_book.getM_title());
			jTextField_title.setSize(new Dimension(280, 27));
			jTextField_title.setPreferredSize(new Dimension(280, 24));
			jTextField_title.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			jTextField_title.setLocation(new Point(115, 108));
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
			jTextField_author = new JTextField(m_book.getM_author());
			jTextField_author.setSize(new Dimension(280, 27));
			jTextField_author.setPreferredSize(new Dimension(280, 24));
			jTextField_author.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			jTextField_author.setLocation(new Point(115, 136));
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
			jTextField_lang = new JTextField(m_book.getM_language());
			jTextField_lang.setSize(new Dimension(280, 27));
			jTextField_lang.setPreferredSize(new Dimension(280, 24));
			jTextField_lang.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			jTextField_lang.setLocation(new Point(115, 164));
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
			String topics = null;
			String[] t = m_book.gettopics();
			for(String s : t)
			{
				topics = topics + s + ",";
			}
			jTextField_topic = new JTextField(topics);
			jTextField_topic.setSize(new Dimension(280, 27));
			jTextField_topic.setPreferredSize(new Dimension(280, 24));
			jTextField_topic.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			jTextField_topic.setLocation(new Point(115, 192));
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
			jTextField_r_date = new JTextField(m_book.getM_release_date());
			jTextField_r_date.setSize(new Dimension(280, 27));
			jTextField_r_date.setPreferredSize(new Dimension(280, 24));
			jTextField_r_date.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			jTextField_r_date.setLocation(new Point(115, 248));
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
			jTextField_publisher = new JTextField(m_book.getM_publisher());
			jTextField_publisher.setSize(new Dimension(280, 27));
			jTextField_publisher.setPreferredSize(new Dimension(280, 24));
			jTextField_publisher.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			jTextField_publisher.setLocation(new Point(115, 276));
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
			String price = Double.toString(m_book.getM_price());
			jTextField_price = new JTextField(price);
			jTextField_price.setSize(new Dimension(120, 27));
			jTextField_price.setPreferredSize(new Dimension(120, 24));
			jTextField_price.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			jTextField_price.setLocation(new Point(114, 332));
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
			jScrollPane_summary.setLocation(new Point(23, 390));
			jScrollPane_summary.setSize(new Dimension(376, 67));
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
			jTextArea_summary = new JTextArea(m_book.getM_summary());
			jTextArea_summary.setSize(new Dimension(374, 90));
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
			jScrollPane_TOC.setSize(new Dimension(263, 251));
			jScrollPane_TOC.setLocation(new Point(415, 206));
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
			jTextArea_toc = new JTextArea(m_book.getM_TOC());
			jTextArea_toc.setSize(new Dimension(261, 270));
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
			jCheckBox_visibilityCheck.setLocation(new Point(582, 84));
			if(m_book.getM_invisible())
			      jCheckBox_visibilityCheck.setSelected(true);
			else if (!m_book.getM_invisible())
				  jCheckBox_visibilityCheck.setSelected(false);
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
	 * This method initializes jButton_back_EBD	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getjButton_back_EBD() {
		if (jButton_back_EBD == null) {
			jButton_back_EBD = new JButton();
			jButton_back_EBD.setText("Back");
			jButton_back_EBD.setSize(new Dimension(150, 34));
			jButton_back_EBD.setLocation(new Point(62, 480));
			jButton_back_EBD.addActionListener(this);
		}
		return jButton_back_EBD;
	}

	/**
	 * This method initializes jButton_Save_EBD	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getjButton_Save_EBD() {
		if (jButton_Save_EBD == null) {
			jButton_Save_EBD = new JButton();
			jButton_Save_EBD.setText("Save");
			jButton_Save_EBD.setSize(new Dimension(150, 34));
			jButton_Save_EBD.setLocation(new Point(486, 480));
			jButton_Save_EBD.addActionListener(this);
		}
		return jButton_Save_EBD;
	}
	
	

	/**
	 * This method initializes jTextField_label	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_label() {
		if (jTextField_label == null) {
			jTextField_label = new JTextField(m_book.getM_lables());
			jTextField_label.setSize(new Dimension(280, 27));
			jTextField_label.setPreferredSize(new Dimension(280, 24));
			jTextField_label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			jTextField_label.setLocation(new Point(114, 304));
		}
		return jTextField_label;
	}
	
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == jButton_back_EBD)
		{
			setLastChoice(EBDDecision.BACK);
			this.setVisible(false);
		}
		if(source == jButton_deleteBook)
		{
			try {
				((CLibrarian)AUser.getInstance()).deleteBook(m_book.getM_ISBN(), jComboBox_fileTypes.getSelectedItem().toString());
			     this.setLastChoice(EBDDecision.DELETEBOOK);
			     this.setVisible(false);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		if(source == jButton_Save_EBD)
		{ 		
				try {
					
					((CLibrarian)AUser.getInstance()).updateBookDetails(m_book.getM_ISBN(),jTextField_title.getText(), jTextField_author.getText(), jTextField_r_date.getText(), jTextField_publisher.getText(), jTextArea_summary.getText(),jTextField_price.getText(), jTextField_topic.getText(), jTextField_label.getText(), jTextArea_toc.getText(), jCheckBox_visibilityCheck.isSelected(), jTextField_lang.getText(),jComboBox_fileTypes.getSelectedItem().toString());
					setLastChoice(EBDDecision.SAVE);
					this.setVisible(false);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage() + e.getClass() ,"Error",JOptionPane.ERROR_MESSAGE);
					}			
		}
	}

	/**
	 * This method initializes jTextField_subtopic	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_subtopic() {
		if (jTextField_subtopic == null) {
			String subtopic = null;
			String[] st = m_book.getSubtopics();
			for(String s : st)
			{
				subtopic = subtopic + s + ",";
			}
			jTextField_subtopic = new JTextField(subtopic);
			jTextField_subtopic.setLocation(new Point(115, 220));
			jTextField_subtopic.setSize(new Dimension(280, 27));
		}
		return jTextField_subtopic;
	}

	/**
	 * This method initializes jComboBox_fileTypes	
	 * 	
	 * @return javax.swing.JComboBox	
	 * @throws Exception 
	 */
	private JComboBox getJComboBox_fileTypes() {
		if (jComboBox_fileTypes == null) {
			try {
				String[] bookFileTypes = AUser.getInstance().getFileType(m_book.getM_ISBN());
				jComboBox_fileTypes = new JComboBox(bookFileTypes);
				jComboBox_fileTypes.setSize(new Dimension(130, 25));
				jComboBox_fileTypes.setLocation(new Point(515, 140));
			} catch (Exception e) {
				System.out.println("Can't get File Types!");
			}
			
		}
		return jComboBox_fileTypes;
	}

	/**
	 * This method initializes jButton_deleteBook	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_deleteBook() {
		if (jButton_deleteBook == null) {
			jButton_deleteBook = new JButton();
			jButton_deleteBook.setText("Delete Book");
			jButton_deleteBook.setSize(new Dimension(150, 34));
			jButton_deleteBook.setLocation(new Point(274, 480));
			jButton_deleteBook.addActionListener(this);
		}
		return jButton_deleteBook;
	}


}
