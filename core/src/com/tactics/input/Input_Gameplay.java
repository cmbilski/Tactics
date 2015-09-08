package com.tactics.input;

import com.badlogic.gdx.InputProcessor;
import com.tactics.Global;
import com.tactics.screen.Screen_Gameplay;

public class Input_Gameplay extends Input implements InputProcessor {

	private Screen_Gameplay screen;
	
	public Input_Gameplay(Screen_Gameplay screen) {
		this.screen = screen;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == KEYCODE_UP) {
			// UP
			Global.camera.moveCameraUp();
			return true;
		} else if (keycode == KEYCODE_DOWN) {
			// DOWN
			Global.camera.moveCameraDown();
			return true;
		} else if (keycode == KEYCODE_LEFT) {
			// LEFT
			Global.camera.moveCameraLeft();
			return true;
		} else if (keycode == KEYCODE_RIGHT) {
			// RIGHT
			Global.camera.moveCameraRight();
			return true;
		} else if (keycode == KEYCODE_SPACE) {
			select();
			return true;
		} else if (keycode == KEYCODE_N) {
			nextTurn();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private void select() {
		screen.state.selectedEntity = screen.state.hoverEntity;
	}
	
	private void nextTurn() {
		screen.state.nextTurn();
	}
	
}
