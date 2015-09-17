/* 
 * This class is for the actions performed on the saveas activity.
 * 
*/
package com.example.notepad;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.*;
import android.view.*;
import android.view.View.OnClickListener;
// Class begins
public class Save extends Activity implements OnClickListener {
    // Standard onCreate method thats sets the layout.
	// Additionally sets the onclick listener for the "Save" and "Cancel" button.
	// Shubham Rai
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		
		EditText editText = (EditText) findViewById(R.id.editText1);
		@SuppressWarnings("unused")
		String filenm = editText.getText().toString();
		 final Button saveas = (Button) findViewById(R.id.SaveAs);
         saveas.setOnClickListener(this);
         final Button cancel = (Button) findViewById(R.id.Cancel);
         cancel.setOnClickListener(this);
         
         

	}
    // Onclick implementation that handles the button actions. For Save, this return as success result with filename passed as a parameter.
    // For cancel, a RESULT_CANCELLED is passed back to the main activity.
	// Shubham Rai
	@Override
    public void onClick(View v)
    {
		Intent intent = getIntent();
		// Perform action on click
		EditText editText = (EditText) findViewById(R.id.editText1);
		
		switch(v.getId()) {
        case R.id.SaveAs:
        	Log.d("saveasbutton", "save");
           intent.putExtra("FILENM", editText.getText().toString());
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
	// Onbackpressed implementation. A RESULT_CANCELLED is passed back.
	// Shubham Rai
	@Override
	public void onBackPressed()
	{
		Intent intent = getIntent();
		setResult(RESULT_CANCELED, intent);
		finish();
	}
	// Inflate menu actions
	// Shubham Rai
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.save, menu);
		return true;
	}

	
	
}
