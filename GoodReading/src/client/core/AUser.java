package client.core;


import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import common.api.CEntry;
import common.api.CListOptions;
import common.data.CBook;
import common.data.CBookReview;
import client.common.CClientConnector;
/**
 * Abstract class that holds user individual parameters. 
 * is a "super class" for privileged user classes.
 * AUser is a Singleton.
 * 
 *  @see CClientConnector
 *  
 */
public abstract class AUser implements Serializable{
	
	static private final long serialVersionUID = 1L;
	 /**   m_actor           A stored for client instance of AUser class */	
	static protected AUser m_actor = null;
	       /**   m_firstName       Actor's real first name */
		   private String  m_firstName; 
		   /**   m_lastName        Actor's real last name */
		   private String  m_lastName;
		   /**   m_userID          Actor's real ID number */
		   private int     m_userID;
		   /**   m_userName        Actor's user name for login */
		   private String  m_userName;
		   /**   m_privilege       Actor's actual privilege */
		   private EActor  m_privilege;
		   /**   m_UserSessionId   Connection session id for this user to identify in front of server */
		   private int 	   m_UserSessionId;
		   
		   
	/**	
	 * Protected constructor for AUser and subclasses of AUser.
	 * Initialize singleton instance of AUser
	 * 
	 * @param FirstName   the real first name of user
	 * @param LastName   the real last name of user
	 * @param UserId   the user's real id number 
	 * @param UserName   the nick name to use in program,need for authorization
	 * @param Privilege   the actual priority of the user
	 * @param SessionID   the connection serial number to server
	 */
    protected AUser(String FirstName, String LastName, int UserId, String UserName, EActor Privilege, int SessionID)
    {
    	FirstName = FirstName.replace("\"", "");
    	LastName  = LastName.replace("\"", "");
    	m_firstName     = Character.toUpperCase(FirstName.charAt(0)) + FirstName.substring(1, FirstName.length());
    	m_lastName      = Character.toUpperCase(LastName.charAt(0)) + LastName.substring(1, LastName.length());
    	m_userID        = UserId;
    	m_userName      = UserName;
    	m_privilege     = Privilege;
    	m_UserSessionId = SessionID;
    }
    
    
    /**
     * Building "API Unit" with request for instance of AUser class from server, 
     * if succeed, its also requesting to set up the CListOption class from server.  
     * 
     * @param username  the authorization name parameter  
     * @param password  the authorization number parameter
     * @return Enum  returns the priority of user
     * @throws Exception  username or password input invalid
     * @throws Exception  username or password incorrect
     * @throws Exception unsuccessful messageToServer pass
     */ 
	final static public EActor login(String username, String password) throws Exception
	{	
		Pattern pu = Pattern.compile("(\\p{Alpha})+((\\p{Digit})*(\\p{Alpha})*)*");
		Pattern pp = Pattern.compile("(((\\p{Digit})+(\\p{Alpha})*)+|((\\p{Digit})*(\\p{Alpha})+)+)+");
		Matcher mu = pu.matcher(username);
		Matcher mp = pp.matcher(password);
		boolean b = mu.matches();
		b &= mp.matches();
		if(!b){
			throw new IOException("Invalid Username/Password Characters");
		}
		HashMap<String,String> UP = new HashMap<String,String>();
		UP.put("user", username);
		UP.put("password", password);
		CEntry clientEntry = new CEntry("Login",UP,username,-1);
		Object result = CClientConnector.getInstance().messageToServer(clientEntry);
		//		
		if(result instanceof AUser )
		{
			setActor(result);
			System.out.println("Connection was successful");
			HashMap<String,String> listoption = new HashMap<String,String>();
			CEntry ListOption = new CEntry("GetList",listoption,username,-1);
			CListOptions ListOptions = (CListOptions) CClientConnector.getInstance().messageToServer(ListOption);
			CListOptions.CListOptionsInit(ListOptions.getm_languages(),ListOptions.getM_topics());
			return(m_actor.getPrivilege());
		}
		throw new IOException("Incorrect Username/Password");
	}
	
	
	/**
	 * this method returns the AUser instance
	 * @return  current instance of AUser 
	 * @throws Exception if no AUser instance defined 
	 */
	final static public AUser getInstance() throws Exception
	{
		if(null == m_actor)
		{
			throw new Exception("No Login");
		}
		else
		{
			return m_actor;
		}
	}
	
	
    /**
     * Drop the connection with server by sending to server the request for logout and closes the connection 
     */
	final static public void logout()
	{
		try
	    {
			HashMap<String,String> Logout = new HashMap<String,String>();
			CEntry dropconnection = new CEntry("Logout",Logout,AUser.getInstance().m_userName,AUser.getInstance().getUserSessionId());
			CClientConnector.getInstance().messageToServer(dropconnection);
			CClientConnector.getInstance().closeConnection();
			m_actor = null;
			System.out.println("Logout was successful");
	    }
	    catch(Exception e) {
	    	System.out.println("Logout wasn't successful");
	    }    
		m_actor = null;
	}
	
	
	/**
	 * Setting the type of AUser instance according to his privilege 
	 * 
	 * @param pri   the privilege of current user
	 */
	static private void setActor(Object pri)
	{
		switch(((AUser)pri).getPrivilege())
		{
		
		case Reader: case User:
			m_actor = (CReader)pri;
			break;
		case Librarian:
			m_actor = (CLibrarian)pri;
			break;
		case LibraryManager:
			m_actor = (CLibraryManager)pri;
			break;
		}
	}
	
	
	/**
	 * Uses to update the privilege of user in some cases
	 * 
	 * @param mPrivilege  the privilege to update to 
	 */
	protected void updateAccount (EActor mPrivilege)
	{
		m_privilege = mPrivilege;
	}
	
	
	/**
	 * Building "API Unit" for requesting server to search for available file formats for 
	 * specific book given by isbn number.
	 * 
	 * @param isbn  the string isbn to focus the search of formats to specific book 
	 * @return string array of formats for book
	 * @throws Exception string isbn not recived required parameter 
	 * @throws Exception unsuccessful messageToServer pass
	 */
	public String[] getFileType(String isbn) throws Exception
	{
		CEntry EntryToSrv = null;
		Map <String,String> fileType = new HashMap<String,String>();
		String[] result = new String[5];
		String[] fail = {"No file formats"};
		if(isbn.isEmpty())
			throw new IOException("Book ISBN not located! Action fail");
		else
		{
			fileType.put("isbn",isbn );
			EntryToSrv = new CEntry("GetFormats",fileType,this.getUserName(),this.getUserSessionId());
			result = (String[]) CClientConnector.getInstance().messageToServer(EntryToSrv);
		}
		if(result == null)
			 return fail;
		return result;
	}
	
	
	/**
	 * Building "API Unit" for requesting server to search for books mutching the given parameters of book
	 * to search.
	 * 
	 * @param book_param  the HashMap with parameters to focus the search of the book
	 * @return the LinkedList with Class's named CBook that holds book info
	 * @throws Exception recived language parameter is not one of available 
	 * @throws Exception recived topic parameter is not one of available
	 * @throws Exception unsuccessful messageToServer pass
	 */
	public LinkedList<CBook> searchBook(HashMap<String,String> book_param) throws Exception
	{
		CEntry EntryToSrv =null;
		HashMap<String,String> search_param = new HashMap<String,String>();
		String lang, topic = "",subtopic;
		if(book_param.get("language").compareTo(" ") != 0  &&  !book_param.get("language").isEmpty())
		{
			lang = (String)book_param.get("language");
		    Set<String> avail_lang = CClientConnector.getInstance().getM_listOptions().getm_languages();
		    if (!avail_lang.contains(lang))
		    {
		    	throw new Exception("Language unavailable!");
		    }
		    else
		    {
		    	search_param.put("language", lang);
		    }
		}
	    if(book_param.get("topic").compareTo(" ") != 0  && !book_param.get("topic").isEmpty())
		{
	    	topic = (String)book_param.get("topic");
	    	Set<String> avail_topics = CClientConnector.getInstance().getM_listOptions().getM_topics();
		    if (!avail_topics.contains(topic))
		    {
		    	throw new Exception("Topic unavailable!");
		    }
		    else
		    {
		    	search_param.put("topic", topic);
		    }
		}
	    if(book_param.get("subtopic").compareTo(" ") != 0 && !book_param.get("subtopic").isEmpty())
		{
	    	subtopic = (String)book_param.get("subtopic");
	    	String[] avail_subtopics = getSubTopics(topic);
	    	boolean have = false;
		    for(int i = 0; i< avail_subtopics.length ; i++ )
		    {
		    	if(avail_subtopics[i].compareTo(subtopic) == 0)
		    	     have = true;
		    }
	    	if (!have)
		    	throw new Exception("Subtopic unavailable!");
		    else
		    	search_param.put("subtopic", subtopic);
		    
		}
	    if(!book_param.get("title").isEmpty())
	                search_param.put("title", (String)book_param.get("title"));
	    if(!book_param.get("author").isEmpty())
	                search_param.put("author", (String)book_param.get("author"));
	    if(!book_param.get("summary").isEmpty())
	                search_param.put("summary", (String)book_param.get("summary"));
	    if(!book_param.get("toc").isEmpty())
	                search_param.put("toc", (String)book_param.get("toc"));
	    if(!book_param.get("labels").isEmpty())
	                search_param.put("labels", (String)book_param.get("labels"));
	    if(!book_param.get("toggle").isEmpty())
	    	        search_param.put("toggle", book_param.get("toggle"));
	    else
	    	        search_param.put("toggle", "false");
	    EntryToSrv = new CEntry("SearchBook",search_param,m_userName,m_UserSessionId);
		Object res =  CClientConnector.getInstance().messageToServer(EntryToSrv);
		@SuppressWarnings("unchecked")
		LinkedList<CBook> result = (LinkedList<CBook>)res;
		return result;
		
	}
	
	/**
	 * Requesting the server to get list off all available topics from DB 
	 * 
	 * @return  string array of topics
	 */
	public String[] getTopics() 
	{
		CEntry EntryToSrv =null;
		Object temp = null;
		String[] no_topics = {"No Topics"};
		HashMap<String,String> searchTopics = new HashMap<String,String>();
		EntryToSrv = new CEntry("SearchTopics",searchTopics,m_userName,m_UserSessionId);
		try {			
			temp = CClientConnector.getInstance().messageToServer(EntryToSrv);
		} catch (Exception e) {
			System.out.println("Fail to recive Topics from server !");
		}
			@SuppressWarnings("unchecked")
		    LinkedList<String> topics = (LinkedList<String>)temp;		
			if(topics.size() > 0 && temp != null)
			{
				String[] answer = new String[topics.size()+1];
				answer[0] = " ";
				Iterator<String> it = topics.iterator();
				int i = 1;
				while(it.hasNext())
				{
					answer[i] = it.next();
					i++;
				}
			    return answer;
			}
		return no_topics;
	}
	
	
	
	
	
	/**
	 * Building "API Unit" for requesting server to search for subtopics bounded to given topic.
	 * 
	 * @param topic  the string topic to focus search for bound subtopics 
	 * @return string array of found subtopics
	 */
	public String[] getSubTopics(String topic) 
	{
		CEntry EntryToSrv =null;
		Object temp = null;
		String[] no_subs = {""};
		HashMap<String,String> search_subtopics = new HashMap<String,String>();
		search_subtopics.put("topic", topic);
		EntryToSrv = new CEntry("SearchSubtopics",search_subtopics,m_userName,m_UserSessionId);
		try {			
			temp = CClientConnector.getInstance().messageToServer(EntryToSrv);
		} catch (Exception e) {
			System.out.println("Fail to recive Subtopics from server !");
		}
			@SuppressWarnings("unchecked")
		    LinkedList<String> subtopics = (LinkedList<String>)temp;		
			if(subtopics.size() > 0 && temp != null)
			{
				String[] answer = new String[subtopics.size()+1];
				answer[0] = " ";
				Iterator<String> it = subtopics.iterator();
				int i = 1;
				while(it.hasNext())
				{
					answer[i] = it.next();
					i++;
				}
			    return answer;
			}
		return no_subs;
	}
	
	
	/**
	 * Building "API Unit" for requesting server to search for reviews mutching the given parameters
	 * 
	 * @param review_param   the HashMap with parameters to focus the search of the reviews for book
	 * @return  LinkedList with Class's named CBookReview that holds review info
	 * @throws Exception  unsuccessful messageToServer pass 
	 */
	public LinkedList<CBookReview> searchBookReview(HashMap<String,String> review_param) throws Exception
	{
		CEntry EntryToSrv =null;
		HashMap<String,String> r_search_param = new HashMap<String,String>();
		if(!review_param.get("title").isEmpty())
			r_search_param.put("title", (String)review_param.get("title"));
		if(!review_param.get("author").isEmpty())
			r_search_param.put("author", (String)review_param.get("author"));
		if(!review_param.get("review").isEmpty())
			r_search_param.put("review", (String)review_param.get("review"));
		EntryToSrv = new CEntry("SearchReview",r_search_param,m_userName,m_UserSessionId);
		Object res =   CClientConnector.getInstance().messageToServer(EntryToSrv);
		@SuppressWarnings("unchecked")
		LinkedList<CBookReview> result = (LinkedList<CBookReview>)res;
		return result;
	}
	
	/**
	 * For validate the given date in front of Java date formater
	 * 
	 * @param inDate  the date to validate
	 * @return boolean true if valid or false if invalid
	 */
	protected boolean isValidDate(String inDate) {

	    if (inDate == null)
	      return false;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	    
	    if (inDate.trim().length() != dateFormat.toPattern().length())
	      return false;
	    dateFormat.setLenient(false);	    
	    try {
	      dateFormat.parse(inDate.trim());
	    }
	    catch (ParseException pe) {
	      return false;
	    }
	    return true;
	  }
	
		
	/**
	 * Return the privlege of user
	 * @return the m_privilege    the current privilege of user
	 */
	public EActor getPrivilege() {
		return m_privilege;
	}
	
	/**
	 * Set the first name of user
	 * @param arg   the string determines first name of user
	 */
	public void setFirstName(String arg) {
		m_firstName = arg;
	}
	
	/**
	 * Set the last name of user
	 * @param arg   the string determines last name of user
	 */
	public void setLastName(String arg) {
		m_lastName = arg;
	}
	
	/**
	 * Set the id number of user
	 * @param arg   the int determines the ID number of user
	 */
	public void setID(int arg) {
		m_userID = arg;
	}
	
	/**
	 * Get the first name of user
	 * @return the m_firstName   the first name of user
	 */
	public String getFirstName() {
		return m_firstName;
	}

	/**
	 * Get the last name of user
	 * @return the m_lastName    the last name of user
	 */
	public String getLastName() {
		return m_lastName;
	}

	/**
	 * Get the id number of user
	 * @return the m_userID   the user id of user
	 */
	public int getUserID() {
		return m_userID;
	}

	/**
	 * Get the user name of this user
	 * @return the m_userName   the user name of user
	 */
	public String getUserName() {
		return m_userName;
	}

	/**
	 * Get the the session id of the connection instance of this user
	 * @return the m_UserSessionId   the session id of this instance in front of server
	 */
	public int getUserSessionId() {
		return m_UserSessionId;
	}

	

}
