package client.gui;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.Point;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class CSearchResultPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton m_jButton_back_SRP = null;
	private JList m_jList_Results_SRP = null;
	private JScrollPane m_JScrollPane_Results_SRP = null;



	////////////////////////////////STUB////////////////////////////////
	private String[] tempString = {"The Hobbit, J. R. R. Tolkien",
								   "Alice's Adventures in Wonderland, Lewis Carroll",
								   "A","A","A","A","A","A","A","A","A","A",
								   "A","A","A","A","A","A","A","A","A","A",
								   "A","A","A","A","A","A","A","A","A","A",
								   "A","A","A","A","A","A","A","A"
	
	
	
	
	
	};
	////////////////////////////////////////////////////////////////////
	private JLabel m_jLabel_SRP_title = null;
	private JButton m_jButton_ShowDetails_SRP = null;
	
	
	
	/**
	 * This is the default constructor
	 */
	public CSearchResultPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
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
	 * 	
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
	 */
	private JList getM_jList_Results_SRP() {
		if (m_jList_Results_SRP == null) {
			m_jList_Results_SRP = new JList(tempString);
			m_jList_Results_SRP.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			m_jList_Results_SRP.setSize(new Dimension(600, 400));
			m_jList_Results_SRP.setLocation(new Point(50, 50));
			m_jList_Results_SRP.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);	
		}
		return m_jList_Results_SRP;
	}

	/**
	 * @return the m_JScrollPane_Results_SRP
	 */
	public JScrollPane getM_JScrollPane_Results_SRP() {
		if(m_JScrollPane_Results_SRP == null)
		{
			m_JScrollPane_Results_SRP = new JScrollPane(getM_jList_Results_SRP());
			m_JScrollPane_Results_SRP.setSize(new Dimension(600, 400));
			m_JScrollPane_Results_SRP.setLocation(new Point(50, 50));
			
		}
		return m_JScrollPane_Results_SRP;
	}


	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jButton_back_SRP)
		{
			this.setVisible(false);
		}
	}

	/**
	 * This method initializes m_jButton_ShowDetails_SRP	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_ShowDetails_SRP() {
		if (m_jButton_ShowDetails_SRP == null) {
			m_jButton_ShowDetails_SRP = new JButton();
			m_jButton_ShowDetails_SRP.setBounds(new Rectangle(396, 481, 208, 34));
			m_jButton_ShowDetails_SRP.setText("Show Details");
		}
		return m_jButton_ShowDetails_SRP;
	}

}