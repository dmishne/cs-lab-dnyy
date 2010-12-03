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
	final private static String m_DEFAULTHOST="jdbc:mysql://remote-mysql4.servage.net/cslabdnyy";
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
			return this.m_DB_Connection.createStatement().executeQuery("SELECT u.user,u.password FROM users u WHERE u.user like '\""+user+"\"';");
		}
		catch(SQLException e)
		{ 
			System.out.println("SQL exception error! "+ e.getMessage());
			
			return null;
		}
		
	} /////////////////TODO:need to fix " inside of DB
	
	
	
	
	
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






	



	
	public boolean ValidateLogin(String user, String password) 
	{
		ResultSet rs;
		try {
			
		rs=MySQL_LoginQuery(user);
		if(!rs.next())
			return false;
		
		if(password.compareTo(rs.getString(2).replaceAll("\"", ""))==0)
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
	
	public boolean RemoveCC(String user)
	{
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("DELETE from credit_card_details ccd WHERE ccd.user LIKE "+user+";");
			return true;	
		} catch (SQLException e) {
			System.out.println("RemoveCC():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	
		return false;
	}
	public boolean AddCC(String user,String CCnum,String CCExpire,String CCid)
	{
		try {
			this.MySQLQuery("INSERT INTO `credit_card_details` (`user`,`cc_num`,`cc_expire`,`cc_id`) VALUES ('"+user+"','"+CCnum+"','"+CCExpire+"','"+CCid+"'");
			return true;	
		} catch (SQLException e) {
			System.out.println("AddCC():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	
		return false;
	}
	
	public AUser getUserInstance(String user,int sessionID)
	{
		ResultSet rs;
		try {
			rs= this.MySQLQuery("SELECT * FROM users u WHERE u.user LIKE \""+user+"\";");
		if(!rs.next()) //nothing in rs
			return null;
		AUser arg;
		switch (rs.getInt(3))
		{
		case (0):
			return null;
		case (1):
			arg=new CReader(rs.getString(7), rs.getString(8), rs.getInt(5), user, sessionID);
			return arg;
		case (3):
			arg=new CLibrarian(rs.getString(7), rs.getString(8), rs.getInt(5), user, sessionID);
			return arg;
		case (5):
			arg=new CLibraryManager(rs.getString(7), rs.getString(8), rs.getInt(5), user, sessionID);
			return arg;
		}
		
		
		
		//TODO: add log
		} catch (SQLException e) {
			System.out.print("SQL Exception while gettins user info, func GetUsernstance: "+e.getErrorCode()+" "+e.getMessage());	}
	
		return null;
		
	}
	

}