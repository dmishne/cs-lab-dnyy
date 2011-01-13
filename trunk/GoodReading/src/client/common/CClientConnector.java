package client.common;

import java.util.*;

import common.api.CListOptions;

import ocsf.client.AbstractClient;

/**
 * CClientConnector holds the client's application connection information
 * CClientConnector is a Singleton
 */
public class CClientConnector extends AbstractClient {
		
		/**
		 * Instance of class
		 */
		private static CClientConnector m_ConnectorInstance = null;
		/**
		 * Clients session ID
		 * Users to identify in front of server
		 */
		private int m_clientSessionID;
		/**
		 * Indicates whether there is a message waiting
		 */
		private boolean m_haveMsg;
		/**
		 * Store the last received message
		 */
		private Object m_msg;
		/**
		 * Host of Server
		 */
		private static String m_host = "localhost";
		/**
		 * Indicates whether Server host has been changes
		 */
		private static boolean m_change;
		/**
		 * Save list of available book topic and languages in database
		 */
		private CListOptions m_listOptions;
		
		/**
		 * Hidden Constructor,
		 * Initialize singleton instance, receiving and saving Books's topics list.   
		 * @param host Server's address xxx.xxx.xxx.xxx
		 * @param port Server's port xxxx
		 */
		private CClientConnector(String host, int port)
		{
			super(host,port);
			this.m_clientSessionID = -1;
			m_change = false;
			this. m_listOptions = CListOptions.getInstance(); 
		}
		
		/**
		 * getInstance return an instance of CClientConnect and open connection to server.
		 * @return An instance of CClientConnector.
		 * @throws Exception When can't connect to server.
		 */
		public static CClientConnector getInstance() throws Exception
		{
			if(m_ConnectorInstance == null || m_change == true)
			{
				m_ConnectorInstance = new CClientConnector(m_host,5555);
				m_change = false;
			}
			m_ConnectorInstance.openConnection();
			return m_ConnectorInstance;
		}

		/**
		 * setConnectionHost sets the server's address.
		 * @param host Server's address xxx.xxx.xxx.xxx
		 */
		public static void setConnectionHost(String host)
		{
			m_host = host;
			m_change = true;
		}
		
		/**
		 * handleMessageFromServer overload a method from AbstractClient.
		 * handleMessageFromServer handle the message that received from the server
		 * by storing the message and informs that the message has received.
		 * @param message message from server.
		 */
		final protected void handleMessageFromServer(Object message)
		{
			     // if message exist, the new message will overwrite it
			     this.m_msg = message;
			     this.m_haveMsg = true;
			     synchronized(this)
			     {
			    	 this.notify();
			     }
		}
		
		/**
		 * messageToServer sends message to server and return the message received from server.
		 * messageToServer will wait till it get response message from server. 
		 * @param message Message that should be passed to the server.
		 * @return The response from to the server to the message to the client.
		 */
		public Object messageToServer(Object message)
		{
		   //send to server
			try {
				sendToServer(message);
				while(!isWaitingMsg())
				{
					synchronized(this)
					{
						this.wait();
					}
				}
			//get answer	
			return (CClientConnector.getInstance().getMsg());		
			} 
			catch (InterruptedException e) { 
				e.printStackTrace();
			} catch (Exception e) {
				e.getCause();
			} 				
			return (null);	
		}
		
		
		
		public boolean isWaitingMsg()
		{
			return(m_haveMsg);	
		}
		
		/**
		 * @return The Message
		 */
		public Object getMsg() {
			m_haveMsg = false;
			return m_msg;
		}
				
		/**
		 * @return The client session ID
		 */
		public int getM_clientSessionID() {
			return m_clientSessionID;
		}

		/**
		 * @return The listOptions
		 */
		public CListOptions getM_listOptions() {
			return m_listOptions;
		}
		
		/**
		 * setM_clientSessionID setting client session ID
		 * @param m_clientSessionID client session ID
		 */
		public void setM_clientSessionID(int m_clientSessionID) {
			this.m_clientSessionID = m_clientSessionID;
		}

		/**
		 * getLangages returns Available book's languages in server 
		 * @return Available book's languages in server 
		 */
		public String[] getLangages()
		{
			String[] lang = new String[ (m_listOptions.getm_languages()).size() + 1];
			lang[0]= " ";
			int i =1;
			Iterator<String> it = m_listOptions.getm_languages().iterator();
			while(it.hasNext())
			{
				lang[i] = it.next();
				i++;
			}
			
			return lang;
		}
		
		/**
		 * getTopics returns Available book's topics in server 
		 * @return Available book's topics in server
		 */
		public String[] getTopics()
		{
			String[] topics = new String[ m_listOptions.getM_topics().size() + 1];
			topics[0]=" ";
			int i =1;
			Iterator<String> it = m_listOptions.getM_topics().iterator();
			while(it.hasNext())
			{
				topics[i] = it.next();
				i++;
			}
			return topics;
		}
		
}