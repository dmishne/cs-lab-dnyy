package client.gui.addnewbook;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.core.AUser;
import client.core.CLibrarian;
import client.gui.searchbook.CEditBookDetailsPanel;

/**
 * CManageTopicsPanel defines the panes which enables adding topic and subtopics to database
 * and assigning topics and subtopics to books
 */
public class CManageTopicsPanel extends JPanel implements ActionListener,ItemListener, ListSelectionListener{

	/**
	 * Defines the available operations that can cause removing this panel
	 */
	public enum MTPDecision
	{
		SET,BACK
	}
	
	/**
	 * Defines the panel that has enabled this panel
	 */
	public enum MTPfrom
	{
		ADDBOOK,EDITBOOK
	}
	
	private static final long serialVersionUID = 1L;
	private JLabel jLabel_main_label = null;
	private JScrollPane jScrollPane_ChoosenTopics_MT = null;
	private JList jList_ChoosenTopics_MT = null;
	private JComboBox jComboBox_AvailTopics = null;
	private JComboBox jComboBox_AvailSubTopics_MT = null;
	private JTextField jTextField_AddTopic_MT = null;
	private JTextField jTextField_AddSubTopic_MT = null;
	private JButton jButton_add_Topic = null;
	private JButton jButton_addSubTopic_MT = null;
	private JButton jButton_AddToList_MT = null;
	private JButton jButton_Back_MT = null;
	private JButton jButton_Set_MT = null;
	private JButton jButton_RemoveFromList_MT = null;
	private DefaultListModel listModel = null;
	/**
	 * m_last save the String representation of the previous panel.
	 */
	private String m_last = null;
	/**
	 * m_list save the list of chosen topics and subtopics.
	 */
	private static JList m_list = null;
	/**
	 * Saves the Enabling panel enum  
	 */
	private MTPfrom from = null;
	private boolean m_flag = false;
	/**
	 * save last selected row on the chosen topics and subtopics list
	 */
	private int selection = 0;
	/**
	 * Saves the last choice of the user
	 */
	private MTPDecision m_lastChoice = null;  //  @jve:decl-index=0:
	
	
	

	/**
	 * CManageTopicsPanel is the default constructor
	 */
	public CManageTopicsPanel(String from) {
		super();
		m_last = from;
		initialize();
	}

	/**
	 * Initialize() initializes this class
	 */
	private void initialize() {
		listModel = new DefaultListModel();
		jLabel_main_label = new JLabel();
		jLabel_main_label.setText("Book Organize Maneger");
		jLabel_main_label.setLocation(new Point(0, 0));
		jLabel_main_label.setPreferredSize(new Dimension(700, 75));
		jLabel_main_label.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_main_label.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		jLabel_main_label.setSize(new Dimension(700, 35));
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(jLabel_main_label, null);
		this.add(getJScrollPane_ChoosenTopics_MT(), null);
		this.add(getJComboBox_AvailTopics(), null);
		this.add(getJComboBox_AvailSubTopics_MT(), null);
		this.add(getJTextField_AddTopic_MT(), null);
		this.add(getJTextField_AddSubTopic_MT(), null);
		this.add(getJButton_add_Topic(), null);
		this.add(getJButton_addSubTopic_MT(), null);
		this.add(getJButton_AddToList_MT(), null);
		this.add(getJButton_Back_MT(), null);
		this.add(getJButton_Set_MT(), null);
		this.add(getJButton_RemoveFromList_MT(), null);
		if(m_last.compareTo("EBD") == 0)
		{
			JList list =  CEditBookDetailsPanel.getLastList();
			int size  = list.getModel().getSize();
		    Object [] last = new String[size];
		    for (int i = 0; i<size;i++)
		    {
		    	last[i] = list.getModel().getElementAt(i);
		    }
			for(String s : (String[])last)
			{
				s.replace("@", ":");
				listModel.addElement(s);
			}
		}
		else if(m_last.compareTo("ANB") == 0)
		{
			JList list =  CAddNewBookPanel.getLastList();
			int size  = list.getModel().getSize();
		    Object [] last = new String[size];
		    for (int i = 0; i<size;i++)
		    {
		    	last[i] = list.getModel().getElementAt(i);
		    }
			for(String s : (String[])last)
			{
				s.replace("@", ":");
				listModel.addElement(s);
			}
		}
	}

	
	
	/**
	 * @return the m_lastChoice
	 */
	public MTPDecision getLastChoice() {
		return m_lastChoice;
	}

	/**
	 * @param m_lastChoice the m_lastChoice to set
	 */
	public void setLastChoice(MTPDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	public JList getList(){
		return m_list;
		}
	
	
	/**
	 * @return the from
	 */
	public MTPfrom getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(MTPfrom from) {
		this.from = from;
	}

	/**
	 * This method initializes jScrollPane_ChoosenTopics_MT	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_ChoosenTopics_MT() {
		if (jScrollPane_ChoosenTopics_MT == null) {
			jScrollPane_ChoosenTopics_MT = new JScrollPane();
			jScrollPane_ChoosenTopics_MT.setLocation(new Point(40, 75));
			jScrollPane_ChoosenTopics_MT.setSize(new Dimension(620, 150));
			jScrollPane_ChoosenTopics_MT.setViewportView(getJList_ChoosenTopics_MT());
		}
		return jScrollPane_ChoosenTopics_MT;
	}

	/**
	 * This method initializes jList_ChoosenTopics_MT	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList_ChoosenTopics_MT() {
		if (jList_ChoosenTopics_MT == null) {			
		     jList_ChoosenTopics_MT = new JList(listModel);
		     jList_ChoosenTopics_MT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		     jList_ChoosenTopics_MT.setSize(new Dimension(620, 145));
		     jList_ChoosenTopics_MT.addListSelectionListener(this);
		}
		return jList_ChoosenTopics_MT;
	}

	/**
	 * This method initializes jComboBox_AvailTopics	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox_AvailTopics() {
		if (jComboBox_AvailTopics == null) {
			try {
				//jComboBox_AvailTopics = new JComboBox(CClientConnector.getInstance().getTopics());
				jComboBox_AvailTopics = new JComboBox(((CLibrarian)AUser.getInstance()).getTopics());
				jComboBox_AvailTopics.setSize(new Dimension(278, 27));
				jComboBox_AvailTopics.setLocation(new Point(40, 280));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
			}
			jComboBox_AvailTopics.addItemListener(this);
			jComboBox_AvailTopics.setVisible(true);
		}
		return jComboBox_AvailTopics;
	}

	/**
	 * This method initializes jComboBox_AvailSubTopics_MT	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox_AvailSubTopics_MT() {
		if (jComboBox_AvailSubTopics_MT == null) {
			jComboBox_AvailSubTopics_MT = new JComboBox();
			jComboBox_AvailSubTopics_MT.setEnabled(false);
			jComboBox_AvailSubTopics_MT.setLocation(new Point(380, 280));
			jComboBox_AvailSubTopics_MT.setSize(new Dimension(278, 27));
		}
		return jComboBox_AvailSubTopics_MT;
	}

	/**
	 * This method initializes jTextField_AddTopic_MT	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_AddTopic_MT() {
		if (jTextField_AddTopic_MT == null) {
			jTextField_AddTopic_MT = new JTextField();
			jTextField_AddTopic_MT.setSize(new Dimension(220, 26));
			jTextField_AddTopic_MT.setLocation(new Point(40, 250));
		}
		return jTextField_AddTopic_MT;
	}

	/**
	 * This method initializes jTextField_AddSubTopic_MT	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_AddSubTopic_MT() {
		if (jTextField_AddSubTopic_MT == null) {
			jTextField_AddSubTopic_MT = new JTextField();
			jTextField_AddSubTopic_MT.setEditable(false);
			jTextField_AddSubTopic_MT.setSize(new Dimension(220, 26));
			jTextField_AddSubTopic_MT.setLocation(new Point(380, 250));
		}
		return jTextField_AddSubTopic_MT;
	}

	/**
	 * This method initializes jButton_add_Topic	
	 * Pressing on this button add the topic to the database.	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_add_Topic() {
		if (jButton_add_Topic == null) {
			jButton_add_Topic = new JButton();
			jButton_add_Topic.setText("Add");
			jButton_add_Topic.setLocation(new Point(263, 250));
			jButton_add_Topic.setSize(new Dimension(56, 24));
			jButton_add_Topic.addActionListener(this);
		}
		return jButton_add_Topic;
	}

	/**
	 * This method initializes jButton_addSubTopic_MT	
	 * Pressing on this button add the subtopic to the database.
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_addSubTopic_MT() {
		if (jButton_addSubTopic_MT == null) {
			jButton_addSubTopic_MT = new JButton();
			jButton_addSubTopic_MT.setEnabled(false);
			jButton_addSubTopic_MT.setLocation(new Point(603, 250));
			jButton_addSubTopic_MT.setSize(new Dimension(56, 24));
			jButton_addSubTopic_MT.setText("Add");
			jButton_addSubTopic_MT.addActionListener(this);
		}
		return jButton_addSubTopic_MT;
	}

	/**
	 * This method initializes jButton_AddToList_MT	
	 * Pressing on this button add the topic/subtopic to the list of topics.
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_AddToList_MT() {
		if (jButton_AddToList_MT == null) {
			jButton_AddToList_MT = new JButton();
			jButton_AddToList_MT.setText("Add To List");
			jButton_AddToList_MT.setSize(new Dimension(135, 30));
			jButton_AddToList_MT.setLocation(new Point(282, 355));
			jButton_AddToList_MT.addActionListener(this);
		}
		return jButton_AddToList_MT;
	}

	/**
	 * This method initializes jButton_Back_MT	
	 * Pressing on this change the panel to the previous panel without 
	 * save the list of topics.
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Back_MT() {
		if (jButton_Back_MT == null) {
			jButton_Back_MT = new JButton();
			jButton_Back_MT.setText("Back");
			jButton_Back_MT.setSize(new Dimension(208, 34));
			jButton_Back_MT.setLocation(new Point(94, 480));
			jButton_Back_MT.addActionListener(this);
		}
		return jButton_Back_MT;
	}

	/**
	 * This method initializes jButton_Set_MT	
	 * Pressing on this change the panel to the previous panel with 
	 * save the list of topics.
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Set_MT() {
		if (jButton_Set_MT == null) {
			jButton_Set_MT = new JButton();
			jButton_Set_MT.setText("Set");
			jButton_Set_MT.setSize(new Dimension(208, 34));
			jButton_Set_MT.setLocation(new Point(396, 480));
			jButton_Set_MT.addActionListener(this);
		}
		return jButton_Set_MT;
	}

	/**
	 * This method initializes jButton_RemoveFromList_MT	
	 * Pressing on this button removes selected topic/subtopic to the list of topics.
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_RemoveFromList_MT() {
		if (jButton_RemoveFromList_MT == null) {
			jButton_RemoveFromList_MT = new JButton();
			jButton_RemoveFromList_MT.setText("Remove From List");
			jButton_RemoveFromList_MT.setSize(new Dimension(135, 30));
			jButton_RemoveFromList_MT.setLocation(new Point(282, 390));
			jButton_RemoveFromList_MT.addActionListener(this);
		}
		return jButton_RemoveFromList_MT;
	}

	/**
	 * itemStateChanged() responsible on setting the relevant subtopics
	 * in the subtopics combobox.
	 * On choosing topic from the topics combobox, this method will load
	 * the relevant subtopics.
	 */
	public void itemStateChanged(ItemEvent te) {
		  if (te.getItemSelectable() == jComboBox_AvailTopics)
		  {
			  if( jComboBox_AvailTopics.getSelectedItem().toString().compareTo(" ") != 0)
			  {			  
				  String[] subtopics = {""};
				try {
					subtopics = AUser.getInstance().getSubTopics((String)jComboBox_AvailTopics.getSelectedItem());
				    } catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
				    }
				  jComboBox_AvailSubTopics_MT.setEnabled(true);
				  jComboBox_AvailSubTopics_MT.removeAllItems();
				  //jComboBox_AvailSubTopics_MT.addItem("");
				  for(String t : subtopics)
					  jComboBox_AvailSubTopics_MT.addItem(t);			  
				  jTextField_AddSubTopic_MT.setEditable(true);
				  jButton_addSubTopic_MT.setEnabled(true);
				  m_flag = true;
			  }
		  
			  else if(te.getItemSelectable() == jComboBox_AvailTopics && m_flag && jComboBox_AvailTopics.getSelectedItem().toString().compareTo(" ") == 0)
			  {
				  jTextField_AddSubTopic_MT.setEditable(false);
				  jButton_addSubTopic_MT.setEnabled(false);
				  jComboBox_AvailSubTopics_MT.removeAllItems();
				  jComboBox_AvailSubTopics_MT.setEnabled(false);
				  m_flag = false;
			  }
		  }
	}

	/**
	 * actionPerformed handle responsible for action performed.
     * @param e ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == jButton_add_Topic)
		{
			try {
				((CLibrarian)AUser.getInstance()).addTopic(jTextField_AddTopic_MT.getText());
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
			}
			this.remove(jComboBox_AvailTopics);
			this.jComboBox_AvailTopics = null;
			this.add(getJComboBox_AvailTopics(), null);
		}
		else if(source == jButton_addSubTopic_MT)
		{			
		    if (jComboBox_AvailTopics.getSelectedItem().toString().compareTo(" ") != 0)
			{
				try {
					((CLibrarian)AUser.getInstance()).addSubTopic(jComboBox_AvailTopics.getSelectedItem().toString(),jTextField_AddSubTopic_MT.getText());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "You have to choose topic first" ,"Error",JOptionPane.ERROR_MESSAGE);
			}
			if(jComboBox_AvailTopics.getSelectedItem().toString().compareTo(" ") != 0)
			{
				this.remove(jComboBox_AvailSubTopics_MT);
				this.jComboBox_AvailSubTopics_MT = null;
				this.add(getJComboBox_AvailSubTopics_MT(), null);
				String[] subtopics = {""};
				try {
					subtopics = AUser.getInstance().getSubTopics((String)jComboBox_AvailTopics.getSelectedItem());
				    } catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
				    }
				  //jComboBox_AvailSubTopics_MT.addItem("");
				  for(String t : subtopics)
					  jComboBox_AvailSubTopics_MT.addItem(t);
				  jComboBox_AvailSubTopics_MT.setEnabled(true);
			}
		}
		else if(source == jButton_AddToList_MT)
		{
			String toSet = "";
			if((toSet = jComboBox_AvailTopics.getSelectedItem().toString()).compareTo(" ") != 0)
			   if(jComboBox_AvailSubTopics_MT.getSelectedItem().toString().compareTo("") != 0)  
				   toSet = toSet + " : " + jComboBox_AvailSubTopics_MT.getSelectedItem().toString();			
			
			listModel.addElement(toSet);
			this.remove(jList_ChoosenTopics_MT);
			jList_ChoosenTopics_MT = null;
			this.add(getJList_ChoosenTopics_MT());
		}
		else if (source == jButton_RemoveFromList_MT)
		{
			listModel.remove(selection);
			this.remove(jList_ChoosenTopics_MT);
			jList_ChoosenTopics_MT = null;
			this.add(getJList_ChoosenTopics_MT());
		}
		else
		{
			if(m_last.compareTo("ANB") == 0)
			{
				this.setFrom(MTPfrom.ADDBOOK);
			}
			else if(m_last.compareTo("EBD") == 0)
			{
				this.setFrom(MTPfrom.EDITBOOK);
			}
			if(source == jButton_Back_MT)
			{
				this.setLastChoice(MTPDecision.BACK);
				this.setVisible(false);
			}
			else if (source == jButton_Set_MT)
			{
				m_list = jList_ChoosenTopics_MT;
				this.setLastChoice(MTPDecision.SET);
				this.setVisible(false);
			}
		}
	}

	
	/**
	 * valueChanged saving the selected index in the list.
	 */
	public void valueChanged(ListSelectionEvent a) {
		if(!a.getValueIsAdjusting())
		{
			JList lst = (JList)a.getSource();
			if(lst.getSelectedIndex()> -1)
			      selection = lst.getSelectedIndex();
		}
		
	}

}  
