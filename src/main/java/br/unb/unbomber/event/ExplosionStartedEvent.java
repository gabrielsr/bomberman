package events;

import components.CellPosition;

public class ExplosionStartedEvent {

	/* Initial position in the grid of the explosion */
	private CellPosition initialPosition;

	/* Power of the explosion */
	private int power;

	public CellPosition getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(CellPosition initialPosition) {
		this.initialPosition = initialPosition;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

}
