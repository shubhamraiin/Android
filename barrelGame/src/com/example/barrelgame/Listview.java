/*
 * List view class to show the high scores
 */
package com.example.barrelgame;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.SimpleAdapter;

public class Listview extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        /* Read the scores by creating the file object */
        fileOperations flist = new fileOperations();
        String filedata = flist.readSavedData(getApplicationContext(), "~userscore~");
        String[] userscores = filedata.split("~");
        String[][] data2d = null;
        List<Map<String, String>> array = new ArrayList<Map<String, String>>();
        /* Store in a 2D list*/
        for (int i=0; i<userscores.length;i++) {
        	Map<String, String> values = new HashMap<String, String>(2);    
            if (userscores[i] != "")
            {
            	String[] data = userscores[i].split(";");
            	Log.d("User Scores", data[0] + data[1]);
            	
            	values.put("User Name", data[0]);
                values.put("Time taken", data[1]);
                array.add(values);
            }
            
        }
        
      /*  for (int i =0; i<userscores.length;i++)
        {
        	values.put("User Name", data2d[i][0]);
            values.put("Time taken", data2d[i][1]);
            array.add(values);
        }*/
        /* Map the list adapter to the 2d array */
        SimpleAdapter adapter = new SimpleAdapter(this, array,
                android.R.layout.simple_list_item_2,
                new String[] {"User Name", "Time taken"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});
        /* Set the adapter */
setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listview, menu);
		return true;
	}

}
