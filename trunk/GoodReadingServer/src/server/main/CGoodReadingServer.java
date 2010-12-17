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

}
