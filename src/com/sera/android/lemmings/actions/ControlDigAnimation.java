package com.sera.android.lemmings.actions;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ControlDigAnimation extends Animation
{
	private final int NFRAMES=14;
	
	public ControlDigAnimation(Context c)
	{
		this.setNframes(NFRAMES);
		
		Resources res = c.getResources();
		this.frames_left = new Bitmap[NFRAMES];
		for (int i=0; i<NFRAMES; i++) {
			frames_left[i] = BitmapFactory.decodeResource(res, res.getIdentifier("control_dig_sel_"+i, "drawable", c.getPackageName()));
		}
		frames_right = frames_left;
	}
	
	public void scaleFrames(int x,int y) {
		for (int i=0; i<NFRAMES; i++) {
			frames_left[i] = Bitmap.createScaledBitmap(frames_left[i], x, y, true);
		}
	}

}
