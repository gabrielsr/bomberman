package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

public class Movable extends Component {
	/* parametro que guarda a velocidade da entidade */
	private float speed = 1/8;

	/* metodo que retorna a volocidade da entidade */
	public float getSpeed() {
		return speed;
	}

	/* metodo que atribui a volocidade da entidade */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
