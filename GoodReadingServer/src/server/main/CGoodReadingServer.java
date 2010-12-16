package server.main;

import java.io.IOException;
import java.util.Map;

import server.core.CExecuter;
import server.core.CStandbyUnit;
import server.db.CDBInteractionGenerator;
import server.db.CFile;

public class CGoodReadingServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 *   		start up server,
		 *  		first comes DB
		 *  		second comes in the CStandbyUnit, Executer SHOULD go after standby unit, for he calls for an instance of it, however there is no limitation currently, it will justmake some of the code redundant to call it first
		 *	TODO:	next we should ??  
		 */
		
		CDBInteractionGenerator.GetInstance();		//.ServerUpdateLog("Server Started loading at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
		if(CStandbyUnit.GetInstance() == null)
			System.out.println("Problem Instanciating Standby Unit");
		else {
			try {
				CStandbyUnit.GetInstance().listen();
			} catch (IOException e) {
				System.out.println("Error - Couldn't listen to client");
			}
			System.out.println("Standby Unit online and waiting");
		}
		if(CExecuter.GetInstance() == null)
				System.out.println("Problem Instanciating Executer");
		else System.out.println("Excecuter online and waiting");
	
		
	//	CDBInteractionGenerator.GetInstance().MySQLInsertBlobFile(new CFile("arg.txt").getFilearray().toString());
		
		//at last, we check that everthing is working
	/*	if(CDBInteractionGenerator.GetInstance() != null && CStandbyUnit.GetInstance() != null && CExecuter.GetInstance() != null)
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server online at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
		else 
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server failed startup at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
		
		/* TODO: what next? */
	}

	private String buildSearchBookWhere(Map params)
	{
		String ans="";
		if(params.isEmpty())
			return ans;
		ans="WHERE ";

		if(params.containsKey("title"))
		{
			ans=ans+"m_m_title CONTAINS "+params.get("title");
			params.remove("title");
		}
		else if(params.containsKey("author"))
		{
			ans=ans+"m_m_author CONTAINS "+params.get("author");
			params.remove("author");
		}
		else if(params.containsKey("lable"))
		{
			ans=ans+"m_m_lable CONTAINS "+params.get("lable");
			params.remove("lable");
		}
		else if(params.containsKey("isbn"))
		{
			ans=ans+"m_m_isbn CONTAINS "+params.get("isbn");
			params.remove("isbn");
		}
		else if(params.containsKey("publisher"))
		{
			ans=ans+"m_m_publisher CONTAINS "+params.get("publisher");
			params.remove("publisher");
		}
		else if(params.containsKey("summary"))
		{
			ans=ans+"m_m_summary CONTAINS "+params.get("summary");
			params.remove("summary");
		}
		else if(params.containsKey("topic"))
		{
			ans=ans+"m_m_author topic "+params.get("topic");
			params.remove("topic");
		}
		else if(params.containsKey("TOC"))
		{
			ans=ans+"m_m_author TOC "+params.get("TOC");
			params.remove("TOC");
		}
		else if(params.containsKey("language"))
		{
			ans=ans+"m_m_language "+params.get("language");
			params.remove("language");
		}
		
		
		//now inserting new attributes
		if(params.containsKey("title"))
		{
			ans=ans+" AND m_m_title CONTAINS "+params.get("title");
			params.remove("title");
		}
		 if(params.containsKey("author"))
		{
			ans=ans+" AND m_m_author CONTAINS "+params.get("author");
			params.remove("author");
		}
		 if(params.containsKey("lable"))
		{
			ans=ans+" AND m_m_lable CONTAINS "+params.get("lable");
			params.remove("lable");
		}
		 if(params.containsKey("isbn"))
		{
			ans=ans+" AND m_m_isbn CONTAINS "+params.get("isbn");
			params.remove("isbn");
		}
		 if(params.containsKey("publisher"))
		{
			ans=ans+" AND m_m_publisher CONTAINS "+params.get("publisher");
			params.remove("publisher");
		}
		 if(params.containsKey("summary"))
		{
			ans=ans+" AND m_m_summary CONTAINS "+params.get("summary");
			params.remove("summary");
		}
		 if(params.containsKey("topic"))
		{
			ans=ans+" AND m_m_author topic "+params.get("topic");
			params.remove("topic");
		}
		 if(params.containsKey("TOC"))
		{
			ans=ans+" AND m_m_author TOC "+params.get("TOC");
			params.remove("TOC");
		}
		 if(params.containsKey("language"))
		{
			ans=ans+" AND m_m_language "+params.get("language");
			params.remove("language");
		}
		
		
		return ans;		
	}
}
