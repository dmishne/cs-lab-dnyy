package client.core;

public class CLibrarian extends AUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CLibrarian(String FirstName, String LastName, int UserId, String UserName)
	{
		super(FirstName,LastName,UserId,UserName, EActor.Librarian);
	}
	
}
