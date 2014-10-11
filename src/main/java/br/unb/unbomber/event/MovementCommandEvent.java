package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

public class MovementCommandEvent extends Event {

	public enum MovementType{
		MOVE_UP,
		MOVE_RIGHT,
		MOVE_DOWN,
		MOVE_LEFT;
	}

	private final MovementType type;
	
	private final int entityId;
	
	public MovementCommandEvent(MovementType type, int entityId){
		this.type = type;
		this.entityId = entityId;
	}

	public MovementType getType() {
		return type;
	}
	
	public int getEntityId(){
		return this.entityId;
	}
	
}
