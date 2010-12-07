package server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CBook implements IDBable {

	private String m_isbn;
	private String m_author;
	private String m_title;
	private String m_release_date;
	private String m_publisher;
	private String m_summary;
	private float m_price;
	private int m_score_count;
	private long m_score_sum;
	private String m_topic;
	private Set <String> m_lables;
	private String m_TOC;
	private boolean m_visibility;
	private String m_Language;

	private CBook(String aisbn,String aauthor,String atitle,String arelease,String apublisher,String asummary,float aprice, int count,long sum, String atopic,String alables,
			String aTOC,boolean avisibility, String alang)
	{
		String[] a=alables.split(",");
		
		setM_isbn(aisbn);
		setAuthor(aauthor);
		setTitle(atitle);
		setRelease_date(arelease);
		setPublisher(apublisher);
		setSummary(asummary);
		setPrice(aprice);
		m_score_count=count;
		m_score_sum=sum;
		setTopic(atopic);
		
		m_lables=new HashSet<String>();
		for(String arg: a)
			m_lables.add(arg);
		
		setTOC(aTOC);
		setVisibility(avisibility);
		setLanguage(alang);
	}
	
	public Set FactoryData(ResultSet data) 
	{
		Set <CBook> arg=new HashSet<CBook>();
		
		try {
			while(data.next())
				arg.add(new CBook(data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6),data.getFloat(7),data.getInt(8),data.getLong(9),data.getString(10),data.getString(11),data.getString(12),data.getBoolean(13),data.getString(14)));
		} catch (Exception e) 
		{	 System.out.println("Exception while reading data from result set (FactoryData() "+e.getMessage());	}	
	
		return arg;
	}
	
	
	//getters and setters

	private void setM_isbn(String m_isbn) {
		this.m_isbn = m_isbn;
	}

	public String getISBN() {
		return m_isbn;
	}
	
	public void setAuthor(String m_author) {
		this.m_author = m_author;
	}

	public String getAuthor() {
		return m_author;
	}

	public void setTitle(String m_title) {
		this.m_title = m_title;
	}

	public String getTitle() {
		return m_title;
	}

	public void setRelease_date(String m_release_date) {
		this.m_release_date = m_release_date;
	}

	public String getRelease_date() {
		return m_release_date;
	}

	public void setPublisher(String m_publisher) {
		this.m_publisher = m_publisher;
	}

	public String getPublisher() {
		return m_publisher;
	}

	public void setSummary(String m_summary) {
		this.m_summary = m_summary;
	}

	public String getSummary() {
		return m_summary;
	}

	public void setPrice(float m_price) {
		this.m_price = m_price;
	}

	public float getPrice() {
		return m_price;
	}
	
	public long getScore() { 
		return m_score_sum /m_score_count; 
	}

	public void setTOC(String m_TOC) {
		this.m_TOC = m_TOC;
	}

	public String geTOC() {
		return m_TOC;
	}

	public void setVisibility(boolean m_visibility) {
		this.m_visibility = m_visibility;
	}

	public boolean isVisibility() {
		return m_visibility;
	}

	public void setLanguage(String m_Language) {
		this.m_Language = m_Language;
	}

	public String getLanguage() {
		return m_Language;
	}

	public void setTopic(String m_topic) {
		this.m_topic = m_topic;
	}

	public String getTopic() {
		return m_topic;
	}

	
}
