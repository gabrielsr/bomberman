package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

/**
 * Classe evento que indica que uma entidade tomou dano de uma outra entidade.
 * 
 * @version 0.2 21 Out 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
public class DamageEntityEvent extends Event {
	/** Id da entidade que realizou o dano. */
	int sourceId;
	/** Id da entidade que sofreu o dano. */
	int targetId;

	/**
	 * Construtor da classe.
	 * 
	 * @param sourceId  
	 * @param targetId
	 */
	public DamageEntityEvent(int sourceId, int targetId) {
		setSourceId(sourceId);
		setTarget(targetId);
	}

	/** 
	 * Coleta a identidade da entidade que realizou o dano. 
	 * 
	 * @return sourceId
	 */
	public int getSourceId() {
		return sourceId;
	}

	/** 
	 * Atribui a identidade da entidade que realizou o dano.  
	 * 
	 * @param sourceId
	 */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	/** 
	 * Coleta a identidade da entidade que sofreu o dano. 
	 * 
	 * @return targetId
	 */
	public int getTargetId() {
		return targetId;
	}

	/** 
	 * Atribui a identidade da entidade que sofreu o dano. 
	 * 
	 * @param targetId
	 */
	public void setTarget(int sourceId) {
		this.targetId = sourceId;
	}

}
