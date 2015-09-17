/* 
 * This class saves & implements the settings for the app such as font, size, bgcolor and textcolor.
 * 
*/
package com.example.notepad;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
//import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
// Class begins.
public class Settings extends Activity {
    //Standard onCreatemethod to set the content view.
	// Additionally the settings file is opened and initialized with the file values.
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		fileOperations fo = new fileOperations();
		//StringBuffer data = fo.openFile(getApplicationContext(), "systemsettings~", this.getFilesDir().getAbsolutePath());;
		StringBuffer data = fo.openFile(getApplicationContext(), "systemsettings~", this.getFilesDir().getAbsolutePath());;
		String[] values = data.toString().split(";");
		// The systemsettings~ file is split by the character ";" for different setting.
		// Assign the spinner with the values from the file.
		// 
		if (values != null)
		{
			for (int i = 0; i<values.length ; i++)
			{
				Log.d("settings", Integer.toString(i) + values[i]);
				if ( i == 0)
				{
				Spinner s1 = (Spinner) findViewById(R.id.spinner1);
				@SuppressWarnings("rawtypes")
				ArrayAdapter adapters1 = (ArrayAdapter) s1.getAdapter();
				Log.d("settings",Integer.toString(adapters1.getPosition(values[i])));
				s1.setSelection(adapters1.getPosition(values[i]));
				}
				if ( i == 1)
				{
				Spinner s2 = (Spinner) findViewById(R.id.spinner2);
				@SuppressWarnings("rawtypes")
				ArrayAdapter adapters2 = (ArrayAdapter) s2.getAdapter();
				Log.d("settings", values[i] + "-" + Integer.toString(adapters2.getPosition(values[i])));
				s2.setSelection(adapters2.getPosition(values[i]));
				}
				if ( i == 2)
				{
				Spinner s3 = (Spinner) findViewById(R.id.spinner3);
				@SuppressWarnings("rawtypes")
				ArrayAdapter adapters3 = (ArrayAdapter) s3.getAdapter();
				Log.d("settings", values[i] + "-" + Integer.toString(adapters3.getPosition(values[i])));
				s3.setSelection(adapters3.getPosition(values[i]));
				}
				if ( i == 3)
				{
				Spinner s4 = (Spinner) findViewById(R.id.spinner4);
				@SuppressWarnings("rawtypes")
				ArrayAdapter adapters4 = (ArrayAdapter) s4.getAdapter();
				Log.d("settings", values[i] + "-" + Integer.toString(adapters4.getPosition(values[i])));
				s4.setSelection(adapters4.getPosition(values[i]));
				}
			}
		}
		
	}
    // Inflate the menu with the action bar. Save and Exit actions are added - 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	// Onsave, get the settings saved in the systemsettings~ file in the apdroid file system - 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items - 
	    switch (item.getItemId()) {
	        case R.id.action_save: // Save action - 
	        	Spinner fontname = (Spinner) findViewById(R.id.spinner1);
	        	String filename = "systemsettings~";
	        	fileOperations fs = new fileOperations();
	        	Spinner fontsz = (Spinner) findViewById(R.id.spinner2);
	        	Spinner bgcolor = (Spinner) findViewById(R.id.spinner3);
	        	Spinner fgcolor = (Spinner) findViewById(R.id.spinner4);
	        	String filedata = fontname.getSelectedItem().toString()+ ";" + fontsz.getSelectedItem().toString() + ";" + bgcolor.getSelectedItem().toString() + ";" + fgcolor.getSelectedItem().toString() + ";";
	        	fs.saveFile(getApplicationContext(), filename, this.getFilesDir().getAbsolutePath(), filedata);
	        	  Context context = getApplicationContext();
	      		CharSequence text = "Settings Saved";
	      		int duration = Toast.LENGTH_LONG;
               
	      		Toast toast = Toast.makeText(context, text, duration);
	      		toast.setGravity(Gravity.CENTER, 50, 50);
	      		toast.show();
	        	return true;
	        case R.id.action_exit: // Exit the activity - 
	        finish();
	        return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
