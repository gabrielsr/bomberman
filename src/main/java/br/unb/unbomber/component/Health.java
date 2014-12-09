package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

/**
 * Classe para verificar se houve dano a uma entidade.
 * 
 * @version 0.3 19 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class Health extends Component {

	/** Health de uma entidade Character ou Monster. */
	private int lifeEntity;
	/**
	 * Coleta True ou False caso seja permitido retirar dano de uma entidade
	 * monster ou character.
	 */
	private boolean canTakeDamaged;

	/**
	 * Construtor da classe.
	 * 
	 * @param lifeEntity
	 *            Vida da entidade.
	 * @param canTakeDamaged
	 *            Possibilidade de retirar vida da entidade.
	 */
	public Health(int lifeEntity, boolean canTakeDamaged) {
		setLifeEntity(lifeEntity);
		setCanTakeDamaged(canTakeDamaged);
	}

	/**
	 * Inicializa vida completa a uma entidade Monster ou Entity.
	 * 
	 * @param lifeEntity
	 *            Vida da entidade.
	 */
	public void setLifeEntity(int lifeEntity) {
		this.lifeEntity = lifeEntity;
	}

	/**
	 * Coleta a quantidade de vida que uma entidade possui.
	 * 
	 * @return lifeEntity Vida da entidade.
	 */
	public int getLifeEntity() {
		return lifeEntity;
	}

	/**
	 * Atribui a possibilidade de causar dano a uma entidade.
	 * 
	 * @param canTakeDamaged
	 *            Possibilidade de retirar vida da entidade.
	 */
	public void setCanTakeDamaged(boolean canTakeDamaged) {
		this.canTakeDamaged = canTakeDamaged;
	}

	/**
	 * Coleta a possibilidade de causar dano a uma entidade.
	 * 
	 * @return canTakeDamaged Possibilidade de retirar vida da entidade.
	 */
	public boolean isCanTakeDamaged() {
		return canTakeDamaged;
	}
}
