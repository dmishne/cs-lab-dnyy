package common.data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import client.core.EActor;

public class CUser implements Serializable{
	
	static private final long serialVersionUID = 1L;
	
	private String  m_firstName;
	private String  m_lastName;
	private int     m_userID;
	private String  m_userName;
	private String  m_birthDay;
	private String  m_adress;
	private String  m_pass;
	private EActor  m_privilege;
	private boolean b_suspend;
	private LinkedList<String> PayTypes;
		

    public CUser(String FirstName, String LastName, int UserId, String UserName, String birthday, String adress,String[] payTypes, EActor Privilege, boolean suspend)
    {   	
    	m_firstName     = FirstName ;
    	m_lastName      = LastName ;
    	m_userID        = UserId;
    	m_userName      = UserName;
    	m_birthDay      = birthday;
    	m_adress        = adress;
    	m_privilege     = Privilege;
    	b_suspend       = suspend;
    }

//factory for DBIG
	public CUser(ResultSet data,LinkedList<String> arg) throws SQLException {
		m_firstName     = data.getString(7) ;
    	m_lastName      = data.getString(8) ;
    	m_userID        = data.getInt(5) ;
    	m_userName      = data.getString(1) ;
    	m_birthDay      = data.getString(4) ;
    	m_adress        = data.getString(6) ;
    	setM_pass(data.getString(2)) ;
    	PayTypes=arg;
       	b_suspend=data.getBoolean("suspended");
    	switch(data.getInt(3))
    	{
	    	case(-1):
	    		//user ban
	    		b_suspend=true;
	    		m_privilege=EActor.None;
	    		break;
	    	case(0):
	    		//user ban
	    		m_privilege=EActor.None;
	    		break;
	    	case(1):
	    		m_privilege=EActor.User;
	    		break;
	    	case(2):
	    		m_privilege=EActor.Reader;
	    		break;
	    	case(3):
	    		m_privilege=EActor.Librarian;
	    		break;
	    	case(5):
	    		m_privilege=EActor.LibraryManager;
	    		break;
	    	default:	
	    		m_privilege=EActor.User;
	    		break;
    	}
	    		
    }



	/**
	 * @return the m_firstName
	 */
	public String getM_firstName() {
		return m_firstName;
	}


	/**
	 * @return the m_lastName
	 */
	public String getM_lastName() {
		return m_lastName;
	}


	/**
	 * @return the m_userID
	 */
	public int getM_userID() {
		return m_userID;
	}


	/**
	 * @return the m_userName
	 */
	public String getM_userName() {
		return m_userName;
	}


	/**
	 * @return the m_privilege
	 */
	public EActor getM_privilege() {
		return m_privilege;
	}


	
	/**
	 * @return the b_suspend
	 */
	public boolean isSuspend() {
		return b_suspend;
	}
	public void SetSuspend(boolean arg)
	{
		b_suspend=arg;
	}


	/**
	 * @param m_birthDay the m_birthDay to set
	 */
	public void setBirthDay(String m_birthDay) {
		this.m_birthDay = m_birthDay;
	}


	/**
	 * @return the m_birthDay
	 */
	public String getBirthDay() {
		return m_birthDay;
	}
	
	
	public String getNormalBirthDay() {
		String normalBD = m_birthDay.substring(8, 10) + "-" + m_birthDay.substring(5, 7) + "-" + m_birthDay.substring(0, 4);
		return normalBD;
	}


	/**
	 * @param m_adress the m_adress to set
	 */
	public void setAdress(String m_adress) {
		this.m_adress = m_adress;
	}


	/**
	 * @return the m_adress
	 */
	public String getAdress() {
		return m_adress;
	}


	public void setM_pass(String m_pass) {
		this.m_pass = m_pass;
	}


	public String getM_pass() {
		return m_pass;
	}


	public String getM_birthDay() {
		return m_birthDay;
	}


	public void setM_birthDay(String m_birthDay) {
		this.m_birthDay = m_birthDay;
	}


	public String getM_adress() {
		return m_adress;
	}


	public void setM_adress(String m_adress) {
		this.m_adress = m_adress;
	}


	public boolean isB_suspend() {
		return false;
	}


	public void setB_suspend(boolean b_suspend) {
		this.b_suspend = b_suspend;
	}


	public void setM_firstName(String m_firstName) {
		this.m_firstName = m_firstName;
	}


	public void setM_lastName(String m_lastName) {
		this.m_lastName = m_lastName;
	}


	public void setM_userID(int m_userID) {
		this.m_userID = m_userID;
	}


	public void setM_userName(String m_userName) {
		this.m_userName = m_userName;
	}


	public void setM_privilege(EActor m_privilege) {
		this.m_privilege = m_privilege;
	}


	public LinkedList<String> getPayTypes() {
		return PayTypes;
	}
}
