package com.tactics.action;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tactics.gamestate.GameState;

public class Action_Attack extends Action {

	private Sprite icon = new Sprite(new Texture("textures/icons/attack.png"));

	@Override
	public void select() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void act() {
		
	}

	@Override
	public Sprite getIcon() {
		return icon;
	}

	@Override
	public void cancel() {
		
	}
	
}
