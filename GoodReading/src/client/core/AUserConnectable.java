package client.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import client.common.*;

public abstract class AUserConnectable{
	
	/* FOR CHECKING PURPOSE ONLY - TO DELETE */
	static final private String Username = "Daniel";
	static final private String Password = "12345";  //  @jve:decl-index=0:
	static final private EActor tmpAct = EActor.LibraryManager;
	/* ------------------------------------- */
	
	static protected AUserConnectable m_actor = null;
	
	final static public EActor login(String username, String password)
	{	
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
		/*
		 * TODO:
		 * CHECKING IF LOGGING SUCCESSFUL USING result  
		 * 
		 * 
		 * 
		 */

		// TEMPORARY //	
		if((Username.compareTo(username)==0) && (Password.compareTo(password)==0))
		{
			/*
			 * TODO:
			 * Need Connection to Server.
			 * Need Validation.
			 * Need Update of User Privilege.
			 */
		
			if(m_actor == null)
			{
				createActor(tmpAct);
				return(tmpAct);
			}
			return(tmpAct); 
		}
		return(EActor.None);

	}
	
	final static public AUserConnectable getInstance() throws Exception
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
	
	final public boolean logout()
	{
		// -Stub-
		m_actor = null;
		return(true);
		// ------
	}
	
	static private void createActor(EActor actor)
	{
		switch(actor)
		{
		case User:
			m_actor = new CUser();
			break;
		case Reader:
			m_actor = new CReader();
			break;
		case Librarian:
			m_actor = new CLibrarian();
			break;
		case LibraryManager:
			m_actor = new CLibraryManager();
			break;
		}
	}
	
	protected final Object handshakeWithServer(Object message)
	{
	   //send to server
		try {
			CClientConnector.getInstance().sendToServer(message);
			while(!CClientConnector.getInstance().isWaitingMsg())
			{
				wait();
			}
		} catch (IOException e) { 
			e.getCause();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
		// get answer		
		return (CClientConnector.getInstance().getMsg());	
	}
	
}
