package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;
import br.unb.unbomber.gridphysics.Vector2D;

public class CellPlacement extends Component{

	private int cellX;
	
	private int cellY;

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
	
	public Vector2D<Float> centerPosition(){
		return new Vector2D<>(cellX*1.0f + 0.5f, cellY+ 0.5f);
	}
	
	public Vector2D<Integer> getIndex(){
		return new Vector2D<>(cellX, cellY);
	}
}
