package server.main;

public class SR_Executer implements Runnable
{
	/*signleton*/
	private static SR_Executer obj;
	private boolean sleeping;
	/*signleton*/
	static SR_Executer GetInstance()
	{
		/* TODO add constructor args */
		if(obj == null)
			init();
		return obj;
		
	}
	
	private SR_Executer()
	{
	//todo constructor
	}
	private static void init()
	{
		obj=new SR_Executer();
		obj.sleeping=true;
		new Thread(obj).start();
	}
	public void run()
	{
		SR_Client_Entry Work;
		try {
			while (true)
			{
				if(SR_Standby_Unit.GetInstance().isEmpty())
					sleeping = true;
				while(sleeping)
						wait();
				Work=SR_Standby_Unit.GetInstance().getEntryFromQueue();
				/*handle entry from standby unit*/
				
			}
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Server fail, can't 'wait' in func run via SR_Executer");
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
