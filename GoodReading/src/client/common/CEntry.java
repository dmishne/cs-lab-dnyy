package client.common;

import java.util.*;


public class CEntry {
	
	private String m_msgType;			    // Message Type
	private Map<String,String> m_msgMap;    // Message Entry
	private int m_sessionID;				// User's Session ID
	private String m_userName;       		// ????????? 	
		
	/*
	 * CEntry Constructor
	 */
	public CEntry(String msg_type, Object msg, String usrname, int sessID) 
	{
		this.m_sessionID = sessID;
		this.m_userName  = usrname;
		this.m_msgType   = msg_type;		
		@SuppressWarnings("unchecked")
		Map<String, String> temp_msg = (Map<String, String>)msg;
		this.m_msgMap    = temp_msg;
	}

	/**
	 * @return the m_msgType
	 */
	public String getM_msgType() {
		return m_msgType;
	}

	/**
	 * @return the m_msgMap
	 */
	public Map<String, String> getM_msgMap() {
		return m_msgMap;
	}

	/**
	 * @return the m_sessionID
	 */
	public int getM_sessionID() {
		return m_sessionID;
	}

	/**
	 * @return the m_userName
	 */
	public String getM_userName() {
		return m_userName;
	}
}
