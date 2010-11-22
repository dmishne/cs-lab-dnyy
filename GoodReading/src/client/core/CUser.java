package client.core; 

public class CUser {
	

	private static  CUser m_instance = null;
	private         EUserPrivilege m_user = null;
	
	/* FOR CHECKING PURPOSE ONLY - TO DELETE */
	final String Username = "Daniel";
	final String Password = "12345";  //  @jve:decl-index=0:
	/* ------------------------------------- */
		
	public String getUserInfo()
	{
		// -Stub-
		return("Nothing");
		// ------
	}
	public EUserPrivilege getUserPrivilage()
	{
		// -Stub-
		return(EUserPrivilege.User);
		// -Stub-
	}
	
	public boolean login(String username, String password)
	{
		if((Username.compareTo(username)==0) && (Password.compareTo(password)==0))
		{
			/*
			 * TODO:
			 * Need Connection to Server.
			 * Need Validation.
			 * Need Update of User Privilege.
			 */
			return(true);
		}
		else
		{
			return(false);
		}
	}
	
	public boolean logout()
	{
		// -Stub-
		return(true);
		// ------
	}
	
	static public CUser getInstance()
	{
		if(m_instance == null)
		{
			m_instance = new CUser();
		}
		return m_instance;
	}
	
}
