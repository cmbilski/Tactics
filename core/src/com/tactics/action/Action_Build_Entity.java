package com.tactics.action;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tactics.Global;
import com.tactics.entity.Entity;
import com.tactics.gamestate.BuildOrder;

public abstract class Action_Build_Entity extends Action {

	@Override
	public void select() {
		// When we select the action to build an entity we should...
		// Check the costs. Can we afford it?
		if (!checkCosts()) {
			return;
		}
		
		// If we can, go ahead and toss together the build order
		act();
	}

	@Override
	public void act() {
		// At this point, we know that we are going to be building the entity
		Entity newEntity = getNewEntity();
		int timeToBuild = getTimeToBuild();
		
		// Construct the build order
		BuildOrder newOrder = new BuildOrder(getNewEntity(), Global.state.selectedEntity, timeToBuild);
		
		// Queue the build order
		Global.state.addBuildOrder(newOrder);
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	// Each entity is going to have some costs associated with them
	private boolean checkCosts() {
		return true;
	}
	
	protected abstract Entity getNewEntity();
	
	protected int getTimeToBuild() {
		return 0;
	}
	
}
