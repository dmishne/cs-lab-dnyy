package client.core;

import java.io.IOException;
import java.util.*;

import client.common.CClientConnector;
import client.gui.searchbook.CBookDetailPanel;

import common.api.CEntry;
import common.data.CPurchaseStats;
import common.data.CUser;

public class CLibraryManager extends CLibrarian{

	private static final long serialVersionUID = 1L;

	
	public CLibraryManager(String FirstName, String LastName, int UserId, String UserName, int SessionID)
	{
		super(FirstName,LastName,UserId,UserName,SessionID);
		this.updateAccount(EActor.LibraryManager);
	}
	
	
	public LinkedList<CUser> searchUser(String UserName, String UserID, String FirstName, String LastName) throws Exception
	{
		CEntry entryToSrv ;
		Map<String,String> userParam = new HashMap <String,String>();
		if(UserName.isEmpty() == false)
		{
			userParam.put("username", UserName);
		}
		if(UserID.isEmpty() == false)
		{
			userParam.put("userid", UserID);
		}
		if(FirstName.isEmpty() == false)
		{
			userParam.put("firstname", FirstName);
		}
		if(LastName.isEmpty() == false)
		{
			userParam.put("lastname", LastName);
		}
		entryToSrv = new CEntry("SearchUser",userParam, this.getUserName(),this.getUserSessionId());
		Object res = CClientConnector.getInstance().messageToServer(entryToSrv);
		@SuppressWarnings("unchecked")
		LinkedList<CUser> result = (LinkedList<CUser>)res;
		return result;		
	}
	
	
	public String updateUserDetails (String UserName, String firstName, String lastName, int userID, String birthDay, String adress, String[] payType, EActor privilage, boolean suspend) throws Exception
	{
		CEntry entryToSrv ;
		String payChain = new String();
		String answer;
		String UnnormalBirthDay = birthDay.substring(6, 10)+"-"+birthDay.substring(3, 5)+"-"+birthDay.substring(0, 2);
		Map<String,String> newUDetails = new HashMap<String,String>();
		if(!UserName.isEmpty())
		{
		    newUDetails.put("username", UserName);
		
			if(!firstName.isEmpty())
			           newUDetails.put("firstname", firstName);
			if(!lastName.isEmpty())
		               newUDetails.put("lastname", lastName);
			if(userID != 0)   
				       newUDetails.put("userid", Integer.toString(userID));
			if(!birthDay.isEmpty())
				       newUDetails.put("birthday", UnnormalBirthDay);
			if(!adress.isEmpty())
				       newUDetails.put("adress", adress);
			for(String pt : payType)
				if(pt != null)
			           payChain =  pt + "-";
			if(!payChain.isEmpty())
			        newUDetails.put("paytypes", payChain);		
			if(privilage != null)
		               newUDetails.put("privilage", privilage.toString());
			if(suspend)
				   newUDetails.put("suspend", "true");
			else if (!suspend)
				   newUDetails.put("suspend", "false");
			entryToSrv = new CEntry("EditUser",newUDetails, this.getUserName(),this.getUserSessionId());
			Object ans=CClientConnector.getInstance().messageToServer(entryToSrv);
			if( ans instanceof String )
			{
				answer = ans.toString();
				return answer;
			}
		else
			throw new IOException("Error occurred! Update fail");
	   }
		return "Fail";
	}
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String,Integer> getBookViews(String year) throws Exception
	{
		HashMap<String,Integer> answer;
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("isbn",CBookDetailPanel.getBook().getM_ISBN());
		temp.put("year",year);
		CEntry entryToSrv = new CEntry("getBookViews",temp, this.getUserName(),this.getUserSessionId());
		Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
		if( ans instanceof HashMap<?,?> )
		{
			answer = (HashMap<String,Integer>)ans;
			return answer;
		}
		else
		{
			throw new IOException("Error occurred! Update fail");
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String,Integer> getBookSales(String year) throws Exception
	{
		HashMap<String,Integer> answer;
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("isbn",CBookDetailPanel.getBook().getM_ISBN());
		temp.put("year",year);
		CEntry entryToSrv = new CEntry("getBookSales",temp, this.getUserName(),this.getUserSessionId());
		Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
		if( ans instanceof HashMap<?,?> )
		{
			answer = (HashMap<String,Integer>)ans;
			return answer;
		}
		else
		{
			throw new IOException("Error occurred! Update fail");
		}
	}

	@SuppressWarnings("unchecked")
	public Vector<CPurchaseStats> getUserPurchases(String username,String year) throws Exception
	{
		Set<CPurchaseStats> answer;
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("username",username);
		temp.put("year",year);
		CEntry entryToSrv = new CEntry("UserFullUserPurchases",temp, this.getUserName(),this.getUserSessionId());
		Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
		if( ans instanceof Set<?> )
		{
			answer = (Set<CPurchaseStats>)ans;
			Vector<CPurchaseStats> vanswer = new Vector<CPurchaseStats>();
			Iterator<CPurchaseStats> it = answer.iterator();
			while(it.hasNext())
			{
				vanswer.add(it.next());
			}
			return vanswer;
		}
		else
		{
			throw new IOException("Error occurred! Update fail");
		}
	}
	
	@SuppressWarnings("unchecked")
	public Vector<String> getYears() throws Exception
	{
		Vector<String> vanswer;	
		CEntry entryToSrv = new CEntry("GetYears",new HashMap<String,String>(), this.getUserName(),this.getUserSessionId());
		Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
		if( ans instanceof Vector<?> )
		{
			vanswer = (Vector<String>)ans;
			return vanswer;
		}
		else
		{
			throw new IOException("Error occurred! Problem with connection to server");
		}
	}
}
