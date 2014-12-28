package br.unb.unbomber.event;

import net.mostlyoriginal.api.event.common.Event;

import com.artemis.Entity;

/**
 * Classe evento que indica que uma entidade nao possui mais vida e tentativas
 * de vida.
 * 
 * @version 0.3 19 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class GameOverEvent  implements Event {
	/** Id da entidade que nao possui mais vida e tentativas de vida. */
	private Entity source;

	/**
	 * Construtor da classe.
	 * 
	 * @param hUD
	 *            Id da entidade que nao possui mais vida e tentativas de vida.
	 */
	public GameOverEvent(Entity hUD) {
		setSource(hUD);
	}

	/**
	 * Coleta a identidade da entidade que nao possui mais vida e tentativas de
	 * vida.
	 * 
	 * @return sourceId Id da entidade que nao possui mais vida e tentativas de
	 *         vida.
	 */
	public Entity getSource() {
		return source;
	}

	/**
	 * Atribui a identidade da entidade que nao possui mais vida e tentativas de
	 * vida.
	 * 
	 * @param sourceId
	 *            Id da entidade que nao possui mais vida e tentativas de vida.
	 */
	public void setSource(Entity source) {
		this.source = source;
	}

}
