package com.sera.android.lemmings;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.sera.android.lemmings.actions.ActionsFactory;
import com.sera.android.lemmings.actions.Animation;

public class LemmingsThread extends Thread 
{
	private static final long FPS = 7;
	private static final int LEMMING_INTERVAL=3; // 3 segundos entre cada nuevo lemming
	private int next_lemming_wait=0;
	
	private LemmingsActivity gameActivity;
	private GameStatus gameStatus;
	boolean touched=true;
	
	private SurfaceHolder mSurfaceHolder;
	
	public LemmingsThread(SurfaceHolder surfaceHolder,LemmingsActivity activity)
	{
		this.mSurfaceHolder = surfaceHolder;
		this.gameActivity = activity;
		this.gameStatus = new GameStatus(activity);
		gameStatus.state=GameStatus.STATE_PAUSE;
		//gameStatus.addLemming();
	}
	
	
    public void doStart() {
    	gameStatus.state=GameStatus.STATE_DOOR_OPENING;
    }
    
    @Override
    public void run()
    {
    	long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        GameLevel currentLevel = gameStatus.currentLevel;
        
    	while (gameStatus.state!=GameStatus.STATE_EXIT) {
    		startTime = System.currentTimeMillis();
    		
    		// 1) Actualizar datos de juego
    		// - Entrada
    		// Abrimos puerta
    		if (gameStatus.state == GameStatus.STATE_DOOR_OPENING) {
    			currentLevel.door.incFrame();
    		}
    		// Si ya está abierta, iniciamos juego
    		if (gameStatus.state == GameStatus.STATE_DOOR_OPENING && 
    			currentLevel.door.getFrame()==currentLevel.door.getNFrames()-1)
    		{
    			gameStatus.state = GameStatus.STATE_RUNNING;
    			gameStatus.addLemming();
    		}
    		
    		// - Salida
    		currentLevel.exit.incFrame();

    		// - Add lemming
    		if (gameStatus.state != GameStatus.STATE_DOOR_OPENING && 
    			gameStatus.lemmings.size()<currentLevel.numLemmings) {
    			next_lemming_wait++;
    			if (next_lemming_wait==FPS*LEMMING_INTERVAL) {
    				gameStatus.addLemming();
    				next_lemming_wait=0;
    			}
    		}

    		// - Lemmings
    		
    		//if (!this.touched)
    		for (int i=0; i<gameStatus.lemmings.size(); i++) {
    			Lemming l = gameStatus.lemmings.get(i);
    			l.incFrame();
    			l.move(currentLevel);
    		}
    		
    		
    		// 2) Dibujar
    		draw();
    		
    		// 3) Retardo para animación fluida
    		sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
            	if (sleepTime > 0)
                	sleep(sleepTime);
                else
                	sleep(10);
            } catch (Exception e) {}    		
    	}
    }
    

    /**
     * Esta función se encarga de gestionar los toques de pantalla.
     * La recepción de los toques se realiza en la DrawingSurface (que captura el OnTouchEvent de la View), y entonces se invoca esta función.
     * @param event
     */
    float oldX,oldY;
    public void touch(MotionEvent event) {
		if (event.getAction()==MotionEvent.ACTION_DOWN) {
			//int x = (int)(event.getX()*this.mCanvasWidth/this.mSurfaceWidth);
			//int y = (int)(event.getY()*this.mCanvasHeight/this.mSurfaceHeight);
			
			float x = event.getX();
			float y = event.getY();
			
			if (y<gameStatus.currentLevel.background.getHeight()) {
				// Guardamos las coordenadas por si vamos a scrollear
				oldX=x;
				oldY=y;

				if (gameStatus.currentLevel.selectedAction>-1) {
					// Miramos si hemos seleccionado algun lemming
					ArrayList<Lemming> lemmings = gameStatus.lemmings;
					for (int i=0; i<lemmings.size(); i++) {
						Lemming l = lemmings.get(i);
						float lx = l.getX();
						float ly = l.getY();
						
						if (x>lx-l.getWidth() && x<lx+2*l.getWidth() && y>ly-l.getHeight() && y<ly+2*l.getHeight()) {
							Log.d(this.getClass().getName(), "Seleccionado lemming #"+i);
							l.setCurrentAction(ActionsFactory.getAction(gameStatus.currentLevel.selectedAction,this.gameActivity));
						}
					}
				}
			} else {
				// Barra de comandos
				int action_clicked = (int)(x/gameStatus.currentLevel.commandWidth);
				switch (action_clicked) {
				case 0: gameStatus.currentLevel.selectedAction=Animation.ACTION_CLIMB; break; 
				case 1: gameStatus.currentLevel.selectedAction=Animation.ACTION_FLOAT; break; 
				case 2: gameStatus.currentLevel.selectedAction=Animation.ACTION_EXPLODE; break; 
				case 3: gameStatus.currentLevel.selectedAction=Animation.ACTION_BLOCK; break; 
				case 4: gameStatus.currentLevel.selectedAction=Animation.ACTION_BUILD; break; 
				case 5: gameStatus.currentLevel.selectedAction=Animation.ACTION_BASH; break; 
				case 6: gameStatus.currentLevel.selectedAction=Animation.ACTION_MINE; break; 
				case 7: gameStatus.currentLevel.selectedAction=Animation.ACTION_DIG; break; 
				case 8: gameStatus.currentLevel.selectedAction=Animation.ACTION_MORE; break; 
				case 9: gameStatus.currentLevel.selectedAction=Animation.ACTION_LESS; break; 
				case 10: gameStatus.currentLevel.selectedAction=Animation.ACTION_PAUSE; break; 
				case 11: gameStatus.currentLevel.selectedAction=Animation.ACTION_SPEED; break; 
				case 12: gameStatus.currentLevel.selectedAction=Animation.ACTION_NUKE; break; 
				}
			}
				
			return;
		}
    	if (event.getAction()==MotionEvent.ACTION_MOVE) {
    		float currentX = event.getX();
    		float currentY = event.getY();
    		
			if (currentY>gameStatus.currentLevel.background.getHeight()) {
				return;
			}

			float despX = currentX - oldX;
    		float despY = currentY - oldY;
    		
    		/* Intentamos desplazamiento suave - no parece servir */
    		//despX = (despX>0)?2:-2;
    		//despY = (despY>0)?2:-2;
    		
    		offsetX += despX;
    		if (offsetX>0 || offsetX < -(gameStatus.currentLevel.background.getWidth()-gameStatus.activityWidth)) {
    			offsetX -= despX;
    		}
    		
    		offsetY += despY;
    		if (offsetY>0 || offsetY < -(gameStatus.currentLevel.background.getHeight()-gameStatus.activityHeight)) {
    			offsetY -= despY;
    		}

			oldX = currentX;
			oldY = currentY;
    		return;
    	}
		touched=!touched;
    }
    
    
    int offsetX=-100,offsetY=0;
	synchronized void draw() {
		Canvas canvas=null;
		try{
			Paint p = new Paint();
			synchronized (mSurfaceHolder) {
				canvas = mSurfaceHolder.lockCanvas(null);
				assert(canvas==null);
				canvas.save();
				
				// Lo que dibujemos a partir de aquí estará desplazado
				canvas.translate(offsetX, offsetY);
				
				// Fondo
				canvas.drawBitmap(gameStatus.currentLevel.background, 0,0, p);
				
				// Puertas
				canvas.drawBitmap(gameStatus.currentLevel.door.getCurrentFrame(), gameStatus.currentLevel.door.getX(), gameStatus.currentLevel.door.getY(),p);
				canvas.drawBitmap(gameStatus.currentLevel.exit.getCurrentFrame(), gameStatus.currentLevel.exit.getX(), gameStatus.currentLevel.exit.getY(),p);
				
				// Lemmings
				ArrayList<Lemming> lemmings = gameStatus.lemmings;
				for (int i=0; i<lemmings.size(); i++) {
					Lemming l = lemmings.get(i);
					canvas.drawBitmap(l.getCurrentFrame(), l.getX(),l.getY(), p);

					/*
					if (touched) {
						Log.d(this.getClass().getName(), "-----------------------------------------------");
						Log.d(this.getClass().getName(), "Canvas dimensions: "+canvas.getWidth()+"x"+canvas.getHeight());
						Log.d(this.getClass().getName(), "Activity dimensions: "+gameActivity.getResources().getDisplayMetrics().widthPixels+"x"+gameActivity.getResources().getDisplayMetrics().heightPixels);
						Log.d(this.getClass().getName(), "Fondo: ("+gameStatus.currentLevel.background.getWidth()+","+gameStatus.currentLevel.background.getHeight()+")");
						Log.d(this.getClass().getName(), "Escala: " + gameStatus.currentLevel.escalaBitmap );
						Log.d(this.getClass().getName(), "Puerta: ("+gameStatus.currentLevel.door.getX()+","+gameStatus.currentLevel.door.getY()+")");
						Log.d(this.getClass().getName(), "Salida: ("+gameStatus.currentLevel.exit.getX()+","+gameStatus.currentLevel.exit.getY()+")");
						Log.d(this.getClass().getName(), "Lemming: ("+l.getX()+","+l.getY()+")");
						Log.d(this.getClass().getName(), "-----------------------------------------------");
						touched=false;
					}*/
				}
				
				// Fin del desplazamiento, volvemos a dibujar objetos fijos en pantalla
				canvas.restore();
				
				// Barra Controles
				for (int x=0; x<gameStatus.currentLevel.actionBarItems.length; x++) {
					int action = gameStatus.currentLevel.actionBarItems[x];
					Bitmap bmp = gameStatus.currentLevel.actionBar.get(action);
					if (gameStatus.currentLevel.selectedAction==action && gameStatus.currentLevel.actionBarSelected.get(action)!=null) {
						AnimatedObject currentAnim = gameStatus.currentLevel.actionBarSelected.get(action); 
						bmp = currentAnim.getCurrentFrame();
						currentAnim.incFrame();
					}
					canvas.drawBitmap(bmp, x*bmp.getWidth(), gameStatus.currentLevel.background.getHeight(),p);
				}
			}
		}
		finally {
			if (canvas!=null)
				mSurfaceHolder.unlockCanvasAndPost(canvas);
		}
	}
	
	
    /* Callback invoked when the surface dimensions change. */
    public void setSurfaceSize(int cwidth, int cheight,int awidth,int aheight)
    {
    	// synchronized to make sure these all change atomically
        synchronized (this) {
            gameStatus.canvasWidth = cwidth;
            gameStatus.canvasHeight = cheight;
            gameStatus.activityWidth = awidth;
            gameStatus.activityHeight = aheight;
        	Log.w(getClass().getName(),"Cambiando dimensiones: Canvas=("+gameStatus.canvasWidth+","+gameStatus.canvasHeight+") / Activity=("+gameStatus.activityWidth+","+gameStatus.activityHeight+")");
        }
    }
}
