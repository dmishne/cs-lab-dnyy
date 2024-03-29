package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import server.core.CServerConstants;
import client.core.AUser;
import client.core.CLibrarian;
import client.core.CLibraryManager;
import client.core.CReader;
import client.core.EActor;

import common.data.CBook;
import common.data.CBookReview;
import common.data.CBookStats;
import common.data.CFile;
import common.data.CPurchaseStats;
import common.data.CUser;

public class CDBInteractionGenerator 
{
	private Connection m_DB_Connection;
	private static CDBInteractionGenerator m_obj;
	
	/**
	 * Executes an SQL query
	 * @param query
	 * @return ResultSet with the SQLQuery result from DB
	 * @throws SQLException
	 */
	public ResultSet MySQLQuery(String query) throws SQLException
	{
		return this.m_DB_Connection.createStatement().executeQuery(query);	
	}

	/**
	 * returns the authorization level of the user
	 * @param user
	 * @return authorization level for user
	 */
	public int MySQLGetAuth(String user)  
	{
		try
		{
			ResultSet rs = MySQLQuery("SELECT u.authorization FROM users u WHERE u.user LIKE '" + user + "';");
			if(rs.next()){
				int i=rs.getInt(1);
				rs.close();
				return i;
			}
			rs.close();
		} catch(SQLException e)
		{
			System.out.println("SQLException during MySQLGetAuth: "+e.getErrorCode()+" "+e.getMessage());
		}
		return -1;
	}

	/**
	 * Executes SQL statement
	 * @param statement
	 * @return true / false on success / failure
	 */
	private boolean MySQLExecute(String statement)
	{
		try{
			//create statement
			Statement st = this.m_DB_Connection.createStatement();
			//execute statement
			st.executeUpdate(statement);
			return true;	//return success
		} catch (SQLException e) {
				System.out.println("can't execute statement: SQL exception: "+e.getErrorCode()+" "+e.getMessage());		
				return false;
		}
	}
	
	/**
	 * returns login information of user
	 * @param user
	 * @return login information of user
	 */
	public ResultSet MySQLLoginQuery(String user)
	{
		try {
			return this.m_DB_Connection.createStatement().executeQuery("SELECT u.user,u.password,u.suspended FROM users u WHERE u.user like '"+user+"';");
		}
		catch(SQLException e)
		{ 
			System.out.println("SQL exception error! "+ e.getMessage());
			
			return null;
		}
	} 
	
	/**
	 * Returns the (only) instance of CDBInteractionGenerator
	 * @return instance of CDBInteractionGenerator
	 */
	public static CDBInteractionGenerator GetInstance()
	{
		if(m_obj == null)
			{
				m_obj=new CDBInteractionGenerator();
			}
		return m_obj;
	}
	
	/**
	 *  Constructor for CDBInteractionGenerator class
	 */
	private CDBInteractionGenerator()
	{
		try 
		{
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();	
	        m_DB_Connection=DriverManager.getConnection(CServerConstants.DEFAULTHOST(),CServerConstants.DEFAULTUSER(),CServerConstants.DEFAULTPASS());
            System.out.println("SQL connection succeed");
	 	} 
		catch (Exception ex) 
 	    {
			System.out.println("Error initializing DB connection 5: " + ex.getMessage());
        }
		
	}

	
	
	/**
	 * Updates log
	 */
	public void ServerUpdateLog(String toLog) 
	{
		System.err.println(toLog);
	}
	
	/**
	 * Validates the user login details
	 * @param user
	 * @param password
	 * @return true if login is OK, false otherwise
	 */
	public boolean ValidateLogin(String user, String password) 
	{
		ResultSet rs;
		try {
			
		rs=MySQLLoginQuery(user);
		if(!rs.next() || rs.getInt(3) == 1 )
			{
				rs.close();
				return false;
			}
		
		if(password.compareTo(rs.getString(2).replaceAll("\"", ""))==0)
			{ 
				rs.close();
				return true;
			}
		//if validated, create session! then respond to client
		rs.close();
		return false;//return failure to client
		
		} catch(SQLException e)
		{ 
			System.out.println("MySQL exception: "+e.getMessage());
			System.out.println("Client Connection rejected due to SQL exception");
		}
		return false;		
	}
	
	/**
	 * Deletes a CreditCard details from database
	 * @param user
	 * @return true on success, false on failure
	 */
	public boolean RemoveCC(String user)
	{
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("DELETE from credit_card_details WHERE user LIKE '"+user+"';");
			return true;	
		} catch (SQLException e) {
			System.out.println("RemoveCC():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	
		return false;
	}
	
	/**
	 * Adds CreditCard details of a user into database
	 * @param user
	 * @param CCnum
	 * @param CCExpire
	 * @param CCid
	 * @return true on success, false on failure
	 */
	public boolean AddCC(String user,String CCnum,String CCExpire,String CCid)
	{ 
		if(CCnum.length() > 12) return false;
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("INSERT INTO credit_card_details VALUES ('"+user+"',"+CCnum+",'"+CCExpire+"',"+CCid+")");
			st.executeUpdate("INSERT INTO subscriptions VALUES ('"+ user +"','Credit Card',100000)");
			return true;	
		} catch (SQLException e) {
			System.out.println("AddCC():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	
		return false;
	}
	
	/**
	 * Creates an instance of AUser by user and sessionID
	 * @param user
	 * @param sessionID
	 * @return new instance of AUser for the specified user
	 */
	public AUser getUserInstance(String user,int sessionID)
	{
		ResultSet rs;
		try {
			rs= this.MySQLQuery("SELECT * FROM users u WHERE u.user LIKE '"+user+"';");
		if(!rs.next()) //nothing in rs
		{
			rs.close();
			return null;
		}
			
		AUser arg;
		switch (rs.getInt(3))
		{
		case (0):
			return null;
		case (1): //User level
			arg=new CReader(rs.getString(7), rs.getString(8), rs.getInt(5), user, sessionID, EActor.User);
			rs.close();
			return arg;
		case (2): //Reader level
			arg=new CReader(rs.getString(7), rs.getString(8), rs.getInt(5), user, sessionID, EActor.Reader);
			rs.close();
			return arg;
		case (3): //Librarian level
			arg=new CLibrarian(rs.getString(7), rs.getString(8), rs.getInt(5), user, sessionID);
			rs.close();
			return arg;
		case (5): //Manager level
			arg=new CLibraryManager(rs.getString(7), rs.getString(8), rs.getInt(5), user, sessionID);
			rs.close();
			return arg;
		default:
			return null;
		}
		//TODO: add log
		} catch (SQLException e) {
			System.out.print("SQL Exception while gettins user info, func GetUsernstance: "+e.getErrorCode()+" "+e.getMessage());	}
		return null;
		
	}

	/**
	 * Adds a monthly subscription in the database for the user
	 * @param userName
	 * @return true on success, false on failure
	 */
	public boolean AddMonthly(String userName) {
		try {
			ResultSet rs=MySQLQuery("SELECT * FROM subscriptions s WHERE s.type LIKE 'monthly' AND s.user LIKE '"+userName+"';");
			if(rs.next())
				//if there is a row already in table, then update
				this.MySQLExecute("UPDATE subscriptions SET ammount="+(CServerConstants.MONTHLY_AMMOUNT()+rs.getInt(3))+" WHERE type LIKE 'monthly' AND user LIKE '"+userName+"';");
			else
				//else, we need to insert
				this.MySQLExecute("INSERT INTO subscriptions VALUES ('"+userName+"','monthly',"+CServerConstants.MONTHLY_AMMOUNT()+");");
			this.MySQLExecute("UPDATE users SET authorization=2 WHERE user LIKE '"+userName+"';");
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException during AddMonthly: "+e.getErrorCode()+" "+e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Adds a yearly subscription in the database for the user
	 * @param userName
	 * @return true on success, false on failure
	 */
	public boolean AddYearly(String userName) {
		try {
			ResultSet rs=MySQLQuery("SELECT * FROM subscriptions s WHERE s.type LIKE 'yearly' AND s.user LIKE '"+userName+"';");
			if(rs.next())
				//if there is a row already in table, then update
				this.MySQLExecute("UPDATE subscriptions SET ammount="+(CServerConstants.YEARLY_AMMOUNT()+rs.getInt(3))+" WHERE type LIKE 'yearly' AND user LIKE '"+userName+"';");
			else
				//else, we need to insert
				this.MySQLExecute("INSERT INTO subscriptions VALUES ('"+userName+"','yearly',"+CServerConstants.YEARLY_AMMOUNT()+");");
			this.MySQLExecute("UPDATE users SET authorization=2 WHERE user LIKE '"+userName+"';");
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException during AddYearly: "+e.getErrorCode()+" "+e.getMessage());
			return false;
		}
		return true;
	}
	

	/**
	 * Builds the WHERE clause for search book function
	 * @param params
	 * @return SQL syntax WHERE clause
	 */
	private String buildSearchBookWhere(Map<String,String> params)
	{ 
		String ans="";
		String Delimiter="AND";
		
		if(params.containsKey("toggle") && params.get("toggle").compareTo("true") == 0)
			Delimiter="OR";
		params.remove("toggle");
		
		if(params.isEmpty())
			return ans;
		ans="WHERE ";
		
		
		Set<String> a = params.keySet();
		for(String arg: a)
			if (params.get(arg).equals(""))
				params.remove(arg);
		
		if(params.containsKey("title"))
		{
			ans=ans+"title LIKE '%"+params.get("title")+"%'";
			params.remove("title");
		}
		else if(params.containsKey("author"))
		{
			ans=ans+"author LIKE '%"+params.get("author")+"%'";
			params.remove("author");
		}
		else if(params.containsKey("labels"))
		{
			ans=ans+"lables LIKE '%"+params.get("labels")+"%'";
			params.remove("labels");
		}
		else if(params.containsKey("isbn"))
		{
			ans=ans+"isbn LIKE '"+params.get("isbn")+"'";
			params.remove("isbn");
		}
		else if(params.containsKey("publisher"))
		{
			ans=ans+"publisher LIKE '%"+params.get("publisher")+"%'";
			params.remove("publisher");
		}
		else if(params.containsKey("summary"))
		{
			ans=ans+"summary LIKE '%"+params.get("summary")+"%'";
			params.remove("summary");
		}
		else if(params.containsKey("toc"))
		{
			ans=ans+"TOC LIKE '%"+params.get("toc")+"%'";
			params.remove("toc");
		}
		else if(params.containsKey("language"))
		{
			ans=ans+"language LIKE '%"+params.get("language")+"%'";
			params.remove("language");
		}
	
		//now inserting new attributes
		if(params.containsKey("title"))
		{
			ans=ans+" "+Delimiter+" title LIKE '%"+params.get("title")+"%'";
		}
		 if(params.containsKey("author"))
		{
			ans=ans+" "+Delimiter+" author LIKE '%"+params.get("author")+"%'";
		}
		 if(params.containsKey("labels"))
		{
			ans=ans+" "+Delimiter+" lables LIKE '%"+params.get("labels")+"%'";
		}
		 if(params.containsKey("isbn"))
		{
			ans=ans+" "+Delimiter+" isbn LIKE '"+params.get("isbn")+"'";
		}
		 if(params.containsKey("publisher"))
		{
			ans=ans+" "+Delimiter+" publisher Like '%"+params.get("publisher")+"%'";
		}
		 if(params.containsKey("summary"))
		{
			ans=ans+" "+Delimiter+" summary LIKE '%"+params.get("summary")+"%'";
		}
		 if(params.containsKey("TOC"))
		{
			ans=ans+" "+Delimiter+" TOC LIKE '%"+params.get("toc")+"%'";
		}
		 if(params.containsKey("language"))
		{
			ans=ans+" "+Delimiter+" language LIKE '%"+params.get("language")+"%'";
		}
		if(Delimiter.compareTo("OR") == 0)
			params.put("toggle","true");
		else params.put("toggle","false");
			
		return ans;		
	}

	
	/**
	 * Searches for a book in the database
	 * @param msgMap
	 * @return a list of books
	 */
	public LinkedList<CBook> SearchBook(Map<String, String> msgMap) 
	{
		LinkedList<CBook> arg=new LinkedList<CBook>();
		ResultSet data=null;
		if(msgMap.get("topic") == null && msgMap.get("subtopic") == null)
		{
			try {
				data = this.MySQLQuery("SELECT * FROM books "+this.buildSearchBookWhere(msgMap)+";");
				while(data.next())
					arg.add(new CBook(data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6),data.getFloat(7),data.getInt(8),data.getLong(9),getBookTopics(data.getString(1)),data.getString(11),data.getString(12),data.getBoolean(13),data.getString(14),data.getInt(15)));
				} catch (Exception e) 
			{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}	
		}
		else if(msgMap.get("subtopic") == null)
		{
			String atopic =  msgMap.get("topic");
			msgMap.remove("topic");
			boolean bool = false;
			if(msgMap.size() > 1)
			{
				bool = true;
			}
			try {
				data = this.MySQLQuery("SELECT * FROM books "+this.buildSearchBookWhere(msgMap)+";");
				while(data.next())
					arg.add(new CBook(data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6),data.getFloat(7),data.getInt(8),data.getLong(9),getBookTopics(data.getString(1)),data.getString(11),data.getString(12),data.getBoolean(13),data.getString(14),data.getInt(15)));
			
				} catch (Exception e) 
			{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
				LinkedList<CBook> argtopic=new LinkedList<CBook>();
				argtopic = searchByTopicOnly(atopic);
				LinkedList<CBook> list =new LinkedList<CBook>();
				if(!bool) return argtopic;	//in case there are only topics and subtopics to search from
				//first we check if we don't need to use OR
				if(msgMap.containsKey("toggle") && msgMap.get("toggle").compareTo("true") != 0)
					for(int i = 0 ; i < arg.size(); i++)
					{
						for(int j =0 ; j < argtopic.size(); j++)
							{
								if(arg.get(i).getM_ISBN().compareTo(argtopic.get(j).getM_ISBN()) == 0)
									list.add(arg.get(i));
							}
					} //end of for (AND)
				
				else //added for OR possibility
				{
					list.addAll(arg);
					for(CBook a: argtopic)
					{
						boolean t=true;
						for(CBook b:list)
							if(a.getM_ISBN().compareTo(b.getM_ISBN()) == 0)
									t=false;
						if(t)	//if there isn't any book in list that already carries this ISBN
							list.add(a);
					}
				}	//end of or
				return list;
		}
		else
		{
			String atopic = msgMap.get("topic");
			String asubtopic =  msgMap.get("subtopic");
			msgMap.remove("topic");
			msgMap.remove("subtopic");
			boolean bool = false;
			if(msgMap.size() > 1)
			{
				bool = true;
			}
			try {
				data = this.MySQLQuery("SELECT * FROM books "+this.buildSearchBookWhere(msgMap)+";");
				while(data.next())
					arg.add(new CBook(data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6),data.getFloat(7),data.getInt(8),data.getLong(9),getBookTopics(data.getString(1)),data.getString(11),data.getString(12),data.getBoolean(13),data.getString(14),data.getInt(15)));
			
				} catch (Exception e) 
			{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
			LinkedList<CBook> argtopic=new LinkedList<CBook>();
			argtopic = searchByTopics(atopic, asubtopic);
			LinkedList<CBook> list =new LinkedList<CBook>();
			if(!bool) return argtopic;	//in case there are only topics and subtopics to search from
			//first we check if we don't need to use OR
			if(msgMap.containsKey("toggle") && msgMap.get("toggle").compareTo("true") != 0)
				for(int i = 0 ; i < arg.size(); i++)
				{
					for(int j =0 ; j < argtopic.size(); j++)
						{
							if(arg.get(i).getM_ISBN().compareTo(argtopic.get(j).getM_ISBN()) == 0)
								list.add(arg.get(i));
						}
				} //end of for (AND)
			
			else //added for OR possibility
			{
				list.addAll(arg);
				for(CBook a: argtopic)
				{
					boolean t=true;
					for(CBook b:list)
						if(a.getM_ISBN().compareTo(b.getM_ISBN()) == 0)
								t=false;
					if(t)	//if there isn't any book in list that already carries this ISBN
						list.add(a);
				}
			}	//end of or
			return list;
		}
		return arg;
	}
	
	/**
	 * Searches books in database according to their topics
	 * @param topic
	 * @return a list of books
	 */
	private LinkedList<CBook> searchByTopicOnly(String topic)
	{
		LinkedList<CBook> books = new LinkedList<CBook>();
		ResultSet res;
		try {
			res = this.MySQLQuery("CALL SearchByTopicOnly ('"+ topic +"')");
			while(res.next())
				books.add(new CBook(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getFloat(7),res.getInt(8),res.getLong(9),res.getString(10),res.getString(11),res.getString(12),res.getBoolean(13),res.getString(14),res.getInt(15)));
		
			} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return books;
	}

	/**
	 * Searches books in database according to their topics and sub topics
	 * @param topic
	 * @param subtopic
	 * @return a list of books
	 */
	private LinkedList<CBook> searchByTopics(String topic, String subtopic)
	{
		LinkedList<CBook> books = new LinkedList<CBook>();
		ResultSet res;
		try {
			res = this.MySQLQuery("CALL SearchByTopics ('"+ topic +"','"+ subtopic +"')");
			while(res.next())
				books.add(new CBook(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getFloat(7),res.getInt(8),res.getLong(9),res.getString(10),res.getString(11),res.getString(12),res.getBoolean(13),res.getString(14),res.getInt(15)));
		
			} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return books;
	}
	
	/**
	 * Searches for a review in the database
	 * @param msgMap
	 * @return a list of reviews
	 */
	public LinkedList<CBookReview> SearchReview(Map<String, String> msgMap) 
	{
		LinkedList<CBookReview> arg=new LinkedList<CBookReview>();
		ResultSet data=null;
		try {
			data = this.MySQLQuery("SELECT * FROM reviews "+this.buildSearchBookReviewWhere(msgMap)+";");
			while(data.next())
				arg.add( new CBookReview( data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5)  ,data.getInt(6),data.getString(8) ,data.getString(9),data.getString(10) ) ); 
		//      public CBookReview(       String isbn,       String author,  String title,        String review,  String writedate,         int accepted,     String Checkdate,   String authUser	,String book_name)
			} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}	
	
		return arg;
	}

	/**
	 * Builds the WHERE clause for search review
	 * @param params
	 * @return SQL syntax WHERE clause
	 */
	private String buildSearchBookReviewWhere(Map<String, String> params) {
		String ans="";
		
		if(params.isEmpty())
			return ans;
		
		Set<String> a = params.keySet();
		for(String arg: a)
			if (params.get(arg).equals(""))
				params.remove(arg);
		
		ans="WHERE ";

		if(params.containsKey("title"))
		{
			ans=ans+"title LIKE '%"+params.get("title")+"%'";
			params.remove("title");
		}
		else if(params.containsKey("author"))
		{
			ans=ans+"author LIKE '%"+params.get("author")+"%'";
			params.remove("author");
		}
		else if(params.containsKey("isbn"))
		{
			ans=ans+"isbn LIKE '"+params.get("isbn")+"'";
			params.remove("isbn");
		}
		else if(params.containsKey("review"))
		{
			ans=ans+"review LIKE '%"+params.get("review")+"%'";
			params.remove("review");
		}
		else if(params.containsKey("authority"))
		{
			ans=ans+"auth_by LIKE '%"+params.get("authority")+"%'";
			params.remove("authority");
		}
		else if(params.containsKey("bookname"))
		{
			ans=ans+"book_name LIKE '%"+params.get("bookname")+"%'";
			params.remove("bookname");
		}
				
		
		//now inserting new attributes
		if(params.containsKey("title"))
			ans=ans+" AND title LIKE '%"+params.get("title")+"%'";

		 if(params.containsKey("author"))
			ans=ans+" AND author LIKE '%"+params.get("author")+"%'";

	
		 if(params.containsKey("isbn"))
			ans=ans+" AND isbn LIKE '"+params.get("isbn")+"'";
	
	
		 if(params.containsKey("review"))
			ans=ans+" AND review LIKE '%"+params.get("review")+"%'";
	
		 if(params.containsKey("authority"))
			ans=ans+" AND auth_by LIKE '%"+params.get("authority")+"%'";
	
		 if(params.containsKey("bookname"))
				ans=ans+" AND book_name LIKE '%"+params.get("bookname")+"%'";
		
		return ans;		
	}

	/**
	 * returns the price of the specified book
	 * @param isbn
	 * @return book price
	 */
	public double getPrice(String isbn) {
		ResultSet price=null;
		try {
			price = this.MySQLQuery("CALL GetPriceByISBN ('"+ isbn +"');");
			if(price.next())
				{
					return Double.parseDouble((price.getString("price")));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}	
		return -1;
	}

	/**
	 * Bills a user with the specified subscription
	 * @param type
	 * @param userName
	 * @return true on success, false on failure
	 */
	public boolean subscriptionPay(String type, String userName) {
		ResultSet check = null;
		String ltype = null;
		int amount = 0;
		try {
			check = this.MySQLQuery("CALL CheckSubscription ('"+ userName + "');");
			if(check.next())
				{
					check.beforeFirst();
					while(check.next())
					{
						ltype = check.getString("type");
						amount = check.getInt("ammount");
						if(type.equalsIgnoreCase(ltype)) break;
					}
					if(type.equalsIgnoreCase(ltype)) // check that user has a matching type subscription
					{
						Statement st = this.m_DB_Connection.createStatement();
						// sub 1 from amount and delete if needed
						st.executeUpdate("CALL DecreaseFromSubscription('"+ userName +"',"+ (amount-1) +");");
					}
					else return false;	// if user doesn't have a subscription of "type" return false
				}
				else return false;
		} catch (Exception e) 
		{	 System.out.println("Exception in subscriptionPay (FactoryData() "+e.getMessage());	}
		return true;
	}

	/**
	 * Simulates CreditCard payment
	 * @param userName
	 * @param price
	 * @param string2
	 * @return true
	 */
	public boolean ccPay( String userName, double price, String string2) {
		return true;
	}

	/**
	 * Creates a receipt for a purchased book 
	 * @param userName
	 * @param isbn
	 * @param type
	 * @param sid
	 * @return the receipt number
	 */
	public int createReciept(String userName, String isbn, String type,int sid) {	
		ResultSet rs = null;
		try {
			rs = this.MySQLQuery("CALL CreateReciept('"+ userName +"','"+ isbn +"','"+ type+"','"+sid+"');");
			if(rs.next())
				{
					return (rs.getInt("MAX(receipt_id)"));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}		
		return -1;
	}

	/**
	 * Checks if the user has read the specified book
	 * @param isbn
	 * @param userName
	 * @return true on success, false on failure
	 */
	public boolean hasUserRead(String isbn, String userName) {
		ResultSet check = null;
		  try {
		 
			check = this.MySQLQuery( "CALL CheckReceiptByUserNameAndISBN ('"+ isbn + "','"+ userName +"');");
			if(check.next())
				{
					return true;
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}		
		return false;
	}

	/**
	 * Submits a new review to database
	 * @param isbn
	 * @param userName
	 * @param title
	 * @param review
	 * @return true on success, false on failure
	 */
	public boolean submitReview(String isbn, String userName, String title, String review) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("CALL InsertReview('"+ isbn +"','"+ userName +"','"+ title +"','"+ review +"');");
			return true;	
		} catch (SQLException e) {
			System.out.println("InsertReview():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
		return false;
	}

	/**
	 * Scores a book in database
	 * @param isbn
	 * @param userName
	 * @param score
	 * @return true on success, false on failure
	 */
	public boolean giveScore(String isbn, String userName, int score) 
	{
		ResultSet check = null;
		try {
			check = this.MySQLQuery("CALL CheckScore ('"+ isbn + "','"+ userName +"');");
			if(check.next()) //user has already scored this book
				{
				try {
					Statement st = this.m_DB_Connection.createStatement();
					st.executeUpdate("CALL UpdateScore('"+ isbn +"','"+ userName +"',"+ score +");");
					return true;	
				} catch (SQLException e) {
					System.out.println("UpdateScore():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
				}
			else 
			{
				try {
					Statement st = this.m_DB_Connection.createStatement();
					st.executeUpdate("CALL SubmitScore('"+ isbn +"','"+ userName +"',"+ score +");");
					return true;	
				} catch (SQLException e) {
					System.out.println("SubmitScore():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
			}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return false;
	}

	/**
	 * Inserts a new book into the database
	 * @param isbn
	 * @param title
	 * @param author
	 * @param release_date
	 * @param publisher
	 * @param summary
	 * @param price
	 * @param score
	 * @param score_count
	 * @param topic
	 * @param lables
	 * @param toc
	 * @param invisible
	 * @param language
	 * @return true on success, false on failure
	 */
	public boolean insertNewBook(String isbn, String title, String author, String release_date, String publisher, String summary, Double price, int score, int score_count,  String topic, String lables, String toc, boolean invisible, String language)
	{
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("CALL InsertBook('"+ isbn +"','"+ title +"','"+ author +"','"+ release_date +"','"+ publisher +"','"+ summary +"',"+ price +","+ score +","+ score_count +",'"+ null +"','"+ lables +"','"+ toc +"',"+ invisible +",'"+ language +"');");
			if(InsertTopicsAndSubTopics(topic,isbn)) return true;	
		} catch (SQLException e) {
			System.out.println("insertNewBook():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
		return false;
	}
	
	/**
	 * Updates a books details in the database
	 * @param aBook
	 * @return true on success, false on failure
	 */
	public boolean editBookDetails(CBook aBook)
	{
		try {
			Statement st = this.m_DB_Connection.createStatement();
			//generate good string for Date
			String a=aBook.getM_release_date().substring(6, 10)+"-"+aBook.getM_release_date().substring(3, 5)+"-"+aBook.getM_release_date().substring(0, 2);
			int i;		
			if(aBook.getM_invisible())
				i = st.executeUpdate("CALL ChangeBookDetails ('"+ aBook.getM_ISBN() +"','"+ aBook.getM_title() +"','"+ aBook.getM_author() +"','"+ a +"','"+ aBook.getM_publisher() +"','"+ aBook.getM_summary() +"',"+ aBook.getM_price() +","+ aBook.getM_score() +","+ aBook.getM_score_count() +",'"+ null +"','"+ aBook.getM_lables() +"','"+ aBook.getM_TOC() +"',1,'"+ aBook.getM_language() +"');");
			else
				i = st.executeUpdate("CALL ChangeBookDetails ('"+ aBook.getM_ISBN() +"','"+ aBook.getM_title() +"','"+ aBook.getM_author() +"','"+ a +"','"+ aBook.getM_publisher() +"','"+ aBook.getM_summary() +"',"+ aBook.getM_price() +","+ aBook.getM_score() +","+ aBook.getM_score_count() +",'"+ null +"','"+ aBook.getM_lables() +"','"+ aBook.getM_TOC() +"',0,'"+ aBook.getM_language() +"');");			
			if(i == 1 && editBookTopics(aBook.getM_topic(), aBook.getM_ISBN())) return true;
		} catch (SQLException e) {
			System.out.println("editBookDetails():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	return false;
	}
	
	/**
	 * Updates a book's topics and subtopics
	 * @param topics
	 * @param isbn
	 * @return true on success, false on failure
	 */
	private boolean editBookTopics(String topics, String isbn)
	{
		if(topics.compareTo(getBookTopics(isbn)) == 0) return true;
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("CALL DeleteBookTopics('"+ isbn +"')");	
		} catch (SQLException e) {
			System.out.println("editBookTopics():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
		return InsertTopicsAndSubTopics(topics, isbn);
	}

	/**
	 * Inserts topics and subtopics into database
	 * @param topic
	 * @param isbn
	 * @return true on success, false on failure
	 */
	private boolean InsertTopicsAndSubTopics(String topic, String isbn) //handle topic and subtopic insertion
	{
		if(!topic.isEmpty()) 
		{
			String[] topics = topic.split("~");
			String s = topic;
			String top = null;
			String subs = null;
			int start = 0;
			int end = 0;
			for (int i = 0; i < topics.length-1; i++)
			{
				if(s.indexOf("@") != -1)
				{
					start = s.indexOf("~");
					end = s.indexOf("@");
					top = s.substring(start+1, end);
				}
				else 
				{
					insertTopic(s.substring(1));
					return true;
				}
				insertTopic(top);
				s = s.substring(end+1);
				if(s.indexOf("~") != -1)
				{
					subs = s.substring(0, s.indexOf("~"));
				}
				else subs = s;
				int csubs = subs.split(",").length;
				String ss = null;
				for(int j = 0; j < csubs; j++)
				{
					if(subs.indexOf(",") > 0)
						ss = subs.substring(0, subs.indexOf(","));
					else ss = subs;
					insertBookTopics(isbn, top, ss);
					subs = subs.substring(subs.indexOf(",")+1);
				}
				if(s.indexOf("~") != -1)
				{
					s = s.substring(s.indexOf("~"));
				}else return true;
			}
		}else return true;
		return false;
	}
	
	/**
	 * Edits a review's details in the database
	 * @param isbn
	 * @param author
	 * @param title
	 * @param review
	 * @param accepted
	 * @param user
	 * @return true on success, false on failure
	 */
	public boolean editReview(String isbn, String author, String title, String review, int accepted, String user)
	{
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL ChangeReview ('"+ isbn +"','"+ author +"','"+ title +"','"+ review +"',"+ accepted +",'"+ user +"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("editReview():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
		return false;
	}
	
	/**
	 * Returns a user's existing subscription types in the database
	 * @param userName
	 * @return a list of subscription types
	 */
	public LinkedList<String> getUserPayments(String userName) {
		ResultSet cc;
		LinkedList<String>ans=new LinkedList<String>();
		try {
			cc = this.MySQLQuery("CALL CheckSubscription ('"+ userName +"');");
			while(cc.next())
				{
					if(cc.getString("type").equalsIgnoreCase("Monthly"))
							ans.add("Monthly");								
					else if (cc.getString("type").equalsIgnoreCase("Yearly"))
						ans.add("Yearly");
				}
			cc = null;
			cc = this.MySQLQuery("CALL CheckCC ('"+ userName +"');");
			if(cc.next())
			{
				ans.add("Credit Card");	
			}
		} catch (Exception e) 
		{	 System.out.println("getUserPayments():SQL exception: "+e.getMessage());	} 
		
		return ans;
	}

	/**
	 * Returns the existing book formats in the database
	 * @param isbn
	 * @return a list of book formats
	 */
	public LinkedList<String> getBookFormats(String isbn) {
		ResultSet formats;
		LinkedList<String>ans=new LinkedList<String>();
		try {
			formats = this.MySQLQuery("CALL GetBookFormats ('"+ isbn +"');");
			while(formats.next())
				{
					if(formats.getString("type").equalsIgnoreCase("doc"))
							ans.add("doc");								
					else if (formats.getString("type").equalsIgnoreCase("pdf"))
						ans.add("pdf");
					else if (formats.getString("type").equalsIgnoreCase("fb2"))
						ans.add("fb2");
				}
		} catch (Exception e) 
		{	 System.out.println("getBookFormats():SQL exception: "+e.getMessage());	}
		return ans ;
	}

	/**
	 * Adds statistics for this isbn and user (user viewed book at DATE)
	 * @param isbn
	 * @param userName
	 */
	public void StatisticsAddView(String isbn, String userName) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("CALL AddStatsView ('"+ isbn +"','"+ userName +"');");
		} catch (SQLException e) {
			System.out.println("StatisticsAddView():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	}

	/**
	 * Checks in the database whether the user has bought this book
	 * @param isbn
	 * @param userName
	 * @param sessionID
	 * @return true if yes false if no
	 */
	public boolean hasUserBought(String isbn, String userName, int sessionID) {
		ResultSet bought;
		try {
			bought = this.MySQLQuery("CALL CheckUserBought ('"+ isbn +"','"+ userName +"',"+ sessionID +");");
			if(bought.next())
				{
					return true;
				}
		} catch (Exception e) 
		{	 System.out.println("hasUserBought():SQL exception: "+e.getMessage());	}
		return false;
	}

	/**
	 * Retrieves a book file from the database
	 * @param isbn
	 * @param format
	 * @return Blob containing the book
	 */
	public CFile getBook(String isbn, String format) {
		CFile cf=null;
		ResultSet rs;
		try {
			rs = this.MySQLQuery("CALL GetBook ('"+ isbn +"','"+ format +"');");
			if(rs.next())
				{
					cf=new CFile(rs.getBlob("book"));
				}
		} catch (Exception e) 
		{	 System.out.println("GetBook() Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return cf;
	}

	/**
	 * Searches for a user in the database
	 * @param params
	 * @return a list of users
	 */
	public LinkedList<CUser> SearchUser(Map<String, String> params) {
		LinkedList<CUser> res = new LinkedList<CUser>();
		ResultSet data=null;
		try {
			data = this.MySQLQuery("SELECT * FROM users "+ this.buildSearchUserWhere(params) +";");
			while(data.next())
			{
				try{	res.add( new CUser(data,getUserPayments(data.getString(1))) );
				
				}catch(Exception e) { System.out.println("DBIG: Problem resolving user, giving him up on list, plz check on users table in DB");}
			}
			data.close();
		} catch (Exception e){	
			System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	
			}	
		return res;
	}
	
	/**
	 * Builds a WHERE clause for search user action
	 * @param params
	 * @return SQL syntax WHERE clause
	 */
	private String buildSearchUserWhere(Map<String, String> params) {
		String ans="";
		
		if(params.isEmpty())
			return ans;
		
		Set<String> a = params.keySet();
		for(String arg: a)
			if (params.get(arg).equals(""))
				params.remove(arg);
		
		ans="WHERE ";

		if(params.containsKey("username"))
		{
			ans=ans+"user LIKE '%"+params.get("username")+"%'";
			params.remove("username");
		}
		else if(params.containsKey("id"))
		{
			ans=ans+"ID LIKE '%"+params.get("userid")+"%'";
			params.remove("id");
		}
		else if(params.containsKey("firstname"))
		{
			ans=ans+"first_name LIKE '%"+params.get("firstname")+"%'";
			params.remove("firstname");
		}
		else if(params.containsKey("lastname"))
		{
			ans=ans+"last_name LIKE '%"+params.get("lastname")+"%'";
			params.remove("lastname");
		}
				
		
		//now inserting new attributes
		if(params.containsKey("username"))
			ans=ans+" AND user LIKE '%"+params.get("username")+"%'";

		 if(params.containsKey("id"))
			ans=ans+" AND ID LIKE '%"+params.get("id")+"%'";

	
		 if(params.containsKey("firstname"))
			ans=ans+" AND first_name LIKE '%"+params.get("firstname")+"%'";
	
	
		 if(params.containsKey("lastname"))
			ans=ans+" AND last_name LIKE '%"+params.get("lastname")+"%'";

		return ans;		
	}

	/**
	 * Updates the user's privileges in the database
	 * @param usr
	 * @param priv
	 */
	public void SetUserPriv(CUser usr, int priv) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("CALL SetUserPrivilege ('"+ usr.getM_userName() +"',"+ priv +");");
		} catch (SQLException e) {
			System.out.println("SetUserPriv():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	}

	/**
	 * Updates a user's details in the database
	 * @param usr
	 * @return true on success false on failure
	 */
	public boolean editUser(CUser usr) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL EditUser ('"+ usr.getM_userName() +"','"+ usr.getAdress() +"','"+ usr.getM_firstName() +"','"+ usr.getM_lastName() +"','"+ usr.getBirthDay() +"',"+ usr.isSuspend() +");");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("editUser():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
		return false;
	}
	
	/**
	 * Deletes  areview form the database
	 * @param isbn
	 * @param userName
	 * @return true on success false on failure
	 */
	public boolean deleteReview(String isbn, String userName) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL DeleteReview ('"+ isbn +"','"+ userName +"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("deleteReview():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
		return false;
	}

	/**
	 * Retrieves the existing languages from the database
	 * @return a set of languages
	 */
	public Set<String> getLangs() {
		ResultSet langs;
		Set<String> m_langueges = new TreeSet<String>();
		try {
			langs = this.MySQLQuery("CALL GetLanguages ();");
			while(langs.next())
				{
					if(langs.getString("language").equalsIgnoreCase("Hebrew"))
						m_langueges.add("Hebrew");								
					else if (langs.getString("language").equalsIgnoreCase("English"))
						m_langueges.add("English");
					else if (langs.getString("language").equalsIgnoreCase("Russian"))
						m_langueges.add("Russian");
				}
		} catch (Exception e) 
		{	 System.out.println("getLangs():SQL exception: "+e.getMessage());	}
		return m_langueges;
	}

	/**
	 * Retrieves the existing topics from the database
	 * @return a set of topics
	 */
	public Set<String> getTopics() {
		Set<String> m_topics = new HashSet<String>();
		ResultSet topic;
		try {
			topic = this.MySQLQuery("CALL GetTopics ();");
			while(topic.next())
				{
					m_topics.add(topic.getString("topic"));
				}
		} catch (Exception e) 
		{	 System.out.println("getTopics():SQL exception: "+e.getMessage());	}
		return m_topics;
	}

	/**
	 * Retrieves the existing subtopics for a topic from the database
	 * @param topic
	 * @return a list of subtopics
	 */
	public LinkedList<String> getSubTopics(String topic) {
		LinkedList<String> m_subTopics =new LinkedList<String>();
		ResultSet subtopic;
		try {
			subtopic = this.MySQLQuery("CALL GetSubTopicsByTopic ('"+ topic +"');");
			while(subtopic.next())
				{
					m_subTopics.add(subtopic.getString("subtopic"));
				}
		} catch (Exception e) 
		{	 System.out.println("getTopics():SQL exception: "+e.getMessage());	}
		
		return m_subTopics;
	}

	/**
	 * Retrieves the existing topic and subtopics of a book from the database
	 * @param isbn
	 * @return a string containing topics and subtopics with special delimiters "~" and "@"
	 */
	public String getBookTopics (String isbn)	{
		String res = "";
		String prevTopic = "";
		ResultSet book;
		try {
			book = this.MySQLQuery("CALL GetBookTopics ('"+ isbn +"');");
			while(book.next())
				{
					if(book.getString("topic").compareToIgnoreCase(prevTopic) == 0)
						res += ","+book.getString("subtopic");
					else
						res += "~"+book.getString("topic")+"@"+book.getString("subtopic");
					prevTopic = book.getString("topic");
				}
		} catch (Exception e) 
		{	 System.out.println("getBookTopics():SQL exception: "+e.getMessage());	}
		return res;
	}
	
	/**
	 * Inserts a topic into the database
	 * @param topic
	 * @return true on success false on failure
	 */
	public boolean insertTopic(String topic) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL InsertTopic ('"+ topic +"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("insertTopic():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		return false;
	}
	
	/**
	 * Relates a topic and a sub topic to a book in the database
	 * @param isbn
	 * @param topic
	 * @param subTopic
	 * @return true on success false on failure
	 */
	public boolean insertBookTopics(String isbn, String topic, String subTopic)
	{
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL InsertBookTopics ('"+ isbn +"','"+ topic +"','"+ subTopic+"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("insertBookTopics():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		return false;
	}
	
	/**
	 * Inserts a subtopic into the database
	 * @param topic
	 * @param subTopic
	 * @return true on success false on failure
	 */
	public boolean insertSubTopic(String topic, String subTopic) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL InsertSubTopic ('"+ topic +"','"+ subTopic+"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("insertTopic():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		return false;
	}
	
	/**
	 * Deletes a book from the database
	 * @param isbn
	 * @return true on success false on failure
	 */
	public boolean deleteBook(String isbn) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL DeleteBook ('"+ isbn +"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("deleteBook():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		return false;
	}

	/**
	 * Deletes a file of a book from the databse
	 * @param isbn
	 * @param format
	 * @return true on success false on failure
	 */
	public boolean deleteFile(String isbn, String format) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL DeleteFile ('"+ isbn +"','"+ format +"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("deleteFile():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		return false;
	}

	/**
	 * Creates an instance of AUser
	 * @param rs
	 * @return an AUser instance
	 */
	public AUser getUserInstance(ResultSet rs)
	{
		try{		
		AUser arg;
		switch (rs.getInt(3))
		{
		case (0):
			return null;
		case (1): //User level
			arg=new CReader(rs.getString(7), rs.getString(8), rs.getInt(5), rs.getString(1), -1, EActor.User);
	//		rs.close();
			return arg;
		case (2): //Reader level
			arg=new CReader(rs.getString(7), rs.getString(8), rs.getInt(5), rs.getString(1), -1, EActor.Reader);
	//		rs.close();
			return arg;
		case (3): //Librarian level
			arg=new CLibrarian(rs.getString(7), rs.getString(8), rs.getInt(5),rs.getString(1), -1 );
	//		rs.close();
			return arg;
		case (5): //Manager level
			arg=new CLibraryManager(rs.getString(7), rs.getString(8), rs.getInt(5), rs.getString(1), -1);
	//		rs.close();
			return arg;
		default:
			return null;
		}
		} catch(Exception e)
		{ 
			//TODO: add blahhhh
			System.out.println("exception during userInstanceFactory: "+e.getMessage());
		}
		
		return null;
		
	}

	/**
	 * Uploads a file to the database
	 * @param isbn
	 * @param format
	 * @param file
	 * @return true on success false on failure
	 */
	public boolean UploadFile(String isbn, String format) { 
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL InsertFile ('"+ isbn +"','"+ format +"','  ');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("UploadFile():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		return false;
	}

	/**
	 * Retrieves the amount of unhandeled reviews form the database 
	 * @return the number of relevant reviews
	 */
	public int CountMessages() {
		ResultSet ms;
		try {
			ms = this.MySQLQuery("CALL CountMessages ();");
			if(ms.next())
				{
					return ms.getInt(1);
				}
		} catch (Exception e) 
		{	 System.out.println("CountMessages():SQL exception: "+e.getMessage());	}
		return 0;
	}

	/**
	 * deletes a certain book's files from the database
	 * @param isbn
	 */
	public void clearFiles(String isbn) {
		LinkedList<String> arg=getBookFormats(isbn);
		for(String s:arg)
			this.deleteFile(isbn, s);
	}

	/**
	 * Deletes subscription for user
	 * @param m_userName
	 * @param type
	 * @throws Exception
	 */
	public void deleteSubscription(String m_userName, String type) throws Exception {
		//method deletes subscription for username, type = mothly / yearly
		//on fail THROW EXCEPTION. (counting on it in executer)
		int i = 0;
		try {
			Statement st = this.m_DB_Connection.createStatement();
			i = st.executeUpdate("CALL DeleteSubscription ('"+ m_userName +"','"+ type +"');");	
		} catch (SQLException e) {
			System.out.println("deleteSubscription():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		//throw new Exception("arg");
		if(i == 1) return;
		throw new Exception("deleteSubscription() failed!");
	}

	/**
	 * Deletes Credit card for user
	 * @param m_userName
	 * @throws Exception
	 */
	public void deleteCC(String m_userName) throws Exception{
		//method deletes Credit card for user.
		//on fail THROW EXCEPTION. (counting on it in executer)
		int i = 0;
		try {
			Statement st = this.m_DB_Connection.createStatement();
			i = st.executeUpdate("CALL DeleteCC ('"+ m_userName +"');");	
		} catch (SQLException e) {
			System.out.println("deleteCC():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		//throw new Exception("arg");
		if(i == 1) return;
		throw new Exception("deleteCC() failed!");
	}

	/**
	 * Retrieves the months and amount in every month that a book has been searched
	 * @param isbn
	 * @param from_date
	 * @param to_date
	 * @return a map of months and amounts
	 */
	public Map<String,Integer> getBookViews(String isbn, String from_date, String to_date)
	{
		Map<String,Integer> mp = new HashMap<String,Integer>();
		ResultSet rs;
		String tdate = to_date;
		String fdate = from_date;
		if(to_date == null) tdate = "2200-12-12";
		if(from_date == null) fdate = "1700-01-01";
		try {
			rs = this.MySQLQuery("CALL GetBookViewsByDate ('"+ isbn +"','"+ fdate +"','"+ tdate +"');");
			while(rs.next())
				{
				String month = rs.getString("date").substring(5, 7);
				if(mp.containsKey(month))
				{
					mp.put(month, mp.get(month)+rs.getInt("amount"));
				}
				else mp.put(month, rs.getInt("amount"));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return mp;
	}

	/**
	 * Retrieves the months and amount in every month that a book has been searched
	 * @param isbn
	 * @param year
	 * @return a map of months and amounts
	 */
	public Map<String,Integer> getBookViews(String isbn, String year)
	{
		Map<String,Integer> mp = new HashMap<String,Integer>();
		ResultSet rs;
		String fdate = year+"-01-01";
		String tdate = year+"-12-31";
		try {
			rs = this.MySQLQuery("CALL GetBookViewsByDate ('"+ isbn +"','"+ fdate +"','"+ tdate +"');");
			while(rs.next())
				{
					String month = rs.getString("date").substring(5, 7);
					if(mp.containsKey(month))
					{
						mp.put(month, mp.get(month)+rs.getInt("amount"));
					}
					else mp.put(month, rs.getInt("amount"));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return mp;
	}

	/**
	 * Retrieves the user details of every user which viewed the specified book in the specified year
	 * @param isbn
	 * @param year
	 * @return a set of user's details
	 */
	public Set<CBookStats> getFullBookViews(String isbn, String year) {
		ResultSet rs;
		Set<CBookStats> set = new HashSet<CBookStats>();
		String fdate = year+"-01-01";
		String tdate = year+"-12-31";
		try {
			rs = this.MySQLQuery("CALL GetFullBookViews ('"+ isbn +"','"+ fdate +"','"+ tdate +"');");
			while(rs.next())
				{
					set.add(new CBookStats(rs.getString("first_name")+" "+rs.getString("last_name"), rs.getString("user"), rs.getString("date").substring(5, 7)));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return set;
	}
	
	/**
	 * Retrieves dates and amounts of a book's purchases in a specified year
	 * @param isbn
	 * @param year
	 * @return a map of dates and amounts
	 */
	public Map<String, Integer> getBookSales(String isbn, String year) {
		//return short version of report (for histogram
		Map<String,Integer> mp = new HashMap<String,Integer>();
		ResultSet rs;
		String fdate = year+"-01-01";
		String tdate = year+"-12-31";
		try {
			rs = this.MySQLQuery("CALL GetBookSalesByDate ('"+ isbn +"','"+ fdate +"','"+ tdate +"');");
			while(rs.next())
			{
				String month = rs.getString("date").substring(5, 7);
				if(mp.containsKey(month))
				{
					mp.put(month, mp.get(month)+rs.getInt("1"));
				}
				else mp.put(month, rs.getInt("1"));
			}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return mp;
	}
	
	/**
	 * Retrieves the user details of every user which bought the specified book in the specified year
	 * @param isbn
	 * @param year
	 * @return a set of user's details
	 */
	public Set<CBookStats> getFullBookSales(String isbn, String year) {
		ResultSet rs;
		Set<CBookStats> set = new HashSet<CBookStats>();
		String fdate = year+"-01-01";
		String tdate = year+"-12-31";
		try {
			rs = this.MySQLQuery("CALL GetFullBookSales ('"+ isbn +"','"+ fdate +"','"+ tdate +"');");
			while(rs.next())
				{
					set.add(new CBookStats(rs.getString("first_name")+" "+rs.getString("last_name"), rs.getString("user"), rs.getString("date").substring(5, 7)));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return set;
	}

	/**
	 * Retrieves the book details of every book which was bought by the specified user in the specified year
	 * @param username
	 * @param year
	 * @return a set of book's details
	 */
	public Set<CPurchaseStats> getFullUserPurchases(String username, String year) {
		ResultSet rs;
		Set<CPurchaseStats> set = new HashSet<CPurchaseStats>();
		String fdate = year+"-01-01";
		String tdate = year+"-12-31";
		try {
			rs = this.MySQLQuery("CALL GetFullUserPurchases ('"+ username +"','"+ fdate +"','"+ tdate +"');");
			while(rs.next())
				{
					set.add(new CPurchaseStats(rs.getString("isbn"), rs.getString("title"), rs.getString("date").substring(5, 7)));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return set;
	}

	/**
	 * Returns the years in which there was activity
	 * @return a vector of years
	 */
	public Vector<String> getYears() {
		//this method returns the years in which there was activity.
		Vector<String> ans=new Vector<String>();
		ResultSet years;
		try {
			years = this.MySQLQuery("CALL GetYears ()");
			while(years.next())
				{
					ans.add(years.getString("YEAR(s.date)"));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return ans;
	}
	
	/**
	 * Returns all purchases (from all times) for this book
	 * @param isbn
	 * @return amount of purchases
	 */
	public int GetPurchases(String isbn) {
		//This function returns all purchases (from all times) for this book
		ResultSet purchases;
		try {
			purchases = this.MySQLQuery("CALL GetPurchases ('"+ isbn +"')");
			if(purchases.next())
				{
					return purchases.getInt("purchases");
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return 0;
	}
	
	/**
	 * Returns all views (from all times) for this book
	 * @param isbn
	 * @return amount of views
	 */
	public int GetViews(String isbn) {
		//This function returns all views (from all times) for this book
		ResultSet views;
		try {
			views = this.MySQLQuery("CALL GetViews ('"+ isbn +"')");
			if(views.next())
				{
					return views.getInt("views");
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return 0;
	}

	/**
	 * Sets the rank of the specified book
	 * @param isbn
	 * @param rank
	 */
	public void SetRank(String isbn,int rank) {
		// function changes rank of book
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("CALL SetRank('"+ isbn +"',"+ rank +");");	
		} catch (SQLException e) {
			System.out.println("SetRank():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	}

	/**
	 * Deletes the session id within an old receipt
	 * Function is a maintenance function
	 * @see server.core.CExecuter
	 */
	public void removeSessionId()
	{
		synchronized(this) 
			{
				try {
					Statement st = this.m_DB_Connection.createStatement();
					st.executeUpdate("CALL RemoveSessionId();");	
				} catch (SQLException e) {
					System.out.println("removeSessionId():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
			}
	}
	
}