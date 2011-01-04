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
		m_langueges = new HashSet<String>();
		m_topics = new HashSet<String>();
	}
	
	public static CListOptions getInstance()
	{
		if(m_hold == null)
			m_hold=new CListOptions();
		return m_hold;
	}
	//CListOptions.CListOptionsInit((CListOptions)o.getM_langueges(),(CListOptions)o.getM_topics());
	public static CListOptions CListOptionsInit(Set lang,Set topics)
	{
		if(m_hold == null)
			m_hold=new CListOptions();
		m_hold.m_langueges = new HashSet<String>(lang);
		m_hold.m_topics = new HashSet<String>(topics);
		m_hold.m_langueges.add("");
		m_hold.m_topics.add("");
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
