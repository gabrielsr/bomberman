//Classe: MovementCommandEvent
//Versão: 1.00
//Feito pelo Grupo 7

package br.unb.unbomber.event;

import br.unb.entitysystem.Event;

public class MovementCommandEvent extends Event {

	//Definição dos tipos de ação capturados
	public enum MovementType{
		MOVE_UP,
		MOVE_RIGHT,
		MOVE_DOWN,
		MOVE_LEFT;
	}

	private final MovementType type;
	
	private final int entityId;
	
	/**
	 * Method MovementCommandEvent recebe quem se moveu e armazena no evento.
	 * @param type tipo do movimento (UP, LEFT, DOWN, RIGHT)
	 * @param entityId qual entidade se moveu
	 */
	public MovementCommandEvent(MovementType type, int entityId){
		this.type = type;
		this.entityId = entityId;
	}

	/**
	 * Method MovementType resgato o tipo do movimento no evento.
	 * @return MovementType (UP, LEFT, DOWN, RIGHT)
	 */
	public MovementType getType() {
		return type;
	}
	
	/**
	 * Method getEntityId resgata o ID da entidade que se moveu no evento
	 * @return int (entityId)
	 */
	public int getEntityId(){
		return this.entityId;
	}
	
}
