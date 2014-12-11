/**
 * @file MovimentSystem.java 
 * @brief Este modulo trata dos Movimentos jogo bomberman,  
 * criado pelo grupo 6 da turma de programacao sistematica 2-2014 ministrada pela professora genaina
 *
 * @author Igor Chaves Sodre
 * @author Pedro Borges Pio
 * @author Kilmer Luiz Aleluia
 * @since 01/10/2014
 * @version 1.1
 */
package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.MovedEntityEvent;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;
import br.unb.unbomber.event.MovementMadeEvent;

public class MovimentSystem extends BaseSystem {

	/**
	 * bomb constructor
	 */
	public MovimentSystem() {
		super();
	}

	/**
	 * bomb constructor
	 * 
	 * @param model
	 *            one instance of the EntityManager
	 */
	public MovimentSystem(EntityManager model) {
		super(model);
	}
	
	//private EntityManager model;

	private int originalX;
	private int originalY;

	/** < inicia as acoes de movimeto do jogo */
	public void update() {
		EntityManager manager = getEntityManager();

		/** < cria uma lista de eventos de movimentos feitos */
		List<Event> actionEvents = getEntityManager().getEvents(
				MovementCommandEvent.class);

		/** < variavel que recebera as novas coordenadas a serem manipuladas */
		CellPlacement Coord;

		/** < loop que trata os eventos capturados na lista movimentos anterior */
		for (Event event : actionEvents) {

			/** < retira um evento da lista */
			MovementCommandEvent actionCommand = (MovementCommandEvent) event;

			/** < recebe o id da entidade que realizar� o movimento */
			int id = actionCommand.getEntityId();

			/** < recebe a velocidade da entidade */
			Movable speedable = (Movable) getEntityManager().getComponent(
					Movable.class, id);
			float speed = speedable.getSpeed();

			/** < recebe a posicao atual da entidade */
			Coord = (CellPlacement) getEntityManager().getComponent(
					CellPlacement.class, id);
			/** < recebe as coordenadas da posicao atual da entidade */
			int x = Coord.getCellX();
			int y = Coord.getCellY();
			originalX = x;
			originalY = y;
			
			MovedEntityEvent movedEntity = new MovedEntityEvent();
			movedEntity.setSpeed(speed);
			movedEntity.setId(id);
			/**
			 * < verifica o tipo de movimento e atualiza as coordenadas x e y de
			 * acordo com o mesmo
			 */
			if (actionCommand.getType() == MovementType.MOVE_UP) {
				y = (int) (Coord.getCellY() + speed);
				movedEntity.setDirection(Direction.UP);

			}
			if (actionCommand.getType() == MovementType.MOVE_DOWN) {
				y = (int) (Coord.getCellY() - speed);
				movedEntity.setDirection(Direction.DOWN);
			}
			if (actionCommand.getType() == MovementType.MOVE_RIGHT) {
				x = (int) (Coord.getCellX() + speed);
				movedEntity.setDirection(Direction.RIGHT);
			}
			if (actionCommand.getType() == MovementType.MOVE_LEFT) {
				x = (int) (Coord.getCellX() - speed);
				movedEntity.setDirection(Direction.LEFT);
			}

			/** < cria o evento de novo posicionamento da entidade */
			// MovementMadeEvent newPlacement = new MovementMadeEvent();
			manager.addEvent(movedEntity);
			Coord.setCellY(y);
			Coord.setCellX(x);

			int colidiu = 0;

			CollisionSystem collisionUpdate = new CollisionSystem(manager);
			//collisionUpdate.update(); o colision system nao esta funcionando corretemente

			List<Event> collisionEvents = getEntityManager().getEvents(
					CollisionEvent.class);

			for (Event colEvent : collisionEvents) {
				CollisionEvent collision = (CollisionEvent) colEvent;
				String pudim = "pudim";
				System.out.println(pudim);

				int sourceId = collision.getSourceId();
				/** <caso haja colis�o muda valor da variavel colidiu */
				if (id == sourceId || id == collision.getTargetId()) {
					colidiu++;

				}
				
			}

			/** < volta pro posicionamento original da entidade */
			if (colidiu != 0) {
				Coord.setCellX(originalX);
				Coord.setCellY(originalY);
				/*
				 * String pudim = "pudim"; System.out.println(pudim);
				 */
			}else {
				Coord.setCellX(x);
				Coord.setCellY(y);
			}
		}
	}

}