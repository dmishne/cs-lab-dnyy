package server.core;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class CClient_Entry implements Serializable {
	String type; /* type of request */
	Map <String, String> args; /* arguments for request - may change from request to request*/
	java.net.InetAddress IP;
	Object client;
	
	boolean isLogin()
	{
		if ( type.equals("Login"))
			return true;
		return false;
	}	
}
