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

import br.unb.entitysystem.BaseSystem;
import br.unb.entitysystem.Component;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.Event;
import br.unb.gridphysics.GridDisplacement;
import br.unb.gridphysics.MovementCalc;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.MovementBarrier;
import br.unb.unbomber.component.MovementBarrier.MovementBarrierType;
import br.unb.unbomber.event.MovedEntityEvent;
import br.unb.unbomber.event.MovementCommandEvent;

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

		createMovementIntention();

		handleMovedEntityEvents();		
	}
	
	private void handleMovedEntityEvents() {
		// Movement made
		
		List<Event> pendingMovements = getEntityManager().getEvents(MovedEntityEvent.class);
		
		for (Event movement : pendingMovements) {
			handle((MovedEntityEvent)movement);
		}

		pendingMovements.clear();

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
		
		if(originCell == null){
			LOGGER.log(Level.SEVERE, "moving entity withou cellposiiont. It was removed?");
			return; 
		}
		
		Vector2D<Integer> originCellIndex = originCell.getIndex();
		
		/** Calculate the displacement and new cell*/
		
		Direction movementDirection = Direction.asDirection(command.getType());
		
		Vector2D<Float> displacement = MovementCalc.displacement(
				movementDirection, moved.getSpeed());

		Vector2D<Float> origPosition = moved.getCellPosition();
				
		/** This is a Vector with the final point */
		Vector2D<Float> dest = origPosition.add(displacement);

		Vector2D<Integer> crossVector = MovementCalc.getCrossVector(origPosition, dest);

		Vector2D<Integer> barriers = lookAhead(originCellIndex, crossVector);
		
		displacement = restrictUpdate(origPosition, displacement, barriers);

		//TODO handle grid wrapper. If an entity get to the a border should reenter in another
		
		/** calculate the displacement */
		GridDisplacement gridDisplacement = MovementCalc.gridDisplacement(moved.getCellPosition(), displacement);
		
		Vector2D<Integer> destCellIndex = originCellIndex.add(gridDisplacement.getCells());
		Vector2D<Float> destCellInternalPosition = gridDisplacement.getPosition();
		
		/** fire an event so the collision system can check this movement */		
		MovedEntityEvent movedEntity = new MovedEntityEvent();
		movedEntity.setSpeed(moved.getSpeed());
		movedEntity.setMovedEntityId(moved.getEntityId());
		movedEntity.setDestinationCell(destCellIndex);		
		movedEntity.setDisplacement(destCellInternalPosition);
		getEntityManager().addEvent(movedEntity);
		
		/** we're done with this command */
		processedEvents.add(command);
		
		/** this movement is not done yet! We need to check for collisions it a future tick*/
		pendingMovements.add(movedEntity);
	}
	
	private void handle(MovedEntityEvent movement) {
		
		/** Update cellPlacement */
		int entityId = movement.getMovedEntityId();
	
		/** Update Cell Relative Position */	
		Movable movable = (Movable) getEntityManager().getComponent(Movable.class, entityId);
		
		updateEntity(entityId, movement.getDestinationCell(), movement.getDisplacement());
		
		LOGGER.log(Level.FINE, "entity moved to" + movable.getCellPosition());
		
	}
	
	void updateEntity(int entityId, Vector2D<Integer> cell, Vector2D<Float> cellPosition) {
		
		CellPlacement cellPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class, entityId);
		cellPlacement.setIndex(cell);
		
		Movable movable = (Movable) getEntityManager().getComponent(Movable.class, entityId);
		movable.setCellPosition(cellPosition);
		
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
	

	/**
	 * Return a restricted displacement.
	 * 
	 *  0 = actual + restrictedDisplacement
	 *  rDisplacement = (0.5 - actual) so the entity don't pass half a cell
	 * towards a BLOCK.
	 * 
	 * @param actual
	 * @param displacement
	 * @param barriers
	 * @return
	 */
	protected Vector2D<Float> restrictUpdate(Vector2D<Float> actual, Vector2D<Float> displacement,
			Vector2D<Integer> barriers) {
		
		final Vector2D<Float> HALF_CELL = new Vector2D<Float>(0.5f, 0.5f);
		/* invert barriers, xor (1,1) */
		Vector2D<Integer> modBarriers =  barriers.xor(new Vector2D<>(0,0));
		
		/* invert barriers, xor (1,1) */
		Vector2D<Integer> invertedBarriers =  modBarriers.xor(new Vector2D<>(1,1));

		/* do restrict to actual complement */
		Vector2D<Float> restrictedDisplacement = modBarriers.toFloatVector().mult(HALF_CELL.sub(actual));
		
		/* filter not restricted */
		Vector2D<Float> notRestrictedDisplacement =  invertedBarriers.toFloatVector().mult(displacement);
		
		/* unify the restricted and not restricted */
		return notRestrictedDisplacement.add(restrictedDisplacement);
		
	}
	
}