package server.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import ocsf.server.ConnectionToClient;
import server.db.CDBInteractionGenerator;
import client.core.AUser;

import common.api.CEntry;
import common.api.CListOptions;
import common.data.CBook;
import common.data.CBookReview;
import common.data.CBookStats;
import common.data.CFile;
import common.data.CPurchaseStats;
import common.data.CUser;

/*                     
 *	
 *	CExecuter is the glue behind Server, basically it's the Class holding everything together.
 *
 *	It's responsibility is basically authenticating a client's request (CEntry) and working to return the client an answer.	
 *	                          
 *	@see CStandbyUnit
 *
 *	@see CClientSession
 *	
 *	@see CDBInteractionGenerator 
 *
 *	@see CRespondToClient
 *
 */

public class CExecuter implements Runnable
{ 
	private Set <CClientSession> m_sessions;	// @member m_sessions holds all active sessions
	private boolean m_sleeping;					// @member m_sleeping indicates Executer is sleeping (waiting for jobs to do
	private static boolean m_running=false;		// @member m_running holds the MaintenanceRunner boolean to show it if to continue running or pause
	private static MaintenanceRunner m_MaintenanceRunner;	// @member m_MaintenanceRunner holds the MaintenanceRunner thread
	private Thread m_ThreadHolder;				// @member m_ThreadHolder holds the thread on which the executer is running
	private Random m_generator;					// @member m_generator is actually infrastracture helping to generate random numbers.
	private static CExecuter m_obj;				// @member m_obj is a part of the implementation for the Singleton Design patern
	
	/*  
	 * GetInstance finishes the implementation for the Singleton
	 * @returns the only instance of CExecuter
	 */
	public static CExecuter GetInstance()
	{
		if(m_obj == null)
			init();
		return m_obj;
	}
	
	private CExecuter()
	{
	    //Instance is configured inside init()
		this.m_sessions=new HashSet<CClientSession>();
	}
	
	/*
	 * Init() creates and initializes the instance of CExecuter
	 */
	private static void init()
	{
		m_obj=new CExecuter();
		m_obj.m_sleeping=true;
		m_obj.m_generator = new Random( 19580427 );
		m_obj.m_ThreadHolder=new Thread(m_obj);
		m_obj.m_ThreadHolder.start();
	}
	
	
	/*
	 * run() implements the runnable interface.
	 * This is the function that runs non-stop while server is up.
	 * Started in init()
	 * @see java.lang.Runnable#run()
	 * @see server.core.CExecuter#init()
	 */
	public void run()
	{
		CEntry Work=new CEntry("", new HashMap<String,String>(), "", -1);				 //from now on this will be the referance to the CEntry we're working on.
		
		try {
			while (true)									//run forever
			{
				Work.setSessionID(-1);
				if(CStandbyUnit.GetInstance().isEmpty())
				{
					m_sleeping = true;
				}
				synchronized(m_obj)							//take hold of CExecuter
				{
					while(m_sleeping)						//wait if there's no work.
						wait();
				}
				
				Work=CStandbyUnit.GetInstance().getEntryFromQueue();
				
				/*handle entry from standby unit*/
				if(Work.isLogin())
				{
					
					//make sure nobody's using the selected key
					do {
						Work.setSessionID(Random());
					} while( CRespondToClient.GetInstance().isRegistered(Work.getSessionID()) );
					
					//insert to Connections
					CRespondToClient.GetInstance().InsertOutstream(Work.getSessionID(), Work.getClient());
			
					//handle Login
					handleLogin(Work);			
					
				}//end of login handling
				
				
				else if(Work.getMsgType().compareTo("GetList") == 0)
				{
					Set<String> lang=CDBInteractionGenerator.GetInstance().getLangs();
					Set<String> topics=CDBInteractionGenerator.GetInstance().getTopics();

					try {
						((ConnectionToClient)Work.getClient()).sendToClient(CListOptions.CListOptionsInit(lang, topics));
					} catch (IOException e) {
						System.out.println("CExecuter: Error returning msg during GetList: "+e.getMessage());
						CDBInteractionGenerator.GetInstance().ServerUpdateLog("CExecuter: Error returning msg during GetList: "+e.getMessage());
					
					}
				}//end CListOptions
				
				
				
				else 
				{
					if(Work.getMsgMap() == null)
						Work.setMsgMap(new HashMap<String,String>());//@SuppressWarnings 'unchecked'
					
					//first off we lowercase everything in msgmap
					Map<String,String> tmp=new HashMap<String,String>(Work.getMsgMap());
					String v;
					for(String k:Work.getMsgMap().keySet())
					{
						v=tmp.get(k);
						if(v != null)
							v=v.replace("\\", "/").replace("\'", "'").replace("'", "\\'");
						tmp.remove(k);
						tmp.put(k.toLowerCase(), v);
					}
					Work.setMsgMap(tmp);
					//end of lower casing
					
					//start
					int Privilage=-1;
					
					for(CClientSession s : this.m_sessions) //find session
							if(s.getSessionID() == Work.getSessionID()) //sessionID match
								if(Work.getUserName().compareTo(s.getUsername())==0) //username match
								{
									Privilage=s.getUserAuth();
									break;
								}
								else { 
									break;
								}
					if(Privilage<0)
					{
						//if user is not logged or is disabled
						try {
							((ConnectionToClient)Work.getClient()).sendToClient("User is not logged or is disabled");
						}	catch (Exception e) {	
							System.out.println("Server fail, can't 'send' response to client");
							CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server fail, can't 'send' response to client");
						}
					}
					else
					{
						if(Privilage >-1)
							{	// update responder to contain NEW connection
								CRespondToClient.GetInstance().Remove(Work.getSessionID());
								CRespondToClient.GetInstance().InsertOutstream(Work.getSessionID(),Work.getClient());
								Work.setClient(null);
							}
						
						//getting an instance as "db" will make things easier for this part
						CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
						//executing entry, currently only little to do
						
						
						if(Work.getMsgType().compareTo("ArrangePayment") == 0)
						{	
							ArrangePayment(Work,Privilage);
						}// end of ArrangePayment
						
						
						else if(Work.getMsgType().compareTo("Logout") == 0)
						{
							LogOut(Work,Privilage);
						}//end of Logout
						
						
						else if(Work.getMsgType().compareTo("SearchSubtopics") == 0)
						{
							LinkedList<String> rez=db.getSubTopics(Work.getMsgMap().get("topic"));
							CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), rez);
						}//end of SearchSubtopics
						
						else if(Work.getMsgType().compareTo("AddTopic") == 0)
						{	
							if(Privilage >2)
								if(!Work.getMsgMap().containsKey("topic"))
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "No topic to add!");
								else if(!db.insertTopic(Work.getMsgMap().get("topic")))
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Failed to add topic "+Work.getMsgMap().get("topic"));
								else 
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Added topic!");
							else 
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "not enough privilage!");
							
						}// end of AddTopic

						else if(Work.getMsgType().compareTo("SearchBook") == 0)
						{
							SearchBook(Work,Privilage);
						} //end of Searchbook


						else if(Work.getMsgType().compareTo("SearchTopics") == 0)
						{
							
							Set<String> ans=db.getTopics();
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), new LinkedList<String>(ans));
							
						} //end of SearchTopics

						else if(Work.getMsgType().compareTo("GetYears") == 0)
						{
							if(Privilage > 3)
							{
								Vector<String> ans=db.getYears();
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), ans);
							}
						} //end of Searchbook
					

						else if(Work.getMsgType().compareTo("DownloadBook") == 0)
						{
							DownloadBook(Work,Privilage);
						} //end of DownloadBook
						
						
						else if(Work.getMsgType().compareTo("SearchReview") == 0)
						{
							SearchReview(Work,Privilage);	
						} //end of Searchreview
						else if(Work.getMsgType().compareTo("GetUnhandledReviews") == 0)
						{

							GetUnhandledReviews(Work,Privilage);
						} //end of GetUnhandledReviews
						else if(Work.getMsgType().compareTo("AddReview") == 0)
						{

							AddReview(Work,Privilage);
						} //end of AddReview
						else if(Work.getMsgType().compareTo("DeleteReview") == 0)
						{

							DeleteReview(Work,Privilage);
						} //end of DeleteReview
						else if(Work.getMsgType().compareTo("DeleteBook") == 0)
						{
							
							DeleteBook(Work,Privilage);
						} //end of DeleteBook
						
						else if(Work.getMsgType().compareTo("DeleteFile") == 0)
						{	
						
							DeleteFile(Work,Privilage);
						} //end of DeleteFile
						
						else if(Work.getMsgType().compareTo("EditReview") == 0)
						{	
							EditReview(Work,Privilage);
						} //end of EditReview
						else if(Work.getMsgType().compareTo("PurchaseBook") == 0)
						{
							PurchaseBook(Work,Privilage);
						} //end of PurchaseBook
						
						
						else if(Work.getMsgType().compareTo("SubmitScore") == 0)
						{
							
							SubmitScore(Work,Privilage);
						} //end of SubmitScore	
						
						else if(Work.getMsgType().compareTo("AddBook") == 0)
						{	
							AddNewBook(Work,Privilage);						
						} //end of add book
						
						else if(Work.getMsgType().compareTo("AddSubtopic") == 0)
						{	
							if(Privilage >2)
								if(db.insertSubTopic(Work.getMsgMap().get("topic"), Work.getMsgMap().get("subtopic")))
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Success!");
								else 
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Fail!");
							else 
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "not enough privilage!");
					
						} //end of add subtopic
						
						else if(Work.getMsgType().compareTo("EditBook") == 0)
						{
							EditBook(Work,Privilage);
						} //end of edit book
				
						else if(Work.getMsgType().compareTo("GetPayment") == 0)
						{
							LinkedList <String> ans= db.getUserPayments(Work.getUserName()) ;
							String[] a = {"No affordable pay types"};
							if(ans.isEmpty())
							      CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),a);
							else
							      CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),ans.toArray(new String[ans.size()]));
						}//end of GetPayment
						
						else if(Work.getMsgType().compareTo("CountMessages") == 0)
						{
							if(Privilage <3)
							      CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),new Integer(0));
							else
								{
							      CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),new Integer(db.CountMessages()));
								}
						}//end of CountMessages
						
						
						else if(Work.getMsgType().compareTo("GetFormats") == 0)
						{
							String[] a = {"No affordable file formats"};
							LinkedList <String> ans = db.getBookFormats(Work.getMsgMap().get("isbn"));
							if(Privilage <3)
								db.StatisticsAddView(Work.getMsgMap().get("isbn"),Work.getUserName());
							if(ans == null)
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),a);
							else
							    CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),ans.toArray(new String[ans.size()]));
						}//end of GetFormats

						
						else if(Work.getMsgType().compareTo("getBookSales") == 0)
						{
							if( Privilage >3 ) 
							{
								Map<String,Integer> rez=ChangeMonthsNames((HashMap<String, Integer>) db.getBookSales(Work.getMsgMap().get("isbn"),Work.getMsgMap().get("year")));
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),rez);
							}
							else
							    CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"User must have privilage!");
						
						}//end of getBookSales HISTOGRAM
						
						
						else if(Work.getMsgType().compareTo("getBookViews") == 0)
						{
							if( Privilage >3 ) 
							{
								Map<String,Integer> rez=ChangeMonthsNames((HashMap<String, Integer>)db.getBookViews(Work.getMsgMap().get("isbn"),Work.getMsgMap().get("year")));
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),rez);
							}
							else
							    CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"User must have privilage!");
						
						}//end of getBookViews HISTOGRAM
						
						
						else if(Work.getMsgType().compareTo("UserFullUserPurchases") == 0)
						{
							if( Privilage >3 ) 
							{
								Set<CPurchaseStats> rez=db.getFullUserPurchases(Work.getMsgMap().get("username"),Work.getMsgMap().get("year"));
								
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),this.ChangeLongMonthsNames(rez));
							}
							else
							    CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"User must have privilage!");
						
						}//end of UserFullUserPurchases
						
						else if(Work.getMsgType().compareTo("FullBookViews") == 0)
						{
							if( Privilage >3 ) 
							{
								Set<CBookStats> rez=db.getFullBookViews(Work.getMsgMap().get("isbn"),Work.getMsgMap().get("year"));
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),this.ChangeLongMonthNames( rez ));
							}
							else
							    CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"User must have privilage!");
						}//end of BookViews
						else if(Work.getMsgType().compareTo("FullBookSales") == 0)
						{
							if( Privilage >3 ) 
							{
								Set<CBookStats> rez=db.getFullBookSales(Work.getMsgMap().get("isbn"),Work.getMsgMap().get("year"));
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),this.ChangeLongMonthNames(rez));
							}
							else
							    CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"User must have privilage!");
						}//end of FullBookSales
						
						
											
						else if(Work.getMsgType().compareTo("SearchUser") == 0)
						{ 
							SearchUser(Work,Privilage);
						}//end of SearchUser
						
						else if(Work.getMsgType().compareTo("EditUser") == 0)
						{
							EditUser(Work,Privilage);
						}//end of EditUser
						
						
						else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Don't know this func");
						
					
					} //end of handling Entry
				}					
			}//end of while(forever)
		}//try 
		catch (InterruptedException e) 
		{
			System.out.println("Server fail, can't 'wait' in func run via CExecuter");
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server fail, can't wait in func run via CExecuter");
		}
	}

	//
	private void SearchUser(CEntry Work, int Privilage) 
	{
		LinkedList<CUser> ans;
		Map<String,String> arg=Work.getMsgMap();
		for(String a: arg.keySet())
			if (arg.get(a).compareTo("")==0)
				arg.remove(a);
		if(Privilage >3)
		{
			ans=CDBInteractionGenerator.GetInstance().SearchUser(arg);
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),ans);
		}
		else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"no privilage!");
	}

	
	//
	private void EditUser(CEntry Work, int Privilage) 
	{
		CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
		CUser usr=null;
		Map<String,String> arg=Work.getMsgMap();
		if(!arg.containsKey("username"))
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"no username specified!");
			else 
			{
				Map<String,String> n=new HashMap<String,String>();
				
				n.put("username", arg.get("username"));	//make sure we have the right user

				for(CUser u:db.SearchUser(n))
					if(arg.get("username").compareTo(u.getM_userName()) == 0)
						usr=u;
	
				//set attributes of usr
				arg.remove("username");
				if(!arg.keySet().isEmpty())
				for(String a:arg.keySet())
				{
					if(a.compareTo("lastname")==0)
						usr.setM_lastName(arg.get(a));
					else if(a.compareTo("firstname")==0)
						usr.setM_firstName(arg.get(a));
					else if(a.compareTo("adress")==0)
						usr.setAdress(arg.get(a));
					else if(a.compareTo("birthday")==0)
						usr.setBirthDay(arg.get(a));
		}
				//take care of suspend
				if(arg.containsKey("suspend") && Boolean.parseBoolean(arg.get("suspend")))
					usr.SetSuspend(true);
				else usr.SetSuspend(false);

				if(db.editUser(usr) )
					{
					if( arg.containsKey("privilage"))
						{
						String temp=arg.get("privilage");
							int p = 0;
							if(temp.compareTo("LibraryManager") == 0)
							{
								p = 5;
							}else if(temp.compareTo("Librarian") == 0)
							{
								p = 3;
							}else if(temp.compareTo("User") == 0)
							{
								p = 1;											
							}else if(temp.compareTo("Reader") == 0)
							{
								p = 2;										
							}
							db.SetUserPriv(usr,p);
						}
					
					boolean paytypes=ChangePayments(usr,arg.get("paytypes"));
					
					if(paytypes)
						CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"success");
					else
						CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"user updated, failed to change PaymentTypes");
					}
				else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"fail");
				
			}
	}

	//Wrik map should contain isbn and 1 more attr of CBook
	private void EditBook(CEntry Work, int Privilage) 
	{
		CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();

		Map<String,String> arg= Work.getMsgMap();
		if(  !arg.containsKey("isbn"))
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must specify ISBN");
		else {
			//create new map with only isbn
			Map<String,String> getb= new HashMap<String,String>();
			getb.put("isbn", Work.getMsgMap().get("isbn"));
			
			if (Privilage <3)
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must have sufficient privilage to edit a book");
			else if(db.SearchBook(getb).isEmpty()) 
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: book does not exist!");
			else 
			{try {
				
				LinkedList<CBook> lama=db.SearchBook(getb);
				CBook a=null;
				
				for(CBook b:lama)
					if( b.getM_ISBN().compareTo(Work.getMsgMap().get("isbn")) == 0)
					 a=b;
				
				//(String m_ISBN, String m_author, String m_title, String m_release, String m_publisher,				 String m_summary,double m_price, long m_score_count,double m_score,String m_topic, String m_lables, String m_TOC,boolean m_invisible, String m_language)
				//we got book by isbn, now we need to edit data:
				if(arg.containsKey("title"))
					a.settitle(arg.get("title"));
				if(arg.containsKey("release"))
					a.setrelease_date(arg.get("release"));
				 if(arg.containsKey("author"))
					a.setauthor(arg.get("author"));
				 if(arg.containsKey("lables"))
					a.setlables(arg.get("lables"));
				 if(arg.containsKey("publisher"))
					a.setpublisher(arg.get("publisher"));
				 if(arg.containsKey("summary"))
					a.setsummary(arg.get("summary"));
				 if(arg.containsKey("topic"))
					 a.settopic(arg.get("topic"));
				 if(arg.containsKey("toc"))
					a.setTOC(arg.get("toc"));
				 if(arg.containsKey("languages"))
					a.setlanguage(arg.get("languages"));
				 if(arg.containsKey("price"))
					a.setprice(Double.parseDouble(arg.get("price")));
				 if(arg.containsKey("invisible") && Privilage >3)
					a.setinvisible( Boolean.parseBoolean(arg.get("invisible")) );
			
					
				if (!db.editBookDetails(a))
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"edit book: FAIL");
				else {
					//first we clear all files of this book
					db.clearFiles(arg.get("isbn"));
					int count=0;
					//now we check if we have new files to add
					if(arg.containsKey("format"))
					{
						if(arg.containsKey("format") && arg.get("format") != null)
						{
						for(String s: arg.get("format").split(","))
							if( s!= null && s.compareTo("") != 0 )
									count++;
							
						if(arg.get("format") != null && arg.get("format").compareTo("") != 0)
						for(String s: arg.get("format").split(","))
							if( db.UploadFile(arg.get("isbn"),s,new CFile(CServerConstants.DEFAULT_Global_Library_Path()+arg.get("isbn")+"."+s)) )
								count--;
						}
					if(count == 0)
						CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Edit new book: SUCCESS");
					else 
						CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Edit book: OK, problems uploading files");
					} 	
				}
				}
			catch(NullPointerException e)
				{
					System.out.println("Can't find book");	
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Edit book: FAIL, couldn't find book");
				
				}
			}
		}	
	}	//end of edit book

	private void AddNewBook(CEntry Work, int Privilage) 
	{
		CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();

		if (Privilage <3 )
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must have sufficient privilage to add new book");
		
		else 
		{
			Map<String,String> arg= Work.getMsgMap();
			
			Map<String,String> getb= new HashMap<String,String>();
			getb.put("isbn", Work.getMsgMap().get("isbn"));
			
			if(! db.SearchBook(getb).isEmpty() ) // if book already exists
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "added new book: FAIL - book already exists");
			
			//									insertNewBook(String isbn, 		 title, 		 author, 			 release_date, String	 publisher, String 		summary, Double price, int score, int score_count, 			 String topic,		 String lables, String toc, boolean invisible, String language)
			else if (Work.getMsgMap().size()<12 || !db.insertNewBook(arg.get("isbn"),  arg.get("title"), arg.get("author")  , arg.get("release"), arg.get("publisher"), arg.get("summary"), Double.parseDouble(arg.get("price")), (int)0, (int)0, arg.get("topic"), arg.get("lables"), arg.get("toc"), Boolean.parseBoolean(arg.get("invisible")), arg.get("languages")))
				{		
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "added new book: FAIL");
				}
			else 
			{  //add files
				int count=0;
				//now we check if we have new files to add
				if(arg.containsKey("format"))
				{
					for(String a: arg.get("format").split(","))
						if( a!= null && a.compareTo("") != 0 )
							count++;
					
					for(String a: arg.get("format").split(","))
					{
						CFile asd=new CFile(CServerConstants.DEFAULT_Global_Library_Path()+arg.get("isbn")+"."+a);
						
						if( a!= null && a.compareTo("") != 0 && db.UploadFile(arg.get("isbn"),a,asd) )
							count--;
					}
				}
				
				if(count == 0)
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"added new book: SUCCESS");
				else 
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"added new book: OK, problems uploading files");
				 
			}//end of add files
		} //end of privilage
	}//end of add book

	
	private void SubmitScore(CEntry Work, int Privilage) 
	{
		CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
		
		Map<String,String> arg= Work.getMsgMap();
		
		if(Privilage <3 )
			if(!db.hasUserRead(arg.get("isbn"),Work.getUserName()))
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must read book before submitting a score");
			
			
			else//we're here because client has read the book
			{
				if(db.giveScore(arg.get("isbn"),Work.getUserName(),Integer.parseInt(arg.get("score"))))
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "success");
				else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Failed to comply");
			}
		
		
		else //take care of unprivileged ppl
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Not authorized to use function \"SubmitScore()\"");		
			
	
		
	}

	private void PurchaseBook(CEntry Work, int Privilage) 
	{
		CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
		if(Privilage <3)
		{//here we made sure user is actually a reader and not a librarian / library manager
			Map<String,String> arg= Work.getMsgMap();
			//update account according to payment method
			if(arg.get("paytype").compareTo("Monthly")==0)
				if (db.subscriptionPay("Monthly",Work.getUserName()))
					{ 
					int rID=db.createReciept(Work.getUserName(),arg.get("isbn"),"Monthly",Work.getSessionID());//generate reciept
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),new Integer(rID));//response to client
					}
				else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "failed operation");
			
			else if(arg.get("paytype").compareTo("Yearly")==0)
				if (db.subscriptionPay("Yearly",Work.getUserName()))
				{ 
					int rID=db.createReciept(Work.getUserName(),arg.get("isbn"),"Yearly",Work.getSessionID());//generate reciept
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),new Integer(rID));//response to client
				}
				else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "failed operation");
			
			else if(arg.get("paytype").compareTo("Credit Card")==0)
				if (db.ccPay(Work.getUserName(),db.getPrice(arg.get("isbn")),arg.get("isbn")))
				{ 
					int rID=db.createReciept(Work.getUserName(),arg.get("isbn"),"Credit Card",Work.getSessionID());//generate reciept
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), new Integer(rID));//response to client
				}
				else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "failed operation");

		} //end of privilege
		else //take care of unprivileged ppl
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Not authorized to use function \"PurchaseBook()\"");		
		
	}

	private void EditReview(CEntry Work, int Privilage) 
	{

		if(Privilage < 3)
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: inappropriate user privilage");
		else
		{
			Map<String,String> arg=Work.getMsgMap();
			
			int i=arg.get("confirm").compareTo("true");
			if( i ==0 ) //confirm = true
				i=1;
			else
				i=0;
			if(CDBInteractionGenerator.GetInstance().editReview(arg.get("isbn"),arg.get("author"),arg.get("title"),arg.get("review"), i  ,Work.getUserName()))
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Review Eddited.");

			else 
			{
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: failed to submit");
				CDBInteractionGenerator.GetInstance().ServerUpdateLog("Fail: failed to submit");  System.out.println("failure at executer: editting review");
			}
		}

		
	}

	private void DeleteFile(CEntry Work, int Privilage) 
	{
		//this function is not implemented in GUI, but it is usefull in order to delete book files in DB and it's par func on DBIG is actually used a few times.
		Map<String,String> arg=Work.getMsgMap();
		if(Privilage <3)
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must have proper privilage");
		else
		{//we're here because client has privilage
			if(CDBInteractionGenerator.GetInstance().deleteFile(arg.get("isbn"),arg.get("format")))
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "File Deleted");
			else 
			{
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: failed to delete");
				CDBInteractionGenerator.GetInstance().ServerUpdateLog("Failure at executer: deleting File\n");  System.out.println("failure at executer: delete File");
			}
		}//end of auth
		
	}

	private void DeleteBook(CEntry Work, int Privilage) {

		Map<String,String> arg=Work.getMsgMap();
		if(Privilage <3)
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must have proper privilage");
		else
		{//we're here because client has privilage
			if(CDBInteractionGenerator.GetInstance().deleteBook(arg.get("isbn")))
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Book Deleted");
			else 
			{
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: failed to delete");
				CDBInteractionGenerator.GetInstance().ServerUpdateLog("Failure at executer: deleting Book\n");  System.out.println("failure at executer: delete Book");
			}
		}//end of auth
		
	}

	private void DeleteReview(CEntry Work, int Privilage) 
	{
		
		Map<String,String> arg=Work.getMsgMap();
		if(Privilage <3)
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must have proper privilage");
		else
		{//we're here because client has privilage
			if(CDBInteractionGenerator.GetInstance().deleteReview(arg.get("isbn"),arg.get("author")))
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Review Deleted");
			else 
			{
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: failed to delete");
				CDBInteractionGenerator.GetInstance().ServerUpdateLog("Failure at executer: deleting review\n");  System.out.println("failure at executer: delete review");
			}
		}//end of auth
	}

	private void AddReview(CEntry Work, int Privilage) 
	{
		CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
		
		Map<String,String> arg=Work.getMsgMap();
		if(!db.hasUserRead(arg.get("isbn"),Work.getUserName()))
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must read book before submitting a review");
		else
		{//we're here because client has read the book
			if(db.submitReview(arg.get("isbn"),Work.getUserName(),arg.get("title"),arg.get("review")))
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Review Submitted");
			else 
			{
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: failed to submit");
				CDBInteractionGenerator.GetInstance().ServerUpdateLog("Failure at executer: adding review\n");  System.out.println("failure at executer: adding review");
			}
		}//end of reading auth
	}

	private void GetUnhandledReviews(CEntry Work, int Privilage) 
	{
		if(Privilage <3)
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "User not authorized");
		else {
			//get set
			LinkedList<CBookReview> rez=CDBInteractionGenerator.GetInstance().SearchReview(Work.getMsgMap());
			LinkedList<CBookReview> temp=new LinkedList<CBookReview>(rez);
			
			//remove handled reviews
			for(CBookReview rev: temp)
				if(rev.getaccepted() ==1)
					rez.remove(rev);
			
			//reply to client
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), rez);
		}
		
	}

	private void SearchReview(CEntry Work, int Privilage) 
	{


		//get set
		LinkedList<CBookReview> rez=CDBInteractionGenerator.GetInstance().SearchReview(Work.getMsgMap());
		
		//if not a librarian, remove unapproved reviews
		if(Privilage < 3 && !rez.isEmpty())
		{
			Iterator<CBookReview> a=(new LinkedList<CBookReview>(rez)).iterator();
			while(a.hasNext())
			{
				CBookReview rev=(CBookReview) a.next();
				if(rev.getaccepted() <1)
					rez.remove(rev);
			}
		}
		//reply to client
		CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), rez);
		
	}

	private void DownloadBook(CEntry Work, int Privilage) 
	{
		CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
		
		//if not a librarian, remove unapproved reviews
		if(Privilage > 3)
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"user is not a reader!");
		
		else if( !Work.getMsgMap().containsKey("isbn") || !Work.getMsgMap().containsKey("format") )
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"not enough params");
		
		else if(!db.hasUserBought(Work.getMsgMap().get("isbn"), Work.getUserName(),Work.getSessionID()))
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"user Must buy book first!");
		
		//return db.getbook(isbn,format)
		else 
			if(!db.getBookFormats(Work.getMsgMap().get("isbn")).contains(Work.getMsgMap().get("format")))
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"no such format for this book");
			else
				CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),db.getBook(Work.getMsgMap().get("isbn"),Work.getMsgMap().get("format")));
		
	}

	private void SearchBook(CEntry Work, int Privilage) 
	{

		//get set
		LinkedList<CBook> rez=CDBInteractionGenerator.GetInstance().SearchBook(Work.getMsgMap());
		
		//if not a librarian, remove invisible books
		if(Privilage < 3)
		{
			Object[] it= rez.toArray();
			for(Object b: it)
				if(((CBook)b).getM_invisible())
					rez.remove(b);
			
		}
		
		//reply to client
		CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), rez);
			
	}

	private void LogOut(CEntry Work, int Privilage) 
	{
		Set <CClientSession> temp=new HashSet<CClientSession>(m_sessions);
		//remove from sessions
		for(CClientSession s : temp) //find session
			if(s.getSessionID() == Work.getSessionID()) //sessionID match
				this.m_sessions.remove(s);
		//remove from responder and RESPOND
		try 
		{
			CRespondToClient.GetInstance().Remove(Work.getSessionID()).sendToClient("Logout OK");
		} 
		catch (IOException e)
		{
			System.out.println("CExecuter: Error returning msg during logout: "+e.getMessage());
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("CExecuter: Error returning msg during logout: "+e.getMessage());
		}	
	}

	private void ArrangePayment(CEntry Work, int Privilage) 
	{
		
		CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
		if(Privilage <3)
		{//here we made sure user is actually a reader and not a librarian / library manager
			if(Work.getMsgMap().get("type").compareTo("once") == 0)
			{
				db.RemoveCC(Work.getUserName());
				if(db.AddCC(Work.getUserName(), Work.getMsgMap().get("cc_num"), Work.getMsgMap().get("cc_expire"), Work.getMsgMap().get("cc_id")))
				  CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Updated user's credit card details");
				else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), null);								
			}
			else if(Work.getMsgMap().get("type").compareTo("monthly") == 0)
			{
				//add / update user's Subscription
				if(db.AddMonthly(Work.getUserName()))
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Updated user's Monthly subscription details");
				else
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),null);
			}
			else if(Work.getMsgMap().get("type").compareTo("yearly") == 0)
			{
				//add / update user's Subscription
				if(db.AddYearly(Work.getUserName()))
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Updated user's Yearly subscription details");
				else
					CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),null);
			}
		}
		else {
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Not authorized to use function \"ArrangePayment()\"");								
		}
	
		
	}

	private boolean ChangePayments(CUser usr, String newpays) 
	{
		CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
		//get current state
		LinkedList<String> curr=db.getUserPayments(usr.getM_userName());
		
		//get new state
		LinkedList<String> newp=new LinkedList<String>();
		if(newpays != null && newpays.compareTo("") != 0)
			for(String s: newpays.split(","))
				newp.add(s);
		
		try {
			
			if(!newp.isEmpty())
			for(String s:newp)
				if(! curr.contains(s) )
					if(s.toLowerCase().compareTo("monthly") == 0)
						db.AddMonthly(usr.getM_userName());
					else if(s.toLowerCase().compareTo("yearly") == 0)
						db.AddYearly(usr.getM_userName());
			
			if(!curr.isEmpty())		
			for(String s:curr)
				if(!newp.contains(s))
					if(s.toLowerCase().compareTo("credit card") != 0)
						db.deleteSubscription(usr.getM_userName(),s.toLowerCase());
					else
						db.deleteCC(usr.getM_userName());
			return true;
		} catch(Exception e) { System.out.println("could not update user Payment types: "+e.getMessage()); }
		return false;
	}
	
	
	/*
	 * this function just adds to the m_sessions container/
	 */
	private void add(CClientSession s)
	{
		this.m_sessions.add(s);
	}
	
	/*
	 *  checking to see if user is logged
	 *  
	 *  @returns true if user is logged
	 */
	private  boolean isLogged(CEntry Work)
	{
	//instead of isLogged() - saves checking if it's logged and then finding the session to kill
	for(CClientSession t : m_sessions)
		if(t.isOfUser(Work))
			return true;
	return false;
	}
	

	/*
	 * this function is mainly for the use of CStandbyUnit
	 * Used to let CExecuter know that he's got a CEntry waiting for him.
	 * @see server.core.CStandbyUnit
	 */
	public void NotifyOfEntry()
	{
		synchronized(m_obj)
		{
			if(m_sleeping)
				m_sleeping=false;
			notify(); 
		}
	}
	
	/*
	 * Infrastructure function.
	 * @returns a random integer from 0 to 19580427.
	 */
	public int Random()
	{
		return m_generator.nextInt();
	}

/*
 * function is used to kill a session that's related to the relevant CEntry
 * this function is open for public for possible future upgrades.
 */
	public void Kill(CEntry work) 
	{
		if(m_sessions.isEmpty())
			return;
		Set<CClientSession> arg=new HashSet<CClientSession>(m_sessions);
		for(CClientSession c:arg)
			if(work.getSessionID() != c.getSessionID() && work.getUserName().compareTo(c.getUsername()) == 0)
				{
					m_sessions.remove(c);//remove from sessions
					CRespondToClient.GetInstance().Remove(c.getSessionID()); //remove from Client list
				}
	}	//end of kill

	/*
	 * This function is a getter for the thread running on behalf of CExecuter
	 * This will only really be relevant if we want to expand the activity (to have 2 or more executers running) 
	 * Another idea (not implemented) was shutting the server down via killing the threads.
	 * @return Thread running CExecuter.
	 */
	public Thread getThread() {
		return m_ThreadHolder;
	}
	
		
	private void handleLogin(CEntry Work) 
	{
		
		//call on ValidateLogin to make sure user matches password		
		if(CDBInteractionGenerator.GetInstance().ValidateLogin(Work.getMsgMap().get("user"),Work.getMsgMap().get("password")))			
		{	
			
			//create session and add to Executer's session list
			CClientSession newSession=new CClientSession(Work.getSessionID(),Work.getUserName(),CDBInteractionGenerator.GetInstance().MySQLGetAuth(Work.getUserName())); 
			CExecuter.GetInstance().add(newSession);
			Work.setSessionID(newSession.getSessionID());
			
			//create instance
			AUser feedback=CDBInteractionGenerator.GetInstance().getUserInstance(Work.getUserName(),Work.getSessionID());
			
			
			
			//kill previous session
			if(CExecuter.GetInstance().isLogged(Work))
				CExecuter.GetInstance().Kill(Work);	/*   - session dead*/
			
			//send response to client
			CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), feedback);
			return;
		}	//end of valid login
		
		CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),null);
		CRespondToClient.GetInstance().Remove(Work.getSessionID());
	
		return ; //quick exit
		
	}
	
	
	//function just changes names of months from numbers to actual names
	//also adds months that aren't in set with 0 as counter
	private HashMap<String,Integer> ChangeMonthsNames(HashMap<String,Integer> arg)
	{
		HashMap<String,Integer> tmp=new HashMap<String,Integer>(arg);
		arg.clear();
		
		if(tmp.containsKey("01"))
			arg.put("Jan",tmp.get("01"));
		else
			arg.put("Jan",new Integer(0));
		
		
		if(tmp.containsKey("02"))
			arg.put("Feb",tmp.get("02"));
		else
			arg.put("Feb",new Integer(0));
		
		
		if(tmp.containsKey("03"))
			arg.put("Mars",tmp.get("03"));
		else
			arg.put("Mars",new Integer(0));
		
		
		if(tmp.containsKey("04"))
			arg.put("Apr",tmp.get("04"));
		else
			arg.put("Apr",new Integer(0));
		
		
		if(tmp.containsKey("05"))
			arg.put("May",tmp.get("05"));
		else
			arg.put("May",new Integer(0));
		
		
		if(tmp.containsKey("06"))
			arg.put("June",tmp.get("06"));
		else
			arg.put("June",new Integer(0));
		
		
		if(tmp.containsKey("07"))
			arg.put("July",tmp.get("07"));
		else
			arg.put("July",new Integer(0));
		
		
		if(tmp.containsKey("08"))
			arg.put("Aug",tmp.get("08"));
		else
			arg.put("Aug",new Integer(0));
		
		
		if(tmp.containsKey("09"))
			arg.put("Sep",tmp.get("09"));
		else
			arg.put("Sep",new Integer(0));
		
		
		if(tmp.containsKey("10"))
			arg.put("Oct",tmp.get("10"));
		else
			arg.put("Oct",new Integer(0));

		
		if(tmp.containsKey("11"))
			arg.put("Nov",tmp.get("11"));
		else
			arg.put("Nov",new Integer(0));

		
		if(tmp.containsKey("12"))
			arg.put("Dec",tmp.get("12"));
		else
			arg.put("Dec",new Integer(0));
		
		
		return arg;
	}

	//same as prev only with full month name
	private Set<CPurchaseStats> ChangeLongMonthsNames(Set<CPurchaseStats> arg)
	{
		Set<CPurchaseStats> tmp=new HashSet<CPurchaseStats>(arg);
		arg.clear();
		
		for(CPurchaseStats a: tmp)
			if(a.getmonth().compareTo("01") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"Jannuar"));
			else if(a.getmonth().compareTo("02") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"Februar"));
			else if(a.getmonth().compareTo("03") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"Mars"));
			else if(a.getmonth().compareTo("04") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"April"));
			else if(a.getmonth().compareTo("05") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"May"));
			else if(a.getmonth().compareTo("06") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"June"));
			else if(a.getmonth().compareTo("07") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"July"));
			else if(a.getmonth().compareTo("08") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"August"));
			else if(a.getmonth().compareTo("09") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"September"));
			else if(a.getmonth().compareTo("10") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"October"));
			else if(a.getmonth().compareTo("11") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"November"));
			else if(a.getmonth().compareTo("12") == 0)
				arg.add(new CPurchaseStats(a.getisbn(),a.gettitle(),"Jannuar"));
			
	
		
		return arg;
		
	}
	
	private Set<CBookStats> ChangeLongMonthNames(Set<CBookStats> arg)
	{
		Set<CBookStats> tmp=new HashSet<CBookStats>(arg);
		arg.clear();
		
		for(CBookStats a: tmp)
			if(a.getmonth().compareTo("01") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"Jannuar"));
			else if(a.getmonth().compareTo("02") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"Februar"));
			else if(a.getmonth().compareTo("03") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"Mars"));
			else if(a.getmonth().compareTo("04") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"April"));
			else if(a.getmonth().compareTo("05") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"May"));
			else if(a.getmonth().compareTo("06") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"June"));
			else if(a.getmonth().compareTo("07") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"July"));
			else if(a.getmonth().compareTo("08") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"August"));
			else if(a.getmonth().compareTo("09") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"September"));
			else if(a.getmonth().compareTo("10") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"October"));
			else if(a.getmonth().compareTo("11") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"November"));
			else if(a.getmonth().compareTo("12") == 0)
				arg.add(new CBookStats(a.getUsername(),a.getFullName(),"Jannuar"));
	
		return arg;
		
	}

	/*
	 * function checks popularity of all books in DB and sets their ranks accordingly (in DB!)
	 * @see server.core.CExecuter#startCheck()
	 */
	public static void recheckPopularity()
	{
		synchronized(CExecuter.GetInstance()) 
		{ //just to make sure there won't be 2 or more checks running simultaneously
			CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
			//get all books
			LinkedList<CBook> blist=db.SearchBook(new HashMap<String,String>());
			//create a new temp set to hold these
			TreeSet<CBook> tmp = new TreeSet<CBook>();
			int k,l;
			for(CBook b:blist)	//for every book, set rank as popularity, and add to tmp (sorted)
			{
				k=db.GetViews(b.getM_ISBN());
				l=db.GetPurchases(b.getM_ISBN());
				b.setM_rank( k / CServerConstants.POPULARITY_RATIO() +l );
				tmp.add(b);
			}
			
			int i=1;
			Iterator<CBook> it=tmp.descendingIterator();
			while(it.hasNext())
			{	//for each book set it's rank, and ask dbig to 
				CBook temp=it.next();
				temp.setM_rank(i);
				db.SetRank(temp.getM_ISBN(), i++);
			}
			tmp.clear();
			
		}//end of synchronized
	}//end of popularity check
	
	
	
	
	/*
	 * this function is dangerous as it may take a long time until thread returns to caller!
	 * function will tell the maintenance thread to stop, which will happen as soon as it ends it's cycle ( and wakes up from Thread.sleep).
	 * @see server.core.CExecuter#startCheck()
	 */
	public void stopMaintenance() throws InterruptedException
	{
		if(m_MaintenanceRunner == null)
			return;
		m_running=false;
		while(m_MaintenanceRunner != null)
			Thread.sleep(100);
	}
	/*
	 * this function starts the check.
	 * if maintenance check is already running or if there's a thread responsible for it then it will return false
	 * the  maintenance functions (popularity check and clear sIDs) will return every cycle of the thread.
	 * @param delay states how much to wait between cycles(in miliseconds) NOTE: delay can't be smaller than 5 seconds, if it is function will just set it to 5 seconds
	 * @return success
	 */
	public boolean startCheck(int delay)
	{
		//if exists then stop checking
		if(m_MaintenanceRunner != null)
			return false;
		
		//create and start check
		m_MaintenanceRunner=new MaintenanceRunner(delay); 
		m_MaintenanceRunner.start();
		return true;
	}
	
	//private class to help keep in touch of the thread running the checks
	private class MaintenanceRunner extends Thread
	{
		private int m_checkdelay;
		//constructor
		public MaintenanceRunner(int sl)
		{
			if(sl <5000)
				m_checkdelay=5000;
			else
				m_checkdelay=sl;
			
			m_running=true;
		}
		
		
		/*
		 * flow chart for function
		 * while ( should run && wasn't told to stop )
		 * 	 DO: maintenance {
		 * 						popularity check
		 * 						remove sID from old reciepts
		 * 					}
		 * 		Sleep for awhile( delay / 1000) seconds
		 * 
		 * @see java.lang.Thread#run()
		 * @see server.core.CExecuter#startCheck()
		 */
		public void run()
		{
			m_running=true;
			
			while(CExecuter.m_running)
			{
				//Maintenance functions start
				CExecuter.recheckPopularity();
				CDBInteractionGenerator.GetInstance().removeSessionId();
				//Maintenance functions end
				
				
				//go to sleep
				try 
				{ 
					Thread.sleep(m_checkdelay*1000);
				} 
				catch (InterruptedException e) 
				{	
					//if interrupted
					System.out.println("Popularity check timer halted(can't sleep): "+e.getMessage()); 
					CExecuter.m_running=false; 
					CExecuter.m_MaintenanceRunner=null;
					return;	
				}
			} //end of loop
		} //end of run
		
	}	//end of MaintenanceRunner
	
}





