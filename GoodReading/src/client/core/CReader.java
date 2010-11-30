package client.core;

import client.common.CClientConnector;
import client.common.CEntry;

public class CReader extends AUser{

	public enum EPaymentType {Monthly,Yearly}
	
	public CReader()
	{
		m_privilege = EActor.Reader;
	}

	
	// *  This method get the payment type from GUI and send 
	//    update info to server
	
	public Object ArrangePayment(EPaymentType PT)
	{
		CEntry m_CEntry = null;
		Object result = null;
	   try {
		   m_CEntry = new CEntry("ArrangePayment",PT,AUser.getInstance().m_userName,CClientConnector.getInstance().getM_clientSessionID());
		   result =  CClientConnector.getInstance().messageToServer(m_CEntry);
	   } 
	   catch (Exception e){
		   e.printStackTrace();
	   }
	   
	   // receipt
	   return(result);      	          
	   
	}
	
}
