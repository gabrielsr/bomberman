package br.unb.unbomber.component;

import br.unb.unbomber.event.MovementCommandEvent.MovementType;

public enum Direction {
	RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0), UP(0, 1);

	int x;
	int y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public static Direction asDirection(MovementType type) {

		switch (type) {

		case MOVE_RIGHT:
			return RIGHT;
		case MOVE_DOWN:
			return DOWN;
		case MOVE_LEFT:
			return LEFT;
		case MOVE_UP:
			return UP;
		default:
			return null;
		}

	}
}
