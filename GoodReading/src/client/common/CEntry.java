package client.common;

import java.util.*;

import ocsf.server.ConnectionToClient;


public class CEntry {
	
	private String m_msgType;			        // Message Type
	private Map<String,String> m_msgMap;        // Message Entry
	private String m_userName;       		    // 
	private int m_sessionID;			        // User's Session ID
	private ConnectionToClient m_clientConnect; // ClientConnectionInfo
		
	/*
	 * CEntry Constructor
	 */
	public CEntry(String msg_type, Object msg, String usrname, int sessID) 
	{
		this.m_sessionID     = sessID;
		this.m_userName      = usrname;
		this.m_msgType       = msg_type;		
		@SuppressWarnings("unchecked")
		Map<String, String> temp_msg = (Map<String, String>)msg;
		this.m_msgMap        = temp_msg;
		this.m_clientConnect = null;
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
	public void setClientConnect(ConnectionToClient m_clientConnect) {
		this.m_clientConnect = m_clientConnect;
	}

	/**
	 * @return the m_clientConnect
	 */
	public ConnectionToClient getClientConnect() {
		return m_clientConnect;
	}
}
