/********************************************************************************************************************************
 *Grupo 2:
 *Maxwell Moura Fernandes     - 10/0116175
 *João Paulo Araujo           -
 *Alexandre Magno             -
 *Marcelo Giordano            -
 *********************************************************************************************************************************/
package br.unb.unbomber.event;

import br.unb.entitysystem.Event;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Direction;

//O grupo responsável pelo módulo de movimento precisa setar OwnerId com a id da entidade que se moveu no tick
public class MovedEntityEvent extends Event {

	private int entityId;

	private Direction direction;
	
	private float speed;
	
	private Vector2D<Integer> destinationCell;

	private Vector2D<Float> displacement;
	
	// get the id of an entity which moved
	public int getMovedEntityId() {
		return this.entityId;
	}

	// set the id of an entity which moved
	public void setMovedEntityId(int id) {
		this.entityId = id;
	}

	//
	public Direction getDirection() {
		return this.direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public float getSpeed() {
		return this.speed;
	}
	public void setSpeed(float speed2){
		this.speed = speed2;
	}

	// Position that the entity is trying to go

	public Vector2D<Integer> getDestinationCell() {
		return destinationCell;
	}

	public void setDestinationCell(Vector2D<Integer> index) {
		this.destinationCell = index;
	}

	public Vector2D<Float> getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Vector2D<Float> displacement) {
		this.displacement = displacement;
	}

}
