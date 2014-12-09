package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

/**
 * Classe reponsavel por atribuir um tipo a uma entidade.
 * 
 * Tipos: Char, Monster, Bomb, Hard Block, Soft Block, Power Up e Disease.
 * 
 * @version 0.2 19 Nov 2014
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
		POWER_UP,	/** Entidade do tipo Power Up */
		DISEASE;	/** Entidade do tipo Disease */
	}

	/** O tipo da entidade */
	private Type type;

	/**
	 * Construtor da classe.
	 * 
	 * @param type
	 *            Tipo da entidade
	 */
	public LifeType(Type type) {
		setType(type);
	}

	/**
	 * Coleta o tipo de uma entidade.
	 * 
	 * @return type Tipo da entidade.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Atribui um tipo de uma entidade.
	 * 
	 * @param type
	 *            Tipo da entidade.
	 */
	public void setType(Type type) {
		this.type = type;
	}

}
