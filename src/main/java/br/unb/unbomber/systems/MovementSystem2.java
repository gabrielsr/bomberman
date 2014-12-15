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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.MovementBarrier;
import br.unb.unbomber.component.MovementBarrier.MovementBarrierType;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.MovedEntityEvent;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.gridphysics.MovementCalc;
import br.unb.unbomber.gridphysics.Vector2D;

public class MovementSystem2 extends BaseSystem {

	/**
	 * bomb constructor
	 */
	public MovementSystem2() {
		super();
	}
	
	List<MovementCommandEvent> processedEvents;
	
	List<MovedEntityEvent> pendingMovements;

	/**
	 * bomb constructor
	 * 
	 * @param model
	 *            one instance of the EntityManager
	 */
	public MovementSystem2(EntityManager model) {
		super(model);
	}

	@Override
	public void start(){
		 processedEvents = new ArrayList<>();
		 pendingMovements = new ArrayList<>();
	}

	
	public void update() {
		
		handlePastMovementIntentions();
		
		createMovementIntention();
		
	}
	
	private void handlePastMovementIntentions() {
		List<Event> collisionEvents = getEntityManager().getEvents(
				CollisionEvent.class);

		//Movement made
		for(MovedEntityEvent movement: pendingMovements){
			
			
			boolean handled = false;
			
			//collisions that occurred
			for (Event colEvent : collisionEvents) {
				CollisionEvent collision = (CollisionEvent) colEvent;
				
				if (movement.getMovedEntityId() ==  collision.getSourceId()) {
					 handleCollision(movement, collision);
					handled = true;
					break;
				}
			}
			if(!handled){
				handleFreeMoviment(movement);	
			}
			
		}
		
		pendingMovements.clear();

	}

	private void handleCollision(MovedEntityEvent movement,
			CollisionEvent collision) {
		
		
		
		
		
		
		
	}
	
	private void handleFreeMoviment(MovedEntityEvent movement) {
		
		/** Update cellPlacement */
		int entityId = movement.getMovedEntityId();
	
		CellPlacement oldCell = (CellPlacement) getEntityManager().getComponent(CellPlacement.class, entityId);		
		CellPlacement newCell = (CellPlacement) movement.getDestinationCell();
		
		changeComponent(entityId, oldCell, newCell);
		
		/** Update Cell Relative Position */
		
		
		
		Movable movable = (Movable) getEntityManager().getComponent(Movable.class, entityId);
		Vector2D<Float> finalDisplacement = movable.getCellDisplacement()
				.add(movement.getDisplacement());
		
		finalDisplacement = MovementCalc.rebase(oldCell, newCell, finalDisplacement);
		
		movable.setCellDisplacement(finalDisplacement);
		
		LOGGER.log(Level.FINE, "entity moved to" + movable.getCellDisplacement());
		
	}
	
	void changeComponent(int entityId, Component orig, Component dest) {
		dest.setEntityId(entityId);
		
		if(orig!=null){
			getEntityManager().remove(orig);
		}
		
		getEntityManager().addComponent(dest);
	}

	/**
	 * Look for new Movement Commands and create their equivalent MovedEntityEvent.
	 * It do not update the entity placement. Think in MovedEntityEvent as a 
	 * intention of movement.
	 */
	public void createMovementIntention(){
		/** < cria uma lista de eventos de movimentos feitos */
		List<Event> actionEvents = getEntityManager().getEvents(
				MovementCommandEvent.class);

		actionEvents.removeAll(processedEvents);		
		
		/** < loop que trata os eventos capturados na lista movimentos anterior */
		for (Event event : actionEvents) {
			/** Query Movable */

			MovementCommandEvent command = (MovementCommandEvent) event;
			handle(command);

		}
	}
	
	public void handle(MovementCommandEvent command){
		int entityId = command.getEntityId();
		Movable moved = (Movable) getEntityManager().getComponent(
				Movable.class, entityId);
		
		CellPlacement originCell = (CellPlacement) getEntityManager().getComponent(
				CellPlacement.class, entityId);
		
		
		/** Calculate the displacement and new cell*/
		
		Direction movementDirection = Direction.asDirection(command.getType());
		
		Vector2D<Float> displacement = MovementCalc.displacement(
				movementDirection, moved.getSpeed());

		Vector2D<Float> origPosition = moved.getCellDisplacement();
				
		/** This is a Vector with the final point */
		Vector2D<Float> dest = displacement.add(moved.getCellDisplacement());

		Vector2D<Integer> crossVector = MovementCalc.getCrossVector(origPosition, dest);

		Vector2D<Integer> barriers = lookAhead(originCell.getIndex(), crossVector);
		
		if(barriers.equals(new Vector2D<Number>(0, 0))){
			restrictUpdate(displacement, barriers, crossVector);
		}

		/** calculate the new cell */
		CellPlacement finalPosition = MovementCalc.finalCellPlacement(originCell, displacement,moved.getCellDisplacement());

		
		/** fire an event so the collision system can check this movement */
		
		MovedEntityEvent movedEntity = new MovedEntityEvent();
		movedEntity.setSpeed(moved.getSpeed());
		movedEntity.setMovedEntityId(moved.getEntityId());
		
		movedEntity.setDestinationCell(finalPosition);		
		movedEntity.setDisplacement(displacement);
		getEntityManager().addEvent(movedEntity);
		
		/** we're done with this command */
		processedEvents.add(command);
		
		/** this movement is not done yet! We need to check for collisions it a future tick*/
		pendingMovements.add(movedEntity);
	}
	


	/**
	 * Return an Vector. 0 in a dimension mean that this dimention is free.
	 * 1 mean it it blocked.
	 * 
	 * 
	 * @param gridPosition
	 * @param displacement
	 * @return
	 */
	public Vector2D<Integer> lookAhead( Vector2D<Integer> gridPosition, Vector2D<Integer> displacement){
				
		/** in principle, the path is free, no restriction in x or y */
		Vector2D<Integer> restrictions = new  Vector2D<Integer>(0,0);
		
		
		for(Vector2D<Integer> displacementComponent:displacement.components()){
			Vector2D<Integer> toLookCell = gridPosition.add(displacementComponent);
			
			//TODO create a index and turn it more efficient
			for(Component compCellPlacement: getEntityManager().getComponents(CellPlacement.class)){
				CellPlacement cell = (CellPlacement) compCellPlacement;
				
				//Compare the cell position
				if(cell.getIndex().equals(toLookCell)){
					MovementBarrier barrier = (MovementBarrier) getEntityManager()
							.getComponent(MovementBarrier.class, cell.getEntityId());
					if(barrier!=null 
							&& barrier.getType() == MovementBarrierType.BLOCKER){
						restrictions = restrictions.add(displacementComponent);
						break;
					}
				}
			}

		}
		return restrictions;
	}
	

	
	private void restrictUpdate(Vector2D<Float> displacement,
			Vector2D<Integer> barriers, Vector2D<Integer> crossVector) {
	
		/** turn the displacement 0 in the direction with barrier */
		displacement.add(
				(displacement.mult(-1.0f).mult(barriers.toFloatVector())));
		
	}
	
}