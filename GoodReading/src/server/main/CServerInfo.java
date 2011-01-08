package server.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class CServerInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel m_jLabel_IP = null;
	private JLabel m_jLabel_IPA = null;
	private JProgressBar m_jProgressBar_loader = null;
	private int m_progress;

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
		m_progress = 0;
		this.setSize(300, 180);
		this.setContentPane(getJContentPane());
		this.setTitle(name);
		this.setLocationByPlatform(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_jLabel_IPA.setVisible(false);
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
			m_jLabel_IPA.setSize(new Dimension(285, 87));
			m_jLabel_IPA.setLocation(new Point(0, 73));
			m_jLabel_IPA.setHorizontalAlignment(SwingConstants.CENTER);
			m_jLabel_IP = new JLabel();
			m_jLabel_IP.setText("Loading Server...");
			m_jLabel_IP.setFont(new Font("Monotype Corsiva", Font.BOLD, 24));
			m_jLabel_IP.setLocation(new Point(0, 0));
			m_jLabel_IP.setSize(new Dimension(285, 45));
			m_jLabel_IP.setHorizontalAlignment(SwingConstants.CENTER);
			m_jLabel_IP.setPreferredSize(new Dimension(285, 60));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(m_jLabel_IP, null);
			jContentPane.add(m_jLabel_IPA, null);
			jContentPane.add(getM_jProgressBar_loader(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes m_jProgressBar_loader	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getM_jProgressBar_loader() {
		if (m_jProgressBar_loader == null) {
			m_jProgressBar_loader = new JProgressBar(0,4);
			m_jProgressBar_loader.setBounds(new Rectangle(5, 50, 285, 25));
			m_jProgressBar_loader.setStringPainted(true);
			m_jProgressBar_loader.setValue(m_progress);
		}
		return m_jProgressBar_loader;
	}
	
	public void incProgress(){
		m_progress++;
		m_jProgressBar_loader.setValue(m_progress);
	}
	
	
	public void done()
	{
		if(m_jProgressBar_loader.getValue() == 3)
		{
			m_jProgressBar_loader.setValue(4);
			m_jProgressBar_loader.setString("Server Loading Completed");
			m_jLabel_IP.setText("Server's IP Address: \n ");
			m_jLabel_IPA.setText(getIPAddress());
			m_jLabel_IPA.setVisible(true);
		}
		else
		{
			m_jProgressBar_loader.setValue(0);
			m_jProgressBar_loader.setString("Error! Close all instances then restart");
			
		}
	}
	
	
	
	
	
	/** Returns the IP address that the server */
	public String getIPAddress() {

		NetworkInterface ni = null;
		Enumeration<NetworkInterface> eni;
		Enumeration<InetAddress> eip = null;
		InetAddress ip = null;
		boolean check = true;
		try {
			eni = NetworkInterface.getNetworkInterfaces();
			while(check)
			{
				while(eni.hasMoreElements())
				{
					ni = eni.nextElement();
					if(ni.isUp())
					{
						break;
					}
				}
				if(!eni.hasMoreElements())
				{
					check = false;
				}
				eip = ni.getInetAddresses();
				while(eip.hasMoreElements())
				{
					ip = eip.nextElement();
					if(ip.getHostAddress().indexOf(":") == -1 && ip.getHostName().compareTo("127.0.0.1")!=0)
					{
						check = false;
						break;
					}
					ip = null;
				}	
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		if(ip != null)
		{
			return ip.getHostAddress();
		}
		else
		{
			return "Error";
		}
	}

	

}
