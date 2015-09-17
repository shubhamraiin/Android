/* 
 * This is the main class for this application. This mostly interacts with UI & Fileoperations 
 * classes for performing the user operations on this application.
 * 
*/
package com.example.notepad;

import java.io.File;
import com.example.notepad.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.*;
import android.widget.*;
/* Class begins here - */
@SuppressLint("CutPasteId")
public class MainActivity extends Activity {
	private String filename; 
    private int onresumeswitch = 0; 
    private int submenuswitch =0; /*variable used to identify the click action performed on recent files action item - */
    byte[] buffer = new byte[1024];
    private String path;
    private UI uiobj = new UI();
    //public Bundle state = null;
	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		path = this.getFilesDir().getAbsolutePath(); // Get the android file system internal path for file operation- 
		EditText notepadTitle = (EditText) findViewById(R.id.title);
		EditText editText = (EditText) findViewById(R.id.body);
		uiobj.initializeSettings(getApplicationContext(), path); //Initialize default settings - 
		uiobj.initializeUI(path, (EditText) findViewById(R.id.title), (EditText) findViewById(R.id.body)); //Initialize UI
		uiobj.implementSettings(getApplicationContext(), (EditText) findViewById(R.id.title), (EditText) findViewById(R.id.body), path); // Implement the UI settings
		//EditText editText = (EditText) findViewById(R.id.body);
		//editText.setTextSize(20);
		//editText.setTypeface(Typeface.SERIF);
		//editText.setBackgroundColor(Color.GRAY);
		//setContentView(R.layout.activity_settings);
		//EditText editText = (EditText) findViewById(R.id.body);
		//editText.setText(Integer.toString(file.length));
		
		/*try {
			FileInputStream inputStream = openFileInput(filename);
			
			while (inputStream.read(buffer) != -1) {
			    fileContent.append(new String(buffer));
			}
			//System.out.println(fileContent.toString());
			//EditText editText = (EditText) findViewById(R.id.editText1);
			//editText.setText(fileContent.toString());
		}catch (Exception e) {
			  e.printStackTrace();
			}*/
	}
	// This adds items to the action bar.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	// Generic method to show toast notifications on the UI. Text to be displayed is a parameter - 
	public void showToast(String toasttext)
	{
		Context context = getApplicationContext();
  		CharSequence text = toasttext;
  		int duration = Toast.LENGTH_LONG;
  		Toast toast = Toast.makeText(context, text, duration);
  		toast.setGravity(Gravity.CENTER, 50, 50);
  		toast.show();
	}
	//Implementing this method for synchronous activity calls from the main activity. 
	//The different activities are distinguished based on the request code - 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
        case 1: //Saveas activity call
        	if (resultCode == RESULT_OK) {
		filename = data.getStringExtra("FILENM");
		EditText editText = (EditText) findViewById(R.id.body);
    	fileOperations fs = new fileOperations();
    	if (!fs.fileExists(getApplicationContext(), filename, path)) {
    	String filedata = editText.getText().toString();
        if (fs.saveFile(getApplicationContext(), filename, path, filedata))
        {
        	showToast("Note Saved");
        }
    	}
    	else
    	{
    		showToast("Note already exist!");
    	}
        	}
        	break;
        case 2: // Openfile activity call
        	filename = data.getStringExtra("FILENAME");
        	fileOperations fo = new fileOperations();
        	StringBuffer filecontent = fo.openFile(getApplicationContext(), filename, path);
        	//String filecontent = fo.openFile(getApplicationContext(), filename, path);
        	EditText fileopenbody = (EditText) findViewById(R.id.body);
        	EditText fileopentitle = (EditText) findViewById(R.id.title);
        	fileopentitle.setText(filename);
        	fileopenbody.setFocusable(true);
        	fileopenbody.setEnabled(true);
        	fileopenbody.setText(filecontent.toString());
        	fileopenbody.setFocusable(true);
        	fileopenbody.setEnabled(true);
        	break;
		}
	 
	}
	
    //Code implementation for various action bar items such as Save, New Note, Save as etc - 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	        case R.id.action_saveas: //Gets the file name by calling another activity. Save is performed in the onActivityResult method - 
	        	Intent saveas = new Intent(this, Save.class);
	    		startActivityForResult(saveas, 1);
	        	return true;
	        case R.id.action_save: //Performs save by creating an object for fileOperations class - 
	        	EditText title = (EditText) findViewById(R.id.title);
	        	filename = title.getText().toString();
	        	fileOperations fs = new fileOperations();
	        	if (!fs.fileExists(getApplicationContext(), filename, path)) {
	        	EditText body = (EditText) findViewById(R.id.body);
	        	String filedata = body.getText().toString();
	        	if (fs.saveFile(getApplicationContext(), filename, path, filedata))
	        	{
	        		showToast("Note Saved");
	        	}
	        	}
	        	else
	        	{
	        		showToast("Note already exist!");
	        	}
	        	return true;
	        case R.id.action_list: // Synchronous call to another activity that has list view to display all saved files - 
	        	Intent intent = new Intent(this, ListviewActivity.class);
	        	startActivityForResult(intent, 2);
	        	return true;
	        case R.id.action_recent: // Recent file submenu item addition  
	        	submenuswitch =1;
	        	fileOperations frecent = new fileOperations();
	        	File recentfiles[] = frecent.getFilenames(path);
	        	item.getSubMenu().clear();
	        	int rf1=1;
	        	int rf2=0;
	        	int num = 0;
	        	// Logic to get the recent 5 files or less - 
	        	if (recentfiles.length >= 6)
	        	{
	        		num = 5;
	        		num = num +1;
	        	}
	        	else
	        	{
	        		num = recentfiles.length;
	        		
	        	}
	        	while (rf1 < num )
	        	{
	        		if (!recentfiles[recentfiles.length-1-rf2].getName().equals("systemsettings~")){
	        	item.getSubMenu().add(recentfiles[recentfiles.length-1-rf2].getName());
	        	rf1 = rf1+1;
	        		}
	        	rf2 = rf2+1;
	        	}
	        	return true;
	        
	        case R.id.action_exit: //Exit application - 
	        finish();
	        return true;
	        case R.id.action_delete: //Delete action implementation using fileOperations class object - 
	        	fileOperations fd = new fileOperations();
	        	EditText deletetitle = (EditText) findViewById(R.id.title);
	        	if (fd.deleteFile(getApplicationContext(), deletetitle.getText().toString(), path)) 
	        	{
	        		showToast("Note Deleted");
		      		setContentView(R.layout.activity_main);
		      		uiobj.initializeSettings(getApplicationContext(), path);
		    		uiobj.initializeUI(path, (EditText) findViewById(R.id.title), (EditText) findViewById(R.id.body));
		    		uiobj.implementSettings(getApplicationContext(), (EditText) findViewById(R.id.title), (EditText) findViewById(R.id.body), path);
		      		
	        	}
		        return true;
	        case R.id.action_settings: // Call to another activity that has application settings. Asynchronous call - 
	        	onresumeswitch = 1;
	        	Intent setting = new Intent(this, Settings.class);
	    		startActivity(setting);
	    		
	    		return true;
	        case R.id.action_new: //New note creation - 
	        	setContentView(R.layout.activity_main);
	        	uiobj.initializeSettings(getApplicationContext(), path);
	    		uiobj.initializeUI(path, (EditText) findViewById(R.id.title), (EditText) findViewById(R.id.body));
	    		uiobj.implementSettings(getApplicationContext(), (EditText) findViewById(R.id.title), (EditText) findViewById(R.id.body), path);
	    		findViewById(R.id.title).setFocusable(true);
	        	return true;
	        default: //Default has code to open the recent files. As the recent file list is populated dynamically, using the temporary variable submenuswitch, we identify the corresponding click action on recent files -  
	        	if (submenuswitch ==1)
	        	{
	        	fileOperations fo = new fileOperations();
	        	StringBuffer filecontent = fo.openFile(getApplicationContext(), item.getTitle().toString(), path);
	        	EditText fileopenbody = (EditText) findViewById(R.id.body);
	        	EditText fileopentitle = (EditText) findViewById(R.id.title);
	        	fileopentitle.setText(item.getTitle().toString());
	        	fileopenbody.setFocusable(true);
	        	fileopenbody.setEnabled(true);
	        	fileopenbody.setText(filecontent.toString());
	        	fileopenbody.setFocusable(true);
	        	fileopenbody.setEnabled(true);
	        	submenuswitch =0;
	        	}
	            return super.onOptionsItemSelected(item);
	    }
	    
	    
	    
	}
	
	@Override
	public void onResume() // Upon change of settings, implement the changed UI settings - 
	{
		//Log.d("onresume", Integer.toString(onresumeswitch));
		super.onResume();
		if (onresumeswitch == 1)
		{
		uiobj.implementSettings(getApplicationContext(), (EditText) findViewById(R.id.title), (EditText) findViewById(R.id.body), path);
		}
	}
	

}
