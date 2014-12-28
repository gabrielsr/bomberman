package br.unb.unbomber.event;

import com.artemis.Entity;

import net.mostlyoriginal.api.event.common.Event;

public class ActionCommandEvent implements Event  {

	public enum ActionType{
		DROP_BOMB,
		EXPLODE_REMOTE_BOMB,
		TRIGGERS_REMOTE_BOMB; //TODO duplicated
	}
	
	private ActionType type;

	private Entity entity;
	
	public ActionCommandEvent(){
		
	}
	
	public ActionCommandEvent(ActionType type, Entity entity){
		this.type = type;
		this.entity = entity;
	}

	public ActionType getType() {
		return type;
	}
	
	public void setType(ActionType type) {
		this.type = type;
	}

	public Entity getEntity(){
		return this.entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
