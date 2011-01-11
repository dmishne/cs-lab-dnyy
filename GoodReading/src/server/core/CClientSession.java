package server.core;
import common.api.CEntry;

public class CClientSession implements Comparable<Object>
{
	//public java.net.InetAddress m_IP;
	private int m_SessionID;
	private String m_UserName;
	private Thread m_executingThread;
	private int m_UserAuth;
	///*TODO: what happens if we try to 'execute' while there's a request waiting? */
	
	
	public CClientSession(int randomID,String user,int auth)
	{
		this.m_executingThread=null;
		this.m_UserName=user;
		this.m_SessionID=randomID;
		this.m_UserAuth=auth;
	}
	
	
	
	public boolean isOfUser(CEntry b)//
	{
		if (this.m_UserName == b.getUserName())
			return true;
		return false;
	}
	
	
	
	//session.kill
	public void Kill() {
		// TODO Auto-generated method stub
		
	}


	public void setSessionID(int m_SessionID) {
		this.m_SessionID = m_SessionID;
	}



	public int getSessionID() 
	{
		return m_SessionID;
	}



	public void setexecutingThread(Thread m_executingThread) 
	{
		this.m_executingThread = m_executingThread;
	}



	public Thread getexecutingThread() 
	{
		return m_executingThread;
	}



	
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


	public int getUserAuth() {
		return m_UserAuth;
	}
	public String getUsername() {
		return this.m_UserName;
	}
}
