package br.unb.unbomber.component;

import com.artemis.Component;

/**
 * Classe reponsavel por atribuir um tipo a uma entidade.
 * 
 * Tipos: Char, Monster
 * 
 * @version 0.2 19 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
public class LifeType extends Component {

	/** Os tipos que uma Entidade pode ser. */
	public enum Type {
		
		CHAR, 		/** Entidade do tipo Character */
		MONSTER,  	/** Entidade do tipo Monster */
	}

	/** O tipo da entidade */
	private Type type;
	
	/** 
	 * No arguments contructor. 
	 */
	public LifeType(){
		
	}

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
