package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

/**
 * Classe evento que indica a destruição de uma entidade.
 * 
 * @version 0.3 19 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
public class DestroyedEvent extends Event {
	/** Entidade que gerou a destruição. */
	private int sourceId;
	/** Entidade que foi destruída. */
	private int targetId;

	/**
	 * Construtor da classe.
	 * 
	 * @param sourceId
	 *            Entidade que gerou a destruição.
	 * @param targetId
	 *            Entidade que foi destruída.
	 */
	public DestroyedEvent(int sourceId, int targetId) {
		setSourceId(sourceId);
		setTargetId(targetId);
	}

	/**
	 * Coleta a identidade que gerou a destruição.
	 * 
	 * @return sourceId Entidade que gerou a destruição.
	 */
	public int getSourceId() {
		return sourceId;
	}

	/**
	 * Atribui a identidade que gerou a destruição.
	 * 
	 * @param sourceId
	 *            Entidade que gerou a destruição.
	 */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * Coleta a identidade da entidade que sera destruída.
	 * 
	 * @return targetId Entidade que foi destruída.
	 */
	public int getTargetId() {
		return targetId;
	}

	/**
	 * Atribui a identidade da entidade que sera destruída.
	 * 
	 * @param targetId
	 *            Entidade que foi destruída.
	 */
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

}
