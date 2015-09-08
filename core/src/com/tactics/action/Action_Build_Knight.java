package com.tactics.action;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tactics.entity.Entity;
import com.tactics.entity.Entity_Knight;

public class Action_Build_Knight extends Action_Build_Entity {

	private Sprite icon = new Sprite(new Texture("textures/icons/knight.png"));

	protected Entity getNewEntity() {
		return new Entity_Knight();
	}

	@Override
	public Sprite getIcon() {
		return icon;
	}

}
