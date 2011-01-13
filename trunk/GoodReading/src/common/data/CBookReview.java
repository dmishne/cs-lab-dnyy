package common.data;

import java.io.Serializable;
/**
 * Data type, holding attributes for a review made by user.
 */
public class CBookReview implements Serializable
{
	
	 private static final long serialVersionUID = 1L;
	 /** isbn = International Standard Book Number. our key for CBook*/
	 private String m_isbn;
	 /** author - the username of the author of this specific Review*/
	 private String m_author;
	 /** title made for this review*/
	 private String m_title;
	 /** context of the review*/
	 private String m_review;
	 /** date - when review was submitted*/
	 private String m_write_date;
	 /** review must be Accepted before published. default value is 0 - unhandled, 1 = approved */
	 private int m_accepted; 
	 /** when review was approved / eddited*/
	 private String m_checkout_date;
	 /** the Editor / Librarian's username */
	 private String m_auth_by;
	 /** name of the book reviewed, here for GUI's purposes.*/
	 private String m_bookName;

	 /**
	  * simple constructor - factory-by value based.
	  * @param isbn
	  * @param author
	  * @param title
	  * @param review
	  * @param writedate
	  * @param accepted
	  * @param Checkdate
	  * @param authUser
	  * @param bookname
	  */
	 public CBookReview(String isbn,String author,String title,String review,String writedate,int accepted,String Checkdate,String authUser, String bookname)
	 {
		 m_isbn=isbn;
		 m_author=author;
		 m_title=title;
		 m_review=review;
		 m_write_date=writedate;
		 m_accepted=accepted;
		 m_checkout_date=Checkdate;
		 m_auth_by=authUser;
		 m_bookName = bookname;
	 }
	 
	/**
	 * @return ISBN
	 */
	public String getisbn() {
		return m_isbn;
	} 
	/**
	 * @return author
	 */
	public String getauthor() {
		return m_author;
	}
	/**
	 * @return authorizer
	 */
	public String getauth_by() {
		return m_auth_by;
	}

	/**
	 * @return book name
	 */
	public String getBookName() {
		return m_bookName;
	}

	/**
	 * simple setter for title
	 */
	public void settitle(String m_title) {
		this.m_title = m_title;
	}

	/**
	 * @return title
	 */
	public String gettitle() {
		return m_title;
	}
	/**
	 * simple setter for context
	 */
	public void setreview(String m_review) {
		this.m_review = m_review;
	}

	/**
	 * @return review's context
	 */
	public String getreview() {
		return m_review;
	}

	/**
	 * simple setter for write date
	 */
	public void setwrite_date(String m_write_date) {
		this.m_write_date = m_write_date;
	}
	/**
	 * @return write date
	 */
	public String getwrite_date() {
		return m_write_date;
	}
	/**
	 * simple setter for acceptance of review
	 */
	public void setaccepted(int m_accepted) {
		this.m_accepted = m_accepted;
	}
	/**
	 * @return acceptance of review
	 */
	public int getaccepted() {
		return m_accepted;
	}

	/**
	 *  simple setter for checkout-date
	 */
	public void setcheckout_date(String m_checkout_date) {
		this.m_checkout_date = m_checkout_date;
	}
	/**
	 * @return checkout date
	 */
	public String getcheckout_date() {
		return m_checkout_date;
	}
	/**
	 * simple setter for authorizer
	 */
	public void setauth_by(String m_auth_by) {
		this.m_auth_by = m_auth_by;
	}

	/**
	 * simple setter for book name
	 */	
	public void setBookName(String m_bookName) {
		this.m_bookName = m_bookName;
	}
	/**
	 * @return normal write date (for GUI / display, date is saved as a string in this format: YYYY-MM-DD)
	 */	
	public String getNormalWriteDate()
	{
	       String date = m_write_date.substring(8, 10)+"-"+m_write_date.substring(5, 7)+"-"+m_write_date.substring(0, 4);
	   return date;
	}
	 
} //end of class
