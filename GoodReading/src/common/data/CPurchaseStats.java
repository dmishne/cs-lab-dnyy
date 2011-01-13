package common.data;

import java.io.Serializable;
/**
 * simple container class in order to pass results to client
 */
public class CPurchaseStats implements Serializable,Comparable<CPurchaseStats>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_isbn;
	private String m_title;
	private String m_month;
	
	public String getisbn() {
		return m_isbn;
	}
	public String gettitle() {
		return m_title;
	}
	public String getmonth() {
		return m_month;
	}
	public CPurchaseStats(String i,String t,String m)
	{
		m_isbn=i;
		m_title=t;
		m_month=m;
	}
	
	public String toString()
	{
		String temp = new String();
		temp+= String.valueOf(m_month) + " - " + m_title + " - " + m_isbn;
		return temp;
	}

	public int compareTo(CPurchaseStats o) {
		return -1; //TODO: make a valid comparator
	}
}
