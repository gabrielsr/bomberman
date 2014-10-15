//DestroyedEvent
// Quando uma entidade nao conter mais health.
// Coleta a Id da entidade que sera destruida do grid.

/* Indica de qual pacote o arquivo pertence */
/* No caso ele pertence ao Event */
package br.unb.unbomber.event;

/* Interface para dados de Event */
import br.unb.unbomber.core.Event;

/* Heranca unica do evento DestroyedEvent */
public class DestroyedEvent extends Event {
	int sourceId;

	/* Construtor do Evento */
	public DestroyedEvent(int sourceId) {
		this.sourceId = sourceId;
	}

	// get the id of an entity which will be destroyed
	public int getSourceId() {
		return sourceId;
	}

	// set the id of an entity which will be destroyed
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

}
