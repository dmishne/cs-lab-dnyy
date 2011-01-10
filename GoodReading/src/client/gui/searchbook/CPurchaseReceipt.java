package client.gui.searchbook;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;


import client.core.*;

import common.data.CBook;

public class CPurchaseReceipt extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel m_jLabel_MainLabel = null;
	private JTextArea m_jTextArea_receipt = null;
	private JButton m_jButton_Back = null;
	private JButton m_jButton_SaveFile = null;
	private JButton m_jButton_SaveReceipt = null;
	private JScrollPane m_jScrollPane_receipt = null;
	private String m_receipt = null;  //  @jve:decl-index=0:
	private JFileChooser m_fileChooser = null;

	/**
	 * This is the default constructor
	 */
	public CPurchaseReceipt() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		m_jLabel_MainLabel = new JLabel();
		m_jLabel_MainLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		m_jLabel_MainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		m_jLabel_MainLabel.setText("Receipt");
		m_jLabel_MainLabel.setLocation(new Point(0, 15));
		m_jLabel_MainLabel.setSize(new Dimension(700, 50));
		m_jLabel_MainLabel.setPreferredSize(new Dimension(700, 50));
		m_jLabel_MainLabel.setFont(new Font("Freestyle Script", Font.BOLD, 48));
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(m_jLabel_MainLabel, null);
		this.add(getM_ScrollPane_receipt(), null);
		this.add(getM_jButton_Back(), null);
		this.add(getM_jButton_SaveFile(), null);
		this.add(getM_jButton_SaveReceipt(), null);
	}

	/**
	 * This method initializes m_jTextArea_receipt	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getM_jTextArea_receipt() {
		if (m_jTextArea_receipt == null) {
			m_jTextArea_receipt = new JTextArea(receipt());
			m_jTextArea_receipt.setEditable(false);
			m_jTextArea_receipt.setLocation(new Point(50, 100));
			m_jTextArea_receipt.setSize(new Dimension(600, 300));
			m_jTextArea_receipt.setFont(new Font("Freestyle Script", Font.BOLD, 24));
		}
		return m_jTextArea_receipt;
	}
	
	private JScrollPane getM_ScrollPane_receipt() {
		if (m_jScrollPane_receipt == null) {
			m_jScrollPane_receipt = new JScrollPane(getM_jTextArea_receipt());
			m_jScrollPane_receipt.setLocation(new Point(50, 100));
			m_jScrollPane_receipt.setSize(new Dimension(600, 300));
		}
		return m_jScrollPane_receipt;
	}

	private String receipt()
	{
		m_receipt = new String();
		CBook currBook = CBookDetailPanel.getBook();
		m_receipt += "Receipt number: " + COrderBookPanel.getReceipt() + "\n";
		m_receipt += "--------------\n";
		m_receipt += "Title: " + currBook.getM_title() + "\n";
		m_receipt += "Author: " + currBook.getM_author() + "\n";
		m_receipt += "ISBN: " + currBook.getM_ISBN() + "\n";
		m_receipt += "\n\nTotal Price: " + currBook.getM_price() + "$\n";
		m_receipt += "\nThank you for buying books with GoodReading";
		return m_receipt;
	}
	
	
	
	/**
	 * This method initializes m_jButton_Back	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Back() {
		if (m_jButton_Back == null) {
			m_jButton_Back = new JButton();
			m_jButton_Back.setLocation(new Point(62, 480));
			m_jButton_Back.setText("Back");
			m_jButton_Back.setSize(new Dimension(150, 34));
			m_jButton_Back.addActionListener(this);
		}
		return m_jButton_Back;
	}

	/**
	 * This method initializes m_jButton_SaveFile	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_SaveFile() {
		if (m_jButton_SaveFile == null) {
			m_jButton_SaveFile = new JButton();
			m_jButton_SaveFile.setText("Save File");
			m_jButton_SaveFile.setLocation(new Point(274, 480));
			m_jButton_SaveFile.setSize(new Dimension(150, 34));
			m_jButton_SaveFile.addActionListener(this);
		}
		return m_jButton_SaveFile;
	}

	/**
	 * This method initializes m_jButton_SaveReceipt	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_SaveReceipt() {
		if (m_jButton_SaveReceipt == null) {
			m_jButton_SaveReceipt = new JButton();
			m_jButton_SaveReceipt.setText("Save Receipt");
			m_jButton_SaveReceipt.setLocation(new Point(486, 480));
			m_jButton_SaveReceipt.setSize(new Dimension(150, 34));
			m_jButton_SaveReceipt.addActionListener(this);
		}
		return m_jButton_SaveReceipt;
	}

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jButton_SaveReceipt)
		{
			m_fileChooser = new JFileChooser();
			int returnVal = m_fileChooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = m_fileChooser.getSelectedFile();
				if(file.exists())
				{
					int res = JOptionPane.showConfirmDialog(null,"File already exist, Overwrite?", "File Exist", JOptionPane.YES_NO_OPTION);
					if(res != JOptionPane.YES_OPTION)
					{
						return;
					}
				}
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter(file));
					String [] rec = receipt().split("\n");
					for(String curr : rec)
					{
						out.write(curr);
						out.newLine();
					}
					out.close();
				} catch (IOException e) {
					 JOptionPane.showMessageDialog(null,"Error in saving file", "ERROR MESSAGE", 1);
				}	
			}			
		}
		else if(source == m_jButton_SaveFile)
		{
			m_fileChooser = new JFileChooser();
			int returnVal = m_fileChooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					File file = m_fileChooser.getSelectedFile();
					if(file.exists())
					{
						int res = JOptionPane.showConfirmDialog(null,"File already exist, Overwrite?", "File Exist", JOptionPane.YES_NO_OPTION);
						if(res != JOptionPane.YES_OPTION)
						{
							return;
						}
					}			
					System.out.println(file.getAbsolutePath());
					((CReader)AUser.getInstance()).downloadBook(CBookDetailPanel.getBook().getM_ISBN(),COrderBookPanel.getChosenFileType(),file.getAbsolutePath());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,e.getMessage(), "ERROR MESSAGE", 1);
				}
			}			
		}
		else if(source == m_jButton_Back)
		{
			this.setVisible(false);
		}
	}

}
