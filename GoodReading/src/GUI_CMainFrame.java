import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

public class GUI_CMainFrame extends JFrame implements ComponentListener{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private GUI_CLoginPanel GUI_CLoginPanel = null;
	private GUI_CMainMenuPanel GUI_CMainMenuPanel = null;
	private JLabel m_jLabel_LOGO = null;
	/**
	 * This is the default constructor
	 */
	public GUI_CMainFrame() {
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
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700, 700));
		this.setContentPane(getJContentPane());
		this.setTitle("GoodReading V " + CGoodReading.Version + "." + CGoodReading.Revision);
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
			m_jLabel_LOGO.setText("Good Reading V" + CGoodReading.Version + "." + CGoodReading.Revision);
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
	private GUI_CLoginPanel getGUI_CLoginPanel() {
		if (GUI_CLoginPanel == null) {
			GUI_CLoginPanel = new GUI_CLoginPanel();
			GUI_CLoginPanel.setSize(new Dimension(244, 142));
			GUI_CLoginPanel.setPreferredSize(new Dimension(244, 142));
			GUI_CLoginPanel.setLocation(new Point(228, 279));
			GUI_CLoginPanel.addComponentListener(this);
		}
		return GUI_CLoginPanel;
	}
	
	@SuppressWarnings("static-access")
	public void componentHidden(ComponentEvent ceh) {
		Object source = ceh.getSource();
		if(source == GUI_CLoginPanel)
		{
			GUI_CMainMenuPanel.setVisible(true);
		}
		else if(source == GUI_CMainMenuPanel)
		{
			if(GUI_CMainMenuPanel.getM_lastChoice() == GUI_CMainMenuPanel.getM_lastChoice().LOGOUT);
			{
				GUI_CMainMenuPanel.setVisible(true);
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
	private GUI_CMainMenuPanel getGUI_CMainMenuPanel() {
		if (GUI_CMainMenuPanel == null) {
			GUI_CMainMenuPanel = new GUI_CMainMenuPanel();
			GUI_CMainMenuPanel.setLocation(new Point(100, 100));
			GUI_CMainMenuPanel.setSize(new Dimension(500, 500));
			GUI_CMainMenuPanel.setVisible(false);
			GUI_CMainMenuPanel.addComponentListener(this);
		}
		return GUI_CMainMenuPanel;
	}
	
	

}  //  @jve:decl-index=0:visual-constraint="10,10"
