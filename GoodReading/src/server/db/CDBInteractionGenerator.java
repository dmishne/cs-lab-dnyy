package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import common.data.*;


import client.core.AUser;
import client.core.CLibrarian;
import client.core.CLibraryManager;
import client.core.CReader;
import client.core.EActor;

public class CDBInteractionGenerator 
{
	
	private static CDBInteractionGenerator m_obj;
	private Connection m_DB_Connection;
	/*
	final private static String m_DEFAULTHOST="jdbc:mysql://remote-mysql4.servage.net/cslabdnyy";
	final private static String m_DEFAULTUSER="cslabdnyy";
	final private static String m_DEFAULTPASS="q1w2e3r4";
	*/
	final private int m_MONTHLY_AMMOUNT=5;
	final private int m_YEARLY_AMMOUNT=150;
	
	
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
	
	public ResultSet MySQLLoginQuery(String user)
	{
		try {
			return this.m_DB_Connection.createStatement().executeQuery("SELECT u.user,u.password FROM users u WHERE u.user like '"+user+"';");
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
			
		rs=MySQLLoginQuery(user);
		if(!rs.next())
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
	public boolean AddCC(String user,String CCnum,String CCExpire,String CCid)
	{
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("INSERT INTO credit_card_details VALUES ('"+user+"',"+CCnum+","+CCExpire+","+CCid+")");
			return true;	
		} catch (SQLException e) {
			System.out.println("AddCC():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	
		return false;
	}
	
	
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

	public boolean AddMonthly(String userName) {
		try {
			ResultSet rs=MySQLQuery("SELECT * FROM subscriptions s WHERE s.type LIKE 'monthly' AND s.user LIKE '"+userName+"';");
			if(rs.next())
				//if there is a row already in table, then update
				this.MySQLExecute("UPDATE subscriptions SET ammount="+(this.m_MONTHLY_AMMOUNT+rs.getInt(3))+" WHERE type LIKE 'monthly' AND user LIKE '"+userName+"';");
			else
				//else, we need to insert
				this.MySQLExecute("INSERT INTO subscriptions VALUES ('"+userName+"','monthly',"+this.m_MONTHLY_AMMOUNT+");");
			this.MySQLExecute("UPDATE users SET authorization=2 WHERE user LIKE '"+userName+"';");
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException during AddMonthly: "+e.getErrorCode()+" "+e.getMessage());
			return false;
		}
		return true;
	}

	public boolean AddYearly(String userName) {
		try {
			ResultSet rs=MySQLQuery("SELECT * FROM subscriptions s WHERE s.type LIKE 'yearly' AND s.user LIKE '"+userName+"';");
			if(rs.next())
				//if there is a row already in table, then update
				this.MySQLExecute("UPDATE subscriptions SET ammount="+(this.m_YEARLY_AMMOUNT+rs.getInt(3))+" WHERE type LIKE 'yearly' AND user LIKE '"+userName+"';");
			else
				//else, we need to insert
				this.MySQLExecute("INSERT INTO subscriptions VALUES ('"+userName+"','yearly',"+this.m_YEARLY_AMMOUNT+");");
			this.MySQLExecute("UPDATE users SET authorization=2 WHERE user LIKE '"+userName+"';");
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException during AddYearly: "+e.getErrorCode()+" "+e.getMessage());
			return false;
		}
		return true;
	}
	

	
	private String buildSearchBookWhere(Map<String,String> params)
	{
		String ans="";
		if(params.isEmpty())
			return ans;
		ans="WHERE ";

		Set<String> a = params.keySet();
		for(String arg: a)
			if (params.get(arg).equals(""))
				params.remove(arg);
		
		if(params.containsKey("title"))
		{
			ans=ans+"title LIKE '"+params.get("title")+"'";
			params.remove("title");
		}
		else if(params.containsKey("author"))
		{
			ans=ans+"author LIKE '"+params.get("author")+"'";
			params.remove("author");
		}
		else if(params.containsKey("lable"))
		{
			ans=ans+"lables LIKE '"+params.get("lable")+"'";
			params.remove("lable");
		}
		else if(params.containsKey("isbn"))
		{
			ans=ans+"isbn LIKE '"+params.get("isbn")+"'";
			params.remove("isbn");
		}
		else if(params.containsKey("publisher"))
		{
			ans=ans+"publisher LIKE '"+params.get("publisher")+"'";
			params.remove("publisher");
		}
		else if(params.containsKey("summary"))
		{
			ans=ans+"summary LIKE '"+params.get("summary")+"'";
			params.remove("summary");
		}
		else if(params.containsKey("topic"))
		{
			ans=ans+"topic LIKE '"+params.get("topic")+"'";
			params.remove("topic");
		}
		else if(params.containsKey("TOC"))
		{
			ans=ans+"TOC LIKE '"+params.get("TOC")+"'";
			params.remove("TOC");
		}
		else if(params.containsKey("language"))
		{
			ans=ans+"language LIKE '"+params.get("language")+"'";
			params.remove("language");
		}
		
		
		//now inserting new attributes
		if(params.containsKey("title"))
		{
			ans=ans+" AND title LIKE '"+params.get("title")+"'";
			params.remove("title");
		}
		 if(params.containsKey("author"))
		{
			ans=ans+" AND author LIKE '"+params.get("author")+"'";
			params.remove("author");
		}
		 if(params.containsKey("lable"))
		{
			ans=ans+" AND lables LIKE '"+params.get("lable")+"'";
			params.remove("lable");
		}
		 if(params.containsKey("isbn"))
		{
			ans=ans+" AND isbn LIKE '"+params.get("isbn")+"'";
			params.remove("isbn");
		}
		 if(params.containsKey("publisher"))
		{
			ans=ans+" AND publisher Like '"+params.get("publisher")+"'";
			params.remove("publisher");
		}
		 if(params.containsKey("summary"))
		{
			ans=ans+" AND summary LIKE '"+params.get("summary")+"'";
			params.remove("summary");
		}
		 if(params.containsKey("topic"))
		{
			ans=ans+" AND topic LIKE '"+params.get("topic")+"'";
			params.remove("topic");
		}
		 if(params.containsKey("TOC"))
		{
			ans=ans+" AND TOC LIKE '"+params.get("toc")+"'";
			params.remove("toc");
		}
		 if(params.containsKey("language"))
		{
			ans=ans+" AND language LIKE '"+params.get("language")+"'";
			params.remove("language");
		}
		return ans;		
	}

	
	public LinkedList<CBook> SearchBook(Map<String, String> msgMap) 
	{
		LinkedList<CBook> arg=new LinkedList<CBook>();
		ResultSet data=null;
		try {
			data = this.MySQLQuery("SELECT * FROM books "+this.buildSearchBookWhere(msgMap)+";");
			while(data.next())
				arg.add(new CBook(data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6),data.getFloat(7),data.getInt(8),data.getLong(9),data.getString(10),data.getString(11),data.getString(12),data.getBoolean(13),data.getString(14)));
		
			} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}	
	
		return arg;
	}

	public LinkedList<CBookReview> SearchReview(Map<String, String> msgMap) 
	{
		LinkedList<CBookReview> arg=new LinkedList<CBookReview>();
		ResultSet data=null;
		try {
			data = this.MySQLQuery("SELECT * FROM reviews "+this.buildSearchBookReviewWhere(msgMap)+";");
			while(data.next())
				arg.add( new CBookReview( data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5)  ,data.getInt(6),data.getString(8) ,data.getString(9) ) );
		//      public CBookReview(       String isbn,       String author,  String title,        String review,  String writedate,         int accepted,     String Checkdate,   String authUser)
			} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}	
	
		return arg;
	}

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
			ans=ans+"title LIKE '"+params.get("title")+"'";
			params.remove("title");
		}
		else if(params.containsKey("author"))
		{
			ans=ans+"author LIKE '"+params.get("author")+"'";
			params.remove("author");
		}
		else if(params.containsKey("isbn"))
		{
			ans=ans+"isbn LIKE '"+params.get("isbn")+"'";
			params.remove("isbn");
		}
		else if(params.containsKey("review"))
		{
			ans=ans+"review LIKE '"+params.get("review")+"'";
			params.remove("review");
		}
		else if(params.containsKey("authority"))
		{
			ans=ans+"auth_by LIKE '"+params.get("authority")+"'";
			params.remove("authority");
		}
				
		
		//now inserting new attributes
		if(params.containsKey("title"))
			ans=ans+" AND title LIKE '"+params.get("title")+"'";

		 if(params.containsKey("author"))
			ans=ans+" AND author LIKE '"+params.get("author")+"'";

	
		 if(params.containsKey("isbn"))
			ans=ans+" AND isbn LIKE '"+params.get("isbn")+"'";
	
	
		 if(params.containsKey("review"))
			ans=ans+" AND review LIKE '"+params.get("review")+"'";
	
		 if(params.containsKey("authority"))
			ans=ans+" AND auth_by LIKE '"+params.get("authority")+"'";
	
		
		return ans;		
	}


	public double getPrice(String isbn) {
		ResultSet price=null;
		Double p = null;
		try {
			price = this.MySQLQuery("CALL GetPriceByISBN ('"+ isbn +"');");
			if(price.next())
				{
					return p.parseDouble((price.getString("price")));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}	
		return -1;
	}

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
						if(type.equals(ltype)) break;
					}
					if(type.equals(ltype)) // check that user has a matching type subscription
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

	public boolean ccPay( String userName, double price, String string2) {
		return true;
	}

	public int createReciept(String userName, String isbn, String type) {	
		ResultSet rs = null;
		try {
			rs = this.MySQLQuery("CALL CreateReciept('"+ userName +"','"+ isbn +"','"+ type+"');");
			if(rs.next())
				{
					return (rs.getInt("reciept_ID"));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}		
		return -1;
	}

	public boolean hasUserRead(String isbn, String userName) {
		ResultSet check = null;
		try {
			check = this.MySQLQuery("CALL CheckReceiptByUserNameAndISBN ('"+ isbn + "','"+ userName +"');");
			if(check.next())
				{
					return true;
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}		
		return false;
	}

	public boolean submitReview(String isbn, String userName, String title, String review) {
		//TODO verify all values in SP here
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("CALL InsertReview('"+ isbn +"','"+ userName +"','"+ title +"','"+ review +"');");
			return true;	
		} catch (SQLException e) {
			System.out.println("InsertReview():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
		return false;
	}

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

	public boolean insertNewBook(String isbn, String title, String author, String release_date, String publisher, String summary, Double price, int score, int score_count,  String topic, String lables, String toc, boolean invisible, String language)
	{
		//TODO handle function
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("CALL InsertBook('"+ isbn +"','"+ title +"','"+ author +"','"+ release_date +"','"+ publisher +"','"+ summary +"',"+ price +","+ score +","+ score_count +",'"+ topic +"','"+ lables +"','"+ toc +"',"+ invisible +",'"+ language +"');");
			return true;	
		} catch (SQLException e) {
			System.out.println("insertNewBook():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
		return false;
	}
	
	public boolean editBookDetails(CBook aBook)
	{
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL ChangeBookDetails ('"+ aBook.getM_ISBN() +"','"+ aBook.getM_title() +"','"+ aBook.getM_author() +"','"+ aBook.getM_release_date() +"','"+ aBook.getM_publisher() +"','"+ aBook.getM_summary() +"',"+ aBook.getM_price() +","+ aBook.getM_score() +","+ aBook.getM_score_count() +",'"+ aBook.getM_topic() +"','"+ aBook.getM_lables() +"','"+ aBook.getM_TOC() +"',"+ 0 +",'"+ aBook.getM_language() +"');");
			if(i == 1) return true;
		} catch (SQLException e) {
			System.out.println("insertNewBook():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	return false;
	}

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

}