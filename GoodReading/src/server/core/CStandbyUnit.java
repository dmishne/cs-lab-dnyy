package server.core;
import java.util.LinkedList;
import java.util.Queue;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import common.api.CEntry;
/**
 * class responsibility is thus: get requests from client, store them, and tell the CExecuter he's got work.
 * @see server.core.CExecuter
 * @see common.api.CEntry
 */
public class CStandbyUnit  extends AbstractServer 
{
	
	/**Singleton*/
	private static CStandbyUnit m_obj;

	/**Entry queue*/
	private Queue <CEntry> m_que;

	/**
	 * Implementation for the Singleton DP
	 * @returns the instance of CStandbyUnit
	 */	
	public static CStandbyUnit GetInstance()
	{
		if(CStandbyUnit.m_obj == null)
			m_obj=new CStandbyUnit(CServerConstants.DEFAULT_PORT());
		return CStandbyUnit.m_obj;
	}
	/** simple constructor for this class
	 * @param port determines to which port the server will listen
	 */
	private CStandbyUnit(int port) 
	{
		super(port);
		m_que=new LinkedList<CEntry>();
	}
	/**
	 * used by CExecuter to get a request from the clients.
	 * @returns CEntry which is the base for the client-server API.
	 * @see common.api.CEntry
	 */
	public CEntry getEntryFromQueue() 
	{
		return m_que.remove();
	}
	
	/**
	 * checks if queue is empty or have Entries that needs to be taken care of.
	 * @return answer in boolean
	 */
	public boolean isEmpty() 
	{
		return m_que.isEmpty();
	}
	
	
	
	/**
	 * Implementation for the OCSF package.
	 * handle messages from client.
	 */
	public void handleMessageFromClient (Object msg, ConnectionToClient client)
	{
		((CEntry) msg).setClient(client); //save client so we could send the response later on.
		m_que.add((CEntry) msg);
		System.out.println("Server recieved client request from " + client.getInetAddress());
		CExecuter.GetInstance().NotifyOfEntry();
	}

}
