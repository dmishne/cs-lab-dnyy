package common.api;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
	
	/**
	 * this class is requested during startup of client.
	 * it contains a set of Languages which are in use
	 * also a set of topics that are in use.
	 * this is for GUI to have data transfered into the combo-boxes 
	 */

public class CListOptions implements Serializable{
	
	private static final long serialVersionUID = -7986416378972374649L;
	/**
	 *  a set of used languages.
	 */
	private Set<String> m_languages;
	/**
	 * a set of used topics.
	 */
	private Set<String> m_topics;
	/**
	 * holder, is referenced in client side as singleton.
	 */
	private static CListOptions m_hold;
	
	/**	
	 * simple constructor for class
	 * @param m_languages the new languages set
	 * @param m_topics the new topics set
	 */
	public CListOptions(Set<String> m_languages, Set<String> m_topics) {
		this.m_languages = m_languages;
		this.m_topics = m_topics;
	}
	
	/**
	 * private constructor
	 * does nothing but create new sets.
	 */

	private CListOptions() {
		m_languages = new HashSet<String>();
		m_topics = new HashSet<String>();
	}
	/**
	 * Singleton implementation for Client.
	 * @return the singleton of CListOptions
	 */
	public static CListOptions getInstance()
	{
		if(m_hold == null)
			m_hold=new CListOptions();
		return m_hold;
	}
	/**
	 * initialization of CListOptions,
	 * @param lang new lang set
	 * @param topics new topics set
	 * @return singleton of CListOptions
	 */
	public static CListOptions CListOptionsInit(Set<String> lang,Set<String> topics)
	{
		if(m_hold == null)
			m_hold=new CListOptions();
		m_hold.m_languages = new HashSet<String>(lang);
		m_hold.m_topics = new HashSet<String>(topics);
		return m_hold;
	}
	


	/**
	 * @return the m_languages
	 */
	public Set<String> getm_languages() {
		return m_languages;
	}


	/**
	 * @return the m_topics
	 */
	public Set<String> getM_topics() {
		return m_topics;
	}

	
}
