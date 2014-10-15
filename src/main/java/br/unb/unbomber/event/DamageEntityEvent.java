//DamageEntityEvent
//Quando uma entidade tomar dano de uma outra entidade.
// Coleta a Id que recebeu dano e a Id e levou dano.

/* Indica de qual pacote o arquivo pertence */
/* No caso ele pertence ao Event */
package br.unb.unbomber.event;

/* Interface para dados de Event */
import br.unb.unbomber.core.Event;

/* Heranca unica do evento DamageEntityEvent */
public class DamageEntityEvent extends Event {
	int sourceId;
	int targetId;
	
	/* Construtor do Evento */
	public DamageEntityEvent(int sourceId, int targetId){
		this.sourceId = sourceId;
		this.targetId = targetId;
	}
	
	//get the id of an entity which has taken the damage
	public int getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity which has taken the damage
	public void  setSourceId(int sourceId){
		this.sourceId = sourceId;
	}
	
	//get the id of an entity which has caused the damage
	public int  getTargetId(){
		return targetId;
	}
	
	//set the id of an entity which has caused the damage
	public void  setTarget(int sourceId){
		this.targetId = sourceId;
	}

}
