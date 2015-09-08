package com.tactics.entity;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tactics.action.Action;
import com.tactics.action.Action_Move;
import com.tactics.camera.CadCamera;
import com.tactics.screen.Screen_Gameplay;
import com.tactics.tile.Tile;

public abstract class Entity {

	public int x;
	public int y;
	public int width;
	public int height;
	public int team;
	public boolean onCooldown;

	protected Action [] actions;

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;

		width = 1;
		height = 1;
		onCooldown = false;
	}

	public Entity() {
		this(0, 0);
	}

	protected Point2D.Float getCameraCoordinates(CadCamera camera) {
		float camX, camY;

		// x - camera.camX is the offset between where the entity is and the camera
		camX = x - camera.camX;
		// Same for y
		camY = y - camera.camY;

		// Change from tile to pixel space
		camX *= Tile.TILE_SIZE;
		camY *= Tile.TILE_SIZE;

		// Use these as offsets from the center of the camera
		camX += camera.viewportWidth / 2;
		camY += camera.viewportHeight / 2;

		// Y offset because things are weird
		camY += 16;

		return new Point2D.Float(camX, camY);
	}

	public void setPoint(Point newPoint) {
		this.x = newPoint.x;
		this.y = newPoint.y;
	}

	public abstract void render(SpriteBatch batch, CadCamera camera);

	public abstract Sprite getIcon();

	public Action [] getActions() {
		return this.actions;
	}

	public Action [] setActions(Action [] actions) {
		synchronized(this.actions) {
			Action [] temp = this.actions;
			this.actions = actions;
			return temp;
		}
	}

	public int [] getMovementTypes() {
		Action [] actions = this.getActions();
		ArrayList<Integer> movement = new ArrayList<Integer>();

		for (int i = 0; i < actions.length; i++) {
			// Get the move actions
			if (actions[i] instanceof Action_Move) {
				Action_Move tempAction = (Action_Move) actions[i];
				int [] movementTypes = tempAction.movementType;

				// For every type, if we haven't added it already, add it
				for (int j = 0; j < movementTypes.length; j++) {
					if (!movement.contains(movementTypes[j])) {
						movement.add(movementTypes[j]);
					}
				}


			}
		}

		int [] tempArray = new int[movement.size()];
		for (int i = 0; i < movement.size(); i++) {
			tempArray[i] = movement.get(i);
		}

		return tempArray;
	}
}
