package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

/**
 * Classe evento que indica a destruição de uma entidade.
 * 
 * @version 0.2 21 Out 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
public class DestroyedEvent extends Event {
	/** Id da entidade que sera destruida. */
	int sourceId;
	int targetId;

	/**
	 * Construtor da classe.
	 * 
	 * @param sourceId
	 */
	public DestroyedEvent(int sourceId, int targetId) {
		setSourceId(sourceId);
		setTargetId(targetId);
	}
	
	/**
	 * Coleta a identidade que gerou a destruição.
	 * 
	 * @return sourceId
	 */
	public int getSourceId() {
		return sourceId;
	}

	/**
	 * Atribui  a identidade que gerou a destruição.
	 * 
	 * @return sourceId
	 */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	
	/**
	 * Coleta a identidade da entidade que sera destruída.
	 * 
	 * @return targetId
	 */
	public int getTargetId() {
		return targetId;
	}

	/**
	 * Atribui a identidade da entidade que sera destruída.
	 * 
	 * @return targetId
	 */
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

}
