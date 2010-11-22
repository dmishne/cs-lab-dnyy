package client.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class CMainFrame extends JFrame implements ActionListener,ComponentListener{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private CLoginPanel GUI_CLoginPanel = null;
	private CMainMenuPanel GUI_CMainMenuPanel = null;
	private JLabel m_jLabel_LOGO = null;
	private JMenuBar m_jJMenuBar_MenuBar = null;
	private JMenu m_jMenu_Help = null;
	private JMenuItem m_jMenuItem_Help_About = null;
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
		this.setSize(700, 700);
		this.setJMenuBar(getM_jJMenuBar_MenuBar());
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
			m_jLabel_LOGO.setBounds(new Rectangle(3, 3, 680, 94));
			m_jLabel_LOGO.setHorizontalAlignment(SwingConstants.CENTER);
			m_jLabel_LOGO.setFont(new Font("Edwardian Script ITC", Font.BOLD, 72));
			m_jLabel_LOGO.setText("Good Reading V" + client.main.CGoodReading.Version + "." + client.main.CGoodReading.Revision);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getGUI_CLoginPanel(), null);
			jContentPane.add(getGUI_CMainMenuPanel(), null);
			jContentPane.add(m_jLabel_LOGO, null);
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
	
	
	/* -------------------FIX IT--------------------  */
	@SuppressWarnings("static-access")
	/* Probably need to declare ENUM in another class
	 * or to use Strings/Integers instead             */
	/* ---------------------------------------------- */
	public void componentHidden(ComponentEvent ceh) {
		Object source = ceh.getSource();
		if(source == GUI_CLoginPanel)
		{
			GUI_CMainMenuPanel.setVisible(true);
		}
		else if(source == GUI_CMainMenuPanel)
		{
			if(GUI_CMainMenuPanel.getM_lastChoice() ==  GUI_CMainMenuPanel.getM_lastChoice().LOGOUT);
			{
				GUI_CLoginPanel.setVisible(true);
			}
		}
	}

	public void componentShown(ComponentEvent ces) {
		
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
	private CMainMenuPanel getGUI_CMainMenuPanel() {
		if (GUI_CMainMenuPanel == null) {
			GUI_CMainMenuPanel = new CMainMenuPanel();
			GUI_CMainMenuPanel.setLocation(new Point(100, 100));
			GUI_CMainMenuPanel.setSize(new Dimension(500, 500));
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

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jMenuItem_Help_About)
		{
			JOptionPane.showMessageDialog(null, "Group 9\n\nGroup Members:\nDaniel Mishne\nEvgeny Radbel\nNir Geffen\nYotam Margolin" ,"About",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
