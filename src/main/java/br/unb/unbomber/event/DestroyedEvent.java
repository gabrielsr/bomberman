package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;

/**
 * Classe evento que indica a destruição de uma entidade.
 * 
 * @version 0.3 19 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
public class DestroyedEvent  implements Event {
	/** Entidade que gerou a destruição. */
	private UUID sourceId;
	/** Entidade que foi destruída. */
	private UUID targetId;

	/**
	 * Construtor da classe.
	 * 
	 * @param sourceId
	 *            Entidade que gerou a destruição.
	 * @param targetId
	 *            Entidade que foi destruída.
	 */
	public DestroyedEvent(UUID sourceId, UUID targetId) {
		setSourceId(sourceId);
		setTargetId(targetId);
	}

	/**
	 * Coleta a identidade que gerou a destruição.
	 * 
	 * @return sourceId Entidade que gerou a destruição.
	 */
	public UUID getSourceId() {
		return sourceId;
	}

	/**
	 * Atribui a identidade que gerou a destruição.
	 * 
	 * @param sourceId
	 *            Entidade que gerou a destruição.
	 */
	public void setSourceId(UUID sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * Coleta a identidade da entidade que sera destruída.
	 * 
	 * @return targetId Entidade que foi destruída.
	 */
	public UUID getTargetId() {
		return targetId;
	}

	/**
	 * Atribui a identidade da entidade que sera destruída.
	 * 
	 * @param targetId
	 *            Entidade que foi destruída.
	 */
	public void setTargetId(UUID targetId) {
		this.targetId = targetId;
	}

}
