package br.unb.unbomber.event;

import br.unb.unbomber.component.*;

import br.unb.unbomber.event.MovementCommandEvent.MovementType;

public class MovementMadeEvent extends Movable {

	public int getSpeed(){
		return speed;
	}

	int ID=getEntityId();

	private MovementType Muv = getDirection();

	public MovementType getDirection(){
		return Muv;
	}

	CellPlacement Coord = new CellPlacement();

	public void MakeMovement(){
		int x = Coord.getCellX();
		int y = Coord.getCellY();  

		if(Muv==MovementType.MOVE_UP){
			Coord.setCellY(y+speed);
		}

		if(Muv==MovementType.MOVE_DOWN){
			Coord.setCellY(y-speed);
		}

		if(Muv==MovementType.MOVE_RIGHT){
			Coord.setCellY(x+speed);
		}

		if(Muv==MovementType.MOVE_LEFT){
			Coord.setCellY(x-speed);
		}

	}

}