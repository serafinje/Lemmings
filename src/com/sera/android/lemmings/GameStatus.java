package com.sera.android.lemmings;

import java.util.ArrayList;
import java.util.Properties;

import android.content.Context;

import com.sera.android.lemmings.actions.ActionsFactory;
import com.sera.android.lemmings.actions.Animation;

public class GameStatus
{
	final static int STATE_DOOR_OPENING=0;
	final static int STATE_RUNNING=1;
	final static int STATE_PAUSE=2;
	final static int STATE_EXIT=3;

	// Dimensiones de pantalla
	// Tamaño de pantalla: Por defecto pongo los valores en mi movil 
    int activityWidth = 800;
    int activityHeight= 480;
    
    // Tamaño de canvas: Tamaño fijo en el que voy a dibujar
    int canvasWidth = 800;
    int canvasHeight= 480;
    float escalaPantalla;
	
	ArrayList<Lemming> lemmings;
	int state;
	GameLevel currentLevel;
	Context context;
	
	public GameStatus(Context c)
	{
		this.context=c;
		this.activityWidth = c.getResources().getDisplayMetrics().widthPixels;
		this.activityHeight= c.getResources().getDisplayMetrics().heightPixels;
		escalaPantalla = (float)(activityHeight-100)/(float)canvasHeight;
		
		// Lista de lemmings (en principio, vacía)
		lemmings = new ArrayList<Lemming>();

		Properties p = new Properties();
		p.put("name","Just dig!");
		p.put("background", "fun1");
		p.put("dimx", "515");	// Tamaño real del BMP
		p.put("dimy", "160");
		p.put("door_x", "201");
		p.put("door_y", "38");
		p.put("exit_x", "349");
		p.put("exit_y","113");
		p.put("num_lemmings", "1");
		currentLevel = new GameLevel(p,c);
	}
	
	public Lemming addLemming()
	{
		Lemming l = new Lemming();
		l.setDirection(Lemming.DIR_RIGHT);
		l.setCurrentAction(ActionsFactory.getAction(Animation.ACTION_FALL,context));
		l.setX(currentLevel.door.getX()+currentLevel.door.getCurrentFrame().getWidth()/2-l.getWidth()/2);
		l.setY(currentLevel.door.getY());
		lemmings.add(l);
		return l;
	}
	
}
