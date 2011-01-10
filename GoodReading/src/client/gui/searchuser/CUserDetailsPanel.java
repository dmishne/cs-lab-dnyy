package client.gui.searchuser;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import client.core.AUser;
import client.core.CLibraryManager;
import client.core.EActor;
import common.data.CUser;


public class CUserDetailsPanel extends JPanel implements ActionListener,ItemListener{

	private static final long serialVersionUID = 1L;
	private JButton jButton_Edit_UD = null;
	private JButton jButton_Back_UD = null;
	private JButton jButton_showReport_UD = null;
	private JButton jButton_Save_UD = null;
	private JLabel jLabel_mainLabel = null;
	private JLabel jLabel_userName = null;
	private JLabel jLabel_UserID = null;
	private JLabel jLabel_firstName = null;
	private JLabel jLabel_lastName = null;
	private JLabel jLabel_Privilage = null;
	private JLabel jLabel_suspend = null;
	private JLabel jLabel_payType = null;
	private JLabel jLabel_adress = null;
	private JLabel jLabel_birthDay = null;
	private JTextField jTextField_userName = null;
	private JTextField jTextField_userID = null;
	private JTextField jTextField_firstName = null;
	private JTextField jTextField_lastName = null;
	private JTextField jTextField_adress = null;
	private JTextField jTextField_birthDay = null;
	private JComboBox jComboBox_privilage = null;
	private JCheckBox jCheckBox_yearly = null;
	private JCheckBox jCheckBox_monthly = null;
	private JCheckBox jCheckBox_creditCard = null;
	private JLabel jLabel_yearly = null;
	private JLabel jLabel_monthly = null;
	private JLabel jLabel_creditCard = null;
	private JCheckBox jCheckBox_suspend = null;
	private boolean edit_flag;
	private CUser chosenUser=null;
	private UDDecision m_lastChoice = null;
	
	
	public enum UDDecision
	{
		BACK,SHOWREPORT,SAVE
	}
	
	/**
	 * This is the default constructor
	 */
	public CUserDetailsPanel() {
		super();
		chosenUser = CShowUserListPanel.getChosenUser();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel_creditCard = new JLabel();
		jLabel_creditCard.setText("Credit Card");
		jLabel_creditCard.setSize(new Dimension(80, 30));
		jLabel_creditCard.setLocation(new Point(120, 360));
		jLabel_monthly = new JLabel();
		jLabel_monthly.setText("Monthly");
		jLabel_monthly.setSize(new Dimension(80, 30));
		jLabel_monthly.setLocation(new Point(120, 320));
		jLabel_yearly = new JLabel();
		jLabel_yearly.setText("Yearly");
		jLabel_yearly.setSize(new Dimension(80, 30));
		jLabel_yearly.setLocation(new Point(120, 280));
		jLabel_birthDay = new JLabel();
		jLabel_birthDay.setText("Birth Day");
		jLabel_birthDay.setSize(new Dimension(80, 30));
		jLabel_birthDay.setLocation(new Point(40, 230));
		jLabel_adress = new JLabel();
		jLabel_adress.setText("Adress");
		jLabel_adress.setSize(new Dimension(80, 30));
		jLabel_adress.setLocation(new Point(40, 180));
		jLabel_payType = new JLabel();
		jLabel_payType.setText("Pay Type :");
		jLabel_payType.setSize(new Dimension(80, 30));
		jLabel_payType.setLocation(new Point(40, 280));
		jLabel_suspend = new JLabel();
		jLabel_suspend.setText("Suspend");
		jLabel_suspend.setLocation(new Point(485, 360));
		jLabel_suspend.setSize(new Dimension(111, 30));
		jLabel_suspend.setBorder(BorderFactory.createLineBorder(Color.black));
		jLabel_Privilage = new JLabel();
		jLabel_Privilage.setText("Privilage");
		jLabel_Privilage.setLocation(new Point(375, 230));
		jLabel_Privilage.setSize(new Dimension(80, 30));
		jLabel_lastName = new JLabel();
		jLabel_lastName.setText("Last Name");
		jLabel_lastName.setLocation(new Point(375, 130));
		jLabel_lastName.setSize(new Dimension(80, 30));
		jLabel_firstName = new JLabel();
		jLabel_firstName.setText("First Name");
		jLabel_firstName.setLocation(new Point(40, 130));
		jLabel_firstName.setSize(new Dimension(80, 30));
		jLabel_UserID = new JLabel();
		jLabel_UserID.setText("User ID");
		jLabel_UserID.setLocation(new Point(375, 80));
		jLabel_UserID.setSize(new Dimension(80, 30));
		jLabel_userName = new JLabel();
		jLabel_userName.setText("User Name");
		jLabel_userName.setLocation(new Point(40, 80));
		jLabel_userName.setSize(new Dimension(80, 30));
		jLabel_mainLabel = new JLabel();
		jLabel_mainLabel.setText("User Details");
		jLabel_mainLabel.setLocation(new Point(0, 0));
		jLabel_mainLabel.setPreferredSize(new Dimension(700, 35));
		jLabel_mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_mainLabel.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		jLabel_mainLabel.setSize(new Dimension(700, 35));
		edit_flag = Boolean.FALSE;
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(getJButton_Edit_UD(), null);
		this.add(getJButton_Back_UD(), null);
		this.add(getJButton_showReport_UD(), null);
		this.add(getJButton_Save_UD(), null);
		this.add(jLabel_mainLabel, null);
		this.add(jLabel_userName, null);
		this.add(jLabel_UserID, null);
		this.add(jLabel_firstName, null);
		this.add(jLabel_lastName, null);
		this.add(jLabel_Privilage, null);
		this.add(jLabel_suspend, null);
		this.add(jLabel_payType, null);
		this.add(jLabel_adress, null);
		this.add(jLabel_birthDay, null);
		this.add(getJTextField_userName(), null);
		this.add(getJTextField_userID(), null);
		this.add(getJTextField_firstName(), null);
		this.add(getJTextField_lastName(), null);
		this.add(getJTextField_adress(), null);
		this.add(getJTextField_birthDay(), null);
		this.add(getJComboBox_privilage(), null);
		this.add(getJCheckBox_yearly(), null);
		this.add(getJCheckBox_monthly(), null);
		this.add(getJCheckBox_creditCard(), null);
		this.add(jLabel_yearly, null);
		this.add(jLabel_monthly, null);
		this.add(jLabel_creditCard, null);
		this.add(getJCheckBox_suspend(), null);
	}

	/**
	 * @return the m_lastChoice
	 */
	public UDDecision getLastChoice() {
		return m_lastChoice;
	}

	/**
	 * @param m_lastChoice the m_lastChoice to set
	 */
	public void setLastChoice(UDDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	/**
	 * This method initializes jButton_Edit_UD	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Edit_UD() {
		if (jButton_Edit_UD == null) {
			jButton_Edit_UD = new JButton();
			jButton_Edit_UD.setText("Edit");
			jButton_Edit_UD.setSize(new Dimension(130, 34));
			jButton_Edit_UD.setLocation(new Point(202, 480));
			jButton_Edit_UD.addActionListener(this);
		}
		return jButton_Edit_UD;
	}

	/**
	 * This method initializes jButton_Back_UD	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Back_UD() {
		if (jButton_Back_UD == null) {
			jButton_Back_UD = new JButton();
			jButton_Back_UD.setText("Back");
			jButton_Back_UD.setSize(new Dimension(130, 34));
			jButton_Back_UD.setLocation(new Point(36, 480));
			jButton_Back_UD.addActionListener(this);
		}
		return jButton_Back_UD;
	}

	/**
	 * This method initializes jButton_showReport_UD	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_showReport_UD() {
		if (jButton_showReport_UD == null) {
			jButton_showReport_UD = new JButton();
			jButton_showReport_UD.setText("Show Report");
			jButton_showReport_UD.setSize(new Dimension(130, 34));
			jButton_showReport_UD.setLocation(new Point(368, 480));
			jButton_showReport_UD.addActionListener(this);
		}
		return jButton_showReport_UD;
	}

	/**
	 * This method initializes jButton_Save_UD	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Save_UD() {
		if (jButton_Save_UD == null) {
			jButton_Save_UD = new JButton();
			jButton_Save_UD.setText("Save");
			jButton_Save_UD.setSize(new Dimension(130, 34));
			jButton_Save_UD.setLocation(new Point(534, 480));
			jButton_Save_UD.addActionListener(this);
		}
		return jButton_Save_UD;
	}

	/**
	 * This method initializes jTextField_userName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_userName() {
		if (jTextField_userName == null) {
			jTextField_userName = new JTextField();
			jTextField_userName.setEditable(false);
			jTextField_userName.setSize(new Dimension(200, 26));
			jTextField_userName.setLocation(new Point(120, 82));
			jTextField_userName.setText(chosenUser.getM_userName());
		}
		return jTextField_userName;
	}

	/**
	 * This method initializes jTextField_userID	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_userID() {
		if (jTextField_userID == null) {
			jTextField_userID = new JTextField();
			jTextField_userID.setEditable(false);
			jTextField_userID.setSize(new Dimension(200, 26));
			jTextField_userID.setLocation(new Point(455, 82));
			String userid = Integer.toString(chosenUser.getM_userID());
			jTextField_userID.setText(userid);
		}
		return jTextField_userID;
	}

	/**
	 * This method initializes jTextField_firstName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_firstName() {
		if (jTextField_firstName == null) {
			jTextField_firstName = new JTextField();
			jTextField_firstName.setEditable(false);
			jTextField_firstName.setSize(new Dimension(200, 26));
			jTextField_firstName.setLocation(new Point(120, 132));
			jTextField_firstName.setText(chosenUser.getM_firstName());
		
		}
		return jTextField_firstName;
	}

	/**
	 * This method initializes jTextField_lastName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_lastName() {
		if (jTextField_lastName == null) {
			jTextField_lastName = new JTextField();
			jTextField_lastName.setEditable(false);
			jTextField_lastName.setLocation(new Point(455, 132));
			jTextField_lastName.setSize(new Dimension(200, 26));
			jTextField_lastName.setText(chosenUser.getM_lastName());
		}
		return jTextField_lastName;
	}

	/**
	 * This method initializes jTextField_adress	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_adress() {
		if (jTextField_adress == null) {
			jTextField_adress = new JTextField();
			jTextField_adress.setEditable(false);
			jTextField_adress.setSize(new Dimension(535, 26));
			jTextField_adress.setLocation(new Point(120, 182));
			jTextField_adress.setText(chosenUser.getAdress());
		}
		return jTextField_adress;
	}

	/**
	 * This method initializes jTextField_birthDay	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_birthDay() {
		if (jTextField_birthDay == null) {
			jTextField_birthDay = new JTextField();
			jTextField_birthDay.setEditable(false);
			jTextField_birthDay.setSize(new Dimension(200, 26));
			jTextField_birthDay.setLocation(new Point(120, 232));
			jTextField_birthDay.setText(chosenUser.getNormalBirthDay());
		}
		return jTextField_birthDay;
	}

	/**
	 * This method initializes jComboBox_privilage	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox_privilage() {
		if (jComboBox_privilage == null) {
			String[] privilagesToShow = new String[4];
			String[] privilage = {EActor.Reader.toString(),EActor.User.toString(),EActor.Librarian.toString(),EActor.LibraryManager.toString()};
			privilagesToShow[0] = chosenUser.getM_privilege().toString(); 
			int i = 1;
			for(String pr : privilage)
			{
				if( privilagesToShow[0].compareTo(pr) != 0)
				{
					privilagesToShow[i]=pr;
				    i++;
				}
			}
			jComboBox_privilage = new JComboBox(privilagesToShow);
			jComboBox_privilage.setEnabled(false);
			jComboBox_privilage.setSize(new Dimension(200, 20));
			jComboBox_privilage.setLocation(new Point(455, 235));
			jComboBox_privilage.setSelectedItem(privilagesToShow[0]);
			jComboBox_privilage.addItemListener(this);
		}
		return jComboBox_privilage;
	}

	/**
	 * This method initializes jCheckBox_yearly	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_yearly() {
		if (jCheckBox_yearly == null) {
			jCheckBox_yearly = new JCheckBox();
			jCheckBox_yearly.setSize(new Dimension(23, 20));
			jCheckBox_yearly.setEnabled(false);
			jCheckBox_yearly.setLocation(new Point(210, 287));
			for(String pt : chosenUser.getPayTypes())
			   if(pt.compareTo("Yearly") == 0)
				   jCheckBox_yearly.setSelected(true);
		}
		return jCheckBox_yearly;
	}

	/**
	 * This method initializes jCheckBox_monthly	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_monthly() {
		if (jCheckBox_monthly == null) {
			jCheckBox_monthly = new JCheckBox();
			jCheckBox_monthly.setSize(new Dimension(17, 17));
			jCheckBox_monthly.setEnabled(false);
			jCheckBox_monthly.setLocation(new Point(210, 327));
			for(String pt : chosenUser.getPayTypes())
				   if(pt.compareTo("Monthly") == 0)
					   jCheckBox_monthly.setSelected(true);
		}
		return jCheckBox_monthly;
	}

	/**
	 * This method initializes jCheckBox_creditCard	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_creditCard() {
		if (jCheckBox_creditCard == null) {
			jCheckBox_creditCard = new JCheckBox();
			jCheckBox_creditCard.setSize(new Dimension(22, 16));
			jCheckBox_creditCard.setEnabled(false);
			jCheckBox_creditCard.setLocation(new Point(210, 367));
			for(String pt : chosenUser.getPayTypes())
				   if(pt.compareTo("CreditCard") == 0)
					   jCheckBox_creditCard.setSelected(true);
		}
		return jCheckBox_creditCard;
	}

	/**
	 * This method initializes jCheckBox_suspend	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_suspend() {
		if (jCheckBox_suspend == null) {
			jCheckBox_suspend = new JCheckBox();
			jCheckBox_suspend.setSize(new Dimension(17, 15));
			jCheckBox_suspend.setEnabled(false);
			jCheckBox_suspend.setFont(new Font("Freestyle Script", Font.BOLD, 12));
			jCheckBox_suspend.setForeground(Color.red);
			jCheckBox_suspend.setText("");
			jCheckBox_suspend.setLocation(new Point(565, 367));
		    if(chosenUser.isSuspend())
				jCheckBox_suspend.setSelected(true);
		}
		return jCheckBox_suspend;
	}

	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == jButton_Back_UD)
		{
			this.setLastChoice(UDDecision.BACK);
			this.setVisible(false);
		}
		if(source == jButton_Edit_UD)
		{
			if(!edit_flag)
			{
				jCheckBox_suspend.setEnabled(true);
				if(chosenUser.getM_privilege() == EActor.Reader  || chosenUser.getM_privilege() == EActor.User)
				{
					jCheckBox_monthly.setEnabled(true);
				    jCheckBox_yearly.setEnabled(true);
				}
				jComboBox_privilage.setEnabled(true);
				jTextField_birthDay.setEditable(true);
				jTextField_adress.setEditable(true);
				jTextField_lastName.setEditable(true);
				jTextField_firstName.setEditable(true);
				edit_flag = Boolean.TRUE;
			}
			else if(edit_flag)
			{
				jCheckBox_suspend.setEnabled(false);
				jCheckBox_monthly.setEnabled(false);
				jCheckBox_yearly.setEnabled(false);
				jComboBox_privilage.setEnabled(false);
				jTextField_birthDay.setEditable(false);
				jTextField_adress.setEditable(false);
				jTextField_lastName.setEditable(false);
				jTextField_firstName.setEditable(false);
				edit_flag = Boolean.FALSE;
			}
		}
		if(source == jButton_showReport_UD)
		{
			this.setLastChoice(UDDecision.SHOWREPORT);
			this.setVisible(false);
		}
		if(source == jButton_Save_UD)
		{
			String[] paytypes = new String[3];
			EActor privilage = EActor.User;
			if(jCheckBox_yearly.isSelected())
				paytypes[0] = "Yearly";
			if(jCheckBox_monthly.isSelected())
				paytypes[1] = "Monthly";
			if(jCheckBox_creditCard.isSelected())
				paytypes[2] = "CreditCard";
		    if(jComboBox_privilage.getSelectedItem().toString() == "Reader")
				privilage = EActor.Reader;
			else if(jComboBox_privilage.getSelectedItem().toString() == "LibraryManager")
				privilage = EActor.LibraryManager;
			else if(jComboBox_privilage.getSelectedItem().toString() == "Librarian")
				privilage = EActor.Librarian;			
				try {
					String answer = ((CLibraryManager)AUser.getInstance()).updateUserDetails(chosenUser.getM_userName(), jTextField_firstName.getText(), jTextField_lastName.getText(), Integer.parseInt(jTextField_userID.getText()), jTextField_birthDay.getText(), jTextField_adress.getText(), paytypes, privilage, jCheckBox_suspend.isSelected());
					JOptionPane.showMessageDialog(null, answer ,"Server answer :",JOptionPane.INFORMATION_MESSAGE);
				} catch (NumberFormatException e) {
					System.out.println("Can't convert from String to Integer");
				} catch (Exception e) {
					System.out.println("Fail to reach client.core.CLibraryManager.updateUserDetails");
				}
			this.setLastChoice(UDDecision.SAVE);
			this.setVisible(false);
		}
		
	}

	
	public void itemStateChanged(ItemEvent pe) {
		 if (pe.getItemSelectable() == jComboBox_privilage)
		 {
			 String priv = jComboBox_privilage.getSelectedItem().toString();
			 if( (priv.compareTo(EActor.Librarian.toString()) == 0 || priv.compareTo(EActor.LibraryManager.toString()) == 0 || (priv.compareTo(EActor.User.toString()) == 0)))
			 {
				 jCheckBox_monthly.setSelected(false);
				 jCheckBox_monthly.setEnabled(false);
				 jCheckBox_yearly.setSelected(false);
				 jCheckBox_yearly.setEnabled(false);
				 jCheckBox_creditCard.setSelected(false);
			 }
			 else if((priv.compareTo(EActor.Reader.toString()) == 0 || priv.compareTo(EActor.User.toString()) == 0))
			 {
				 for(String pt : chosenUser.getPayTypes())
				 {
					   if(pt.compareTo("Yearly") == 0)
						   jCheckBox_yearly.setSelected(true);
					   if(pt.compareTo("Monthly") == 0)
						   jCheckBox_monthly.setSelected(true);
					   if(pt.compareTo("CreditCard") == 0)
						   jCheckBox_creditCard.setSelected(true);
				 }
				 jCheckBox_monthly.setEnabled(true);
				 jCheckBox_yearly.setEnabled(true);
			 }
		 }
		
	}

}
