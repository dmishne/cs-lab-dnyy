package server.core;
import client.common.*;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import server.db.CDBInteractionGenerator;


public class CExecuter implements Runnable
{
	private Set <CClientSession> m_sessions;
	private boolean m_sleeping; // @param indicates Executer is sleeping
	private Thread m_ThreadHolder;
	private Random m_generator;	//infrastracture helping to generate random numbers.
	/*TODO add randomly generated sessionIDs */
	//private static CExecuter m_obj;/*signleton*/
	
	
	
	
	/*signleton*/
	private static CExecuter m_obj;
	public static CExecuter GetInstance()
	{
		/* TODO add constructor args */
		if(m_obj == null)
			init();
		return m_obj;
	}

	
	
	private CExecuter()
	{
	//Instance is configured inside init()
		this.m_sessions=new TreeSet<CClientSession>();
	}
	
	
	
	
	
	private static void init()
	{
		m_obj=new CExecuter();
		m_obj.m_sleeping=true;
		m_obj.m_generator = new Random( 19580427 );
		//new Thread(m_obj).start();
		m_obj.m_ThreadHolder=new Thread(m_obj);
		m_obj.m_ThreadHolder.start();
		
	}
	
	
	
	
	
	public void run()
	{
		CEntry Work;
		try {
			while (true)
			{
				if(CStandbyUnit.GetInstance().isEmpty())
					m_sleeping = true;
				synchronized(m_obj)
				{
					while(m_sleeping)
						wait();
				}
				Work=CStandbyUnit.GetInstance().getEntryFromQueue();
				/*handle entry from standby unit*/
				//((CClient_Entry) msg).getSessionID() = m_generator.nextInt();
				if(Work.isLogin())
				{
					//make sure nobody's using the selected key
					do {
						Work.setSessionID(Random());
					} while( CRespondToClient.GetInstance().isRegistered(Work.getSessionID()) );
					
					//insert to Connections
					CRespondToClient.GetInstance().InsertOutstream(Work.getSessionID(), Work.getClient());
					
					//handle Login
					CDBInteractionGenerator.GetInstance().handleLogin(Work);
					
				}//end of login handling
				else 
				{
					boolean flag=false;
					for(CClientSession t : m_sessions)
						if(t.isOfUser(Work))
							flag=true;
					if(flag == false)
					;/* TODO:respond false and close! */
					else
					{
						
						
					}
				}//end of isLogin
					
			}//end of while(forever)
		}//try 
		catch (InterruptedException e) 
		{
			System.out.println("Server fail, can't 'wait' in func run via CExecuter");
			e.printStackTrace();
		}
	}
	
	
	
	
	public void add(CClientSession s)
	{
		this.m_sessions.add(s);
	}
	public  boolean isLogged(CEntry Work)
	{
	//instead of isLogged() - saves checking if it's logged and then finding the session to kill
	for(CClientSession t : m_sessions)
		if(t.isOfUser(Work))
			return true;
	return false;
	}
	

	
	public void NotifyOfEntry()
	{
		synchronized(m_obj)
		{
			if(m_sleeping)
				m_sleeping=false;
			notify(); ///need to check that we're not missing on notifyall()
		}
	}
	
	
	public int Random()
	{
		return m_generator.nextInt();
	}



	public void Kill(CEntry work) {
		// TODO Auto-generated method stub
		
	}



	public Thread getThread() {
		// TODO Auto-generated method stub
		return m_ThreadHolder;
	}
	
}





