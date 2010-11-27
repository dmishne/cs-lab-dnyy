package server.core;

public class CExecuter implements Runnable
{
	/*signleton*/
	private static CExecuter obj;
	private boolean sleeping;
	/*signleton*/
	static CExecuter GetInstance()
	{
		/* TODO add constructor args */
		if(obj == null)
			init();
		return obj;
		
	}
	
	private CExecuter()
	{
	//todo constructor
	}
	private static void init()
	{
		obj=new CExecuter();
		obj.sleeping=true;
		new Thread(obj).start();
	}
	public void run()
	{
		CClient_Entry Work;
		try {
			while (true)
			{
				if(CStandby_Unit.GetInstance().isEmpty())
					sleeping = true;
				while(sleeping)
						wait();
				Work=CStandby_Unit.GetInstance().getEntryFromQueue();
				/*handle entry from standby unit*/
				
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
		if(sleeping)
			sleeping=false;
		notifyAll();
	}
}
