/*
 * ExplosionStartedEvent
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

package br.unb.unbomber.event;

import net.mostlyoriginal.api.event.common.Event;
import br.unb.unbomber.component.Position;

public class ExplosionStartedEvent  implements Event {

	/* Initial position in the grid of the explosion */
	private Position initialPosition;

	/* Power of the explosion */
	private int explosionRange;

	public Position getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(Position initialPosition) {
		this.initialPosition = initialPosition;
	}

	public int getExplosionRange() {
		return explosionRange;
	}

	public void setExplosionRange(int explosionRange) {
		this.explosionRange = explosionRange;
	}

}
