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
	        m_DB_Connection=DriverManager.getConnection(CServerConstants.DEFAULTHOST(),CServerConstants.DEFAULTUSER(),CServerConstants.DEFAULTPASS());
            System.out.println("SQL connection succeed");
	 	} 
		catch (Exception ex) 
 	    {/* handle any errors*/
			System.out.println("Error initializing DB connection 5: " + ex.getMessage());
        }
		
	}

	
	
	
	public void ServerUpdateLog(String toLog) 
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
		if(CCnum.length() > 12) return false;
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("INSERT INTO credit_card_details VALUES ('"+user+"',"+CCnum+",'"+CCExpire+"',"+CCid+")");
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
	

	
	private String buildSearchBookWhere(Map<String,String> params)
	{  ///TODO: add sub catagories
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
			ans=ans+"title LIKE '%"+params.get("title")+"%'";
			params.remove("title");
		}
		else if(params.containsKey("author"))
		{
			ans=ans+"author LIKE '%"+params.get("author")+"%'";
			params.remove("author");
		}
		else if(params.containsKey("lable"))
		{
			ans=ans+"lables LIKE '%"+params.get("lable")+"%'";
			params.remove("lable");
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
		else if(params.containsKey("topic"))
		{
			ans=ans+"topic LIKE '%"+params.get("topic")+"%'";
			params.remove("topic");
		}
		else if(params.containsKey("TOC"))
		{
			ans=ans+"TOC LIKE '%"+params.get("TOC")+"%'";
			params.remove("TOC");
		}
		else if(params.containsKey("language"))
		{
			ans=ans+"language LIKE '%"+params.get("language")+"%'";
			params.remove("language");
		}
		
		
		
		
		//now inserting new attributes
		if(params.containsKey("title"))
		{
			ans=ans+" AND title LIKE '%"+params.get("title")+"%'";
		}
		 if(params.containsKey("author"))
		{
			ans=ans+" AND author LIKE '%"+params.get("author")+"%'";
		}
		 if(params.containsKey("lable"))
		{
			ans=ans+" AND lables LIKE '%"+params.get("lable")+"%'";
		}
		 if(params.containsKey("isbn"))
		{
			ans=ans+" AND isbn LIKE '"+params.get("isbn")+"'";
		}
		 if(params.containsKey("publisher"))
		{
			ans=ans+" AND publisher Like '%"+params.get("publisher")+"%'";
		}
		 if(params.containsKey("summary"))
		{
			ans=ans+" AND summary LIKE '%"+params.get("summary")+"%'";
		}
		 if(params.containsKey("topic"))
		{
			ans=ans+" AND topic LIKE '%"+params.get("topic")+"%'";
		}
		 if(params.containsKey("TOC"))
		{
			ans=ans+" AND TOC LIKE '%"+params.get("toc")+"%'";
		}
		 if(params.containsKey("language"))
		{
			ans=ans+" AND language LIKE '%"+params.get("language")+"%'";
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
				arg.add(new CBook(data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6),data.getFloat(7),data.getInt(8),data.getLong(9),data.getString(10),data.getString(11),data.getString(12),data.getBoolean(13),data.getString(14),"arg")); //TODO: replace "arg" with data.getString(15)
		
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
				arg.add( new CBookReview( data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5)  ,data.getInt(6),data.getString(8) ,data.getString(9),data.getString(10) ) ); 
		//      public CBookReview(       String isbn,       String author,  String title,        String review,  String writedate,         int accepted,     String Checkdate,   String authUser	,String book_name)
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
					return (rs.getInt("receipt_ID"));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}		
		return -1;
	}

	public boolean hasUserRead(String isbn, String userName) {
		ResultSet check = null;
	//nir's sql
	/*	try {
			check = this.MySQLQuery("SELECT * FROM receipts r WHERE r.user like '"+userName+"' AND r.isbn like '"+isbn+"';");
			if(check.next())
				{
					return true;
				}
		} catch (Exception e) 
		{	}// System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}		
	
	*/	
		
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

	public boolean submitReview(String isbn, String userName, String title, String review) {
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
			//generate good string for Date
			String a=aBook.getM_release_date().substring(6, 10)+"-"+aBook.getM_release_date().substring(3, 5)+"-"+aBook.getM_release_date().substring(0, 2);
		
			int i;		
			if(aBook.getM_invisible())
				i = st.executeUpdate("CALL ChangeBookDetails ('"+ aBook.getM_ISBN() +"','"+ aBook.getM_title() +"','"+ aBook.getM_author() +"','"+ a +"','"+ aBook.getM_publisher() +"','"+ aBook.getM_summary() +"',"+ aBook.getM_price() +","+ aBook.getM_score() +","+ aBook.getM_score_count() +",'"+ aBook.getM_topic() +"','"+ aBook.getM_lables() +"','"+ aBook.getM_TOC() +"',1,'"+ aBook.getM_language() +"');");
			else
				i = st.executeUpdate("CALL ChangeBookDetails ('"+ aBook.getM_ISBN() +"','"+ aBook.getM_title() +"','"+ aBook.getM_author() +"','"+ a +"','"+ aBook.getM_publisher() +"','"+ aBook.getM_summary() +"',"+ aBook.getM_price() +","+ aBook.getM_score() +","+ aBook.getM_score_count() +",'"+ aBook.getM_topic() +"','"+ aBook.getM_lables() +"','"+ aBook.getM_TOC() +"',0,'"+ aBook.getM_language() +"');");
			
			if(i == 1) return true;
		} catch (SQLException e) {
			System.out.println("editBookDetails():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
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
	
	public LinkedList<String> getUserPayments(String userName) {
		ResultSet cc;
		LinkedList<String>ans=new LinkedList<String>();
		//Nir's SQL
		/*try {
			cc=this.MySQLQuery("Select type FROM subscriptions WHERE user like '"+userName+"';");
	
			while(cc.next())
			{
			 ans.add(cc.getString(1));	
			}
			cc.close();
			cc=this.MySQLQuery("Select * FROM credit_card_details WHERE user like '"+userName+"';");
			if(cc.next())
				ans.add("Credit Card");
			cc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		
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

	public LinkedList<String> getBookFormats(String isbn) {
		ResultSet formats;
		LinkedList<String>ans=new LinkedList<String>();
		// Nir's sql
		/*try {
			cc=this.MySQLQuery("SELECT * FROM files WHERE isbn like '"+isbn+"';");
	
			while(cc.next())
			{
			 ans.add(cc.getString(2));	
			}
			cc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		*/
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

	public void StatisticsAddView(String isbn, String userName) {
		//adds statistics for this isbn and user (user viewed book at DATE)
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("CALL AddStatsView ('"+ isbn +"','"+ userName +"');");
		} catch (SQLException e) {
			System.out.println("StatisticsAddView():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	}

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

	public CFile getBook(String isbn, String format) {
		CFile cf=null;
		ResultSet rs;
		try {
			rs = this.MySQLQuery("CALL GetBook ('"+ isbn +"','"+ format +"');");
			if(rs.next())
				{
					cf=new CFile(rs.getBlob("book"));//.getBytes (1, (int)( rs.getBlob("book").length() ))  );
				}
		} catch (Exception e) 
		{	 System.out.println("GetBook() Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return cf;
	}

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

	public void SetUserPriv(CUser usr, int priv) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			st.executeUpdate("CALL SetUserPrivilege ('"+ usr.getM_userName() +"',"+ priv +");");
		} catch (SQLException e) {
			System.out.println("SetUserPriv():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
	}

	public boolean editUser(CUser usr) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL EditUser ('"+ usr.getM_userName() +"','"+ usr.getAdress() +"','"+ usr.getM_firstName() +"','"+ usr.getM_lastName() +"','"+ usr.getBirthDay() +"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("editUser():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
		return false;
	}
	
	public boolean deleteReview(String isbn, String userName) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL DeleteReview ('"+ isbn +"','"+ userName +"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("deleteReview():SQL exception: "+e.getErrorCode()+" "+e.getMessage());		}
		return false;
	}

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

	public Set<String> getTopics() {
		// TODO Auto-generated method stub
		Set<String> m_topics = new HashSet<String>();
		m_topics.add("Action");
		m_topics.add("Drama");
		m_topics.add("Comedy");	
		return m_topics;
	}

	public LinkedList<String> getSubTopics(String topic) {
		// TODO Auto-generated method stub
		LinkedList<String> arg=new LinkedList<String>();
		arg.add("kjafg");
		arg.add("jkhfs");
		arg.add("hdsag");
		return arg;
	}

	public boolean deleteBook(String isbn) {
		///delete book from DB
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL DeleteBook ('"+ isbn +"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("deleteBook():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		return false;
	}

	public boolean deleteFile(String isbn, String format) {
		///delete book FILE from DB
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL DeleteFile ('"+ isbn +"','"+ format +"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("deleteFile():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		return false;
	}

	public AUser getUserInstance(ResultSet rs)
	{
		try{
		//ResultSet rs;		
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

	public boolean UploadFile(String isbn, String format, CFile file) {
		try {
			Statement st = this.m_DB_Connection.createStatement();
			int i = st.executeUpdate("CALL InsertFile ('"+ isbn +"','"+ format +"','"+ new String(file.getChars()) +"');");
			if(i == 1) return true;	
		} catch (SQLException e) {
			System.out.println("UploadFile():SQL exception: "+e.getErrorCode()+" "+e.getMessage());  }
		return false;
	}

	public int CountMessages() {
		//count unhandled reviews
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

	public void clearFiles(String isbn) {
		LinkedList<String> arg=getBookFormats(isbn);
		for(String s:arg)
			this.deleteFile(isbn, s);
	}

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

	public Set<CBookStats> getFullBookViews(String isbn, String year) {
		ResultSet rs;
		Set<CBookStats> set = new HashSet<CBookStats>();
		String fdate = year+"-01-01";
		String tdate = year+"-12-31";
		try {
			rs = this.MySQLQuery("CALL GetFullBookViews ('"+ isbn +"','"+ fdate +"','"+ tdate +"');");
			while(rs.next())
				{
					set.add(new CBookStats(rs.getString("first_name")+" "+rs.getString("last_name"), rs.getString("user"), Integer.parseInt(rs.getString("date").substring(5, 7))));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return set;
	}
	
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
	
	public Set<CBookStats> getFullBookSales(String isbn, String year) {
		ResultSet rs;
		Set<CBookStats> set = new HashSet<CBookStats>();
		String fdate = year+"-01-01";
		String tdate = year+"-12-31";
		try {
			rs = this.MySQLQuery("CALL GetFullBookSales ('"+ isbn +"','"+ fdate +"','"+ tdate +"');");
			while(rs.next())
				{
					set.add(new CBookStats(rs.getString("first_name")+" "+rs.getString("last_name"), rs.getString("user"), Integer.parseInt(rs.getString("date").substring(5, 7))));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return set;
	}

	public Set<CPurchaseStats> getFullUserPurchases(String username, String year) {
		ResultSet rs;
		Set<CPurchaseStats> set = new HashSet<CPurchaseStats>();
		String fdate = year+"-01-01";
		String tdate = year+"-12-31";
		try {
			rs = this.MySQLQuery("CALL GetFullUserPurchases ('"+ username +"','"+ fdate +"','"+ tdate +"');");
			while(rs.next())
				{
					set.add(new CPurchaseStats(rs.getString("isbn"), rs.getString("title"), Integer.parseInt(rs.getString("date").substring(5, 7))));
				}
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}
		return set;
	}

}