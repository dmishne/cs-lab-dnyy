package common.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
/**
 * CFile is our base for uploading and downloading files.
 * implemented with buffers and interaction with DB.
 */
public class CFile implements Serializable
{

	private static final long serialVersionUID = 4431100074929112042L;
	/**
	 * this attribute represents the file's context in a byte array
	 */
	private byte [] m_file;
	
	/**
	 * simple constructor
	 * @param arg is set into the m_file (file context)
	 */
	private CFile(byte [] arg)
	{
		this.m_file=arg;
	}
	/**
	 * public constructor.
	 * takes a file path, and reads it into the byte array
	 * @param path the path to the file in file system.
	 */
	public CFile(String path)
	{
		m_file=getFile(path);
	}
	
	/**
	 * factory implementation for this function.
	 * referenced in DBIG.
	 * sends only the blob instead of the entire result set.
	 * @param blob is read into the byte array
	 * @throws SQLException is regarded in CDBInteractionGenerator
	 * @see server.db.CDBInteractionGenerator
	 */
	public CFile(Blob blob) throws SQLException 
	{
		m_file=blob.getBytes(1, (int) blob.length());//toString().replace("com.mysql.jdbc.Blob","[B").getBytes();
	}
	
	/**
	 * this method is used to read a file from file system.
	 * first it checks (and prints to log, as exception is dealt with later on) that file exists and that we have reading permissions.
	 * secondly it reads the file into allocated byte array.
	 * this function is referenced in CFile constructor.
	 * @param path path to file we need to read into JVM.
	 * @return byte array representing the file's context.
	 */
	private static byte[] getFile(String path)
	{
		try {
			File myFile = new File (path); //get "File"
		    byte [] mybytearray  = new byte [(int)myFile.length()]; //allocate space for file
		    
		    if(!myFile.exists())
		    	System.out.println("file not here!");
		    	
		    if(!myFile.canRead())
		    	System.out.println("file can not be read!");
		    	
		    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile)); //get Buffered Stream
		    
		    bis.read(mybytearray,0,mybytearray.length); // read file into array
		  
		    bis.close();
		    
		    return mybytearray;	//return array
			
		} catch (FileNotFoundException e) {
			System.out.println("Error getting file "+ path+": "+e.getMessage());		
		} catch(IOException e) {
			System.out.println("Error getting file "+ path+": "+e.getMessage());	
		}
		return null;
	}
	
	/**
	 * simple getter for m_file
	 * @return file's context.
	 */
	public byte[] getChars() {
		return m_file;
	}
	/**
	 * simple setter for file context. 
	 * @param m_file file's new context.
	 */
	public void setChars(byte[] m_file) {
		this.m_file = m_file;
	}

	/**
	 * saves a context into a new file in file system (used on CLIENT side).
	 * @param path in which to save the file
	 * @param mybytearray is actually the file's context
	 * @return success (true) / failure (false)
	 */
	public static boolean saveFile(String path, byte[] mybytearray)
	{
		try {
			// set output stream to write file in filesystem
		    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path)); 
		   
		    bos.write(mybytearray, 0 , mybytearray.length); //write out file
		    bos.flush(); //close buffer
		    bos.close();
		    return true;
	    
		} catch (FileNotFoundException e) {
			System.out.println("Error saving file "+ path+": "+e.getMessage());		}
		catch(IOException e) {
			System.out.println("Error saving file "+ path+": "+e.getMessage());	
		}
		return false;
	}
	
	/**
	 * saves a context into a new file in file system (used on CLIENT side).
	 * @param path in which to save the file
	 * @return success (true) / failure (false)
	 */
	public boolean saveFile(String path)
	{
		try {
			// set output stream to write file in filesystem
			FileOutputStream fos = new FileOutputStream(path); 
			BufferedOutputStream bos = new BufferedOutputStream( fos);	    
		    
		    bos.write(m_file, 0 , m_file.length); //write out file
		    bos.flush(); //close buffer
		    bos.close();
		    return true;
	    
		} catch (FileNotFoundException e) {
			System.out.println("Error saving file "+ path+": "+e.getMessage());		}
		catch(IOException e) {
			System.out.println("Error saving file "+ path+": "+e.getMessage());		}		
		return false;
	}

}
