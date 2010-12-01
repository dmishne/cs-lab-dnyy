package client.core;

public class CLibraryManager extends CLibrarian{

	private static final long serialVersionUID = 1L;

	public CLibraryManager(String FirstName, String LastName, int UserId, String UserName)
	{
		super(FirstName,LastName,UserId,UserName);
		this.setPrivilege(EActor.LibraryManager);
	}
	
	
	
}
