package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;


public class Explosive extends Component {

	private int onwnerId;
	
	private int explosionRange;

	public int getOnwnerId() {
		return onwnerId;
	}

	public void setOnwnerId(int onwnerId) {
		this.onwnerId = onwnerId;
	}

	public int getExplosionRange() {
		return explosionRange;
	}

	public void setExplosionRange(int explosionRange) {
		this.explosionRange = explosionRange;
	}
	
	
}
