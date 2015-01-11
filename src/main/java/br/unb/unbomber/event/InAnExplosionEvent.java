package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;

public class InAnExplosionEvent  implements Event {
	/* id of the entity that was hit by an explosion */
	private UUID hitUuid;

	private UUID explosionCause;
	
	public UUID getHitUuid() {
		return hitUuid;
	}

	public void setHitUuid(UUID hitUuid) {
		this.hitUuid = hitUuid;
	}

	public UUID getExplosionCause() {
		return explosionCause;
	}

	public void setExplosionCause(UUID explosionCause) {
		this.explosionCause = explosionCause;
	}

}
