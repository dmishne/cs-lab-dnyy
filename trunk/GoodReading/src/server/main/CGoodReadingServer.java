package server.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ocsf.client.ObservableClient;

import client.core.AUser;

import common.data.CBook;
import common.data.CBookReview;
import common.data.CFile;

import server.core.CExecuter;
import server.core.CServerConstants;
import server.core.CStandbyUnit;
import server.db.CDBInteractionGenerator;

public class CGoodReadingServer {

	
	final public static int Version = 0;
	final public static int Revision = 1;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * 			load properties (configuration)
		 *   		start up server,
		 *  		first comes DB
		 *  		second comes in the CStandbyUnit, Executer SHOULD go after standby unit, for he calls for an instance of it, however there is no limitation currently, it will justmake some of the code redundant to call it first
		 *	TODO:	next we should ??  
		 */
		CServerConstants.Config(); //load all properties
	
		CDBInteractionGenerator.GetInstance();		//.ServerUpdateLog("Server Started loading at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
		if(CStandbyUnit.GetInstance() == null)
			{
				System.out.println("Problem Instanciating Standby Unit");
				CDBInteractionGenerator.GetInstance().ServerUpdateLog("Problem Instanciating Standby Unit");
			}
		else {
			try {
				CStandbyUnit.GetInstance().listen();
			} catch (IOException e) {
				System.out.println("Error - Couldn't listen to client");
				CDBInteractionGenerator.GetInstance().ServerUpdateLog("Error - Couldn't listen to client");
			}
			System.out.println("Standby Unit online and waiting");
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Standby Unit online and waiting");
		}
		if(CExecuter.GetInstance() == null)
				{
					System.out.println("Problem Instanciating Executer");
					CDBInteractionGenerator.GetInstance().ServerUpdateLog("Problem Instanciating Executer");
				}
		else {
			System.out.println("Excecuter online and waiting");
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Excecuter online and waiting");
		}
		if (CServerConstants.POP_WINDOW())
			{try {
			 CServerInfo info = new CServerInfo("GoodReadingServer V" + Version + "." + Revision);
 			 info.setVisible(true);
			} catch(Exception e) { System.out.println("Can't get ip address: "+e.getMessage()); }
			}
		try {
		CFile arg=new CFile("c:/library/1234.pdf"); //
		arg.saveFile("c:/library/a.pdf");
		arg=CDBInteractionGenerator.GetInstance().getBook("1234","pdf");
		arg.saveFile("c:/library/b.pdf");
		} catch (Exception e) { System.out.println("AARRG!!"); }
		//	CFile arg=new CFile("c:/library/asd.pdf");
		
	//	CDBInteractionGenerator.GetInstance().MySQLInsertBlobFile(new CFile("arg.txt").getFilearray().toString());
	//	System.out.print(CDBInteractionGenerator.GetInstance().getPrice("1234"));
	//	System.out.print(CDBInteractionGenerator.GetInstance().hasUserRead("1234", "test2"));
	//	System.out.print(CDBInteractionGenerator.GetInstance().createReciept("yotam", "1234", "YEARLY"));
	//	System.out.print(CDBInteractionGenerator.GetInstance().submitReview("978-0747532744", "yotam", "title...", "review..."));
	//	System.out.print(CDBInteractionGenerator.GetInstance().giveScore("1234", "daniel", 9));
	//	System.out.print(CDBInteractionGenerator.GetInstance().subscriptionPay("YEARLY", "yotam"));
	/*	Map<String,String> mp = new HashMap<String,String>();
		mp.put("title", "Wheres Pluto");
		mp.put("publisher", "Keter");
		LinkedList<CBook> cb = new LinkedList<CBook>();
		cb = CDBInteractionGenerator.GetInstance().SearchBook(mp);
		Iterator<CBook> it = cb.iterator();
		while(it.hasNext())
		{
			System.out.printf(it.next().getM_author());
		}
	*/
	/*	Map<String,String> mp1 = new HashMap<String,String>();
		mp1.put("author", "yotam");
		LinkedList<CBookReview> cb1 = new LinkedList<CBookReview>();
		cb1 = CDBInteractionGenerator.GetInstance().SearchReview(mp1);
		Iterator<CBookReview> it1 = cb1.iterator();
		while(it1.hasNext())
		{
			System.out.printf(it1.next().getreview());
		}
	*/
	//	CBook cb = new CBook("978-0785673215", "J.K. Rowling", "Harry Potter 2", "2007-07-07", "Bloomsbury", "The 2nd harry potter book", 8.84, 0, 0, "topic1", "labele1", "toc1", false, "hebrew");
	//	System.out.print(CDBInteractionGenerator.GetInstance().insertNewBook(cb));
	//	CBook cb = new CBook("978-0785673215", "J.K. Rowling", "Harry Potter 12", "2007-07-07", "Bloomsbury", "The 2nd harry potter book", 8.84, 0, 0, "topic1", "labele1", "toc1", false, "hebrew");
	//	System.out.print(CDBInteractionGenerator.GetInstance().editBookDetails(cb));
	//	System.out.print(CDBInteractionGenerator.GetInstance().editReview("1234", "Yotam", "The book is awfull", "Cant read the book at all...", 0,"someone"));
	/*	LinkedList<String> lst = new LinkedList<String>();
		lst = CDBInteractionGenerator.GetInstance().getBookFormats("1234");
		Iterator<String> it = lst.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
	*/
	/*	LinkedList<String> lst = new LinkedList<String>();
		lst = CDBInteractionGenerator.GetInstance().getUserPayments("test2");
		Iterator<String> it = lst.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
	*/	
	//	CDBInteractionGenerator.GetInstance().StatisticsAddView("1234", "yotam1");
	//	System.out.print("stat view added");
	//	System.out.print(CDBInteractionGenerator.GetInstance().hasUserBought("1234", "test2", 12345));
	//	System.out.print(CDBInteractionGenerator.GetInstance().getBookViews("1234", "2010").toString());
	/*	Map<String,String> mp1 = new HashMap<String,String>();
		mp1.put("lastname", "mishne");
		LinkedList<AUser> cb1 = new LinkedList<AUser>();
		cb1 = CDBInteractionGenerator.GetInstance().SearchUser(mp1);
		Iterator<AUser> it1 = cb1.iterator();
		while(it1.hasNext())
		{
			System.out.printf(it1.next().toString());
		}
	*/	
	/*	Set<String> s = new TreeSet<String>();
		s = CDBInteractionGenerator.GetInstance().getLangs();
		Iterator<String> it = s.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
	*/
	//	System.out.print(CDBInteractionGenerator.GetInstance().deleteReview("1234", "nir"));
	//	System.out.print(CDBInteractionGenerator.GetInstance().AddCC("yotam", "1234567812345678", "2012-01-01", "1231231231"));
	/*	try {
			CDBInteractionGenerator.GetInstance().deleteCC("yotam");
		} catch (Exception e) {
			e.printStackTrace();
		}
	*/	
		
		
		//at last, we check that everthing is working
	/*
	 * 	if(CDBInteractionGenerator.GetInstance() != null && CStandbyUnit.GetInstance() != null && CExecuter.GetInstance() != null)
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server online at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
		else 
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server failed startup at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
	*/
	}

}
