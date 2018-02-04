package com.sera.android.lemmings;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LemmingsActivity extends Activity
{
	// Pantalla e hilo del programa
	LemmingsDrawingSurface 	gameView;
	LemmingsThread 			gameThread;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        
        // Nos guardamos la vista de dibujado para manejarla durante el ciclo de vida de la aplicación
        gameView = (LemmingsDrawingSurface)findViewById(R.id.lemmingsDrawingSurface);
        
        // Sacamos el thread del juego y lo ponemos en marcha
        gameThread = gameView.getThread();
        gameThread.doStart();
    }
}