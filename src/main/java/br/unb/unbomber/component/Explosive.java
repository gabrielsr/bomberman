package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;


public class Explosive extends Component {

	private int onwnerId;
	
	private int range;

	public int getOnwnerId() {
		return onwnerId;
	}

	public void setOnwnerId(int onwnerId) {
		this.onwnerId = onwnerId;
	}

	public int getPower() {
		return range;
	}

	public void setPower(int bombRange) {
		this.range = bombRange;
	}
	
	
}
