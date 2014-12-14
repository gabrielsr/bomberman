package br.unb.unbomber.event;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.core.Event;

public class CreateDiseaseEvent extends Event{

	private CellPlacement placement;

	public CellPlacement getPlacement() {
		return placement;
	}

	public void setPlacement(CellPlacement placement) {
		this.placement = placement;
	}
}
