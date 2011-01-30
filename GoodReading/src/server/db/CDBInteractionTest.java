package server.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


/**
 *  This is the test suite for the book views statistics.
 * do note that this test "throws" exceptions.
 * these exceptions are actually caught in DBIG (or CDBInteractionGenerator) 
 * as a part of the functunality of getBookViews()
 * SO - expect data flowing via console.
 * 
 * Just one more thing, Default values for the SQL connection are stored in CServerConstants.
 * Since nobody's initializing it, no need to go to config.properties to change values, password and username can be changed hardcoded into the holders. 
 */
public class CDBInteractionTest extends TestCase 
{
	/** a map to help us with extra definitions of vars. */
	Map<String,Integer> bookViews = new HashMap<String,Integer>();
	
	@Before
	public void setUp() throws Exception 
	{
		//set up initialization
	CDBInteractionGenerator.GetInstance().deleteBook("1234");
	}

	/** first test is a simple case of checking what happens when the books isn't the the DB (from setUp) */
	@Test
	public void testGetBookViewsWithoutBook() {
		Assert.assertTrue(CDBInteractionGenerator.GetInstance().getBookViews("1234", "2011").isEmpty());
	}
	
	/** this time we'll be checking what happens when we give the function a bad year (-1), what actually happens is that an exception is thrown in DBIG, and it returns an empty list */
	public void testGetBookViewsWithBadYear() {
		CDBInteractionGenerator.GetInstance().insertNewBook("1234", "try title", "me me!", "2010-11-11", "publisher", "a story about a boy..", new Double(7.10), 1, 1, "", "arg", "1...2..", false, "english");
		Assert.assertTrue(CDBInteractionGenerator.GetInstance().getBookViews("1234", "-1").isEmpty());
	}

	/** the next test is for nulls, we check what happens when we give the function nulls, first is hte isbn, then we give it a valid isbn and null year, then both as null. do note there's currently a book in DB with isbn 1234 - was added in previous test*/
	public void testGetBookViewsWithNulls() {
		Assert.assertTrue(CDBInteractionGenerator.GetInstance().getBookViews(null, "2000").isEmpty());
		Assert.assertTrue(CDBInteractionGenerator.GetInstance().getBookViews("1234", null).isEmpty());
		Assert.assertTrue(CDBInteractionGenerator.GetInstance().getBookViews(null, null).isEmpty());
	}

	/**
	 * this is the most complex test in this suite, first we delete the book from DB.
	 * next we re-insert it (previous data / views / sales are deleted because of the on-delete-cascade)
	 * now we simply check the views stats is empty, this is an extra check.
	 * pending we'll insert a view by a test user, and check the view is there.
	 * and last but not least, we check for irrelevant data, that all other rows are empty, so we remove "this" month's views,and check to see
	 * that the list is empty
	 */
	public void testGetBookViews() {
		CDBInteractionGenerator.GetInstance().deleteBook("1234");
		CDBInteractionGenerator.GetInstance().insertNewBook("1234", "try title", "me me!", "2010-11-11", "publisher", "a story about a boy..", new Double(7.10), 1, 1, "", "arg", "1...2..", true, "english");
		bookViews = CDBInteractionGenerator.GetInstance().getBookViews("1234", "2011");
		Assert.assertTrue(bookViews.isEmpty());
		
		
		CDBInteractionGenerator.GetInstance().StatisticsAddView("1234", "test2");
		bookViews = CDBInteractionGenerator.GetInstance().getBookViews("1234", "2011");
		Assert.assertTrue(bookViews.containsKey(String.valueOf(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()))));
		
		bookViews.remove(String.valueOf(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime())));
		Assert.assertTrue(bookViews.isEmpty());
	}
}
