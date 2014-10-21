package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;


/**
 * Classe que indica quando uma entidade coletar um Power Up do tipo Life.
 * 
 * @version 0.2 21 Out 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
public class LifePowerUpEvent extends Event {
	/** Id da entidade que coletou o Power Up. */
	int sourceId;

	/**
	 * Construtor da classe.
	 * 
	 * @param sourceId
	 */
	public LifePowerUpEvent(int sourceId) {
		setSourceId(sourceId);
	}

	/**
	 * Coleta a identidade da entidade que coletou o Power Up.
	 * 
	 * @return sourceId
	 */
	public int getSourceId() {
		return sourceId;
	}

	/**
	 * Atribui a identidade da entidade que coletou o Power Up.
	 * 
	 * @return sourceId
	 */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

}
