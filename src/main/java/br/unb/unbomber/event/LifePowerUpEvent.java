///LifePowerUpEvent
// Evento que aumenta health de alguma entidade.
// Coleta Id de uma entidade que tenha coletado este Power Up.

package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;


/**
 * Classe para verificar a ocorrencia powerUp na entidade
 * 
 * @version 0.1 14 Out 2014
 * @author Grupo 5
 */

/* Heranca unica do evento LifePowerUpEvent */
public class LifePowerUpEvent extends Event {
	int sourceId;

	/* Construtor do Evento */
	public LifePowerUpEvent(int sourceId) {
		this.sourceId = sourceId;
	}

	// get the id of an entity which has collected the power up
	public int getSourceId() {
		return sourceId;
	}

	// set the id of an entity which has collected the power up
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

}
