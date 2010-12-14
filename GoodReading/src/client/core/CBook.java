package client.core;

import java.io.Serializable;
import java.util.*;

public class CBook implements Serializable {
	
	static private final long serialVersionUID = 1L;
	private String  m_title;
	private String  m_author;
	private String m_ISBN;
	private Date m_release_date;    // Warning : Date type used
	private String m_publisher;
	private String m_summary;
	private double m_price;
	private double m_score=0;
	private int m_score_count=0;
	private String m_topic;
	private String m_lables;
	private String m_TOC;
	private int m_invisible;
	private String m_language;
	private List <CBookReview> Reviews;    // Include list of CBookReview class
	
	
	
	
	
	
	/**
	 * @param m_title
	 * @param m_author
	 * @param m_ISBN
	 * @param m_release_date     (Date type)
	 * @param m_publisher
	 * @param m_summary
	 * @param m_price
	 * @param m_score
	 * @param m_score_count
	 * @param m_topic
	 * @param m_lables
	 * @param m_TOC
	 * @param m_invisible
	 * @param m_language
	 */
	public CBook(String m_title, String m_author, String m_ISBN,
		    String m_publisher, String m_summary,double m_price, String m_topic,
		    String m_lables, String m_TOC,int m_invisible, String m_language) 
	{   
		//  To Do : add date to constructor
		this.m_title = m_title;
		this.m_author = m_author;
		this.m_ISBN = m_ISBN;
		//this.m_release_date = m_release_date;
		this.m_publisher = m_publisher;
		this.m_summary = m_summary;
		this.m_price = m_price;
		this.m_topic = m_topic;
		this.m_lables = m_lables;
		this.m_TOC = m_TOC;
		this.m_invisible = m_invisible;
		this.m_language = m_language;
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
	public Date getM_release_date() {
		return m_release_date;
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
	public int getM_score_count() {
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
	public int getM_invisible() {
		return m_invisible;
	}
	
	/**
	 * @return the m_language
	 */
	public String getM_language() {
		return m_language;
	}



	/**
	 * @return "List<CBookReview>" with reviews
	 */
	public List<CBookReview> getReviews() {
		return Reviews;
	}
	
	/**
	 * @param m_score the m_score to set
	 */
	
	
	public void setM_score(double score) {
		double temp =(this.m_score*this.m_score_count);
		this.m_score_count++;
		this.m_score = (temp + score)/this.m_score_count;
	}



	/**
	 * @param add review to "List<CBookReview>"
	 */
	public void addReviews(CBookReview review) {
		Reviews.add(review);
	}
	
	
	/*// ??????????????????????
	public void deleteReview(int index)
	{
		Reviews.remove(index);
	}*/

	
	public void orderBook()
	{
		// TO DO
	}
	
	
}
