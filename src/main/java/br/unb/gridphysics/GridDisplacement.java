package br.unb.gridphysics;

public class GridDisplacement {

	private Vector2D<Integer> cells;
	
	private Vector2D<Float> position;
	
	public GridDisplacement(){
		
	}
	
	public GridDisplacement(Vector2D<Integer> cells, Vector2D<Float> position){
		this.setCells(cells);
		this.setPosition(position);
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
}
