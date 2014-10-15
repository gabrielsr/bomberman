package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

public class InAnExplosionEvent extends Event {
	/* id of the entity that was hit by an explosion */
	private int idHit;

	public int getIdHit() {
		return idHit;
	}

	public void setIdHit(int idHit) {
		this.idHit = idHit;
	}

}
