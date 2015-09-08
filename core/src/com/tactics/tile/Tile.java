package com.tactics.tile;

public class Tile {

	public static final int TILE_SIZE = 32;
	
	public static final int ID_GROUND = 156;
	public static final int ID_WATER = 69;
	
	public static final int MOVEMENT_GROUND = 1;
	public static final int MOVEMENT_WATER = 2;

	public static final Tile TILE_GROUND = new Tile(MOVEMENT_GROUND);
	public static final Tile TILE_WATER = new Tile(MOVEMENT_WATER);
	
	public int movementAllowed;
	
	public Tile(int movementAllowed) {
		this.movementAllowed = movementAllowed;
	}
	
	public boolean isValidMovement(int [] movementTypes) {
		// If any of the movement types supported are the desired type, return true
		for (int i = 0; i < movementTypes.length; i++) {
			if (movementTypes[i] == movementAllowed) {
				return true;
			}
		}
		
		// Otherwise, not supported, return false
		return false;
	}
}
