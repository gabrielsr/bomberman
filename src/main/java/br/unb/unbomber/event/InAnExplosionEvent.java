package br.unb.unbomber.event;

import net.mostlyoriginal.api.event.common.Event;

public class InAnExplosionEvent  implements Event {
	/* id of the entity that was hit by an explosion */
	private int idHit;

	public int getIdHit() {
		return idHit;
	}

	public void setIdHit(int idHit) {
		this.idHit = idHit;
	}

}
