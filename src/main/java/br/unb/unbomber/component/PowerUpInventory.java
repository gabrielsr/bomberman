package br.unb.unbomber.component;


import java.util.ArrayList;
import java.util.List;

import br.unb.unbomber.component.PowerUp.PowerType;

import com.artemis.Component;

/**
 * Classe reponsavel por atribuir os tipos de power ups que uma entidade possui.
 * 
 * Tipos: HealthUp, LifeUp, FireUp, BombUp, RemoteControl, PassThrougBomb,
 * SpeedUp, PassThroug, BoxingGloveAcquired, Invicible e KickAcquired.
 * 
 * @version 0.1 20 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class PowerUpInventory extends Component {

	
	/** Lista de power ups que uma entidade possui. */
	private List<PowerType> types = new ArrayList<PowerType>();

	/**
	 * Construtor da classe.
	 * 
	 */
	public PowerUpInventory() {
	}

	
	/**
	 * Adiciona um tipo de Power Up para a entidade.
	 * 
	 * @param event
	 *            Tipo de Power Up.
	 */
	public void addType(PowerType powerType) {
		this.types.add(powerType);
	}

	/**
	 * Retorna a lista de tipos de power ups que uma entidade possui.
	 * 
	 * @return listPowerUp Lista de power ups.
	 */
	public List<PowerType> getTypes() {
		return types;
	}
	
	public boolean hasType(PowerType type){
		return getTypes().contains(type);
	}

}
