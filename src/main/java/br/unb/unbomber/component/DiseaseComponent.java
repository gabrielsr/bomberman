package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

public class DiseaseComponent extends Component{
	
	public enum DiseaseType{
		CHANGEPOSITION,
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
