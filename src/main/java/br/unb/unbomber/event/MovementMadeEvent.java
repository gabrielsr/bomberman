package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;


public class MovementMadeEvent extends Event {

	private int NewCellX;
	private int NewCellY;

	public int getNewCellX() {
		return NewCellX;
	}

	public void setNewCellX(int newCellX) {
		NewCellX = newCellX;
	}

	public int getNewCellY() {
		return NewCellY;
	}

	public void setNewCellY(int newCellY) {
		NewCellY = newCellY;
	}  


}