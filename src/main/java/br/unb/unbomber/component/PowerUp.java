package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Event;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe para armazenar os Power Ups que uma entidade possui.
 * 
 * @version 0.1 20 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class PowerUp extends Component {

	/** Lista de eventos que indicam o power up coletado. */
	private List<Event> listPowerUp = new ArrayList<Event>();

	/**
	 * Construtor da classe.
	 * 
	 * @param event
	 *            Evento que indica o Power Up a ser adicionado na lista de
	 *            Power Ups deste componente da entidade.
	 */
	public PowerUp(Event event) {
		setEvent(event);
	}

	/**
	 * Adiciona um evento de Power Up para a entidade.
	 * 
	 * @param event
	 *            Evento de Power Up.
	 */
	public void setEvent(Event event) {
		this.listPowerUp.add(event);
	}

	/**
	 * Retorna o evento procurado, caso entidade não possua evento então é
	 * retornado null.
	 * 
	 * @param eventId
	 *            Id do evento procurado.
	 * 
	 * @return eachEvent Evento procurado caso entidade possuí-lo.
	 */
	public Event getEvent(int eventId) {
		if (listPowerUp != null) {
			for (Event eachEvent : listPowerUp) {
				if (eachEvent.getEventId() == eventId) {
					return eachEvent;
				}
			}
		}
		return null;
	}

	/**
	 * Retorna a possibilidade de retirar um evento da lista de Power Ups da
	 * entidade.
	 * 
	 * @return boolean Possibilidade de retirar evento da lista de Power Ups da
	 *         entidade.
	 */
	public boolean canRemoveEvent() {
		return (listPowerUp != null);
	}

	/**
	 * Remove algum evento da lista de Power Ups da entidade.
	 * 
	 * @param eventId
	 *            Id do evento procurado.
	 */
	public void removeEvent(int eventId) {
		Event eventToRemove = new Event();
		if (canRemoveEvent()) {
			for (Event eachEvent : listPowerUp) {
				if (eachEvent.getEventId() == eventId) {
					eventToRemove = eachEvent;
				}
			}
			this.listPowerUp.remove(eventToRemove);
		}
	}

	/** Remove todos os eventos de Power Up da entidade. */
	public void clearEvents() {
		this.listPowerUp.clear();
	}

}
