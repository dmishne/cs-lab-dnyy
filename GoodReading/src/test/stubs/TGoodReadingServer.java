package test.stubs;

import server.db.CDBInteractionGenerator;

public class TGoodReadingServer {
	
/*
	try {//this is a check for CFile
		CFile arg=new CFile("c:/library/1234.pdf"); //
		System.out.println(arg.getFilearray());
		arg.saveFile("c:/library/a.pdf");
		System.out.println(arg.getFilearray());
		arg=CDBInteractionGenerator.GetInstance().getBook("1234","pdf");
		System.out.println(arg.getFilearray());
		arg.saveFile("c:/library/b.pdf");
		
} catch (Exception e) { System.out.println("AARRG!!"); }*/
	
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
//	System.out.print(CDBInteractionGenerator.GetInstance().getBookViews("1234", null, null).toString());
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
//	System.out.print(CDBInteractionGenerator.GetInstance().getBookSales("012345", "2011"));
/*	Set<CBookStats> s = new HashSet<CBookStats>();
	s = CDBInteractionGenerator.GetInstance().getFullBookViews("1234", "2010");
	Iterator<CBookStats> it = s.iterator();
	while(it.hasNext())
	{
		System.out.println(it.next().getFullName());
	}
*/			
	
	
	//	System.out.println(CDBInteractionGenerator.GetInstance().insertTopic("Comedy"));
	//	System.out.println(CDBInteractionGenerator.GetInstance().insertSubTopic("Comedy","SitCom"));
	//	System.out.println(CDBInteractionGenerator.GetInstance().getTopics().toString());
	//	System.out.println(CDBInteractionGenerator.GetInstance().getSubTopics("Comedy").toString());
	//	System.out.println(CDBInteractionGenerator.GetInstance().insertBookTopics("1234", "Comedy", "SitCom"));
	//	System.out.println(CDBInteractionGenerator.GetInstance().getBookTopics("1234"));
	//	System.out.println(CDBInteractionGenerator.GetInstance().insertNewBook("61010101021", "topic book else", "topic11", "2000-08-08", "booz", "summi", 8.5, 0, 0, "~utopic@usub1,usub2,usub3,usub4~utopic2@usub1,usub2,usub3~utopic3@usub1,usub3", "lllll", "1. 2. 3.", false, "English"));
	//  System.out.print(CDBInteractionGenerator.GetInstance().GetPurchases("012345"));
	
	//at last, we check that everthing is working
/*
 * 	if(CDBInteractionGenerator.GetInstance() != null && CStandbyUnit.GetInstance() != null && CExecuter.GetInstance() != null)
		CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server online at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
	else 
		CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server failed startup at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
*/
}
