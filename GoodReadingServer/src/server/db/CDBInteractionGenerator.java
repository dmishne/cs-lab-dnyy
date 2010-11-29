package server.db;

import java.sql.*;
import java.util.*;
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
}