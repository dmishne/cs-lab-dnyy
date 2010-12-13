package client.common;

import java.util.HashSet;
import java.util.Set;


public class CListOptions {
	
	
	private Set<String> m_langueges;
	private Set<String> m_topics;
	
	
	/**
	 * @param m_langueges
	 * @param m_topics
	 */
	public CListOptions(Set<String> m_langueges, Set<String> m_topics) {
		this.m_langueges = m_langueges;
		this.m_topics = m_topics;
	}
	
	public CListOptions() {
		CListOptionsInit();
	}
	
	
	public void CListOptionsInit()
	{
		m_langueges = new HashSet<String>();
		m_topics = new HashSet<String>();
		
		m_langueges.add("Hebrew");
		m_langueges.add("English");
		m_langueges.add("Russian");
		m_topics.add("Action");
		m_topics.add("Drama");
		m_topics.add("Comedy");
		
	
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
