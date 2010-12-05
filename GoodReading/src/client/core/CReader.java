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
	public String ArrangePayment(String PayType, String CreditCardNumber, String Expire, String UserID ) throws Exception
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

		fromGui.put("type", "once");
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
		EntryToSrv = new CEntry("ArrangePayment",fromGui,AUser.getInstance().getUserName(),this.getUserSessionId());
		OResult =  CClientConnector.getInstance().messageToServer(EntryToSrv);

		String SResult = CGuiTranslator.getInstance().TranslateForGui(OResult);
		if(SResult.compareTo("null") == 0 )
		{
			throw new Exception("Update wasn't successful, please try again later");
		}
		return(SResult);      	          
	}
	
	public String ArrangePayment(String PayType) throws Exception
	{
		CEntry EntryToSrv = null;
		Object OResult = null;
		
		Map <String,String> fromGui = new HashMap<String,String>();
		if(PayType == "CreditCard")
		{
			throw new Exception("Missing CreditCard Details");
		}
		else if (PayType == "Monthly")
			fromGui.put("type", "monthly");
		else
		{
			fromGui.put("type", "yearly");
		}
		EntryToSrv = new CEntry("ArrangePayment",fromGui,AUser.getInstance().getUserName(),this.getUserSessionId());
		OResult =  CClientConnector.getInstance().messageToServer(EntryToSrv);
		
		String SResult = CGuiTranslator.getInstance().TranslateForGui(OResult);
		if(SResult.compareTo("null") == 0 )
		{
			throw new Exception("Update wasn't successful, please try again later");
		}
		return(SResult);      	          
	}
	
	
	
	
}
