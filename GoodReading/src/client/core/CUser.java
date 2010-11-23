package client.core; 

public class CUser extends AUserConnectable{
	

	private static  CUser m_instance = null;
		
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

	
	
	static public CUser getInstance()
	{
		if(m_instance == null)
		{
			m_instance = new CUser();
		}
		return m_instance;
	}
	
}
