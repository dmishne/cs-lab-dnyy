package server.main;
import java.io.*;
import java.util.Queue;

import ocsf.server.*;


public class SR_Standby_Unit  extends AbstractServer 
{
	/*signleton*/
	private static SR_Standby_Unit obj;
	final public static int DEFAULT_PORT = 5555;
	 
	/*Entry queue*/
	private Queue <SR_Client_Entry> que;
	/*signleton*/
	public static SR_Standby_Unit GetInstance()
	{
		/* TODO add constructor args */
		if(obj == null)
			obj=new SR_Standby_Unit();
		return obj;
	}

	/*Entry queue*/
	public void RemoveEntries( java.net.InetAddress oIP)
	{
		for(SR_Client_Entry t : que)
			if(t.IP.equals(oIP))
				que.remove(t);
	}
	
	public SR_Client_Entry getEntryFromQueue() 
	{
		return que.remove();
	}
	public boolean isEmpty() 
	{
		return que.isEmpty();
	}
	
	/*handle msgs from client*/
	public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
	{
		que.add((SR_Client_Entry) msg);
		((SR_Client_Entry) msg).client = client;
		System.out.println("Server recieved client request from" + client);
		SR_Executer.GetInstance().notify();
	}

}
