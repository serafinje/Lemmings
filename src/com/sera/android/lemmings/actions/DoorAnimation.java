package com.sera.android.lemmings.actions;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DoorAnimation extends Animation
{
	private final int NFRAMES=10;
	
	public DoorAnimation(Context c)
	{
		this.setNframes(NFRAMES);
		
		Resources res = c.getResources();
		this.frames_left = new Bitmap[NFRAMES];
		for (int i=0; i<NFRAMES; i++) {
			frames_left[i] = BitmapFactory.decodeResource(res, res.getIdentifier("door"+i, "drawable", c.getPackageName()));
		}
		frames_right = frames_left;
	}
}
