package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;

public class HitWallEvent implements Event {

	private UUID entityUuid;

	public UUID getEntityUuid() {
		return entityUuid;
	}

	public void setEntityUuid(UUID entityUuid) {
		this.entityUuid = entityUuid;
	}
	
	
}
