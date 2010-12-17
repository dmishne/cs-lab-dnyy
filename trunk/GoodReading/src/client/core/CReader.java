package client.core;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.api.CEntry;

import client.common.*;


public class CReader extends AUser{
	
	private static final long serialVersionUID = 1L;
	
	Map <String,String> m_forGui;
	
	public CReader(String FirstName, String LastName, int UserId, String UserName, int SessionID, EActor pri)
	{
		super(FirstName,LastName,UserId,UserName, pri,SessionID);
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
		
		
		Pattern pu = Pattern.compile("(\\p{Digit})+");
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

		String SResult = OResult.toString();
		if(SResult.compareTo("null") == 0 )
		{
			throw new Exception("Update wasn't successful, please try again later");
		}
		this.updateAccount(EActor.Reader);
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
		
		String SResult = OResult.toString();
		if(SResult.compareTo("null") == 0 )
		{
			throw new Exception("Update wasn't successful, please try again later");
		}
		return(SResult);      	          
	}
	
	public void submitScore(int score, int bookISBN)
	{
		CEntry EntryToSrv = null;
		Map <String,String> Bscore = new HashMap<String,String>();
		String isbn = Integer.toString(bookISBN);
		String sc = Integer.toString(score);
		Bscore.put("ISBN",isbn );
		Bscore.put("Score", sc);
		EntryToSrv = new CEntry("submitScore",Bscore,this.getUserName(),this.getUserSessionId());
		try {
			CClientConnector.getInstance().messageToServer(EntryToSrv);
		} catch (Exception e) {
			System.out.println("Can't send to server");
		}
	}
	
	
	public void submitReview(String reviewTitle , String review , int bookISBN) 
	{
		CEntry EntryToSrv = null;
		Map <String,String> Breview = new HashMap<String,String>();
		String isbn = Integer.toString(bookISBN);
		Breview.put("ISBN",isbn );
		Breview.put("Title",reviewTitle );
		Breview.put("Review", review);
		EntryToSrv = new CEntry("AddBookReview",Breview,this.getUserName(),this.getUserSessionId());
		try {
			CClientConnector.getInstance().messageToServer(EntryToSrv);
		} catch (Exception e) {
			System.out.println("Can't send to server");
		}
	}
	
	
	public String[] getPaymentType() throws Exception
	{
		CEntry EntryToSrv = null;
		String[] result = new String[5];
		EntryToSrv = new CEntry("getPaymentType",null,this.getUserName(),this.getUserSessionId());
		result = (String[]) CClientConnector.getInstance().messageToServer(EntryToSrv);
		return result;
	}
	
	
	public String[] getFileType(int bookISBN) throws Exception
	{
		CEntry EntryToSrv = null;
		Map <String,String> fileType = new HashMap<String,String>();
		String[] result = new String[5];
		String isbn = Integer.toString(bookISBN);
		fileType.put("ISBN",isbn );
		EntryToSrv = new CEntry("getFileType",fileType,this.getUserName(),this.getUserSessionId());
		result = (String[]) CClientConnector.getInstance().messageToServer(EntryToSrv);
		return result;
	}
	
	
	public String orderBook (int bookISBN, String PayType, String FileType) throws Exception 
	{
		CEntry EntryToSrv = null;
		Map <String,String> orderInfo = new HashMap<String,String>();
		String isbn = Integer.toString(bookISBN);
		orderInfo.put("ISBN", isbn);
		orderInfo.put("PayType", PayType);
		orderInfo.put("FileType", FileType);
		EntryToSrv = new CEntry("orderBook",orderInfo,this.getUserName(),this.getUserSessionId());
		String res = (String)CClientConnector.getInstance().messageToServer(EntryToSrv);
		return res;
	}
	
	
}
