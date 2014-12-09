package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe reponsavel por atribuir os tipos de power ups que uma entidade possui.
 * 
 * Tipos: HealthUp, LifeUp, FireUp, BombUp, RemoteControl, PassThrougBomb,
 * SpeedUp, PassThroug, BoxingGloveAcquired, Invicible e KickAcquired.
 * 
 * @version 0.1 20 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class PowerUp extends Component {

	/** Os tipos de power que uma entidade pode possuir. */
	public enum PowerType {

		HEALTHUP, /** Aumenta vida da entidade. */
		LIFEUP, /** Aumenta tentativa de vida da entidade. */
		FIREUP, /** Aumenta range da explosão da bomba. */
		BOMBUP, /** Aumenta a quantidade de bomba que pode ser dropada. */
		REMOTECONTROL, /**
		 * Atribui a possibilidade de controlar o momento de
		 * explosão de uma bomba.
		 */
		PASSTHROUGBOMB, /** Atribui a possibilidade de passar por uma bomba. */
		SPEEDUP, /** Aumenta a velocidade da entidade. */
		PASSTHROUG, /** Atribui a possibilidade de passar sobre blocos. */
		BOXINGGLOVEACQUIRED, /** Atribui a possibilidade de pegar uma bomba. */
		INVINCIBLE, /** Atribui invencibilidade a entidade. */
		KICKACQUIRED;
		/** Atribui a possibilidade de chutar uma bomba. */

	}
	
	/** Lista de power ups que uma entidade possui. */
	private List<PowerType> listPowerUp = new ArrayList<PowerType>();

	/**
	 * Construtor da classe.
	 * 
	 * @param powerType
	 *            Tipo da entidade
	 */
	public PowerUp(PowerType powerType) {
		setType(powerType);
	}
	
	/**
	 * Adiciona um tipo de Power Up para a entidade.
	 * 
	 * @param event
	 *            Tipo de Power Up.
	 */
	public void setType(PowerType powerType) {
		this.listPowerUp.add(powerType);
	}


	/**
	 * Retorna a lista de tipos de power ups que uma entidade possui.
	 * 
	 * @return listPowerUp Lista de power ups.
	 */
	public List<PowerType> getTypes() {
		if (listPowerUp != null) {
			return listPowerUp;
		}
		return null;
	}

}
