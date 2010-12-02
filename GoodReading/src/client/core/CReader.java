package client.core;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.common.*;


public class CReader extends AUser{
	
	private static final long serialVersionUID = 1L;
	
	Map <String,String> m_forGui;
	
	public CReader(String FirstName, String LastName, int UserId, String UserName, int SessionID)
	{
		super(FirstName,LastName,UserId,UserName, EActor.Reader,SessionID);
	}
	
	
	
	
	/*
	 *  This method get the payment type from GUI and send 
	 *   update info to server
	 */
	public Object ArrangePayment(String PayType, String CreditCardNumber, String Expire, String UserID ) throws Exception
	{
		CEntry EntryToSrv = null;
		Object OResult = null;
		
		Map <String,String> fromGui = new HashMap<String,String>();
		
		
		Pattern pu = Pattern.compile("(\\p{Digit})*");
		Matcher mu = pu.matcher(CreditCardNumber);
		boolean b = mu.matches();
		mu = pu.matcher(Expire);
		b &= mu.matches();
		mu = pu.matcher(UserID);
		b &= mu.matches();
		if(!b){
			throw new IOException("Invalid Input, Use Only Digits");
		}

		fromGui.put("PayType", PayType);
		if(PayType == "CreditCard")
	    {
			fromGui.put("cc_num", CreditCardNumber);
			fromGui.put("cc_expire", Expire);
			fromGui.put("cc_id", UserID);
	    }
		else
		{
			throw new Exception("Credit Card Information doesn't needed");
		}
		EntryToSrv = new CEntry("ArrangePayment",fromGui,AUser.getInstance().getUserName(),CClientConnector.getInstance().getM_clientSessionID());
		OResult =  CClientConnector.getInstance().messageToServer(EntryToSrv);

		String SResult = CGuiTranslator.getInstance().TranslateForGui(OResult);
		return(SResult);      	          
	}
	
	public Object ArrangePayment(String PayType) throws Exception
	{
		CEntry EntryToSrv = null;
		Object OResult = null;
		
		Map <String,String> fromGui = new HashMap<String,String>();
		fromGui.put("PayType", PayType);
		if(PayType == "CreditCard")
		{
			throw new Exception("Missing CreditCard Details");
		}
		EntryToSrv = new CEntry("ArrangePayment",fromGui,AUser.getInstance().getUserName(),CClientConnector.getInstance().getM_clientSessionID());
		OResult =  CClientConnector.getInstance().messageToServer(EntryToSrv);
		
		String SResult = CGuiTranslator.getInstance().TranslateForGui(OResult);
		return(SResult);      	          
	}
	
	
	
	
}
