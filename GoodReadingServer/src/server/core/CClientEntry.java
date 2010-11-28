package server.core;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class CClientEntry implements Serializable 
{
	public String m_type; /* type of request */
	public Map <String, String> m_args; /* arguments for request - may change from request to request*/
	public java.net.InetAddress m_IP;
	public int m_sessionID;
	public String m_user;
	public Object m_connection;

	boolean isLogin()
	{
		if ( m_type.equals("Login"))
			return true;
		return false;
	}	
}
