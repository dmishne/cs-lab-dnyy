package client.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.common.CClientConnector;

import common.api.CEntry;
import common.data.CBookReview;

public class CLibrarian extends AUser{

	/**
	 * CLibrarian Is A AUser subclass.
	 * Provide the AUser instance while privilege is Librarian.
	 * Holds compatible methods for the privilege.
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor for CLibrarian.
	 * Initialize user instance of type Librarian. 
	 */
	public CLibrarian(String FirstName, String LastName, int UserId, String UserName, int SessionID)
	{
		super(FirstName,LastName,UserId,UserName, EActor.Librarian, SessionID);
	}
	
	/**
	 * Request server to add new book to DB with given parameters.
	 * Visibility of book is unchangeable for Librarian privilege.
	 * 
	 * @param title            the title of new book
	 * @param author           the author of new book
	 * @param isbn             the isbn of new book
	 * @param release          the release date of new book
	 * @param publisher        the publisher of new book
	 * @param summary          the summary for book
	 * @param price            the price of new book
	 * @param topics           the topics and subtopics this book associated with 
	 * Defined as (~) before topic, (@) between topic and subtopic, (,) between subtopics
	 * @param lable            the labels for book
	 * @param TOC              the Table of content in the book
	 * @param vis              the boolean visibility in catalog, true for visible, false for invisible 
	 * @param lang             the language of new book
	 * @param fileType         the types of book files affordable for order
	 * @return                 string answer from server
	 * @throws IOException     one or more "must have" parameters not recived 
	 * @throws Exception       request to server fail
	 */
	public String addNewBook(String title, String author, String isbn, String release, String publisher, String summary, String price, String topics, String lable, String TOC, boolean vis, String lang, String[] fileType) throws IOException, Exception
	{
		CEntry entryToSrv;
		HashMap<String, String> newBook = new HashMap<String, String>();
		String invisible = "false";
		String format = "";
		String answer;
		// check date
		Pattern pd = Pattern.compile("(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})");
		Matcher md = pd.matcher(release);
		if(!md.matches()){
			throw new IOException("Invalid Date format!");
		}
		String date = release.substring(6, 10)+"-"+release.substring(3, 5)+"-"+release.substring(0, 2);
		if(  !vis      &&       this.getPrivilege() != EActor.LibraryManager  )
		{
			throw new IOException("You have no permition to edit book visibility!");
			
		}
		else if ( this.getPrivilege() == EActor.LibraryManager  && vis == false)
			          invisible = "true"; 
		if(isbn.isEmpty())
			throw new IOException("Book ISBN is a must!");
		else if(author.isEmpty())
			throw new IOException("Book Author is a must!");
		else if(title.isEmpty())
			throw new IOException("Book Title is a must!");
		else if(publisher.isEmpty())
			throw new IOException("Book Publisher is a must!");
		else if(topics.isEmpty())
			throw new IOException("Book Topic is a must!");
		else if(lang.isEmpty())
			throw new IOException("Book Language is a must!");
		else if(price.isEmpty())
			throw new IOException("Book Price is a must!");
		else if(!isValidDate(date))
			throw new IOException("Wrong date!");
		else if(fileType.length < 1)
			throw new IOException("Book's File Type is a must!");
		else if (fileType.length > 0)
		{
			format = "";
			for(int i = 0; i< fileType.length ; i++)
			{
				if(fileType[i] != null)
					if(format.equals(""))
						format = format + fileType[i];				
					else
						format = format + "," + fileType[i];
			}
			
			newBook.put("isbn", isbn);
			newBook.put("author", author);
			newBook.put("title", title);
			newBook.put("release", date);
			newBook.put("publisher", publisher);
			newBook.put("summary", summary);
			newBook.put("price", price);
			newBook.put("topic", topics);
			newBook.put("lables", lable);
			newBook.put("toc", TOC);
			newBook.put("invisible", invisible);
			newBook.put("languages", lang);
			newBook.put("format", format);
			entryToSrv = new CEntry("AddBook", newBook, this.getUserName(),this.getUserSessionId());
			Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
			if(ans instanceof String)
			{
			       answer = ans.toString(); 
			       return answer;
			}
		}
		return "Fail";
	}
	
	/**
	 * Request server to search reviews that not confirmed yet.
	 * 
	 * @return               LinkedList with CBookReview class contains review information
	 * @throws Exception     request to server fail
	 */
	public LinkedList<CBookReview> searchNewReviews() throws Exception
	{		
		HashMap<String, String> empty = new HashMap<String, String>();
		CEntry entryToSrv = new CEntry("GetUnhandledReviews", empty, this.getUserName(), this.getUserSessionId() );
		Object temp = CClientConnector.getInstance().messageToServer(entryToSrv);
		@SuppressWarnings("unchecked")
		LinkedList<CBookReview> booksReview = (LinkedList<CBookReview>) temp;
		return booksReview;
	}
	
	/**
	 * Request server to update review fields in DB with given parameters.
	 * 
	 * @param isbn          isbn number of reviewed book
	 * @param author        author of the review
	 * @param title         title of the review
	 * @param curr_title    currently known review title
	 * @param review        the review it self
	 * @param confirm       boolean cofirmation, true for confirmed, false for not confirmed 
	 * @throws IOException  one or more "must have" parameters not recived
	 * @throws Exception    request to server fail
	 */
	public void updateReview(String isbn, String author, String title, String curr_title, String review, boolean confirm) throws Exception,IOException
	{
		HashMap<String, String> upReview = new HashMap<String, String>();
		CEntry entryToSrv ;
		
	    if(isbn.isEmpty())
			throw new IOException("Error: Book ISBN not found!");
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
			if(confirm)
				upReview.put("confirm","true");
			else if(!confirm)
				upReview.put("confirm","false");
			entryToSrv = new CEntry("EditReview", upReview, this.getUserName(), this.getUserSessionId() );
			CClientConnector.getInstance().messageToServer(entryToSrv);
		}
	}
	
	/**
	 * 
	 * Request server to update book fields in DB with given parameters.
	 * Visibility of book is unchangeable for Librarian privilege.
	 * Book isbn unchangeable.
	 * 
	 * @param isbn             the isbn of book
	 * @param title            the new title of book
	 * @param author           the new author of book
	 * @param release          the new release date of book
	 * @param publisher        the new publisher of book
	 * @param summary          the new summary for book
	 * @param price            the new price of book
	 * @param topic            the new topics and subtopics this book associated with, defined as (~) before topic, (@) between topic and subtopic, (,) between subtopics
	 * @param lable            the new labels for book
	 * @param TOC              the new Table of content in the book
	 * @param vis              the boolean visibility in catalog, true for visible, false for invisible 
	 * @param lang             the new language of new book
	 * @param fileType         the new types of book files affordable for order
	 * @return                 string answer from server
	 * @throws Exception       request to server fail
	 */
	public String updateBookDetails(String isbn, String title, String author, String release, String publisher, String summary, String price, String topic, String lable, String TOC, boolean vis, String lang, String[] fileType) throws Exception
	{
		CEntry entryToSrv ;
		HashMap<String, String> newBookDetails = new HashMap<String, String>();
		String invisible = "false";
		String format = "";
		String answer;
		Pattern pd = Pattern.compile("(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})");
		Matcher md = pd.matcher(release);
		if(!md.matches()){
			throw new IOException("Invalid Date format!");
		}
		String date = release.substring(6, 10)+"-"+release.substring(3, 5)+"-"+release.substring(0, 2);
		if(  !vis      &&       this.getPrivilege() != EActor.LibraryManager  )
		{
			throw new IOException("You have no permition to edit book visibility!");
			
		}
		else if ( this.getPrivilege() == EActor.LibraryManager  && vis == false)
			          invisible = "true";
	    if(isbn.isEmpty())
			System.out.println("Error: ISBN not resived!");
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
		else if(!isValidDate(date))  
			throw new IOException("Wrong date!");
		else if(fileType.length < 1)
			throw new IOException("Book's File Type is a must!");
		else if (fileType.length > 0)
		{
			format = "";
			for(int i = 0; i< fileType.length ; i++)
			{
				if(fileType[i] != null)
					if(format.equals(""))
						format = format + fileType[i];				
					else
						format = format + "," + fileType[i];
			}
			newBookDetails.put("isbn", isbn);
			newBookDetails.put("author", author);
			newBookDetails.put("title", title);
			newBookDetails.put("release", date);
			newBookDetails.put("publisher", publisher);
			newBookDetails.put("summary", summary);
			newBookDetails.put("price", price);
			newBookDetails.put("topic", topic);
			newBookDetails.put("lables", lable);
			newBookDetails.put("toc", TOC);
			newBookDetails.put("invisible", invisible);
			newBookDetails.put("languages", lang);
			newBookDetails.put("format", format);
			entryToSrv = new CEntry("EditBook",newBookDetails, this.getUserName(),this.getUserSessionId());
			Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
			if(ans instanceof String)
			{
			       answer = ans.toString(); 
			       return answer;
			}
		}
		return "Fail";
	}
	
	/**
	 * Requesting server to delete the specific review given by parameters
	 * 
	 * @param title            review title
	 * @param author           review author
	 * @param isbn             reviewed book isbn number
	 * @return                 string answer from server
	 * @throws Exception       request to server fail
	
	 */
	public String deleteReview( String title, String author, String isbn) throws Exception
	{
		CEntry entryToSrv ;
		String answer;
		Map<String, String> delReview = new HashMap<String, String>();
		
	    if(title.isEmpty() || author.isEmpty() || isbn.isEmpty())
			throw new IOException("Not enough information to perform the action!");
		else
		{
			
			delReview.put("title", title);
			delReview.put("author", author);
			delReview.put("isbn", isbn);
			entryToSrv = new CEntry("DeleteReview",delReview, this.getUserName(),this.getUserSessionId());
			Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
			if(ans instanceof String)
			{
			       answer = ans.toString(); 
			       return answer;
			}
		}
	    return "Fail";
	}
	
	/**
	 * Requesting server to delete the specific book given by isbn number
	 * 
	 * @param isbn             books isbn
	 * @return                 string answer from server
	 * @throws Exception       request to server fail
	 */
	public String deleteBook(String isbn) throws Exception
	{
		CEntry entryToSrv ;
		Map<String, String> delBook = new HashMap<String, String>();
		String answer;
		if(isbn.isEmpty())
			throw new IOException("Book ISBN is a must!");		
			delBook.put("isbn", isbn);
			entryToSrv = new CEntry("DeleteBook", delBook, this.getUserName(), this.getUserSessionId());
			Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
			if(ans instanceof String)
			{
			       answer = ans.toString(); 
			       return answer;
			}
	    return "Fail";
	}
	
	/**
	 * Return the current unconfirmed review count
	 * 
	 * @return            unconfirmed review count
	 * @throws Exception  request to server fail
	 */
	public int isMessages() throws Exception
	{
		CEntry entryToSrv ;
		Map<String, String> temp = new HashMap<String, String>();
		entryToSrv = new CEntry("CountMessages", temp , this.getUserName(), this.getUserSessionId());
		Integer Count = (Integer)CClientConnector.getInstance().messageToServer(entryToSrv);
		return Count.intValue();
	}
	
	/**
	 * Requesting server to add new topic to DB
	 * 
	 * @param topic        the new topic to add
	 * @return             string answer from server
	 * @throws Exception   request to server fail
	 */
	public String addTopic(String topic) throws Exception
	{
		CEntry entryToSrv ;
		String answer;
		Map<String, String> topics = new HashMap<String, String>();
		topics.put("topic", topic);
		entryToSrv = new CEntry("AddTopic", topics , this.getUserName(), this.getUserSessionId());
		Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
		if(ans instanceof String)
		{
			answer = ans.toString();
			return answer;
		}
		return "Fail";
	}
	
	/**
	 * Requesting server to add new subtopic to DB
	 * 
	 * @param topic         the topic with which new subtopic associates
	 * @param subtopic      the new subtopic
	 * @return              string answer from server
	 * @throws Exception    request to server fail
	 */
	public String addSubTopic(String topic,String subtopic) throws Exception
	{
		CEntry entryToSrv ;
		String answer;
		Map<String, String> subtopics = new HashMap<String, String>();
		subtopics.put("topic", topic);
		subtopics.put("subtopic", subtopic);
		entryToSrv = new CEntry("AddSubtopic", subtopics , this.getUserName(), this.getUserSessionId());
		Object ans = CClientConnector.getInstance().messageToServer(entryToSrv);
		if(ans instanceof String)
		{
			answer = ans.toString();
			return answer;
		}
		return "Fail";
	}
	
	
}
