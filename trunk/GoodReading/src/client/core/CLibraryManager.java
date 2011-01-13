package client.core;

import java.io.IOException;
import java.util.*;

import client.common.CClientConnector;
import client.gui.searchbook.CBookDetailPanel;

import common.api.CEntry;
import common.data.CPurchaseStats;
import common.data.CUser;

/**
 * CLibraryManager Is A CLibrarian subclass.
 * Provide the AUser instance while privilege is Library Manager.
 * Holds compatible methods for the privilege 
 */


public class CLibraryManager extends CLibrarian{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for CLibraryManager.
	 * Initialize user instance of type Library Manager 
	 */
	public CLibraryManager(String FirstName, String LastName, int UserId, String UserName, int SessionID)
	{
		super(FirstName,LastName,UserId,UserName,SessionID);
		this.updateAccount(EActor.LibraryManager);
	}
	
	/**
	 * Request server to return a list of users matching the given parameters
	 *  
	 * @param UserName   user name to search in DB
	 * @param UserID     user id to search in DB
	 * @param FirstName  users first name to search in DB
	 * @param LastName   users last name to search in DB
	 * @return  LinkedList of CUsers that matching the search pararmeters 
	 * @throws Exception  request to server fail
	 */
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
	
	/**
	 *   Request server to update users fields in DB with given parameters.
	 *   
	 * @param UserName    users user_name to identify the user (unchangeable)
	 * @param firstName   users new first name 
	 * @param lastName    users new last name
	 * @param userID      users id (unchangeable)
	 * @param birthDay    users new birth day 
	 * @param address      users new address
	 * @param payType     users new pay types
	 * @param privilege   users new privilege
	 * @param suspend     user status
	 * @return            server answer to this action
	 * @throws Exception  servers answer type is not acceptable
	 */
	public String updateUserDetails (String UserName, String firstName, String lastName, int userID, String birthDay, String address, String[] payType, EActor privilege, boolean suspend) throws Exception
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
			if(!address.isEmpty())
				       newUDetails.put("adress", address);
			for(String pt : payType)
				if(pt != null)
					   payChain = payChain + pt + ",";
			if(!payChain.isEmpty())
			        newUDetails.put("paytypes", payChain);		
			if(privilege != null)
		               newUDetails.put("privilage", privilege.toString());
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
	
	
    /**
     * Request server to return statistic information of book views for given year.
     * Views refers to number of book details shows    
     *  
     * @param year        the preffered year to search the statisic for
     * @return            HashMap with number of book views by month
     * @throws Exception  servers answer type is not acceptable
     */
	public HashMap<String,Integer> getBookViews(String year) throws Exception
	{
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("isbn",CBookDetailPanel.getBook().getM_ISBN());
		temp.put("year",year);
		CEntry entryToSrv = new CEntry("getBookViews",temp, this.getUserName(),this.getUserSessionId());
		Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
		if( ans instanceof HashMap<?,?> )
		{
			@SuppressWarnings("unchecked")
			HashMap<String,Integer> answer = (HashMap<String,Integer>)ans;
			return answer;
		}
		else
		{
			throw new IOException("Error occurred! Update fail");
		}
	}
	

	/**
     * Request server to return statistic information of book orders for given year.
     * Orders refers to number of book accepted purchases    
     *  
     * @param year        the preffered year to search the statisic for
     * @return            HashMap with number of book orders by month
     * @throws Exception  servers answer type is not acceptable
     */
	public HashMap<String,Integer> getBookSales(String year) throws Exception
	{
		
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("isbn",CBookDetailPanel.getBook().getM_ISBN());
		temp.put("year",year);
		CEntry entryToSrv = new CEntry("getBookSales",temp, this.getUserName(),this.getUserSessionId());
		Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
		if( ans instanceof HashMap<?,?> )
		{
			@SuppressWarnings("unchecked")
			HashMap<String,Integer> answer = (HashMap<String,Integer>)ans;
			return answer;
		}
		else
		{
			throw new IOException("Error occurred! Update fail");
		}
	}

	/**
     * Request server to return statistic information for user purchases for given year.
     *          
     * @param username     the user name of user to search the statistic for
     * @param year         the preffered year to search the statisic for
     * @return             vector with CPurchaseStats class contains user purchases by month for book 
     * @throws Exception   servers answer type is not acceptable
     */
	public Vector<CPurchaseStats> getUserPurchases(String username,String year) throws Exception
	{
		
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("username",username);
		temp.put("year",year);
		CEntry entryToSrv = new CEntry("UserFullUserPurchases",temp, this.getUserName(),this.getUserSessionId());
		Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
		if( ans instanceof Set<?> )
		{
			@SuppressWarnings("unchecked")
			Set<CPurchaseStats> answer = (Set<CPurchaseStats>)ans;
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
	
	/**
	 *   ????????????????
	 * @return   vector
	 * @throws Exception    request to server fail
	 */
	public Vector<String> getYears() throws Exception
	{
			
		CEntry entryToSrv = new CEntry("GetYears",new HashMap<String,String>(), this.getUserName(),this.getUserSessionId());
		Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
		if( ans instanceof Vector<?> )
		{
			@SuppressWarnings("unchecked")
			Vector<String> vanswer = (Vector<String>)ans;
			return vanswer;
		}
		else
		{
			throw new IOException("Error occurred! Problem with connection to server");
		}
	}
}
