package com.sera.android.lemmings.actions;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DigAction extends Animation
{
	private final int NFRAMES=16;
	
	public DigAction(Context c) {
		this.code = Animation.ACTION_DIG;
		
		// Cargar grafico
		// mBackgroundImage = BitmapFactory.decodeResource(res,R.drawable.earthrise);
		Resources res = c.getResources();
		this.sprite_left = BitmapFactory.decodeResource(res, com.sera.android.lemmings.R.drawable.lemming_dig_l);
		this.sprite_right= BitmapFactory.decodeResource(res, com.sera.android.lemmings.R.drawable.lemming_dig_r);
		sprite_left =  Bitmap.createScaledBitmap(sprite_left,32*NFRAMES,32,true);
		sprite_right=  Bitmap.createScaledBitmap(sprite_right,32*NFRAMES,32,true);
		
		// Splitear en n frames
		this.setNframes(NFRAMES);
		this.splitSprite();
	}
}
