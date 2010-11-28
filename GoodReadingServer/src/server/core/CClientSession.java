package server.core;
import client.common.CEntry;

public class CClientSession 
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



	public int getSessionID() {
		return m_SessionID;
	}



	public void setexecutingThread(Thread m_executingThread) {
		this.m_executingThread = m_executingThread;
	}



	public Thread getexecutingThread() {
		return m_executingThread;
	}
	
}
