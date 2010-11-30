package client.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.common.*;

public abstract class AUser{
	
	/* FOR CHECKING PURPOSE ONLY - TO DELETE */
	static final private String temp_Username = "Daniel";
	static final private String temp_Password = "12345";  //  @jve:decl-index=0:
	static final private EActor tmpAct = EActor.LibraryManager;
	/* ------------------------------------- */
	
	static protected AUser  m_actor = null;
		   protected EActor m_privilege;
		   protected String m_firstName;
		   protected String m_lastName;
		   protected int    m_userID;
		   protected String m_userName;
		   
	final static public EActor login(String username, String password) throws Exception
	{	
		 /*
		  *  Username && Password input validation 
		 */
		Pattern pu = Pattern.compile("(\\p{Alpha})+((\\p{Digit})*(\\p{Alpha})*)*");
		Pattern pp = Pattern.compile("((\\p{Digit})*(\\p{Alpha})*)*");
		Matcher mu = pu.matcher(username);
		Matcher mp = pp.matcher(password);
		boolean b = mu.matches();
		b &= mp.matches();
		if(!b){
			throw new Exception("Invalid Username/Password Characters");
		}
		
		
		
		
		/*
	    try {
	    	CClientConnector.getInstance().openConnection();
			Map<String,String> UP = new HashMap<String,String>();
			UP.put(username, password);
			CEntry clientEntry = new CEntry("login",UP,username,-1);
			CClientConnector.getInstance().sendToServer(clientEntry);
			while(!CClientConnector.getInstance().isWaitingMsg())
			{
				Thread.yield();
			}	
		} 
	    catch (IOException e) {
			System.out.println(e.getMessage());
		}
		CEntry result = (CEntry)CClientConnector.getInstance().getMsg();
		setActor(result.getClient());
		*/
		
		
		/*
		 * TODO:
		 * CHECKING IF LOGGING SUCCESSFUL USING result  
		 * 
		 * 
		 * 
		 */



		
		// TEMPORARY //	
		if((temp_Username.compareTo(username)==0) && (temp_Password.compareTo(password)==0))
		{
			/*
			 * TODO:
			 * Need Connection to Server.
			 * Need Validation.
			 * Need Update of User Privilege.
			 */
		
			m_actor = new CLibraryManager();
			return(m_actor.getPrivilege());
		}
		throw new Exception("Incorrect Username/Password");
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
	
	final static public boolean logout()
	{
		// -Stub-
		m_actor = null;
		return(true);
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
	
	
}
