package server.core;

public class CClient_Session 
{
	public java.net.InetAddress m_IP;
	public int m_sessionID;
	public String m_user;
	public boolean isOfUser(CClient_Entry b)
	{
		if (this.m_IP == b.m_IP && this.m_user == b.m_user)
			return true;
		return false;
	}
	//session.kill
}
