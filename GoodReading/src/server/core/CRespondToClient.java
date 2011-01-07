package server.core;

import java.util.Map;
import java.util.TreeMap;

import ocsf.server.ConnectionToClient;

public class CRespondToClient {

	private Map <Integer,Object> m_connections;
	private static CRespondToClient m_obj;
	
	private CRespondToClient()
	{
		m_connections=new TreeMap<Integer,Object>();
	}
		
	//get instance for Singleton
	public static CRespondToClient GetInstance()
	{
		if(m_obj==null)
			m_obj=new CRespondToClient();
		return m_obj;
	}
		
	//insert to Map
	public void InsertOutstream(int key,Object stream)
	{
		m_connections.put(new Integer(key),stream);				 		
	}
	
	//send response
	public void SendResponse(int i, Object msg)
	{
		System.out.println("Sending message to client @ "+((ConnectionToClient)m_connections.get(i)).getName());
		if(msg==null)
			System.out.print("	null");
		else System.out.print("	"+msg.toString());
		try{
			if(msg==null)
				((ConnectionToClient)m_connections.get(i)).sendToClient("null");
			else
				((ConnectionToClient)m_connections.get(i)).sendToClient(msg);	
		}
		catch (Exception e) {	// TODO Auto-generated catch block
			System.out.println("Response Unit failed to send msg to "+i+": "+e.getMessage());
		}
	}
	
	//is object defined in map
	public boolean isRegistered(int key)
	{
		return this.m_connections.containsKey(key);
	}
	
	//removing an object so we can replace it
	public ConnectionToClient Remove(int key)
	{
		ConnectionToClient arg=(ConnectionToClient) this.m_connections.get(key);
		this.m_connections.remove(key);
		return arg;
	}
	
	/*
	 *  TODO:
	 *  think about timeout  
	*/
	
}
