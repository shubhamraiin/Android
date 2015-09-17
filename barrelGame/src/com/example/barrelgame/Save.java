/* 
 * Activity to show the save dialog to user. Contains a save and a cancel button. Activity set as transparent in the manifest
 */
package com.example.barrelgame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class Save extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		EditText editText = (EditText) findViewById(R.id.editText1);
		String filenm = editText.getText().toString();
		/* Initialize the button objects with the click listener */
		 final Button save = (Button) findViewById(R.id.Save);
         save.setOnClickListener(this);
         final Button cancel = (Button) findViewById(R.id.Cancel);
         cancel.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save, menu);
		return true;
	}
/* Handle the button clicks. Pass the intent back to the main activity */
	@Override
    public void onClick(View v)
    {
		Intent intent = getIntent();
		// Perform action on click
		EditText editText = (EditText) findViewById(R.id.editText1);
		
		switch(v.getId()) {
        case R.id.Save:
        	Log.d("saveasbutton", "save");
           intent.putExtra("USERNM", editText.getText().toString());
          	 setResult(RESULT_OK, intent);
           	 finish();
          break;
        case R.id.Cancel:
        	Log.d("saveasbutton", "cancel");
        	setResult(RESULT_CANCELED, intent);
    		finish();
          break;
      }
		
   	
    }
}
