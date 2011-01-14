package client.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
/**
 * CLoginPanel is a Login Panel
 * With this panel, the user can login with his username and password.
 */
public class CLoginPanel extends JPanel implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private JLabel m_JLabel_Username = null;
	private JLabel m_JLabel_Password = null;
	private JTextField m_jTextField_Username = null;
	private JPasswordField m_jPasswordField_Password = null;
	private JButton m_jButton_Login = null;
	
	/**
	 * CLoginPanel() is the default constructor
	 */
	public CLoginPanel() {
		super();
		initialize();
	}

	/**
	 * initialize() initializes this class
	 * 
	 * @return void
	 */
	private void initialize() {
		m_JLabel_Password = new JLabel();
		m_JLabel_Password.setBounds(new Rectangle(15, 65, 76, 25));
		m_JLabel_Password.setHorizontalAlignment(SwingConstants.RIGHT);
		m_JLabel_Password.setText("Password:");
		m_JLabel_Username = new JLabel();
		m_JLabel_Username.setText("Username:");
		m_JLabel_Username.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		m_JLabel_Username.setHorizontalTextPosition(SwingConstants.TRAILING);
		m_JLabel_Username.setHorizontalAlignment(SwingConstants.RIGHT);
		m_JLabel_Username.setBounds(new Rectangle(15, 28, 76, 25));
		this.setSize(244, 142);
		this.setLayout(null);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		this.add(m_JLabel_Username, null);
		this.add(m_JLabel_Password, null);
		this.add(getM_jTextField_Username(), null);
		this.add(getM_jPasswordField_Password(), null);
		this.add(getM_jButton_Login(), null);
		this.addKeyListener(this);
	}
	
	
	/**
	 * keyPressed() checks if Enter was pressed, is yes, trying to log in.
	 * @param e KeyEvent
	 */
	public void keyPressed(KeyEvent e) {
		char[] inputPasswordField =  m_jPasswordField_Password.getPassword();
		String Pass = String.valueOf(inputPasswordField, 0, inputPasswordField.length);
		String User = m_jTextField_Username.getText(); 
		if(e.getKeyChar() == KeyEvent.VK_ENTER  &&  !Pass.isEmpty()  && !User.isEmpty()) 
		{
			try {
				client.core.AUser.login(User,Pass);
				this.setVisible(false); 
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
			}   
		}
	}


	/**
	 * This method initializes m_jTextField_Username	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getM_jTextField_Username() {
		if (m_jTextField_Username == null) {
			m_jTextField_Username = new JTextField();
			m_jTextField_Username.setBounds(new Rectangle(105, 28, 123, 25));
			m_jTextField_Username.addKeyListener(this);
		}
		return m_jTextField_Username;
	}

	/**
	 * This method initializes m_jPasswordField_Password	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getM_jPasswordField_Password() {
		if (m_jPasswordField_Password == null) {
			m_jPasswordField_Password = new JPasswordField();
			m_jPasswordField_Password.setBounds(new Rectangle(104, 65, 124, 25));
			m_jPasswordField_Password.addKeyListener(this);
		}
		return m_jPasswordField_Password;
	}

	/**
	 * This method initializes m_jButton_Login	
	 * Pressing on m_jButton_Login will cause trying to login
	 * using username and password. 	
	 * @return javax.swing.JButton	
	 */
	private JButton getM_jButton_Login() {
		if (m_jButton_Login == null) {
			m_jButton_Login = new JButton();
			m_jButton_Login.setBounds(new Rectangle(54, 102, 131, 26));
			m_jButton_Login.setText("Login");
			m_jButton_Login.addActionListener(this);
		}
		return m_jButton_Login;
	}

	/** 
	 * This method responsible on the actions occur in this panel
	 * Login: try to login using given username and password.
	 */
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == m_jButton_Login)
		{
			char[] inputPasswordField =  m_jPasswordField_Password.getPassword();
			String Pass = String.valueOf(inputPasswordField, 0, inputPasswordField.length);
			String User = m_jTextField_Username.getText();
			
			try {
				client.core.AUser.login(User,Pass);
				this.setVisible(false);
			} 
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Does Nothing
	 */
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Does Nothing
	 */
	public void keyTyped(KeyEvent e) {	
	}
}  //  @jve:decl-index=0:visual-constraint="44,10"
