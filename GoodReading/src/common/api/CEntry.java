package common.api;

import java.io.Serializable;
import java.util.*;

import ocsf.server.ConnectionToClient;


public class CEntry implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	/** indicates what sort of request client sends to server */
	private String m_msgType;			       
	/** the message map holds the parameters of the request, e.g. Search criteria  <NEVER null> */
	private HashMap<String,String> m_msgMap;   
	/** the Username of the client */
	private String m_userName;       		   
	/** holds the session ID*/
	private int m_sessionID;			       
	/** for use of the server, holds the connection back to client while entry's waiting in the queue */
	private Object m_client; 				   
		
	/*
	 * CEntry Constructor
	 */
	public CEntry(String msg_type, Object msg, String usrname, int sessID) 
	{
		this.m_sessionID     = sessID;
		this.m_userName      = usrname;
		this.m_msgType       = msg_type;		
		@SuppressWarnings("unchecked")
		HashMap<String, String> temp_msg = (HashMap<String, String>)msg;
		this.m_msgMap        = temp_msg;
		this.m_client	     = null;
	}


	/**
	 * @return the m_msgType
	 */
	public String getMsgType() {
		return m_msgType;
	}

	/**
	 * @return the m_msgMap
	 */
	public Map<String, String> getMsgMap() {
		return m_msgMap;
	}

	/**
	 * sets the session ID
	 * @param SessionID value to set in CEntry.m_sessionID
	 */
	public void setSessionID(int SessionID) {
		m_sessionID = SessionID;
	}

	/**
	 * @return the m_sessionID
	 */
	public int getSessionID() {
		return m_sessionID;
	}

	/**
	 * @return the m_userName
	 */
	public String getUserName() {
		return m_userName;
	}

	/**
	 * @param m_clientConnect the m_clientConnect to set
	 */
	public void setClient(ConnectionToClient m_clientConnect) {
		this.m_client = m_clientConnect;
	}

	/**
	 * @return the m_clientConnect
	 */
	public Object getClient() {
		return m_client;
	}
	
	 public String getKey()	 {
	  return Integer.toString(m_sessionID)+ "~" + m_userName;
	 }
	 
	 /**
	  * short check, if CEntry is for login, this will mean a different handling in CExecuter
	  * @return true only if CEntry is a login request
	  */
	 public boolean isLogin()
	 {
		 if ( m_msgType.compareTo("Login") == 0)
		 {
			 return true;
		 }
		 return false;
	 }

	 /**
	  * Setter for msg map
	  * @param tmp
	  */
	public void setMsgMap(Map<String, String> tmp) {
		this.m_msgMap=(HashMap<String, String>) tmp;
	}
}
