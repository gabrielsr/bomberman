package br.unb.unbomber.event;

import net.mostlyoriginal.api.event.common.Event;

public class ActionCommandEvent implements Event  {

	public enum ActionType{
		DROP_BOMB,
		EXPLODE_REMOTE_BOMB,
		TRIGGERS_REMOTE_BOMB; //TODO duplicated
	}
	
	private ActionType type;

	private int entityId;
	
	public ActionCommandEvent(){
		
	}
	
	public ActionCommandEvent(ActionType type, int entityId){
		this.type = type;
		this.entityId = entityId;
	}

	public ActionType getType() {
		return type;
	}
	
	public void setType(ActionType type) {
		this.type = type;
	}

	public int getEntityId(){
		return this.entityId;
	}
	
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

}
