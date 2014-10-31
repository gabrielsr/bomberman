package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

public class Movable extends Component {
	/* parametro que guarda a velocidade da entidade */
	private int speed;

	/* metodo que retorna a volocidade da entidade */
	public int getSpeed() {
		return speed;
	}

	/* metodo que atribui a volocidade da entidade */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
