package client.core;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import common.api.CEntry;
import common.data.CFile;
import client.common.*;

/**
 * CReader Is A AUser subclass.
 * Provide the AUser instance while privilege is Reader.
 * Holds compatible methods for the privilege 
 */
public class CReader extends AUser{
	
	private static final long serialVersionUID = 1L;
	
	//Map <String,String> m_forGui;
	/**
	 * Constructor for CReader.
	 * Initialize user instance of type Reader 
	 */
	public CReader(String FirstName, String LastName, int UserId, String UserName, int SessionID, EActor pri)
	{
		super(FirstName,LastName,UserId,UserName, pri,SessionID);
	}
		
	
	/**
	 * Building "API Unit" for server with relevant Credit Card payment parameters to arrange new Credit Card payment for user.
	 * 
	 * @param PayType  the string PayType determines the type pg choosen payment
	 * @param CreditCardNumber   the string CreditCardNumber is a number of users credit card to use for orders 
	 * @param Expire   the string Expire is a date of credit card expire date
	 * @param UserID   the string UserID is ID number bound to this specific credit card
	 * @return the string answer from server if action was succesful or not
	 * @throws Exception the recived date isn't have the correct form of dd-mm-yyyy ( - any sign) 
	 * @throws Exception the credit card number or ID number contains not only digits
	 * @throws Exception the recived pay type is not credit card while this method activated with other parameters  
	 * @throws Exception the server answer to this action was NULL (server not succseed)
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
	
	
	/**
	 * Building "API Unit" for server with relevant subscription type to arrange new payment subscription for user.
	 * 
	 * @param PayType  the string PayType determines type of payment user purchase
	 * @return  the string answer from server if action was succesful or not
	 * @throws Exception this method is not for credit card payment arrangement
	 * @throws Exception the server answer to this action was NULL (server not succseed)
	 */
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
	
	
	
 /**
  * Requesting from server to store new user review in DB
  * 
  * @param reviewTitle  the string reviewTitle is a reviewer title for this review  
  * @param review  the string review is a review it self
  * @param isbn  the string isbn is a isbn number of book the review is about
  * @return the string answer to this action from server
  * @throws Exception  the title for review was empty
  * @throws Exception  the review was empty
  */
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
	
	
	/**
	 * Requesting server to make a list of afordable payment types for this user,
	 * based on user_name taken from the instance class of user.
	 * 
	 * @return  string array with payment types of current user
	 * @throws Exception unsuccessful messageToServer pass 
	 */
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
	

	
	/**
	 * Requesting server to build receipt for the order while provided with relevant information 
	 * 
	 * @param isbn  the string isbn of book to order
	 * @param PayType  the string payment type user choosed to pay with
	 * @return string answer from server if fails or int number of receipt if success 
	 * @throws Exception not recived one of parameters (isbn or pay type) required for order 
	 * @throws Exception unsuccessful messageToServer pass
	 */
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
		Object res = (Integer)CClientConnector.getInstance().messageToServer(EntryToSrv);
		if(res instanceof Integer)
		{
		       receipt_num = String.valueOf(((Integer)res).intValue());
		       return receipt_num;
		}
		else if(res instanceof String)
			   return (String)res;
		return "Fail";
	}
	
	
	/**
	 * Adding score to book through request from server 
	 * 
	 * @param isbn  the string isbn to focus on specific book
	 * @param score  the int score is a value of score to add 
	 * @return the string answer from server about successnes of action
	 * @throws Exception not recived book isbn or score is not positive value
	 * @throws Exception unsuccessful messageToServer pass
	 */
	public String addScore(String isbn, int score) throws Exception
	{
		CEntry EntryToSrv;
		Map <String,String> addSc = new HashMap<String,String>();
		if(score < 0)
			throw new IOException("The score must be positive !");
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
	
	
	/**
	 * Providing server with request and parameters to recive preferd ordered file
	 * 
	 * @param isbn      the ordered book isbn
	 * @param format    the preffered file format of the file
	 * @param path      the path to the directory where to save the file
	 * @throws Exception  server unable to get the file
	 */
	public void downloadBook(String isbn, String format, String path) throws Exception
	{
		CEntry EntryToSrv;
		Map <String,String> info = new HashMap<String,String>();
		info.put("isbn",isbn);
		info.put("format",format);
		EntryToSrv = new CEntry("DownloadBook",info,this.getUserName(),this.getUserSessionId());
		Object answer = CClientConnector.getInstance().messageToServer(EntryToSrv);
		if(answer instanceof CFile)
		{
			CFile custFile  = (CFile)answer;
			String newPath = path.replace("\\" , "/");
			custFile.saveFile(newPath);
		}
		else
		{
			throw new Exception((String)answer);
		}
	}

}
