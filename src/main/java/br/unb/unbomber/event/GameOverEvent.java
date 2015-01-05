package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;

/**
 * Classe evento que indica que uma entidade nao possui mais vida e tentativas
 * de vida.
 * 
 * @version 0.3 19 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class GameOverEvent  implements Event {
	/** Id da entidade que nao possui mais vida e tentativas de vida. */
	private UUID sourceUuid;

	/**
	 * Construtor da classe.
	 * 
	 * @param hUD
	 *            Id da entidade que nao possui mais vida e tentativas de vida.
	 */
	public GameOverEvent(UUID hUD) {
		setSource(hUD);
	}

	/**
	 * Coleta a identidade da entidade que nao possui mais vida e tentativas de
	 * vida.
	 * 
	 * @return sourceId Id da entidade que nao possui mais vida e tentativas de
	 *         vida.
	 */
	public UUID getSource() {
		return sourceUuid;
	}

	/**
	 * Atribui a identidade da entidade que nao possui mais vida e tentativas de
	 * vida.
	 * 
	 * @param sourceId
	 *            Id da entidade que nao possui mais vida e tentativas de vida.
	 */
	public void setSource(UUID source) {
		this.sourceUuid = source;
	}

}
