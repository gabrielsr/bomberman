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

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;
import br.unb.gridphysics.Vector2D;

public class ExplosionStartedEvent  implements Event {

	/* Initial position in the grid of the explosion */
	private Vector2D<Integer> initialPosition;

	private UUID creatorUUID;
	
	/* Power of the explosion */
	private int explosionRange;


	public int getExplosionRange() {
		return explosionRange;
	}

	public void setExplosionRange(int explosionRange) {
		this.explosionRange = explosionRange;
	}

	public UUID getCreatorUUID() {
		return creatorUUID;
	}

	public void setCreatorUUID(UUID creatorUUID) {
		this.creatorUUID = creatorUUID;
	}

	public Vector2D<Integer> getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(Vector2D<Integer> initialPosition) {
		this.initialPosition = initialPosition;
	}

}
