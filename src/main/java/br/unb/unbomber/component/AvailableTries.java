package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

/**
 * Classe para verificar quantas vidas uma entidade possui.
 * 
 * @version 0.3 19 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class AvailableTries extends Component {

	/** Quantidade de vidas que um entidade possui. */
	private int lifeTries;
	/** Informa True ou False caso seja permitido retirar dano de uma entidade. */
	private boolean canTakeLife;

	/**
	 * Construtor da classe.
	 * 
	 * @param lifeTries
	 *            Quantidade de tentativas de vida.
	 * @param canTakeLife
	 *            Possibilidade de retirar tentativas de vida.
	 */
	public AvailableTries(int lifeTries, boolean canTakeLife) {
		setLifeTries(lifeTries);
		setCanTakeLife(canTakeLife);
	}

	/**
	 * Atribui o numero de tentativas que uma entidade possui.
	 * 
	 * @param lifeTries
	 *            Quantidade de tentativas de vida.
	 */
	public void setLifeTries(int lifeTries) {
		this.lifeTries = lifeTries;
	}

	/**
	 * Coleta a quantidade de vidas que uma entidade possui.
	 * 
	 * @return lifeTries Quantidade de tentativas de vida.
	 */
	public int getLifeTries() {
		return lifeTries;
	}

	/**
	 * Atribui a possibilidade de retirar uma vida de uma entidade.
	 * 
	 * @param canTakeLife
	 *            Possibilidade de retirar tentativas de vida.
	 */
	public void setCanTakeLife(boolean canTakeLife) {
		this.canTakeLife = canTakeLife;
	}

	/**
	 * Coleta a possibilidade de retirar uma vida de uma entidade.
	 * 
	 * @return canTakeLife Possibilidade de retirar tentativas de vida.
	 */
	public boolean isCanTakeLife() {
		return canTakeLife;
	}
}
