package client.core;

import java.io.Serializable;
import java.util.*;

public class CBookReview implements Serializable {
	
	
	static private final long serialVersionUID = 1L;
	static protected CBookReview m_BookReview = null;
	static private final Calendar cal = Calendar.getInstance();
	private String  m_Review;
	private String  m_reviewTitle;
	private String  m_reviewAuthor;
	private Date  m_datePosted = cal.getTime();;
	private int confirmation =0;
	
	
	/**
	 * @param m_Review
	 * @param m_reviewAuthor
	 */
	public CBookReview(String m_Review, String m_reviewAuthor, String m_reviewTitle) {
		this.m_Review = m_Review;
		this.m_reviewAuthor = m_reviewAuthor;
		this.m_reviewTitle = m_reviewTitle;
	}
	

    //          For permited users only
	//---------------------------------------------------
	/**
	 * @return the confirmation
	 */
	public int getConfirmation() {
		return confirmation;
	}


	/**
	 * @param confirmation the confirmation to set
	 */
	public void setConfirmation(int confirmation) {
		this.confirmation = confirmation;
	}


	/**
	 * @return the m_Review
	 */
	public String getM_Review() {
		return m_Review;
	}


	/**
	 * @return the m_reviewBook
	 */
	public String getM_reviewBook() {
		return m_reviewTitle;
	}


	/**
	 * @return the m_reviewAuthor
	 */
	public String getM_reviewAuthor() {
		return m_reviewAuthor;
	}


	/**
	 * @return the m_datePosted
	 */
	public Date getM_datePosted() {
		return m_datePosted;
	}
}

