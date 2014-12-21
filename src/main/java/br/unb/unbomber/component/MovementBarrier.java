package br.unb.unbomber.component;

import br.unb.entitysystem.Component;


public class MovementBarrier extends Component {
	
	public enum MovementBarrierType {
		BLOCKER, PASS_THROUGH;
	}
	
	public MovementBarrier(){
	}
	
	public MovementBarrier(MovementBarrierType type){
		this.type = type;
	}
	
	private MovementBarrierType type;

	public MovementBarrierType getType() {
		return type;
	}

	public void setType(MovementBarrierType type) {
		this.type = type;
	}
	
	
	
}
