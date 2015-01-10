package br.unb.unbomber.event;

import java.util.UUID;

public class ActionCommandEvent extends CommandEvent  {

	public enum ActionType{
		DROP_BOMB,
		EXPLODE_REMOTE_BOMB,
		TRIGGERS_REMOTE_BOMB,
		THROW; //TODO duplicated
	}
	
	private ActionType type;

	public ActionCommandEvent(){
		
	}
	
	public ActionCommandEvent(ActionType type, UUID entityUUID){
		this.type = type;
		this.entityUuid = entityUUID;
	}

	public ActionType getType() {
		return type;
	}
	
	public void setType(ActionType type) {
		this.type = type;
	}

}
