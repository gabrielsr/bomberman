package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

public class ActionCommandEvent extends Event {

	public enum ActionType{
		DROP_BOMB,
		EXPLODE_REMOTE_BOMB;
	}
	
	private final ActionType type;
	
	private final int entityId;
	
	public ActionCommandEvent(ActionType type, int entityId){
		this.type = type;
		this.entityId = entityId;
	}

	public ActionType getType() {
		return type;
	}
	
	public int getEntityId(){
		return this.entityId;
	}
	
}
