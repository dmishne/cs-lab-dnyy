package server.core;

import java.util.Map;
import java.util.TreeMap;
import ocsf.server.ConnectionToClient;

	/**
	 * CRespondToClient class was written with the Single Responsibility principle of the OOP in mind.
	 * it's sole purpose is to return a msg to client.
	 * for this, it ALSO holds the container of client connections
	 * @see server.core.CExecuter main customer for this unit
	 */
public class CRespondToClient {

	private Map <Integer,Object> m_connections;	//holds all connections to clients
	private static CRespondToClient m_obj; 		//used for implementation of the Singleton DP
	
	private CRespondToClient()
	{
		m_connections=new TreeMap<Integer,Object>();
	}
		
	/**
	 * this func is the implementation of the Singleton DP
	 * @return the instance of CRespondToClient
	 */
	public static CRespondToClient GetInstance()
	{
		if(m_obj==null)
			m_obj=new CRespondToClient();
		return m_obj;
	}
		
	/**
	 * Save connection to client (or out stream)
	 */
	public void InsertOutstream(int key,Object stream)
	{
		m_connections.put(new Integer(key),stream);				 		
	}
	
	/**
	 * send response to client
	 */
	public void SendResponse(int i, Object msg)
	{
		if(i < 0)
			return;
		System.out.print("\n\nSending message to client @ "+((ConnectionToClient)m_connections.get(i)).getName());
		if(msg==null)
			System.out.print("	null");
		else System.out.print("	"+msg.toString());
		try{
			if(msg==null)
				((ConnectionToClient)m_connections.get(i)).sendToClient("null");
			else
				((ConnectionToClient)m_connections.get(i)).sendToClient(msg);	
		}
		catch (Exception e) {	
			System.out.println("\nResponse Unit failed to send msg to "+i+": "+e.getMessage());
		}
	}//end of SendResponse
	
	
	/**
	 * is object defined in map
	 * @return answer (is object defined in map / is key registered to a client)
	 */
	public boolean isRegistered(int key)
	{
		return this.m_connections.containsKey(key);
	}
	
	/**
	 * removing an object so we can replace it / returns stream, it is also used in logout to return msg to client
	 * @return connection to client inserted by CExecuter
	 */
	public ConnectionToClient Remove(int key)
	{
		ConnectionToClient arg=(ConnectionToClient) this.m_connections.get(key);
		this.m_connections.remove(key);
		return arg;
	}	
}
