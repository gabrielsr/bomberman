//Classe: MovementCommandEvent
//Versão: 1.00
//Feito pelo Grupo 7

package br.unb.unbomber.event;

import java.util.UUID;

import br.unb.unbomber.component.Direction;

public class MovementCommandEvent  extends CommandEvent {

	private final Direction direction;
	
	/**
	 * Method MovementCommandEvent recebe quem se moveu e armazena no evento.
	 * @param type tipo do movimento (UP, LEFT, DOWN, RIGHT)
	 * @param entityUuid qual entidade se moveu
	 */
	public MovementCommandEvent(Direction type, UUID entityUuid){
		this.direction = type;
		this.entityUuid = entityUuid;
	}

	/**
	 * Method MovementType resgato o tipo do movimento no evento.
	 * @return MovementType (UP, LEFT, DOWN, RIGHT)
	 */
	public Direction getDirection() {
		return direction;
	}

	
}
