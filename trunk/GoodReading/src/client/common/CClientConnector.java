package client.common;

import java.util.*;

import common.api.CListOptions;

import ocsf.client.AbstractClient;

public class CClientConnector extends AbstractClient {
		

		private static CClientConnector m_ConnectorInstance = null;
		private int m_clientSessionID;
		private boolean m_haveMsg;
		private Object m_msg;
		private static String m_host = "localhost";
		private static boolean m_change;
		private CListOptions m_listOptions;
				
		private CClientConnector(String host, int port)
		{
			super(host,port);
			this.m_clientSessionID = -1;
			m_change = false;
			this. m_listOptions = CListOptions.getInstance(); 
		}
		
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

		public static void setConnectionHost(String host)
		{
			m_host = host;
			m_change = true;
		}
		
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
				// TODO Auto-generated catch block
				e.getCause();
			} 				
			return (null);	
		}
		
		
		
		public boolean isWaitingMsg()
		{
			return(m_haveMsg);	
		}
		
		
		/**
		 * @return the m_msg
		 */
		public Object getMsg() {
			m_haveMsg = false;
			return m_msg;
		}
		
		
		/**
		 * @return the m_clientSessionID
		 */
		public int getM_clientSessionID() {
			return m_clientSessionID;
		}

		
		/**
		 * @return the m_listOptions
		 */
		public CListOptions getM_listOptions() {
			return m_listOptions;
		}
		
		
		/**
		 * @param m_clientSessionID the m_clientSessionID to set
		 */
		public void setM_clientSessionID(int m_clientSessionID) {
			this.m_clientSessionID = m_clientSessionID;
		}

		
		public String[] getLangages()
		{
			String[] lang = new String[ m_listOptions.getM_langueges().size() + 1];
			lang[0]= " ";
			int i =1;
			Iterator<String> it = m_listOptions.getM_langueges().iterator();
			while(it.hasNext())
			{
				lang[i] = it.next();
				i++;
			}
			
			return lang;
		}
		
		
		

		public String[] getTopics()
		{
			//String[] topics = new String[ m_listOptions.getM_topics().size() + 1];
			//topics[0]=" ";
			/*int i =1;
			Iterator<String> it = m_listOptions.getM_topics().iterator();
			while(it.hasNext())
			{
				topics[i] = it.next();
				i++;
			}*/	
			String[] topics = {" ","Food","Internet","History"};
			return topics;
		}


}

