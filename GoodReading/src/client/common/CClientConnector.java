package client.common;

import java.io.IOException;

import ocsf.client.*;

public class CClientConnector extends AbstractClient {
		

		private static CClientConnector m_ConnectorInstance = null;
		private int m_clientSessionID;
		private boolean m_haveMsg;
		private Object m_msg;
		
		
		
		
		
		private CClientConnector(String host, int port)
		{
			super(host,port);
			this.m_clientSessionID = -1;
		}
		
		public static CClientConnector getInstance()
		{
			if(m_ConnectorInstance == null)
			{
				m_ConnectorInstance = new CClientConnector("localhost",5555);
			}
			return m_ConnectorInstance;
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
				
			} catch (IOException e) { 
				e.getCause();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 				
			// get answer		
			return (CClientConnector.getInstance().getMsg());	
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

