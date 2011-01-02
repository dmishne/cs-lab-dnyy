package server.core;

import client.core.AUser;

import common.api.CEntry;
import common.data.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import server.db.CDBInteractionGenerator;

import ocsf.server.ConnectionToClient;

public class CExecuter implements Runnable
{
	private Set <CClientSession> m_sessions;
	private boolean m_sleeping; // @param indicates Executer is sleeping
	private Thread m_ThreadHolder;
	private Random m_generator;	//infrastracture helping to generate random numbers.
	private static CExecuter m_obj;
	
	/*signleton*/
	public static CExecuter GetInstance()
	{
		if(m_obj == null)
			init();
		return m_obj;
	}

	private CExecuter()
	{
	    //Instance is configured inside init()
		this.m_sessions=new TreeSet<CClientSession>();
	}
	
	private static void init()
	{
		m_obj=new CExecuter();
		m_obj.m_sleeping=true;
		m_obj.m_generator = new Random( 19580427 );
		//new Thread(m_obj).start();
		m_obj.m_ThreadHolder=new Thread(m_obj);
		m_obj.m_ThreadHolder.start();
	}
	
	public void run()
	{
		CEntry Work;
		try {
			while (true)
			{
				if(CStandbyUnit.GetInstance().isEmpty())
				{
					m_sleeping = true;
				}
				synchronized(m_obj)
				{
					while(m_sleeping)
						wait();
				}
				Work=CStandbyUnit.GetInstance().getEntryFromQueue();
				/*handle entry from standby unit*/
				//((CClient_Entry) msg).getSessionID() = m_generator.nextInt();
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
				else 
				{
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
							System.out.println("Server fail, can't 'wait' in func run via CExecuter");
							CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server fail, can't 'wait' in func run via CExecuter");
						}
					}
					else
					{
						//TODO: update responder to contain NEW connection
						
						//getting an instance as "db" will make things easier for this part
						CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
						//executing entry, currently only little to do
						if(Work.getMsgType().compareTo("ArrangePayment") == 0)
						{	
							
							if(Privilage <3)
							{//here we made sure user is actually a reader and not a librarian / library manager
								if(Work.getMsgMap().get("type").compareTo("once") == 0)
								{
									db.RemoveCC(Work.getUserName());
									//TODO: validation for existing params
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
						}// end of ArrangePayment
						
						
						else if(Work.getMsgType().compareTo("Logout") == 0)
						{
							//remove from sessions
							for(CClientSession s : this.m_sessions) //find session
								if(s.getSessionID() == Work.getSessionID()) //sessionID match
									this.m_sessions.remove(s);
							//remove from responder and RESPOND
							try {
								CRespondToClient.GetInstance().Remove(Work.getSessionID()).sendToClient("Logout OK");
							} catch (IOException e) {
								System.out.println("CExecuter: Error returning msg during logout: "+e.getMessage());
								CDBInteractionGenerator.GetInstance().ServerUpdateLog("CExecuter: Error returning msg during logout: "+e.getMessage());
								}
							
						}//end of Logout
						
						
						else if(Work.getMsgType().compareTo("SearchBook") == 0)
						{
							//get set
							LinkedList<CBook> rez=db.SearchBook(Work.getMsgMap());
							
							//if not a librarian, remove invisible books
							if(Privilage < 3)
							{
								Iterator<CBook> it = rez.iterator();
								while(it.hasNext())
								{
									CBook temp = it.next();
									if(!temp.getM_invisible())
									{
										it.remove();
									}
								}
							}
							
							//reply to client
							CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), rez);
							
						} //end of Searchbook
						

						else if(Work.getMsgType().compareTo("DownloadBook") == 0)
						{
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
							
						} //end of DownloadBook
						
						
						else if(Work.getMsgType().compareTo("SearchReview") == 0)
						{
							//get set
							LinkedList<CBookReview> rez=db.SearchReview(Work.getMsgMap());
							
							//if not a librarian, remove unapproved reviews
							if(Privilage < 3)
								for(CBookReview rev: rez)
									if(rev.getaccepted() <1)
										rez.remove(rev);
							
							//reply to client
							CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), rez);
							
						} //end of Searchreview
						else if(Work.getMsgType().compareTo("GetUnhandledReviews") == 0)
						{
							if(Privilage <3)
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "User not authorized");
							else {
								//get set
								LinkedList<CBookReview> rez=db.SearchReview(Work.getMsgMap());
								
								//remove handled reviews
								for(CBookReview rev: rez)
									if(rev.getaccepted() ==1)
										rez.remove(rev);
								
								//reply to client
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), rez);
							}
						} //end of GetUnhandledReviews
						else if(Work.getMsgType().compareTo("AddReview") == 0)
						{
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
						} //end of AddReview
						
						else if(Work.getMsgType().compareTo("EditReview") == 0)
						{	
								
							if(Privilage < 3)
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: inappropriate user privilage");
							else
							{
								Map<String,String> arg=Work.getMsgMap();
								//public boolean editReview(String isbn, String author, String title, String review)
								
								if(db.editReview(arg.get("isbn"),arg.get("author"),arg.get("title"),arg.get("review"),Integer.parseInt(arg.get("accepted")),Work.getUserName()))
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Review Submitted");

								else 
								{
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: failed to submit");
									CDBInteractionGenerator.GetInstance().ServerUpdateLog("Fail: failed to submit");  System.out.println("failure at executer: editting review");
								}
							}
						} //end of EditReview
						else if(Work.getMsgType().compareTo("PurchaseBook") == 0)
						{
							if(Privilage <3)
							{//here we made sure user is actually a reader and not a librarian / library manager
								Map<String,String> arg= Work.getMsgMap();
								//update account according to payment method
								if(arg.get("paymethod").compareTo("monthly")==0)
									if (db.subscriptionPay("monthly",Work.getUserName()))
										{ 
										int rID=db.createReciept(Work.getUserName(),arg.get("isbn"),"monthly");//generate reciept
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Success "+Integer.toString(rID));//response to client
										}
									else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "failed operation");
								
								else if(arg.get("paymethod").compareTo("yearly")==0)
									if (db.subscriptionPay("yearly",Work.getUserName()))
									{ 
										int rID=db.createReciept(Work.getUserName(),arg.get("isbn"),"yearly");//generate reciept
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Success "+Integer.toString(rID));//response to client
									}
									else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "failed operation");
								
								else if(arg.get("paymethod").compareTo("once")==0)
									if (db.ccPay(Work.getUserName(),db.getPrice(arg.get("isbn")),arg.get("isbn")))
									{ 
										int rID=db.createReciept(Work.getUserName(),arg.get("isbn"),"once");//generate reciept
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Success "+Integer.toString(rID));//response to client
									}
									else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "failed operation");
				
							} //end of privilege
							else //take care of unprivileged ppl
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Not authorized to use function \"PurchaseBook()\"");		
							
						} //end of PurchaseBook
						
						
						//submit Score
						else if(Work.getMsgType().compareTo("SubmitScore") == 0)
						{
							Map<String,String> arg= Work.getMsgMap();
							
							if(Privilage <3 )
								if(!db.hasUserRead(arg.get("isbn"),Work.getUserName()))
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must read book before submitting a score");
								
								
								else//we're here because client has read the book
								{
									if(db.giveScore(arg.get("isbn"),Work.getUserName(),Integer.reverse(Integer.getInteger(arg.get("score")))))
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "success");
									else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Failed to comply");
								}
							
							
							else //take care of unprivileged ppl
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Not authorized to use function \"SubmitScore()\"");		
								
							} //end of SubmitScore	
						else if(Work.getMsgType().compareTo("AddBook") == 0)
						{
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
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "added new book: FAIL");
								else 
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"added new book: OK");
									
							}
						} //end of add book
						
						else if(Work.getMsgType().compareTo("EditBook") == 0)
						{
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
								{
									CBook a=db.SearchBook(getb).getFirst();
												//(String m_ISBN, String m_author, String m_title, String m_release, String m_publisher,				 String m_summary,double m_price, long m_score_count,double m_score,String m_topic, String m_lables, String m_TOC,boolean m_invisible, String m_language)
									//we got book by isbn, now we need to edit data:
								
									if(arg.containsKey("title"))
										a.settitle(arg.get("title"));
									 if(arg.containsKey("author"))
										a.setauthor(arg.get("author"));
									 if(arg.containsKey("lable"))
										a.setlables(arg.get("lable"));
									 if(arg.containsKey("publisher"))
										a.setpublisher(arg.get("publisher"));
									 if(arg.containsKey("summary"))
										a.setsummary(arg.get("summary"));
									 if(arg.containsKey("topic"))
										 a.settopic(arg.get("topic"));
									 if(arg.containsKey("TOC"))
										a.setTOC(arg.get("toc"));
									 if(arg.containsKey("language"))
										a.setlanguage(arg.get("language"));
									 if(arg.containsKey("price"))
										a.setprice(Double.parseDouble(arg.get("price")));
									 if(arg.containsKey("invisible"))
										a.setinvisible(Boolean.parseBoolean(arg.get("invisible")));
								
										
									if (!db.editBookDetails(a))
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"edit book: FAIL");
									else 
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"edit book: OK");
										
								}
							}
						} //end of edit book
						else if(Work.getMsgType().compareTo("GetPayment") == 0)
						{
							LinkedList <String> ans= db.getUserPayments(Work.getUserName());
							CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),ans.toArray());
						}//end of GetPayment
						else if(Work.getMsgType().compareTo("GetFormats") == 0)
						{
							LinkedList <String> ans = db.getBookFormats(Work.getMsgMap().get("isbn"));
							db.StatisticsAddView(Work.getMsgMap().get("isbn"),Work.getUserName());
							CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),ans.toArray());
						}//end of GetFormats	
						
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
	
	
	

	public void add(CClientSession s)
	{
		this.m_sessions.add(s);
	}
	public  boolean isLogged(CEntry Work)
	{
	//instead of isLogged() - saves checking if it's logged and then finding the session to kill
	for(CClientSession t : m_sessions)
		if(t.isOfUser(Work))
			return true;
	return false;
	}
	

	
	public void NotifyOfEntry()
	{
		synchronized(m_obj)
		{
			if(m_sleeping)
				m_sleeping=false;
			notify(); ///need to check that we're not missing on notifyall()
		}
	}
	
	
	public int Random()
	{
		return m_generator.nextInt();
	}


	public void Kill(CEntry work) {
		// TODO Auto-generated method stub
		
	}

	public Thread getThread() {
		// TODO Auto-generated method stub, might not be needed**!!
		return m_ThreadHolder;
	}
	
		
	public void handleLogin(CEntry Work) 
	{
	
		//call on ValidateLogin to make sure user matches password		
		if(CDBInteractionGenerator.GetInstance().ValidateLogin(Work.getMsgMap().get("user"),Work.getMsgMap().get("password")))			
		{	
			
			//create session and add to Executer's session list
			CClientSession newSession=new CClientSession(Work.getSessionID(),Work.getUserName(),CDBInteractionGenerator.GetInstance().MySQLGetAuth(Work.getMsgMap().get("user"))); 
			CExecuter.GetInstance().add(newSession);
			
			
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
	
}





