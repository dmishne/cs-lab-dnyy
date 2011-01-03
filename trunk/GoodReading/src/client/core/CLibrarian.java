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
	
	
	public void addNewBook(String title, String author, String isbn, String release, String publisher, String summary, String price, String topic, String lable, String TOC, boolean invis, String lang) throws IOException, Exception
	{
		CEntry entryToSrv;
		HashMap<String, String> newBook = new HashMap<String, String>();
		String visible = "true";
		// check date
		Pattern pd = Pattern.compile("(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})");
		Matcher md = pd.matcher(release);
		if(!md.matches()){
			throw new IOException("Invalid Date format!");
		}
		if(  !invis      &&       this.getPrivilege() != EActor.LibraryManager  )
		{
			throw new IOException("You have no permition to edit book visibility!");
			
		}
		else if ( this.getPrivilege() == EActor.LibraryManager  && invis == false)
			          visible = "false"; 
		if(isbn.isEmpty())
			throw new IOException("Book ISBN is a must!");
		else if(author.isEmpty())
			throw new IOException("Book Author is a must!");
		else if(title.isEmpty())
			throw new IOException("Book Title is a must!");
		else if(publisher.isEmpty())
			throw new IOException("Book Publisher is a must!");
		else if(topic.isEmpty())
			throw new IOException("Book Topic is a must!");
		else if(lang.isEmpty())
			throw new IOException("Book Language is a must!");
		else if(price.isEmpty())
			throw new IOException("Book Price is a must!");
		else
		{
			newBook.put("isbn", isbn);
			newBook.put("author", author);
			newBook.put("title", title);
			newBook.put("release", release);
			newBook.put("publisher", publisher);
			newBook.put("summary", summary);
			newBook.put("price", price);
			newBook.put("topic", topic);
			newBook.put("lables", lable);
			newBook.put("toc", TOC);
			newBook.put("invisible", visible);
			newBook.put("languages", lang);
			entryToSrv = new CEntry("AddBook", newBook, this.getUserName(),this.getUserSessionId());
			CClientConnector.getInstance().messageToServer(entryToSrv);
		}
	}
	
	
	public LinkedList<CBookReview> searchNewReviews() throws Exception
	{		
		HashMap<String, String> empty = new HashMap<String, String>();
		CEntry entryToSrv = new CEntry("GetUnhandledReviews", empty, this.getUserName(), this.getUserSessionId() );
		Object temp = CClientConnector.getInstance().messageToServer(entryToSrv);
		@SuppressWarnings("unchecked")
		LinkedList<CBookReview> booksReview = (LinkedList<CBookReview>) temp;
		return booksReview;
	}
	
	public void updateReview(String isbn, String author, String title, String curr_title, String review) throws Exception
	{
		HashMap<String, String> upReview = new HashMap<String, String>();
		CEntry entryToSrv ;
		if(isbn.isEmpty())
			throw new IOException("Book ISBN required!");
		else if(author.isEmpty())
			throw new IOException("Review author required!");
		else if(curr_title.isEmpty())
			throw new IOException("Current review title required!");
		else if(review.isEmpty())
			throw new IOException("Please delete review via <html><u><b>Delete Review</b></u></html> option!");
		else
		{
			upReview.put("isbn",isbn);
			upReview.put("author",author);
			upReview.put("title",title);
			upReview.put("currenttitle",curr_title);
			upReview.put("review",review);
			entryToSrv = new CEntry("EditReview", upReview, this.getUserName(), this.getUserSessionId() );
			CClientConnector.getInstance().messageToServer(entryToSrv);
		}
	}
	
	
	public void updateBookDetails(String curr_isbn, String title, String author, String isbn, String release, String publisher, String summary, String price, String topic, String lable, String TOC, boolean invis, String lang) throws Exception
	{
		CEntry entryToSrv ;
		HashMap<String, String> newBookDetails = new HashMap<String, String>();
		String visible = "true";
		Pattern pd = Pattern.compile("(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})");
		Matcher md = pd.matcher(release);
		if(!md.matches()){
			throw new IOException("Invalid Date format!");
		}
		if(  !invis      &&       this.getPrivilege() != EActor.LibraryManager  )
		{
			throw new IOException("You have no permition to edit book visibility!");
			
		}
		else if ( this.getPrivilege() == EActor.LibraryManager  && invis == false)
			          visible = "false";
	    if(curr_isbn.isEmpty())
			System.out.println("Error: Current ISBN not resived!");
		else if(isbn.isEmpty())
			throw new IOException("Book ISBN is a must!");
		else if(author.isEmpty())
			throw new IOException("Book Author is a must!");
		else if(title.isEmpty())
			throw new IOException("Book Title is a must!");
		else if(publisher.isEmpty())
			throw new IOException("Book Publisher is a must!");
		else if(topic.isEmpty())
			throw new IOException("Book Topic is a must!");
		else if(lang.isEmpty())
			throw new IOException("Book Language is a must!");
		else if(price.isEmpty())
			throw new IOException("Book Price is a must!");
		else
		{
			newBookDetails.put("isbn", isbn);
			newBookDetails.put("author", author);
			newBookDetails.put("title", title);
			newBookDetails.put("release", release);
			newBookDetails.put("publisher", publisher);
			newBookDetails.put("summary", summary);
			newBookDetails.put("price", price);
			newBookDetails.put("topic", topic);
			newBookDetails.put("lables", lable);
			newBookDetails.put("toc", TOC);
			newBookDetails.put("invisible", visible);
			newBookDetails.put("languages", lang);
			newBookDetails.put("bookisbn",curr_isbn);
			entryToSrv = new CEntry("EditBook",newBookDetails, this.getUserName(),this.getUserSessionId());
			CClientConnector.getInstance().messageToServer(entryToSrv);
		}
	}
	
	
	public void deleteReview(String title, String author, String isbn) throws Exception
	{
		CEntry entryToSrv ;
		Map<String, String> delReview = new HashMap<String, String>();
		if(title.isEmpty() || author.isEmpty() || isbn.isEmpty())
			throw new IOException("Not enough information to perform the action!");
		else
		{
			delReview.put("title", title);
			delReview.put("author", author);
			delReview.put("isbn", isbn);
			entryToSrv = new CEntry("deletereview",delReview, this.getUserName(),this.getUserSessionId());
			CClientConnector.getInstance().messageToServer(entryToSrv);	
		}
	}
	
	
	
}