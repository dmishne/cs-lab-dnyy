package client.core;

public class CLibraryManager extends CLibrarian{

	private static final long serialVersionUID = 1L;

	public CLibraryManager(String FirstName, String LastName, int UserId, String UserName, int SessionID)
	{
		super(FirstName,LastName,UserId,UserName,SessionID);
		this.updateAccount(EActor.LibraryManager);
	}
}
