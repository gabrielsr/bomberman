package br.unb.unbomber.robot.tasks;

import br.unb.unbomber.component.Direction;


public class ManyTurnMovement {

	private final Direction direction;
	
	private final float distance;
	
	public ManyTurnMovement(Direction direction, float distance) {
		super();
		this.direction = direction;
		this.distance = distance;
	}

	public Direction getDirection() {
		return direction;
	}

	public float getDistance() {
		return distance;
	}
	
}