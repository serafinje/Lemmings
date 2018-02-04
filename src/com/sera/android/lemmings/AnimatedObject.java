package com.sera.android.lemmings;

import android.graphics.Bitmap;

import com.sera.android.lemmings.actions.Animation;

public class AnimatedObject
{
	private Animation currentAction;

	private float x,y;
	private int nFrame;

	public float getX() 										{		return x;	}
	public void setX(float x) 									{		this.x = x;	}
	
	public float getY()											{		return y;	}
	public void setY(float y) 									{		this.y = y;	}
	
	public int getFrame() 										{		return nFrame;	}
	public void setFrame(int nFrame)							{		this.nFrame = nFrame;	}
	public void incFrame() {
		nFrame++;
		if (nFrame>=currentAction.getNframes()) {
			nFrame=0;
		}
	}
	
	
	public Animation getCurrentAction() 					{		return currentAction;	}
	public void setCurrentAction(Animation currentAction) 	{
		if (this.currentAction==null || !this.currentAction.equals(currentAction)) {
			this.currentAction = currentAction;
			this.setFrame(0);
		}
	}
	
	public Bitmap getCurrentFrame() {
		return getCurrentAction().frames_left[getFrame()];
	}

	public int getNFrames() { return currentAction.getNframes(); }
	public int getWidth() { return getCurrentFrame().getWidth(); }
	public int getHeight() { return getCurrentFrame().getHeight(); }
	public int getCurrentActionCode() { return currentAction.getCode(); }
	


}
