package server.core;

public class CClientSession 
{
	public java.net.InetAddress m_IP;
	public int m_sessionID;
	public String m_user;
	public Thread m_executingThread; /*TODO: might not be needed, to check on later*/
	
	public boolean isOfUser(CClientEntry b)
	{
		if (this.m_IP == b.m_IP && this.m_user == b.m_user)
			return true;
		return false;
	}
	//session.kill
	public void Kill() {
		// TODO Auto-generated method stub
		
	}
	
}
