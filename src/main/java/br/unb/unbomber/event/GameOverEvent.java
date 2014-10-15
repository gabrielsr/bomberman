//GameOverEvent
//Quando um Character nao possuir mais health ou availableTries.
// Coleta e atribui a Id da entidade que sera retirada do grid se Health
// for igual a zero e ainda possuir vidas ou servira como parametro para
// indicar GameOver caso a entidade nao tenha vida restante.

/* Indica de qual pacote o arquivo pertence */
/* No caso ele pertence ao Event */
package br.unb.unbomber.event;

/* Interface para dados de Event */
import br.unb.unbomber.core.Event;

/* Heranca unica do evento GameOverEvent */
public class GameOverEvent extends Event {
	int sourceId;

	/* Construtor do Evento */
	public GameOverEvent(int sourceId){
		this.sourceId = sourceId;

	}
	
	//get the ID of an entity that has lost all lives.
	public int getSourceId(){
		return sourceId;
	}
	
	//set the ID of an entity that has lost all lives.
	public void  setSourceId(int sourceId){
		this.sourceId = sourceId;
	}
	
}
