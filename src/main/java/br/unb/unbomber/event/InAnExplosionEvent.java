package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;

public class InAnExplosionEvent  implements Event {
	/* id of the entity that was hit by an explosion */
	private UUID idHit;

	private UUID explosionCause;
	
	
	public UUID getIdHit() {
		return idHit;
	}

	public void setIdHit(UUID idHit) {
		this.idHit = idHit;
	}

	public UUID getExplosionCause() {
		return explosionCause;
	}

	public void setExplosionCause(UUID explosionCause) {
		this.explosionCause = explosionCause;
	}

}
