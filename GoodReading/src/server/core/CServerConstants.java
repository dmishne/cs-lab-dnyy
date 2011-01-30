package server.core;

import java.util.ResourceBundle;

	/**
	 * This class uses the ResourceBundle to load properties for the server from a properties file (named config)
	 * CServerConstants make a good option for avoiding hard coded text and helping to make outside configuration easier.
	 * All members are called statically, so there's no need for an actual instance of this Class 
	 */
public class CServerConstants {

	/*
	 * these are all members who can be called statically
	 */
	/** m_POP_WINDOW determines whether or not the pop up with progress bar will pop */
	private static boolean m_POP_WINDOW=true;
	/** m_DEFAULT_Global_Library_Path the default path to the library (for uploading files into db) 	 */
	private static String m_DEFAULT_Global_Library_Path="c:/library/";
	/** m_YEARLY_AMMOUNT the ammount of books you get for buying a yearly subscription */
	private static int m_YEARLY_AMMOUNT=150;
	/** m_MONTHLY_AMMOUNT the ammount of books you get for buying a monthly subscription */
	private static int m_MONTHLY_AMMOUNT=5;
	/** m_DEFAULTHOST default host for the DB */
	private static String m_DEFAULTHOST="jdbc:mysql://localhost/cslabdnyy";
	/** m_DEFAULTUSER default user for the DB */
	private static String m_DEFAULTUSER="root";
	/** m_DEFAULTPASS default password for the DB */
	private static String m_DEFAULTPASS="m00nkey";
	/** m_DEFAULT_PORT default port for the server (from which clients may connect */
	private static int m_DEFAULT_PORT=5555;
	/** m_POPULARITY_RATIO determines how much a view is worth vs a purchase, for example if the ratio is 5 than 5 views equals a purchase. @see server.core.CExecuter#recheckPopularity */
	private static int m_POPULARITY_RATIO=10;
	/** m_DEBUG if true, will display output to console, false means output goes to logs  */
	private static boolean m_DEBUG=false;
	
	/**
	 * method configures all the static vars
	 * does not require any path as input
	 * should be called BEFORE classes use these values!
	 */
	public static void Config()
	{
		Config("server.core.config");
	}
	/**
	 * method configures all the static variables
	 * @param path determines the path to the configuration file
	 * should be called BEFORE classes use these values!
	 */	
	public static void Config(String path)
	{
		System.out.println("Loading all properties");
		ResourceBundle arg=null;
		try{
			arg=ResourceBundle.getBundle(path);
			if(arg.containsKey("POP_WINDOW"))
				m_POP_WINDOW=Boolean.parseBoolean(arg.getString("POP_WINDOW"));
			if(arg.containsKey("DEFAULT_Global_Library_Path"))
				m_DEFAULT_Global_Library_Path=arg.getString("DEFAULT_Global_Library_Path");
			if(arg.containsKey("YEARLY_AMMOUNT"))
				m_YEARLY_AMMOUNT=Integer.parseInt(arg.getString("YEARLY_AMMOUNT"));
			if(arg.containsKey("MONTHLY_AMMOUNT"))
				m_MONTHLY_AMMOUNT=Integer.parseInt(arg.getString("MONTHLY_AMMOUNT"));
			if(arg.containsKey("POPULARITY_RATIO"))
				m_POPULARITY_RATIO=Integer.parseInt(arg.getString("POPULARITY_RATIO"));
			if(arg.containsKey("DEFAULTHOST"))
				m_DEFAULTHOST=arg.getString("DEFAULTHOST");
			if(arg.containsKey("DEFAULTUSER"))
				m_DEFAULTUSER=arg.getString("DEFAULTUSER");
			if(arg.containsKey("DEFAULTPASS"))
				m_DEFAULTPASS=arg.getString("DEFAULTPASS");
			if(arg.containsKey("DEFAULT_PORT"))
				m_DEFAULT_PORT=Integer.parseInt(arg.getString("DEFAULT_PORT"));
			if(arg.containsKey("DEBUG"))
				m_DEBUG=Boolean.parseBoolean(arg.getString("DEBUG"));
		}
		catch(Exception e) 
		{
			System.out.println("Failed to load properties, using defaults!");	
		}
	}//end of config
	

	//Getters for constants
	/** getter for POP_WINDOW */
	public static boolean POP_WINDOW() {
		return m_POP_WINDOW;
	}

	/** getter for DEFAULT_Global_Library_Path */
	public static String DEFAULT_Global_Library_Path() {
		return m_DEFAULT_Global_Library_Path;
	}

	/** getter for MONTHLY_AMMOUNT */
	public static int MONTHLY_AMMOUNT() {
		return m_MONTHLY_AMMOUNT;
	}

	/** getter for YEARLY_AMMOUNT */
	public static int YEARLY_AMMOUNT() {
		return m_YEARLY_AMMOUNT;
	}

	/** getter for DEFAULTHOST */
	public static String DEFAULTHOST() {
		return m_DEFAULTHOST;
	}

	/** getter for DEFAULTUSER */
	public static String DEFAULTUSER() {
		return m_DEFAULTUSER;
	}

	/** getter for DEFAULTPASS */
	public static String DEFAULTPASS() {
		return m_DEFAULTPASS;
	}	

	/** getter for POPULARITY_RATIO */
	public static int POPULARITY_RATIO()
	{
		return m_POPULARITY_RATIO;
	}

	/** getter for DEFAULT_PORT */
	public static int DEFAULT_PORT() {
		return m_DEFAULT_PORT;
	}
	
	/** getter for DEBUG */
	public static boolean DEBUG() {
		return m_DEBUG;
	}
}
