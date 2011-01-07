package server.main;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Point;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.SwingConstants;

public class CServerInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel m_jLabel_IP = null;
	private JLabel m_jLabel_IPA = null;

	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public CServerInfo(String name) throws Exception {
		super();
		initialize(name);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws Exception 
	 */
	private void initialize(String name) throws Exception {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle(name);
		this.setLocationByPlatform(true);
		this.setLocationRelativeTo(null);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 * @throws Exception 
	 */
	private JPanel getJContentPane() throws Exception {
		
		if (jContentPane == null) {
			m_jLabel_IPA = new JLabel();
			m_jLabel_IPA.setFont(new Font("Monotype Corsiva", Font.BOLD, 24));
			m_jLabel_IPA.setSize(new Dimension(285, 100));
			m_jLabel_IPA.setLocation(new Point(0, 60));
			m_jLabel_IPA.setHorizontalAlignment(SwingConstants.CENTER);
			m_jLabel_IPA.setText(""+Inet4Address.getLocalHost().getHostAddress());
			m_jLabel_IP = new JLabel();
			m_jLabel_IP.setText("Server's IP Address: \n ");
			m_jLabel_IP.setFont(new Font("Monotype Corsiva", Font.BOLD, 24));
			m_jLabel_IP.setLocation(new Point(0, 0));
			m_jLabel_IP.setSize(new Dimension(285, 60));
			m_jLabel_IP.setHorizontalAlignment(SwingConstants.CENTER);
			m_jLabel_IP.setPreferredSize(new Dimension(285, 60));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(m_jLabel_IP, null);
			jContentPane.add(m_jLabel_IPA, null);
		}
		return jContentPane;
	}

}
