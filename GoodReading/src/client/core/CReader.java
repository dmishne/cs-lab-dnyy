package client.core;

import java.util.*;
import client.common.*;


public class CReader extends AUser{
	
	private static final long serialVersionUID = 1L;
	
	Map <String,String> m_forGui;
	
	public CReader()
	{
		m_privilege = EActor.Reader;
	}
	
	
	
	/*
	 *  This method get the payment type from GUI and send 
	 *   update info to server
	 */
	public Object ArrangePayment(String PayType, String CreditCardNumber, String Expire, String UserID )
	{
		CEntry m_CEntry = null;
		Object OResult = null;
		
		Map <String,String> m_fromGui = new HashMap<String,String>();
	/**///---------------------------------------------------------------------*/
	/*/    Translate GUI parameters to CEntry for server
	/**///----------------------------------------------
	/**/	m_fromGui.put("PayType", PayType);
	/**/	if(PayType == "CreditCard")
	/**/    {
	/**/		     m_fromGui.put("cc_num", CreditCardNumber);
	/**/		     m_fromGui.put("cc_expire", Expire);
	/**/			 m_fromGui.put("cc_id", UserID);
	/**/	}
	/**/	
	/**/   try {
	/**/	   m_CEntry = new CEntry("ArrangePayment",m_fromGui,AUser.getInstance().m_userName,CClientConnector.getInstance().getM_clientSessionID());
	/**/	   OResult =  CClientConnector.getInstance().messageToServer(m_CEntry);
	/**/   } 
	/**/   catch (Exception e){
	/**/	   e.printStackTrace();
	/**/   }
	/*///----------------------------------------------------------------------*/
	   String SResult = CGuiTranslator.getInstance().TranslateForGui(OResult);
	   return(SResult);      	          
	   
	}
	
}
