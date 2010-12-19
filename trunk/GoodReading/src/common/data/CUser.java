package common.data;

import java.io.Serializable;

import client.core.EActor;

public class CUser implements Serializable{
	
	static private final long serialVersionUID = 1L;
	
		   private String  m_firstName;
		   private String  m_lastName;
		   private int     m_userID;
		   private String  m_userName;
		   private EActor  m_privilege;
		

    public CUser(String FirstName, String LastName, int UserId, String UserName, EActor Privilege)
    {   	
    	m_firstName     = FirstName ;
    	m_lastName      = LastName ;
    	m_userID        = UserId;
    	m_userName      = UserName;
    	m_privilege     = Privilege;
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
	
	

}
