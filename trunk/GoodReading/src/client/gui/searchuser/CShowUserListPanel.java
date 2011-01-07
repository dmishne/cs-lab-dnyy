package client.gui.searchuser;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Point;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import common.data.CUser;
import javax.swing.JButton;

public class CShowUserListPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel jLabel_main_label = null;
	private JScrollPane jScrollPane_UL = null;
	private JList jList_UL = null;
	private JButton jButton_back_UL = null;
	private JButton jButton_ShowUserDetails_UL = null;
	private static CUser m_chosenUser = null;
	private ULPDecision m_lastChoice = null;
	private LinkedList<CUser> usersList = null;

	
	
	public enum ULPDecision
	{
		BACK,USERLIST
	}
	/**
	 * This is the default constructor
	 */
	public CShowUserListPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(){
		jLabel_main_label = new JLabel();
		jLabel_main_label.setText("Users Result List");
		jLabel_main_label.setLocation(new Point(0, 0));
		jLabel_main_label.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_main_label.setFont(new Font("Freestyle Script", Font.BOLD, 36));
		jLabel_main_label.setSize(new Dimension(700, 35));
		this.setSize(700, 550);
		this.setLayout(null);
		this.add(jLabel_main_label, null);
		this.add(getJScrollPane_UL(), null);
		this.add(getJButton_back_UL(), null);
		this.add(getJButton_ShowUserDetails_UL(), null);
	}

	
	/**
	 * @param m_lastChoice the m_lastChoice to set
	 */
	public void setLastChoice(ULPDecision m_lastChoice) {
		this.m_lastChoice = m_lastChoice;
	}

	/**
	 * @return the m_lastChoice
	 */
	public ULPDecision getLastChoice() {
		return m_lastChoice;
	}

	
	
	/**
	 * @return the m_chosenUser
	 */
	static public CUser getChosenUser() {
		return m_chosenUser;
	}

	/**
	 * This method initializes jScrollPane_UL	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_UL(){
		if (jScrollPane_UL == null) {
			jScrollPane_UL = new JScrollPane();
			jScrollPane_UL.setLocation(new Point(50, 50));
			jScrollPane_UL.setViewportView(getJList_UL());
			jScrollPane_UL.setSize(new Dimension(600, 400));
			jScrollPane_UL.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		return jScrollPane_UL;
	}

	/**
	 * This method initializes jList_UL	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList_UL() {
		if (jList_UL == null) {
			usersList = CSearchUserPanel.getResult();
			String []users = new String[usersList.size()];
			int i = 0;
			for( CUser user : usersList )
			{
				users[i] = user.getM_userName() + " - " + user.getM_userID() + " - " + user.getM_firstName() + " - " + user.getM_lastName();
				i++;
			}
			jList_UL = new JList(users);
			jList_UL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jList_UL.setSize(new Dimension(600, 400));
			jList_UL.setLocation(new Point(50, 50));
			jList_UL.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return jList_UL;
	}

	/**
	 * This method initializes jButton_back_UL	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_back_UL() {
		if (jButton_back_UL == null) {
			jButton_back_UL = new JButton();
			jButton_back_UL.setLocation(new Point(94, 480));
			jButton_back_UL.setText("Back");
			jButton_back_UL.setSize(new Dimension(208, 34));
			jButton_back_UL.addActionListener(this);
		}
		return jButton_back_UL;
	}

	/**
	 * This method initializes jButton_ShowUserDetails_UL	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_ShowUserDetails_UL() {
		if (jButton_ShowUserDetails_UL == null) {
			jButton_ShowUserDetails_UL = new JButton();
			jButton_ShowUserDetails_UL.setLocation(new Point(396, 480));
			jButton_ShowUserDetails_UL.setText("Show User Details");
			jButton_ShowUserDetails_UL.setSize(new Dimension(208, 34));
			jButton_ShowUserDetails_UL.addActionListener(this);
		}
		return jButton_ShowUserDetails_UL;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == jButton_back_UL)
		{
			this.setLastChoice(ULPDecision.BACK);
			this.setVisible(false);
		}
		if(source == jButton_ShowUserDetails_UL)
		{
			try{
				String res = (String)jList_UL.getSelectedValue();
				String[] splitedRes = res.split(" - ");
				Iterator<CUser> it = usersList.iterator();
				while(it.hasNext())
				{
					CUser temp = it.next();
					if(temp.getM_userName().compareTo(splitedRes[0]) == 0)
					{
						m_chosenUser = temp;
						break;
					}
				}
				this.m_lastChoice = ULPDecision.USERLIST;
				this.setVisible(false);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Please choose user from list" ,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	


	
}
