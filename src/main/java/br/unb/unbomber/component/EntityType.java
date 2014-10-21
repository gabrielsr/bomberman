package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

/**
 * Classe reponsavel por atribuir um tipo a uma entidade.
 * 
 * Tipos: Char, Monster, Bomb, Hard Block, Soft Block e Power Up.
 * 
 * @version 0.1 20 Out 2014
 * @author Grupo 5 - Dayanne
 */
public class EntityType extends Component {

	/** Os tipos que uma Entidade pode ser. */
	public enum EntType {

		CHAR, MONSTER, BOMB, HARD_BLOCK, SOFT_BLOCK, POWER_UP;

	}

	/** O tipo da entidade */
	private EntType entType;
	
	/**
	 * Construtor da classe.
	 * 
	 * @param entType  
	 */
	public EntityType (EntType entType){
		setEntType(entType);
	}

	/**
	 * Coleta o tipo de uma entidade.
	 * 
	 * @return entType
	 */
	public EntType getEntType() {
		return entType;
	}

	/**
	 * Atribui um tipo de uma entidade.
	 * 
	 * @param entType
	 */
	public void setEntType(EntType entType) {
		this.entType = entType;
	}

}
