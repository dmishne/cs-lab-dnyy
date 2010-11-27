package server.core;
import java.util.*;
import ocsf.server.*;

public class CStandby_Unit  extends AbstractServer 
{
	
	/*signleton*/
	private static CStandby_Unit m_obj;
	final public static int DEFAULT_PORT = 5555;

	/*Entry queue*/
	private Queue <CClient_Entry> m_que;
	/*signleton*/
	public static CStandby_Unit GetInstance()
	{
		/* TODO add constructor args */
		synchronized(CStandby_Unit.m_obj) 
		{
			if(CStandby_Unit.m_obj == null)
				m_obj=new CStandby_Unit(DEFAULT_PORT);
			return CStandby_Unit.m_obj;
		}
	}

	/*Entry queue*/
	public void RemoveEntries( java.net.InetAddress oIP)
	{
		synchronized( m_que )
		{
			for(CClient_Entry t : m_que)
				if(t.m_IP.equals(oIP))
					m_que.remove(t);
		}
	}
	
	public CClient_Entry getEntryFromQueue() 
	{
		return m_que.remove();
	}
	
	//checks if queue is empty or have Entries that needs to be taken care of.
	public boolean isEmpty() 
	{
		return m_que.isEmpty();
	}
	
	/*handle msgs from client*/
	public void handleMessageFromClient (Object msg, ConnectionToClient client)
	{
		m_que.add((CClient_Entry) msg);
		System.out.println("Server recieved client request from" + client);
		CExecuter.GetInstance().Notify();
		//client.sendToClient(msg);/* TODO add this client as a map for the sender type <ConnectionToClient> */
	}

	public CStandby_Unit(int port) 
	{
		super(port);
	}

}
