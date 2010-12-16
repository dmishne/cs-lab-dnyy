package server.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class CFile implements Serializable
{
	private byte [] m_file;
		
	public CFile(String path)
	{
		m_file=getFile(path);
	}
	
	private static byte[] getFile(String path)
	{
		try {
		File myFile = new File (path); //get "File"
	    byte [] mybytearray  = new byte [(int)myFile.length()]; //allocate space for file
	    
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
			System.out.println("Error getting file "+ path+": "+e.getMessage());		}
		catch(IOException e) {
			System.out.println("Error getting file "+ path+": "+e.getMessage());	
		}
		return false;
	}

	public byte [] getFilearray() {
		return m_file;
	}
	
}
