package br.unb.unbomber.event;


public class InAnExplosionEvent {

	/* id of the entity that was hit by an explosion */
	private int idHit;

	/*
	 * id of the character that dropped the bomb that exploded and hit one
	 * entity
	 */
	private int idSource;

	public int getIdHit() {
		return idHit;
	}

	public void setIdHit(int idHit) {
		this.idHit = idHit;
	}

	public int getIdSource() {
		return idSource;
	}

	public void setIdSource(int idSource) {
		this.idSource = idSource;
	}

}
