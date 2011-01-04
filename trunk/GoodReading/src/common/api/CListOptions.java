package common.api;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class CListOptions implements Serializable{
	
	private Set<String> m_langueges;
	private Set<String> m_topics;
	private static CListOptions m_hold;
	
	/**
	 * @param m_langueges
	 * @param m_topics
	 */
	public CListOptions(Set<String> m_langueges, Set<String> m_topics) {
		this.m_langueges = m_langueges;
		this.m_topics = m_topics;
	}
	
	
	
	private CListOptions() {
	//	CListOptionsInit();
	}
	
	public CListOptions getInstance()
	{
		if(m_hold == null)
			m_hold=new CListOptions();
		return m_hold;
	}
	public static CListOptions CListOptionsInit(Set a,Set b)
	{
		if(m_hold == null)
			m_hold=new CListOptions();
		m_hold.m_langueges = new HashSet<String>(a);
		m_hold.m_topics = new HashSet<String>(b);
		
		return m_hold;
	}
	


	/**
	 * @return the m_langueges
	 */
	public Set<String> getM_langueges() {
		return m_langueges;
	}


	/**
	 * @return the m_topics
	 */
	public Set<String> getM_topics() {
		return m_topics;
	}
	
	
	

}
