package server.core;
import common.api.CEntry;

/**
 * class to contain the client's session details.
 *
 */
public class CClientSession implements Comparable<Object>
{
	/** the clients session ID by which we recognize him, is actually the key for identification */
	private int m_SessionID;
	/** holds the client's username */
	private String m_UserName;
	/** holds the user's authorization (privilage) */
	private int m_UserAuth;
	
	/** simple constructor
	 * @param ID a session ID by which to recognize the session (distinct during runtime - ) 
	 * @param user the client's username
	 * @param auth the clients authorization
	 */
	public CClientSession(int ID,String user,int auth)
	{
		this.m_UserName=user;
		this.m_SessionID=ID;
		this.m_UserAuth=auth;
	}
	
	/**
	 * certifies that the noted entry is of the session's user's request
	 * @param b CEntry by which to check.
	 * @return answer if the Entry is related to ClientSession
	 */
	public boolean isOfUser(CEntry b)//
	{
		if (this.m_UserName == b.getUserName())
			return true;
		return false;
	}
	
	/**
	 * simple setter for session ID
	 * @param m_SessionID sets the new SID
	 */
	public void setSessionID(int SessionID) {
		this.m_SessionID = SessionID;
	}


	/**getter for Session ID */
	public int getSessionID() 
	{
		return m_SessionID;
	}

	/** comparator for class 
	 * implements the Comparable interface
	 * @param b is the other object to which we compare THIS
	 * @return 0 if equal, 1 if this > b, -1 else. 
	 */
	public int compareTo(Object b) 
	{
		if(b instanceof CEntry)
		{
			if(this.m_UserName.compareTo(((CEntry) b).getUserName()) == 0)
				if(this.m_SessionID > ((CEntry) b).getSessionID())
					return 1;
				else if(this.m_SessionID == ((CEntry) b).getSessionID())
					return 0;
				else return -1;
			return this.m_UserName.compareTo(((CEntry) b).getUserName());
		}
		if(b instanceof CClientSession)
		{
		 	if(this.m_UserName.compareTo(((CClientSession)b).m_UserName) == 0)
				if(this.m_SessionID > ((CClientSession)b).m_SessionID)
					return 1;
				else if(this.m_SessionID == ((CClientSession)b).m_SessionID)
					return 0;
				else return -1;
			return this.m_UserName.compareTo(((CClientSession)b).m_UserName);

		}

		
		if(this.equals(b))
			return 0;
		return this.toString().compareTo(b.toString());
	}

/** getter for privilage / user authorization */
	public int getUserAuth() {
		return m_UserAuth;
	}
	/**getter for user name	 */
	public String getUsername() {
		return this.m_UserName;
	}
}
