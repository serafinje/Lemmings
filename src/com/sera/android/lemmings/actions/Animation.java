package com.sera.android.lemmings.actions;

import android.graphics.Bitmap;

public abstract class Animation
{
	public final static int ACTION_BASH=0;
	public final static int ACTION_BLOCK=1;
	public final static int ACTION_BUILD=2;
	public final static int ACTION_CLIMB=3;
	public final static int ACTION_DIG=4;
	public final static int ACTION_CLIMBTOP=5;
	public final static int ACTION_DROWN=6;
	public final static int ACTION_EXPLODE=7;
	public final static int ACTION_FALL=8;
	public final static int ACTION_FLOAT=9;
	public final static int ACTION_FRIE=10;
	public final static int ACTION_HOME=11;
	public final static int ACTION_MINE=12;
	public final static int ACTION_SPLUT=13;
	public final static int ACTION_WALK=14;
	public final static int ACTION_LESS=15;
	public final static int ACTION_MORE=16;
	public final static int ACTION_NUKE=17;
	public final static int ACTION_PAUSE=18;
	public final static int ACTION_SPEED=19;
	
	int code;
	public int getCode() { return code; }
	
	Bitmap sprite_left;
	Bitmap sprite_right;
	public Bitmap getSprite_left() {		return sprite_left;		}
	public Bitmap getSprite_right() {		return sprite_right;	}

	final static int FRAME_DIM=32;
	public Bitmap[] frames_left;
	public Bitmap[] frames_right;
	int nframes;
	
	public int getNframes() {		return nframes;	}
	public void setNframes(int nframes) {		this.nframes = nframes;	}

	void splitSprite() {
		this.frames_left = new Bitmap[nframes];
		for (int i=0; i<nframes; i++) {
			frames_left[i] = Bitmap.createBitmap(sprite_left, FRAME_DIM*i,0 , FRAME_DIM,FRAME_DIM);
		}
		
		this.frames_right= new Bitmap[nframes];
		for (int i=0; i<nframes; i++) {
			frames_right[i] = Bitmap.createBitmap(sprite_right, FRAME_DIM*i,0 , FRAME_DIM,FRAME_DIM);
		}

	}
	
}
