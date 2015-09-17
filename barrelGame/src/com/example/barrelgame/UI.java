/* 
 * The user interface construction happens here in this class. The static barrels, the dynamic horse, and the boundaries are drawn here in this class. 
 */
package com.example.barrelgame;

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
import android.view.Display;
import android.view.Gravity;
import android.view.View;
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

@SuppressLint("NewApi")
public class UI {

	
    int width;
    int height;
    int staticcircleradius, dynamiccircleradius;
    float xp, yp;
    float circle1width, circle1height, circle2width, circle2height, circle3width, circle3height, distance1, distance2, distance3;
    float acc = (float) 9.81;
    Bitmap bitmap;
    private Canvas g;
    Paint paint = new Paint();
    Paint Pblack = new Paint();
    Paint Pred = new Paint();
    Paint Pgreen = new Paint();
    Paint Pgray = new Paint();
    Paint pWhite = new Paint();
    /* Declare the paint variables */
    /* This method initializes the hieght, width and the necessary pixel variables */
	public void initialize(Activity C)
	{
		/* Two different codes to set the width and the height based on the android versions */
		Display display = ((Activity) C).getWindowManager().getDefaultDisplay();
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){               
	    	Point size = new Point();
	        display.getSize(size);
	        setWidth(size.x);
	        setHeight((int) (size.y*((float)6/10)));
	    }else{  
	    	setHeight((int) (display.getHeight()*((float)6/10)));
		    setWidth(display.getWidth());
	    	
	    }
		setStaticcircleradius(width/20);
		setDynamiccircleradius(width/30);
		xp = width/2;
		yp = height-dynamiccircleradius;
	
	    
	    paint.setColor(Color.YELLOW);
	    
	    Pred.setColor(Color.RED);
	    
	    Pgreen.setColor(Color.GREEN);
	    
	    Pgray.setColor(Color.GRAY);
	    pWhite.setColor(Color.WHITE);
	    circle1width = getWidth()*((float) 1/2);
	    circle1height = getHeight() *((float) 1/3);
	    circle2width = getWidth()*((float) 1/4);
	    circle2height = getHeight() * ((float) 2/3);
	    circle3width = getWidth()*((float) 3/4);
	    circle3height = getHeight() * ((float) 2/3);
	}
	/* Set width method called to set the width of the screen */
	public void setWidth(int w)
	{
		width = w;
		
	}
	/* Get width return the private width variable */
	public int getWidth()
	{
		return width;
	}
	/* Set Height method called to set the height of the screen */
	public void setHeight(int h)
	{
		height = h;
		
	}
	/* Get height returns the private height variable */
	public int getHeight()
	{
		return height;
	}
	/* Set the barrel radius */
	public void setStaticcircleradius(int str)
	{
		staticcircleradius = str;
		
	}
	/* Get the static barrel radius */
	public int getStaticcircleradius()
	{
		return staticcircleradius;
	}
	/* Set the moving ball radius */
	public void setDynamiccircleradius(int dyr)
	{
		dynamiccircleradius = dyr;
		
	}
	/* Get the moving ball radius*/
	public int getDynamiccircleradius()
	{
		return dynamiccircleradius;
	}
	/* Draw barrels to draw the static circles based on the set width and height. Applies for devices with all screens */
	public void drawBarrels()
	{
	    g.drawCircle(circle1width ,circle1height , staticcircleradius, Pblack);
        g.drawCircle(circle2width , circle2height , staticcircleradius, Pblack);
        g.drawCircle(circle3width, circle3height , staticcircleradius, Pblack);
        
	}
	/* Draw base draws the base from where the horse starts */
	public void drawBase()
	{
		g.drawRect(0, getHeight()-(2*dynamiccircleradius), (getWidth()/2)-(2*dynamiccircleradius), getHeight(), pWhite);
        g.drawRect((getWidth()/2)+(2*dynamiccircleradius), getHeight()-(2*dynamiccircleradius), getWidth(), getHeight(), pWhite);
        g.drawRect((getWidth()/2)-dynamiccircleradius, getHeight()-(2*dynamiccircleradius), (getWidth()/2)+dynamiccircleradius, getHeight(), paint);
	}
	/* Draw barrel1 with dynamic color, width and height */
	public void drawBarrel1(Paint pdyn)
	{
		g.drawCircle(circle1width ,circle1height , staticcircleradius, pdyn);
	}
	/* Draw barrel2 with dynami color, width and height */
	public void drawBarrel2(Paint pdyn)
	{
		g.drawCircle(circle2width ,circle2height , staticcircleradius, pdyn);
	}
	/* Draw barrel3 with dynami color, width and height */
	public void drawBarrel3(Paint pdyn)
	{
		g.drawCircle(circle3width ,circle3height , staticcircleradius, pdyn);
	}
	/* Dynamic (moving ball) draw */
	public void drawBall(float xa, float ya, Paint pdyn)
	{
		xp = xp - (xa*acc);
	    yp = yp + (ya*acc);
		g.drawCircle(xp, yp, dynamiccircleradius, pdyn);
	}
	/* Draw the ball from starting position */
	public void drawStart()
	{
		g.drawCircle(getWidth()/2, getHeight()-dynamiccircleradius, dynamiccircleradius, Pblack);
	}
	/* Draw adhoc based on the passed width and height instead of accelerometer values */
	public void drawAdhoc(float xa, float ya,int r, Paint pdyn)
	{
		g.drawCircle(xa, ya, r, pdyn);
	}
	/* Set the bitmap image using which a canvas is constructed*/
	public void setBitmap()
	{
		bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.RGB_565);
	}
	/*Get bitmap */
	public Bitmap getBitmap()
	{
		return bitmap;
	}
	/* Create/ set canvas based on the created bitmap */
	public void setCanvas()
	{
		g = new Canvas(bitmap);
	    g.drawColor(Color.YELLOW);
	}
}
