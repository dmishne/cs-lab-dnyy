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
import javax.swing.JList;

import com.mysql.jdbc.log.Jdk14Logger;

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
        private JLabel jLabel_FileTypes = null;
        private JLabel jLabel_Type_pdf = null;
        private JLabel jLabel_Type_fb2 = null;
        private JLabel jLabel_Type_doc = null;
        private JCheckBox jCheckBox_pdf = null;
        private JCheckBox jCheckBox_fb2 = null;
        private JCheckBox jCheckBox_doc = null;
        private JLabel jLabel_subtopic = null;
		private JList jList_topics = null;
		private JButton jButton_manageTopics = null;
		private JScrollPane jScrollPane_topics = null;
        public enum ANBDecision
        {
                BACK,ADDBOOK,SETTOPICS
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
                jLabel_subtopic = new JLabel();
                jLabel_subtopic.setText("Subtopic :");
                jLabel_subtopic.setSize(new Dimension(90, 26));
                jLabel_subtopic.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
                jLabel_subtopic.setLocation(new Point(20, 304));
                jLabel_Type_doc = new JLabel();
                jLabel_Type_doc.setText("DOC");
                jLabel_Type_doc.setSize(new Dimension(30, 20));
                jLabel_Type_doc.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
                jLabel_Type_doc.setLocation(new Point(631, 125));
                jLabel_Type_fb2 = new JLabel();
                jLabel_Type_fb2.setText("FB2");
                jLabel_Type_fb2.setSize(new Dimension(30, 20));
                jLabel_Type_fb2.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
                jLabel_Type_fb2.setLocation(new Point(571, 125));
                jLabel_Type_pdf = new JLabel();
                jLabel_Type_pdf.setText("PDF");
                jLabel_Type_pdf.setSize(new Dimension(30, 20));
                jLabel_Type_pdf.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
                jLabel_Type_pdf.setLocation(new Point(511, 125));
                jLabel_FileTypes = new JLabel();
                jLabel_FileTypes.setText("File Type :");
                jLabel_FileTypes.setSize(new Dimension(60, 20));
                jLabel_FileTypes.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
                jLabel_FileTypes.setLocation(new Point(431, 125));
                jLabel_label = new JLabel();
                jLabel_label.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
                jLabel_label.setSize(new Dimension(90, 26));
                jLabel_label.setLocation(new Point(20, 248));
                jLabel_label.setText("Labels");
                jLabel_toc = new JLabel();
                jLabel_toc.setText("Table Of Contents");
                jLabel_toc.setLocation(new Point(476, 185));
                jLabel_toc.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
                jLabel_toc.setSize(new Dimension(140, 22));
                jLabel_summary = new JLabel();
                jLabel_summary.setBounds(new Rectangle(63, 368, 91, 22));
                jLabel_summary.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
                jLabel_summary.setText("Summary");
                jLabel_MainLabel = new JLabel();
                jLabel_MainLabel.setText("Add New Book");
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
                jLabel_Publisher.setLocation(new Point(20, 220));
                jLabel_Publisher.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
                jLabel_Publisher.setSize(new Dimension(90, 26));
                jLabel_ReleaseDate = new JLabel();
                jLabel_ReleaseDate.setText("Release Date  :");
                jLabel_ReleaseDate.setLocation(new Point(20, 192));
                jLabel_ReleaseDate.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
                jLabel_ReleaseDate.setSize(new Dimension(90, 26));
                jLabel_Topic = new JLabel();
                jLabel_Topic.setText("Topic &");
                jLabel_Topic.setLocation(new Point(20, 276));
                jLabel_Topic.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
                jLabel_Topic.setSize(new Dimension(90, 32));
                jLabel_Language = new JLabel();
                jLabel_Language.setText("Language  :");
                jLabel_Language.setLocation(new Point(20, 164));
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
                this.add(jLabel_FileTypes, null);
                this.add(jLabel_Type_pdf, null);
                this.add(jLabel_Type_fb2, null);
                this.add(jLabel_Type_doc, null);
                this.add(getJCheckBox_pdf(), null);
                this.add(getJCheckBox_fb2(), null);
                this.add(getJCheckBox_doc(), null);
                this.add(jLabel_subtopic, null);
                this.add(getJButton_manageTopics(), null);
                this.add(getJScrollPane_topics(), null);
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
                        jTextField_isbn.setSize(new Dimension(280, 27));
                        jTextField_isbn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                        jTextField_isbn.setPreferredSize(new Dimension(280, 22));
                        jTextField_isbn.setLocation(new Point(115, 80));
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
                        jTextField_author = new JTextField();
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
                        jTextField_lang = new JTextField();
                        jTextField_lang.setSize(new Dimension(280, 27));
                        jTextField_lang.setPreferredSize(new Dimension(280, 24));
                        jTextField_lang.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                        jTextField_lang.setLocation(new Point(115, 164));
                }
                return jTextField_lang;
        }

        /**
         * This method initializes jTextField_r_date    
         *      
         * @return javax.swing.JTextField      
         */
        private JTextField getJTextField_r_date() {
                if (jTextField_r_date == null) {
                        jTextField_r_date = new JTextField();
                        jTextField_r_date.setSize(new Dimension(280, 27));
                        jTextField_r_date.setPreferredSize(new Dimension(280, 24));
                        jTextField_r_date.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                        jTextField_r_date.setLocation(new Point(115, 192));
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
                        jTextField_publisher.setSize(new Dimension(280, 27));
                        jTextField_publisher.setPreferredSize(new Dimension(280, 24));
                        jTextField_publisher.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                        jTextField_publisher.setLocation(new Point(115, 220));
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
                        jScrollPane_summary.setSize(new Dimension(655, 67));
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
                        jScrollPane_TOC.setSize(new Dimension(263, 153));
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
                        jTextArea_toc = new JTextArea();
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
                        jButton_back.setLocation(new Point(94, 480));
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
                        jButton_AddBook.setLocation(new Point(396, 480));
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
                        jTextField_label.setSize(new Dimension(280, 27));
                        jTextField_label.setPreferredSize(new Dimension(280, 24));
                        jTextField_label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                        jTextField_label.setLocation(new Point(115, 248));
                }
                return jTextField_label;
        }
       
       
        /**
         * This method initializes jCheckBox_pdf        
         *      
         * @return javax.swing.JCheckBox        
         */
        private JCheckBox getJCheckBox_pdf() {
                if (jCheckBox_pdf == null) {
                        jCheckBox_pdf = new JCheckBox();
                        jCheckBox_pdf.setSize(new Dimension(19, 17));
                        jCheckBox_pdf.setLocation(new Point(515, 150));
                }
                return jCheckBox_pdf;
        }

        /**
         * This method initializes jCheckBox_fb2        
         *      
         * @return javax.swing.JCheckBox        
         */
        private JCheckBox getJCheckBox_fb2() {
                if (jCheckBox_fb2 == null) {
                        jCheckBox_fb2 = new JCheckBox();
                        jCheckBox_fb2.setSize(new Dimension(18, 18));
                        jCheckBox_fb2.setLocation(new Point(575, 150));
                }
                return jCheckBox_fb2;
        }

        /**
         * This method initializes jCheckBox_doc        
         *      
         * @return javax.swing.JCheckBox        
         */
        private JCheckBox getJCheckBox_doc() {
                if (jCheckBox_doc == null) {
                        jCheckBox_doc = new JCheckBox();
                        jCheckBox_doc.setSize(new Dimension(22, 16));
                        jCheckBox_doc.setLocation(new Point(635, 150));
                }
                return jCheckBox_doc;
        }
       
       
        public void actionPerformed(ActionEvent ae) {
                Object source = ae.getSource();
                String topics = "";
                if(source == jButton_back)
                {
                        setLastChoice(ANBDecision.BACK);
                        this.setVisible(false);
                }
                else if(source == jButton_manageTopics)
                {
                	this.setLastChoice(ANBDecision.SETTOPICS);
                	this.setVisible(false);
                }
                else if(source == jButton_AddBook)
                {              
                                try {
                                	    String[] formats = new String[3];
                                        int i = 0;
                                        if(jCheckBox_pdf.isSelected())
                                        {
                                        	formats[i] = "pdf";
                                        	i++;
                                        }
                                        if(jCheckBox_fb2.isSelected())
                                        {
                                        	formats[i] = "fb2";
                                        	i++;
                                        }
                                        if(jCheckBox_doc.isSelected())
                                        {
                                        	formats[i] = "doc";                            	
                                        }
                                        int len = jList_topics.getModel().getSize();
                                        for(int j =0 ; j < len; j++)
                                        	topics = topics + "~"+ jList_topics.getModel().getElementAt(j);
                                        
                                        String answer = ((CLibrarian)AUser.getInstance()).addNewBook(jTextField_title.getText(), jTextField_author.getText(), jTextField_isbn.getText(), jTextField_r_date.getText(), jTextField_publisher.getText(), jTextArea_summary.getText(),jTextField_price.getText(), topics ,jTextField_label.getText(), jTextArea_toc.getText(), jCheckBox_visibilityCheck.isSelected(), jTextField_lang.getText(),formats);
                                        setLastChoice(ANBDecision.ADDBOOK);
                                        JOptionPane.showMessageDialog(null, answer ,"Server answer : ",JOptionPane.INFORMATION_MESSAGE);
                                        this.setVisible(false);
                                        } catch (Exception e) {
                                                JOptionPane.showMessageDialog(null, e.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
                                        }                      
                }
        }

		/**
		 * This method initializes jList_topics	
		 * 	
		 * @return javax.swing.JList	
		 */
		private JList getJList_topics() {
			if (jList_topics == null) {
				jList_topics = new JList();
				jList_topics.setEnabled(false);
			}
			return jList_topics;
		}
		
		
		
		public void setNewList(JList nList)
		{
			this.remove(jList_topics);
			this.remove(jScrollPane_topics);
			jList_topics = nList;
			jScrollPane_topics = null;
			jList_topics.setLocation(new Point(118, 276));
			jList_topics.setSize(new Dimension(274, 54));
			jList_topics.setEnabled(true);
			jList_topics.setVisible(true);
			this.add(getJScrollPane_topics());
		}

		/**
		 * This method initializes jButton_manageTopics	
		 * 	
		 * @return javax.swing.JButton	
		 */
		private JButton getJButton_manageTopics() {
			if (jButton_manageTopics == null) {
				jButton_manageTopics = new JButton();
				jButton_manageTopics.setText("Set Topics");
				jButton_manageTopics.setSize(new Dimension(134, 24));
				jButton_manageTopics.setLocation(new Point(260, 334));
				jButton_manageTopics.addActionListener(this);
			}
			return jButton_manageTopics;
		}

		/**
		 * This method initializes jScrollPane_topics	
		 * 	
		 * @return javax.swing.JScrollPane	
		 */
		private JScrollPane getJScrollPane_topics() {
			if (jScrollPane_topics == null) {
				jScrollPane_topics = new JScrollPane();
				jScrollPane_topics.setLocation(new Point(118, 276));
				jScrollPane_topics.setViewportView(getJList_topics());
				jScrollPane_topics.setSize(new Dimension(274, 54));
			}
			return jScrollPane_topics;
		}

       

}

