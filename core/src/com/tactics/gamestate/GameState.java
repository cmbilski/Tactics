package com.tactics.gamestate;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.tactics.entity.Entity;
import com.tactics.tile.Tile;

public class GameState {

	public int numberOfPlayers;

	// Data structures used to keep track of the game state
	public HashMap<Point, Entity> entitiesByPoint;
	public HashMap<Point, Tile> tiles;

	public ArrayList<ArrayList<Entity>> entities;
	private ArrayList<ArrayList<BuildOrder>> constructionQueues;

	public int currentTurn;
	
	public int mapWidth;
	public int mapHeight;

	public int cursorX;
	public int cursorY;

	public Entity selectedEntity;
	public Entity hoverEntity;

	public ArrayList<Point> highlightedTiles;
	public Sprite highlightSprite = new Sprite(new Texture("textures/ui/highlight.png"));

	public GameState(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;

		// Set up the data structures needed
		this.entities = new ArrayList<ArrayList<Entity>>();
		this.entitiesByPoint = new HashMap<Point, Entity>();
		this.tiles = new HashMap<Point, Tile>();

		// Set up the construction queues
		constructionQueues = new ArrayList<ArrayList<BuildOrder>>();
		for (int i = 0; i < numberOfPlayers; i++) {
			constructionQueues.add(new ArrayList<BuildOrder>());
			entities.add(new ArrayList<Entity>());
		}
		
		currentTurn = -1;
		nextTurn();
	}
	
	public GameState() {
		this(2);
	}

	public void nextTurn() {
		// Increment the turn
		currentTurn = (currentTurn + 1) % numberOfPlayers;
		
		System.out.printf("Player %d's turn.\n", currentTurn);
		
		// Update the construction queue
		updateConstructionQueue();
		
		// Ready all units
		for (int i = 0; i < entities.get(currentTurn).size(); i++) {
			entities.get(currentTurn).get(i).onCooldown = false;
		}
	}
	
	public Entity createNewEntity(Entity newEntity) {
		entities.get(newEntity.team).add(newEntity);
		
		for (int i = 0; i < newEntity.width; i++) {
			for (int j = 0; j < newEntity.height; j++) {
				Point tempPoint = new Point(newEntity.x + i, newEntity.y  + j);
				entitiesByPoint.put(tempPoint, newEntity);
			}
		}

		return newEntity;
	}

	public Entity getEntityByTile(int x, int y) {
		return entitiesByPoint.get(new Point(x, y));
	}

	public void loadMap(TiledMap map) {
		// We need to take the data from the tiledmap and store it in a useful place for ourselves
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

		mapWidth = layer.getWidth();
		mapHeight = layer.getHeight();

		for (int i = 0; i < layer.getWidth(); i++) {
			for (int j = 0; j < layer.getHeight(); j++) {
				int id = layer.getCell(i, j).getTile().getId();
				if (id == Tile.ID_GROUND) {
					tiles.put(new Point(i, j), Tile.TILE_GROUND);
				} else if (id == Tile.ID_WATER) {
					tiles.put(new Point(i, j), Tile.TILE_WATER);
				}
			}
		}
	}

	public void moveEntity(Entity entity, Point newPoint) {
		// For each tile the unit takes, we need to clean up the old point and place it in the new point
		for (int i = 0; i < entity.width; i++) {
			for (int j = 0; j < entity.height; j++) {
				// Construct the point
				Point tempPoint = new Point(i + entity.x, j + entity.y);
				// Remove the old one
				entitiesByPoint.remove(tempPoint);
				// Place the new one
				entitiesByPoint.put(new Point(newPoint.x + i, newPoint.y + j), entity);
			}
		}

		// Set the point on the entity
		entity.setPoint(newPoint);
	}

	public boolean isTileValid(Point point, Entity entity, int [] movementTypes) {
		// We want to return whether or not the selected unit could cross the given point
		// Also, if it's in bounds
		if (point.x < 0 || point.x >= mapWidth || point.y < 0 || point.y >= mapHeight) {
			return false;
		}

		if (entitiesByPoint.get(point) != null && entitiesByPoint.get(point) != entity) {
			return false;
		}

		Tile tempTile = tiles.get(point);
		if (tempTile != null) {
			return tempTile.isValidMovement(movementTypes);
		}

		return false;
	}
	
	public boolean isTileValid(Point point, Entity entity) {
		return isTileValid(point, entity, entity.getMovementTypes());
	}

	public void highlightTiles(ArrayList<Point> tiles, Color color) {
		this.highlightedTiles = tiles;
		this.highlightSprite.setColor(color);
	}

	public void clearHighlights() {
		this.highlightedTiles = null;
	}

	public void addBuildOrder(BuildOrder newOrder) {
		// The new entity's team derives from the old one
		int team = newOrder.getBuilder().team;

		// Toss the order onto the correct queue
		constructionQueues.get(team).add(newOrder);
	}

	public void updateConstructionQueue() {
		// Get the relevant construction queue
		ArrayList<BuildOrder> queue = constructionQueues.get(currentTurn);
		
		// Update each order
		for (int i = 0; i < queue.size(); i++) {
			// Update each order
			Entity newEntity = queue.get(i).update();
			
			// If they are finished, they return their entity to be built
			if (newEntity != null) {
				// Find an open spot for the entity
				Point entityPoint = findPositionForEntity(queue.get(i).getBuilder(), newEntity);
				
				// Potentially there could be no place to put it, in that case... Break
				if (entityPoint == null) {
					continue;
				}
				
				newEntity.x = entityPoint.x;
				newEntity.y = entityPoint.y;
				
				// Have the state place it
				createNewEntity(newEntity);
				
				// Remove this order and decrement our counter
				queue.remove(i);
				i--;
			}
		}
	}

	private Point findPositionForEntity(Entity source, Entity newEntity) {
		// We need to find the closest point to the source entity to place the new one
		// BFS! BFS! BFS!
		LinkedList<Point> toExplore = new LinkedList<Point>();
		HashSet<Point> explored = new HashSet<Point>();
		
		// Initialize the queue with the points the source exists on
		for (int i = 0; i < source.width; i++) {
			for (int j = 0; j < source.height; j++) {
				Point temp = new Point(source.x + i, source.y + j);
				toExplore.add(temp);
			}
		}
		
		// We want to go until there is nothing else to search
		// If we ever break out of this, there is nowhere to put the entity and we will return null
		while (!toExplore.isEmpty()) {
			Point curPoint = toExplore.poll();
			
			// If the point we found is suitable, great! We found it
			if (isTileValid(curPoint, newEntity)) {
				return curPoint;
			}
			
			// Otherwise, we need to add the adjacent ones and keep checking
			if (curPoint.x > 0) {
				toExplore.add(new Point(curPoint.x - 1, curPoint.y));
			}
			if (curPoint.x < mapWidth) {
				toExplore.add(new Point(curPoint.x + 1, curPoint.y));
			}
			if (curPoint.y > 0) {
				toExplore.add(new Point(curPoint.x, curPoint.y - 1));
			}
			if (curPoint.y < mapHeight) {
				toExplore.add(new Point(curPoint.x, curPoint.y + 1));
			}
		}
		
		return null;
	}

}
