package client.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CGoodReading{

	/**
	 * @param args
	 */
	final public static int Version = 0;
	final public static int Revision = 1;

	public static void main(String[] args) {

		/*
		 * TODO:
		 * Create Connection To Server 
		 *  
		 * 
		 * 
		 */
		initLookAndFeel();
		client.gui.CMainFrame.setDefaultLookAndFeelDecorated(true); // Set Graphics to be similar to OS.
		client.gui.CMainFrame mainFrame = new client.gui.CMainFrame();
		mainFrame.setVisible(true);
	}
	
	/* Look And Feel Initialization */
	static void initLookAndFeel()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e) {
			System.err.println("Couldn't find class for specified look and feel:"
					+ UIManager.getSystemLookAndFeelClassName());
	        System.err.println("Did you include the L&F library in the class path?");
	        System.err.println("Using the default look and feel.");
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	    	System.err.println("Can't use the specified look and feel ("
	    			+ UIManager.getSystemLookAndFeelClassName()
	                + ") on this platform.");
	        System.err.println("Using the default look and feel.");
	    } 
	    catch (Exception e) {
	        System.err.println("Couldn't get specified look and feel ("
	        		+ UIManager.getSystemLookAndFeelClassName()
	                + "), for some reason.");
	        System.err.println("Using the default look and feel.");
	        e.printStackTrace();
	    }
	}
}
