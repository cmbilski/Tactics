package com.tactics.input;

import com.badlogic.gdx.InputProcessor;
import com.tactics.Global;
import com.tactics.ui.UI_SelectEntity;

public class Input_SelectUnit extends Input implements InputProcessor {

	private UI_SelectEntity ui;
	
	public Input_SelectUnit(UI_SelectEntity ui) {
		this.ui = ui;
	}
	
	public boolean keyDown(int keycode) {
		if (keycode == KEYCODE_UP) {
			// UP
			modifyAction(-4);
			return true;
		} else if (keycode == KEYCODE_DOWN) {
			// DOWN
			modifyAction(4);
			return true;
		} else if (keycode == KEYCODE_LEFT) {
			// LEFT
			modifyAction(-1);
			return true;
		} else if (keycode == KEYCODE_RIGHT) {
			// RIGHT
			modifyAction(1);
			return true;
		} else if (keycode == KEYCODE_SPACE) {
			select();
			return true;
		} else if (keycode == KEYCODE_ESC) {
			// Cancel this menu
			cancel();
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
	
	private void modifyAction(int change) {
		// Make the change
		ui.currentAction += change;
		
		// Potentially revert
		if (ui.currentAction >= Global.state.selectedEntity.getActions().length || ui.currentAction < 0) {
			ui.currentAction -= change;
		}
		
	}
	
	private void cancel() {
		ui.close();
	}
	
	private void select() {
		ui.selectAction();
		ui.currentAction = 0;
	}

}
