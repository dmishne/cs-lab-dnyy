package server.core;

public class CClientSession 
{
	//public java.net.InetAddress m_IP;
	private int m_SessionID;
	private String m_UserName;
	private Thread m_executingThread; /*TODO: might not be needed, to check on later*/
	
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
