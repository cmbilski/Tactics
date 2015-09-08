package com.tactics.entity;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tactics.action.Action;
import com.tactics.camera.CadCamera;

public class Entity_Knight extends Entity {

	private static final Sprite sprite = new Sprite(new Texture("textures/knight.png"));
	private static final Sprite icon = new Sprite(new Texture("textures/icons/knight.png"));

	private Action [] actions = {Action.ACTION_ATTACK, Action.ACTION_MOVE, Action.ACTION_BUILDLIST_BARRACKS};

	public Entity_Knight(int x, int y) {
		super(x, y);
		super.actions = actions;
	}
	
	public Entity_Knight() {
		super.actions = actions;
	}

	@Override
	public void render(SpriteBatch batch, CadCamera camera) {
		Point2D.Float coords = getCameraCoordinates(camera);

		sprite.setPosition(coords.x, coords.y);
		sprite.draw(batch);
	}

	@Override
	public Sprite getIcon() {
		return icon;
	}
}
