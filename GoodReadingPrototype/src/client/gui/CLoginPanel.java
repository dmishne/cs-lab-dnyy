package client.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class CLoginPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel m_JLabel_Username = null;
	private JLabel m_JLabel_Password = null;
	private JTextField m_jTextField_Username = null;
	private JPasswordField m_jPasswordField_Password = null;
	private JButton m_jButton_Login = null;
	
	/**
	 * This is the default constructor
	 */
	public CLoginPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
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
		}
		return m_jPasswordField_Password;
	}

	/**
	 * This method initializes m_jButton_Login	
	 * 	
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

	/* 
	 * This method responsible on the actions occur in this container
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
}  //  @jve:decl-index=0:visual-constraint="44,10"
