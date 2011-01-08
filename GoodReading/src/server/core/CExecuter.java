package server.core;

import client.core.AUser;
import client.core.EActor;

import common.api.CEntry;
import common.api.CListOptions;
import common.data.*;
import ocsf.server.ConnectionToClient;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import server.db.CDBInteractionGenerator;


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
		this.m_sessions=new HashSet<CClientSession>();
	}
	
	private static void init()
	{
		m_obj=new CExecuter();
		m_obj.m_sleeping=true;
		m_obj.m_generator = new Random( 19580427 );
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
						v=tmp.get(k).replace("\\", "/").replace("\'", "'").replace("'", "\\'");
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
							System.out.println("Server fail, can't 'wait' in func run via CExecuter");
							CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server fail, can't 'wait' in func run via CExecuter");
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
							
						}//end of Logout
						
						else if(Work.getMsgType().compareTo("SearchSubtopics") == 0)
						{
							LinkedList<String> rez=db.getSubTopics(Work.getMsgMap().get("topic"));
							CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), rez.toArray());
						}//end of SearchSubtopics
						
						else if(Work.getMsgType().compareTo("SearchBook") == 0)
						{
							//get set
							LinkedList<CBook> rez=db.SearchBook(Work.getMsgMap());
							
							//if not a librarian, remove invisible books
							if(Privilage < 3)
							{ //TODO: test searchBook
								Object[] it= rez.toArray();
								for(Object b: it)
									if(((CBook)b).getM_invisible())
										rez.remove(b);
								
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
							
						} //end of Searchreview
						else if(Work.getMsgType().compareTo("GetUnhandledReviews") == 0)
						{
							if(Privilage <3)
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "User not authorized");
							else {
								//get set
								LinkedList<CBookReview> rez=db.SearchReview(Work.getMsgMap());
								LinkedList<CBookReview> temp=new LinkedList<CBookReview>(rez);
								
								//remove handled reviews
								for(CBookReview rev: temp)
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
						else if(Work.getMsgType().compareTo("DeleteReview") == 0)
						{
							Map<String,String> arg=Work.getMsgMap();
							if(Privilage <3)
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must have proper privilage");
							else
							{//we're here because client has privilage
								if(db.deleteReview(arg.get("isbn"),arg.get("author")))
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Review Deleted");
								else 
								{
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: failed to delete");
									CDBInteractionGenerator.GetInstance().ServerUpdateLog("Failure at executer: deleting review\n");  System.out.println("failure at executer: delete review");
								}
							}//end of reading auth
						} //end of DeleteReview
						else if(Work.getMsgType().compareTo("DeleteBook") == 0)
						{
							Map<String,String> arg=Work.getMsgMap();
							if(Privilage <3)
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must have proper privilage");
							else
							{//we're here because client has privilage
								if(db.deleteBook(arg.get("isbn")))
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Book Deleted");
								else 
								{
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: failed to delete");
									CDBInteractionGenerator.GetInstance().ServerUpdateLog("Failure at executer: deleting Book\n");  System.out.println("failure at executer: delete Book");
								}
							}//end of reading auth
						} //end of DeleteBook
						else if(Work.getMsgType().compareTo("DeleteFile") == 0)
						{	//this function is not implemented in GUI, but it is usefull in order to delete book files in DB and it's par func on DBIG is actually used a few times.
							Map<String,String> arg=Work.getMsgMap();
							if(Privilage <3)
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: user must have proper privilage");
							else
							{//we're here because client has privilage
								if(db.deleteFile(arg.get("isbn"),arg.get("format")))
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "File Deleted");
								else 
								{
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: failed to delete");
									CDBInteractionGenerator.GetInstance().ServerUpdateLog("Failure at executer: deleting File\n");  System.out.println("failure at executer: delete File");
								}
							}//end of reading auth
						} //end of DeleteFile
						
						else if(Work.getMsgType().compareTo("EditReview") == 0)
						{	
								
							if(Privilage < 3)
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Fail: inappropriate user privilage");
							else
							{
								Map<String,String> arg=Work.getMsgMap();
								//public boolean editReview(String isbn, String author, String title, String review)
								int i=arg.get("confirm").compareTo("true");
								if( i ==0 ) //confirm = true
									i=1;
								else
									i=0;
								if(db.editReview(arg.get("isbn"),arg.get("author"),arg.get("title"),arg.get("review"), i  ,Work.getUserName()))
									CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "Review Eddited.");

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
								if(arg.get("paytype").compareTo("Monthly")==0)
									if (db.subscriptionPay("Monthly",Work.getUserName()))
										{ 
										int rID=db.createReciept(Work.getUserName(),arg.get("isbn"),"Monthly");//generate reciept
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),new Integer(rID));//response to client
										}
									else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "failed operation");
								
								else if(arg.get("paytype").compareTo("Yearly")==0)
									if (db.subscriptionPay("Yearly",Work.getUserName()))
									{ 
										int rID=db.createReciept(Work.getUserName(),arg.get("isbn"),"Yearly");//generate reciept
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),new Integer(rID));//response to client
									}
									else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "failed operation");
								
								else if(arg.get("paytype").compareTo("Credit Card")==0)
									if (db.ccPay(Work.getUserName(),db.getPrice(arg.get("isbn")),arg.get("isbn")))
									{ 
										int rID=db.createReciept(Work.getUserName(),arg.get("isbn"),"Credit Card");//generate reciept
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), new Integer(rID));//response to client
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
									if(db.giveScore(arg.get("isbn"),Work.getUserName(),Integer.parseInt(arg.get("score"))))
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
									{		
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(), "added new book: FAIL");
									}
								else 
								{
									int count=0;
									//now we check if we have new files to add
									if(arg.containsKey("format"))
									{
										count=arg.get("format").split(",").length;
										for(String a: arg.get("format").split(","))
											if( db.UploadFile(arg.get("isbn"),a,new CFile("c:/library/"+arg.get("isbn")+"."+a)) )
												count--;
									}
									if(count == 0)
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"added new book: SUCCESS");
									else 
										CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"added new book: OK, problems uploading files");
									 
								}	
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
											count=arg.get("format").split(",").length;
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
								}catch(NullPointerException e)
								{
								System.out.println("Can't find book");	
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"Edit book: FAIL, couldn't find book");
								
								}
								}
							}
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
								Map<String,Integer> rez=db.getBookSales(Work.getMsgMap().get("isbn"),Work.getMsgMap().get("year"));
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),rez);
							}
							else
							    CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"User must have privilage!");
						
						}//end of getBookSales HISTOGRAM
						
						
						else if(Work.getMsgType().compareTo("getBookViews") == 0)
						{
							if( Privilage >3 ) 
							{
								Map<String,Integer> rez=db.getBookViews(Work.getMsgMap().get("isbn"),Work.getMsgMap().get("year"));
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
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),rez);
							}
							else
							    CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"User must have privilage!");
						
						}//end of UserFullUserPurchases
						
						else if(Work.getMsgType().compareTo("FullBookViews") == 0)
						{
							if( Privilage >3 ) 
							{
								Set<CBookStats> rez=db.getFullBookViews(Work.getMsgMap().get("isbn"),Work.getMsgMap().get("year"));
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),rez);
							}
							else
							    CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"User must have privilage!");
						}//end of BookViews
						else if(Work.getMsgType().compareTo("FullBookSales") == 0)
						{
							if( Privilage >3 ) 
							{
								Set<CBookStats> rez=db.getFullBookSales(Work.getMsgMap().get("isbn"),Work.getMsgMap().get("year"));
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),rez);
							}
							else
							    CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"User must have privilage!");
						}//end of FullBookSales
						
						
											
						else if(Work.getMsgType().compareTo("SearchUser") == 0)
						{
							LinkedList<CUser> ans;
							Map<String,String> arg=Work.getMsgMap();
							for(String a: arg.keySet())
								if (arg.get(a).compareTo("")==0)
									arg.remove(a);
							if(Privilage >3)
							{
								ans=db.SearchUser(arg);
								CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),ans);
							}
							else CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"no privilage!");
						}//end of SearchUser
						
						else if(Work.getMsgType().compareTo("EditUser") == 0)
						{
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
										{
											db.SetUserPriv(usr,0);
											if(db.editUser(usr))
												CRespondToClient.GetInstance().SendResponse(Work.getSessionID(),"success");
										}
							
									
									else if(db.editUser(usr) && (!arg.containsKey("suspend") || !Boolean.parseBoolean(arg.get("suspend"))) )
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
	
	
	

	private boolean ChangePayments(CUser usr, String newpays) 
	{
		CDBInteractionGenerator db=CDBInteractionGenerator.GetInstance();
		//get current state
		LinkedList<String> curr=db.getUserPayments(usr.getM_userName());
		
		//get new state
		LinkedList<String> newp=new LinkedList<String>();
		for(String s: newpays.split(","))
			newp.add(s);
		try {
		for(String s:newp)
			if(! curr.contains(s) )
				if(s.toLowerCase().compareTo("monthly") == 0)
					db.AddMonthly(usr.getM_userName());
				else if(s.toLowerCase().compareTo("yearly") == 0)
					db.AddYearly(usr.getM_userName());
		for(String s:curr)
			if(!newp.contains(s))
				if(s.toLowerCase().compareTo("creditcard") != 0)
					db.deleteSubscription(usr.getM_userName(),s.toLowerCase());
				else
					db.deleteCC(usr.getM_userName());
		return true;
		} catch(Exception e) { System.out.println("could not update user Payment types: "+e.getMessage()); }
		return false;
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
	}

	public Thread getThread() {
		// @param this function will only really be relevant if we want to expand the activity (to have 2 or more executers running) 
		return m_ThreadHolder;
	}
	
		
	public void handleLogin(CEntry Work) 
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
	
}





