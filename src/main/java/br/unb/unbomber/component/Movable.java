package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;
import br.unb.unbomber.gridphysics.Vector2D;

public class Movable extends Component {
	
	/** The diplacement between entity center point and cell center point */
	private Vector2D<Float> cellPosition;
	
	/* parametro que guarda a velocidade da entidade */
	private float speed = 1/16;
	
	public Movable(){
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
	
	public Vector2D<Float> getCellPosition() {
		return cellPosition;
	}

	public void setCellPosition(Vector2D<Float> cellDisplacement) {
		this.cellPosition = cellDisplacement;
	}
	
	public String toString(){
		return "{ cellPosition:" + this.cellPosition + "\n" +
					"speed:" + this.speed + "}";
				
	}

}
