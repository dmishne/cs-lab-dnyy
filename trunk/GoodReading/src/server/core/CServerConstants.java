package server.core;

import java.util.ResourceBundle;

public class CServerConstants {

	private static boolean m_POP_WINDOW=true; //TODO: toggle to true before presentation
	private static String m_DEFAULT_Global_Library_Path="c:/library/";
	
	private static int m_YEARLY_AMMOUNT=150;
	private static int m_MONTHLY_AMMOUNT=5;
	private static String m_DEFAULTHOST="jdbc:mysql://localhost/cslabdnyy";
	private static String m_DEFAULTUSER="root";
	private static String m_DEFAULTPASS="m00nkey";
	/*
	final private static String m_DEFAULTHOST="jdbc:mysql://remote-mysql4.servage.net/cslabdnyy";
	final private static String m_DEFAULTUSER="cslabdnyy";
	final private static String m_DEFAULTPASS="q1w2e3r4";
	*/
	

	public static void Config()
	{
		System.out.println("Loading all properties");
		ResourceBundle arg=ResourceBundle.getBundle("server.core.config");

		m_POP_WINDOW=Boolean.parseBoolean(arg.getString("m_POP_WINDOW"));
		m_DEFAULT_Global_Library_Path=arg.getString("m_DEFAULT_Global_Library_Path");
		m_YEARLY_AMMOUNT=Integer.parseInt(arg.getString("m_YEARLY_AMMOUNT"));
		m_MONTHLY_AMMOUNT=Integer.parseInt(arg.getString("m_MONTHLY_AMMOUNT"));
		m_DEFAULTHOST=arg.getString("m_DEFAULTHOST");
		m_DEFAULTUSER=arg.getString("m_DEFAULTUSER");
		m_DEFAULTPASS=arg.getString("m_DEFAULTPASS");
	}
	public static void Config(String path)
	{
		System.out.println("Loading all properties");
		ResourceBundle arg=ResourceBundle.getBundle(path);

		m_POP_WINDOW=Boolean.parseBoolean(arg.getString("m_POP_WINDOW"));
		m_DEFAULT_Global_Library_Path=arg.getString("m_DEFAULT_Global_Library_Path");
		m_YEARLY_AMMOUNT=Integer.parseInt(arg.getString("m_YEARLY_AMMOUNT"));
		m_MONTHLY_AMMOUNT=Integer.parseInt(arg.getString("m_MONTHLY_AMMOUNT"));
		m_DEFAULTHOST=arg.getString("m_DEFAULTHOST");
		m_DEFAULTUSER=arg.getString("m_DEFAULTUSER");
		m_DEFAULTPASS=arg.getString("m_DEFAULTPASS");

	}

	//Getters for constants
	public static boolean POP_WINDOW() {
		return m_POP_WINDOW;
	}
	public static String DEFAULT_Global_Library_Path() {
		return m_DEFAULT_Global_Library_Path;
	}
	public static int MONTHLY_AMMOUNT() {
		return m_MONTHLY_AMMOUNT;
	}
	public static int YEARLY_AMMOUNT() {
		return m_YEARLY_AMMOUNT;
	}
	public static String DEFAULTHOST() {
		return m_DEFAULTHOST;
	}
	public static String DEFAULTUSER() {
		return m_DEFAULTUSER;
	}
	public static String DEFAULTPASS() {
		return m_DEFAULTPASS;
	}	
}
