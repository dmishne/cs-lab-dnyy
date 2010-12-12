package client.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.common.CClientConnector;
import client.common.CEntry;

public abstract class AUser implements Serializable{
	
	static private final long serialVersionUID = 1L;
	static protected AUser m_actor = null;
		   private String  m_firstName;
		   private String  m_lastName;
		   private int     m_userID;
		   private String  m_userName;
		   private EActor  m_privilege;
		   private int 	   m_UserSessionId;
		

    public AUser(String FirstName, String LastName, int UserId, String UserName, EActor Privilege, int SessionID)
    {
    	FirstName = FirstName.replace("\"", "");
    	LastName  = LastName.replace("\"", "");
    	m_firstName     = Character.toUpperCase(FirstName.charAt(0)) + FirstName.substring(1, FirstName.length());
    	m_lastName      = Character.toUpperCase(LastName.charAt(1)) + LastName.substring(2, LastName.length() - 1);
    	m_userID        = UserId;
    	m_userName      = UserName;
    	m_privilege     = Privilege;
    	m_UserSessionId = SessionID;
    }
    
	final static public EActor login(String username, String password) throws Exception
	{	
		 /*
		  *  Username && Password input validation 
		 */
		Pattern pu = Pattern.compile("(\\p{Alpha})+((\\p{Digit})*(\\p{Alpha})*)*");
		Pattern pp = Pattern.compile("(((\\p{Digit})+(\\p{Alpha})*)+|((\\p{Digit})*(\\p{Alpha})+)+)+");
		Matcher mu = pu.matcher(username);
		Matcher mp = pp.matcher(password);
		boolean b = mu.matches();
		b &= mp.matches();
		if(!b){
			throw new IOException("Invalid Username/Password Characters");
		}
		
		/*
		 * Creating Entry to send to server
		 */
		HashMap<String,String> UP = new HashMap<String,String>();
		UP.put("user", username);
		UP.put("password", password);
		CEntry clientEntry = new CEntry("Login",UP,username,-1);
		Object result = CClientConnector.getInstance().messageToServer(clientEntry);
		if(result instanceof AUser )
		{
			setActor(result);
			System.out.println("Connection was successful");
			return(m_actor.getPrivilege());
		}
		throw new IOException("Incorrect Username/Password");
	}
	
	final static public AUser getInstance() throws Exception
	{
		if(null == m_actor)
		{
			throw new Exception("No Login");
		}
		else
		{
			return m_actor;
		}
	}
	
	final static public void logout()
	{
		// -Stub-
		m_actor = null;
		try
	    {
			CClientConnector.getInstance().closeConnection();
	    }
	    catch(Exception e) {
	    	System.out.println("logout wasn't successful");
	    }    
		// ------
	}
	
	static private void setActor(Object pri)
	{
		switch(((AUser)pri).getPrivilege())
		{
		case Reader:
			m_actor = (CReader)pri;
			break;
		case Librarian:
			m_actor = (CLibrarian)pri;
			break;
		case LibraryManager:
			m_actor = (CLibraryManager)pri;
			break;
		}
	}
		
	public EActor getPrivilege() {
		return m_privilege;
	}

	public void setPrivilege(EActor mPrivilege) {
		m_privilege = mPrivilege;
	}
	
	 /**
	 * @return the m_firstName
	 */
	public String getFirstName() {
		return m_firstName;
	}

	/**
	 * @return the m_lastName
	 */
	public String getLastName() {
		return m_lastName;
	}

	/**
	 * @return the m_userID
	 */
	public int getUserID() {
		return m_userID;
	}

	/**
	 * @return the m_userName
	 */
	public String getUserName() {
		return m_userName;
	}

	/**
	 * @return the m_UserSessionId
	 */
	public int getUserSessionId() {
		return m_UserSessionId;
	}

}
