package com.tactics.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tactics.gamestate.GameState;

public class UI_EntityDetails extends UI_Element {

	private Sprite sprite = new Sprite(new Texture("textures/ui/entity_details.png"));
	
	public UI_EntityDetails(GameState state) {
		super(state);
	}

	@Override
	public void render(float delta, SpriteBatch batch) {
		// This element won't render if there's no selected entity
		if (state.hoverEntity == null || state.selectedEntity != null) {
			return;
		}
		
		// Draw the details frame
		sprite.setPosition(Gdx.graphics.getWidth() - sprite.getWidth(), 100);
		sprite.draw(batch);
		
		// Grab the icon from the unit
		Sprite icon = state.hoverEntity.getIcon();
		icon.setPosition(Gdx.graphics.getWidth() - (sprite.getWidth() * 3 / 4), 100 + (sprite.getHeight() / 2 - icon.getHeight() / 2));
		icon.draw(batch);
	}

	
	
}
