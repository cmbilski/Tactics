package com.tactics.screen;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.tactics.Global;
import com.tactics.camera.CadCamera;
import com.tactics.entity.Entity;
import com.tactics.entity.Entity_Knight;
import com.tactics.gamestate.BuildOrder;
import com.tactics.gamestate.GameState;
import com.tactics.input.Input_Gameplay;
import com.tactics.tile.Tile;
import com.tactics.ui.UI_Element;
import com.tactics.ui.UI_EntityDetails;
import com.tactics.ui.UI_SelectEntity;

public class Screen_Gameplay implements Screen {

	private static Sprite cursorSprite = new Sprite(new Texture("textures/ui/cursor.png"));

	public static final int MAP_SCALE = 1;

	public int mapPixelHeight;
	public int mapPixelWidth;

	// Size of the map in tile coordinates
	public int mapHeight;
	public int mapWidth;

	public GameState state;

	private TiledMap map;
	private OrthogonalTiledMapRenderer tmxRenderer;
	private CadCamera camera;

	private SpriteBatch batch;

	// UI modules
	public ArrayList<UI_Element> uiElements;

	public Screen_Gameplay() {
		Global.random = new Random(System.currentTimeMillis());
		map = new TmxMapLoader().load("tmx/test2.tmx");

		// Set up the camera
		Global.camera = new CadCamera(MAP_SCALE, map);
		Global.state = new GameState();
		camera = Global.camera;
		camera.setToOrtho(false, Gdx.graphics.getWidth() * MAP_SCALE, Gdx.graphics.getHeight() * MAP_SCALE);


		mapPixelWidth = mapWidth * Tile.TILE_SIZE;
		mapPixelHeight = mapHeight * Tile.TILE_SIZE;

		tmxRenderer = new OrthogonalTiledMapRenderer(map);
		state = Global.state;

		state.loadMap(map);

		batch  = new SpriteBatch();

		InputMultiplexer inputHandler = new InputMultiplexer();
		inputHandler.addProcessor(new Input_Gameplay(this));

		Gdx.input.setInputProcessor(inputHandler);

		uiElements = new ArrayList<UI_Element>();
		uiElements.add(new UI_EntityDetails(state));
		uiElements.add(new UI_SelectEntity(state));

		Entity knight = new Entity_Knight(10, 10);
		state.createNewEntity(knight);
		BuildOrder temp = new BuildOrder(new Entity_Knight(11, 12), knight, 2);
		state.addBuildOrder(temp);
	}

	public void update(float delta) {
		camera.updateCamera();

		// Update the current hovered over entity
		state.hoverEntity = state.getEntityByTile(camera.hoverX, camera.hoverY);
	}

	public void render(float delta) {
		update(delta);
		tmxRenderer.setView(camera);
		tmxRenderer.render();

		batch.begin();

		// Render each entity in the frame
		for (int i = 0; i < state.entities.size(); i++) {
			// We have to go through each list of entities
			ArrayList<Entity> entities = state.entities.get(i);
			for (int j = 0; j < entities.size(); j++) {
				if (camera.inCamera(entities.get(j))) {
					entities.get(j).render(batch, camera);
				}
			}
		}

		// Render the highlights
		renderHighlights();

		// Render the cursor
		// Translate its coordinates to camera tile coordinates
		int cursorX = camera.hoverX - camera.camX;
		int cursorY = camera.hoverY - camera.camY;

		cursorSprite.setPosition((cursorX * Tile.TILE_SIZE) + Gdx.graphics.getWidth() / 2, 
				16 + (cursorY * Tile.TILE_SIZE) + Gdx.graphics.getHeight() / 2);
		cursorSprite.draw(batch);

		// Render all of the ui elements
		for (int i = 0; i < uiElements.size(); i++) {
			uiElements.get(i).render(delta, batch);
		}


		batch.end();

	}

	private void renderHighlights() {
		// If we have any highlights...
		if (state.highlightedTiles != null) {
			// Render the highlights
			for (int i = 0; i < state.highlightedTiles.size(); i++) {
				// Calculate the offsets from the center of the camera
				int offX = camera.camX - state.highlightedTiles.get(i).x;
				int offY = camera.camY - state.highlightedTiles.get(i).y;

				state.highlightSprite.setPosition(camera.viewportWidth / 2 - offX * Tile.TILE_SIZE, 
						camera.viewportHeight / 2 - offY * Tile.TILE_SIZE + 16);
				state.highlightSprite.draw(batch);
			}
		}
	}



	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}



}
