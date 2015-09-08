package com.tactics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class Tactics extends ApplicationAdapter {
private Screen screen;
	
	@Override
	public void create () {
		this.setScreen(new com.tactics.screen.Screen_Gameplay());
	}

	@Override
	public void render () {
		this.screen.render(Gdx.graphics.getDeltaTime());
	}
	
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
}
