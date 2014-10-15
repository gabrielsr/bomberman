package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;


public class Explosive extends Component {

	private int ownerId;
	
	private int explosionRange;

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getExplosionRange() {
		return explosionRange;
	}

	public void setExplosionRange(int explosionRange) {
		this.explosionRange = explosionRange;
	}
	
	
}
