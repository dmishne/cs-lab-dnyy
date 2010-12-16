package server.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CFile {
	
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
	    
	    return mybytearray;	//return array
		
		} catch (FileNotFoundException e) {
			System.out.println("Error getting file "+ path+": "+e.getMessage());		}
		catch(IOException e) {
			System.out.println("Error getting file "+ path+": "+e.getMessage());	
		}
		
		return null;
	}
	
	
	public static boolean saveFile(String path, byte[] mybytearray)
	{
		try {
		    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
		   
		    bos.write(mybytearray, 0 , mybytearray.length);
		    bos.flush();
		    return true;
	    
		} catch (FileNotFoundException e) {
			System.out.println("Error getting file "+ path+": "+e.getMessage());		}
		catch(IOException e) {
			System.out.println("Error getting file "+ path+": "+e.getMessage());	
		}
		return false;
	}
	
}
