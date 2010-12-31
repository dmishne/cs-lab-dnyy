package server.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import common.data.CBook;
import common.data.CBookReview;
import common.data.CFile;

import server.core.CExecuter;
import server.core.CStandbyUnit;
import server.db.CDBInteractionGenerator;

public class CGoodReadingServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 *   		start up server,
		 *  		first comes DB
		 *  		second comes in the CStandbyUnit, Executer SHOULD go after standby unit, for he calls for an instance of it, however there is no limitation currently, it will justmake some of the code redundant to call it first
		 *	TODO:	next we should ??  
		 */
		
		CDBInteractionGenerator.GetInstance();		//.ServerUpdateLog("Server Started loading at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
		if(CStandbyUnit.GetInstance() == null)
			System.out.println("Problem Instanciating Standby Unit");
		else {
			try {
				CStandbyUnit.GetInstance().listen();
			} catch (IOException e) {
				System.out.println("Error - Couldn't listen to client");
			}
			System.out.println("Standby Unit online and waiting");
		}
		if(CExecuter.GetInstance() == null)
				System.out.println("Problem Instanciating Executer");
		else System.out.println("Excecuter online and waiting");
	
		
	//	CDBInteractionGenerator.GetInstance().MySQLInsertBlobFile(new CFile("arg.txt").getFilearray().toString());
	//	System.out.print(CDBInteractionGenerator.GetInstance().getPrice("1234"));
	//	System.out.print(CDBInteractionGenerator.GetInstance().hasUserRead("1234", "yotam"));
	//	System.out.print(CDBInteractionGenerator.GetInstance().createReciept("yotam", "1234", "YEARLY"));
	//	System.out.print(CDBInteractionGenerator.GetInstance().submitReview("978-0747532744", "yotam", "title...", "review..."));
	//	System.out.print(CDBInteractionGenerator.GetInstance().giveScore("1234", "yotam", 3));
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
		
		
		//at last, we check that everthing is working
	/*
	 * 	if(CDBInteractionGenerator.GetInstance() != null && CStandbyUnit.GetInstance() != null && CExecuter.GetInstance() != null)
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server online at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
		else 
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server failed startup at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
	*/
	}

}
