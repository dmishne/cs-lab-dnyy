package client.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		Pattern pd = Pattern.compile("(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})");
		Matcher md = pd.matcher(Expire);
		if(!md.matches()){
			throw new IOException("Invalid Date format!");
		}		
		Pattern pu = Pattern.compile("(\\p{Digit})+");
		Matcher mu = pu.matcher(CreditCardNumber);
		boolean b = mu.matches();
		mu = pu.matcher(UserID);
		b &= mu.matches();
		if(!b){
			throw new IOException("Invalid Input, Use Only Digits");
		}
        String UnnormalExpire = Expire.substring(6, 10)+"-"+Expire.substring(3, 5)+"-"+Expire.substring(0, 2);
        if(!isValidDate(UnnormalExpire))
			 throw new IOException("No such date !");
        fromGui.put("type", "once");
		if(PayType == "CreditCard")
	    {
			fromGui.put("cc_num", CreditCardNumber);
			fromGui.put("cc_expire", UnnormalExpire);
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
	
	
	

	public String submitReview(String reviewTitle , String review , String isbn) throws Exception 
	{
		CEntry EntryToSrv = null;
		String fromServer = null;
		Map <String,String> Breview = new HashMap<String,String>();
		if(review.compareTo(" ") != 0 && !review.isEmpty())
		{
			if(reviewTitle.compareTo(" ") != 0  && !reviewTitle.isEmpty())
			{
			    Breview.put("review", review);
			    Breview.put("isbn",isbn );
				Breview.put("title",reviewTitle );
				EntryToSrv = new CEntry("AddReview",Breview,this.getUserName(),this.getUserSessionId());
				try {
					fromServer = (String) CClientConnector.getInstance().messageToServer(EntryToSrv);
				} catch (Exception e) {
					System.out.println("Can't send to server");
				}
			}
			else
				throw new Exception("No Title added!");
		}				
		else
			throw new Exception("No Review added!");
		
		return fromServer;
	}
	
	
	public String[] getPaymentType() throws Exception
	{
		CEntry EntryToSrv = null;
		String[] fail = {"No payment type"};
		Map <String,String> PayType = new HashMap<String,String>();
		EntryToSrv = new CEntry("GetPayment",PayType,this.getUserName(),this.getUserSessionId());
		String[] result = (String[])CClientConnector.getInstance().messageToServer(EntryToSrv);	
		if(result == null)
			return fail;
		return result;		
	}
	

	
	
	public String orderBook (String isbn, String PayType) throws Exception 
	{
		CEntry EntryToSrv = null;
		Map <String,String> orderInfo = new HashMap<String,String>();
		String receipt_num; 
		if(isbn.isEmpty())
			throw new IOException("Book ISBN required for order!"); 
		orderInfo.put("isbn", isbn);
		if(PayType.isEmpty())
			throw new IOException("Pay type required for order!");
		orderInfo.put("paytype", PayType);
		EntryToSrv = new CEntry("PurchaseBook",orderInfo,this.getUserName(),this.getUserSessionId());
		Object res = (String)CClientConnector.getInstance().messageToServer(EntryToSrv);
		if(res instanceof Integer)
		{
		       receipt_num = ((Integer)res).toString();
		       return receipt_num;
		}
		else if(res instanceof String)
			   return (String)res;
		return "Fail";
	}
	
	public String addScore(String isbn, int score) throws Exception
	{
		CEntry EntryToSrv;
		Map <String,String> addSc = new HashMap<String,String>();
		String sc = Integer.toString(score);
		String answer;
		if(isbn.isEmpty())
			throw new IOException("Book ISBN not located! Update fail");
		else
		{
			addSc.put("isbn", isbn);
			addSc.put("score", sc);
			EntryToSrv = new CEntry("SubmitScore",addSc,this.getUserName(),this.getUserSessionId());
		    answer = (String)CClientConnector.getInstance().messageToServer(EntryToSrv);
		   return answer;
		}
	}
	
	
	protected boolean isValidDate(String inDate) {

	    if (inDate == null)
	      return false;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	    
	    if (inDate.trim().length() != dateFormat.toPattern().length())
	      return false;
	    dateFormat.setLenient(false);	    
	    try {
	      dateFormat.parse(inDate.trim());
	    }
	    catch (ParseException pe) {
	      return false;
	    }
	    return true;
	  }
	
	
}
