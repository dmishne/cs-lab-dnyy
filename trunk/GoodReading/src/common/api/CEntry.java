package common.api;

import java.io.Serializable;
import java.util.*;

import ocsf.server.ConnectionToClient;


public class CEntry implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String m_msgType;			        // Message Type
	private HashMap<String,String> m_msgMap;    // Message Entry
	private String m_userName;       		    // 
	private int m_sessionID;			        // User's Session ID
	private Object m_client; 				    // Client Info
		
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

	public void setSessionID(int mSessionID) {
		m_sessionID = mSessionID;
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
	 
	 public boolean isLogin()
	 {
		 if ( m_msgType.compareTo("Login") == 0)
		 {
			 return true;
		 }
		 return false;
	 }
}
