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

public class CFile implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4431100074929112042L;
	private byte [] m_file;
	
	
	public CFile(byte [] arg)
	{
		this.m_file=arg;
	}
	public CFile(String path)
	{
		m_file=getFile(path);
	}
	
	
	public CFile(Blob blob) throws SQLException 
	{
		m_file=blob.getBytes(1, (int) blob.length());//toString().replace("com.mysql.jdbc.Blob","[B").getBytes();
	}
	
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
	
	
	public byte[] getChars() {
		return m_file;
	}

	public void setChars(byte[] m_file) {
		this.m_file = m_file;
	}

	
	
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
	
	public byte [] getFilearray() {
		return m_file;
	}

	
	
}
