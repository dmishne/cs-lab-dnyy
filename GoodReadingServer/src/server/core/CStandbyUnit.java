package server.core;
import java.util.*;
import ocsf.server.*;
import client.common.CEntry;

public class CStandbyUnit  extends AbstractServer 
{
	
	/*signleton*/
	private static CStandbyUnit m_obj;
	final public static int DEFAULT_PORT = 5555;

	/*Entry queue*/
	private Queue <CEntry> m_que;
	/*signleton*/
	public static CStandbyUnit GetInstance()
	{
		/* TODO add constructor args */
		synchronized(CStandbyUnit.m_obj) 
		{
			if(CStandbyUnit.m_obj == null)
				m_obj=new CStandbyUnit(DEFAULT_PORT);
			return CStandbyUnit.m_obj;
		}
	}

	/*Entry queue
	public void RemoveEntries( java.net.InetAddress oIP)
	{
		synchronized( m_que )
		{
			for(CEntry t : m_que)
				if(t.m_IP.equals(oIP))
					m_que.remove(t);
		}
	}*/
	
	public CEntry getEntryFromQueue() 
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
		((CEntry) msg).setClient(client); //save client so we could send the response later on.
		m_que.add((CEntry) msg);
		System.out.println("Server recieved client request from" + client);
		CExecuter.GetInstance().NotifyOfEntry();
	}

	public CStandbyUnit(int port) 
	{
		super(port);
	}

}
