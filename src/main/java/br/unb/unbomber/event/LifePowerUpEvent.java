///LifePowerUpEvent
// Evento que aumenta health de alguma entidade.
// Coleta Id de uma entidade que tenha coletado este Power Up.

/* Indica de qual pacote o arquivo pertence */
/* No caso ele pertence ao Event */
package br.unb.unbomber.event;

/* Interface para dados de Event */
import br.unb.unbomber.core.Event;

/* Heranca unica do evento LifePowerUpEvent */
public class LifePowerUpEvent extends Event {
	int sourceId;
	
	/* Construtor do Evento */
	public LifePowerUpEvent(int sourceId){
		this.sourceId = sourceId;
	}
	
	//get the id of an entity which has collected the power up
	public int getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity which has collected the power up
	public void  setSourceId(int sourceId){
		this.sourceId = sourceId;
	}

}
