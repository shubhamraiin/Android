/* Android Assignment - Notepad Application
 * 
 * This class has the code to implement and handle the listview control that shows all the saved files in the application.
 * 
*/
package com.example.notepad;

import java.util.ArrayList;

import android.os.Bundle;
import android.content.Intent;
import android.view.*;
import android.widget.ListView;
import java.util.*;
import android.app.ListActivity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.*;

import android.widget.SimpleAdapter;
//Class begins
public class ListviewActivity extends ListActivity {
	private ListView lv;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        //Logic below derives the list of all files stored in the applications.  
        fileOperations flist = new fileOperations();
        File fnames[] = flist.getFilenames(this.getFilesDir().getAbsolutePath());
        /*String[] values = new String[fnames.length];
        for (int i=0; i<fnames.length;i++)
        {
        	values[i] = fnames[i].getName() + new Date(fnames[i].lastModified()).toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);*/
        List<Map<String, String>> array = new ArrayList<Map<String, String>>();
      
        
        // Populate the map two dimensional array with all the filenames and the set the listadapter. Contains last modified date & time too. //
        for (int i=fnames.length-1; i>=0;i--) {
            Map<String, String> values = new HashMap<String, String>(2);
            if (!fnames[i].getName().toString().equals("systemsettings~")) {
            values.put("File Name", fnames[i].getName());
            values.put("Modified Date", new Date(fnames[i].lastModified()).toString());
            array.add(values);
            }
        }
        SimpleAdapter adapter = new SimpleAdapter(this, array,
                android.R.layout.simple_list_item_2,
                new String[] {"File Name", "Modified Date"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});
setListAdapter(adapter);
// Implement onitemclicklistener. Pass the filename to the mainactivity as soon as the item is clicked.
lv = this.getListView();
lv.setOnItemClickListener(new OnItemClickListener()  {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	Intent intent = getIntent();
    	@SuppressWarnings("unchecked")
		HashMap<String, String> filename =(HashMap<String, String>) lv.getItemAtPosition(position);
    	//String filename = "";
    	List<String> list = new ArrayList<String>(filename.values());
        intent.putExtra("FILENAME", list.get(1));
        setResult(RESULT_OK, intent);
        finish();
    }
});
	}
	
	//Backpressed implementation to set the result as cancelled for the synchronous activity call from Mainactivity.
@Override
public void onBackPressed()
{
	Intent intent = getIntent();
	setResult(RESULT_CANCELED, intent);
	finish();

    }
// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

}
