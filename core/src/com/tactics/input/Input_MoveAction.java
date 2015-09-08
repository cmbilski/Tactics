package com.tactics.input;

import com.badlogic.gdx.InputProcessor;
import com.tactics.Global;
import com.tactics.action.Action_Move;

public class Input_MoveAction extends Input implements InputProcessor {

	public Action_Move action;
		
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
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void select() {
		action.act();
	}

}
