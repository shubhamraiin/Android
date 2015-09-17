package com.example.barrelgame;
/* 
 * Main class for the barrel game. This drives the execution of the chronometer and the accelerometer.
 */

import java.io.File;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Chronometer;
import android.widget.Toast;
import android.os.Vibrator;
/* Import the necessary packages */ 
@SuppressLint("DrawAllocation")
/* Implement the sensor event listener and button click listener interfaces */
public class MainActivity extends Activity implements SensorEventListener, OnClickListener {
	private SensorManager mSensorManager; 
	private Sensor mAccelerometer;
	private Canvas g;
	private int c = Color.YELLOW;
    private int width;
    private int height;
    String userscore;
	float xp, yp;
	long t,t0;
	long penality;
	long difference;
	long elapsedrealtime;
	long pauseelapsed;
	long startpausetime;
	String path;
	int staticcircleradius, dynamiccircleradius;
	boolean chronometerstatus = false;
	boolean circle1=false, circle2 = false, circle3 = false;
	boolean resetcircle1c = false, resetcircle1a = false;
	boolean startnew = false;
	int circle1q1c = 0, circle1q2c = 0, circle1q3c = 0, circle1q4c=0;
	int circle1q1a = 0, circle1q2a = 0, circle1q3a = 0, circle1q4a=0;
	int circle2q1c = 0, circle2q2c = 0, circle2q3c = 0, circle2q4c=0;
	int circle2q1a = 0, circle2q2a = 0, circle2q3a = 0, circle2q4a=0;
	int circle3q1c = 0, circle3q2c = 0, circle3q3c = 0, circle3q4c=0;
	int circle3q1a = 0, circle3q2a = 0, circle3q3a = 0, circle3q4a=0;
	int c1Currentquadrantc =0, c1Previousquadrantc =0,c1Currentquadranta =0, c1Previousquadranta =0, Circle1counterc = 0, Circle1countera=0;
	int c2Currentquadrantc =0, c2Previousquadrantc =0,c2Currentquadranta =0, c2Previousquadranta =0, Circle2counterc = 0, Circle2countera=0;
	int c3Currentquadrantc =0, c3Previousquadrantc =0,c3Currentquadranta =0, c3Previousquadranta =0, Circle3counterc = 0, Circle3countera=0;
	UI uid = new UI();
	Button start, pause, endgame;
	barrelRace br = new barrelRace();
	float circle1startx, circle1starty;
	Vibrator v;
    EditText timer;
    Chronometer mChronometer;
    float alpha = 0.95f;
    float[] gravity = {0.0f,0.0f,0.0f};
    float[] linear_acceleration = {0.0f,0.0f,0.0f};
    /* Declare the necessary variables */
  @Override
  public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         path = this.getFilesDir().getAbsolutePath();
         initializeuserscore(getApplicationContext(), path);
         /* Initialize the user score file with null data */
		 mChronometer = (Chronometer) findViewById(R.id.chronometer);
		 v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		 
		 start = (Button) findViewById(R.id.button1);
         start.setOnClickListener(this);
         pause = (Button) findViewById(R.id.button2);
         pause.setOnClickListener(this);
         endgame = (Button) findViewById(R.id.button3);
         endgame.setOnClickListener(this);
         pause.setEnabled(false);
 		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
 		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
 		/* Accelerometer sensor manager */
		Display display = getWindowManager().getDefaultDisplay();
		
	    /* Call the uid classes to set the initial barrels and the bitmap images */
	    uid.initialize(this);
	    uid.setBitmap();
	    uid.setCanvas();
	    uid.drawStart();
	    /* Draw the starting position of the ball */
	    draw(0,0);
/*	    
	    height = (int) (height*((float) 6/10));
	    //height = (int) (height * ((float)(7/10)));
	    Log.d("width and height",width + ";" + height);
	    Log.d("width and height",width*(1/2) + ";" + height);
	    Log.d("width and height",width*(1/4) + ";" + height);
	    Log.d("width and height",width*(3/4) + ";" + height);

staticcircleradius = width/20;
dynamiccircleradius = width/30;
xp = width/2;
yp = height-dynamiccircleradius;
	    draw(0, 0);*/
		/*display.getSize(size);
		int width = size.x;
		int height = size.y;
		Log.d("Widht & Height", Integer.toString(width) + ";" + Integer.toString(height));*/
	    

  }
  /* Click handler for the action high scores item */
  @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	  switch (item.getItemId()) {
	  case R.id.action_list:
      	Intent intent = new Intent(this, Listview.class);
      	/* Start another activity from this */
      	startActivityForResult(intent, 2);
      	return true;
	  }
	  return false;
  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
/* This method starts the chronometer based on the basetime. On start, it sets to the system time. While on pause and resume times, it sets the time based on the paused time */
  public void startChronometer(long basetime)
  {
	  difference = 0;
	  mChronometer.setBase(basetime);
	  mChronometer.start();
	  mChronometer.setOnChronometerTickListener(
		         new Chronometer.OnChronometerTickListener(){
		     @Override
		     public void onChronometerTick(Chronometer chronometer) {
		      //long myElapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase() + difference;
		      long myElapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
		      pauseelapsed = myElapsedMillis;
		      String strElapsedMillis = ((myElapsedMillis/1000)/60)%60 + ":" + (int) ((myElapsedMillis/1000)%60);
		      userscore = strElapsedMillis;
		      //showToast(strElapsedMillis);
		      //Toast.makeText(getApplicationContext(), strElapsedMillis, Toast.LENGTH_SHORT).show();
		      
		      //timer.setText(strElapsedMillis);
		     }}
		       );
	  mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
  }
/* Handler for the button click events */  
  @Override
  public void onClick(View v)
  {

		switch(v.getId()) {
      case R.id.button1:
    	  if (startnew)
    	  {
    	  Intent newactivity = getIntent();
    	  mChronometer.stop();
    	  mSensorManager.unregisterListener(this, mAccelerometer);
    	  MainActivity.this.finish();
    	  startActivityForResult(newactivity, 2);
    	  }
    	
    	  elapsedrealtime = SystemClock.elapsedRealtime();
    	  startChronometer(elapsedrealtime);
    	  //Button Start = (Button) findViewById(R.id.button1);
    	  start.setEnabled(false);
    	  pause.setEnabled(true);
    	  chronometerstatus = true;
        break;
    
      case R.id.button2:
    	  
    	  if (pause.getText().toString().toLowerCase().equals("pause"))
    	  {
    		  startpausetime = SystemClock.elapsedRealtime();
    		  mChronometer.stop();
        	  mSensorManager.unregisterListener(this, mAccelerometer);
    	  pause.setText("Resume");
    	  chronometerstatus = false;
    	  }
    	  else
    	  {
    		  
    		  startChronometer(mChronometer.getBase() + (SystemClock.elapsedRealtime()-startpausetime));
    		  pause.setText("Pause");
    		  chronometerstatus = true;
    	  }
    	  //finish();
        break;
      case R.id.button3:
    	  mChronometer.stop();
    	  mSensorManager.unregisterListener(this, mAccelerometer);
    MainActivity.this.finish();
        break;
    }
		
 	
  }
  /* Show toast messages */
  public void showToast(String toasttext)
	{
		Context context = getApplicationContext();
		CharSequence text = toasttext;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER, 36, 36);
		toast.show();
	}
  /*Draw function to draw the moving ball. Accelerometer changed values are passed as input parameters. */
	public void draw (float xa, float ya)
	{
		Paint pblack = new Paint();
    	pblack.setColor(Color.BLACK);
		penality = 0;
		uid.setBitmap();
		uid.setCanvas();
		//* Set the bitmap and canvas for the initial drawing */
		if (!circle1 && !circle2 && !circle3)
		{
			/* Draw the barrels and the base in RED color if none of the barrels are completed */
			uid.drawBarrels();
			uid.drawBase();
		}
		else
		{
			/* Draw the circles in green color if the barrel has been circled successfully before */
			if (circle1)
			{
				uid.drawBarrel1(uid.Pgreen);
				uid.drawBase();
			}
			else
			{
				uid.drawBarrel1(uid.Pblack);
				uid.drawBase();
			}
			if (circle2)
			{
				uid.drawBarrel2(uid.Pgreen);
				uid.drawBase();
			}
			else
			{
				uid.drawBarrel2(uid.Pblack);
				uid.drawBase();
			}
			if (circle3)
			{
				uid.drawBarrel3(uid.Pgreen);
				uid.drawBase();
			}
			else
			{
				uid.drawBarrel3(uid.Pblack);
				uid.drawBase();
			}
		}
		/* Initialize clas variables */
	   boolean clash1 = false, clash2 = false, clash3 = false, clash4= false;
	   /* Determine barrel1 clash */
        if (br.determineCircle1clash(uid, xa, ya))
        {                             
        	clash1 = true;
        }
        /* Determine barrel2 clash */
        else if (br.determineCircle2clash(uid, xa, ya))
        {
        	
        	//g.drawCircle(circle2width , circle2height , staticcircleradius, Pred);
        	clash2 = true;
        
        }
        /* Determine barrel3 clash */
        else if (br.determineCircle3clash(uid, xa, ya))
        {
       
        	//g.drawCircle(circle3width, circle3height , staticcircleradius, Pred);
        	clash3 = true;
        }
        else
        {
        	
        /* Set the new x & y coordinates */	
        br.setxpyp(uid, xa, ya);
        	
	    //clash = true;
        }
       /* Determine the boundary clash and increment the chronometer by 5 seconds each */
        if (br.determineBoundaryclash(uid, xa, ya))
        {
        	penality = 5;
	    	 difference = difference + (penality*1000);
	    	 mChronometer.setBase(elapsedrealtime-difference);
	    	 //v.vibrate(100);
	    	 //uid.drawAdhoc(uid.xp, uid.yp, uid.dynamiccircleradius, uid.Pblack);
	    	 //uid.drawBall(xa, ya, uid.Pblack);
        }
        uid.drawAdhoc(uid.xp, uid.yp, uid.dynamiccircleradius, uid.Pblack);
        /* If clash, redraw the barrels with the RED color */
        if (clash1)
	    {
	    	//g.drawCircle(circle1width ,circle1height , staticcircleradius, Pred);
	    	uid.drawAdhoc(uid.circle1width, uid.circle1height,uid.staticcircleradius, uid.Pred);
	    	//v.vibrate(100);
	    }
	    if (clash2)
	    {
	    	//g.drawCircle(circle2width , circle2height , staticcircleradius, Pred);
	    	uid.drawAdhoc(uid.circle2width, uid.circle2height, uid.staticcircleradius, uid.Pred);
	    	//v.vibrate(100);
	    }
	    if (clash3)
	    {
        	//g.drawCircle(circle3width, circle3height , staticcircleradius, Pred);
	    	uid.drawAdhoc(uid.circle3width, uid.circle3height, uid.staticcircleradius, uid.Pred);
        	//v.vibrate(100);
	    }
	    /* If there are not barrel clash, start determining whether the revolution is complete */
	    if (!clash1 && !clash2 && !clash3)
	    {
	    if (!circle1)
        {
	    	if (br.determineCircle1revolution(uid))
	    	{
	    		circle1 = true;
	    		uid.drawAdhoc(uid.circle1width, uid.circle1height, uid.staticcircleradius, uid.Pgreen);
	    	}
        }
	    	        if (!circle2)
	            {
	    	        	if (br.determineCircle2revolution(uid))
	    		    	{
	    	        		circle2 = true;
	    		    		uid.drawAdhoc(uid.circle2width, uid.circle2height, uid.staticcircleradius, uid.Pgreen);
	    		    	}
	       	    }
	            if (!circle3)
	            {
	            	if (br.determineCircle3revolution(uid))
	    	    	{
	            		circle3 = true;
	    	    		uid.drawAdhoc(uid.circle3width, uid.circle3height, uid.staticcircleradius, uid.Pgreen);
	    	    	}
	       	        }
        /*if (Circle1counterc ==5 || Circle1countera ==5)
        {
        	Circle1counterc = 0;
        	Circle1countera = 0;
        	Circle2counterc = 0;
        	Circle2countera = 0;
        	Circle3counterc = 0;
        	Circle3countera = 0;
        	circle1 = true;
        g.drawCircle(circle1width ,circle1height , staticcircleradius, Pgreen);
        }
        if (Circle2counterc ==5 || Circle2countera ==5)
        {
          	Circle1counterc = 0;
        	Circle1countera = 0;
        	Circle2counterc = 0;
        	Circle2countera = 0;
        	Circle3counterc = 0;
        	Circle3countera = 0;
        	circle2 = true;
        g.drawCircle(circle2width ,circle2height , staticcircleradius, Pgreen);
        }
        if (Circle3counterc ==5 || Circle3countera ==5)
        {
          	Circle1counterc = 0;
        	Circle1countera = 0;
        	Circle2counterc = 0;
        	Circle2countera = 0;
        	Circle3counterc = 0;
        	Circle3countera = 0;
        	circle3 = true;
        g.drawCircle(circle3width ,circle3height , staticcircleradius, Pgreen);
        }
        */
         /* Method to check whether the user has won the game */
	    Checkwin();
	    }
	    else
	    {
	    	uid.drawAdhoc(uid.xp, uid.yp, uid.dynamiccircleradius, uid.paint);
	    	mChronometer.stop();
	      	  mSensorManager.unregisterListener(this, mAccelerometer);
	      	chronometerstatus = false;
	      	//Button Pause = (Button) findViewById(R.id.button2);
	      	pause.setEnabled(false);
	      	start.setText("Start New"); 
	      	start.setEnabled(true);
	      	//showToast("You lost");
	      	uid.xp = uid.getWidth()/2;
	      	uid.yp = uid.getHeight()-uid.dynamiccircleradius;
	      	uid.drawStart();
	      	startnew = true;
	      	
	    }
	    /* Get the imageview from the layout and set the drawn bitmap image */
	    //ImageView imageView = new ImageView(this);
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		 // Set this ImageView's bitmap to ours
        imageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            	if (!chronometerstatus){
            	
          	  Button Start = (Button) findViewById(R.id.button1);
          	  //
          	  if (Start.isEnabled()) { 
          	startChronometer(SystemClock.elapsedRealtime());
          	Start.setEnabled(false);
          	  }
          	  else
          	  {
          		Button Pause = (Button) findViewById(R.id.button2);
          	startChronometer(mChronometer.getBase() + (SystemClock.elapsedRealtime()-startpausetime));
  		  Pause.setText("Pause");
          	  }
          	chronometerstatus = true;
            	}
            	return true;
            }
        });
         /* Set the drawn image */
		 imageView.setImageBitmap(uid.getBitmap());
        
}
	
	/* This method checks whether the user has won the game or not and sets the win flags*/
	public void Checkwin()
	{
		if (circle1 && circle2 && circle3 && uid.xp>= uid.getWidth()/2-(2*uid.dynamiccircleradius) && uid.xp<= uid.getWidth()/2+(2*uid.dynamiccircleradius) && uid.yp >= uid.getHeight()-uid.dynamiccircleradius)
		{
			//draw(0,0);
		//draw(width/2,height-dynamiccircleradius);	
			mChronometer.stop();
      	  mSensorManager.unregisterListener(this, mAccelerometer);
      	chronometerstatus = false;
      	pause.setEnabled(false);
      	circle1 = false;
      	circle2 = false;
      	circle3 = false;
      	showToast("You won!");
      	/* Store the user score by calling another activity */
      	Intent savescore = new Intent(this, Save.class);
      	startActivityForResult(savescore, 1);
		}
	}
	
	
	/*Create a dummy user score file in the beginning*/
	public void initializeuserscore(Context c, String path)
	{
		fileOperations fs = new fileOperations();
		File f = new File(path, "~userscore~");
		if (!f.exists()) 
		{
			fs.saveFile(c, "~userscore~", path, "");	
		}
		
	}
	/* Handle the return from the save score screen. Calls the file class to store the user name and the timing */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
        case 1:
        	if (resultCode == RESULT_OK) {
		String username = data.getStringExtra("USERNM");
		if (!username.equals("")){
    	fileOperations fs = new fileOperations();
    	if (fs.fileExists(getApplicationContext(), "~userscore~", path))
    	{
    		String existingdata = fs.readSavedData(getApplicationContext(), "~userscore~");
    	String filedata = existingdata + username + ";" + userscore + "~";
        if (fs.saveFile(getApplicationContext(), "~userscore~", path, filedata))
        {
        	showToast("Score Saved");
        	showToast(fs.readSavedData(getApplicationContext(), "~userscore~"));
        }
       
    	//EditText fileopentitleaftersave = (EditText) findViewById(R.id.title);
    	//fileopentitleaftersave.setText("test");
    	//Log.d("aftersaveasmessage",fileopentitleaftersave.getText().toString() );
    	}
    	else
    	{
    		
    		showToast("Score file does not exist");
    	}
		}
        	}
        	else
            {
            	start.setText("Start New"); 
    	      	start.setEnabled(true);
    	      	startnew = true;
            }
        	break;
		}
		
	}
	/* Accelerometer changes calls this event. The DRAW method gets called iteratively from this method*/
	@Override
	public void onSensorChanged(SensorEvent event) {
		   //setContentView(R.layout.activity_main);
	float x = event.values[0];
	float y = event.values[1];
	float z = event.values[2];
    gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
    gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
    gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
    linear_acceleration[0] = event.values[0] - gravity[0];
    linear_acceleration[1] = event.values[1] - gravity[1];
    linear_acceleration[2] = event.values[2] - gravity[2];
    /* High pass filter applied based on alpha value */
    //xa = linear_acceleration[0];
    //ya = linear_acceleration[1];
	Log.d("coordinates", Float.toString(x) + Float.toString(y));
	draw(linear_acceleration[0], linear_acceleration[1]);
	
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	
	
}
