package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

/**
 * Classe reponsavel por atribuir um tipo a uma entidade.
 * 
 * Tipos: Char, Monster, Bomb, Hard Block, Soft Block e Power Up.
 * 
 * @version 0.1 20 Out 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
public class LifeType extends Component {

	/** Os tipos que uma Entidade pode ser. */
	public enum Type {
		
		CHAR, 		/** Entidade do tipo Character */
		MONSTER,  	/** Entidade do tipo Monster */
		BOMB, 		/** Entidade do tipo Bomba */
		HARD_BLOCK,	/** Entidade do tipo Hard Block */
		SOFT_BLOCK,	/** Entidade do tipo Soft Block */
		POWER_UP;	/** Entidade do tipo Power Up */

	}

	/** O tipo da entidade */
	private Type type;

	/**
	 * Construtor da classe.
	 * 
	 * @param type
	 */
	public LifeType(Type type) {
		setType(type);
	}

	/**
	 * Coleta o tipo de uma entidade.
	 * 
	 * @return type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Atribui um tipo de uma entidade.
	 * 
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
	}

}
