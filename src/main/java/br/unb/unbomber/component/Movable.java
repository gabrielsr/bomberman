package br.unb.unbomber.component;

import br.unb.gridphysics.Vector2D;

import com.artemis.Component;

public class Movable extends Component {
	
	/** The displacement between entity center point and cell center point */
	private Vector2D<Float> cellPosition;
	
	/** the max speed that the entity can move */
	private float speed = 0;
	
	/** the direction that the entity is facing */
	private Direction faceDirection = Direction.UP;
	
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

	public Direction getFaceDirection() {
		return faceDirection;
	}

	public void setFaceDirection(Direction faceDirection) {
		this.faceDirection = faceDirection;
	}

	public String toString(){
		return "{ cellPosition:" + this.cellPosition + "\n" +
					"speed:" + this.speed +"\n"+ 
					"direction:"+ this.faceDirection + "}";
	}

}
