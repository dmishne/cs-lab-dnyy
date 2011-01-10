package server.main;

import java.io.IOException;
import server.core.CExecuter;
import server.core.CServerConstants;
import server.core.CStandbyUnit;
import server.db.CDBInteractionGenerator;


/*	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *
 *	
 *  CGoodReadingServer class
 *
 *	Holds Main for server initialization and startup sequence.
 *                        
 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 *	 */
public class CGoodReadingServer {

	final public static int Version = 0;
	final public static int Revision = 1;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * 			load properties (configuration)
		 * 			open up window for feedback to user (progress bar + IP address)
		 *   		start up server,
		 *  		first comes DB
		 *  		second comes in the CStandbyUnit, Executer SHOULD go after standby unit, for he calls for an instance of it, however there is no limitation currently, it will justmake some of the code redundant to call it first
		 *	
		 */
		
		CServerConstants.Config(); //load all properties
		
		/*  Here starts the server's pop-up */
		CServerInfo info = null;
	
		if (CServerConstants.POP_WINDOW())
		{
		try {
			 info = new CServerInfo("GoodReadingServer V" + Version + "." + Revision);
			 info.setVisible(true);
			 Thread.sleep(1000);
		} 
		catch(Exception e) { System.out.println("Can't get ip address: "+e.getMessage()); }
		}
		
		/*  Here we halted for DBIG to start up */
		
		CDBInteractionGenerator.GetInstance();
		
		/*  Back to update! */
		if(info != null)
		{
			info.incProgress();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/*  Initialize Standby Unit */
		if(CStandbyUnit.GetInstance() == null)
		{
			System.out.println("Problem Instanciating Standby Unit");
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Problem Instanciating Standby Unit");
		}
		else 
		{
			try {
				CStandbyUnit.GetInstance().listen();
				if(info != null)
				{
					info.incProgress();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				System.out.println("Error - Couldn't listen to client");
				CDBInteractionGenerator.GetInstance().ServerUpdateLog("Error - Couldn't listen to client");
			}
			System.out.println("Standby Unit online and waiting");
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Standby Unit online and waiting");
		}
		
		
		/*  Last but not least comes the executer */
		if(CExecuter.GetInstance() == null)
		{
			System.out.println("Problem Instanciating Executer");
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Problem Instanciating Executer");
		}
		else 
		{
			System.out.println("Excecuter online and waiting");
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Excecuter online and waiting");
			if(info != null)
			{
				info.incProgress();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if(info != null)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			info.done();
		} 
		
	//	System.out.println(CDBInteractionGenerator.GetInstance().insertTopic("Comedy"));
	//	System.out.println(CDBInteractionGenerator.GetInstance().insertSubTopic("Comedy","SitCom"));
	//	System.out.println(CDBInteractionGenerator.GetInstance().getTopics().toString());
	//	System.out.println(CDBInteractionGenerator.GetInstance().getSubTopics("Comedy").toString());
	//	System.out.println(CDBInteractionGenerator.GetInstance().insertBookTopics("1234", "Comedy", "SitCom"));
	//	System.out.println(CDBInteractionGenerator.GetInstance().getBookTopics("1234"));
	//	System.out.println(CDBInteractionGenerator.GetInstance().insertNewBook("61010101021", "topic book else", "topic11", "2000-08-08", "booz", "summi", 8.5, 0, 0, "~utopic@usub1,usub2,usub3,usub4~utopic2@usub1,usub2,usub3~utopic3@usub1,usub3", "lllll", "1. 2. 3.", false, "English"));
	}

}
