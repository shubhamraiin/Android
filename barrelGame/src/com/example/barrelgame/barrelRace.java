/* 
 * Entire logic to check whether the ball is clashing the boundary and whether the ball is coming around the barrel 
 */
package com.example.barrelgame;

import android.os.Vibrator;
import android.util.Log;

public class barrelRace {
	Vibrator v;
	int circle1q1c = 0, circle1q2c = 0, circle1q3c = 0, circle1q4c=0;
	int circle1q1a = 0, circle1q2a = 0, circle1q3a = 0, circle1q4a=0;
	int circle2q1c = 0, circle2q2c = 0, circle2q3c = 0, circle2q4c=0;
	int circle2q1a = 0, circle2q2a = 0, circle2q3a = 0, circle2q4a=0;
	int circle3q1c = 0, circle3q2c = 0, circle3q3c = 0, circle3q4c=0;
	int circle3q1a = 0, circle3q2a = 0, circle3q3a = 0, circle3q4a=0;
	int c1Currentquadrantc =0, c1Previousquadrantc =0,c1Currentquadranta =0, c1Previousquadranta =0, Circle1counterc = 0, Circle1countera=0;
	int c2Currentquadrantc =0, c2Previousquadrantc =0,c2Currentquadranta =0, c2Previousquadranta =0, Circle2counterc = 0, Circle2countera=0;
	int c3Currentquadrantc =0, c3Previousquadrantc =0,c3Currentquadranta =0, c3Previousquadranta =0, Circle3counterc = 0, Circle3countera=0;
	boolean c1cq1=false, c1cq2 =false, c1cq3 = false, c1cq4 = false;
    boolean c1aq1=false, c1aq2 =false, c1aq3 = false, c1aq4 = false;
    boolean c2cq1=false, c2cq2 =false, c2cq3 = false, c2cq4 = false;
    boolean c2aq1=false, c2aq2 =false, c2aq3 = false, c2aq4 = false;
    boolean c3cq1=false, c3cq2 =false, c3cq3 = false, c3cq4 = false;
    boolean c3aq1=false, c3aq2 =false, c3aq3 = false, c3aq4 = false;
    /* Declare the necessary flags and variables for the logic */
    /* Determines the circle 1 clash based on accelerometer values */
public boolean determineCircle1clash(UI uiobj, float xa, float ya)
{
	float distance1 = (float) Math.sqrt((uiobj.circle1width-(uiobj.xp - (xa*uiobj.acc)))*(uiobj.circle1width-(uiobj.xp - (xa*uiobj.acc)))+(uiobj.circle1height-(uiobj.yp + (ya*uiobj.acc)))*(uiobj.circle1height-(uiobj.yp + (ya*uiobj.acc))));
    
    if ((distance1 <= uiobj.staticcircleradius + uiobj.dynamiccircleradius))
    {                             

    return true;
    }
	return false;
}
/* Determines the circle 2 clash based on accelerometer values */
public boolean determineCircle2clash(UI uiobj, float xa, float ya)
{
    
	float distance2 = (float) Math.sqrt((uiobj.circle2width-(uiobj.xp - (xa*uiobj.acc)))*(uiobj.circle2width-(uiobj.xp - (xa*uiobj.acc)))+(uiobj.circle2height-(uiobj.yp + (ya*uiobj.acc)))*(uiobj.circle2height-(uiobj.yp + (ya*uiobj.acc))));
    
    if ((distance2 <= uiobj.staticcircleradius + uiobj.dynamiccircleradius))
    {                             

    return true;
    }
	return false;
}

/* Determines the circle 3 clash based on accelerometer values */
public boolean determineCircle3clash(UI uiobj, float xa, float ya)
{
    
	float distance3 = (float) Math.sqrt((uiobj.circle3width-(uiobj.xp - (xa*uiobj.acc)))*(uiobj.circle3width-(uiobj.xp - (xa*uiobj.acc)))+(uiobj.circle3height-(uiobj.yp + (ya*uiobj.acc)))*(uiobj.circle3height-(uiobj.yp + (ya*uiobj.acc))));
    
    if ((distance3 <= uiobj.staticcircleradius + uiobj.dynamiccircleradius))
    {                             

    return true;
    }
	return false;
}
/* Sets the x & y pixel values */
public void setxpyp(UI uiobj, float x, float y)
{
	uiobj.xp = uiobj.xp - (x*uiobj.acc);
	uiobj.yp = uiobj.yp + (y*uiobj.acc);
}
/* Determines the boundary clash to increment the timer/ chronometer by 5 seconds*/
public boolean determineBoundaryclash(UI uiobj, float xa, float ya)
{
    
	boolean ret=false;
    if ((uiobj.xp >uiobj.getWidth()-uiobj.dynamiccircleradius))
    {                             
    ret = true;
    uiobj.xp = uiobj.getWidth()-uiobj.dynamiccircleradius;
    }
    if ((uiobj.yp >uiobj.getHeight()-(3*uiobj.dynamiccircleradius)) && ((uiobj.xp <= (uiobj.getWidth()/2)-(1*uiobj.dynamiccircleradius)) || (uiobj.xp) >= (uiobj.getWidth()/2)+(1*uiobj.dynamiccircleradius)))
    {
    	ret = true;
        uiobj.yp = uiobj.getHeight()-(3*uiobj.dynamiccircleradius);
    }
    if ((uiobj.yp >uiobj.getHeight()-uiobj.dynamiccircleradius) && ((uiobj.xp >= (uiobj.getWidth()/2)-(1*uiobj.dynamiccircleradius)) && (uiobj.xp) <= (uiobj.getWidth()/2)+(1*uiobj.dynamiccircleradius)))
    {
    ret = false;
    uiobj.yp = uiobj.getHeight()-uiobj.dynamiccircleradius;
    }
    if (uiobj.xp <uiobj.dynamiccircleradius)
    {
    	ret = true;
    	uiobj.xp = uiobj.dynamiccircleradius;
    }
    if (uiobj.yp <uiobj.dynamiccircleradius)
    {
    	ret = true;
    	uiobj.yp = uiobj.dynamiccircleradius;
    }
	return ret;
}
/* Determine whether the circle 1 is circled by the ball */
public boolean determineCircle1revolution(UI uiobj)
{
	
   	c1Previousquadrantc = c1Currentquadrantc;
    if ((uiobj.xp >= uiobj.circle1width) && (uiobj.yp <= uiobj.circle1height + uiobj.staticcircleradius))
    {
    	
    	circle1q1c = 1;
    	c1Currentquadrantc = 1;
    	c1cq1 = true;
    	Log.d("Debug c Q1", String.valueOf(circle1q1c) + String.valueOf(circle1q2c) + String.valueOf(circle1q3c) + String.valueOf(circle1q4c));
    	
    }
    if ((uiobj.xp >= uiobj.circle1width + uiobj.staticcircleradius) && (uiobj.yp >= uiobj.circle1height))
    {
    	
    	circle1q2c = 1;
    	c1Currentquadrantc = 2;
    	c1cq2 = true;
    	Log.d("Debug c Q2", String.valueOf(circle1q1c) + String.valueOf(circle1q2c) + String.valueOf(circle1q3c) + String.valueOf(circle1q4c));
    	
    }
    if ((uiobj.xp <= uiobj.circle1width) && (uiobj.yp >= uiobj.circle1height + uiobj.staticcircleradius))
    {
    	
    	circle1q3c = 1;
    	c1Currentquadrantc= 3;
    	c1cq3 = true;
    	Log.d("Debug c Q3", String.valueOf(circle1q1c) + String.valueOf(circle1q2c) + String.valueOf(circle1q3c) + String.valueOf(circle1q4c));
    	
    }
    if ((uiobj.xp >= uiobj.circle1width - uiobj.staticcircleradius) && (uiobj.yp <= uiobj.circle1height))
    {
    	
    	circle1q4c = 1;
    	c1Currentquadrantc = 4;
    	c1cq4 = true;
    	Log.d("Debug c Q4", String.valueOf(circle1q1c) + String.valueOf(circle1q2c) + String.valueOf(circle1q3c) + String.valueOf(circle1q4c));
    	
    }
    if (c1Previousquadrantc != c1Currentquadrantc)
    {
    //	clockwise = true;
    	Circle1counterc = Circle1counterc + 1;
    }
    		c1Previousquadranta = c1Currentquadranta;
            if ((uiobj.xp <= uiobj.circle1width) && (uiobj.yp <= uiobj.circle1height - uiobj.staticcircleradius))
            {
         
            	circle1q1a = 1;
            	c1Currentquadranta = 1;
            	c1aq1 = true;
         
            }
            if ((uiobj.xp >= uiobj.circle1width + uiobj.staticcircleradius) && (uiobj.yp <= uiobj.circle1height))
            {
            	
            	circle1q2a = 1;
            	c1Currentquadranta = 2;
            	c1aq2 = true;
            	
            }
            if ((uiobj.xp >= uiobj.circle1width) && (uiobj.yp <= uiobj.circle1height + uiobj.staticcircleradius) && (uiobj.yp >= uiobj.circle1height))
            {
            	
            		circle1q3a = 1;
            	c1Currentquadranta = 3;
            	c1aq3 = true;
            	
            }
            if ((uiobj.xp <= uiobj.circle1width - uiobj.staticcircleradius) && (uiobj.yp >= uiobj.circle1height))
            {
            	
            	circle1q4a = 1;
            	c1Currentquadranta = 4;
            	c1aq4 = true;
            	
            }	    
            if (c1Previousquadranta != c1Currentquadranta)
            {
            	Circle1countera = Circle1countera + 1;
            }
	
            if ((((Circle1counterc >=5) && (c1cq1 && c1cq2 && c1cq3 && c1cq4)) || ((Circle1countera >=5) && (c1aq1 && c1aq2 && c1aq3 && c1aq4)) ) )
            {
            	Circle1counterc = 0;
            	Circle1countera = 0;
            	Circle2counterc = 0;
            	Circle2countera = 0;
            	Circle3counterc = 0;
            	Circle3countera = 0;
            	return true;
            }
	return false;
}
/* Determine whether the circle 2 is circled by the ball */
public boolean determineCircle2revolution(UI uiobj)
{
   	c2Previousquadrantc = c2Currentquadrantc;
    if ((uiobj.xp >= uiobj.circle2width) && (uiobj.yp <= uiobj.circle2height + uiobj.staticcircleradius))
    {
    	circle2q1c = 1;
    	c2Currentquadrantc = 1;
    	c2cq1 = true;
    	Log.d("Debug c2 Q1", String.valueOf(circle2q1c) + String.valueOf(circle2q2c) + String.valueOf(circle2q3c) + String.valueOf(circle2q4c));
    	
    }
    if ((uiobj.xp >= uiobj.circle2width + uiobj.staticcircleradius) && (uiobj.yp >= uiobj.circle2height))
    {
    	circle2q2c = 1;
    	c2Currentquadrantc = 2;
    	c2cq2 = true;
    	Log.d("Debug c2 Q2", String.valueOf(circle2q1c) + String.valueOf(circle2q2c) + String.valueOf(circle2q3c) + String.valueOf(circle2q4c));
    }
    if ((uiobj.xp <= uiobj.circle2width) && (uiobj.yp >= uiobj.circle2height + uiobj.staticcircleradius))
    {
    	circle2q3c = 1;
    	c2Currentquadrantc= 3;
    	c1cq3 = true;
    	Log.d("Debug c2 Q3", String.valueOf(circle2q1c) + String.valueOf(circle2q2c) + String.valueOf(circle2q3c) + String.valueOf(circle2q4c));
    }
    if ((uiobj.xp >= uiobj.circle2width - uiobj.staticcircleradius) && (uiobj.yp <= uiobj.circle2height))
    {
    	
    	circle2q4c = 1;
    	c2Currentquadrantc = 4;
    	c2cq4 = true;
    	Log.d("Debug c2 Q4", String.valueOf(circle2q1c) + String.valueOf(circle2q2c) + String.valueOf(circle2q3c) + String.valueOf(circle2q4c));
    }
    if (c2Previousquadrantc != c2Currentquadrantc)
    {
    //	clockwise = true;
    	Circle2counterc = Circle2counterc + 1;
    } 
c2Previousquadranta = c2Currentquadranta;
            if ((uiobj.xp <= uiobj.circle2width) && (uiobj.yp <= uiobj.circle2height - uiobj.staticcircleradius))
            {
            	
            	circle2q1a = 1;
            	c2Currentquadranta = 1;
            	c2aq1 = true;
            	Log.d("Debug a2 Q1", String.valueOf(circle2q1a) + String.valueOf(circle2q2a) + String.valueOf(circle2q3a) + String.valueOf(circle2q4a));
            	
            }
            if ((uiobj.xp >= uiobj.circle2width + uiobj.staticcircleradius) && (uiobj.yp <= uiobj.circle2height))
            {
            	
            	circle2q2a = 1;
            	c2Currentquadranta = 2;
            	c2aq2 = true;
            	Log.d("Debug a2 Q1", String.valueOf(circle2q1a) + String.valueOf(circle2q2a) + String.valueOf(circle2q3a) + String.valueOf(circle2q4a));
            }
            if ((uiobj.xp >= uiobj.circle2width) && (uiobj.yp <= uiobj.circle2height + uiobj.staticcircleradius) && (uiobj.yp >= uiobj.circle2height))
            {
            	
            	circle2q3a = 1;
            	c2Currentquadranta = 3;
            	c2aq3 = true;
            	Log.d("Debug a2 Q1", String.valueOf(circle2q1a) + String.valueOf(circle2q2a) + String.valueOf(circle2q3a) + String.valueOf(circle2q4a));
            }
            if ((uiobj.xp <= uiobj.circle2width - uiobj.staticcircleradius) && (uiobj.yp >= uiobj.circle2height))
            {
            	
            	circle2q4a = 1;
            	c2Currentquadranta = 4;
            	c2aq4 = true;
            	Log.d("Debug a2 Q1", String.valueOf(circle2q1a) + String.valueOf(circle2q2a) + String.valueOf(circle2q3a) + String.valueOf(circle2q4a));
            }	    
            if (c2Previousquadranta != c2Currentquadranta)
            {
            	Circle2countera = Circle2countera + 1;
            }
	if (((Circle2counterc >=5) && (c2cq1 && c2cq2 && c2cq3 && c2cq4)) || ((Circle2countera >=5) &&(c2aq1 && c2aq2 && c2aq3 && c2aq4)))
    {
      	Circle1counterc = 0;
    	Circle1countera = 0;
    	Circle2counterc = 0;
    	Circle2countera = 0;
    	Circle3counterc = 0;
    	Circle3countera = 0;
    	return true;
    }
	return false;
}
/* Determine whether the circle 3 is circled by the ball */
public boolean determineCircle3revolution(UI uiobj)
{
	c3Previousquadrantc = c3Currentquadrantc;
    if ((uiobj.xp >= uiobj.circle3width) && (uiobj.yp <= uiobj.circle3height + uiobj.staticcircleradius))
    {
    	circle3q1c = 1;
    	c3Currentquadrantc = 1;
    	c3cq1 = true;
    }
    if ((uiobj.xp >= uiobj.circle3width + uiobj.staticcircleradius) && (uiobj.yp >= uiobj.circle3height))
    {
    	circle3q2c = 1;
    	c3Currentquadrantc = 2;
    	c3cq2 = true;
    }
    if ((uiobj.xp <= uiobj.circle3width) && (uiobj.yp >= uiobj.circle3height + uiobj.staticcircleradius))
    {
    	circle3q3c = 1;
    	c3Currentquadrantc= 3;
    	c3cq3 = true;
    }
    if ((uiobj.xp >= uiobj.circle3width - uiobj.staticcircleradius) && (uiobj.yp <= uiobj.circle3height))
    {
    	
    	circle3q4c = 1;
    	c3Currentquadrantc = 4;
    	c3cq4 = true;
    }
    if (c3Previousquadrantc != c3Currentquadrantc)
    {
    	Circle3counterc = Circle3counterc + 1;
    } 
c3Previousquadranta = c3Currentquadranta;
            if ((uiobj.xp <= uiobj.circle3width) && (uiobj.yp <= uiobj.circle3height - uiobj.staticcircleradius))
            {
            	
            	circle3q1a = 1;
            	c3Currentquadranta = 1;
            	c3aq1 = true;
            }
            if ((uiobj.xp >= uiobj.circle3width + uiobj.staticcircleradius) && (uiobj.yp <= uiobj.circle3height))
            {
            	
            	circle3q2a = 1;
            	c3Currentquadranta = 2;
            	c3aq2 = true;
            }
            if ((uiobj.xp >= uiobj.circle3width) && (uiobj.yp <= uiobj.circle3height + uiobj.staticcircleradius) && (uiobj.yp >= uiobj.circle3height))
            {
            	
            	circle3q3a = 1;
            	c3Currentquadranta = 3;
            	c3aq3 = true;
            }
            if ((uiobj.xp <= uiobj.circle3width - uiobj.staticcircleradius) && (uiobj.yp >= uiobj.circle3height))
            {
            	
            	circle3q4a = 1;
            	c3Currentquadranta = 4;
            	c3aq4 = true;
            }	    
            if (c3Previousquadranta != c3Currentquadranta)
            {
            	Circle3countera = Circle3countera + 1;
            }
if (((Circle3counterc >=5) && (c3cq1 && c3cq2 && c3cq3 && c3cq4)) || ((Circle3countera >=5) && (c3aq1 && c3aq2 && c3aq3 && c3aq4)))
{
  	Circle1counterc = 0;
	Circle1countera = 0;
	Circle2counterc = 0;
	Circle2countera = 0;
	Circle3counterc = 0;
	Circle3countera = 0;
	return true;
}
return false;
}
}
