package com.tactics.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tactics.gamestate.GameState;

public abstract class UI_Element {

	protected GameState state;
	
	public UI_Element(GameState state) {
		this.state = state;
	}
	
	public abstract void render(float delta, SpriteBatch batch);
	
}
