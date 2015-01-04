package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;

public abstract class CommandEvent implements Event{


	protected UUID entityUuid;
	
	
	/**
	 * ID of commanded entity
	 * 
	 * @return int (entityId)
	 */
	public UUID getEntityUuid(){
		return this.entityUuid;
	}
	
	public void setEntityUuid(UUID entityUUID) {
		this.entityUuid = entityUUID;
	}
}
