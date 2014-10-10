package br.unb.unbomber.event;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.core.Event;

public class ExplosionStartedEvent extends Event{

	/* Initial position in the grid of the explosion */
	private CellPlacement initialPosition;

	/* Power of the explosion */
	private int explosionRange;

	public CellPlacement getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(CellPlacement initialPosition) {
		this.initialPosition = initialPosition;
	}

	public int getExplosionRange() {
		return explosionRange;
	}

	public void setExplosionRange(int explosionRange) {
		this.explosionRange = explosionRange;
	}

}
