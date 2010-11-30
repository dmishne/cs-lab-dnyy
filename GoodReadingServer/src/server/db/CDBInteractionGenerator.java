package server.db;

import java.sql.*;

import client.common.CEntry;
import client.core.*;
import server.core.*;

public class CDBInteractionGenerator 
{
	
	private static CDBInteractionGenerator m_obj;
	private Connection m_DB_Connection;
	/*
	final private static String m_DEFAULTHOST="jdbc:mysql://remote-mysql4.servage.net/nirgeffen";
	final private static String m_DEFAULTUSER="nirgeffen";
	final private static String m_DEFAULTPASS="q1w2e3r4";
	*/
	final private static String m_DEFAULTHOST="jdbc:mysql://localhost/cslabdnyy";
	final private static String m_DEFAULTUSER="root";
	final private static String m_DEFAULTPASS="m00nkey";
	
	
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






	public void handleLogin(CEntry Work) 
	{
	
						
		if(ValidateLogin(Work.getMsgMap().get("user"),Work.getMsgMap().get("password")))			
		{	
			
			//create session
			CClientSession newSession=new CClientSession(Work.getSessionID(),Work.getUserName(),MySQLGetAuth(Work.getMsgMap().get("user"))); 
			if (newSession.getSessionID()==-1)
			{
				CRespondToClient.GetInstance().Remove(Work.getSessionID());
				return ; //quick exit
			}

			
			//add to List
			CExecuter.GetInstance().add(newSession);
			
			//send response to client
			switch (newSession.getSessionID())
			{
			
				case (0):
					Work.setClient(null);
					break;
				case (1):
				//	Work.setClient(new CReader());
					
				case (3):
					
				case (5):
				
			}	
			
			Work.getMsgMap().clear();
			Work.getMsgMap().put("SessionID", Integer.toString( Work.getSessionID() ));
			
			if(CExecuter.GetInstance().isLogged(Work))
				CExecuter.GetInstance().Kill(Work);	/*   - session dead*/
			
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