package server.core;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import ocsf.server.ConnectionToClient;

public class CRespond_To_Client {

	private Map <String,Object> m_connections;
	private static CRespond_To_Client m_obj;
	
	private CRespond_To_Client()
	{
		m_connections=new TreeMap<String,Object>();
	}
	
	//get instance for Singleton
	public static CRespond_To_Client GetInstance()
	{
		if(m_obj==null)
			m_obj=new CRespond_To_Client();
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
	
	/* TODO think about timeout */
	
}
