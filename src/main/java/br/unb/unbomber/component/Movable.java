package br.unb.unbomber.component;

import br.unb.gridphysics.Vector2D;

import com.artemis.Component;

public class Movable extends Component {
	
	/** The diplacement between entity center point and cell center point */
	private Vector2D<Float> cellPosition;
	
	/* parametro que guarda a velocidade da entidade */
	private float speed = 0;
	
	public Movable(){
		this.cellPosition = new Vector2D<>(0.5f, 0.5f); 
	}
	
	public Movable(float speed) {
		this.speed = speed;
		this.cellPosition = new Vector2D<>(0.5f, 0.5f); 
	}

	/* metodo que retorna a volocidade da entidade */
	public float getSpeed() {
		return speed;
	}

	/* metodo que atribui a volocidade da entidade */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public String toString(){
		return "{ cellPosition:" + this.cellPosition + "\n" +
					"speed:" + this.speed + "}";
				
	}

}
