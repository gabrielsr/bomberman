package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;
import br.unb.unbomber.gridphysics.Vector2D;

public class Movable extends Component {
	
	/** The diplacement between entity center point and cell center point */
	private Vector2D<Float> cellDisplacement;
	
	/* parametro que guarda a velocidade da entidade */
	private float speed = 1/16;
	
	public Movable(){
		this.cellDisplacement = new Vector2D<>(0.0f, 0.0f); 
	}
	
	/* metodo que retorna a volocidade da entidade */
	public float getSpeed() {
		return speed;
	}

	/* metodo que atribui a volocidade da entidade */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Vector2D<Float> getCellDisplacement() {
		return cellDisplacement;
	}

	public void setCellDisplacement(Vector2D<Float> cellDisplacement) {
		this.cellDisplacement = cellDisplacement;
	}
	
	public String toString(){
		return "{ displacement:" + this.cellDisplacement + "\n" +
					"speed:" + this.speed + "}";
				
	}

}
