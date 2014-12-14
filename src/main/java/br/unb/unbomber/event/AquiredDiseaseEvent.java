package br.unb.unbomber.event;

import br.unb.unbomber.component.DiseaseComponent.DiseaseType;
import br.unb.unbomber.core.Event;

public class AquiredDiseaseEvent extends Event{

	private DiseaseType diseaseType;

	public DiseaseType getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(DiseaseType diseaseType) {
		this.diseaseType = diseaseType;
	}
}
