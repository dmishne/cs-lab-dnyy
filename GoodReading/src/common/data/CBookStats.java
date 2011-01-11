package common.data;

import java.io.Serializable;

public class CBookStats implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_Username;
	private String m_FullName;
	private String m_month;
	
	

	public CBookStats(String f,String u,String m)
	{
		m_FullName=f;
		m_Username=u;
		m_month=m;
	}
	public String getFullName() {
		return m_FullName;
	}
	public String getmonth() {
		return m_month;
	}
	public String getUsername() {
		return m_Username;
	}
	
}
