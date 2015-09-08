package com.tactics.entity;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tactics.action.Action;
import com.tactics.camera.CadCamera;

public class Entity_Barracks extends Entity {

	private static final Sprite sprite = new Sprite(new Texture("textures/units/barracks.png"));
	private static final Sprite icon = new Sprite(new Texture("textures/icons/barracks.png"));
	
	public Entity_Barracks(int x, int y) {
		super(x, y);
		this.width = 2;
		this.height = 2;
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
