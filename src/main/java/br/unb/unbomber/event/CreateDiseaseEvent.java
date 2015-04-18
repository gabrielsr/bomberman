package br.unb.unbomber.event;

import net.mostlyoriginal.api.event.common.Event;
import br.unb.unbomber.component.Position;

public class CreateDiseaseEvent  implements Event {

	private Position placement;

	public Position getPlacement() {
		return placement;
	}

	public void setPlacement(Position placement) {
		this.placement = placement;
	}
}
