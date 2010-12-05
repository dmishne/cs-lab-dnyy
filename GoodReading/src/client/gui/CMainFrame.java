package client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
			m_jLabel_LOGO.setHorizontalAlignment(SwingConstants.CENTER);
			m_jLabel_LOGO.setFont(new Font("Edwardian Script ITC", Font.BOLD, 72));
			m_jLabel_LOGO.setBounds(new Rectangle(0, 0, 700, 100));
			m_jLabel_LOGO.setText("Good Reading V" + client.main.CGoodReading.Version + "." + client.main.CGoodReading.Revision);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getGUI_CLoginPanel(), null);
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
	
	public void componentHidden(ComponentEvent ceh) {
		//Think about changing to switch//
		
		Object source = ceh.getSource();
		if(source == GUI_CLoginPanel)
		{
			jContentPane.add(getGUI_CMainMenuPanel());
			GUI_CLoginPanel.setVisible(false);
			GUI_CMainMenuPanel.setVisible(true);
		}
		else if(source == GUI_CMainMenuPanel)
		{
			if(GUI_CMainMenuPanel.getLastChoice() ==  CMainMenuPanel.EMMDecision.LOGOUT)
			{
				jContentPane.remove(GUI_CMainMenuPanel);
				GUI_CLoginPanel.setVisible(true);
			}
			if(GUI_CMainMenuPanel.getLastChoice() == CMainMenuPanel.EMMDecision.ARRANGE)
			{
				jContentPane.add(getGUI_CArrangePayPanel());
				GUI_CMainMenuPanel.setVisible(false);
				GUI_CArrangePayPanel.setVisible(true);
			}
		}
		else if(source == GUI_CArrangePayPanel)
		{
			jContentPane.remove(GUI_CArrangePayPanel);
			GUI_CMainMenuPanel.setVisible(true);
		}
		
		
		
		
	}

	public void componentShown(ComponentEvent ces) {
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jMenuItem_Help_About)
		{
			JOptionPane.showMessageDialog(null, "Group 9\n\nGroup Members:\n\nDaniel Mishne\nEvgeny Radbel\nNir Geffen\nYotam Margolin" ,"About",JOptionPane.INFORMATION_MESSAGE);
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
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
