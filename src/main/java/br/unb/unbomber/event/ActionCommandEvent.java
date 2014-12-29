package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;

public class ActionCommandEvent implements Event  {

	public enum ActionType{
		DROP_BOMB,
		EXPLODE_REMOTE_BOMB,
		TRIGGERS_REMOTE_BOMB; //TODO duplicated
	}
	
	private ActionType type;

	private UUID entityUUID;
	
	public ActionCommandEvent(){
		
	}
	
	public ActionCommandEvent(ActionType type, UUID entityUUID){
		this.type = type;
		this.entityUUID = entityUUID;
	}

	public ActionType getType() {
		return type;
	}
	
	public void setType(ActionType type) {
		this.type = type;
	}

	public UUID getEntityUUID(){
		return this.entityUUID;
	}
	
	public void setEntityUUID(UUID entityUUID) {
		this.entityUUID = entityUUID;
	}

}
