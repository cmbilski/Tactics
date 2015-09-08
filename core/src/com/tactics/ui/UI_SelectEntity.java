package com.tactics.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tactics.Util;
import com.tactics.action.Action;
import com.tactics.gamestate.GameState;
import com.tactics.input.Input_SelectUnit;

public class UI_SelectEntity extends UI_Element {

	private Input_SelectUnit input;
	private Sprite sprite = new Sprite(new Texture("textures/ui/entity_select.png"));
	private Sprite cursor = new Sprite(new Texture("textures/ui/cursor.png"));
	
	private Action selectedAction;

	public int currentAction;

	public UI_SelectEntity(GameState state) {
		super(state);
		input = new Input_SelectUnit(this);
		((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(input);
		sprite.setPosition(Gdx.graphics.getWidth() - sprite.getWidth(), 0);
		selectedAction = null;
	}

	private boolean isActive() {
		return (state.selectedEntity != null);
	}

	private void manageInputProcessor() {
		// If we should be active
		if (isActive()) {
			// And our input processor is not currently active
			if (!Util.inputContainsProcessor(input)) {
				// Add it to the stack
				Util.addInputProcessor(input);
				currentAction = 0;
			}
			// Otherwise, we're not supposed to be active
		} else {
			// But our processor is anyways
			if (Util.inputContainsProcessor(input)) {
				// Remove the processor
				Util.removeInputProcessor(input);
			}
		}
	}

	public void render(float delta, SpriteBatch batch) {
		// Make sure our input processor is good
		manageInputProcessor();

		// We don't want to do anything if we're not supposed to be active
		if (!isActive()) {
			return;
		}

		// Draw the sprite
		sprite.draw(batch);

		synchronized(state.selectedEntity.getActions()) {

			// We want to render an icon for each of the actions
			for (int i = 0; i < state.selectedEntity.getActions().length; i++) {
				// Grab the icon
				Sprite icon = state.selectedEntity.getActions()[i].getIcon();

				// Four to a row
				float x = Gdx.graphics.getWidth() - sprite.getWidth() + (((i % 4) * 42) + 10);
				float y = sprite.getHeight() - (42 + ((i / 4) * 42));
				icon.setPosition(x, y);
				icon.draw(batch);

				if (currentAction == i) {
					cursor.setPosition(x, y);
					cursor.draw(batch);
				}
			}
		}

	}

	public void close() {
		// If we have a selected action, go ahead and cancel it
		if (selectedAction != null) {
			selectedAction.cancel();
			selectedAction = null;
		}
		// If we have a list of actions we stole from someone, give them back
		state.selectedEntity = null;
	}

	public void selectAction() {
		selectedAction = state.selectedEntity.getActions()[currentAction];
		selectedAction.select();
	}

}
