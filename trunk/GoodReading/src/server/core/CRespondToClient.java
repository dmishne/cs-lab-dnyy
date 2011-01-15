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
	/**
	 * holds all connections to clients
	 */
	private Map <Integer,Object> m_connections;	
	/**
	 * used for implementation of the Singleton DP
	 */
	private static CRespondToClient m_obj;
	
	/** simple constructor for class, only allocates a new instance for m_connections */
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
	 * @param key the key (Session ID) of the client's relevant stream
	 * @param stream is the stream via which we send the response
	 */
	public void InsertOutstream(int key,Object stream)
	{
		m_connections.put(new Integer(key),stream);				 		
	}
	
	/**
	 * send response to client
	 * @param i is the index (key / sid) of the client's stream
	 * @param msg is the message to send to client
	 */
	public void SendResponse(int i, Object msg)
	{
		if(i == -1)
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
	 * simple Container reference - is object defined in map ?
	 * @param key the key we're searching for in the m_connection container.
	 * @return answer (is object defined in map / is key registered to a client)
	 */
	public boolean isRegistered(int key)
	{
		return this.m_connections.containsKey(key);
	}
	
	/**
	 * removing an object so we can replace it / returns stream, it is also used in logout to return msg to client
	 * @param key the key by which to remove the stream from our list
	 * @return connection to client inserted by CExecuter
	 */
	public ConnectionToClient Remove(int key)
	{
		ConnectionToClient arg=(ConnectionToClient) this.m_connections.get(key);
		this.m_connections.remove(key);
		return arg;
	}	
}
