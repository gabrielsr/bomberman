package br.unb.unbomber.component;

import br.unb.gridphysics.Vector2D;

import com.artemis.Component;

/**
 * An entity with this component will continue a movement
 * till a collision happens
 * 
 * @author grodrigues
 *
 */
public class Velocity extends Component {

	private Vector2D<Float> movement;

	public Vector2D<Float> getMovement() {
		return movement;
	}

	public void setMovement(Vector2D<Float> movement) {
		this.movement = movement;
	}
}
