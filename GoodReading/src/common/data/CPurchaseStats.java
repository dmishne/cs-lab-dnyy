package common.data;

import java.io.Serializable;

public class CPurchaseStats implements Serializable,Comparable<CPurchaseStats>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_isbn;
	private String m_title;
	private int m_month;
	
	public String getisbn() {
		return m_isbn;
	}
	public String gettitle() {
		return m_title;
	}
	public int getmonth() {
		return m_month;
	}
	public CPurchaseStats(String i,String t,int m)
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
		if(m_month > o.m_month)
		{
			return 1;
		}
		else if(m_month < o.m_month)
		{
			return -1;
		}
		return 0;
	}
}
