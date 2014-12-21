package br.unb.unbomber.event;

import br.unb.entitysystem.Event;
import br.unb.unbomber.component.DiseaseComponent.DiseaseType;

public class AquiredDiseaseEvent extends Event{

	private DiseaseType diseaseType;

	public DiseaseType getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(DiseaseType diseaseType) {
		this.diseaseType = diseaseType;
	}
}
