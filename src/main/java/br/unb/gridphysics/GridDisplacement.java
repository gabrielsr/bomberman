package br.unb.gridphysics;

import br.unb.unbomber.component.Direction;

public class GridDisplacement {

	private Vector2D<Integer> cells;
	
	private Vector2D<Float> position;
	
	/** direction of the movement */
	private Direction faceDirection;
	
	public GridDisplacement(){
		
	}
	
	public GridDisplacement(Vector2D<Integer> cells, Vector2D<Float> position){
		this.setCells(cells);
		this.setPosition(position);
	}

	public GridDisplacement(Vector2D<Integer> cells,
			Vector2D<Float> position, Direction faceDirection) {
		this.setCells(cells);
		this.setPosition(position);
		this.setFaceDirection(faceDirection);
	}

	public Vector2D<Integer> getCells() {
		return cells;
	}

	public void setCells(Vector2D<Integer> cells) {
		this.cells = cells;
	}

	public Vector2D<Float> getPosition() {
		return position;
	}

	public void setPosition(Vector2D<Float> position) {
		this.position = position;
	}

	public Direction getFaceDirection() {
		return faceDirection;
	}

	public void setFaceDirection(Direction faceDirection) {
		this.faceDirection = faceDirection;
	}
}
