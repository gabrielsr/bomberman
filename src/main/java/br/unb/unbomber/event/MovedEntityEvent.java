/********************************************************************************************************************************
 *Grupo 2:
 *Maxwell Moura Fernandes     - 10/0116175
 *João Paulo Araujo           -
 *Alexandre Magno             -
 *Marcelo Giordano            -
 *********************************************************************************************************************************/
package br.unb.unbomber.event;

import br.unb.unbomber.component.Direction;
import br.unb.unbomber.core.Event;

//O grupo responsável pelo módulo de movimento precisa setar OwnerId com a id da entidade que se moveu no tick
public class MovedEntityEvent extends Event {
	int entityId;
	Direction direction;
	int speed;

	// get the id of an entity which moved
	public int getEntityId() {
		return this.entityId;
	}

	// set the id of an entity which moved
	public void setId(int id) {

	}

	// TODO direção do moovimento.
	public Direction getDirection() {
		return this.direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getSpeed() {
		return this.speed;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	// TODO velocidade da entitade.

	// current position of an entity which moved in the last tick
	// CellPlacement getCellPlacement()

}
