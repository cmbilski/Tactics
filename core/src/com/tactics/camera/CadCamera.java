package com.tactics.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.tactics.entity.Entity;
import com.tactics.tile.Tile;

public class CadCamera extends OrthographicCamera {

	public static final float CAMERA_TRANSLATION = 32;

	public int cameraScale;

	// Location of the center of the camera in tile coordinates
	public int camX;
	public int camY;

	// Tile coordinates of the camera's upper and lower bounds on movement
	public int camYLowerBound;
	public int camXLowerBound;

	public int camYUpperBound;
	public int camXUpperBound;

	// The currently selected tile
	public int hoverX;
	public int hoverY;

	// Map information
	public int mapHeight;
	public int mapWidth;

	public CadCamera(int cameraScale, TiledMap map) {
		super();

		// Tile height and width are straight from the map
		mapWidth = (Integer) map.getProperties().get("width");
		mapHeight = (Integer) map.getProperties().get("height");

		// Lower bounds are how far we can go without the camera pushing off the map
		camXLowerBound = (int) (Gdx.graphics.getWidth() / (2.0f  * Tile.TILE_SIZE));
		camYLowerBound = (int) (Gdx.graphics.getHeight() / (2.0f * Tile.TILE_SIZE)) + 1;

		// Upper bounds are the entire size minus the lower bounds
		camXUpperBound = mapWidth - camXLowerBound;
		camYUpperBound = mapHeight - camYLowerBound + 1;

		hoverX = 10;
		hoverY = 10;
		camX = hoverX;
		camY = hoverY;

		this.cameraScale = cameraScale;
	}

	public void updateCamera() {
		// Readjust the camera
		position.x = camX * Tile.TILE_SIZE;
		// The screen does weird things with the borders
		// The y value needs to be adjusted to compensate
		position.y = camY * Tile.TILE_SIZE - 16;

		camX = camX;
		camY = camY;

		update();
	}

	public boolean inCamera(Entity entity) {
		// The camera's bounds are its x +- its width
		// And its y +- the height

		// If we are too far to the left, we obviously don't want to render it
		// It should be noted, however, that we do want to render those that positioned outside the camera but would draw themselves inside it
		if (entity.x + entity.width < camX - camXLowerBound) {
			return false;
		}

		// Same for being too far right
		// No special accommodations need to be made here
		if (entity.x > camX + camXLowerBound ) {
			return false;
		}

		// If the sprite is below the edge of the screen, don't render it
		// Again, this will require fixing to accomodate the height of the sprite
		if (entity.y + entity.height < camY - camYLowerBound) {
			return false;
		}

		// If the entity is too far up (small y), don't render
		if (entity.y > camY + camYLowerBound) {
			return false;
		}
		return true;
	}

	public void moveCameraUp() {
		if (hoverY < mapHeight - 1) {
			hoverY++;
		}
		if (camY < camYUpperBound &&
				hoverY > camYLowerBound) {
			camY++;
		}
	}

	public void moveCameraDown() {
		if (hoverY > 0) {
			hoverY--;
		}
		if (camY > camYLowerBound &&
				hoverY < mapHeight - camYLowerBound) {
			camY--;
		}
	}

	public void moveCameraLeft() {
		if (hoverX > 0) {
			hoverX--;
		}
		if (camX > camXLowerBound && 
				hoverX < mapWidth - camXLowerBound) {
			camX--;
		}
	}

	public void moveCameraRight() {
		if (hoverX < mapWidth - 1) {
			hoverX++;
		}
		if (camX < camXUpperBound && 
				hoverX > camXLowerBound) {
			camX++;
		}
	}

}
