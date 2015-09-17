/*
 * All file operations handled in this class. The file operations predominantly includes the user score save and read logic
 */
package com.example.barrelgame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

public class fileOperations {
/* Check to see whether the file exists */
	public boolean fileExists(Context c, String filename, String path)
	{
		File f = new File(path, filename);      
		
		return f.exists();
	}
	/* Saving the user score */
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
/* Read the saved data to display the high scores */
	public String readSavedData (Context c, String filename ) {
        StringBuffer databuffer = new StringBuffer("");
        try {
            FileInputStream inputstream = c.openFileInput(filename) ;
            InputStreamReader streamreader= new InputStreamReader(inputstream);
            BufferedReader reader = new BufferedReader(streamreader);
            String data = reader.readLine ( ) ;
            while ( data!= null ) {
            	//Log.d("fileread", data);
                databuffer.append(data);
                data = reader.readLine ( ) ;
            }

            streamreader.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        return databuffer.toString() ;
    }

}
