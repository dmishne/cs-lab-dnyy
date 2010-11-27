package server.core;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class CExecuter implements Runnable
{
	private Set <CClient_Session> m_sessions;
	/*signleton*/
	private static CExecuter m_obj;
	
	private boolean m_sleeping; // @param indicates Executer is sleeping
	private Random m_generator;	//infrastracture helping to generate random numbers.
	/*TODO add randomly generated sessionIDs */

	
	/*signleton*/
	static CExecuter GetInstance()
	{
		/* TODO add constructor args */
		if(m_obj == null)
			init();
		return m_obj;
	}
	
	private CExecuter()
	{
	//todo constructor
		this.m_sessions=new TreeSet<CClient_Session>();
	}
	private static void init()
	{
		m_obj=new CExecuter();
		m_obj.m_sleeping=true;
		m_obj.m_generator = new Random( 19580427 );
		
		new Thread(m_obj).start();
	}
	
	public void run()
	{
		CClient_Entry Work;
		try {
			while (true)
			{
				if(CStandby_Unit.GetInstance().isEmpty())
					m_sleeping = true;
				while(m_sleeping)
						wait();
				Work=CStandby_Unit.GetInstance().getEntryFromQueue();
				/*handle entry from standby unit*/
				//((CClient_Entry) msg).m_sessionID = m_generator.nextInt();
				if(Work.isLogin())
				{
					for(CClient_Session t : m_sessions)
						if(t.isOfUser(Work))
							;/*TODO return fail*/
					//this here is the else option
					
				}
					
			}
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Server fail, can't 'wait' in func run via CExecuter");
			e.printStackTrace();
		}
	}
	
	public void Notify()
	{
		if(m_sleeping)
			m_sleeping=false;
		notifyAll();
	}
}
