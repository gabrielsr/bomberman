package br.unb.unbomber.event;

import br.unb.entitysystem.Event;
import br.unb.unbomber.component.CellPlacement;

public class CreateDiseaseEvent extends Event{

	private CellPlacement placement;

	public CellPlacement getPlacement() {
		return placement;
	}

	public void setPlacement(CellPlacement placement) {
		this.placement = placement;
	}
}
