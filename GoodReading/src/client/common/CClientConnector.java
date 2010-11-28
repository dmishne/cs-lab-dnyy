package client.common;

import ocsf.client.*;

public class CClientConnector extends AbstractClient {
		

		private static CClientConnector m_ConnectorInstance;
		private String m_clientSessionID;
		private boolean m_haveMsg;
		private Object m_msg;
		
		private CClientConnector(String host, int port)
		{
			super(host,port);
			this.m_clientSessionID = null;
		}
		
		public static CClientConnector getInstance()
		{
			if(m_ConnectorInstance == null)
			{
				m_ConnectorInstance = new CClientConnector("localhost",5555);
			}
			return m_ConnectorInstance;
		}

		protected void handleMessageFromServer(Object message)
		{
			     // if message exist, the new message will overwrite it
			     this.m_msg = message;
			     this.m_haveMsg = true;
			     notifyAll();
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

		public String getClientSessionID() {
			return m_clientSessionID;
		}

}

