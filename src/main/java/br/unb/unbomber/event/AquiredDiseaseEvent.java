package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;
import br.unb.unbomber.component.DiseaseComponent.DiseaseType;

public class AquiredDiseaseEvent implements Event {

	private DiseaseType diseaseType;
	
	private UUID ownerEntityId;

	public AquiredDiseaseEvent() {
		
	}
	
	public DiseaseType getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(DiseaseType diseaseType) {
		this.diseaseType = diseaseType;
	}

	public UUID getOwnerEntityId() {
		return ownerEntityId;
	}

	public void setOwnerEntityId(UUID ownerEntityId) {
		this.ownerEntityId = ownerEntityId;
	}
}
