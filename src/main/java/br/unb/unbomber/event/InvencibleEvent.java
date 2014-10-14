//InvencibleEvent
// Quando uma entidade coletar o Power Up Invencible.
// Coleta a Id da entidade que coletou este power up.

/* Indica de qual pacote o arquivo pertence */
/* No caso ele pertence ao Event */
package br.unb.unbomber.event;

/* Interface para dados de Event */
import br.unb.unbomber.core.Event;

/* Heranca unica do evento InvencibleEvent */
public class InvencibleEvent extends Event {
	int sourceId;
	
	/* Construtor do Evento */
	public InvencibleEvent(int sourceId){
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
