package client.core;


import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import client.common.CClientConnector;
import client.common.CEntry;

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
		if(result instanceof AUser )
		{
			setActor(result);
			System.out.println("Connection was successful");
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
		case Reader:
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
	
	
	public LinkedList<CBook> searchBook(HashMap<String,String> book_param) throws Exception
	{
		CEntry EntryToSrv =null;
		HashMap<String,String> search_param = new HashMap<String,String>();
		String lang, topic;
		if(book_param.containsKey("language"))
		  {
		      lang = (String)book_param.get("language");
		      Set<String> avail_lang = CClientConnector.getInstance().getM_listOptions().getM_langueges();
		    if (!avail_lang.contains(lang))
		    {
		    	throw new Exception("Language unavailable!");
		    }
		    else 
		    	 search_param.put("language", lang);
		  }
	    if(book_param.containsKey("topics"))
		  {
		      topic = (String)book_param.get("topics");
		      Set<String> avail_topics = CClientConnector.getInstance().getM_listOptions().getM_topics();
		    if (!avail_topics.contains(topic))
		    {
		    	throw new Exception("Topic unavailable!");
		    }
		    else 
		    	 search_param.put("topics", topic);
		  }
	    search_param.put("title", (String)book_param.get("title"));
	    search_param.put("author", (String)book_param.get("author"));
	    search_param.put("summary", (String)book_param.get("summary"));
	    search_param.put("TOC", (String)book_param.get("TOC"));
	    search_param.put("labels", (String)book_param.get("labels"));
	    EntryToSrv = new CEntry("searchBook",search_param,this.m_userName,this.m_UserSessionId);
		//result = CClientConnector.getInstance().messageToServer(EntryToSrv);
	    
	    // TEMPORARY
	    LinkedList<CBook> list = new LinkedList<CBook>();
	    CBook book;
	    book = new CBook("HoferStory","Hofer","123hsg553","HaMihlala","Fuck Fuck Fuck",0.01,"Hafirot","hafer,hofer,hafira","eih lahfor",1,"kafkazit");
	    list.add(book);
	    //
		return list;
	}
		
	public EActor getPrivilege() {
		return m_privilege;
	}

	public void setPrivilege(EActor mPrivilege) {
		m_privilege = mPrivilege;
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
