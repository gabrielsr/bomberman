package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

/**
 * Classe evento que indica que uma entidade nao possui mais vida e tentativas
 * de vida.
 * 
 * @version 0.2 21 Out 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class GameOverEvent extends Event {
	/** Id da entidade que nao possui mais vida e tentativas de vida. */
	int sourceId;

	/**
	 * Construtor da classe.
	 * 
	 * @param sourceId
	 */
	public GameOverEvent(int sourceId) {
		setSourceId(sourceId);
	}

	/**
	 * Coleta a identidade da entidade que nao possui mais vida e tentativas de
	 * vida.
	 * 
	 * @return sourceId
	 */
	public int getSourceId() {
		return sourceId;
	}

	/** 
	 * Atribui a identidade da entidade que nao possui mais vida e tentativas de
	 * vida.
	 * 
	 * @return sourceId
	 */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

}
