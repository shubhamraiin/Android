/* 
 * This class deals with all kinds of fileoperations such as fileopen, file save, get list of all file names etc
 * 
*/
package com.example.notepad;


import java.io.*;
import java.util.*;
import android.content.Context;
//Class begins
public class fileOperations {

	@SuppressWarnings("unused")
	private String filenames;
	int j=0;
	//This method returns a file array containing the list of all files (names and last modified) stored in the android file system.
	// Path is the parameter. Returns array of file names.
	
	public File[] getFilenames(String path) {
		//String path = Context.getFilesDir();
		File f = new File(path);
		File[] files = f.listFiles();
		Arrays.sort(files, new Comparator<File>(){
		    public int compare(File f1, File f2)
		    {
		        return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
		    } });
		return files;
		//File file[] = f.listFiles();
		//for (int i=0; i < file.length; i++)
		//{
		   // Log.d("Files", "FileName:" + file[i].getName());
			//filenames = filenames + ";" + file[i].getName();
		//}
		//return file[];
	}
	// This method initializes the file name when the app opens up or new note is created. The logic followed in normal notepad application is replicated here.
	// Filename and the path are parameters. Returns the filename to be initialized.
	
	public String getNextfilename(String filename, String path) {
		File f = new File(path, filename +".txt");
		while (f.exists())
		{
			return "";
		}
		return filename;
	}
// Logic to save the file. The current application context, file name, path and the filedata are the parameters.
	// Returns a boolean value indication whether the file save is successful or not
	// 
	public Boolean saveFile(Context c, String filename, String path, String data)
	{
		
		try {
			
			FileOutputStream outputStream = c.openFileOutput(filename, Context.MODE_PRIVATE);
			  outputStream.write(data.getBytes());
			  outputStream.close();
			
			} catch (Exception e) {
			  e.printStackTrace();
			  return false;
			}
		return true;
	}
   
	

	// Logic to open the file. Application context, filename and path are the parameters. Returns a stringbuffer containing the file data.
	// 
	public StringBuffer openFile(Context c, String filename, String path)
	{
		byte[] buffer = new byte[1024];
		StringBuffer fileContent = new StringBuffer("");
		@SuppressWarnings("unused")
		String content = "";
		try {
		FileInputStream inputStream = c.openFileInput(filename);

		while (inputStream.read(buffer) != -1) {
			//Log.d("buffer", new String(buffer));
			//char carray[] = new String(buffer).toCharArray();
		    fileContent.append(new String(buffer));
		}
	}catch (Exception e) {
		  e.printStackTrace();
		}
		return fileContent;
	}
// This method deletes the file. Application context, filename and path are the parameters.
	// Returns a boolean value indicating whether the file delete is successful or not.
	 
	public boolean deleteFile(Context c, String filename, String path)
	{
		boolean ret = false;
		File f = new File(path, filename);      
		try {
		 ret = f.delete();
	}catch (Exception e) {
		  e.printStackTrace();
		}
		return ret;
	}
	// This method returns whether a file already exists with the name passed as parameter. 
	// Returns a boolean value true if existing, otherwise false.
	
	public boolean fileExists(Context c, String filename, String path)
	{
		File f = new File(path, filename);      
		
		return f.exists();
	}
}
