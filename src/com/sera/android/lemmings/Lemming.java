package com.sera.android.lemmings;

import com.sera.android.lemmings.actions.ActionsFactory;
import com.sera.android.lemmings.actions.Animation;

import android.graphics.Bitmap;

public class Lemming extends AnimatedObject
{
	public final static int DIR_LEFT = -1;
	public final static int DIR_RIGHT = 1;

	private int direction;
	
	public Lemming() {
		this.direction=DIR_RIGHT;
	}
	
	public int getDirection() 									{		return direction;	}
	public void setDirection(int direction) 					{		this.direction = direction;	}
	public void switchDirection() {
		this.direction = (direction==DIR_LEFT?DIR_RIGHT:DIR_LEFT);
	}
	
	public Bitmap getCurrentFrame() {
		if (direction==DIR_LEFT) {
			return getCurrentAction().frames_left[getFrame()];
		} else {
			return getCurrentAction().frames_right[getFrame()];
		}
	}
	
	
	public void move(GameLevel currentLevel)
	{
		switch (getCurrentActionCode()) {
		case Animation.ACTION_WALK: walk(currentLevel); break;
		case Animation.ACTION_FALL: fall(currentLevel); break;
		}
	}
	
	public void walk(GameLevel currentLevel)
	{
		int lheight = getHeight();
		int lwidth = getWidth();
		int lx1 = (int)getX(); int lx2 = lx1 + lwidth;
		int ly1 = (int)getY(); int ly2 = ly1 + lheight;
		int stepSize = getHeight();
		
		if (getDirection()==Lemming.DIR_LEFT) {
			setX(lx1-1);
		} else {
			setX(lx1+1);
		}

		// Vacío ante nuestros pies: Menos de 10 pixels escalón, más caída
		int x = lx1 + lwidth/3;
		if (getDirection()==Lemming.DIR_LEFT) x=lx2-lwidth/3;
		int y=ly2;
		while (currentLevel.isEmpty(x, y) && (y-ly2)<stepSize ) {
			y++;
		}
		if (y!=ly2) {
			if (y-ly2<stepSize) {
				//Log.d(this.getClass().getName(),"Escalón de "+(y-ly2)+" pixels en "+x+","+ly2);
				setY(y-lheight-3);    
			} else {
				//Log.d(this.getClass().getName(),"Caída en "+x+","+ly2);
				setCurrentAction(ActionsFactory.getAction(Animation.ACTION_FALL,currentLevel.context));
			}
		}

		// Irregularidad hacia arriba (menos de 10 pixels es escalón)
		if (getDirection()==Lemming.DIR_LEFT) x=lx1+lwidth/3;
		else x=lx2-lwidth/3;
		y=ly2;
		while (!currentLevel.isEmpty(x, y) && (ly2-y)<stepSize ) {
			y--;
		}
		if (y!=ly2) {
			if (ly2-y<stepSize) {
				//Log.d(this.getClass().getName(),"Escalón de "+(ly2-y)+" pixels en "+x+","+ly2);
				setY(y-lheight-1);    
			} else {
				//Log.d(this.getClass().getName(),"Pared en "+x+","+ly2);
				switchDirection();
			}
		}
	}

	public void fall(GameLevel currentLevel)
	{
		int lheight = getHeight();
		int lwidth = getWidth();
		int lx1 = (int)getX(); int lx2 = lx1 + lwidth;
		int ly1 = (int)getY(); int ly2 = ly1 + lheight;
		
		int x = lx1;
		if (getDirection()==Lemming.DIR_LEFT) x=lx2;
		if ( !currentLevel.isEmpty(x, ly2) ) {
			//l.setY(ly1);
			setCurrentAction(ActionsFactory.getAction(Animation.ACTION_WALK,currentLevel.context));
			//Log.d(this.getClass().getName(),"Lemming toca suelo en "+x+","+ly2);
		} else
		if ( !currentLevel.isEmpty(lx1, ly2+1) ) {
			//l.setY(ly1+1);
			setCurrentAction(ActionsFactory.getAction(Animation.ACTION_WALK,currentLevel.context));
			//Log.d(this.getClass().getName(),"Lemming toca suelo en "+x+","+ly2);
		} else
		if ( !currentLevel.isEmpty(lx1, ly2+2) ) {
			setY(ly1+1);
			setCurrentAction(ActionsFactory.getAction(Animation.ACTION_WALK,currentLevel.context));
			//Log.d(this.getClass().getName(),"Lemming toca suelo en "+x+","+ly2+1);
		} else
		if ( !currentLevel.isEmpty(lx1, ly2+3) ) {
			setY(ly1+2);
			setCurrentAction(ActionsFactory.getAction(Animation.ACTION_WALK,currentLevel.context));
			//Log.d(this.getClass().getName(),"Lemming toca suelo en "+x+","+ly2+2);
		} else
		if ( !currentLevel.isEmpty(lx1, ly2+4) ) {
			setY(ly1+3);
			setCurrentAction(ActionsFactory.getAction(Animation.ACTION_WALK,currentLevel.context));
			//Log.d(this.getClass().getName(),"Lemming toca suelo en "+x+","+ly2+3);
		} else
			if ( !currentLevel.isEmpty(lx1, ly2+5) ) {
   			setY(ly1+4);
				setCurrentAction(ActionsFactory.getAction(Animation.ACTION_WALK,currentLevel.context));
			//Log.d(this.getClass().getName(),"Lemming toca suelo en "+x+","+ly2+4);
			} else {
		//if (l.getCurrentActionCode()==LemmingAction.ACTION_FALL) {
			setY(ly1+5);
			//Log.d(this.getClass().getName(),"Lemming sigue cayendo en "+x+","+ly2+5);
		}
	}
}
