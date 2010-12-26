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
	
	
	

	public void submitReview(String reviewTitle , String review , String isbn) throws Exception 
	{
		CEntry EntryToSrv = null;
		Map <String,String> Breview = new HashMap<String,String>();
		if(review.compareTo(" ") != 0 && !review.isEmpty())
		{
			if(reviewTitle.compareTo(" ") != 0  && !reviewTitle.isEmpty())
			{
			    Breview.put("Review", review);
			    Breview.put("ISBN",isbn );
				Breview.put("Title",reviewTitle );
				EntryToSrv = new CEntry("AddBookReview",Breview,this.getUserName(),this.getUserSessionId());
				try {
					CClientConnector.getInstance().messageToServer(EntryToSrv);
				} catch (Exception e) {
					System.out.println("Can't send to server");
				}
			}
			else
				throw new Exception("No Title added!");
		}				
		else
			throw new Exception("No Review added!");
		
	}
	
	
	public String[] getPaymentType() throws Exception
	{
		CEntry EntryToSrv = null;
		String[] result = new String[5];
		EntryToSrv = new CEntry("getPaymentType",null,this.getUserName(),this.getUserSessionId());
		result = (String[]) CClientConnector.getInstance().messageToServer(EntryToSrv);
		return result;
	}
	
	
	public String[] getFileType(String isbn) throws Exception
	{
		CEntry EntryToSrv = null;
		Map <String,String> fileType = new HashMap<String,String>();
		String[] result = new String[5];
		if(isbn.isEmpty())
			throw new IOException("Book ISBN not located! Action fail");
		else
		{
			fileType.put("ISBN",isbn );
			EntryToSrv = new CEntry("getFileType",fileType,this.getUserName(),this.getUserSessionId());
			result = (String[]) CClientConnector.getInstance().messageToServer(EntryToSrv);
		}
		return result;
	}
	
	
	public String orderBook (String isbn, String PayType) throws Exception 
	{
		CEntry EntryToSrv = null;
		Map <String,String> orderInfo = new HashMap<String,String>();
		if(isbn.isEmpty())
			throw new IOException("Book ISBN required for order!"); 
		orderInfo.put("ISBN", isbn);
		if(PayType.isEmpty())
			throw new IOException("Pay type required for order!");
		orderInfo.put("PayType", PayType);
		EntryToSrv = new CEntry("orderBook",orderInfo,this.getUserName(),this.getUserSessionId());
		String res = (String)CClientConnector.getInstance().messageToServer(EntryToSrv);
		return res;
	}
	
	public void addScore(String isbn, int score) throws Exception
	{
		CEntry EntryToSrv;
		Map <String,String> addSc = new HashMap<String,String>();
		String sc = Integer.toString(score);
		if(isbn.isEmpty())
			throw new IOException("Book ISBN not located! Update fail");
		else
		{
			addSc.put("isbn", isbn);
			addSc.put("score", sc);
			EntryToSrv = new CEntry("addscore",addSc,this.getUserName(),this.getUserSessionId());
		    CClientConnector.getInstance().messageToServer(EntryToSrv);
		}
	}
	
	
}
