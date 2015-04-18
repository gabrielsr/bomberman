/********************************************************************************************************************************
 *Grupo 2:
 *Maxwell Moura Fernandes     - 10/0116175
 *João Paulo Araujo           -
 *Alexandre Magno             -
 *Marcelo Giordano            -
 *********************************************************************************************************************************/
package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Direction;

/**
 * Event fired when a entity was moved
 * @author grodrigues
 *
 */
public class MovedEntityEvent  implements Event {

	private UUID movedEntityUUID;

	private Direction direction;
	
	private float speed;
	
	private Vector2D<Integer> destinationCell;

	private Vector2D<Float> displacement;
	
	// get the id of an entity which moved
	public UUID getMovedEntityUUID() {
		return this.movedEntityUUID;
	}

	// set the id of an entity which moved
	public void setMovedEntityUUID(UUID movedEntityUUID) {
		this.movedEntityUUID = movedEntityUUID;
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
