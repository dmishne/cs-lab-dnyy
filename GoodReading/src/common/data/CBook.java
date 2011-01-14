package common.data;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CBook implements Serializable,Comparable<CBook> {
	
	static private final long serialVersionUID = 1L;
	/** title of the book */
	private String	 m_title;
	/** author of the book */
	private String	 m_author;
	/** International Standard Book Number  - the key for this class */
	private String	 m_ISBN;
	/** release date, stored in SQL format - 2010-1-13*/
	private String	 m_release;   
	/** publisher of the book */
	private String	 m_publisher;
	/** summary of the book */
	private String	 m_summary;
	/** price of the book */
	private double	 m_price;
	/** sum score of the book */
	private double	 m_score=0;
	/** counter for how many rated the book*/
	private long 	 m_score_count=0;
	/** related topics of the book */
	private String	 m_topic;
	/** rank / popularity of the book (all times) */
	private int		 m_rank;
	/** related lables of the book */
	private String	 m_lables;
	/** Table of Context*/
	private String	 m_TOC;
	/** determines if book is visible for Readers */
	private boolean	 m_invisible;
	/** language of the book */
	private String	 m_language;
	
	/**
	 * constructor - implemented in by-value-factory
	 * @param m_ISBN
	 * @param m_author
	 * @param m_title
	 * @param m_release
	 * @param m_publisher
	 * @param m_summary
	 * @param m_price
	 * @param m_score_count
	 * @param m_score
	 * @param m_topic
	 * @param m_lables
	 * @param m_TOC
	 * @param m_invisible
	 * @param m_language
	 * @param Rank
	 */
	public CBook(String m_ISBN, String m_author, String m_title, String m_release, String m_publisher, String m_summary,double m_price, long m_score_count,double m_score,String m_topic, String m_lables, String m_TOC,boolean m_invisible, String m_language,int Rank) 
	{   
		this.m_title = m_title;
		this.m_author = m_author;
		this.m_ISBN = m_ISBN;
		this.m_release = m_release;
		this.m_publisher = m_publisher;
		this.m_summary = m_summary;
		this.m_price = m_price;
		this.m_topic = m_topic;
		this.m_lables = m_lables;
		this.m_TOC = m_TOC;
		this.m_invisible = m_invisible;
		this.m_language = m_language;
		this.m_score_count=m_score_count;
		this.m_score= m_score;
		this.m_rank=Rank;
	}

	/**
	 * @return m_rank
	 */
	public int getM_rank()
	{
		return m_rank;
	}
	/**
	 * setter for book's rank (locally) 
	 * @param r new value for m_rank
	 */
	public void setM_rank(int r)
	{
		m_rank=r;
	}
	
	/**
	 * @return the m_title
	 */
	public String getM_title() {
		return m_title;
	}
	
	/**
	 * @return the m_author
	 */
	public String getM_author() {
		return m_author;
	}
	
	/**
	 * @return the m_ISBN
	 */
	public String getM_ISBN() {
		return m_ISBN;
	}
	
	/**
	 * @return the m_release_date
	 */
	public String getM_release_date() {
		Pattern pd = Pattern.compile("(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Punct})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})+(\\p{Digit})");
		Matcher md = pd.matcher(m_release);
		if(!md.matches()){
			String date = m_release.substring(8, 10)+"-"+m_release.substring(5, 7)+"-"+m_release.substring(0, 4);
			return date;
		}
		return m_release;
	}
	
	/**
	 * @return the m_publisher
	 */
	public String getM_publisher() {
		return m_publisher;
	}
	
	/**
	 * @return the m_summary
	 */
	public String getM_summary() {
		return m_summary;
	}
	
	/**
	 * @return the m_price
	 */
	public double getM_price() {
		return m_price;
	}
	
	/**
	 * @return the m_score
	 */
	public double getM_score() {
		return m_score;
	}
	
	/**
	 * @return the m_score_count
	 */
	public long getM_score_count() {
		return m_score_count;
	}
	
	/**
	 * @return the m_topic
	 */
	public String getM_topic() {
		return m_topic;
	}
	
	/**
	 * @return the m_lables
	 */
	public String getM_lables() {
		return m_lables;
	}
	
	/**
	 * @return the m_TOC
	 */
	public String getM_TOC() {
		return m_TOC;
	}
	
	/**
	 * @return the m_invisible
	 */
	public boolean getM_invisible() {
		return m_invisible;
	}
	
	/**
	 * @return the m_language
	 */
	public String getM_language() {
		return m_language;
	}

	/**
	 * as score is summed up by raters, we take care of the extreme (though short) case in which nobody has yet to rate this book
	 * @return m_score
	 */
	public int getScore() {
		if(this.m_score_count != 0)
		{
			return (int) ((int) this.m_score/this.m_score_count);
		}
		return 0;
	}
	/**
	 * we summed the score up, and now we need to convert it to 5 stars values.
	 * @return Score AVG.
	 */
	public double getAvgScore() {
		return (double) this.m_score/this.m_score_count;
	}
	/**
	 * simple setter for m_title
	 */
	public void settitle(String a) {
		m_title=a;
	}
	/**
	 * simple setter for m_author
	 */	
	public void setauthor(String a) {
		m_author=a;
	}
	/**
	 * simple setter for m_release
	 */
	public void setrelease_date(String a) {
		 m_release=a;
	}
	/**
	 * simple setter for m_publisher
	 */
	public void setpublisher(String a) {
		 m_publisher=a;
	}
	/**
	 * simple setter for m_summary
	 */
	public void setsummary(String a) {
		 m_summary=a;
	}
	/**
	 * simple setter for m_price
	 */
	public void setprice(double a) {
		 m_price=a;
	}
	/**
	 * simple setter for m_topic
	 */
	public void settopic(String a) {
		 m_topic=a;
	}
	/**
	 * simple setter for m_lables
	 */
	public void setlables(String a) {
		 m_lables=a;
	}
	/**
	 * simple setter for m_TOC
	 */
	public void setTOC(String a) {
		 m_TOC=a;
	}
	/**
	 * simple setter for m_invisible
	 */
	public void setinvisible(boolean a) {
		 m_invisible=a;
	}
	/**
	 * simple setter for m_language
	 */
	public void setlanguage(String a) {
		 m_language=a;
	}
	
	/**
	 *	implements the Comparable interface
	 *	this function is only supposed to compare between CBooks, not anything else! 
	 */
	public int compareTo(CBook b) 
	{
		if(b.equals(this))
			return 0;		
		if(m_rank > b.m_rank)
		{
			return 1;
		}
		if(m_rank < b.m_rank)
		{
			return -1;
		}
		return m_ISBN.compareTo(b.m_ISBN);
	}
}
