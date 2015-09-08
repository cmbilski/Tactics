package com.tactics.action;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tactics.gamestate.GameState;

public abstract class Action {
	
	public static Action ACTION_MOVE = new Action_Move();
	public static Action ACTION_ATTACK = new Action_Attack();
	public static Action ACTION_BUILD_KNIGHT = new Action_Build_Knight();

	public static Action ACTION_BUILDLIST_BARRACKS = new Action_BuildList_Barracks();
	
	public abstract void select();
	
	public abstract void act();
	
	public abstract void cancel();
	
	public abstract Sprite getIcon();
	
}
