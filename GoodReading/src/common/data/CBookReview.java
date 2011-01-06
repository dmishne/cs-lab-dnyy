package common.data;

import java.io.Serializable;

public class CBookReview implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private String m_isbn;
	 private String m_author;
	 private String m_title;
	 private String m_review;
	 private String m_write_date;
	 private int m_accepted; //values -1 rejected, 0 unhandled, 1 approved
	 private String m_checkout_date;
	 private String m_auth_by;
	 private String m_bookName;

		//no setter for ISBN or author! (keys of review at DB)
	 
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
	 
	 
	public String getisbn() {
		return m_isbn;
	} 

	public String getauthor() {
		return m_author;
	}
	
	public String getauth_by() {
		return m_auth_by;
	}


	
	public String getBookName() {
		return m_bookName;
	}





	public void settitle(String m_title) {
		this.m_title = m_title;
	}


	public String gettitle() {
		return m_title;
	}

	public void setreview(String m_review) {
		this.m_review = m_review;
	}


	public String getreview() {
		return m_review;
	}


	public void setwrite_date(String m_write_date) {
		this.m_write_date = m_write_date;
	}

	public String getwrite_date() {
		return m_write_date;
	}

	public void setaccepted(int m_accepted) {
		this.m_accepted = m_accepted;
	}

	public int getaccepted() {
		return m_accepted;
	}

	public void setcheckout_date(String m_checkout_date) {
		this.m_checkout_date = m_checkout_date;
	}

	public String getcheckout_date() {
		return m_checkout_date;
	}

	public void setauth_by(String m_auth_by) {
		this.m_auth_by = m_auth_by;
	}

	
	public void setBookName(String m_bookName) {
		this.m_bookName = m_bookName;
	}
	 
} //end of class
