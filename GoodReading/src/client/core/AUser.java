package client.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.common.CClientConnector;
import client.common.CEntry;

public abstract class AUser implements Serializable{
	
	/**
	 * 
	 */

	/* FOR CHECKING PURPOSE ONLY - TO DELETE */
	//static final private String temp_Username = "Daniel";
	//static final private String temp_Password = "12345";  //  @jve:decl-index=0:
	//static final private EActor tmpAct = EActor.LibraryManager;
	/* ------------------------------------- */
	
	static private final long serialVersionUID = 1L;
	static protected AUser m_actor = null;
		   private String  m_firstName;
		   private String  m_lastName;
		   private int     m_userID;
		   private String  m_userName;
		   private EActor  m_privilege;
		

    public AUser(String FirstName, String LastName, int UserId, String UserName, EActor Privilege)
    {
    	m_firstName = FirstName;
    	m_lastName = LastName;
    	m_userID = UserId;
    	m_userName = UserName;
    	m_privilege = Privilege;
    }
    
	final static public EActor login(String username, String password) throws IOException
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
		Map<String,String> UP = new HashMap<String,String>();
		UP.put("user", username);
		UP.put("password", password);
		CEntry clientEntry = new CEntry("Login",UP,username,-1);
		CEntry result = (CEntry)CClientConnector.getInstance().messageToServer(clientEntry);
		if(null == result.getClient())
		{
			setActor(result.getClient());
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
	    catch(IOException e) {}
	    System.exit(0);
		
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

}
