package client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import client.common.CClientConnector;

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
			}
			else if(source == GUI_CMainMenuPanel)
			{
				if(GUI_CMainMenuPanel.getLastChoice() ==  CMainMenuPanel.EMMDecision.LOGOUT)
				{
					jContentPane.remove(GUI_CMainMenuPanel);
					jContentPane.add(getGUI_CLoginPanel());
					GUI_CLoginPanel.setVisible(true);
				}
				else if(GUI_CMainMenuPanel.getLastChoice() == CMainMenuPanel.EMMDecision.ARRANGE)
				{
					jContentPane.add(getGUI_CArrangePayPanel());
					GUI_CMainMenuPanel.setVisible(false);
					GUI_CArrangePayPanel.setVisible(true);
				}
				else if(GUI_CMainMenuPanel.getLastChoice() == CMainMenuPanel.EMMDecision.SEARCHBOOK)
				{
					jContentPane.add(getGUI_cSearchBookPanel());
					GUI_CMainMenuPanel.setVisible(false);
					GUI_cSearchBookPanel.setVisible(true);
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
					jContentPane.add(getGUI_CSearchResultPanel());
					GUI_cSearchBookPanel.setVisible(false);
					GUI_CSearchResultPanel.setVisible(true);
				}
			}
			else if(source == GUI_CSearchResultPanel)
			{
				jContentPane.remove(GUI_CSearchResultPanel);
				GUI_CSearchResultPanel = null;
				GUI_cSearchBookPanel.setVisible(true);
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
	 */
	private CSearchResultPanel getGUI_CSearchResultPanel() {
		if (GUI_CSearchResultPanel == null) {
			GUI_CSearchResultPanel = new CSearchResultPanel();
			GUI_CSearchResultPanel.setSize(new Dimension(700, 550));
			GUI_CSearchResultPanel.setLocation(new Point(0, 100));
			GUI_CSearchResultPanel.setVisible(false);
			GUI_CSearchResultPanel.addComponentListener(this);
		}
		return GUI_CSearchResultPanel;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
