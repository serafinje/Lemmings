package com.sera.android.lemmings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LemmingsDrawingSurface extends SurfaceView 
									implements SurfaceHolder.Callback
{
	private LemmingsActivity parentActivity;
	private LemmingsThread   gameThread;

	public LemmingsDrawingSurface(Context context,AttributeSet attrs)
	{
		super(context,attrs);

		this.parentActivity = (LemmingsActivity)context;

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.setFixedSize(800,480);
        holder.addCallback(this);
        setFocusable(true); // make sure we get key events
        
        // Crear hilo de ejecución
        gameThread = new LemmingsThread(holder,parentActivity);
	}

	/******* Eventos del ciclo de vida *********/
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height)
	{
		//gameThread.mSurfaceHeight=this.getBottom();
		//gameThread.mSurfaceWidth=this.getRight();
		gameThread.setSurfaceSize(width, height,this.getWidth(),this.getHeight());
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// Pasamos el toque al juego, para que lo gestione
		gameThread.touch(event);
		
		return true;
	}
	
	
	public LemmingsThread getThread() { return this.gameThread; }
	
}
