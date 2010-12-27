package client.core;

import java.io.IOException;
import java.util.*;

import client.common.CClientConnector;

import common.api.CEntry;
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
		entryToSrv = new CEntry("searchuser",userParam, this.getUserName(),this.getUserSessionId());
		Object res = CClientConnector.getInstance().messageToServer(entryToSrv);
		@SuppressWarnings("unchecked")
		LinkedList<CUser> result = (LinkedList<CUser>)res;
		return result;		
	}
	
	
	public void updateUserDetails (String currentUserName, String firstName, String lastName, String userName, int userID, EActor privilage) throws Exception
	{
		CEntry entryToSrv ;
		Map<String,String> newUDetails = new HashMap<String,String>();
		if(!currentUserName.isEmpty())
		           newUDetails.put("currentusername", currentUserName);
		else
			throw new IOException("Error occurred! Update fail");
		if(!firstName.isEmpty())
		           newUDetails.put("firstname", firstName);
		if(!lastName.isEmpty())
	               newUDetails.put("lastname", lastName);
		if(!userName.isEmpty())
	               newUDetails.put("username", userName);
		if(userID != 0)   
			       newUDetails.put("userid", Integer.toString(userID));
		if(privilage != null)
	               newUDetails.put("privilage", privilage.toString());
		entryToSrv = new CEntry("updateuserdetails",newUDetails, this.getUserName(),this.getUserSessionId());
		CClientConnector.getInstance().messageToServer(entryToSrv);
	}
	
	
	
	public void updateBookStatus(String isbn, boolean status) throws Exception
	{
		CEntry entryToSrv ;
		Map<String,String> bookStatus = new HashMap<String,String>();
		if(isbn.isEmpty())
			throw new IOException("Book ISBN not located! Update fail");
		else 
		    bookStatus.put("isbn", isbn);
		if(status)
			bookStatus.put("invisible", "TRUE");
		else
			bookStatus.put("invisible", "FALSE");
		entryToSrv = new CEntry("bookstatus",bookStatus, this.getUserName(),this.getUserSessionId());
		CClientConnector.getInstance().messageToServer(entryToSrv);
	}
	
	
}