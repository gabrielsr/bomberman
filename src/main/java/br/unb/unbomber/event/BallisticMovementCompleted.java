package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;

public class BallisticMovementCompleted implements Event {

	/** moving entity uuid */
	private UUID target;

	public UUID getTarget() {
		return target;
	}

	public void setTarget(UUID target) {
		this.target = target;
	}
	
	
}
