package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

/**
 * Classe evento que indica que uma entidade nao possui mais vida e tentativas
 * de vida.
 * 
 * @version 0.3 19 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class GameOverEvent extends Event {
	/** Id da entidade que nao possui mais vida e tentativas de vida. */
	private int sourceId;

	/**
	 * Construtor da classe.
	 * 
	 * @param sourceId
	 *            Id da entidade que nao possui mais vida e tentativas de vida.
	 */
	public GameOverEvent(int sourceId) {
		setSourceId(sourceId);
	}

	/**
	 * Coleta a identidade da entidade que nao possui mais vida e tentativas de
	 * vida.
	 * 
	 * @return sourceId Id da entidade que nao possui mais vida e tentativas de
	 *         vida.
	 */
	public int getSourceId() {
		return sourceId;
	}

	/**
	 * Atribui a identidade da entidade que nao possui mais vida e tentativas de
	 * vida.
	 * 
	 * @param sourceId
	 *            Id da entidade que nao possui mais vida e tentativas de vida.
	 */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

}
