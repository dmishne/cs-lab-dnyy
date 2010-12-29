package common.data;

import java.io.Serializable;

import client.core.EActor;

public class CUser implements Serializable{
	
	static private final long serialVersionUID = 1L;
	
		   private String  m_firstName;
		   private String  m_lastName;
		   private int     m_userID;
		   private String  m_userName;
		   private String  m_birthDay;
		   private String  m_adress;
		   private String[] m_payTypes;
		   private EActor  m_privilege;
		   private boolean b_suspend;
		

    public CUser(String FirstName, String LastName, int UserId, String UserName, String birthday, String adress,String[] payTypes, EActor Privilege, boolean suspend)
    {   	
    	m_firstName     = FirstName ;
    	m_lastName      = LastName ;
    	m_userID        = UserId;
    	m_userName      = UserName;
    	m_birthDay      = birthday;
    	m_adress        = adress;
    	m_payTypes      = payTypes;
    	m_privilege     = Privilege;
    	b_suspend       = suspend;
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
	 * @param b_suspend the b_suspend to set
	 */
	public void setSuspend(boolean b_suspend) {
		this.b_suspend = b_suspend;
	}


	/**
	 * @return the b_suspend
	 */
	public boolean isSuspend() {
		return b_suspend;
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


	/**
	 * @param m_payTypes the m_payTypes to set
	 */
	public void setPayTypes(String[] m_payTypes) {
		this.m_payTypes = m_payTypes;
	}


	/**
	 * @return the m_payTypes
	 */
	public String[] getPayTypes() {
		return m_payTypes;
	}
	
	

}
