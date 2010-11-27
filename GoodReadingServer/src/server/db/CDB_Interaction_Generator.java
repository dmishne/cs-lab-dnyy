package server.db;

import java.sql.*;
import java.util.TreeSet;

import server.core.CClient_Session;
import server.core.CExecuter;

public class CDB_Interaction_Generator 
{
	
	private static CDB_Interaction_Generator m_obj;
	private Connection m_DB_Connection;
	final private static String m_DEFAULTHOST="jdbc:mysql://remote-mysql4.servage.net:3306";
	final private static String m_DEFAULTUSER="nirgeffen";
	final private static String m_DEFAULTPASS="q1w2e3r4";
	
	
	
	static CDB_Interaction_Generator GetInstance()
	{
		/* TODO add constructor args */
		if(m_obj == null)
			m_obj=new CDB_Interaction_Generator(); //init();
		return m_obj;
	}
	
	private CDB_Interaction_Generator()
	{
		try 
		{
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	        m_DB_Connection=DriverManager.getConnection(m_DEFAULTHOST,m_DEFAULTUSER,m_DEFAULTPASS);
	 
	 	} 
		catch (Exception ex) 
 	    {/* handle any errors*/
			System.out.println("Error initializing DB connection: " + ex.getMessage());
        }
		
	}
}