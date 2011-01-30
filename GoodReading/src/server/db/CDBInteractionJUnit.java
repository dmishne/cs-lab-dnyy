package server.db;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import common.data.CBook;

public class CDBInteractionJUnit extends TestCase {

	
	CBook goodBook;
	Map<String,String> bookDetails = new HashMap<String,String>();
	Map<String,Integer> bookViews = new HashMap<String,Integer>();
	LinkedList<CBook> bookList = new LinkedList<CBook>();
	
	@Before
	public void setUp() throws Exception {
		bookDetails.put("isbn", "0763620491");
		bookList = CDBInteractionGenerator.GetInstance().SearchBook(bookDetails);
		goodBook = bookList.getFirst();
	}

	@Test
	
	public void testGetBookViewsWithoutBook() {
		Assert.assertTrue(CDBInteractionGenerator.GetInstance().getBookViews("1234", "2010").isEmpty());
	}
	
	public void testGetBookViewsWithBadYear() {
		Assert.assertTrue(CDBInteractionGenerator.GetInstance().getBookViews("0763620491", "-1").isEmpty());
	}

	public void testGetBookViewsWithNulls() {
		Assert.assertTrue(CDBInteractionGenerator.GetInstance().getBookViews(null, "0").isEmpty());
	}
	
	public void testGetBookViews() {
		goodBook = CDBInteractionGenerator.GetInstance().SearchBook(bookDetails).getFirst();
		CDBInteractionGenerator.GetInstance().deleteBook(goodBook.getM_ISBN());
		CDBInteractionGenerator.GetInstance().insertNewBook(goodBook.getM_ISBN(), goodBook.getM_title(), goodBook.getM_author(), goodBook.getM_release_date(), goodBook.getM_publisher(), goodBook.getM_summary(), goodBook.getM_price(), (int)goodBook.getM_score(), (int)goodBook.getM_score_count(), "", goodBook.getM_lables(), goodBook.getM_TOC(), goodBook.getM_invisible(), goodBook.getM_language());
		bookViews = CDBInteractionGenerator.GetInstance().getBookViews(goodBook.getM_ISBN(), "2011");
		Assert.assertTrue(bookViews.isEmpty());
		CDBInteractionGenerator.GetInstance().StatisticsAddView(goodBook.getM_ISBN(), "test2");
		bookViews = CDBInteractionGenerator.GetInstance().getBookViews(goodBook.getM_ISBN(), "2011");
		Assert.assertTrue(bookViews.containsKey(String.valueOf(Calendar.MONTH)));
		Assert.assertFalse(bookViews.containsKey(String.valueOf(Calendar.MONTH+1)));
	}
}
