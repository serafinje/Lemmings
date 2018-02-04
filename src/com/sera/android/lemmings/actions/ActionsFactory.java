package com.sera.android.lemmings.actions;

import java.util.HashMap;

import android.content.Context;

// Los diferentes estados de los lemmings
public class ActionsFactory 
{
	public static HashMap<Integer,Animation> lemmingActions = new HashMap<Integer,Animation>();

	public static Animation getAction(Integer key,Context c)
	{
		Animation ret = lemmingActions.get(key);
		if (ret==null) {
			switch(key)
			{
			case Animation.ACTION_BASH:
				ret = new BashAction();
				lemmingActions.put(Animation.ACTION_BASH, ret);
				break;
			case Animation.ACTION_BLOCK: 
				ret = new BlockAction();
				lemmingActions.put(Animation.ACTION_BLOCK,ret );
				break;
			case Animation.ACTION_BUILD: 
				ret = new BuildAction();
				lemmingActions.put(Animation.ACTION_BUILD, ret);
				break;
			case Animation.ACTION_CLIMB: 
				ret = new ClimbAction();
				lemmingActions.put(Animation.ACTION_CLIMB,ret );
				break;
			case Animation.ACTION_DIG: 
				ret =  new DigAction(c);
				lemmingActions.put(Animation.ACTION_DIG,ret);
				break;
			case Animation.ACTION_CLIMBTOP: 
				ret =  new ClimbTopAction();
				lemmingActions.put(Animation.ACTION_CLIMBTOP,ret);
				break;
			case Animation.ACTION_DROWN: 
				ret =  new DrownAction();
				lemmingActions.put(Animation.ACTION_DROWN,ret);
				break;
			case Animation.ACTION_EXPLODE: 
				ret =  new ExplodeAction();
				lemmingActions.put(Animation.ACTION_EXPLODE,ret);
				break;
			case Animation.ACTION_FALL: 
				ret =  new FallAction(c);
				lemmingActions.put(Animation.ACTION_FALL,ret);
				break;
			case Animation.ACTION_FLOAT: 
				ret =  new FloatAction();
				lemmingActions.put(Animation.ACTION_FLOAT,ret);
				break;
			case Animation.ACTION_FRIE: 
				ret = new FrieAction();
				lemmingActions.put(Animation.ACTION_FRIE, ret);
				break;
			case Animation.ACTION_HOME: 
				ret = new HomeAction();
				lemmingActions.put(Animation.ACTION_HOME,ret );
				break;
			case Animation.ACTION_MINE: 
				ret =  new MineAction();
				lemmingActions.put(Animation.ACTION_MINE,ret);
				break;
			case Animation.ACTION_SPLUT: 
				ret =  new SplutAction();
				lemmingActions.put(Animation.ACTION_SPLUT,ret);
				break;
			case Animation.ACTION_WALK: 
				ret = new WalkAction(c);
				lemmingActions.put(Animation.ACTION_WALK, ret);		
				break;
			}
		}
		
		return ret;
		
	}
}
