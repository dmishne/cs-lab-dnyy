package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import client.common.CClientConnector;
import client.core.AUser;
import client.gui.searchbook.CBookDetailPanel;
import client.gui.searchbook.COrderBookPanel;
import client.gui.searchbook.CSearchBookPanel;
import client.gui.searchbook.CSearchResultPanel;
import client.gui.searchbook.CSubmitReviewPanel;
import client.gui.searchreview.CSearchReviewPanel;
import client.gui.searchreview.CReviewsListPanel;
import client.gui.addnewbook.CAddNewBookPanel;
import client.gui.newmessages.CNewReviewsPanel;

public class CMainFrame extends JFrame implements ActionListener,ComponentListener{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private CLoginPanel GUI_CLoginPanel = null;
	private CMainMenuPanel GUI_CMainMenuPanel = null;
	private JLabel m_jLabel_LOGO = null;
	private JMenuBar m_jJMenuBar_MenuBar = null;
	private JMenu m_jMenu_Help = null;
	private JMenuItem m_jMenuItem_Help_About = null;
	private CArrangePayPanel GUI_CArrangePayPanel = null;
	private JMenu m_jMenu_Tools = null;
	private JMenuItem m_jMenuItem_ServerInfo = null;
	private CSearchBookPanel GUI_cSearchBookPanel = null;
	private CSearchResultPanel GUI_CSearchResultPanel = null;
	private CBookDetailPanel GUI_CBookDetailPanel = null;
	private CSubmitReviewPanel GUI_CSubmitReviewPanel = null;
	private COrderBookPanel GUI_COrderBookPanel = null;
	private CSearchReviewPanel GUI_CSearchReviewPanel = null;
	private JButton m_jMenu_goto = null;
	private CReviewsListPanel GUI_CReviewsListPanel = null;
	private CAddNewBookPanel GUI_CAddNewBookPanel = null;
	private CNewReviewsPanel GUI_CNewReviewsPanel = null;
	/**
	 * This is the default constructor
	 */
	public CMainFrame() {
		super();
		initialize();	
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setJMenuBar(getM_jJMenuBar_MenuBar());
		this.setSize(700, 700);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700, 700));
		this.setContentPane(getJContentPane());
		this.setTitle("GoodReading V " + client.main.CGoodReading.Version + "." + client.main.CGoodReading.Revision);
		this.setLocationByPlatform(true);
		this.setLocationRelativeTo(null);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			m_jLabel_LOGO = new JLabel();
			m_jLabel_LOGO.setHorizontalAlignment(SwingConstants.CENTER);
			m_jLabel_LOGO.setFont(new Font("Edwardian Script ITC", Font.BOLD, 72));
			m_jLabel_LOGO.setBounds(new Rectangle(0, 0, 700, 100));
			m_jLabel_LOGO.setText("Good Reading V" + client.main.CGoodReading.Version + "." + client.main.CGoodReading.Revision);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getGUI_CLoginPanel(), null);
			jContentPane.add(m_jLabel_LOGO, null);
			jContentPane.setOpaque(false);
		}
		return jContentPane;
	}

	/**
	 * This method initializes GUI_CLoginPanel	
	 * 	
	 * @return GUI_CLoginPanel	
	 */
	private CLoginPanel getGUI_CLoginPanel() {
		if (GUI_CLoginPanel == null) {
			GUI_CLoginPanel = new CLoginPanel();
			GUI_CLoginPanel.setSize(new Dimension(244, 142));
			GUI_CLoginPanel.setPreferredSize(new Dimension(244, 142));
			GUI_CLoginPanel.setLocation(new Point(228, 279));
			GUI_CLoginPanel.addComponentListener(this);
		}
		return GUI_CLoginPanel;
	}
	
	/*--------------DO NOT TO IMPLEMENT---------------*/
	public void componentMoved(ComponentEvent arg0) {}
	public void componentResized(ComponentEvent arg0) {}
	/*------------------------------------------------*/

	/**
	 * This method initializes GUI_CMainMenuPanel	
	 * 	
	 * @return GUI_CMainMenuPanel	
	 */
	private CMainMenuPanel getGUI_CMainMenuPanel() throws Exception {
		if (GUI_CMainMenuPanel == null) {
			GUI_CMainMenuPanel = new CMainMenuPanel();
			GUI_CMainMenuPanel.setLocation(new Point(0, 100));
			GUI_CMainMenuPanel.setSize(new Dimension(700,550));
			GUI_CMainMenuPanel.setVisible(false);
			GUI_CMainMenuPanel.addComponentListener(this);
		}
		return GUI_CMainMenuPanel;
	}

	/**
	 * This method initializes m_jJMenuBar_MenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getM_jJMenuBar_MenuBar() {
		if (m_jJMenuBar_MenuBar == null) {
			m_jJMenuBar_MenuBar = new JMenuBar();
			m_jJMenuBar_MenuBar.add(getM_jMenu_Tools());
			m_jJMenuBar_MenuBar.add(getM_jMenu_About());
			m_jJMenuBar_MenuBar.add(Box.createHorizontalGlue());
			m_jJMenuBar_MenuBar.add(getM_jMenu_goto());
			
		}
		return m_jJMenuBar_MenuBar;
	}

	/**
	 * This method initializes m_jMenu_Help	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getM_jMenu_About() {
		if (m_jMenu_Help == null) {
			m_jMenu_Help = new JMenu();
			m_jMenu_Help.setText("Help");
			m_jMenu_Help.add(getM_jMenuItem_Help_About());
		}
		return m_jMenu_Help;
	}

	/**
	 * This method initializes m_jMenuItem_Help_About	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getM_jMenuItem_Help_About() {
		if (m_jMenuItem_Help_About == null) {
			m_jMenuItem_Help_About = new JMenuItem();
			m_jMenuItem_Help_About.setText("About");
			m_jMenuItem_Help_About.addActionListener(this);
		}
		return m_jMenuItem_Help_About;
	}
	
	public void componentHidden(ComponentEvent ceh){
		//Think about changing to switch//
		
		Object source = ceh.getSource();
		try
		{
			if(source == GUI_CLoginPanel)
			{
				jContentPane.add(getGUI_CMainMenuPanel());
				jContentPane.remove(GUI_CLoginPanel);
				GUI_CLoginPanel = null;
				GUI_CMainMenuPanel.initGreeting();
				GUI_CMainMenuPanel.setVisible(true);
				getM_jMenu_goto().setEnabled(true);
			}
			else if(source == GUI_CMainMenuPanel)
			{
				if(GUI_CMainMenuPanel.getLastChoice() ==  CMainMenuPanel.EMMDecision.LOGOUT)
				{
					jContentPane.remove(GUI_CMainMenuPanel);
					jContentPane.add(getGUI_CLoginPanel());
					m_jMenu_goto.setEnabled(false);
					GUI_CLoginPanel.setVisible(true);
				}
				else if(GUI_CMainMenuPanel.getLastChoice() == CMainMenuPanel.EMMDecision.ARRANGE)
				{
					GUI_CArrangePayPanel = null;
					jContentPane.add(getGUI_CArrangePayPanel());
					GUI_CMainMenuPanel.setVisible(false);
					GUI_CArrangePayPanel.setVisible(true);
				}
				else if(GUI_CMainMenuPanel.getLastChoice() == CMainMenuPanel.EMMDecision.SEARCHBOOK)
				{
					GUI_cSearchBookPanel = null;
					jContentPane.add(getGUI_cSearchBookPanel());
					GUI_CMainMenuPanel.setVisible(false);
					GUI_cSearchBookPanel.setVisible(true);
				}
				else if(GUI_CMainMenuPanel.getLastChoice() == CMainMenuPanel.EMMDecision.SEARCHREVIEW)
				{
					GUI_CSearchReviewPanel = null;
					jContentPane.add(getGUI_CSearchReviewPanel());
					GUI_CMainMenuPanel.setVisible(false);
					GUI_CSearchReviewPanel.setVisible(true);
				}
				else if(GUI_CMainMenuPanel.getLastChoice() == CMainMenuPanel.EMMDecision.ADDNEWBOOK)
				{
					GUI_CAddNewBookPanel = null;
					jContentPane.add(getGUI_CAddNewBookPanel());
					GUI_CMainMenuPanel.setVisible(false);
					GUI_CAddNewBookPanel.setVisible(true);
				}
				else if(GUI_CMainMenuPanel.getLastChoice() == CMainMenuPanel.EMMDecision.NEWMSGS)
				{
					GUI_CNewReviewsPanel = null;
					jContentPane.add(getGUI_CNewReviewsPanel());
					GUI_CMainMenuPanel.setVisible(false);
					GUI_CNewReviewsPanel.setVisible(true);
				}	
			}
			else if(source == GUI_CArrangePayPanel)
			{
				jContentPane.remove(GUI_CArrangePayPanel);
				GUI_CArrangePayPanel = null;
				GUI_CMainMenuPanel.setVisible(true);
			}
			else if(source == GUI_cSearchBookPanel)
			{
				if(GUI_cSearchBookPanel.getLastChoice() == CSearchBookPanel.SBPDecision.BACK)
				{
					jContentPane.remove(GUI_cSearchBookPanel);
					GUI_cSearchBookPanel = null;
					GUI_CMainMenuPanel.setVisible(true);
				}
				else if(GUI_cSearchBookPanel.getLastChoice() == CSearchBookPanel.SBPDecision.SEARCH)
				{
					try{
						GUI_CSearchResultPanel = null;
						jContentPane.add(getGUI_CSearchResultPanel());
						GUI_cSearchBookPanel.setVisible(false);
						GUI_CSearchResultPanel.setVisible(true);
					}
					catch (Exception e)
					{
						if(GUI_CSearchResultPanel != null)
						{
							jContentPane.remove(GUI_CSearchResultPanel);
						}
						GUI_CSearchResultPanel = null;
						jContentPane.add(GUI_cSearchBookPanel);
						GUI_cSearchBookPanel.setVisible(true);
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			else if(source == GUI_CSearchResultPanel)
			{
				if(GUI_CSearchResultPanel.getLastChoice() == CSearchResultPanel.SRPDecision.BACK)
				{
					jContentPane.remove(GUI_CSearchResultPanel);
					GUI_CSearchResultPanel = null;
					GUI_cSearchBookPanel.setVisible(true);
				}
				else
				{
					GUI_CBookDetailPanel = null;
					jContentPane.add(getGUI_CBookDetailPanel());
					GUI_CSearchResultPanel.setVisible(false);
					GUI_CBookDetailPanel.setVisible(true);
				}
			}
			else if(source == GUI_CBookDetailPanel)
			{
				if(GUI_CBookDetailPanel.getLastChoice() == CBookDetailPanel.EBDDecision.BACK)
				{
					jContentPane.remove(GUI_CBookDetailPanel);
					GUI_CBookDetailPanel = null;
					GUI_CSearchResultPanel.setVisible(true);
				}
				else if(GUI_CBookDetailPanel.getLastChoice() == CBookDetailPanel.EBDDecision.REVIEW)
				{
					GUI_CSubmitReviewPanel = null;
					jContentPane.add(getGUI_CSubmitReviewPanel());
					GUI_CBookDetailPanel.setVisible(false);
					GUI_CSubmitReviewPanel.setVisible(true);
				}
				else if(GUI_CBookDetailPanel.getLastChoice() == CBookDetailPanel.EBDDecision.ORDER)
				{
					GUI_COrderBookPanel = null;
					jContentPane.add(getGUI_COrderBookPanel());
					GUI_CBookDetailPanel.setVisible(false);
					GUI_COrderBookPanel.setVisible(true);
				}
			}
			else if(source == GUI_CSubmitReviewPanel)
			{
				if(GUI_CSubmitReviewPanel.getLastChoice() == CSubmitReviewPanel.ESRDecision.BACK)
				{
					jContentPane.remove(GUI_CSubmitReviewPanel);
					GUI_CSubmitReviewPanel = null;
					GUI_CBookDetailPanel.setVisible(true);
				}
			}
			else if (source == GUI_COrderBookPanel)
			{
				if(GUI_COrderBookPanel.getLastChoice() == COrderBookPanel.EOBDecision.BACK)
				{
					jContentPane.remove(GUI_COrderBookPanel);
					GUI_COrderBookPanel = null;
					GUI_CBookDetailPanel.setVisible(true);
				}
			}
			else if (source == GUI_CSearchReviewPanel)
			{
				if(GUI_CSearchReviewPanel.getLastChoice() == CSearchReviewPanel.SRPDecision.BACK)
				{
					jContentPane.remove(GUI_CSearchReviewPanel);
					GUI_CSearchReviewPanel = null;
					GUI_CMainMenuPanel.setVisible(true);
				}
				else if(GUI_CSearchReviewPanel.getLastChoice() == CSearchReviewPanel.SRPDecision.SEARCH)
				{
					try
					{
						GUI_CReviewsListPanel = null;
						jContentPane.add(getGUI_CReviewsListPanel());
						GUI_CSearchReviewPanel.setVisible(false);
						GUI_CReviewsListPanel.setVisible(true);
					}
					catch (Exception e)
					{
						if(GUI_CReviewsListPanel != null)
						{
							jContentPane.remove(GUI_CReviewsListPanel);
						}
						GUI_CReviewsListPanel = null;
						GUI_CSearchReviewPanel.setVisible(true);
						JOptionPane.showMessageDialog(null, "No reviews available", "Error",JOptionPane.ERROR_MESSAGE);	
					}
				}
			}
			else if(source == GUI_CReviewsListPanel)
			{
				if(GUI_CReviewsListPanel.getLastChoice() == CReviewsListPanel.RLPDecision.BACK)
				{
					jContentPane.remove(GUI_CReviewsListPanel);
					GUI_CReviewsListPanel = null;
					GUI_CSearchReviewPanel.setVisible(true);
				}
			}
			else if(source == GUI_CAddNewBookPanel)
			{
				if(GUI_CAddNewBookPanel.getLastChoice() == CAddNewBookPanel.ANBDecision.BACK)
				{
					jContentPane.remove(GUI_CAddNewBookPanel);
					GUI_CAddNewBookPanel = null;
					GUI_CMainMenuPanel.setVisible(true);
				}
			}
			else if(source == GUI_CNewReviewsPanel)
			{
				if(GUI_CNewReviewsPanel.getLastChoice() == CNewReviewsPanel.NRPDecision.BACK)
				{
					jContentPane.remove(GUI_CNewReviewsPanel);
					GUI_CNewReviewsPanel = null;
					GUI_CMainMenuPanel.setVisible(true);
				}
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);	
		}
		pack();
		validate();		
	}

	public void componentShown(ComponentEvent ces) {
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jMenuItem_Help_About)
		{
			JOptionPane.showMessageDialog(null, "Group 9\n\nGroup Members:\n\nDaniel Mishne\nEvgeny Radbel\nNir Geffen\nYotam Margolin" ,"About",JOptionPane.INFORMATION_MESSAGE);
		}
		else if(source == m_jMenuItem_ServerInfo)
		{
			String IP = JOptionPane.showInputDialog(null, "Enter Server's ip address", "IP Input", JOptionPane.QUESTION_MESSAGE );
			Pattern pip = Pattern.compile("\\p{Digit}{1,3}\\.\\p{Digit}{1,3}\\.\\p{Digit}{1,3}\\.\\p{Digit}{1,3}");
			Matcher mu = pip.matcher(IP);
			if(mu.matches() || (IP.compareTo("localhost") == 0) )
				CClientConnector.setConnectionHost(IP);
			else
			{
				JOptionPane.showMessageDialog(null, "Wrong input" ,"Error",JOptionPane.ERROR_MESSAGE);
			}	
		}
		else if(source == m_jMenu_goto)
		{
			try {
				AUser.getInstance();
				jContentPane.removeAll();
				jContentPane.add(getGUI_CLoginPanel());
				jContentPane.add(getGUI_CMainMenuPanel());
				GUI_CLoginPanel.setVisible(false);
				GUI_CMainMenuPanel.setVisible(true);
				pack();
				validate();
				}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Login first" ,"Error",JOptionPane.ERROR_MESSAGE);
				}
		}
	}

	/**
	 * This method initializes GUI_CArrangePayPanel	
	 * 	
	 * @return client.gui.CArrangePayPanel	
	 */
	private CArrangePayPanel getGUI_CArrangePayPanel() {
		if (GUI_CArrangePayPanel == null) {
			GUI_CArrangePayPanel = new CArrangePayPanel();
			GUI_CArrangePayPanel.setBounds(new Rectangle(150, 150, 400, 400));
			GUI_CArrangePayPanel.addComponentListener(this);
		}
		return GUI_CArrangePayPanel;
	}

	/**
	 * This method initializes m_jMenu_Tools	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getM_jMenu_Tools() {
		if (m_jMenu_Tools == null) {
			m_jMenu_Tools = new JMenu();
			m_jMenu_Tools.setText("Tools");
			m_jMenu_Tools.add(getM_jMenuItem_ServerInfo());
		}
		return m_jMenu_Tools;
	}

	/**
	 * This method initializes m_jMenuItem_ServerInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getM_jMenuItem_ServerInfo() {
		if (m_jMenuItem_ServerInfo == null) {
			m_jMenuItem_ServerInfo = new JMenuItem();
			m_jMenuItem_ServerInfo.setText("Set Server's IP address");
			m_jMenuItem_ServerInfo.addActionListener(this);
		}
		return m_jMenuItem_ServerInfo;
	}

	/**
	 * This method initializes GUI_cSearchBookPanel	
	 * 	
	 * @return client.gui.CSearchBookPanel	
	 */
	private CSearchBookPanel getGUI_cSearchBookPanel() throws Exception{
		if (GUI_cSearchBookPanel == null) {
			GUI_cSearchBookPanel = new CSearchBookPanel();
			GUI_cSearchBookPanel.setLocation(new Point(0, 100));
			GUI_cSearchBookPanel.setSize(new Dimension(700, 550));
			GUI_cSearchBookPanel.setVisible(false);
			GUI_cSearchBookPanel.addComponentListener(this);
		}
		return GUI_cSearchBookPanel;
	}

	/**
	 * This method initializes GUI_CSearchResultPanel	
	 * 	
	 * @return client.gui.CSearchResultPanel	
	 * @throws Exception 
	 */
	private CSearchResultPanel getGUI_CSearchResultPanel() throws Exception {
		if (GUI_CSearchResultPanel == null) {
			GUI_CSearchResultPanel = new CSearchResultPanel();
			GUI_CSearchResultPanel.setSize(new Dimension(700, 550));
			GUI_CSearchResultPanel.setLocation(new Point(0, 100));
			GUI_CSearchResultPanel.setVisible(false);
			GUI_CSearchResultPanel.addComponentListener(this);
		}
		return GUI_CSearchResultPanel;
	}

	/**
	 * This method initializes GUI_CBookDetailPanel	
	 * 	
	 * @return client.gui.CBookDetailPanel	
	 */
	private CBookDetailPanel getGUI_CBookDetailPanel() {
		if (GUI_CBookDetailPanel == null) {
			GUI_CBookDetailPanel = new CBookDetailPanel(CSearchResultPanel.getChosenBook());
			GUI_CBookDetailPanel.setLocation(new Point(0, 100));
			GUI_CBookDetailPanel.setPreferredSize(new Dimension(700, 600));
			GUI_CBookDetailPanel.setSize(new Dimension(700, 550));
			GUI_CBookDetailPanel.setVisible(false);
			GUI_CBookDetailPanel.addComponentListener(this);
		}
		return GUI_CBookDetailPanel;
	}

	/**
	 * This method initializes GUI_CSubmitReviewPanel	
	 * 	
	 * @return client.gui.CSubmitReviewPanel	
	 */
	private CSubmitReviewPanel getGUI_CSubmitReviewPanel() {
		if (GUI_CSubmitReviewPanel == null) {
			GUI_CSubmitReviewPanel = new CSubmitReviewPanel();
			GUI_CSubmitReviewPanel.setSize(new Dimension(700, 550));
			GUI_CSubmitReviewPanel.setLocation(new Point(0, 100));
			GUI_CSubmitReviewPanel.setVisible(false);
			GUI_CSubmitReviewPanel.addComponentListener(this);
		}
		return GUI_CSubmitReviewPanel;
	}

	/**
	 * This method initializes GUI_COrderBookPanel	
	 * 	
	 * @return client.gui.COrderBookPanel	
	 * @throws Exception 
	 */
	private COrderBookPanel getGUI_COrderBookPanel() throws Exception {
		if (GUI_COrderBookPanel == null) {
			GUI_COrderBookPanel = new COrderBookPanel();
			GUI_COrderBookPanel.setPreferredSize(new Dimension(700, 600));
			GUI_COrderBookPanel.setSize(new Dimension(700, 550));
			GUI_COrderBookPanel.setLocation(new Point(0, 100));
			GUI_COrderBookPanel.setVisible(false);
			GUI_COrderBookPanel.addComponentListener(this);
		}
		return GUI_COrderBookPanel;
	}

	/**
	 * This method initializes GUI_CSearchReviewPanel	
	 * 	
	 * @return client.gui.CSearchReviewPanel	
	 * @throws Exception 
	 */
	private CSearchReviewPanel getGUI_CSearchReviewPanel() {
		if (GUI_CSearchReviewPanel == null) {
			GUI_CSearchReviewPanel = new CSearchReviewPanel();
			GUI_CSearchReviewPanel.setLocation(new Point(0, 100));
			GUI_CSearchReviewPanel.setSize(new Dimension(700, 550));
			GUI_CSearchReviewPanel.setVisible(false);
			GUI_CSearchReviewPanel.addComponentListener(this);
		}
		return GUI_CSearchReviewPanel;
	}

	/**
	 * This method initializes m_jMenu_goto	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JButton getM_jMenu_goto() {
		if (m_jMenu_goto == null) {
			m_jMenu_goto = new JButton("Back To Main Menu");
			m_jMenu_goto.setBorderPainted(false);
			m_jMenu_goto.addActionListener(this);
			m_jMenu_goto.setEnabled(false);
		}
		return m_jMenu_goto;
	}

	/**
	 * This method initializes GUI_CReviewsListPanel	
	 * 	
	 * @return client.gui.searchreview.CReviewsListPanel	
	 */
	private CReviewsListPanel getGUI_CReviewsListPanel() {
		if (GUI_CReviewsListPanel == null) {
			GUI_CReviewsListPanel = new CReviewsListPanel();
			GUI_CReviewsListPanel.setPreferredSize(new Dimension(700, 600));
			GUI_CReviewsListPanel.setLocation(new Point(0, 100));
			GUI_CReviewsListPanel.setSize(new Dimension(700, 550));
			GUI_CReviewsListPanel.addComponentListener(this);
			GUI_CReviewsListPanel.setVisible(false);
		}
		return GUI_CReviewsListPanel;
	}

	/**
	 * This method initializes GUI_CAddNewBookPanel	
	 * 	
	 * @return client.gui.addnewbook.CAddNewBookPanel	
	 */
	private CAddNewBookPanel getGUI_CAddNewBookPanel() {
		if (GUI_CAddNewBookPanel == null) {
			GUI_CAddNewBookPanel = new CAddNewBookPanel();
			GUI_CAddNewBookPanel.setPreferredSize(new Dimension(700, 600));
			GUI_CAddNewBookPanel.setSize(new Dimension(700, 550));
			GUI_CAddNewBookPanel.setLocation(new Point(0, 100));
			GUI_CAddNewBookPanel.addComponentListener(this);
			GUI_CAddNewBookPanel.setVisible(false);
		}
		return GUI_CAddNewBookPanel;
	}

	/**
	 * This method initializes GUI_CNewReviewsPanel	
	 * 	
	 * @return client.gui.newmessages.CNewReviewsPanel	
	 */
	private CNewReviewsPanel getGUI_CNewReviewsPanel() {
		if (GUI_CNewReviewsPanel == null) {
			GUI_CNewReviewsPanel = new CNewReviewsPanel();
			GUI_CNewReviewsPanel.setSize(new Dimension(700, 550));
			GUI_CNewReviewsPanel.setPreferredSize(new Dimension(700, 550));
			GUI_CNewReviewsPanel.setLocation(new Point(0, 100));
			GUI_CNewReviewsPanel.addComponentListener(this);
			GUI_CNewReviewsPanel.setVisible(false);
		}
		return GUI_CNewReviewsPanel;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
