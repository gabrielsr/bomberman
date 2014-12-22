//Classe: MovementCommandEvent
//Versão: 1.00
//Feito pelo Grupo 7

package br.unb.unbomber.event;

import net.mostlyoriginal.api.event.common.Event;
import br.unb.unbomber.component.Direction;

import com.artemis.Entity;

public class MovementCommandEvent  implements Event {

	private final Direction direction;
	
	private final Entity entity;
	
	/**
	 * Method MovementCommandEvent recebe quem se moveu e armazena no evento.
	 * @param type tipo do movimento (UP, LEFT, DOWN, RIGHT)
	 * @param entity qual entidade se moveu
	 */
	public MovementCommandEvent(Direction type, Entity entity){
		this.direction = type;
		this.entity = entity;
	}

	/**
	 * Method MovementType resgato o tipo do movimento no evento.
	 * @return MovementType (UP, LEFT, DOWN, RIGHT)
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * Method getEntityId resgata o ID da entidade que se moveu no evento
	 * @return int (entityId)
	 */
	public Entity getEntity(){
		return this.entity;
	}
	
}
