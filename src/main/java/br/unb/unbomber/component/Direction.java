package br.unb.unbomber.component;

import br.unb.gridphysics.Vector2D;


public enum Direction {
	RIGHT(1, 0), 
	DOWN(0, -1), 
	LEFT(-1, 0), 
	UP(0, 1);

	int x;
	int y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public Vector2D<Integer> asVector(){
		return new Vector2D<Integer>(this.x,this.y);
	}
}
