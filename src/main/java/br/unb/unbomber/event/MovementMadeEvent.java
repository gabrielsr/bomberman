package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

public class MovementMadeEvent extends Event {
	/* variaveis que guardam o novo posicionamento da entidade no grid */
	private int NewCellX;
	private int NewCellY;

	/* metodo que retorna a posicao do eixo x no grid */
	public int getNewCellX() {
		return NewCellX;
	}

	/* metodo que atribui o novo posicionamento no eixo x da entidade */
	public void setNewCellX(int newCellX) {
		NewCellX = newCellX;
	}

	/* metodo que retorna a posicao do eixo y no grid */
	public int getNewCellY() {
		return NewCellY;
	}

	/* metodo que atribui o novo posicionamento no eixo y da entidade */
	public void setNewCellY(int newCellY) {
		NewCellY = newCellY;
	}

}