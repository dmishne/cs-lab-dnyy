package server.core;
import client.common.CEntry;

public class CClientSession implements Comparable
{
	//public java.net.InetAddress m_IP;
	private int m_SessionID;
	private String m_UserName;
	private Thread m_executingThread;
	///*TODO: what happens if we try to 'execute' while there's a request waiting? */
	
	
	public CClientSession(int randomID,String user)
	{
		this.m_executingThread=null;
		this.m_UserName=user;
		this.m_SessionID=randomID;
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



	
	public int compareTo(CClientSession b) 
	{
		// if Username is the same username, resolve to SessionID (prob isn't needed), else rely on Username
		if(this.m_UserName.compareTo(b.m_UserName) == 0)
			if(this.m_SessionID > b.m_SessionID)
				return 1;
			else if(this.m_SessionID == b.m_SessionID)
				return 0;
			else return -1;
			
		return this.m_UserName.compareTo(b.m_UserName);
	}



	@Override
	public int compareTo(Object o) {
		if(o.equals(this))
			return 0;
		return 1;
	}
	
}
