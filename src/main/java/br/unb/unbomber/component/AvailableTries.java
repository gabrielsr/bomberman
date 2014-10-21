package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

/**
 * Classe para verificar quantas vidas uma entidade Character possui.
 * 
 * @version 0.1 20 Out 2014
 * @author Grupo 5 - Dayanne
 */

public class AvailableTries extends Component {

	/** Quantidade de vidas que um entidade Character possui */
	private int lifeTries;
	/**
	 * Informa True ou False caso seja permitido retirar dano de uma entidade
	 * Character.
	 */
	private boolean canTakeLife;

	/**
	 * Atribui o numero de tentativas que uma entidade Character possui.
	 * 
	 * @param lifeTries
	 */
	public void setLifeTries(int lifeTries) {
		this.lifeTries = lifeTries;
	}

	/**
	 * Coleta a quantidade de vidas que uma entidade Character possui.
	 * 
	 * @return lifeTries
	 */
	public int getLifeTries() {
		return lifeTries;
	}

	/**
	 * Atribui a possibilidade de retirar uma vida de uma entidade Character.
	 * 
	 * Logica: Apos a retirada de algum dano ao health da entity, System confere
	 * se lifeEntity e igual zero, caso seja entao e questionado a possibilidade
	 * de retirar vidas por isCanTakeLife ou, verifica a quantidade de vidas
	 * restantes por getLifeTries. Se for possivel entao e atribuido uma vida a
	 * menos no setLifeTries, e logo apos e conferido quantas vidas restantes a
	 * entidade possui para que seja atualizado o setCanTakeLife de acordo com a
	 * possibilidade.
	 * 
	 * @param canTakeLife
	 */
	public void setCanTakeLife(boolean canTakeLife) {
		this.canTakeLife = canTakeLife;
	}

	/**
	 * Coleta a possibilidade de retirar uma vida de uma entidade Character.
	 * 
	 * Logica descrita acima no metodo setCanTakeLife
	 * 
	 * @return canTakeLife
	 */
	public boolean isCanTakeLife() {
		return canTakeLife;
	}
}
