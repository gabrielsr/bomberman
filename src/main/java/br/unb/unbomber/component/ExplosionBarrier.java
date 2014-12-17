package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;


public class ExplosionBarrier extends Component {
	
	public enum ExplosionBarrierType {

		BLOCKER, STOPPER, PASS_THROUGH;
		
	}

	
	private ExplosionBarrierType type;

	public ExplosionBarrier(){
		
	}
	
	public ExplosionBarrier(ExplosionBarrierType type){
		this.type = type;
	}

	public ExplosionBarrierType getType() {
		return type;
	}

	public void setType(ExplosionBarrierType type) {
		this.type = type;
	}
	
	
	
}
