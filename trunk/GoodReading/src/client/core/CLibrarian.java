package client.core;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.common.CClientConnector;
import common.api.CEntry;
import common.data.CBook;
import common.data.CBookReview;

public class CLibrarian extends AUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CLibrarian(String FirstName, String LastName, int UserId, String UserName, int SessionID)
	{
		super(FirstName,LastName,UserId,UserName, EActor.Librarian, SessionID);
	}
	
	
	public void addNewBook(String title, String author, String isbn, String release, String publisher, String summery, String price, String topic, String lable, String TOC, boolean invis, String lang) throws IOException, Exception
	{
		CEntry entryToSrv;
		CBook book;
		// check date
		Pattern pd = Pattern.compile("(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})");
		Matcher md = pd.matcher(release);
		if(!md.matches()){
			throw new IOException("Invalid Username/Password Characters");
		}
		double Bprice = Double.parseDouble(price);
		book = new CBook(isbn,author, title, release, publisher, summery, Bprice, 0, 0, topic, lable, TOC, invis,lang );
		entryToSrv = new CEntry("addnewbook", book, this.getUserName(),this.getUserSessionId());
		CClientConnector.getInstance().messageToServer(entryToSrv);
	}
	
	
	public LinkedList<CBookReview> searchNewReviews() throws Exception
	{
		
		HashMap<String, String> empty = new HashMap<String, String>();;
		CEntry entryToSrv = new CEntry("getNewReviews", empty, this.getUserName(), this.getUserSessionId() );
		Object temp = CClientConnector.getInstance().messageToServer(entryToSrv);
		@SuppressWarnings("unchecked")
		LinkedList<CBookReview> booksReview = (LinkedList<CBookReview>) temp;
		return booksReview;
	}
	
	public void updateReview(String isbn, String author, String title, String review) throws Exception
	{
		Map<String, String> upReview = new HashMap<String, String>();
		CEntry entryToSrv ;
		upReview.put("isbn",isbn);
		upReview.put("author",author);
		upReview.put("title",title);
		upReview.put("review",review);
		entryToSrv = new CEntry("updateReview", upReview, this.getUserName(), this.getUserSessionId() );
		CClientConnector.getInstance().messageToServer(entryToSrv);
	}
	
	
	public void updateBookDetails(String isbn, Map<String,String> newDetails) throws Exception
	{
		CEntry entryToSrv ;
		newDetails.put("bookisbn",isbn);
		entryToSrv = new CEntry("updatebookdetails",newDetails, this.getUserName(),this.getUserSessionId());
		CClientConnector.getInstance().messageToServer(entryToSrv);
	}
	
	
	public void deleteReview(String title, String author, String isbn) throws Exception
	{
		CEntry entryToSrv ;
		Map<String, String> delReview = new HashMap<String, String>();
		delReview.put("title", title);
		delReview.put("author", author);
		delReview.put("isbn", isbn);
		entryToSrv = new CEntry("deletereview",delReview, this.getUserName(),this.getUserSessionId());
		CClientConnector.getInstance().messageToServer(entryToSrv);		
	}
	
	
	
}
