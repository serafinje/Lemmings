package com.sera.android.lemmings;

import java.util.HashMap;
import java.util.Properties;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sera.android.lemmings.actions.Animation;
import com.sera.android.lemmings.actions.ControlDigAnimation;
import com.sera.android.lemmings.actions.DoorAnimation;
import com.sera.android.lemmings.actions.ExitAnimation;

public class GameLevel 
{
	String name;
	Bitmap background;
	Integer[] actionBarItems = {
			Animation.ACTION_CLIMB,
			Animation.ACTION_FLOAT,
			Animation.ACTION_EXPLODE,
			Animation.ACTION_BLOCK,
			Animation.ACTION_BUILD,
			Animation.ACTION_BASH,
			Animation.ACTION_MINE,
			Animation.ACTION_DIG,
			Animation.ACTION_MORE,
			Animation.ACTION_LESS,
			Animation.ACTION_PAUSE,
			Animation.ACTION_SPEED,
			Animation.ACTION_NUKE
	};
	HashMap<Integer,Bitmap> actionBar;
	HashMap<Integer,AnimatedObject> actionBarSelected;
	int commandWidth;
	AnimatedObject door;
	Lemming exit;
	int dimX,dimY;
	int numLemmings;
	float escalaBitmap;
	int commandBar=100;
	
	// Indicará qué icono de la barra está seleccionado, para tres cosas:
	// - Que al marcar un lemming le aplique la acción
	// - Que el icono esté animado
	// - Que el juego cambie de estado si es necesario
	int selectedAction=-1;
	Context context;
	
	public GameLevel(Properties props,Context c)
	{
		this.context=c;
		name = props.getProperty("name");
		
		dimX = Integer.parseInt(props.getProperty("dimx"));
		dimY = Integer.parseInt(props.getProperty("dimy"));
		
		String backgroundName = props.getProperty("background");
		Resources res = c.getResources();
		
		escalaBitmap = (float)(res.getDisplayMetrics().heightPixels-commandBar)/(float)dimY;
		dimY = res.getDisplayMetrics().heightPixels-commandBar;
		dimX = (int)(escalaBitmap*(float)dimX);
				
		// Cargamos escenario
		background = BitmapFactory.decodeResource(res, res.getIdentifier(backgroundName, "drawable", c.getPackageName()));
		background = Bitmap.createScaledBitmap(background,dimX,dimY,true);

		// Cargamos puerta
		door = new AnimatedObject();
		float doorX = Float.parseFloat(props.getProperty("door_x"))*escalaBitmap;
		float doorY = Float.parseFloat(props.getProperty("door_y"))*escalaBitmap;
		door.setX(doorX);
		door.setY(doorY);
		door.setCurrentAction( new DoorAnimation(c) );

		// Cargamos salida
		exit = new Lemming();
		float exitX = Float.parseFloat(props.getProperty("exit_x"))*escalaBitmap; 
		float exitY = Float.parseFloat(props.getProperty("exit_y"))*escalaBitmap;
		exit.setX(exitX);
		exit.setY(exitY);
		exit.setCurrentAction( new ExitAnimation(c) );

				
		// Cargamos barra de controles
		actionBar = new HashMap<Integer,Bitmap>();
		actionBarSelected = new HashMap<Integer,AnimatedObject>();
		
		Bitmap b = BitmapFactory.decodeResource(res, R.drawable.control_climb);
		Log.d(this.getClass().getName(),"Dimensiones barra comandos: "+b.getWidth()+","+b.getHeight());
		float escalaBarra= commandBar/(float)b.getHeight();
		commandWidth = (int)(escalaBarra*b.getWidth());
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_CLIMB, b);
		
		b = BitmapFactory.decodeResource(res, R.drawable.control_float);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_FLOAT, b);
		
		b = BitmapFactory.decodeResource(res, R.drawable.control_explode);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_EXPLODE, b);

		b = BitmapFactory.decodeResource(res, R.drawable.control_block);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_BLOCK, b);

		b = BitmapFactory.decodeResource(res, R.drawable.control_build);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_BUILD, b);

		b = BitmapFactory.decodeResource(res, R.drawable.control_bash);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_BASH, b);

		b = BitmapFactory.decodeResource(res, R.drawable.control_mine);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_MINE, b);

		b = BitmapFactory.decodeResource(res, R.drawable.control_dig);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_DIG, b);
		AnimatedObject dig = new AnimatedObject();
			dig.setX(7*commandWidth);
			dig.setY(background.getHeight());
				ControlDigAnimation cdig = new ControlDigAnimation(c);
				cdig.scaleFrames(commandWidth, commandBar);
			dig.setCurrentAction(cdig);
		actionBarSelected.put(Animation.ACTION_DIG, dig);

		b = BitmapFactory.decodeResource(res, R.drawable.control_more);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_MORE, b);

		b = BitmapFactory.decodeResource(res, R.drawable.control_less);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_LESS, b);

		b = BitmapFactory.decodeResource(res, R.drawable.control_pause);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_PAUSE, b);

		b = BitmapFactory.decodeResource(res, R.drawable.control_speed);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_SPEED, b);

		b = BitmapFactory.decodeResource(res, R.drawable.control_nuke);
		b = Bitmap.createScaledBitmap(b, commandWidth, commandBar, true);
		actionBar.put(Animation.ACTION_NUKE, b);

		numLemmings = Integer.parseInt(props.getProperty("num_lemmings"));
	}
	
	boolean isEmpty(int x,int y) {
		int pixel = background.getPixel(x, y);
		int red  = pixel & Integer.parseInt("FF0000",16);
		int green= pixel & Integer.parseInt("00FF00",16);
		
		red >>=16;
		green>>=8;
		
		//Log.d(this.getClass().getName(),"Pixel en ("+x+","+y+"): "+Integer.toHexString(pixel)+" ("+Integer.toHexString(red)+"/"+Integer.toHexString(green)+")");
		return (red==0 || green > red);
	}
}
