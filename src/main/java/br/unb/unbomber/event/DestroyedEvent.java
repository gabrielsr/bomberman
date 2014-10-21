package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

/**
 * Classe evento que indica que uma entidade nao possui mais vida.
 * 
 * @version 0.2 21 Out 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
public class DestroyedEvent extends Event {
	/** Id da entidade que sera destruida. */
	int sourceId;

	/**
	 * Construtor da classe.
	 * 
	 * @param sourceId
	 */
	public DestroyedEvent(int sourceId) {
		setSourceId(sourceId);
	}

	/**
	 * Coleta a identidade da entidade que sera destroida.
	 * 
	 * @return sourceId
	 */
	public int getSourceId() {
		return sourceId;
	}

	/**
	 * Atribui a identidade da entidade que sera destroida.
	 * 
	 * @return sourceId
	 */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

}
