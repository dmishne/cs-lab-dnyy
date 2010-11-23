package client.core;

public abstract class AUserConnectable {
	
	
	/* FOR CHECKING PURPOSE ONLY - TO DELETE */
	final String Username = "Daniel";
	final String Password = "12345";  //  @jve:decl-index=0:
	/* ------------------------------------- */
	
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
	
}
