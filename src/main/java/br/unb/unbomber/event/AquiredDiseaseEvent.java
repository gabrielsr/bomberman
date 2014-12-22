package br.unb.unbomber.event;

import net.mostlyoriginal.api.event.common.Event;
import br.unb.unbomber.component.DiseaseComponent.DiseaseType;

public class AquiredDiseaseEvent implements Event {

	private DiseaseType diseaseType;

	public DiseaseType getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(DiseaseType diseaseType) {
		this.diseaseType = diseaseType;
	}
}
