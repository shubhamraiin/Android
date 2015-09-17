/* 
 * This class sets up the UI for the application.
 * 
*/
package com.example.notepad;

import java.io.File;
/*
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.notepad.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.graphics.Canvas;
*/

import android.graphics.Color;
import android.graphics.Typeface;
import android.content.*;
import android.widget.EditText;

// Class begins
public class UI {
	// This method sets the filename on application start or new note creation. The untitled logic is same as that of the normal notepad application.
	// Path, Existing title and the body are the parameters. Returns null as the title is set using the EditText that is passed in the method.
	// 
	@SuppressWarnings("unused")
	public void initializeUI(String path,EditText Title,EditText Body)
	{
		fileOperations flist = new fileOperations();
        File fnames[] = flist.getFilenames(path);
        boolean exists = false;
        int last = 0;
		//List<Map<String, String>> filearray = new ArrayList<Map<String, String>>();
        int len = fnames.length;
        for (int i=0; i<fnames.length;i++) {
        	
        	if (fnames[i].getName().toString().toLowerCase().startsWith("untitled"))
        	{
        		//Log.d("Test", fnames[i].getName().toString().toLowerCase().substring(8));
        		exists = true;
        		if ((fnames[i].getName().toString().toLowerCase().substring(8).length()>0) && (fnames[i].getName().toString().toLowerCase().substring(8, 9).matches("[0-9]")))
        		{
        		last = Integer.parseInt(fnames[i].getName().toString().toLowerCase().substring(8));
        		}
        	}
        		
            // Map<String, String> values = new HashMap<String, String>(2);
           // values.put("File Name", fnames[i].getName());
           // values.put("Modified Date", new Date(fnames[i].lastModified()).toString());
           // array.add(values);
        }
        if (exists)
        {
        	last++;
        	Title.setText("untitled" + last);
        	
        }
        else
        {
        	Title.setText("untitled");
        	
        }
        Title.selectAll();
	}
// This method creates the systemsettings~ initially when the application is created.
	// Get application context and path are the parameters. Returns NULL
	// 
	public void initializeSettings(Context c, String path)
	{
		fileOperations fs = new fileOperations();
		File f = new File(path, "systemsettings~");
		if (!f.exists()) 
		{
			fs.saveFile(c, "systemsettings~", path, "NORMAL;16;WHITE;BLACK;");	
		}
		
	}
	// This method implements/ sets the settings.
	// Application context, title, body and path are the parameters. Returns NULL
	// 
	public void implementSettings(Context c, EditText Title, EditText Body, String path)
	{
		fileOperations fo = new fileOperations();
		//StringBuffer data = fo.openFile(c, "systemsettings~", path);;
		StringBuffer data = fo.openFile(c, "systemsettings~", path);;
		String[] values = data.toString().split(";");
		if (values != null)
		{
			for (int i = 0; i<values.length ; i++)
			{
				if ( i == 0)
				{
					Typeface font = Typeface.create(values[i], 0);
					Body.setTypeface(font);
				}
				if ( i == 1)
				{
					Body.setTextSize(Integer.parseInt(values[i]));
				}
				if ( i == 2)
				{
					//Color c = Color.parseColor(values[i])
					Body.setBackgroundColor(Color.parseColor(values[i]));
				}
				if ( i == 3)
				{
					//Color c = Color.parseColor(values[i])
					Body.setTextColor(Color.parseColor(values[i]));
				}
			}
		}
	}
}
