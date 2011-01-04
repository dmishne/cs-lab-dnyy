package client.core;


import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import common.api.CEntry;
import common.api.CListOptions;
import common.data.CBook;
import common.data.CBookReview;
import client.common.CClientConnector;


public abstract class AUser implements Serializable{
	
	static private final long serialVersionUID = 1L;
	static protected AUser m_actor = null;
		   private String  m_firstName;
		   private String  m_lastName;
		   private int     m_userID;
		   private String  m_userName;
		   private EActor  m_privilege;
		   private int 	   m_UserSessionId;
		   
		   
		

    public AUser(String FirstName, String LastName, int UserId, String UserName, EActor Privilege, int SessionID)
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
    
	final static public EActor login(String username, String password) throws Exception
	{	
		 /*
		  *  Username && Password input validation 
		 */
		
		Pattern pu = Pattern.compile("(\\p{Alpha})+((\\p{Digit})*(\\p{Alpha})*)*");
		Pattern pp = Pattern.compile("(((\\p{Digit})+(\\p{Alpha})*)+|((\\p{Digit})*(\\p{Alpha})+)+)+");
		Matcher mu = pu.matcher(username);
		Matcher mp = pp.matcher(password);
		boolean b = mu.matches();
		b &= mp.matches();
		if(!b){
			throw new IOException("Invalid Username/Password Characters");
		}
		
		/*
		 * Creating Entry to send to server
		 */
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
			CListOptions.CListOptionsInit(ListOptions.getM_langueges(),ListOptions.getM_topics());
			return(m_actor.getPrivilege());
		}
		throw new IOException("Incorrect Username/Password");
	}
	
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
	
	final static public void logout()
	{
		// -Stub-
		m_actor = null;
		try
	    {
			CClientConnector.getInstance().closeConnection();
	    }
	    catch(Exception e) {
	    	System.out.println("logout wasn't successful");
	    }    
		// ------
	}
	
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
	
	
	
	protected void updateAccount (EActor mPrivilege)
	{
		m_privilege = mPrivilege;
	}
	
	
	@SuppressWarnings("unchecked")
	public LinkedList<CBook> searchBook(HashMap<String,String> book_param) throws Exception
	{
		CEntry EntryToSrv =null;
		HashMap<String,String> search_param = new HashMap<String,String>();
		String lang, topic;
		if(book_param.get("language").compareTo(" ") != 0)
		{
			lang = (String)book_param.get("language");
		    Set<String> avail_lang = CClientConnector.getInstance().getM_listOptions().getM_langueges();
		    if (!avail_lang.contains(lang))
		    {
		    	throw new Exception("Language unavailable!");
		    }
		    else
		    {
		    	search_param.put("language", lang);
		    }
		}
	    if(book_param.get("topic").compareTo(" ") != 0)
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
	    if(!book_param.get("title").isEmpty())
	                search_param.put("title", (String)book_param.get("title"));
	    if(!book_param.get("author").isEmpty())
	                search_param.put("author", (String)book_param.get("author"));
	    if(!book_param.get("summary").isEmpty())
	                search_param.put("summary", (String)book_param.get("summary"));
	    if(!book_param.get("TOC").isEmpty())
	                search_param.put("TOC", (String)book_param.get("TOC"));
	    if(!book_param.get("labels").isEmpty())
	                search_param.put("labels", (String)book_param.get("labels"));
	    EntryToSrv = new CEntry("SearchBook",search_param,m_userName,m_UserSessionId);
		LinkedList<CBook> result = (LinkedList<CBook>) CClientConnector.getInstance().messageToServer(EntryToSrv);
	   
		return result;
		
	}
	
	public String[] getSubTopics(String topic) throws Exception
	{
		//CEntry EntryToSrv =null;
		HashMap<String,String> search_subtopics = new HashMap<String,String>();
		search_subtopics.put("topic", topic);
		//EntryToSrv = new CEntry("SearchSubtopics",search_subtopics,m_userName,m_UserSessionId);
		//String[] subtopics = (String[])CClientConnector.getInstance().messageToServer(EntryToSrv);
		String[] subtopics = {"2010","2009","2008"};
		return subtopics;
	}
	
	
	
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
		
	
	public EActor getPrivilege() {
		return m_privilege;
	}

	public void setPrivilege(EActor arg) {
		m_privilege = arg;
	}
	public void setFirstName(String arg) {
		m_firstName = arg;
	}	
	public void setLastName(String arg) {
		m_lastName = arg;
	}
	public void setID(int arg) {
		m_userID = arg;
	}
	
	 /**
	 * @return the m_firstName
	 */
	public String getFirstName() {
		return m_firstName;
	}

	/**
	 * @return the m_lastName
	 */
	public String getLastName() {
		return m_lastName;
	}

	/**
	 * @return the m_userID
	 */
	public int getUserID() {
		return m_userID;
	}

	/**
	 * @return the m_userName
	 */
	public String getUserName() {
		return m_userName;
	}

	/**
	 * @return the m_UserSessionId
	 */
	public int getUserSessionId() {
		return m_UserSessionId;
	}

	

}
