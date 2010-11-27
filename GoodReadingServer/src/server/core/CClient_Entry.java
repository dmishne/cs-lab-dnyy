package server.core;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class CClient_Entry implements Serializable 
{
	String m_type; /* type of request */
	Map <String, String> m_args; /* arguments for request - may change from request to request*/
	public java.net.InetAddress m_IP;
	public int m_sessionID;
	public String m_user;

	boolean isLogin()
	{
		if ( m_type.equals("Login"))
			return true;
		return false;
	}	
}
