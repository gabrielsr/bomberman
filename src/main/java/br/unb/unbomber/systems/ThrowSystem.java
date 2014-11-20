package br.unb.unbomber.systems;

import java.util.List;

import org.apache.tools.ant.filters.LineContains.Contains;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.BombThrowEvent;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;
import br.unb.unbomber.event.MovementMadeEvent;

public class ThrowSystem extends BaseSystem {
	private int TRHOW_CONSTAT = 5;

	public void update() {

		EntityManager entityManager = getEntityManager();
		List<Event> actionCommandEvents = getEntityManager().getEvents(
				ActionCommandEvent.class);
		List<Event> collisionEvents = getEntityManager().getEvents(
				CollisionEvent.class);
		List<Event> movementCommand = getEntityManager().getEvents(
				MovementCommandEvent.class);
		
		for (Event event : actionCommandEvents) {

			/* retira um evento da lista */
			ActionCommandEvent actionCommand = (ActionCommandEvent) event;
			
			int id = actionCommand.getEntityId();
			Component powerup = entityManager.getComponent(PowerUp.class,id);
			
			ActionType type = actionCommand.getType();
			
			if(powerup.getBlueGlove()==true){// fizemos isso pois ainda nao foi implementado o power up
				if(type == ActionType.DROP_BOMB){
					/*
						joga bomba
						*/
					for (Event colEvent : collisionEvents){
						CollisionEvent collision = (CollisionEvent)colEvent;
						int sourceId = collision.getSourceId();
						if(sourceId == id){
							int targetId = collision.getTargetId();
							Component explosion = entityManager.getComponent(Explosion.class, targetId);
							
							if(explosion !=null){
								CellPlacement Coord = (CellPlacement) getEntityManager().getComponent(
										CellPlacement.class, targetId);
								int x = Coord.getCellX();
								int y = Coord.getCellY();
								
								for(Event placement : movementCommand){
									MovementCommandEvent move = (MovementCommandEvent) placement;
									if(move.getEntityId()==sourceId){
										MovementType moveType = move.getType();
										if(moveType == MovementType.MOVE_UP){
											Coord.setCellY(y+TRHOW_CONSTAT);
										}
										if(moveType == MovementType.MOVE_DOWN){
											Coord.setCellY(y-TRHOW_CONSTAT);
										}
										if(moveType == MovementType.MOVE_RIGHT){
											Coord.setCellX(x+TRHOW_CONSTAT);
										}
										if(moveType == MovementType.MOVE_LEFT){
											Coord.setCellX(x-TRHOW_CONSTAT);
										}
									}
								}
					
								
							}
						}
						
					}
				}
			}
			
		}
		/**
		 * pegamos a lista de todos os pwer up pegos na partida
		 **/
		/* pegamos o persinagem que possui o blue glove*/
		/* havera um for que checara as colisoes entre personagem o bomba */
		

	}

}