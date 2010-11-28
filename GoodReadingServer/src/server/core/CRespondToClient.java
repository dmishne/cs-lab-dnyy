package server.core;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import ocsf.server.ConnectionToClient;

public class CRespondToClient {

	private Map <String,Object> m_connections;
	private static CRespondToClient m_obj;
	
	
	
	
	private CRespondToClient()
	{
		m_connections=new TreeMap<String,Object>();
	}
	
	
	
	
	//get instance for Singleton
	public static CRespondToClient GetInstance()
	{
		if(m_obj==null)
			m_obj=new CRespondToClient();
		return m_obj;
	}
	
	
	
	
	//insert to Map
	public void InsertOutstream(String key,Object stream)
	{
		m_connections.put(key,stream);				 		
	}
	//send response
	public void SendResponse(String user, Object msg)
	{
		try 
		{
			((ConnectionToClient)m_connections.get(user)).sendToClient(msg);
		} 
		catch (IOException e) {	// TODO Auto-generated catch block
			System.out.println("\nResponse Unit failed to send msg to"+user+": "+e.getMessage());
		}
	}
	
	
	//is object defined in map
	public boolean isRegistered(String key)
	{
		return this.m_connections.containsKey(key);
	}
	
	
	
	//removing an object so we can replace it
	public void Remove(String key)
	{
		this.m_connections.remove(key);
	}
	
	/* TODO think about timeout */
	
}
