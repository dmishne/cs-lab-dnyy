package server.core;
import java.util.*;
import ocsf.server.*;

public class CStandby_Unit  extends AbstractServer 
{
	
	/*signleton*/
	private static CStandby_Unit obj;
	final public static int DEFAULT_PORT = 5555;
	 
	/*Entry queue*/
	private Queue <CClient_Entry> que;
	/*signleton*/
	public static CStandby_Unit GetInstance()
	{
		/* TODO add constructor args */
		synchronized(CStandby_Unit.obj) 
		{
			if(CStandby_Unit.obj == null)
				obj=new CStandby_Unit(DEFAULT_PORT);
			return CStandby_Unit.obj;
		}
	}

	/*Entry queue*/
	public void RemoveEntries( java.net.InetAddress oIP)
	{
		synchronized( que )
		{
			for(CClient_Entry t : que)
				if(t.IP.equals(oIP))
					que.remove(t);
		}
	}
	
	public CClient_Entry getEntryFromQueue() 
	{
		return que.remove();
	}
	
	//checks if queue is empty or have Entries that needs to be taken care of.
	public boolean isEmpty() 
	{
		return que.isEmpty();
	}
	
	/*handle msgs from client*/
	public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
	{
		que.add((CClient_Entry) msg);
		((CClient_Entry) msg).client = client;
		System.out.println("Server recieved client request from" + client);
		CExecuter.GetInstance().notify();
	}

	public CStandby_Unit(int port) 
	{
		super(port);
	}

}
