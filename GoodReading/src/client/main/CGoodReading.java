package client.main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import client.common.CClientConnector;

public class CGoodReading{

	/**
	 * @param args
	 */
	final public static int Version = 0;
	final public static int Revision = 1;

	public static void main(String[] args) {

		initLookAndFeel();
		client.gui.CMainFrame.setDefaultLookAndFeelDecorated(true); // Set Graphics to be similar to OS.
    	try {
			CClientConnector.getInstance().openConnection();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Can not open connection", "Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
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
