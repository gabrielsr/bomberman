package br.unb.unbomber.event;

import br.unb.unbomber.component.CellPlacement;

public class ExplosionStartedEvent {

	/* Initial position in the grid of the explosion */
	private CellPlacement initialPosition;

	/* Power of the explosion */
	private int power;

	public CellPlacement getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(CellPlacement initialPosition) {
		this.initialPosition = initialPosition;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

}
