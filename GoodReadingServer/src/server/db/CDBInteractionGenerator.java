package server.db;

import java.sql.*;
import java.util.*;

import client.common.CEntry;
import client.core.CReader;
import server.core.*;

public class CDBInteractionGenerator 
{
	
	private static CDBInteractionGenerator m_obj;
	private Connection m_DB_Connection;
	
	final private static String m_DEFAULTHOST="jdbc:mysql://remote-mysql4.servage.net/nirgeffen";
	final private static String m_DEFAULTUSER="nirgeffen";
	final private static String m_DEFAULTPASS="q1w2e3r4";
	/*
	final private static String m_DEFAULTHOST="jdbc:mysql://localhost";
	final private static String m_DEFAULTUSER="root";
	final private static String m_DEFAULTPASS="m00nkey";
	*/
	
	public ResultSet MySQLQuery(String query) throws SQLException
	{
		return this.m_DB_Connection.createStatement().executeQuery(query);
	}

	
	public int MySQLGetAuth(String user)  
	{
		try
		{
		ResultSet rs=MySQLQuery("SELECT u.Autherization FROM users u WHERE u.user LIKE "+user);
		rs.next();
		return rs.getInt(1);
		} catch(SQLException e)
		{
			System.out.println("SQLException during MySQLGetAuth: "+e.getErrorCode()+" "+e.getMessage());
		}
		return -1;
	}

	
	public ResultSet MySQL_LoginQuery(String user)
	{
		try {
			return this.m_DB_Connection.createStatement().executeQuery("USE nirgeffen; SELECT u.user,u,password FROM users WHERE u.user like '\""+user+"\"'");
		}
		catch(SQLException e)
		{ 
			System.out.println("SQL exception error! "+ e.getMessage());
			return null;
		}
		
	} /////////////////need to fix " inside of DB
	
	
	
	
	
	public static CDBInteractionGenerator GetInstance()
	{
		/* TODO add constructor args */
		if(m_obj == null)
			{
				m_obj=new CDBInteractionGenerator(); //init();
			}
		return m_obj;
	}
	
	
	
	
	private CDBInteractionGenerator()
	{
		try 
		{
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();	
	        m_DB_Connection=DriverManager.getConnection(m_DEFAULTHOST,m_DEFAULTUSER,m_DEFAULTPASS);
            System.out.println("SQL connection succeed");
	 	} 
		catch (Exception ex) 
 	    {/* handle any errors*/
			System.out.println("Error initializing DB connection 5: " + ex.getMessage());
        }
		
	}

	
	
	
	public void ServerUpdateLog(String string) 
	{
		// TODO Auto-generated method stub
		
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
			CClientSession newSession=new CClientSession(Work.getSessionID(),Work.getUserName(),MySQLGetAuth(Work.getMsgMap().get("user"))); 
			if (newSession.getSessionID()==-1)
				return ; //quick exit 
			/* TODO: throw out of responder */
			
			//add to List
			CExecuter.GetInstance().add(newSession);
			
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
			
		
		rs=MySQL_LoginQuery(user);
	
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

	


}