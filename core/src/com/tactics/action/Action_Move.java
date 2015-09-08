package com.tactics.action;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tactics.Global;
import com.tactics.Util;
import com.tactics.entity.Entity;
import com.tactics.gamestate.GameState;
import com.tactics.input.Input_MoveAction;
import com.tactics.tile.Tile;

public class Action_Move extends Action {

	private Sprite icon = new Sprite(new Texture("textures/icons/move.png"));
	private static final Color HIGHLIGHT_COLOR = Color.CYAN;

	protected int range = 3;
	public int [] movementType = new int[] {Tile.MOVEMENT_GROUND};
	protected ArrayList<Point> validTiles;

	private static final Input_MoveAction input = new Input_MoveAction();
	
	public void select() {
		// Get the selected entity
		Entity entity = Global.state.selectedEntity;
		
		// We want to get all tiles within the range of the unit
		validTiles = new ArrayList<Point>();

		findValidMoves(validTiles, new Point(entity.x, entity.y), range, -1, Global.state);
		validTiles.remove(new Point(entity.x, entity.y));
		Global.state.highlightTiles(validTiles, HIGHLIGHT_COLOR);
		
		// Attach our own listener to the chain
		input.action = this;
		Util.addInputProcessor(input);
	}

	private void findValidMoves(ArrayList<Point> valid, Point cur, int range, int current, 
			GameState state) {
		// If we are out our range limit, we are done
		if (current >= range) {
			return;
		}

		// If the current tile isn't passable, we are done
		if (!state.isTileValid(cur, state.selectedEntity, movementType)) {
			return;
		}

		// Don't double-count
		if (!valid.contains(cur)) {
			valid.add(cur);
		}

		findValidMoves(valid, new Point(cur.x + 1, cur.y), range, current + 1, state);
		findValidMoves(valid, new Point(cur.x - 1, cur.y), range, current + 1, state);
		findValidMoves(valid, new Point(cur.x, cur.y + 1), range, current + 1, state);
		findValidMoves(valid, new Point(cur.x, cur.y - 1), range, current + 1, state);
	}

	@Override
	public void act() {
		// We want to get the current tile the camera is over
		int hoverX = Global.camera.hoverX;
		int hoverY = Global.camera.hoverY;
		
		Point newPoint = new Point(hoverX, hoverY);
		
		// If it's a valid move, move to it
		if (validTiles.contains(newPoint)) {
			Global.state.moveEntity(Global.state.selectedEntity, newPoint);
			
			// Remove cancel this ui now, we're done moving
			cancel();
			
			// Clear the selected entity
			Global.state.selectedEntity = null;
		}
	}

	@Override
	public Sprite getIcon() {
		return icon;
	}

	@Override
	public void cancel() {
		// Clear out the highlighted tiles
		Global.state.clearHighlights();
		// Remove our listener
		Util.removeInputProcessor(input);
	}

}
