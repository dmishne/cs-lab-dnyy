package server.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import server.core.CExecuter;
import server.core.CStandbyUnit;
import server.db.CDBInteractionGenerator;

public class CGoodReadingServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 *  TODO: start up server,
		 *  		first comes DB
		 *  		second comes in the CStandbyUnit, Executer SHOULD go after standby unit, for he calls for an instance of it, however there is no limitation currently, it will justmake some of the code redundant to call it first
		 *			next we should ??  
		 */
		CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server Started loading at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
		CStandbyUnit.GetInstance();
		CExecuter.GetInstance();
	
		//at last, we check that everthing is working
		if(CDBInteractionGenerator.GetInstance() != null && CStandbyUnit.GetInstance() != null && CExecuter.GetInstance() != null)
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server online at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
		else 
			CDBInteractionGenerator.GetInstance().ServerUpdateLog("Server failed startup at "+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));
		
		/* TODO: what next? */
	}

}
