package br.unb.unbomber.component;

import br.unb.gridphysics.Vector2D;

import com.artemis.Component;

public class Position extends Component{

	private static float CELL_MIDDLE = 0.5f;
	
	private int cellX;
	
	private int cellY;

	/** An entity start at the middle of a cell */
	private Vector2D<Float> cellPosition = new Vector2D<Float>(CELL_MIDDLE, CELL_MIDDLE);

	public Position(){
		
	}
	
	public Position(int x, int y) {
		this.cellX = x;
		this.cellY = y;
	}
	
	public Position(Vector2D<Integer> cellIndex) {
		setIndex(cellIndex);
	}

	public int getCellX() {
		return cellX;
	}

	public void setCellX(int cellX) {
		this.cellX = cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public void setCellY(int cellY) {
		this.cellY = cellY;
	}
	
	
	public Vector2D<Float> getCellPosition() {
		return cellPosition ;
	}

	public void setCellPosition(Vector2D<Float> cellDisplacement) {
		this.cellPosition = cellDisplacement;
	}
	
	
	public Vector2D<Float> centerPosition(){
		return getIndex().toFloatVector().add(this.cellPosition);
	}
	
	public Vector2D<Integer> getIndex(){
		return new Vector2D<>(cellX, cellY);
	}
	
	public void setIndex(Vector2D<Integer> index){
		this.cellX = index.getX();
		this.cellY = index.getY();
	}

	public String toString(){
		return getIndex().toString();
	}
}
