package client.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import client.common.CClientConnector;
import common.api.CEntry;
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
	
	
	public String addNewBook(String title, String author, String isbn, String release, String publisher, String summary, String price, String topic, String subtopic, String lable, String TOC, boolean vis, String lang, String[] fileType) throws IOException, Exception
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
		else if(topic.isEmpty())
			throw new IOException("Book Topic is a must!");
		else if(subtopic.isEmpty())
			throw new IOException("Book Subtopic is a must!");
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
			newBook.put("topic", topic);
			newBook.put("subtopic", subtopic);
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
	
	
	public LinkedList<CBookReview> searchNewReviews() throws Exception
	{		
		HashMap<String, String> empty = new HashMap<String, String>();
		CEntry entryToSrv = new CEntry("GetUnhandledReviews", empty, this.getUserName(), this.getUserSessionId() );
		Object temp = CClientConnector.getInstance().messageToServer(entryToSrv);
		@SuppressWarnings("unchecked")
		LinkedList<CBookReview> booksReview = (LinkedList<CBookReview>) temp;
		return booksReview;
	}
	
	public void updateReview(String isbn, String author, String title, String curr_title, String review, boolean confirm) throws Exception
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
	
	
	protected boolean isValidDate(String inDate) {

	    if (inDate == null)
	      return false;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	    
	    if (inDate.trim().length() != dateFormat.toPattern().length())
	      return false;
	    dateFormat.setLenient(false);	    
	    try {
	      dateFormat.parse(inDate.trim());
	    }
	    catch (ParseException pe) {
	      return false;
	    }
	    return true;
	  }
	
	
	public int isMessages() throws Exception
	{
		CEntry entryToSrv ;
		Map<String, String> temp = new HashMap<String, String>();
		entryToSrv = new CEntry("CountMessages", temp , this.getUserName(), this.getUserSessionId());
		Integer Count = (Integer)CClientConnector.getInstance().messageToServer(entryToSrv);
		return Count.intValue();
	}
}
