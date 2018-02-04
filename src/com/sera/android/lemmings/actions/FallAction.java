package com.sera.android.lemmings.actions;

import com.sera.android.lemmings.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FallAction extends Animation
{
	private final int NFRAMES=4;
	
	
	public FallAction(Context c)
	{
		this.code = Animation.ACTION_FALL;

		// Cargar grafico
		Resources res = c.getResources();
		this.sprite_left = BitmapFactory.decodeResource(res, R.drawable.lemming_fall_l);
		this.sprite_right= BitmapFactory.decodeResource(res, R.drawable.lemming_fall_r);
		sprite_left =  Bitmap.createScaledBitmap(sprite_left,32*NFRAMES,32,true);
		sprite_right=  Bitmap.createScaledBitmap(sprite_right,32*NFRAMES,32,true);
		
		// Splitear en n frames
		this.setNframes(NFRAMES);
		this.splitSprite();
	}

}
