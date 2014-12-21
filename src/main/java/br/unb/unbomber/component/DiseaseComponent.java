package br.unb.unbomber.component;

import br.unb.entitysystem.Component;

public class DiseaseComponent extends Component{
	
	public enum DiseaseType{
		DIARRHEA,
		CONSTIPATION,
		LOWPOWER,
		RAPIDPACE,
		SLOWPACE
	}
	
	private DiseaseType diseaseType;

	public DiseaseType getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(DiseaseType diseaseType) {
		this.diseaseType = diseaseType;
	}

}
