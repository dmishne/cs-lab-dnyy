package client.common;

import ocsf.client.AbstractClient;

public class CClientConnector extends AbstractClient {
		

		private static CClientConnector m_ConnectorInstance = null;
		private int m_clientSessionID;
		private boolean m_haveMsg;
		private Object m_msg;
		private static String m_host = "localhost";
				
		private CClientConnector(String host, int port)
		{
			super(host,port);
			this.m_clientSessionID = -1;
			this.m_host = host;
		}
		
		public static CClientConnector getInstance() throws Exception
		{
			if(m_ConnectorInstance == null)
			{
				m_ConnectorInstance = new CClientConnector(m_host,5555);
			}
			m_ConnectorInstance.openConnection();
			return m_ConnectorInstance;
		}

		public static void setConnectionHost(String host)
		{
			m_host = host;
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
		 * @param m_clientSessionID the m_clientSessionID to set
		 */
		public void setM_clientSessionID(int m_clientSessionID) {
			this.m_clientSessionID = m_clientSessionID;
		}


}

