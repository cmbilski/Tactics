package com.tactics.action;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tactics.Global;

public class Action_BuildList extends Action {

	private Action [] buildActions;
	private Action [] entityActions;
	
	private Sprite icon = new Sprite(new Texture("textures/icons/build.png"));
	
	public Action_BuildList(Action [] buildActions) {
		this.buildActions = buildActions;
	}
	
	public void select() {
		// When this action is selected, we want to replace the current entity's actions with the new build ones
		// This may actually be all stuff that goes in act which we should just call, but, either way
		this.entityActions = Global.state.selectedEntity.getActions();
		Global.state.selectedEntity.setActions(buildActions);
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Sprite getIcon() {
		return icon;
	}

}
