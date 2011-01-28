package fixtures;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import server.main.CGoodReadingServer;
import common.data.CBook;
import client.core.AUser;
import fit.ActionFixture;



public class SearchBookAccTest extends ActionFixture{

	private HashMap<String,String> m_searchDetails = null;
	private AUser user;
	private LinkedList<CBook> books = null;
	private String title = null;
	private String author = null;
	
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}


	public void startSearch()
	{
		CGoodReadingServer.main(null);
		try {
			AUser.login("evgeny", "123");
			user = AUser.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setBookFieldsEmpty()
	{
		m_searchDetails = new HashMap<String,String>();
		try {
			m_searchDetails.put("title","");
			m_searchDetails.put("author","");
			m_searchDetails.put("language","");
			m_searchDetails.put("topic","");
			m_searchDetails.put("summary","");
			m_searchDetails.put("toc","");
			m_searchDetails.put("labels","");
		    m_searchDetails.put("toggle", "false");   
		    m_searchDetails.put("subtopic","");
			books = user.searchBook(m_searchDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void searchBookByTitle()
	{		
		try {
			//m_searchDetails.put("title", "hitchhiker's");
			m_searchDetails.put("title", title);
			m_searchDetails.put("author","");
			m_searchDetails.put("language","");
			m_searchDetails.put("topic","");
			m_searchDetails.put("summary","");
			m_searchDetails.put("toc","");
			m_searchDetails.put("labels","");
		    m_searchDetails.put("toggle", "false");   
		    m_searchDetails.put("subtopic","");
			books = user.searchBook(m_searchDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void searchBookByTitleAndAuthor()
	{
		//m_searchDetails.put("title", "hitchhiker's");
		//m_searchDetails.put("author", "Lea Goldberg");
		m_searchDetails.put("title", title);
		m_searchDetails.put("author", author);
		m_searchDetails.put("language","");
		m_searchDetails.put("topic","");
		m_searchDetails.put("summary","");
		m_searchDetails.put("toc","");
		m_searchDetails.put("labels","");
	    m_searchDetails.put("toggle", "false");   
	    m_searchDetails.put("subtopic","");
		try {
			books = user.searchBook(m_searchDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void searchBookByTitleOrAuthor()
	{
		//m_searchDetails.put("title", "hitchhiker's");
		//m_searchDetails.put("author", "Lea Goldberg");
		m_searchDetails.put("title", title);
		m_searchDetails.put("author", author);
		m_searchDetails.put("language","");
		m_searchDetails.put("topic","");
		m_searchDetails.put("summary","");
		m_searchDetails.put("toc","");
		m_searchDetails.put("labels","");
	    m_searchDetails.put("toggle", "true");   
	    m_searchDetails.put("subtopic","");
		try {
			books = user.searchBook(m_searchDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void loginAsReader()
	{
		AUser.logout();
		try {
			AUser.login("test2", "123");
			user = AUser.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void searchBookEmpty()
	{
		m_searchDetails = new HashMap<String,String>();
		try {
			m_searchDetails.put("title","");
			m_searchDetails.put("author","");
			m_searchDetails.put("language","");
			m_searchDetails.put("topic","");
			m_searchDetails.put("summary","");
			m_searchDetails.put("toc","");
			m_searchDetails.put("labels","");
		    m_searchDetails.put("toggle", "false");   
		    m_searchDetails.put("subtopic","");
			books = user.searchBook(m_searchDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean resultCheckA()
	{
		if(books.size() == 6)
		   return true;
	  return false;
	}
	
	public boolean resultCheckB()
	{
		Iterator<CBook> it = books.iterator();
		while (it.hasNext())
		{
			if(!it.next().getM_title().contains(title))
				 return false;
		}
		if(books.size() != 2)
		   return false;
	 return true;
	}
	
	public boolean resultCheckC()
	{
		if(books.size() == 0)
		   return true;
	  return false;
	}
	
	public boolean resultCheckD()
	{
		Iterator<CBook> it = books.iterator();
		while(it.hasNext())
		{
			CBook curr = it.next();
			if(!(curr.getM_author().contains(author) || curr.getM_title().contains(title)))
				 return false;
		}
		if(books.size() != 3)
		   return false;
	  return true;
	}
	
	public boolean resultCheckE()
	{
		if(books.size() == 6)
		   return true;
	  return false;
	}
	
}
