package br.unb.unbomber.event;

import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.core.Event;

/**
 * Classe que indica quando uma entidade adquirir um Power Up.
 * 
 * @version 0.1 20 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
public class AcquiredPowerUpEvent extends Event {

	/** Tipo do power up. */
	private PowerType powerType;

	/**
	 * Construtor da classe.
	 * 
	 * @param powerType
	 *            Tipo do power up.
	 */
	public AcquiredPowerUpEvent(PowerType powerType) {
		setPowerUpType(powerType);
	}

	/**
	 * Coleta um tipo de power up ao evento.
	 * 
	 * @return powerType Tipo do power up.
	 */
	public PowerType getDiseaseType() {
		return powerType;
	}

	/**
	 * Atribui um tipo de power up ao evento.
	 * 
	 * @param powerType
	 *            Tipo do power up.
	 */
	public void setPowerUpType(PowerType powerType) {
		this.powerType = powerType;
	}
}