package client.core;

public abstract class AUserConnectable{
	
	/* FOR CHECKING PURPOSE ONLY - TO DELETE */
	static final private String Username = "Daniel";
	static final private String Password = "12345";  //  @jve:decl-index=0:
	static final private EActor tmpAct = EActor.LibraryManager;
	/* ------------------------------------- */
	
	static protected AUserConnectable m_actor = null;
	
	static public EActor login(String username, String password)
	{		
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
		}
		return(EActor.None);
	}
	
	static public AUserConnectable getInstance() throws Exception
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
	
	public boolean logout()
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
	
}
