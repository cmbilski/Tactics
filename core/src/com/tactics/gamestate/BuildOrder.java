package com.tactics.gamestate;

import com.tactics.entity.Entity;

public class BuildOrder {

	// Entity being built
	private Entity newEntity;
	// Entity doing the building
	private Entity builder;
	// Turns left until the build is finished
	private int turnsLeft;
	
	public BuildOrder(Entity newEntity, Entity builder, int turnsLeft) {
		this.newEntity = newEntity;
		this.builder = builder;
		this.turnsLeft = turnsLeft;
		
		newEntity.team = builder.team;
	}

	// Getters
	public Entity getNewEntity() {
		return newEntity;
	}

	public Entity getBuilder() {
		return builder;
	}

	public int getTurnsLeft() {
		return turnsLeft;
	}

	public Entity update() {
		// Decrement the time left on the order
		turnsLeft--;
		
		System.out.println(turnsLeft + " turns left...");
		
		// If the order is finished, return the new entity
		if (turnsLeft <= 0) {
			return newEntity;
		}
		
		// Otherwise, nothing
		return null;
	}
	
}
