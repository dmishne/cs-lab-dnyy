package server.core;
import client.common.CEntry;
import client.core.CReader;

import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
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
				while(m_sleeping)
						wait();
				Work=CStandbyUnit.GetInstance().getEntryFromQueue();
				/*handle entry from standby unit*/
				//((CClient_Entry) msg).getSessionID() = m_generator.nextInt();
				if(Work.isLogin())
				{
					handleLogin(Work);
				
	
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
	
	
	
	
	
	private void handleLogin(CEntry Work) 
	{
		//instead of isLogged() - saves checking if it's logged and then finding the session to kill
		for(CClientSession t : m_sessions)
			if(t.isOfUser(Work))
				t.Kill();	/*   - session dead*/
		
		Work.setSessionID(this.m_generator.nextInt());
		
		if(ValidateLogin(Work.getMsgMap().get("user"),Work.getMsgMap().get("password")))			
		{
			//insert to connections
			if(CRespondToClient.GetInstance().isRegistered(Work.getSessionID()+"~"+Work.getUserName()))//extra validation
				CRespondToClient.GetInstance().Remove(Work.getSessionID()+"~"+Work.getUserName());//ovverwrite instance
			CRespondToClient.GetInstance().InsertOutstream(Work.getSessionID()+"~"+Work.getUserName(), Work.getClient());
			
			
			/*TODO:reconsider responder insert method */
			//should move to random method, if session exists then we'll know about it via the sessions set, otherwise we can insert it in StandbyUnit		
			/*int i=this.m_generator.nextInt();
			while(CRespondToClient.GetInstance().isRegistered(i))
				i=this.m_generator.nextInt();*/
			
			
			//create session
			CClientSession newSession=new CClientSession(Work.getSessionID(),Work.getUserName(),CDBInteractionGenerator.GetInstance().MySQLGetAuth(Work.getMsgMap().get("user"))); 
			if (newSession.getSessionID()==-1)
				return ; //quick exit 
			/* TODO: throw out of responder */
			
			//add to List
			this.m_sessions.add(newSession);
			
			//send response to client
			switch (newSession.getSessionID())
			{
			case (0):
				Work.setClient(null);
				break;
			case (1):
				Work.setClient(new CReader());
				
			case (3):
				
			case (5):
				
			}	
			
			Work.getMsgMap().clear();
			Work.getMsgMap().put("SessionID", Integer.toString( Work.getSessionID() ));
			CRespondToClient.GetInstance().SendResponse(Work.getKey(), Work);
			
		}	
	}



	
	private boolean ValidateLogin(String user, String password) 
	{
		ResultSet rs;
		try {
			
		
		rs=CDBInteractionGenerator.GetInstance().MySQL_LoginQuery(user);
	
		if(password.compareTo(rs.getString(2))==0)
			return true;
		//if validated, create session! then respond to client
		else return false;//return failure to client
		
		} catch(SQLException e)
		{ 
			System.out.println("MySQL exception: "+e.getMessage());
			System.out.println("Client Connection rejected due to SQL exception");
		}
		return false;		
	}

	
	
	
	
	
	
	public void NotifyOfEntry()
	{
		if(m_sleeping)
			m_sleeping=false;
		m_obj.m_ThreadHolder.notify(); ///need to check that we're not missing on notifyall()
	}
	
}





